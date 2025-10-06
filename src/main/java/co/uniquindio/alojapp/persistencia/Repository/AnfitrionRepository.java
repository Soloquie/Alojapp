package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones con anfitriones
 */
@Repository
public interface AnfitrionRepository extends JpaRepository<Anfitrion, Long> {

    /**
     * Buscar anfitrión por ID de usuario
     */
    Optional<Anfitrion> findByUsuarioId(Long usuarioId);

    /**
     * Verificar si un usuario ya es anfitrión
     */
    boolean existsByUsuarioId(Long usuarioId);

    /**
     * Buscar anfitriones verificados
     */
    List<Anfitrion> findByVerificado(Boolean verificado);

    /**
     * Buscar anfitriones con al menos un alojamiento
     */
    @Query("SELECT a FROM Anfitrion a WHERE SIZE(a.alojamientos) > 0")
    List<Anfitrion> findAnfitrionesConAlojamientos();

    /**
     * Buscar anfitriones sin alojamientos
     */
    @Query("SELECT a FROM Anfitrion a WHERE SIZE(a.alojamientos) = 0")
    List<Anfitrion> findAnfitrioonesSinAlojamientos();

    /**
     * Contar alojamientos por anfitrión
     */
    @Query("SELECT COUNT(al) FROM Alojamiento al WHERE al.anfitrion.id = :anfitrionId")
    Long countAlojamientosByAnfitrionId(@Param("anfitrionId") Long anfitrionId);

    /**
     * Obtener anfitriones con mejor calificación promedio
     * Calcula el promedio de calificaciones de todos sus alojamientos
     */
    @Query("SELECT a FROM Anfitrion a " +
            "JOIN a.alojamientos al " +
            "JOIN al.comentarios c " +
            "GROUP BY a.id " +
            "HAVING AVG(c.calificacion) >= :calificacionMinima " +
            "ORDER BY AVG(c.calificacion) DESC")
    List<Anfitrion> findAnfitrionesConMejorCalificacion(@Param("calificacionMinima") Double calificacionMinima);

    /**
     * Contar reservas totales de un anfitrión (en todos sus alojamientos)
     */
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.alojamiento.anfitrion.id = :anfitrionId")
    Long countReservasByAnfitrionId(@Param("anfitrionId") Long anfitrionId);

    /**
     * Buscar anfitriones por ciudad de sus alojamientos
     */
    @Query("SELECT DISTINCT a FROM Anfitrion a " +
            "JOIN a.alojamientos al " +
            "WHERE al.ciudad = :ciudad")
    List<Anfitrion> findByCiudadAlojamientos(@Param("ciudad") String ciudad);
}