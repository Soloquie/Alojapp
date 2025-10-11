package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.ComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ReportarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.ComentarioService;
import co.uniquindio.alojapp.negocio.excepciones.AccesoNoAutorizadoException;
import co.uniquindio.alojapp.negocio.excepciones.ReglaNegocioException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.ComentarioDAO;
import co.uniquindio.alojapp.persistencia.Entity.Comentario;
import co.uniquindio.alojapp.persistencia.Mapper.ComentarioMapper;
import co.uniquindio.alojapp.persistencia.Repository.ComentarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComentarioServiceIMPL implements ComentarioService {

    private final ComentarioDAO comentarioDAO;
    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;

    // =====================================
    // Crear
    // =====================================
    @Override
    @Transactional
    public ComentarioDTO crear(Integer usuarioId, CrearComentarioRequest request) {
        log.info("Creando comentario. usuarioId={}, reservaId={}", usuarioId, request.getReservaId());
        // La DAO ya valida RN16/RN17/RN7/RN8 y pertenencia de reserva
        return comentarioDAO.save(request, usuarioId);
    }

    // =====================================
    // Lectura
    // =====================================
    @Override
    public ComentarioDTO obtenerPorId(Integer comentarioId) {
        return comentarioDAO.findById(comentarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comentario no encontrado"));
    }

    @Override
    public PaginacionResponse<ComentarioDTO> listarPorAlojamiento(Integer alojamientoId, int pagina, int tamano) {
        return comentarioDAO.findByAlojamiento(alojamientoId, pagina, tamano);
    }

    @Override
    public java.util.List<ComentarioDTO> listarPorUsuario(Integer usuarioId) {
        return comentarioDAO.findByUsuario(usuarioId);
    }

    @Override
    public PaginacionResponse<ComentarioDTO> listarPorAnfitrion(Integer anfitrionId, int pagina, int tamano) {
        return comentarioDAO.findByAnfitrion(anfitrionId, pagina, tamano);
    }

    @Override
    public java.util.List<ComentarioDTO> listarSinRespuestaPorAnfitrion(Integer anfitrionId) {
        return comentarioDAO.findSinRespuestaByAnfitrion(anfitrionId);
    }

    // =====================================
    // Métricas
    // =====================================
    @Override
    public Double calificacionPromedio(Integer alojamientoId) {
        return comentarioDAO.calcularCalificacionPromedio(alojamientoId);
    }

    @Override
    public Long contarPorAlojamiento(Integer alojamientoId) {
        return comentarioDAO.countByAlojamiento(alojamientoId);
    }

    @Override
    public java.util.List<ComentarioDTO> mejoresComentarios(Integer alojamientoId) {
        return comentarioDAO.findMejoresComentarios(alojamientoId);
    }

    // =====================================
    // Mutaciones: actualizar / eliminar
    // =====================================
    @Override
    @Transactional
    public ComentarioDTO actualizar(Integer usuarioId, Integer comentarioId, ActualizarComentarioRequest request) {
        Comentario comentario = comentarioDAO.findEntityById(comentarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comentario no encontrado"));

        // Solo el autor puede modificar
        if (!Objects.equals(comentario.getAutor().getId(), usuarioId)) {
            throw new AccesoNoAutorizadoException("No estás autorizado a modificar este comentario");
        }

        // Validaciones de negocio (RN7/RN8)
        if (request.getCalificacion() != null) {
            int c = request.getCalificacion();
            if (c < 1 || c > 5) {
                throw new ReglaNegocioException("La calificación debe estar entre 1 y 5");
            }
            comentario.setCalificacion(c);
        }
        if (request.getComentarioTexto() != null && request.getComentarioTexto().length() > 500) {
            throw new ReglaNegocioException("El comentario no puede exceder 500 caracteres");
        }
        if (request.getComentarioTexto() != null) {
            comentario.setComentarioTexto(request.getComentarioTexto());
        }

        // Opcional: podrías actualizar una marca de fecha de edición
        // comentario.setFechaComentario(LocalDateTime.now());

        Comentario actualizado = comentarioRepository.save(comentario);
        return comentarioMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Integer usuarioId, Integer comentarioId, boolean adminOverride) {
        Comentario comentario = comentarioDAO.findEntityById(comentarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comentario no encontrado"));

        boolean esAutor = Objects.equals(comentario.getAutor().getId(), usuarioId);
        if (!esAutor && !adminOverride) {
            throw new AccesoNoAutorizadoException("No estás autorizado a eliminar este comentario");
        }

        comentarioRepository.delete(comentario);
        log.info("Comentario {} eliminado por usuario {} (adminOverride={})", comentarioId, usuarioId, adminOverride);
    }

    // =====================================
    // Reportar
    // =====================================
    @Override
    @Transactional
    public void reportar(Integer usuarioId, Integer comentarioId, ReportarComentarioRequest request) {
        Comentario comentario = comentarioDAO.findEntityById(comentarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comentario no encontrado"));
        log.warn("TODO: Persistir reporte de comentario. usuarioId={}, comentarioId={}, motivo='{}'",
                usuarioId, comentarioId, request.getMotivo());

    }
}
