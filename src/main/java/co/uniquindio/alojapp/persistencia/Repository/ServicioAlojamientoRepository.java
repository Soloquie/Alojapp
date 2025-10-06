package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.ServicioAlojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones con servicios de alojamiento
 */
@Repository
public interface ServicioAlojamientoRepository extends JpaRepository<ServicioAlojamiento, Long> {

    /**
     * Buscar servicio por nombre
     */
    Optional<ServicioAlojamiento> findByNombre(String nombre);

    /**
     * Buscar servicios por nombre (contiene)
     */
    List<ServicioAlojamiento> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Verificar si existe un servicio con ese nombre
     */
    boolean existsByNombre(String nombre);

    /**
     * Buscar servicios más utilizados
     */
    @Query("SELECT s, COUNT(a) as total FROM ServicioAlojamiento s " +
            "JOIN s.alojamientos a " +
            "GROUP BY s.id " +
            "ORDER BY total DESC")
    List<Object[]> findServiciosMasUtilizados();

    /**
     * Contar alojamientos que usan un servicio
     */
    @Query("SELECT COUNT(a) FROM Alojamiento a JOIN a.servicios s WHERE s.id = :servicioId")
    Long countAlojamientosByServicioId(@Param("servicioId") Long servicioId);
}