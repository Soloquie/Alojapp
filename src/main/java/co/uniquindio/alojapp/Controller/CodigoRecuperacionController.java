package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.Service.CodigoRecuperacionService;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.negocio.excepciones.BadRequestException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recuperacion")
@Tag(name = "Recuperación de contraseña", description = "Flujo para 'Olvidé mi contraseña'")
@RequiredArgsConstructor
public class CodigoRecuperacionController {

    private final CodigoRecuperacionService codigoService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    // 1) Solicitar código (público)
    @PostMapping("/solicitar")
    @Operation(summary = "Genera y envía un código de recuperación al correo indicado")
    @ApiResponse(responseCode = "200", description = "Código generado y enviado si el correo existe", content = @Content)
    public ResponseEntity<Map<String,Object>> solicitar(@Valid @RequestBody SolicitarCodigoRequest req) {
        // Anti-enumeración: respondemos 200 aunque el correo no exista.
        usuarioRepository.findByEmailIgnoreCase(req.getEmail()).ifPresent(u -> {
            try {
                codigoService.solicitarCodigo(req.getEmail()); // TU método actual
            } catch (IllegalArgumentException | IllegalStateException ex) {
                // Silenciamos para no revelar estado, pero puedes loguear si quieres
            }
        });
        return ResponseEntity.ok(Map.of(
                "message", "Si el correo existe, hemos enviado un código de recuperación."
        ));
    }

    // 2) Validar código (opcional, UX)
    @PostMapping("/validar")
    @Operation(summary = "Valida un código sin restablecer la contraseña todavía")
    @ApiResponse(responseCode = "200", description = "Resultado de la validación", content = @Content)
    public ResponseEntity<Map<String,Object>> validar(@Valid @RequestBody ValidarCodigoRequest req) {
        Integer userId = usuarioRepository.findByEmailIgnoreCase(req.getEmail())
                .map(u -> u.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        boolean valido = codigoService.validarCodigo(userId, req.getCodigo()); // boolean en tu impl
        return ResponseEntity.ok(Map.of("valido", valido));
    }

    // 3) Restablecer contraseña usando código (público)
    @PostMapping("/restablecer")
    @Operation(summary = "Cambia la contraseña usando un código válido")
    @ApiResponse(responseCode = "204", description = "Contraseña actualizada", content = @Content)
    public ResponseEntity<Void> restablecer(@Valid @RequestBody RestablecerPasswordRequest req) {
        Integer userId = usuarioRepository.findByEmailIgnoreCase(req.getEmail())
                .map(u -> u.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        // 1) validar
        boolean valido = codigoService.validarCodigo(userId, req.getCodigo());
        if (!valido) {
            throw new BadRequestException("Código inválido o expirado");
        }

        // 2) consumir código (lo marca como usado)
        codigoService.consumirCodigo(userId, req.getCodigo());

        // 3) cambiar contraseña SIN requerir la actual (flag de verificación en false)
        usuarioService.cambiarPassword(userId, null, req.getNuevaPassword(), true);

        return ResponseEntity.noContent().build();
    }

    // ======= Requests =======
    @Data
    public static class SolicitarCodigoRequest {
        @NotBlank @Email
        private String email;
    }

    @Data
    public static class ValidarCodigoRequest {
        @NotBlank @Email
        private String email;

        @NotBlank @Size(min = 6, max = 10)
        private String codigo;
    }

    @Data
    public static class RestablecerPasswordRequest {
        @NotBlank @Email
        private String email;

        @NotBlank @Size(min = 8, message = "Mínimo 8 caracteres")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$",
                message = "La contraseña debe incluir al menos una mayúscula y un número")
        private String nuevaPassword;

        @NotBlank @Size(min = 6, max = 10)
        private String codigo;
    }
}
