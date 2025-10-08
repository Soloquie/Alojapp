package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarPerfilRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistrarPerfilAnfitrionRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroUsuarioRequest;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.persistencia.DAO.UsuarioDAO;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceIMPL implements UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder; // BCrypt

    // ====== Reglas de validación alineadas a tu modelo/requests ======
    private static final Pattern EMAIL_REGEX  = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}$");
    private static final Pattern PHONE_REGEX  = Pattern.compile("^\\+?[0-9]{10,20}$"); // igual que en Entity/Requests
    private static final int MIN_PASSWORD_LEN = 8;

    // === CREATE ===
    @Override
    public UsuarioDTO registrar(RegistroUsuarioRequest request) {
        log.info("Registrando usuario: {}", request.getEmail());

        validarRegistro(request);

        // BCrypt
        String hash = passwordEncoder.encode(request.getPassword());

        // Persistir vía DAO (ya normaliza y setea campos de entidad)
        UsuarioDTO creado = usuarioDAO.save(request, hash);

        log.info("Usuario registrado con ID: {}", creado.getId());
        return creado;
    }

    // === READ ===
    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Integer id) {
        return usuarioDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> obtenerPorEmail(String email) {
        return usuarioDAO.findByEmail(normalizarEmail(email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listar() {
        return usuarioDAO.findAll();
    }

    // === UPDATE (perfil) ===
    @Override
    public UsuarioDTO actualizarPerfil(Integer id, ActualizarPerfilRequest request) {
        log.info("Actualizando perfil de usuario ID: {}", id);

        // Asegurar existencia
        obtenerPorId(id);

        // Validar campos presentes
        validarActualizacionPerfil(request);

        return usuarioDAO.actualizarPerfil(id, request)
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar el perfil del usuario " + id));
    }

    // === UPDATE (contraseña) ===
    @Override
    public void cambiarPassword(Integer id, String passwordActual, String nuevaPassword, boolean adminOverride) {
        log.info("Cambiando contraseña de usuario ID: {}", id);

        Usuario usuario = usuarioDAO.findEntityById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        if (!adminOverride) {
            if (passwordActual == null || !passwordEncoder.matches(passwordActual, usuario.getContrasenaHash())) {
                throw new IllegalArgumentException("La contraseña actual no es válida");
            }
        }

        validarReglasContrasena(nuevaPassword);

        String nuevoHash = passwordEncoder.encode(nuevaPassword);
        boolean ok = usuarioDAO.actualizarPassword(id, nuevoHash);
        if (!ok) {
            throw new RuntimeException("No se pudo actualizar la contraseña para el usuario " + id);
        }
    }

    // === ESTADO (     soft delete / activar) ===
    @Override
    public void desactivar(Integer id) {
        log.info("Desactivando usuario (soft delete) ID: {}", id);
        // Asegurar existencia
        obtenerPorId(id);

        boolean ok = usuarioDAO.desactivar(id);
        if (!ok) {
            throw new RuntimeException("No se pudo desactivar el usuario " + id);
        }
    }

    @Override
    public void activar(Integer id) {
        log.info("Activando usuario ID: {}", id);
        // Asegurar existencia
        Usuario usuario = usuarioDAO.findEntityById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Si ya está ACTIVO, nada que hacer
        if (usuario.getEstado() == EstadoUsuario.ACTIVO) {
            return;
        }

        boolean ok = usuarioDAO.activar(id); // <-- reemplazar por usuarioDAO.activar(id) cuando lo tengas
        if (!ok) {
            // Fallback mínimo (si aún no implementas activar en DAO):
            usuario.setEstado(EstadoUsuario.ACTIVO);
            // podrías añadir en DAO un método guardarEntity(Usuario u) o un updateEstado(id, estado)
            throw new RuntimeException("Implementa usuarioDAO.activar(id) para completar esta operación.");
        }
    }

    @Override
    public void delete(Integer id) {
        // En tu dominio preferimos soft delete:
        desactivar(id);
    }

    // ================= VALIDACIONES =================

    private void validarRegistro(RegistroUsuarioRequest r) {
        // Nombre básico (3–100)
        if (r.getNombre() == null || r.getNombre().trim().length() < 3 || r.getNombre().trim().length() > 100) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 100 caracteres");
        }

        // Email
        if (r.getEmail() == null || !EMAIL_REGEX.matcher(normalizarEmail(r.getEmail())).matches()) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }
        if (usuarioDAO.existsByEmail(normalizarEmail(r.getEmail()))) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // Contraseña (mínimo 8, al menos 1 mayúscula y 1 dígito —igual que tu request de restablecer)
        if (r.getPassword() == null) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        validarReglasContrasena(r.getPassword());

        // Teléfono
        if (r.getTelefono() == null || !PHONE_REGEX.matcher(r.getTelefono()).matches()) {
            throw new IllegalArgumentException("El teléfono no tiene un formato válido");
        }

        // Mayor de 18
        if (r.getFechaNacimiento() == null || !esMayorDeEdad(r.getFechaNacimiento())) {
            throw new IllegalArgumentException("El usuario debe ser mayor de 18 años");
        }

        // Normalizar email para continuar el flujo
        r.setEmail(normalizarEmail(r.getEmail()));
    }

    private void validarActualizacionPerfil(ActualizarPerfilRequest req) {
        if (req.getNombre() != null) {
            String n = req.getNombre().trim();
            if (n.length() < 3 || n.length() > 100) {
                throw new IllegalArgumentException("El nombre debe tener entre 3 y 100 caracteres");
            }
        }
        if (req.getTelefono() != null && !PHONE_REGEX.matcher(req.getTelefono()).matches()) {
            throw new IllegalArgumentException("El teléfono no tiene un formato válido");
        }
        if (req.getFechaNacimiento() != null && !esMayorDeEdad(req.getFechaNacimiento())) {
            throw new IllegalArgumentException("El usuario debe ser mayor de 18 años");
        }
        // Email NO se actualiza aquí (si alguna vez lo expones, recuerda validar unicidad)
    }

    private void validarReglasContrasena(String raw) {
        if (raw.length() < MIN_PASSWORD_LEN) {
            throw new IllegalArgumentException("La contraseña debe tener al menos " + MIN_PASSWORD_LEN + " caracteres");
        }
        boolean hasUpper = raw.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = raw.chars().anyMatch(Character::isDigit);
        if (!hasUpper || !hasDigit) {
            throw new IllegalArgumentException("La contraseña debe incluir al menos una mayúscula y un número");
        }
    }

    private boolean esMayorDeEdad(LocalDate nacimiento) {
        return Period.between(nacimiento, LocalDate.now()).getYears() >= 18;
    }

    private String normalizarEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    // UsuarioServiceIMPL.java (añade al final de la clase)
    @Override
    public UsuarioDTO promoverAAdmin(Integer usuarioId) {
        Usuario usuario = usuarioDAO.findEntityById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        // Si ya es admin, simplemente devuelve el DTO
        if (usuarioDAO.existeAdminPorUsuarioId(usuarioId)) {
            return usuarioDAO.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("No se pudo leer el usuario " + usuarioId));
        }

        // Crear fila en 'administradores'
        boolean ok = usuarioDAO.crearAdmin(usuarioId); // TODO: implementar en DAO
        if (!ok) throw new RuntimeException("No se pudo promover a admin el usuario " + usuarioId);

        return usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se pudo leer el usuario " + usuarioId));
    }

    @Override
    public void quitarAdmin(Integer usuarioId) {
        // idempotente: si no es admin, no falla
        boolean ok = usuarioDAO.eliminarAdmin(usuarioId); // TODO: implementar en DAO
        if (!ok) throw new RuntimeException("No se pudo quitar rol admin al usuario " + usuarioId);
    }

    @Override
    public UsuarioDTO registrarComoAnfitrion(Integer usuarioId, RegistrarPerfilAnfitrionRequest req) {
        Usuario usuario = usuarioDAO.findEntityById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        if (usuarioDAO.existeAnfitrionPorUsuarioId(usuarioId)) {
            // Ya es anfitrión → devuelve el DTO
            return usuarioDAO.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("No se pudo leer el usuario " + usuarioId));
        }

        // Validaciones simples
        if (req == null) throw new IllegalArgumentException("Datos de perfil de anfitrión requeridos");
        if (req.getDescripcionPersonal() != null && req.getDescripcionPersonal().length() > 500) {
            throw new IllegalArgumentException("La descripción personal no puede exceder 500 caracteres");
        }

        boolean ok = usuarioDAO.crearAnfitrion(
                usuarioId,
                req.getDescripcionPersonal(),
                req.getDocumentosLegalesUrl(),
                req.getFechaRegistro() // puede ser null; el DAO puede usar NOW()
        ); // TODO: implementar en DAO

        if (!ok) throw new RuntimeException("No se pudo registrar al usuario como anfitrión");

        return usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se pudo leer el usuario " + usuarioId));
    }

    @Override
    public void quitarAnfitrion(Integer usuarioId) {
        boolean ok = usuarioDAO.eliminarAnfitrion(usuarioId); // TODO: implementar en DAO
        if (!ok) throw new RuntimeException("No se pudo quitar rol anfitrión al usuario " + usuarioId);
    }

}
