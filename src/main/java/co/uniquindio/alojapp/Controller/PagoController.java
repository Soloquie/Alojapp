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
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Gestión de pagos asociados a las reservas")
@SecurityRequirement(name = "bearerAuth")
public class PagoController {

    // ===== DTOs simulados =====
    static class PagoRequest {
        @Schema(example = "50") public String reservaId;
        @Schema(example = "88") public String metodoPagoId;
        @Schema(example = "700000") public double monto;
    }

    static class PagoResponse {
        @Schema(example = "200") public String id;
        @Schema(example = "50") public String reservaId;
        @Schema(example = "88") public String metodoPagoId;
        @Schema(example = "700000") public double monto;
        @Schema(example = "APROBADO") public String estado;
        @Schema(example = "2025-09-20") public String fechaPago;
    }

    // =========================
    // ENDPOINT 1: POST /api/pagos
    // =========================
    @PostMapping
    @Operation(
            summary = "Registrar un pago",
            description = "Permite a un huésped registrar el pago de una reserva utilizando un método de pago válido"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Pago registrado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagoResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": \"200\", \"reservaId\": \"50\", \"metodoPagoId\": \"88\", \"monto\": 700000, \"estado\": \"PENDIENTE\", \"fechaPago\": \"2025-09-20\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Reserva o método de pago no encontrado")
    })
    public ResponseEntity<PagoResponse> registrarPago(@RequestBody PagoRequest datos) {
        PagoResponse p = new PagoResponse();
        p.id = "200";
        p.reservaId = datos.reservaId;
        p.metodoPagoId = datos.metodoPagoId;
        p.monto = datos.monto;
        p.estado = "PENDIENTE";
        p.fechaPago = "2025-09-20";
        return ResponseEntity.status(201).body(p);
    }

    // =========================
    // ENDPOINT 2: GET /api/pagos/{id}
    // =========================
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener detalle de un pago",
            description = "Devuelve toda la información de un pago específico por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoResponse> obtenerPago(
            @Parameter(description = "ID del pago", example = "200")
            @PathVariable String id
    ) {
        PagoResponse p = new PagoResponse();
        p.id = id;
        p.reservaId = "50";
        p.metodoPagoId = "88";
        p.monto = 700000;
        p.estado = "APROBADO";
        p.fechaPago = "2025-09-20";
        return ResponseEntity.ok(p);
    }

    // =========================
    // ENDPOINT 3: GET /api/pagos/reserva/{reservaId}
    // =========================
    @GetMapping("/reserva/{reservaId}")
    @Operation(
            summary = "Listar pagos de una reserva",
            description = "Devuelve todos los pagos realizados para una reserva específica"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagos obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<List<PagoResponse>> listarPagosPorReserva(
            @Parameter(description = "ID de la reserva", example = "50")
            @PathVariable String reservaId
    ) {
        List<PagoResponse> lista = new ArrayList<>();
        PagoResponse p = new PagoResponse();
        p.id = "200";
        p.reservaId = reservaId;
        p.metodoPagoId = "88";
        p.monto = 700000;
        p.estado = "APROBADO";
        p.fechaPago = "2025-09-20";
        lista.add(p);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // ENDPOINT 4: PUT /api/pagos/{id}/estado
    // =========================
    @PutMapping("/{id}/estado")
    @Operation(
            summary = "Actualizar estado de un pago",
            description = "Permite al administrador o sistema externo cambiar el estado de un pago (APROBADO, RECHAZADO)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado del pago actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<String> actualizarEstadoPago(
            @Parameter(description = "ID del pago", example = "200") @PathVariable String id,
            @Parameter(description = "Nuevo estado del pago", example = "APROBADO") @RequestParam String nuevoEstado
    ) {
        return ResponseEntity.ok("Pago " + id + " actualizado a estado " + nuevoEstado);
    }
}
