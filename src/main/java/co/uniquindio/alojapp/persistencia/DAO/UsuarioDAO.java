package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroUsuarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarPerfilRequest;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import co.uniquindio.alojapp.persistencia.Mapper.UsuarioMapper;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operaciones de persistencia de usuarios
 *
 * RESPONSABILIDADES:
 * - Abstraer Repository y Mapper del Service
 * - Convertir entre Entity y DTO automáticamente
 * - Manejar validaciones de persistencia
 * - Implementar reglas de negocio de datos (RN1, RN2, RN37)
 */
@Repository
@RequiredArgsConstructor
public class UsuarioDAO {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    /**
     * Crear nuevo usuario
     * RN1: Email único
     * RN2: Contraseña con validaciones (se encripta en el Service)
     */
    public UsuarioDTO save(RegistroUsuarioRequest request, String passwordEncriptada) {
        Usuario entity = new Usuario();
        entity.setNombre(request.getNombre());
        entity.setEmail(request.getEmail().toLowerCase()); // Normalizar email
        entity.setContrasenaHash(passwordEncriptada);
        entity.setTelefono(request.getTelefono());
        entity.setFechaNacimiento(request.getFechaNacimiento());
        entity.setFotoPerfilUrl(request.getFotoPerfilUrl());
        entity.setEstado(EstadoUsuario.ACTIVO);
        entity.setFechaRegistro(LocalDateTime.now());

        Usuario savedEntity = usuarioRepository.save(entity);
        return usuarioMapper.toDTO(savedEntity);
    }

    /**
     * Buscar usuario por ID
     */
    public Optional<UsuarioDTO> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO);
    }

    /**
     * Buscar usuario entity por ID (para uso interno)
     */
    public Optional<Usuario> findEntityById(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Buscar usuario por email (para login)
     */
    public Optional<UsuarioDTO> findByEmail(String email) {
        return usuarioRepository.findByEmail(email.toLowerCase())
                .map(usuarioMapper::toDTO);
    }

    /**
     * Buscar usuario entity por email (para autenticación)
     */
    public Optional<Usuario> findEntityByEmail(String email) {
        return usuarioRepository.findByEmail(email.toLowerCase());
    }

    /**
     * Verificar si existe email
     * RN1: Email único
     */
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email.toLowerCase());
    }

    /**
     * Verificar email único excluyendo un usuario
     * Para updates
     */
    public boolean existsByEmailExceptUser(String email, Long userId) {
        return usuarioRepository.existsByEmailAndIdNot(email.toLowerCase(), userId);
    }

    /**
     * Buscar todos los usuarios
     */
    public List<UsuarioDTO> findAll() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    /**
     * Buscar usuarios por estado
     */
    public List<UsuarioDTO> findByEstado(EstadoUsuario estado) {
        return usuarioMapper.toDTOList(usuarioRepository.findByEstado(estado));
    }

    /**
     * Buscar por nombre
     */
    public List<UsuarioDTO> findByNombre(String nombre) {
        return usuarioMapper.toDTOList(
                usuarioRepository.findByNombreContainingIgnoreCase(nombre)
        );
    }

    /**
     * Actualizar perfil de usuario
     */
    public Optional<UsuarioDTO> actualizarPerfil(Long id, ActualizarPerfilRequest request) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (request.getNombre() != null) {
                        usuario.setNombre(request.getNombre());
                    }
                    if (request.getTelefono() != null) {
                        usuario.setTelefono(request.getTelefono());
                    }
                    if (request.getFechaNacimiento() != null) {
                        usuario.setFechaNacimiento(request.getFechaNacimiento());
                    }
                    if (request.getFotoPerfilUrl() != null) {
                        usuario.setFotoPerfilUrl(request.getFotoPerfilUrl());
                    }
                    Usuario updated = usuarioRepository.save(usuario);
                    return usuarioMapper.toDTO(updated);
                });
    }

    /**
     * Actualizar contraseña
     */
    public boolean actualizarPassword(Long id, String nuevaPasswordEncriptada) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setContrasenaHash(nuevaPasswordEncriptada);
                    usuarioRepository.save(usuario);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Actualizar fecha última conexión
     */
    public void actualizarUltimaConexion(Long id) {
        usuarioRepository.findById(id)
                .ifPresent(usuario -> {
                    usuario.setFechaUltimaConexion(LocalDateTime.now());
                    usuarioRepository.save(usuario);
                });
    }

    /**
     * Cambiar estado de usuario
     */
    public Optional<UsuarioDTO> cambiarEstado(Long id, EstadoUsuario nuevoEstado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setEstado(nuevoEstado);
                    Usuario updated = usuarioRepository.save(usuario);
                    return usuarioMapper.toDTO(updated);
                });
    }

    /**
     * Eliminar usuario (soft delete - cambiar a INACTIVO)
     */
    public boolean desactivar(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setEstado(EstadoUsuario.INACTIVO);
                    usuarioRepository.save(usuario);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Contar usuarios por estado
     */
    public Long countByEstado(EstadoUsuario estado) {
        return usuarioRepository.countByEstado(estado);
    }

    /**
     * Contar total de usuarios
     */
    public long count() {
        return usuarioRepository.count();
    }

    /**
     * Verificar si es anfitrión
     */
    public boolean esAnfitrion(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(u -> u.getPerfilAnfitrion() != null)
                .orElse(false);
    }

    /**
     * Verificar si es administrador
     */
    public boolean esAdministrador(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(u -> u.getPerfilAdministrador() != null)
                .orElse(false);
    }
}