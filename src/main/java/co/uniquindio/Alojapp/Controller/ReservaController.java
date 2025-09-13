package co.uniquindio.Alojapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "Operaciones relacionadas con la gestión de reservas")
public class ReservaController {

    // =========================
    // ENDPOINT 1: POST /api/reservas
    // =========================
    @PostMapping
    @Operation(
            summary = "Crear nueva reserva",
            description = "Permite a un huésped crear una reserva para un alojamiento disponible"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> crearReserva(
            @Parameter(description = "ID del alojamiento a reservar", example = "15") @RequestParam String alojamientoId,
            @Parameter(description = "Fecha de inicio de la reserva", example = "2025-09-20") @RequestParam String fechaInicio,
            @Parameter(description = "Fecha de fin de la reserva", example = "2025-09-25") @RequestParam String fechaFin
    ) {
        return ResponseEntity.status(201).body("Reserva creada para alojamiento " + alojamientoId);
    }

    // =========================
    // ENDPOINT 2: GET /api/reservas
    // =========================
    @GetMapping
    @Operation(
            summary = "Listar reservas del usuario autenticado",
            description = "Devuelve todas las reservas realizadas por el usuario autenticado (huésped) o recibidas (anfitrión)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas correctamente"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> listarReservas() {
        return ResponseEntity.ok("Lista de reservas del usuario autenticado");
    }

    // =========================
    // ENDPOINT 3: GET /api/reservas/{id}
    // =========================
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener detalle de una reserva",
            description = "Permite consultar toda la información de una reserva específica por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> obtenerReserva(
            @Parameter(description = "ID de la reserva a consultar", example = "25") @PathVariable String id
    ) {
        return ResponseEntity.ok("Detalle de la reserva con ID: " + id);
    }

    // =========================
    // ENDPOINT 4: PUT /api/reservas/{id}/cancelar
    // =========================
    @PutMapping("/{id}/cancelar")
    @Operation(
            summary = "Cancelar una reserva",
            description = "Permite a un usuario cancelar una reserva activa"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> cancelarReserva(
            @Parameter(description = "ID de la reserva a cancelar", example = "25") @PathVariable String id
    ) {
        return ResponseEntity.ok("Reserva cancelada con ID: " + id);
    }
}
