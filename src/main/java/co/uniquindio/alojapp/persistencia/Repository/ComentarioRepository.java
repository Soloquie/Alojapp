package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones con comentarios
 * Implementa RN7, RN8, RN16, RN17
 */
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    /**
     * Buscar comentarios por alojamiento
     */
    List<Comentario> findByAlojamientoId(Long alojamientoId);

    Page<Comentario> findByAlojamientoIdOrderByFechaComentarioDesc(Long alojamientoId, Pageable pageable);

    /**
     * Buscar comentarios por usuario
     */
    List<Comentario> findByAutorId(Long autorId);

    /**
     * Buscar comentario por reserva
     * RN17: Solo un comentario por reserva
     */
    Optional<Comentario> findByReservaId(Long reservaId);

    /**
     * Verificar si existe comentario para una reserva
     * RN17: Validación de comentario único
     */
    boolean existsByReservaId(Long reservaId);

    /**
     * Buscar comentarios por anfitrión (todos sus alojamientos)
     */
    @Query("SELECT c FROM Comentario c WHERE c.alojamiento.anfitrion.id = :anfitrionId")
    List<Comentario> findByAnfitrionId(@Param("anfitrionId") Long anfitrionId);

    @Query("SELECT c FROM Comentario c " +
            "WHERE c.alojamiento.anfitrion.id = :anfitrionId " +
            "ORDER BY c.fechaComentario DESC")
    Page<Comentario> findByAnfitrionIdOrderByFechaDesc(
            @Param("anfitrionId") Long anfitrionId,
            Pageable pageable
    );

    /**
     * Buscar comentarios por calificación
     * RN8: Calificación entre 1 y 5
     */
    List<Comentario> findByCalificacion(Integer calificacion);

    /**
     * Buscar comentarios con calificación mayor o igual
     */
    List<Comentario> findByCalificacionGreaterThanEqual(Integer calificacion);

    /**
     * Calcular calificación promedio de un alojamiento
     */
    @Query("SELECT AVG(c.calificacion) FROM Comentario c WHERE c.alojamiento.id = :alojamientoId")
    Double calcularCalificacionPromedio(@Param("alojamientoId") Long alojamientoId);

    /**
     * Contar comentarios por alojamiento
     */
    Long countByAlojamientoId(Long alojamientoId);

    /**
     * Buscar comentarios sin respuesta del anfitrión
     */
    @Query("SELECT c FROM Comentario c WHERE SIZE(c.respuestas) = 0")
    List<Comentario> findComentariosSinRespuesta();

    /**
     * Buscar comentarios sin respuesta de un anfitrión específico
     */
    @Query("SELECT c FROM Comentario c " +
            "WHERE c.alojamiento.anfitrion.id = :anfitrionId " +
            "AND SIZE(c.respuestas) = 0")
    List<Comentario> findComentariosSinRespuestaByAnfitrion(@Param("anfitrionId") Long anfitrionId);

    /**
     * Buscar mejores comentarios (calificación 4-5)
     */
    @Query("SELECT c FROM Comentario c " +
            "WHERE c.alojamiento.id = :alojamientoId " +
            "AND c.calificacion >= 4 " +
            "ORDER BY c.calificacion DESC, c.fechaComentario DESC")
    List<Comentario> findMejoresComentarios(@Param("alojamientoId") Long alojamientoId);
}