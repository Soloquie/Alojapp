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
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "Gestión de reservas de alojamientos")
@SecurityRequirement(name = "bearerAuth")
public class ReservaController {

    // ===== DTOs simulados =====
    static class ReservaRequest {
        @Schema(example = "15") public String alojamientoId;
        @Schema(example = "2025-10-01") public String fechaInicio;
        @Schema(example = "2025-10-05") public String fechaFin;
    }

    static class ReservaResponse {
        @Schema(example = "500") public String id;
        @Schema(example = "15") public String alojamientoId;
        @Schema(example = "juan@correo.com") public String huespedEmail;
        @Schema(example = "2025-10-01") public String fechaInicio;
        @Schema(example = "2025-10-05") public String fechaFin;
        @Schema(example = "CONFIRMADA") public String estado;
        @Schema(example = "1500000") public double total;
    }

    static class PagoResponse {
        @Schema(example = "9001") public String idPago;
        @Schema(example = "500") public String reservaId;
        @Schema(example = "1500000") public double monto;
        @Schema(example = "APROBADO") public String estadoPago;
        @Schema(example = "2025-09-20") public String fecha;
    }

    // =========================
    // ENDPOINT 1: POST /api/reservas
    // =========================
    @PostMapping
    @Operation(summary = "Crear nueva reserva",
            description = "Permite a un huésped reservar un alojamiento en unas fechas específicas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservaResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": \"500\", \"alojamientoId\": \"15\", \"huespedEmail\": \"juan@correo.com\", \"fechaInicio\": \"2025-10-01\", \"fechaFin\": \"2025-10-05\", \"estado\": \"PENDIENTE\", \"total\": 1500000 }"
                            ))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<ReservaResponse> crearReserva(@RequestBody ReservaRequest datos) {
        ReservaResponse r = new ReservaResponse();
        r.id = "500";
        r.alojamientoId = datos.alojamientoId;
        r.huespedEmail = "juan@correo.com";
        r.fechaInicio = datos.fechaInicio;
        r.fechaFin = datos.fechaFin;
        r.estado = "PENDIENTE";
        r.total = 1500000;
        return ResponseEntity.status(201).body(r);
    }

    // =========================
    // ENDPOINT 2: GET /api/reservas/mias
    // =========================
    @GetMapping("/mias")
    @Operation(summary = "Listar reservas del usuario autenticado",
            description = "Devuelve todas las reservas hechas por el huésped autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas correctamente")
    })
    public ResponseEntity<List<ReservaResponse>> listarMisReservas() {
        List<ReservaResponse> lista = new ArrayList<>();
        ReservaResponse r = new ReservaResponse();
        r.id = "500";
        r.alojamientoId = "15";
        r.huespedEmail = "juan@correo.com";
        r.fechaInicio = "2025-10-01";
        r.fechaFin = "2025-10-05";
        r.estado = "CONFIRMADA";
        r.total = 1500000;
        lista.add(r);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // ENDPOINT 3: GET /api/reservas/{id}
    // =========================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener reserva por ID",
            description = "Devuelve el detalle completo de una reserva específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<ReservaResponse> obtenerReserva(
            @Parameter(description = "ID de la reserva", example = "500") @PathVariable String id
    ) {
        ReservaResponse r = new ReservaResponse();
        r.id = id;
        r.alojamientoId = "15";
        r.huespedEmail = "juan@correo.com";
        r.fechaInicio = "2025-10-01";
        r.fechaFin = "2025-10-05";
        r.estado = "CONFIRMADA";
        r.total = 1500000;
        return ResponseEntity.ok(r);
    }

    // =========================
    // ENDPOINT 4: PUT /api/reservas/{id}/cancelar
    // =========================
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar reserva",
            description = "Permite al huésped cancelar una reserva pendiente o confirmada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "403", description = "No autorizado a cancelar esta reserva")
    })
    public ResponseEntity<String> cancelarReserva(
            @Parameter(description = "ID de la reserva", example = "500") @PathVariable String id
    ) {
        return ResponseEntity.ok("Reserva " + id + " cancelada correctamente");
    }

    // =========================
    // ENDPOINT 5: GET /api/reservas/{id}/pago
    // =========================
    @GetMapping("/{id}/pago")
    @Operation(summary = "Obtener pago asociado a la reserva",
            description = "Devuelve el detalle del pago realizado para una reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoResponse> obtenerPagoDeReserva(
            @Parameter(description = "ID de la reserva", example = "500") @PathVariable String id
    ) {
        PagoResponse p = new PagoResponse();
        p.idPago = "9001";
        p.reservaId = id;
        p.monto = 1500000;
        p.estadoPago = "APROBADO";
        p.fecha = "2025-09-20";
        return ResponseEntity.ok(p);
    }
}
