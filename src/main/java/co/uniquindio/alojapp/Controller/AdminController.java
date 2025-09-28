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

    // ===== DTOs simulados para documentación =====
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

    static class ReservaResponse {
        @Schema(example = "40") public String id;
        @Schema(example = "Casa en la playa") public String alojamiento;
        @Schema(example = "huésped@correo.com") public String huespedEmail;
        @Schema(example = "2025-10-01") public String fechaInicio;
        @Schema(example = "2025-10-05") public String fechaFin;
        @Schema(example = "CONFIRMADA") public String estado;
    }

    static class PagoResponse {
        @Schema(example = "77") public String id;
        @Schema(example = "40") public String reservaId;
        @Schema(example = "350000") public Double monto;
        @Schema(example = "TARJETA") public String metodo;
        @Schema(example = "COMPLETADO") public String estado;
    }

    // =========================
    // USUARIOS
    // =========================
    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos los usuarios", description = "Permite al administrador obtener una lista de todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente")
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

    @PutMapping("/usuarios/{id}/estado")
    @Operation(summary = "Cambiar estado de un usuario", description = "Permite al administrador activar o bloquear un usuario de la plataforma")
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
    // ALOJAMIENTOS
    // =========================
    @GetMapping("/alojamientos")
    @Operation(summary = "Listar todos los alojamientos", description = "Permite al administrador obtener todos los alojamientos registrados")
    @ApiResponse(responseCode = "200", description = "Alojamientos obtenidos correctamente")
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

    @PutMapping("/alojamientos/{id}/estado")
    @Operation(summary = "Cambiar estado de un alojamiento", description = "Permite al administrador aprobar o bloquear un alojamiento publicado")
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
    // RESERVAS
    // =========================
    @GetMapping("/reservas")
    @Operation(summary = "Listar todas las reservas", description = "Permite al administrador obtener todas las reservas del sistema")
    @ApiResponse(responseCode = "200", description = "Reservas obtenidas correctamente")
    public ResponseEntity<List<ReservaResponse>> listarReservas() {
        List<ReservaResponse> lista = new ArrayList<>();
        ReservaResponse r = new ReservaResponse();
        r.id = "40";
        r.alojamiento = "Casa en la playa";
        r.huespedEmail = "huésped@correo.com";
        r.fechaInicio = "2025-10-01";
        r.fechaFin = "2025-10-05";
        r.estado = "CONFIRMADA";
        lista.add(r);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // PAGOS
    // =========================
    @GetMapping("/pagos")
    @Operation(summary = "Listar todos los pagos", description = "Permite al administrador obtener todos los pagos realizados en la plataforma")
    @ApiResponse(responseCode = "200", description = "Pagos obtenidos correctamente")
    public ResponseEntity<List<PagoResponse>> listarPagos() {
        List<PagoResponse> lista = new ArrayList<>();
        PagoResponse p = new PagoResponse();
        p.id = "77";
        p.reservaId = "40";
        p.monto = 350000.0;
        p.metodo = "TARJETA";
        p.estado = "COMPLETADO";
        lista.add(p);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // COMENTARIOS
    // =========================
    @DeleteMapping("/comentarios/{id}")
    @Operation(summary = "Eliminar comentario ofensivo", description = "Permite al administrador eliminar un comentario reportado como inapropiado")
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
