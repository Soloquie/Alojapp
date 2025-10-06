package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones con administradores
 */
@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    /**
     * Buscar administrador por ID de usuario
     */
    Optional<Administrador> findByUsuarioId(Long usuarioId);

    /**
     * Verificar si un usuario ya es administrador
     */
    boolean existsByUsuarioId(Long usuarioId);

    /**
     * Buscar administradores por nivel de acceso
     */
    List<Administrador> findByNivelAcceso(String nivelAcceso);

    /**
     * Buscar administradores SUPER_ADMIN
     */
    @Query("SELECT a FROM Administrador a WHERE a.nivelAcceso = 'SUPER_ADMIN'")
    List<Administrador> findSuperAdministradores();

    /**
     * Contar administradores por nivel
     */
    Long countByNivelAcceso(String nivelAcceso);
}