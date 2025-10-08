package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarPerfilRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CambiarPasswordRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroUsuarioRequest;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ========= 1) Registro  =========
    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado")
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrar(@Valid @RequestBody RegistroUsuarioRequest request) {
        UsuarioDTO dto = usuarioService.registrar(request);
        return ResponseEntity
                .created(URI.create("/api/usuarios/" + dto.getId()))
                .body(dto); // 201 Created
    }

    // ========= 2) Obtener perfil propio =========
    @Operation(summary = "Obtener perfil del usuario autenticado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Perfil obtenido")
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> obtenerPerfil() {
        Integer userId = currentUserIdOrThrow();
        UsuarioDTO dto = usuarioService.obtenerPorId(userId);
        return ResponseEntity.ok(dto); // 200
    }

    // ========= 3) Actualizar perfil propio =========
    @Operation(summary = "Actualizar perfil del usuario autenticado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Perfil actualizado")
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @PutMapping("/perfil")
    public ResponseEntity<UsuarioDTO> actualizarPerfil(@Valid @RequestBody ActualizarPerfilRequest request) {
        Integer userId = currentUserIdOrThrow();
        UsuarioDTO dto = usuarioService.actualizarPerfil(userId, request);
        return ResponseEntity.ok(dto); // 200
    }

    // ========= 4) Cambiar contraseña =========
    @Operation(summary = "Cambiar contraseña del usuario autenticado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Contraseña cambiada")
    @ApiResponse(responseCode = "400", description = "Reglas inválidas o actual incorrecta", content = @Content)
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @PutMapping("/perfil/password")
    public ResponseEntity<Void> cambiarPassword(@Valid @RequestBody CambiarPasswordRequest body) {
        Integer userId = currentUserIdOrThrow();
        usuarioService.cambiarPassword(userId, body.getPasswordActual(), body.getNuevaPassword(), false);
        return ResponseEntity.noContent().build(); // 204
    }

    // ========= 5) Listar usuarios (solo ADMIN) =========
    @Operation(summary = "Listar todos los usuarios (solo ADMIN)")
    @ApiResponse(responseCode = "200", description = "Listado devuelto")
    @ApiResponse(responseCode = "403", description = "Prohibido (no ADMIN)", content = @Content)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // ========= 6) Desactivar (soft delete) propio =========
    @Operation(summary = "Desactivar la cuenta del usuario autenticado (soft delete)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Cuenta desactivada")
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @DeleteMapping("/perfil")
    public ResponseEntity<Void> desactivarCuenta() {
        Integer userId = currentUserIdOrThrow();
        usuarioService.desactivar(userId);
        return ResponseEntity.noContent().build(); // 204
    }

    // ================== Helper ==================
    /**
     * Obtiene el ID del usuario autenticado sin usar @AuthenticationPrincipal.
     * Estrategia:
     * 1) Si el Authentication.getName() es numérico -> se usa como ID.
     * 2) Si no, se asume que es email/username -> se busca el ID por email.
     */
    private Integer currentUserIdOrThrow() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("No autenticado"); // Tu @ControllerAdvice lo mapea a 401
        }

        // 1) ¿UserDetails?
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails ud) {
            String username = ud.getUsername(); // normalmente email
            return usuarioService.obtenerPorEmail(username)
                    .map(UsuarioDTO::getId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado para: " + username));
        }

        // 2) Fallback: usar auth.getName()
        String name = auth.getName(); // en muchos casos es email; a veces puede ser el ID
        // ¿es numérico? (id directo)
        try {
            return Integer.parseInt(name);
        } catch (NumberFormatException ignored) { }

        // Si no es numérico, tratar como email/username:
        return usuarioService.obtenerPorEmail(name)
                .map(UsuarioDTO::getId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para: " + name));
    }

    // DuplicateEmailException.java
    @ResponseStatus(HttpStatus.CONFLICT)
    public class DuplicateEmailException extends RuntimeException {
        public DuplicateEmailException(String message) { super(message); }
    }

}
