package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para operaciones con reservas
 * Implementa reglas de negocio RN9-RN15, RN30, RN32
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    /**
     * Buscar reservas por huésped
     */
    List<Reserva> findByHuespedId(Integer huespedId);

    Page<Reserva> findByHuespedId(Integer huespedId, Pageable pageable);

    /**
     * Buscar reservas por huésped y estado
     */
    List<Reserva> findByHuespedIdAndEstado(Integer huespedId, EstadoReserva estado);

    Page<Reserva> findByHuespedIdAndEstado(Integer huespedId, EstadoReserva estado, Pageable pageable);

    /**
     * Buscar reservas por alojamiento
     */
    List<Reserva> findByAlojamientoId(Integer alojamientoId);

    Page<Reserva> findByAlojamientoId(Integer alojamientoId, Pageable pageable);

    /**
     * Buscar reservas por alojamiento y estado
     */
    List<Reserva> findByAlojamientoIdAndEstado(Integer alojamientoId, EstadoReserva estado);

    /**
     * Buscar reservas por anfitrión (a través de alojamiento)
     */
    @Query("SELECT r FROM Reserva r WHERE r.alojamiento.anfitrion.id = :anfitrionId")
    List<Reserva> findByAnfitrionId(@Param("anfitrionId") Integer anfitrionId);

    @Query("SELECT r FROM Reserva r WHERE r.alojamiento.anfitrion.id = :anfitrionId")
    Page<Reserva> findByAnfitrionId(@Param("anfitrionId") Integer anfitrionId, Pageable pageable);

    /**
     * Buscar reservas por anfitrión y estado
     */
    @Query("SELECT r FROM Reserva r " +
            "WHERE r.alojamiento.anfitrion.id = :anfitrionId " +
            "AND r.estado = :estado")
    List<Reserva> findByAnfitrionIdAndEstado(
            @Param("anfitrionId") Integer anfitrionId,
            @Param("estado") EstadoReserva estado
    );

    /**
     * Buscar reservas por rango de fechas
     */
    @Query("SELECT r FROM Reserva r " +
            "WHERE r.fechaCheckin BETWEEN :fechaInicio AND :fechaFin")
    List<Reserva> findByFechaCheckinBetween(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    /**
     * Buscar reservas activas (confirmadas con fecha futura)
     */
    @Query("SELECT r FROM Reserva r " +
            "WHERE r.estado = 'CONFIRMADA' " +
            "AND r.fechaCheckin >= CURRENT_DATE")
    List<Reserva> findReservasActivas();

    /**
     * Buscar reservas completadas pendientes de comentario
     * RN16: Solo reservas completadas pueden comentarse
     */
    @Query("SELECT r FROM Reserva r " +
            "WHERE r.huesped.id = :huespedId " +
            "AND r.estado = 'COMPLETADA' " +
            "AND SIZE(r.comentarios) = 0")
    List<Reserva> findReservasCompletadasSinComentario(@Param("huespedId") Integer huespedId);

    /**
     * Verificar solapamiento de reservas
     * RN14: No pueden existir reservas solapadas
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r " +
            "WHERE r.alojamiento.id = :alojamientoId " +
            "AND r.estado IN ('CONFIRMADA', 'PENDIENTE') " +
            "AND ((:fechaCheckin BETWEEN r.fechaCheckin AND r.fechaCheckout) " +
            "     OR (:fechaCheckout BETWEEN r.fechaCheckin AND r.fechaCheckout) " +
            "     OR (r.fechaCheckin BETWEEN :fechaCheckin AND :fechaCheckout))")
    boolean existeSolapamiento(
            @Param("alojamientoId") Integer alojamientoId,
            @Param("fechaCheckin") LocalDate fechaCheckin,
            @Param("fechaCheckout") LocalDate fechaCheckout
    );

    /**
     * Buscar reservas que necesitan ser marcadas como completadas
     * RN32: Cambio automático a completada después del checkout
     */
    @Query("SELECT r FROM Reserva r " +
            "WHERE r.estado = 'CONFIRMADA' " +
            "AND r.fechaCheckout < CURRENT_DATE")
    List<Reserva> findReservasParaCompletar();

    /**
     * Contar reservas por estado
     */
    Long countByEstado(EstadoReserva estado);

    /**
     * Contar reservas de un huésped por estado
     */
    Long countByHuespedIdAndEstado(Integer huespedId, EstadoReserva estado);

    /**
     * Calcular ingresos totales de un anfitrión
     */
    @Query("SELECT SUM(r.precioTotal) FROM Reserva r " +
            "WHERE r.alojamiento.anfitrion.id = :anfitrionId " +
            "AND r.estado = 'COMPLETADA'")
    BigDecimal calcularIngresosTotales(@Param("anfitrionId") Integer anfitrionId);

    /**
     * Calcular ingresos en un período
     */
    @Query("SELECT SUM(r.precioTotal) FROM Reserva r " +
            "WHERE r.alojamiento.anfitrion.id = :anfitrionId " +
            "AND r.estado = 'COMPLETADA' " +
            "AND r.fechaCheckout BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal calcularIngresosPorPeriodo(
            @Param("anfitrionId") Integer anfitrionId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );
}