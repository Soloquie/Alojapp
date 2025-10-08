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
public interface AnfitrionRepository extends JpaRepository<Anfitrion, Integer> {

    Optional<Anfitrion> findByUsuarioId(Integer usuarioId);

    boolean existsByUsuarioId(Integer usuarioId);

    List<Anfitrion> findByVerificado(Boolean verificado);

    @Query("SELECT a FROM Anfitrion a WHERE SIZE(a.alojamientos) > 0")
    List<Anfitrion> findAnfitrionesConAlojamientos();

    // (typo corregido en el nombre)
    @Query("SELECT a FROM Anfitrion a WHERE SIZE(a.alojamientos) = 0")
    List<Anfitrion> findAnfitrionesSinAlojamientos();

    @Query("SELECT COUNT(al) FROM Alojamiento al WHERE al.anfitrion.id = :anfitrionId")
    Long countAlojamientosByAnfitrionId(@Param("anfitrionId") Integer anfitrionId);

    @Query("""
           SELECT a FROM Anfitrion a
           JOIN a.alojamientos al
           JOIN al.comentarios c
           GROUP BY a.id
           HAVING AVG(c.calificacion) >= :calificacionMinima
           ORDER BY AVG(c.calificacion) DESC
           """)
    List<Anfitrion> findAnfitrionesConMejorCalificacion(@Param("calificacionMinima") Double calificacionMinima);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.alojamiento.anfitrion.id = :anfitrionId")
    Long countReservasByAnfitrionId(@Param("anfitrionId") Integer anfitrionId);

    @Query("""
           SELECT DISTINCT a FROM Anfitrion a
           JOIN a.alojamientos al
           WHERE al.ciudad = :ciudad
           """)
    List<Anfitrion> findByCiudadAlojamientos(@Param("ciudad") String ciudad);

    // ✅ correcto para Usuario.id
    @org.springframework.transaction.annotation.Transactional
    long deleteByUsuarioId(Integer usuarioId);
}
