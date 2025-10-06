package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.AdministradorDTO;
import co.uniquindio.alojapp.persistencia.Entity.Administrador;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.AdministradorMapper;
import co.uniquindio.alojapp.persistencia.Repository.AdministradorRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operaciones de persistencia de administradores
 */
@Repository
@RequiredArgsConstructor
public class AdministradorDAO {

    private final AdministradorRepository administradorRepository;
    private final UsuarioRepository usuarioRepository;
    private final AdministradorMapper administradorMapper;

    /**
     * Crear nuevo administrador
     */
    public AdministradorDTO save(Long usuarioId, String nivelAcceso, String permisos) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Administrador admin = Administrador.builder()
                .usuario(usuario)
                .nivelAcceso(nivelAcceso)
                .permisos(permisos)
                .fechaAsignacion(LocalDateTime.now())
                .build();

        Administrador saved = administradorRepository.save(admin);
        return administradorMapper.toDTO(saved);
    }

    /**
     * Buscar administrador por ID
     */
    public Optional<AdministradorDTO> findById(Long id) {
        return administradorRepository.findById(id)
                .map(administradorMapper::toDTO);
    }

    /**
     * Buscar entity por ID
     */
    public Optional<Administrador> findEntityById(Long id) {
        return administradorRepository.findById(id);
    }

    /**
     * Buscar administrador por usuario ID
     */
    public Optional<AdministradorDTO> findByUsuarioId(Long usuarioId) {
        return administradorRepository.findByUsuarioId(usuarioId)
                .map(administradorMapper::toDTO);
    }

    /**
     * Verificar si usuario es administrador
     */
    public boolean existsByUsuarioId(Long usuarioId) {
        return administradorRepository.existsByUsuarioId(usuarioId);
    }

    /**
     * Buscar todos los administradores
     */
    public List<AdministradorDTO> findAll() {
        return administradorMapper.toDTOList(administradorRepository.findAll());
    }

    /**
     * Buscar por nivel de acceso
     */
    public List<AdministradorDTO> findByNivelAcceso(String nivelAcceso) {
        return administradorMapper.toDTOList(
                administradorRepository.findByNivelAcceso(nivelAcceso)
        );
    }

    /**
     * Buscar super administradores
     */
    public List<AdministradorDTO> findSuperAdmins() {
        return administradorMapper.toDTOList(
                administradorRepository.findSuperAdministradores()
        );
    }

    /**
     * Actualizar nivel de acceso
     */
    public Optional<AdministradorDTO> actualizarNivelAcceso(Long id, String nuevoNivel) {
        return administradorRepository.findById(id)
                .map(admin -> {
                    admin.setNivelAcceso(nuevoNivel);
                    Administrador updated = administradorRepository.save(admin);
                    return administradorMapper.toDTO(updated);
                });
    }

    /**
     * Actualizar permisos
     */
    public Optional<AdministradorDTO> actualizarPermisos(Long id, String permisos) {
        return administradorRepository.findById(id)
                .map(admin -> {
                    admin.setPermisos(permisos);
                    Administrador updated = administradorRepository.save(admin);
                    return administradorMapper.toDTO(updated);
                });
    }

    /**
     * Eliminar administrador
     */
    public boolean deleteById(Long id) {
        if (administradorRepository.existsById(id)) {
            administradorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Contar administradores por nivel
     */
    public Long countByNivel(String nivelAcceso) {
        return administradorRepository.countByNivelAcceso(nivelAcceso);
    }
}