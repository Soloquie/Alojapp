package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones con notificaciones
 * Implementa RN26-RN29: Sistema de notificaciones
 */
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    /**
     * Buscar notificaciones por usuario
     */
    List<Notificacion> findByUsuarioIdOrderByFechaEnvioDesc(Long usuarioId);

    /**
     * Buscar notificaciones no leídas por usuario
     */
    List<Notificacion> findByUsuarioIdAndLeidaOrderByFechaEnvioDesc(Long usuarioId, Boolean leida);

    /**
     * Contar notificaciones no leídas
     */
    Long countByUsuarioIdAndLeida(Long usuarioId, Boolean leida);

    /**
     * Marcar notificación como leída
     */
    @Modifying
    @Query("UPDATE Notificacion n SET n.leida = true WHERE n.id = :notificacionId")
    void marcarComoLeida(@Param("notificacionId") Long notificacionId);

    /**
     * Marcar todas las notificaciones de un usuario como leídas
     */
    @Modifying
    @Query("UPDATE Notificacion n SET n.leida = true WHERE n.usuario.id = :usuarioId AND n.leida = false")
    void marcarTodasComoLeidas(@Param("usuarioId") Long usuarioId);

    /**
     * Eliminar notificaciones antiguas leídas
     */
    @Query("DELETE FROM Notificacion n WHERE n.leida = true " +
            "AND n.fechaEnvio < :fecha")
    void eliminarNotificacionesAntiguas(@Param("fecha") java.time.LocalDateTime fecha);
}