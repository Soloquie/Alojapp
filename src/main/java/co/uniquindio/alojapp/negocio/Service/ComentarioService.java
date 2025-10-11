package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.ComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ReportarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;

import java.util.List;

public interface ComentarioService {

    // Crear
    ComentarioDTO crear(Integer usuarioId, CrearComentarioRequest request);

    // Lectura
    ComentarioDTO obtenerPorId(Integer comentarioId);
    PaginacionResponse<ComentarioDTO> listarPorAlojamiento(Integer alojamientoId, int pagina, int tamano);
    List<ComentarioDTO> listarPorUsuario(Integer usuarioId);
    PaginacionResponse<ComentarioDTO> listarPorAnfitrion(Integer anfitrionId, int pagina, int tamano);
    List<ComentarioDTO> listarSinRespuestaPorAnfitrion(Integer anfitrionId);

    // Métricas
    Double calificacionPromedio(Integer alojamientoId);
    Long contarPorAlojamiento(Integer alojamientoId);
    List<ComentarioDTO> mejoresComentarios(Integer alojamientoId);

    // Mutaciones
    ComentarioDTO actualizar(Integer usuarioId, Integer comentarioId, ActualizarComentarioRequest request);
    void eliminar(Integer usuarioId, Integer comentarioId, boolean adminOverride);

    // Reporte (si luego agregas entidad de reportes)
    void reportar(Integer usuarioId, Integer comentarioId, ReportarComentarioRequest request);
}
