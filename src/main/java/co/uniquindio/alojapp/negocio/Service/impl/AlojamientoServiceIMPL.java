package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.AlojamientoDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.request.BuscarAlojamientosRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.AlojamientoService;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.AlojamientoDAO;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoAlojamiento;
import co.uniquindio.alojapp.persistencia.Repository.AlojamientoRepository;
import co.uniquindio.alojapp.persistencia.Repository.AnfitrionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlojamientoServiceIMPL implements AlojamientoService {

    private final AlojamientoDAO alojamientoDAO;
    private final AnfitrionRepository anfitrionRepository;
    private final AlojamientoRepository alojamientoRepository;


    private Integer anfitrionIdDeUsuario(Integer usuarioId) {
        Anfitrion a = anfitrionRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "El usuario autenticado no es anfitrión"));
        return a.getId();
    }

    @Override
    @Transactional
    public AlojamientoDTO crear(Integer usuarioIdAnfitrion, CrearAlojamientoRequest request) {
        validarCrear(request);
        Integer anfitrionId = anfitrionIdDeUsuario(usuarioIdAnfitrion);
        return alojamientoDAO.save(request, anfitrionId);
    }

    @Override
    @Transactional
    public AlojamientoDTO actualizar(Integer usuarioIdAnfitrion, Integer alojamientoId, ActualizarAlojamientoRequest request) {
        validarActualizar(request);
        Integer anfitrionId = anfitrionIdDeUsuario(usuarioIdAnfitrion);
        return alojamientoDAO.actualizar(alojamientoId, request, anfitrionId)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo actualizar (no existe o no es suyo)"));
    }

    @Override
    @Transactional
    public AlojamientoDTO activar(Integer usuarioIdAnfitrion, Integer alojamientoId) {
        Integer anfitrionId = anfitrionIdDeUsuario(usuarioIdAnfitrion);
        ActualizarAlojamientoRequest req = ActualizarAlojamientoRequest.builder()
                .estado(EstadoAlojamiento.ACTIVO)
                .build();
        return alojamientoDAO.actualizar(alojamientoId, req, anfitrionId)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo activar (no existe o no es suyo)"));
    }

    @Override
    @Transactional
    public AlojamientoDTO desactivar(Integer usuarioIdAnfitrion, Integer alojamientoId) {
        Integer anfitrionId = anfitrionIdDeUsuario(usuarioIdAnfitrion);
        ActualizarAlojamientoRequest req = ActualizarAlojamientoRequest.builder()
                .estado(EstadoAlojamiento.INACTIVO)
                .build();
        return alojamientoDAO.actualizar(alojamientoId, req, anfitrionId)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo desactivar (no existe o no es suyo)"));
    }

    @Override
    public void eliminarFisico(Integer usuarioIdAnfitrion, Integer alojamientoId) {

    }

    @Override
    @Transactional
    public boolean eliminar(Integer usuarioIdAnfitrion, Integer alojamientoId) {
        Integer anfitrionId = anfitrionIdDeUsuario(usuarioIdAnfitrion);
        return alojamientoDAO.eliminar(alojamientoId, anfitrionId);
    }

    @Override
    public Boolean estaDisponible(Integer alojamientoId, LocalDate checkin, LocalDate checkout) {
        if (checkin == null || checkout == null || !checkout.isAfter(checkin)) {
            throw new IllegalArgumentException("Rango de fechas inválido");
        }
        return alojamientoDAO.verificarDisponibilidad(alojamientoId, checkin, checkout);
    }

    @Override
    public PaginacionResponse<AlojamientoDTO> listarActivos(int pagina, int tamano) {
        return alojamientoDAO.findActivos(pagina, tamano);
    }

    @Override
    @Transactional
    public AlojamientoDTO actualizarDeUsuario(Integer usuarioId, Integer alojamientoId, ActualizarAlojamientoRequest req) {
        return alojamientoDAO.actualizarDeUsuario(usuarioId, alojamientoId, req)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado o no te pertenece"));
    }

    @Override
    @Transactional
    public boolean eliminarDeUsuario(Integer usuarioId, Integer alojamientoId) {
        return alojamientoDAO.eliminarDeUsuario(usuarioId, alojamientoId);
    }

    @Override
    @Transactional
    public void actualizarPortada(Integer alojamientoId, String urlPortada) {
        var ent = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alojamiento no encontrado"));

        ent.setImagenPrincipalUrl(urlPortada);
        alojamientoRepository.save(ent);
    }

    @Override
    @Transactional(readOnly = true)
    public AlojamientoDTO obtenerPorId(Integer alojamientoId) {
        return alojamientoDAO.findById(alojamientoId)
                .orElseThrow(() -> new IllegalArgumentException("Alojamiento no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlojamientoDTO> listarPorAnfitrion(Integer usuarioIdAnfitrion) {
        Integer anfitrionId = anfitrionIdDeUsuario(usuarioIdAnfitrion);
        return alojamientoDAO.findByAnfitrion(anfitrionId);
    }

    @Override
    public PaginacionResponse<AlojamientoDTO> listarPorAnfitrion(Integer anfitrionId, int pagina, int tamano) {
        return alojamientoDAO.findByAnfitrion(anfitrionId, pagina, tamano);
    }

    @Override
    public PaginacionResponse<AlojamientoDTO> buscar(BuscarAlojamientosRequest request) {
        // Validación mínima de fechas: si una viene, la otra también
        if ((request.getFechaCheckin() == null) ^ (request.getFechaCheckout() == null)) {
            throw new IllegalArgumentException("Debe enviar checkin y checkout juntos");
        }
        return alojamientoDAO.buscarConFiltros(request);
    }

    @Override
    public AlojamientoDTO agregarImagenes(Integer usuarioIdAnfitrion, Integer alojamientoId, List<String> urls) {
        return null;
    }

    @Override
    public AlojamientoDTO eliminarImagen(Integer usuarioIdAnfitrion, Integer alojamientoId, Integer imagenId) {
        return null;
    }

    // =====================
    // Validaciones simples
    // =====================
    private void validarCrear(CrearAlojamientoRequest r) {
        if (!StringUtils.hasText(r.getTitulo())) throw new IllegalArgumentException("El título es obligatorio");
        if (!StringUtils.hasText(r.getDireccion())) throw new IllegalArgumentException("La dirección es obligatoria");
        if (!StringUtils.hasText(r.getCiudad())) throw new IllegalArgumentException("La ciudad es obligatoria");
        if (r.getPrecioNoche() == null || r.getPrecioNoche().doubleValue() <= 0)
            throw new IllegalArgumentException("El precio por noche debe ser mayor a 0");
        if (r.getCapacidadMaxima() == null || r.getCapacidadMaxima() < 1)
            throw new IllegalArgumentException("La capacidad debe ser al menos 1");
        if (!StringUtils.hasText(r.getImagenPrincipalUrl()))
            throw new IllegalArgumentException("La imagen principal es obligatoria");
        validarLatLon(r.getLatitud(), r.getLongitud());
    }

    private void validarActualizar(ActualizarAlojamientoRequest r) {
        if (r.getPrecioNoche() != null && r.getPrecioNoche().doubleValue() <= 0)
            throw new IllegalArgumentException("El precio por noche debe ser mayor a 0");
        if (r.getCapacidadMaxima() != null && r.getCapacidadMaxima() < 1)
            throw new IllegalArgumentException("La capacidad debe ser al menos 1");
        if (r.getLatitud() != null || r.getLongitud() != null)
            validarLatLon(r.getLatitud(), r.getLongitud());
        if (r.getEstado() != null) {
            // valida contra enum si lo dejas como String:
            try { EstadoAlojamiento.valueOf(r.getEstado().toString()); }
            catch (Exception e) { throw new IllegalArgumentException("Estado inválido"); }
        }
    }

    private void validarFiltroBusqueda(BuscarAlojamientosRequest f) {
        if (f.getPrecioMin() != null && f.getPrecioMin().doubleValue() < 0)
            throw new IllegalArgumentException("precioMin inválido");
        if (f.getPrecioMax() != null && f.getPrecioMax().doubleValue() < 0)
            throw new IllegalArgumentException("precioMax inválido");
        if (f.getPrecioMin() != null && f.getPrecioMax() != null &&
                f.getPrecioMin().doubleValue() > f.getPrecioMax().doubleValue())
            throw new IllegalArgumentException("precioMin no puede ser mayor que precioMax");
        if (f.getCapacidadMinima() != null && f.getCapacidadMinima() < 1)
            throw new IllegalArgumentException("capacidadMinima inválida");
    }

    private void validarLatLon(BigDecimal lat, BigDecimal lon) {
        if (lat == null || lon == null) {
            throw new IllegalArgumentException("Coordenadas obligatorias");
        }
        if (lat.compareTo(new BigDecimal("-90")) < 0 || lat.compareTo(new BigDecimal("90")) > 0) {
            throw new IllegalArgumentException("Latitud inválida");
        }
        if (lon.compareTo(new BigDecimal("-180")) < 0 || lon.compareTo(new BigDecimal("180")) > 0) {
            throw new IllegalArgumentException("Longitud inválida");
        }
    }

}
