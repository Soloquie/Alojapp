package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.RespuestaComentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones con respuestas a comentarios
 * Implementa RN19: Solo el anfitrión propietario puede responder
 */
@Repository
public interface RespuestaComentarioRepository extends JpaRepository<RespuestaComentario, Long> {

    /**
     * Buscar respuestas por comentario
     */
    List<RespuestaComentario> findByComentarioId(Long comentarioId);

    /**
     * Buscar respuestas por anfitrión
     */
    List<RespuestaComentario> findByAnfitrionId(Long anfitrionId);

    /**
     * Verificar si un comentario ya tiene respuesta del anfitrión
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RespuestaComentario r " +
            "WHERE r.comentario.id = :comentarioId " +
            "AND r.anfitrion.id = :anfitrionId")
    boolean existeRespuestaAnfitrion(
            @Param("comentarioId") Long comentarioId,
            @Param("anfitrionId") Long anfitrionId
    );

    /**
     * Contar respuestas de un anfitrión
     */
    Long countByAnfitrionId(Long anfitrionId);
}