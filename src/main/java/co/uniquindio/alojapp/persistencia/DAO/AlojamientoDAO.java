package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.AlojamientoDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.request.BuscarAlojamientosRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import co.uniquindio.alojapp.persistencia.Entity.ServicioAlojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoAlojamiento;
import co.uniquindio.alojapp.persistencia.Mapper.AlojamientoMapper;
import co.uniquindio.alojapp.persistencia.Repository.AlojamientoRepository;
import co.uniquindio.alojapp.persistencia.Repository.AnfitrionRepository;
import co.uniquindio.alojapp.persistencia.Repository.ServicioAlojamientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operaciones de persistencia de alojamientos
 * Implementa RN3, RN4, RN5, RN6, RN21, RN22, RN23, RN24, RN25
 */
@Repository
@RequiredArgsConstructor
public class AlojamientoDAO {

    private final AlojamientoRepository alojamientoRepository;
    private final AnfitrionRepository anfitrionRepository;
    private final ServicioAlojamientoRepository servicioRepository;
    private final AlojamientoMapper alojamientoMapper;

    /**
     * Crear nuevo alojamiento
     * RN3: Al menos 1 imagen, máximo 10
     * RN4: Precio mayor a 0
     * RN5: Capacidad mayor a 0
     * RN6: Ubicación válida
     */
    public AlojamientoDTO save(CrearAlojamientoRequest request, Integer anfitrionId) {
        Anfitrion anfitrion = anfitrionRepository.findById(anfitrionId)
                .orElseThrow(() -> new RuntimeException("Anfitrión no encontrado"));

        Alojamiento alojamiento = Alojamiento.builder()
                .anfitrion(anfitrion)
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .ciudad(request.getCiudad())
                .direccion(request.getDireccion())
                .latitud(request.getLatitud())
                .longitud(request.getLongitud())
                .precioNoche(request.getPrecioNoche())
                .capacidadMaxima(request.getCapacidadMaxima())
                .imagenPrincipalUrl(request.getImagenPrincipalUrl())
                .estado(EstadoAlojamiento.ACTIVO)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        // Asociar servicios si se proporcionaron
        if (request.getServiciosIds() != null && !request.getServiciosIds().isEmpty()) {
            List<ServicioAlojamiento> servicios = servicioRepository.findAllById(request.getServiciosIds());
            alojamiento.setServicios(servicios);
        }

        Alojamiento saved = alojamientoRepository.save(alojamiento);
        return alojamientoMapper.toDTO(saved);
    }

    /**
     * Buscar alojamiento por ID
     */
    public Optional<AlojamientoDTO> findById(Integer id) {
        return alojamientoRepository.findById(id)
                .map(alojamientoMapper::toDTO);
    }

    /**
     * Buscar entity por ID (uso interno)
     */
    public Optional<Alojamiento> findEntityById(Integer id) {
        return alojamientoRepository.findById(id);
    }

    /**
     * Buscar alojamientos activos con paginación
     * RN23: No mostrar eliminados
     * RN24: Paginación de 10 por página
     */
    public PaginacionResponse<AlojamientoDTO> findActivos(int pagina, int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        Page<Alojamiento> page = alojamientoRepository.findByEstado(EstadoAlojamiento.ACTIVO, pageable);

        return buildPaginacionResponse(page);
    }

    /**
     * Buscar alojamientos por anfitrión
     */
    public List<AlojamientoDTO> findByAnfitrion(Integer anfitrionId) {
        return alojamientoMapper.toDTOList(
                alojamientoRepository.findByAnfitrionId(anfitrionId)
        );
    }

    public PaginacionResponse<AlojamientoDTO> findByAnfitrion(Integer anfitrionId, int pagina, int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        Page<Alojamiento> page = alojamientoRepository.findByAnfitrionId(anfitrionId, pageable);

        return buildPaginacionResponse(page);
    }

    /**
     * Búsqueda con filtros completos
     * RN25: Solo alojamientos disponibles en fechas seleccionadas
     */
    public PaginacionResponse<AlojamientoDTO> buscarConFiltros(BuscarAlojamientosRequest request) {
        // Crear ordenamiento
        Sort sort = Sort.by(
                "DESC".equalsIgnoreCase(request.getDireccionOrden())
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                request.getOrdenarPor() != null ? request.getOrdenarPor() : "fechaCreacion"
        );

        Pageable pageable = PageRequest.of(
                request.getPagina(),
                request.getTamanoPagina(),
                sort
        );

        Page<Alojamiento> page = alojamientoRepository.buscarConFiltros(
                request.getCiudad(),
                request.getPrecioMin(),
                request.getPrecioMax(),
                request.getCapacidadMinima(),
                request.getServiciosIds(),
                request.getFechaCheckin(),
                request.getFechaCheckout(),
                pageable
        );

        return buildPaginacionResponse(page);
    }

