package co.uniquindio.alojapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administración", description = "Endpoints para administración general de la plataforma")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    // DTOs simulados solo para documentación de ejemplos
    static class UsuarioResponse {
        @Schema(example = "55") public String id;
        @Schema(example = "juan@correo.com") public String email;
        @Schema(example = "HUESPED") public String rol;
        @Schema(example = "ACTIVO") public String estado;
    }

    static class AlojamientoResponse {
        @Schema(example = "15") public String id;
        @Schema(example = "Casa en la playa") public String nombre;
        @Schema(example = "juan@correo.com") public String anfitrionEmail;
        @Schema(example = "ACTIVO") public String estado;
    }

    // =========================
    // ENDPOINT 1: GET /api/admin/usuarios
    // =========================
    @GetMapping("/usuarios")
    @Operation(
            summary = "Listar todos los usuarios",
            description = "Permite al administrador obtener una lista de todos los usuarios registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente")
    })
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> lista = new ArrayList<>();
        UsuarioResponse u = new UsuarioResponse();
        u.id = "55";
        u.email = "juan@correo.com";
        u.rol = "HUESPED";
        u.estado = "ACTIVO";
        lista.add(u);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // ENDPOINT 2: PUT /api/admin/usuarios/{id}/estado
    // =========================
    @PutMapping("/usuarios/{id}/estado")
    @Operation(
            summary = "Cambiar estado de un usuario",
            description = "Permite al administrador activar o bloquear un usuario de la plataforma"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<String> cambiarEstadoUsuario(
            @Parameter(description = "ID del usuario", example = "55") @PathVariable String id,
            @Parameter(description = "Nuevo estado del usuario", example = "BLOQUEADO") @RequestParam String nuevoEstado
    ) {
        return ResponseEntity.ok("Usuario " + id + " cambiado a estado " + nuevoEstado);
    }

    // =========================
    // ENDPOINT 3: GET /api/admin/alojamientos
    // =========================
    @GetMapping("/alojamientos")
    @Operation(
            summary = "Listar todos los alojamientos",
            description = "Permite al administrador obtener todos los alojamientos registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alojamientos obtenidos correctamente")
    })
    public ResponseEntity<List<AlojamientoResponse>> listarAlojamientos() {
        List<AlojamientoResponse> lista = new ArrayList<>();
        AlojamientoResponse a = new AlojamientoResponse();
        a.id = "15";
        a.nombre = "Casa en la playa";
        a.anfitrionEmail = "juan@correo.com";
        a.estado = "ACTIVO";
        lista.add(a);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // ENDPOINT 4: PUT /api/admin/alojamientos/{id}/estado
    // =========================
    @PutMapping("/alojamientos/{id}/estado")
    @Operation(
            summary = "Cambiar estado de un alojamiento",
            description = "Permite al administrador aprobar o bloquear un alojamiento publicado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado")
    })
    public ResponseEntity<String> cambiarEstadoAlojamiento(
            @Parameter(description = "ID del alojamiento", example = "15") @PathVariable String id,
            @Parameter(description = "Nuevo estado del alojamiento", example = "BLOQUEADO") @RequestParam String nuevoEstado
    ) {
        return ResponseEntity.ok("Alojamiento " + id + " cambiado a estado " + nuevoEstado);
    }

    // =========================
    // ENDPOINT 5: DELETE /api/admin/comentarios/{id}
    // =========================
    @DeleteMapping("/comentarios/{id}")
    @Operation(
            summary = "Eliminar comentario ofensivo",
            description = "Permite al administrador eliminar un comentario reportado como inapropiado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comentario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Comentario no encontrado")
    })
    public ResponseEntity<Void> eliminarComentario(
            @Parameter(description = "ID del comentario a eliminar", example = "100") @PathVariable String id
    ) {
        return ResponseEntity.noContent().build();
    }
}
