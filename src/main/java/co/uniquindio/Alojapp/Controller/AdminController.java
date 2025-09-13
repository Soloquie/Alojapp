package co.uniquindio.Alojapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administración", description = "Operaciones administrativas sobre usuarios, alojamientos, reservas y reportes")
public class AdminController {

    // =========================
    // USUARIOS
    // =========================
    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos los usuarios", description = "Permite al administrador ver todos los usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<String> listarUsuarios() {
        return ResponseEntity.ok("Lista de usuarios");
    }

    @PutMapping("/usuarios/{id}")
    @Operation(summary = "Editar usuario", description = "Permite al administrador editar los datos de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<String> editarUsuario(
            @Parameter(description = "ID del usuario a editar", example = "123") @PathVariable String id
    ) {
        return ResponseEntity.ok("Usuario actualizado con ID: " + id);
    }

    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Eliminar usuario", description = "Permite al administrador eliminar (desactivar) un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar", example = "123") @PathVariable String id
    ) {
        return ResponseEntity.noContent().build();
    }

    // =========================
    // ALOJAMIENTOS
    // =========================
    @GetMapping("/alojamientos")
    @Operation(summary = "Listar alojamientos", description = "Permite al administrador ver todos los alojamientos publicados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alojamientos obtenida")
    })
    public ResponseEntity<String> listarAlojamientos() {
        return ResponseEntity.ok("Lista de alojamientos");
    }

    @PutMapping("/alojamientos/{id}")
    @Operation(summary = "Editar alojamiento", description = "Permite al administrador modificar los datos de un alojamiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alojamiento actualizado"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado")
    })
    public ResponseEntity<String> editarAlojamiento(
            @Parameter(description = "ID del alojamiento a editar", example = "10") @PathVariable String id
    ) {
        return ResponseEntity.ok("Alojamiento actualizado con ID: " + id);
    }

    @DeleteMapping("/alojamientos/{id}")
    @Operation(summary = "Eliminar alojamiento", description = "Permite al administrador eliminar un alojamiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alojamiento eliminado"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado")
    })
    public ResponseEntity<Void> eliminarAlojamiento(
            @Parameter(description = "ID del alojamiento a eliminar", example = "10") @PathVariable String id
    ) {
        return ResponseEntity.noContent().build();
    }

    // =========================
    // RESERVAS
    // =========================
    @GetMapping("/reservas")
    @Operation(summary = "Listar reservas", description = "Permite al administrador ver todas las reservas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida")
    })
    public ResponseEntity<String> listarReservas() {
        return ResponseEntity.ok("Lista de reservas");
    }

    @PutMapping("/reservas/{id}")
    @Operation(summary = "Editar estado de una reserva", description = "Permite al administrador cambiar el estado de una reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<String> editarReserva(
            @Parameter(description = "ID de la reserva a editar", example = "25") @PathVariable String id
    ) {
        return ResponseEntity.ok("Reserva actualizada con ID: " + id);
    }

    // =========================
    // REPORTES / DENUNCIAS
    // =========================
    @GetMapping("/reportes")
    @Operation(summary = "Listar reportes/denuncias", description = "Permite al administrador ver todos los reportes enviados por usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida")
    })
    public ResponseEntity<String> listarReportes() {
        return ResponseEntity.ok("Lista de reportes");
    }

    @PutMapping("/reportes/{id}/resolver")
    @Operation(summary = "Marcar reporte como resuelto", description = "Permite al administrador marcar un reporte como resuelto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte resuelto"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    public ResponseEntity<String> resolverReporte(
            @Parameter(description = "ID del reporte a resolver", example = "77") @PathVariable String id
    ) {
        return ResponseEntity.ok("Reporte marcado como resuelto: " + id);
    }
}
