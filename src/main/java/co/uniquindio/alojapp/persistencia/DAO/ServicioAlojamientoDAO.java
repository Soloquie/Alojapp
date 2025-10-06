package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.ServicioAlojamientoDTO;
import co.uniquindio.alojapp.persistencia.Entity.ServicioAlojamiento;
import co.uniquindio.alojapp.persistencia.Mapper.ServicioAlojamientoMapper;
import co.uniquindio.alojapp.persistencia.Repository.ServicioAlojamientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO para operaciones de persistencia de servicios de alojamiento
 */
@Repository
@RequiredArgsConstructor
public class ServicioAlojamientoDAO {

    private final ServicioAlojamientoRepository servicioRepository;
    private final ServicioAlojamientoMapper servicioMapper;

    /**
     * Crear nuevo servicio
     */
    public ServicioAlojamientoDTO save(String nombre, String descripcion, String iconoUrl) {
        if (servicioRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe un servicio con ese nombre");
        }

        ServicioAlojamiento servicio = ServicioAlojamiento.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .iconoUrl(iconoUrl)
                .build();

        ServicioAlojamiento saved = servicioRepository.save(servicio);
        return servicioMapper.toDTO(saved);
    }

    /**
     * Buscar servicio por ID
     */
    public Optional<ServicioAlojamientoDTO> findById(Long id) {
        return servicioRepository.findById(id)
                .map(servicioMapper::toDTO);
    }

    /**
     * Buscar todos los servicios
     */
    public List<ServicioAlojamientoDTO> findAll() {
        return servicioMapper.toDTOList(servicioRepository.findAll());
    }

    /**
     * Buscar por nombre
     */
    public Optional<ServicioAlojamientoDTO> findByNombre(String nombre) {
        return servicioRepository.findByNombre(nombre)
                .map(servicioMapper::toDTO);
    }

    /**
     * Buscar servicios más utilizados
     */
    public List<Object[]> findServiciosMasUtilizados() {
        return servicioRepository.findServiciosMasUtilizados();
    }

    /**
     * Contar alojamientos que usan un servicio
     */
    public Long countAlojamientosByServicio(Long servicioId) {
        return servicioRepository.countAlojamientosByServicioId(servicioId);
    }

    /**
     * Actualizar servicio
     */
    public Optional<ServicioAlojamientoDTO> actualizar(Long id, String nombre, String descripcion, String iconoUrl) {
        return servicioRepository.findById(id)
                .map(servicio -> {
                    if (nombre != null) servicio.setNombre(nombre);
                    if (descripcion != null) servicio.setDescripcion(descripcion);
                    if (iconoUrl != null) servicio.setIconoUrl(iconoUrl);
                    ServicioAlojamiento updated = servicioRepository.save(servicio);
                    return servicioMapper.toDTO(updated);
                });
    }

    /**
     * Eliminar servicio
     */
    public boolean delete(Long id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}