package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.ServicioAlojamientoDTO;

import java.util.List;

public interface ServicioAlojamientoService {

    /** Crea un nuevo servicio (valida duplicado por nombre). */
    ServicioAlojamientoDTO crear(String nombre, String descripcion, String iconoUrl);

    /** Obtiene un servicio por ID. */
    ServicioAlojamientoDTO obtenerPorId(Integer id);

    /** Lista todos los servicios. */
    List<ServicioAlojamientoDTO> listar();

    /** Actualiza un servicio (nombre/descripcion/iconoUrl son opcionales). */
    ServicioAlojamientoDTO actualizar(Integer id, String nombre, String descripcion, String iconoUrl);

    /** Elimina un servicio por ID. */
    void eliminar(Integer id);

    /** Busca por nombre exacto. */
    ServicioAlojamientoDTO buscarPorNombre(String nombre);

    /** Ranking: servicios más usados con su total de alojamientos. */
    List<ServicioUsoDTO> serviciosMasUtilizados();

    /** Cuenta cuántos alojamientos usan un servicio. */
    Long contarAlojamientosPorServicio(Integer servicioId);

    // DTO de apoyo para el ranking
    class ServicioUsoDTO {
        public final ServicioAlojamientoDTO servicio;
        public final Long totalAlojamientos;
        public ServicioUsoDTO(ServicioAlojamientoDTO servicio, Long totalAlojamientos) {
            this.servicio = servicio;
            this.totalAlojamientos = totalAlojamientos != null ? totalAlojamientos : 0L;
        }
    }
}
