package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoAlojamiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para operaciones con alojamientos
 * Implementa RN23, RN24, RN25 (búsqueda y filtrado)
 */
@Repository
public interface AlojamientoRepository extends JpaRepository<Alojamiento, Long> {

    /**
     * Buscar alojamientos por estado
     * RN23: Los eliminados no deben aparecer en búsquedas
     */
    List<Alojamiento> findByEstado(EstadoAlojamiento estado);

    /**
     * Buscar alojamientos activos (para listado público)
     */
    Page<Alojamiento> findByEstado(EstadoAlojamiento estado, Pageable pageable);

    /**
     * Buscar alojamientos por ciudad
     */
    Page<Alojamiento> findByCiudadContainingIgnoreCaseAndEstado(
            String ciudad,
            EstadoAlojamiento estado,
            Pageable pageable
    );

    /**
     * Buscar por anfitrión
     */
    List<Alojamiento> findByAnfitrionId(Long anfitrionId);

    Page<Alojamiento> findByAnfitrionId(Long anfitrionId, Pageable pageable);

    /**
     * Buscar por rango de precio
     * RN4: El precio debe ser mayor a 0
     */
    @Query("SELECT a FROM Alojamiento a WHERE a.precioNoche BETWEEN :min AND :max AND a.estado = :estado")
    Page<Alojamiento> findByPrecioNocheRange(
            @Param("min") BigDecimal min,
            @Param("max") BigDecimal max,
            @Param("estado") EstadoAlojamiento estado,
            Pageable pageable
    );

    /**
     * Buscar por capacidad mínima
     * RN5: La capacidad debe ser mayor a 0
     */
    @Query("SELECT a FROM Alojamiento a WHERE a.capacidadMaxima >= :capacidad AND a.estado = :estado")
    Page<Alojamiento> findByCapacidadMinima(
            @Param("capacidad") Integer capacidad,
            @Param("estado") EstadoAlojamiento estado,
            Pageable pageable
    );

    /**
     * Búsqueda completa con filtros (RN24, RN25)
     * Busca alojamientos disponibles en fechas específicas
     */
    @Query("SELECT DISTINCT a FROM Alojamiento a " +
            "LEFT JOIN a.servicios s " +
            "WHERE a.estado = 'ACTIVO' " +
            "AND (:ciudad IS NULL OR LOWER(a.ciudad) LIKE LOWER(CONCAT('%', :ciudad, '%'))) " +
            "AND (:precioMin IS NULL OR a.precioNoche >= :precioMin) " +
            "AND (:precioMax IS NULL OR a.precioNoche <= :precioMax) " +
            "AND (:capacidad IS NULL OR a.capacidadMaxima >= :capacidad) " +
            "AND (:serviciosIds IS NULL OR s.id IN :serviciosIds) " +
            "AND a.id NOT IN (" +
            "    SELECT r.alojamiento.id FROM Reserva r " +
            "    WHERE r.estado IN ('CONFIRMADA', 'PENDIENTE') " +
            "    AND ((:fechaCheckin BETWEEN r.fechaCheckin AND r.fechaCheckout) " +
            "         OR (:fechaCheckout BETWEEN r.fechaCheckin AND r.fechaCheckout) " +
            "         OR (r.fechaCheckin BETWEEN :fechaCheckin AND :fechaCheckout))" +
            ")")
    Page<Alojamiento> buscarConFiltros(
            @Param("ciudad") String ciudad,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            @Param("capacidad") Integer capacidad,
            @Param("serviciosIds") List<Long> serviciosIds,
            @Param("fechaCheckin") LocalDate fechaCheckin,
            @Param("fechaCheckout") LocalDate fechaCheckout,
            Pageable pageable
    );

    /**
     * Verificar disponibilidad de un alojamiento en fechas específicas
     * RN14: No pueden existir reservas solapadas
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN false ELSE true END FROM Reserva r " +
            "WHERE r.alojamiento.id = :alojamientoId " +
            "AND r.estado IN ('CONFIRMADA', 'PENDIENTE') " +
            "AND ((:fechaCheckin BETWEEN r.fechaCheckin AND r.fechaCheckout) " +
            "     OR (:fechaCheckout BETWEEN r.fechaCheckin AND r.fechaCheckout) " +
            "     OR (r.fechaCheckin BETWEEN :fechaCheckin AND :fechaCheckout))")
    boolean estaDisponible(
            @Param("alojamientoId") Long alojamientoId,
            @Param("fechaCheckin") LocalDate fechaCheckin,
            @Param("fechaCheckout") LocalDate fechaCheckout
    );

    /**
     * Buscar alojamientos con calificación promedio mayor a X
     */
    @Query("SELECT a FROM Alojamiento a " +
            "JOIN a.comentarios c " +
            "WHERE a.estado = 'ACTIVO' " +
            "GROUP BY a.id " +
            "HAVING AVG(c.calificacion) >= :calificacionMinima")
    List<Alojamiento> findByCalificacionPromedioGreaterThan(@Param("calificacionMinima") Double calificacionMinima);

    /**
     * Obtener alojamientos más populares (más reservas)
     */
    @Query("SELECT a FROM Alojamiento a " +
            "LEFT JOIN a.reservas r " +
            "WHERE a.estado = 'ACTIVO' " +
            "GROUP BY a.id " +
            "ORDER BY COUNT(r) DESC")
    Page<Alojamiento> findMasPopulares(Pageable pageable);

    /**
     * Contar alojamientos por ciudad
     */
    @Query("SELECT a.ciudad, COUNT(a) FROM Alojamiento a " +
            "WHERE a.estado = 'ACTIVO' " +
            "GROUP BY a.ciudad " +
            "ORDER BY COUNT(a) DESC")
    List<Object[]> countByCiudad();

    /**
     * Verificar si un alojamiento tiene reservas futuras
     * RN21: Solo se pueden eliminar sin reservas futuras
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r " +
            "WHERE r.alojamiento.id = :alojamientoId " +
            "AND r.fechaCheckin > CURRENT_DATE " +
            "AND r.estado IN ('CONFIRMADA', 'PENDIENTE')")
    boolean tieneReservasFuturas(@Param("alojamientoId") Long alojamientoId);
}