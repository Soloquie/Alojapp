package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarPerfilRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CambiarPasswordRequest;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.negocio.excepciones.AccesoNoAutorizadoException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import co.uniquindio.alojapp.seguridad.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión del perfil del usuario")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    // ----------------- helpers -----------------
    private String currentEmail() {
        return SecurityUtils.getEmailActual().orElseGet(() -> {
            Authentication a = SecurityContextHolder.getContext().getAuthentication();
            return (a != null) ? a.getName() : null;
        });
    }

    private Integer currentUserId() {
        String email = currentEmail();
        if (!StringUtils.hasText(email)) {
            throw new RecursoNoEncontradoException("Usuario autenticado no encontrado");
        }
        return usuarioRepository.findByEmailIgnoreCase(email)
                .map(Usuario::getId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado por email"));
    }

    // ----------------- perfil -----------------
    @GetMapping("/me")
    @Operation(summary = "Obtener mi perfil")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
    public ResponseEntity<UsuarioDTO> miPerfil() {
        String email = currentEmail();
        UsuarioDTO dto = usuarioService.obtenerPorEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/me")
    @Operation(summary = "Actualizar mi perfil")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
    public ResponseEntity<UsuarioDTO> actualizarPerfil(@Valid @RequestBody ActualizarPerfilRequest req) {
        Integer userId = currentUserId();
        return ResponseEntity.ok(usuarioService.actualizarPerfil(userId, req));
    }

    @PatchMapping("/me/password")
    @Operation(summary = "Cambiar mi contraseña")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contraseña actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Contraseña actual incorrecta", content = @Content)
    })
    public ResponseEntity<Void> cambiarPassword(@Valid @RequestBody CambiarPasswordRequest req) {
        Integer userId = currentUserId();
        usuarioService.cambiarPassword(userId, req.getPasswordActual(), req.getNuevaPassword(), true);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener perfil por ID (solo propio)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Integer id) {
        Integer current = currentUserId();
        if (!current.equals(id)) {
            throw new AccesoNoAutorizadoException("No puedes acceder al perfil de otro usuario");
        }
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }
}
