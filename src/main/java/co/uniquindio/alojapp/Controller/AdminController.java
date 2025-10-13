package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.AdministradorDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAdministradorRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAdministradorRequest;
import co.uniquindio.alojapp.negocio.Service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administración", description = "Gestión de administradores y funciones de plataforma")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AdminController {

    private final AdministradorService adminService;

    // =====================================================================
    // ADMINISTRADORES (asignar / actualizar / revocar / listar / consultar)
    // =====================================================================

    @PostMapping("/administradores")
    @Operation(
            summary = "Asignar rol de administrador a un usuario",
            description = "Crea el registro de administrador para el usuario indicado. "
                    + "Si no se indica nivelAcceso se asume 'ADMIN'."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Administrador asignado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "El usuario ya es administrador", content = @Content)
    })
    // Requiere, por ejemplo, SUPER_ADMIN. Ajusta a tu esquema de autoridades:
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')") // quita o ajusta si aún no mapeas roles
    public ResponseEntity<AdministradorDTO> asignarAdministrador(
            @Valid @RequestBody CrearAdministradorRequest request
    ) {
        AdministradorDTO dto = adminService.asignar(request);
        return ResponseEntity.status(201).body(dto);
    }

    @PutMapping("/administradores/{id}")
    @Operation(summary = "Actualizar datos de un administrador",
            description = "Permite cambiar el nivel de acceso y/o los permisos JSON.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador actualizado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado", content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<AdministradorDTO> actualizarAdministrador(
            @Parameter(description = "ID del administrador", example = "12")
            @PathVariable Integer id,
            @Valid @RequestBody ActualizarAdministradorRequest request
    ) {
        return ResponseEntity.ok(adminService.actualizar(id, request));
    }

    @DeleteMapping("/administradores/usuario/{usuarioId}")
    @Operation(summary = "Revocar rol administrador a un usuario",
            description = "Elimina (si existe) el registro de administrador para el usuario indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Revocado (idempotente)"),
            @ApiResponse(responseCode = "404", description = "— (idempotente, no falla si no existe)", content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<Void> revocarAdministrador(
            @Parameter(description = "ID del usuario", example = "55") @PathVariable Integer usuarioId
    ) {
        adminService.revocarPorUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/administradores/{id}")
    @Operation(summary = "Obtener administrador por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado", content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','MODERADOR')")
    public ResponseEntity<AdministradorDTO> obtenerAdministrador(
            @Parameter(description = "ID del administrador", example = "12")
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(adminService.obtenerPorId(id));
    }

    @GetMapping("/administradores/usuario/{usuarioId}")
    @Operation(summary = "Obtener administrador por ID de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No existe registro para ese usuario", content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','MODERADOR')")
    public ResponseEntity<AdministradorDTO> obtenerPorUsuario(
            @Parameter(description = "ID del usuario", example = "55")
            @PathVariable Integer usuarioId
    ) {
        Optional<AdministradorDTO> dto = adminService.obtenerPorUsuarioId(usuarioId);
        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/administradores")
    @Operation(
            summary = "Listar administradores",
            description = "Si se envía el query param `nivel`, filtra por nivel (SUPER_ADMIN, ADMIN o MODERADOR)."
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<List<AdministradorDTO>> listarAdministradores(
            @Parameter(description = "Filtrar por nivel", example = "ADMIN")
            @RequestParam(required = false) String nivel
    ) {
        if (nivel == null || nivel.isBlank()) {
            return ResponseEntity.ok(adminService.listar());
        }
        return ResponseEntity.ok(adminService.listarPorNivel(nivel));
    }

    @GetMapping("/administradores/_stats")
    @Operation(summary = "Estadísticas de administradores",
            description = "Devuelve el total y, opcionalmente, el total por nivel si se pasa `nivel`.")
    @ApiResponse(responseCode = "200", description = "OK")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<?> statsAdministradores(
            @Parameter(description = "Nivel a contar", example = "MODERADOR")
            @RequestParam(required = false) String nivel
    ) {
        if (nivel == null || nivel.isBlank()) {
            return ResponseEntity.ok(
                    java.util.Map.of("total", adminService.contar())
            );
        }
        return ResponseEntity.ok(
                java.util.Map.of("nivel", nivel.toUpperCase(), "total", adminService.contarPorNivel(nivel))
        );
    }

}
