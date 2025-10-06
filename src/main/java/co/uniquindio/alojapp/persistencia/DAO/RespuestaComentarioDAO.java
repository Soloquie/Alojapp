package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.RespuestaComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ResponderComentarioRequest;
import co.uniquindio.alojapp.persistencia.Entity.RespuestaComentario;
import co.uniquindio.alojapp.persistencia.Entity.Comentario;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import co.uniquindio.alojapp.persistencia.Mapper.RespuestaComentarioMapper;
import co.uniquindio.alojapp.persistencia.Repository.RespuestaComentarioRepository;
import co.uniquindio.alojapp.persistencia.Repository.ComentarioRepository;
import co.uniquindio.alojapp.persistencia.Repository.AnfitrionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operaciones de persistencia de respuestas a comentarios
 * Implementa RN19: Solo el anfitrión propietario puede responder
 */
@Repository
@RequiredArgsConstructor
public class RespuestaComentarioDAO {

    private final RespuestaComentarioRepository respuestaRepository;
    private final ComentarioRepository comentarioRepository;
    private final AnfitrionRepository anfitrionRepository;
    private final RespuestaComentarioMapper respuestaMapper;

    /**
     * Crear respuesta a un comentario
     * RN19: Solo el anfitrión propietario puede responder
     */
    public RespuestaComentarioDTO save(Long comentarioId, ResponderComentarioRequest request, Long anfitrionId) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        Anfitrion anfitrion = anfitrionRepository.findById(anfitrionId)
                .orElseThrow(() -> new RuntimeException("Anfitrión no encontrado"));

        // RN19: Validar que el anfitrión sea propietario del alojamiento comentado
        if (!comentario.getAlojamiento().getAnfitrion().getId().equals(anfitrionId)) {
            throw new RuntimeException("Solo el anfitrión propietario puede responder comentarios");
        }

        RespuestaComentario respuesta = RespuestaComentario.builder()
                .comentario(comentario)
                .anfitrion(anfitrion)
                .respuestaTexto(request.getRespuestaTexto())
                .fechaRespuesta(LocalDateTime.now())
                .build();

        RespuestaComentario saved = respuestaRepository.save(respuesta);
        return respuestaMapper.toDTO(saved);
    }

    /**
     * Buscar respuesta por ID
     */
    public Optional<RespuestaComentarioDTO> findById(Long id) {
        return respuestaRepository.findById(id)
                .map(respuestaMapper::toDTO);
    }

    /**
     * Buscar respuestas por comentario
     */
    public List<RespuestaComentarioDTO> findByComentario(Long comentarioId) {
        return respuestaMapper.toDTOList(
                respuestaRepository.findByComentarioId(comentarioId)
        );
    }

    /**
     * Buscar respuestas por anfitrión
     */
    public List<RespuestaComentarioDTO> findByAnfitrion(Long anfitrionId) {
        return respuestaMapper.toDTOList(
                respuestaRepository.findByAnfitrionId(anfitrionId)
        );
    }

    /**
     * Verificar si ya existe respuesta del anfitrión
     */
    public boolean existeRespuestaAnfitrion(Long comentarioId, Long anfitrionId) {
        return respuestaRepository.existeRespuestaAnfitrion(comentarioId, anfitrionId);
    }

    /**
     * Contar respuestas de un anfitrión
     */
    public Long countByAnfitrion(Long anfitrionId) {
        return respuestaRepository.countByAnfitrionId(anfitrionId);
    }
}