package co.uniquindio.Alojapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
public class UsuarioController {

    // =========================
    // ENDPOINT 1: GET /api/users/me
    // =========================
    @GetMapping("/me")
    @Operation(
            summary = "Obtener perfil del usuario autenticado",
            description = "Devuelve los datos personales del usuario actualmente autenticado en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil obtenido exitosamente"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> obtenerPerfilPropio() {
        return ResponseEntity.ok("Perfil del usuario autenticado");
    }

    // =========================
    // ENDPOINT 2: PUT /api/users/me
    // =========================
    @PutMapping("/me")
    @Operation(
            summary = "Actualizar perfil del usuario autenticado",
            description = "Permite al usuario cambiar sus datos personales"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> actualizarPerfilPropio(
            @Parameter(description = "Nuevo nombre del usuario", example = "Juan Pérez") @RequestParam(required = false) String nombre,
            @Parameter(description = "Nuevo teléfono del usuario", example = "3001234567") @RequestParam(required = false) String telefono
    ) {
        return ResponseEntity.ok("Perfil actualizado correctamente");
    }

    // =========================
    // ENDPOINT 3: GET /api/users/{id}
    // =========================
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener perfil público de un usuario",
            description = "Devuelve información pública de un usuario por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil público obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> obtenerPerfilPublico(
            @Parameter(description = "ID del usuario a consultar", example = "123") @PathVariable String id
    ) {
        return ResponseEntity.ok("Perfil público del usuario con ID: " + id);
    }
}
