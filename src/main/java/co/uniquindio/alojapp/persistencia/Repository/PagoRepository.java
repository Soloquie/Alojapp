package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones con pagos
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    /**
     * Buscar pago por reserva
     */
    Optional<Pago> findByReservaId(Integer reservaId);

    /**
     * Buscar pagos por usuario
     */
    List<Pago> findByUsuarioIdOrderByFechaPagoDesc(Integer usuarioId);

    /**
     * Buscar pagos por estado
     */
    List<Pago> findByEstado(String estado);

    /**
     * Buscar pagos por método de pago
     */
    List<Pago> findByMetodoPago(String metodoPago);

    /**
     * Buscar pagos en rango de fechas
     */
    List<Pago> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Calcular monto total de pagos por usuario
     */
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.usuario.id = :usuarioId AND p.estado = 'APROBADO'")
    BigDecimal calcularTotalPagadoPorUsuario(@Param("usuarioId") Integer usuarioId);

    /**
     * Contar pagos por estado
     */
    Long countByEstado(String estado);

    /**
     * Buscar pagos aprobados de un anfitrión
     */
    @Query("SELECT p FROM Pago p " +
            "WHERE p.reserva.alojamiento.anfitrion.id = :anfitrionId " +
            "AND p.estado = 'APROBADO'")
    List<Pago> findPagosAprobadosByAnfitrion(@Param("anfitrionId") Integer anfitrionId);
}