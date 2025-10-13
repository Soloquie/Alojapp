package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.RespuestaComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ResponderComentarioRequest;
import co.uniquindio.alojapp.negocio.Service.RespuestaComentarioService;
import co.uniquindio.alojapp.negocio.excepciones.ConflictoNegocioException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.RespuestaComentarioDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RespuestaComentarioServiceImpl implements RespuestaComentarioService {

    private final RespuestaComentarioDAO respuestaDAO;

    /**
     * RN19: Solo el anfitrión propietario puede responder.
     * (La validación de propiedad la hace el DAO al construir la entidad)
     * Además, evitamos duplicados del mismo anfitrión en el mismo comentario.
     */
    @Override
    @Transactional
    public RespuestaComentarioDTO responder(Integer comentarioId,
                                            ResponderComentarioRequest request,
                                            Integer anfitrionId) {

        // Regla adicional: no permitir más de una respuesta del mismo anfitrión
        if (respuestaDAO.existeRespuestaAnfitrion(comentarioId, anfitrionId)) {
            throw new ConflictoNegocioException(
                    "El anfitrión ya respondió este comentario");
        }

        // Delegamos en el DAO la creación + validación de propiedad (RN19)
        return respuestaDAO.save(comentarioId, request, anfitrionId);
    }

    @Override
    public RespuestaComentarioDTO obtenerPorId(Integer respuestaId) {
        return respuestaDAO.findById(respuestaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Respuesta no encontrada"));
    }

    @Override
    public List<RespuestaComentarioDTO> listarPorComentario(Integer comentarioId) {
        return respuestaDAO.findByComentario(comentarioId);
    }

    @Override
    public List<RespuestaComentarioDTO> listarPorAnfitrion(Integer anfitrionId) {
        return respuestaDAO.findByAnfitrion(anfitrionId);
    }

    @Override
    public Long contarPorAnfitrion(Integer anfitrionId) {
        return respuestaDAO.countByAnfitrion(anfitrionId);
    }
}
