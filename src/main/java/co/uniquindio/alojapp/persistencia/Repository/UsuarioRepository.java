package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones de base de datos con usuarios
 * Soporta las funcionalidades de huéspedes, anfitriones y administradores
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Buscar usuario por email (para login)
     * RN1: El email debe ser único
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Buscar usuario por email (ignorando mayúsculas/minúsculas)
     */
    Optional<Usuario> findByEmailIgnoreCase(String email);

    /**
     * Verificar si existe un usuario con ese email
     * Usado para validar RN1 antes de crear/actualizar
     */
    boolean existsByEmail(String email);

    /**
     * Verificar si existe email excluyendo un ID específico
     * Útil para validar updates sin contar el propio usuario
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u " +
            "WHERE u.email = :email AND u.id != :userId")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("userId") Long userId);

    /**
     * Buscar usuarios por estado
     */
    List<Usuario> findByEstado(EstadoUsuario estado);

    /**
     * Buscar usuarios por nombre (contiene texto)
     */
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Buscar usuarios registrados después de una fecha
     */
    List<Usuario> findByFechaRegistroAfter(LocalDateTime fecha);

    /**
     * Buscar usuarios que son anfitriones
     */
    @Query("SELECT u FROM Usuario u WHERE u.perfilAnfitrion IS NOT NULL")
    List<Usuario> findUsuariosConPerfilAnfitrion();

    /**
     * Buscar usuarios que son administradores
     */
    @Query("SELECT u FROM Usuario u WHERE u.perfilAdministrador IS NOT NULL")
    List<Usuario> findUsuariosConPerfilAdministrador();

    /**
     * Buscar usuarios huéspedes (sin perfil de anfitrión ni admin)
     */
    @Query("SELECT u FROM Usuario u WHERE u.perfilAnfitrion IS NULL AND u.perfilAdministrador IS NULL")
    List<Usuario> findUsuariosHuespedes();

    /**
     * Contar usuarios por estado
     */
    Long countByEstado(EstadoUsuario estado);

    /**
     * Buscar usuarios por teléfono
     */
    Optional<Usuario> findByTelefono(String telefono);

    /**
     * Actualizar fecha de última conexión
     */
    @Query("UPDATE Usuario u SET u.fechaUltimaConexion = :fecha WHERE u.id = :userId")
    void actualizarFechaUltimaConexion(@Param("userId") Long userId, @Param("fecha") LocalDateTime fecha);

    /**
     * Buscar usuarios inactivos (sin conexión en X días)
     */
    @Query("SELECT u FROM Usuario u WHERE u.fechaUltimaConexion < :fecha")
    List<Usuario> findUsuariosInactivos(@Param("fecha") LocalDateTime fecha);
}