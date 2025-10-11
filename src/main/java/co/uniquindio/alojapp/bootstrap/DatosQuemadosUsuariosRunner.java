package co.uniquindio.alojapp.bootstrap;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarPerfilRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroUsuarioRequest;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.persistencia.DAO.UsuarioDAO;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatosQuemadosUsuariosRunner implements CommandLineRunner {

    private final UsuarioService usuarioService;   // interfaz con métodos en español
    private final UsuarioDAO usuarioDAO;           // para validar el hash directamente en la entidad
    private final PasswordEncoder passwordEncoder; // BCrypt
    private final org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Override
    // @Transactional
    public void run(String... args) {
        log.info("===== INICIO: DATOS QUEMADOS USUARIOS =====");

        final String emailPrueba     = "ana.gomez@ejemplo.com";
        final String passwordInicial = "ClaveSegura1"; // >=8, mayúscula y dígito
        final String passwordNueva   = "ClaveNueva2";

        try {
            // 0) ¿Ya existe el usuario de prueba?
            Optional<UsuarioDTO> existenteOpt = usuarioService.obtenerPorEmail(emailPrueba);
            UsuarioDTO usuarioDTO;

            if (existenteOpt.isEmpty()) {
                // 1) REGISTRAR (una sola vez)
                RegistroUsuarioRequest registro = new RegistroUsuarioRequest();
                registro.setNombre("Ana María Gómez");
                registro.setEmail(emailPrueba);
                registro.setPassword(passwordInicial);
                registro.setTelefono("+573001112233");
                registro.setFechaNacimiento(LocalDate.of(1995, 8, 15));

                usuarioDTO = usuarioService.registrar(registro);
                log.info("Usuario registrado -> ID={} email={}", usuarioDTO.getId(), usuarioDTO.getEmail());

                // Cuenta directa en BD
                Long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuarios", Long.class);
                log.info("Filas en tabla usuarios (consulta directa BD) = {}", total);


            } else {
                usuarioDTO = existenteOpt.get();
                log.info("Usuario de prueba ya existía -> ID={} email={}", usuarioDTO.getId(), usuarioDTO.getEmail());
            }

            // 2) Verificar BCrypt contra la entidad y normalizar si hace falta
            Usuario entidad = usuarioDAO.findEntityById(usuarioDTO.getId())
                    .orElseThrow(() -> new RuntimeException("No se pudo leer la entidad del usuario"));
            boolean hashOK = passwordEncoder.matches(passwordInicial, entidad.getContrasenaHash());
            log.info("Verificación BCrypt (password inicial) = {}", hashOK);

            if (!hashOK) {
                log.warn("El hash actual NO coincide con la contraseña inicial. Se normaliza para la prueba...");
                String nuevoHash = passwordEncoder.encode(passwordInicial);
                jdbcTemplate.update(
                        "UPDATE usuarios SET contrasena_hash = ? WHERE usuario_id = ?",
                        nuevoHash, usuarioDTO.getId()
                );
                // refrescamos
                entidad = usuarioDAO.findEntityById(usuarioDTO.getId()).orElseThrow();
                hashOK = passwordEncoder.matches(passwordInicial, entidad.getContrasenaHash());
                log.info("Verificación tras normalizar (debe ser true) = {}", hashOK);
            }

            // 3) Intentar registrar duplicado (debe fallar por email único)
            try {
                RegistroUsuarioRequest duplicado = new RegistroUsuarioRequest();
                duplicado.setNombre("Ana Duplicada");
                duplicado.setEmail(emailPrueba); // mismo email
                duplicado.setPassword("OtraClave3");
                duplicado.setTelefono("+573001112299");
                duplicado.setFechaNacimiento(LocalDate.of(1996, 1, 20));

                usuarioService.registrar(duplicado);
                log.error("ERROR: El registro duplicado NO debería haber pasado");
            } catch (Exception e) {
                log.info("OK (duplicado rechazado): {}", e.getMessage());
            }

            // 4) LISTAR todos
            List<UsuarioDTO> usuarios = usuarioService.listar();
            log.info("Total usuarios en el sistema: {}", usuarios.size());

            // 5) ACTUALIZAR PERFIL (parcial)
            ActualizarPerfilRequest act = new ActualizarPerfilRequest();
            act.setNombre("Ana M. Gómez");
            act.setTelefono("+573001119999");
            // act.setFechaNacimiento(LocalDate.of(1995, 8, 15)); // opcional

            UsuarioDTO actualizado = usuarioService.actualizarPerfil(usuarioDTO.getId(), act);
            log.info("Perfil actualizado -> ID={} nombre={} tel={}",
                    actualizado.getId(), actualizado.getNombre(), actualizado.getTelefono());

            // 6) CAMBIAR CONTRASEÑA (usuario normal, sin override)
            usuarioService.cambiarPassword(usuarioDTO.getId(), passwordInicial, passwordNueva, false);
            log.info("Contraseña cambiada correctamente para ID={}", usuarioDTO.getId());

            // Verificar nuevo hash
            Usuario entidad2 = usuarioDAO.findEntityById(usuarioDTO.getId()).orElseThrow();
            boolean hashOK2 = passwordEncoder.matches(passwordNueva, entidad2.getContrasenaHash());
            log.info("Verificación BCrypt (password nueva) = {}", hashOK2);

            // 7) DESACTIVAR (soft delete) - opcional
            // usuarioService.desactivar(usuarioDTO.getId());
            // log.info("Usuario desactivado -> ID={}", usuarioDTO.getId());

        } catch (Exception e) {
            log.error("Error en la carga de datos de prueba", e);
        }

        log.info("===== FIN: DATOS QUEMADOS USUARIOS =====");
    }
}
