package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.CodigoRecuperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones con códigos de recuperación de contraseña
 * Implementa RN13: Los códigos expiran en 15 minutos
 */
@Repository
public interface CodigoRecuperacionRepository extends JpaRepository<CodigoRecuperacion, Integer> {

    /**
     * Buscar código por usuario y código
     */
    Optional<CodigoRecuperacion> findByUsuarioIdAndCodigo(Integer usuarioId, String codigo);

    /**
     * Buscar código válido (no usado y no expirado)
     */
    @Query("SELECT c FROM CodigoRecuperacion c " +
            "WHERE c.usuario.id = :usuarioId " +
            "AND c.codigo = :codigo " +
            "AND c.usado = false " +
            "AND c.fechaExpiracion > :now")
    Optional<CodigoRecuperacion> findCodigoValido(
            @Param("usuarioId") Integer usuarioId,
            @Param("codigo") String codigo,
            @Param("now") LocalDateTime now
    );

    /**
     * Buscar códigos por usuario
     */
    List<CodigoRecuperacion> findByUsuarioIdOrderByFechaExpiracionDesc(Integer usuarioId);

    /**
     * Buscar códigos no usados de un usuario
     */
    List<CodigoRecuperacion> findByUsuarioIdAndUsado(Integer usuarioId, Boolean usado);

    /**
     * Eliminar códigos expirados
     */
    @Query("DELETE FROM CodigoRecuperacion c WHERE c.fechaExpiracion < :fecha")
    void eliminarCodigosExpirados(@Param("fecha") LocalDateTime fecha);

    /**
     * Contar códigos válidos de un usuario
     */
    @Query("SELECT COUNT(c) FROM CodigoRecuperacion c " +
            "WHERE c.usuario.id = :usuarioId " +
            "AND c.usado = false " +
            "AND c.fechaExpiracion > :now")
    Long countCodigosValidosByUsuario(@Param("usuarioId") Integer usuarioId, @Param("now") LocalDateTime now);
}