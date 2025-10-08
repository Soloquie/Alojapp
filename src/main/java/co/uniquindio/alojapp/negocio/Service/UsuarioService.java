package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarPerfilRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistrarPerfilAnfitrionRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroUsuarioRequest;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    /**
     * Registrar un nuevo usuario
     * VALIDACIONES:
     * - Email válido y único
     * - Contraseña con reglas y almacenamiento con BCrypt
     * - Teléfono válido
     * - Usuario >= 18 años
     */
    UsuarioDTO registrar(RegistroUsuarioRequest request);

    /** Obtener usuario por ID */
    UsuarioDTO obtenerPorId(Integer id);

    /** Buscar usuario por email (normalizado en minúsculas) */
    Optional<UsuarioDTO> obtenerPorEmail(String email);

    /** Listar todos los usuarios (en producción: paginar) */
    List<UsuarioDTO> listar();

    /**
     * Actualizar perfil (parcial)
     * REGLAS:
     * - Si cambia teléfono, validar formato
     * - Si cambia fecha de nacimiento, debe seguir siendo >= 18 años
     * - Email NO se actualiza aquí
     */
    UsuarioDTO actualizarPerfil(Integer id, ActualizarPerfilRequest request);

    /**
     * Cambiar contraseña del usuario.
     * - Verifica la contraseña actual (si se envía)
     * - Aplica reglas de contraseña y re-BCrypt
     */
    void cambiarPassword(Integer id, String passwordActual, String nuevaPassword, boolean adminOverride);

    /** Desactivar (soft delete → INACTIVO) */
    void desactivar(Integer id);

    /** Activar (→ ACTIVO) */
    void activar(Integer id);

    /**
     * Eliminar (lógica de negocio): por política usamos soft delete,
     * pero dejamos el método para cumplir con la interfaz.
     */
    void delete(Integer id);

    UsuarioDTO promoverAAdmin(Integer usuarioId);
    void quitarAdmin(Integer usuarioId);

    UsuarioDTO registrarComoAnfitrion(Integer usuarioId, RegistrarPerfilAnfitrionRequest req);
    void quitarAnfitrion(Integer usuarioId);
}