    /**
     * Verificar disponibilidad
     * RN14: Validar no solapamiento
     */
    public boolean verificarDisponibilidad(Integer alojamientoId,
                                           java.time.LocalDate fechaCheckin,
                                           java.time.LocalDate fechaCheckout) {
        return alojamientoRepository.estaDisponible(alojamientoId, fechaCheckin, fechaCheckout);
    }

    /**
     * Actualizar alojamiento
     * RN18: Solo el propietario puede modificar
     */
    public Optional<AlojamientoDTO> actualizar(Integer id, ActualizarAlojamientoRequest request, Integer anfitrionId) {
        return alojamientoRepository.findById(id)
                .filter(a -> a.getAnfitrion().getId().equals(anfitrionId)) // Validar propietario
                .map(alojamiento -> {
                    if (request.getTitulo() != null) {
                        alojamiento.setTitulo(request.getTitulo());
                    }
                    if (request.getDescripcion() != null) {
                        alojamiento.setDescripcion(request.getDescripcion());
                    }
                    if (request.getCiudad() != null) {
                        alojamiento.setCiudad(request.getCiudad());
                    }
                    if (request.getDireccion() != null) {
                        alojamiento.setDireccion(request.getDireccion());
                    }
                    if (request.getPrecioNoche() != null) {
                        alojamiento.setPrecioNoche(request.getPrecioNoche());
                    }
                    if (request.getCapacidadMaxima() != null) {
                        alojamiento.setCapacidadMaxima(request.getCapacidadMaxima());
                    }
                    if (request.getEstado() != null) {
                        alojamiento.setEstado(EstadoAlojamiento.valueOf(request.getEstado()));
                    }
                    if (request.getServiciosIds() != null) {
                        List<ServicioAlojamiento> servicios = servicioRepository.findAllById(request.getServiciosIds());
                        alojamiento.setServicios(servicios);
                    }

                    alojamiento.setFechaActualizacion(LocalDateTime.now());
                    Alojamiento updated = alojamientoRepository.save(alojamiento);
                    return alojamientoMapper.toDTO(updated);
                });
    }

    /**
     * Eliminar alojamiento (soft delete)
     * RN21: Solo si no tiene reservas futuras
     * RN22: Soft delete manteniendo historial
     */
    public boolean eliminar(Integer id, Integer anfitrionId) {
        return alojamientoRepository.findById(id)
                .filter(a -> a.getAnfitrion().getId().equals(anfitrionId)) // Validar propietario
                .map(alojamiento -> {
                    // Verificar reservas futuras
                    if (alojamientoRepository.tieneReservasFuturas(id)) {
                        throw new RuntimeException("No se puede eliminar: tiene reservas futuras");
                    }

                    // Soft delete
                    alojamiento.setEstado(EstadoAlojamiento.ELIMINADO);
                    alojamientoRepository.save(alojamiento);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Buscar alojamientos más populares
     */
    public PaginacionResponse<AlojamientoDTO> findMasPopulares(int pagina, int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        Page<Alojamiento> page = alojamientoRepository.findMasPopulares(pageable);

        return buildPaginacionResponse(page);
    }

    /**
     * Buscar por ciudad
     */
    public PaginacionResponse<AlojamientoDTO> findByCiudad(String ciudad, int pagina, int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        Page<Alojamiento> page = alojamientoRepository.findByCiudadContainingIgnoreCaseAndEstado(
                ciudad,
                EstadoAlojamiento.ACTIVO,
                pageable
        );

        return buildPaginacionResponse(page);
    }

    /**
     * Contar alojamientos por ciudad
     */
    public List<Object[]> countByCiudad() {
        return alojamientoRepository.countByCiudad();
    }

    /**
     * Verificar si tiene reservas futuras
     */
    public boolean tieneReservasFuturas(Integer alojamientoId) {
        return alojamientoRepository.tieneReservasFuturas(alojamientoId);
    }

    /**
     * Contar total de alojamientos activos
     */
    public long countActivos() {
        return alojamientoRepository.findByEstado(EstadoAlojamiento.ACTIVO).size();
    }

    // Método helper para construir respuesta paginada
    private PaginacionResponse<AlojamientoDTO> buildPaginacionResponse(Page<Alojamiento> page) {
        List<AlojamientoDTO> dtos = alojamientoMapper.toDTOList(page.getContent());

        return PaginacionResponse.<AlojamientoDTO>builder()
                .contenido(dtos)
                .paginaActual(page.getNumber())
                .tamanoPagina(page.getSize())
                .totalElementos(page.getTotalElements())
                .totalPaginas(page.getTotalPages())
                .esPrimera(page.isFirst())
                .esUltima(page.isLast())
                .tieneSiguiente(page.hasNext())
                .tieneAnterior(page.hasPrevious())
                .build();
    }
}