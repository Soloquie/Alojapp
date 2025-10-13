package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.RespuestaComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ResponderComentarioRequest;

import java.util.List;

public interface RespuestaComentarioService {

    /**
     * Crea la respuesta del anfitrión al comentario indicado (RN19).
     * @param comentarioId ID del comentario a responder
     * @param request cuerpo con el texto de la respuesta (validado en controller)
     * @param anfitrionId ID del anfitrión que responde (propietario)
     * @return Respuesta creada
     */
    RespuestaComentarioDTO responder(Integer comentarioId,
                                     ResponderComentarioRequest request,
                                     Integer anfitrionId);

    /**
     * Obtiene una respuesta por su ID.
     */
    RespuestaComentarioDTO obtenerPorId(Integer respuestaId);

    /**
     * Lista las respuestas de un comentario.
     */
    List<RespuestaComentarioDTO> listarPorComentario(Integer comentarioId);

    /**
     * Lista las respuestas realizadas por un anfitrión.
     */
    List<RespuestaComentarioDTO> listarPorAnfitrion(Integer anfitrionId);

    /**
     * Cuenta las respuestas realizadas por un anfitrión.
     */
    Long contarPorAnfitrion(Integer anfitrionId);
}
