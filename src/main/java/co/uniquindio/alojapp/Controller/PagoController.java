package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.PagoDTO;
import co.uniquindio.alojapp.negocio.Service.PagoService;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import co.uniquindio.alojapp.seguridad.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Gestión de pagos asociados a las reservas")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;
    private final UsuarioRepository usuarioRepository;

    // ===== DTOs simulados solo para swagger rápido (puedes eliminarlos si ya usas PagoDTO / requests reales) =====
    static class PagoRequest {
        @Schema(example = "50") public Integer reservaId;
        @Schema(example = "TARJETA_CREDITO") public String metodoPago;
        @Schema(example = "700000") public double monto;
    }
    static class PagoResponse {
        @Schema(example = "200") public Integer id;
        @Schema(example = "50") public Integer reservaId;
        @Schema(example = "TARJETA_CREDITO") public String metodoPago;
        @Schema(example = "700000") public double monto;
        @Schema(example = "APROBADO") public String estado;
        @Schema(example = "2025-09-20T15:45:00") public String fechaPago;
    }

    // ----------------- helpers -----------------
    private Integer currentUserId() {
        String email = SecurityUtils.getEmailActual().orElseGet(() -> {
            Authentication a = SecurityContextHolder.getContext().getAuthentication();
            return (a != null) ? a.getName() : null;
        });
        if (!StringUtils.hasText(email)) {
            throw new RecursoNoEncontradoException("Usuario autenticado no encontrado");
        }
        return usuarioRepository.findByEmailIgnoreCase(email)
                .map(Usuario::getId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado por email"));
    }

    // =========================
    // ENDPOINT 1: POST /api/pagos  (registrar pago)
    // =========================
    @PostMapping
    @Operation(summary = "Registrar un pago")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago registrado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagoResponse.class),
                            examples = @ExampleObject(value = """
                            { "id": 200, "reservaId": 50, "metodoPago": "TARJETA_CREDITO",
                              "monto": 700000, "estado": "PENDIENTE", "fechaPago": "2025-09-20T15:45:00" }
                            """))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada o no pertenece al usuario")
    })
    public ResponseEntity<PagoDTO> registrarPago(@Valid @RequestBody PagoRequest body) {
        Integer userId = currentUserId();
        PagoDTO dto = pagoService.registrarPago(
                userId,
                body.reservaId,
                body.metodoPago,
                body.monto,
                LocalDateTime.now()
        );
        return ResponseEntity.status(201).body(dto);
    }

    // =========================
    // ENDPOINT 2: GET /api/pagos/{id}
    // =========================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de un pago")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoDTO> obtenerPago(
            @Parameter(description = "ID del pago", example = "200") @PathVariable Integer id
    ) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    // =========================
    // ENDPOINT 3: GET /api/pagos/reserva/{reservaId}
    // =========================
    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Obtener pago por reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Pago/Reserva no encontrado")
    })
    public ResponseEntity<PagoDTO> obtenerPagoPorReserva(
            @Parameter(description = "ID de la reserva", example = "50") @PathVariable Integer reservaId
    ) {
        return ResponseEntity.ok(pagoService.obtenerPorReserva(reservaId));
    }

    // =========================
    // ENDPOINT 4: PUT /api/pagos/{id}/estado
    // =========================
    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de un pago")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoDTO> actualizarEstadoPago(
            @Parameter(description = "ID del pago", example = "200") @PathVariable Integer id,
            @Parameter(description = "Nuevo estado", example = "APROBADO") @RequestParam String nuevoEstado
    ) {
        return ResponseEntity.ok(pagoService.actualizarEstado(id, nuevoEstado));
    }

    // ======================================================================
    // NUEVOS: “mis pagos” (antes estaban en UsuarioController)
    // ======================================================================

    @GetMapping("/mios")
    @Operation(summary = "Listar mis pagos", description = "Pagos del usuario autenticado, orden desc por fecha.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
    public ResponseEntity<List<PagoDTO>> listarMisPagos() {
        Integer userId = currentUserId();
        return ResponseEntity.ok(pagoService.listarPorUsuario(userId));
    }

    @GetMapping("/mios/reserva/{reservaId}")
    @Operation(summary = "Mi pago por reserva", description = "Pago asociado a una de mis reservas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Pago/Reserva no encontrado", content = @Content),
            @ApiResponse(responseCode = "403", description = "La reserva no pertenece al usuario", content = @Content)
    })
    public ResponseEntity<PagoDTO> miPagoPorReserva(@PathVariable Integer reservaId) {
        Integer userId = currentUserId();
        // El service valida pertenencia y lanza excepción si no pertenece.
        return ResponseEntity.ok(pagoService.obtenerDeUsuarioPorReserva(userId, reservaId));
    }

    @GetMapping("/mios/total")
    @Operation(summary = "Total pagado por mí (aprobados)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
    public ResponseEntity<BigDecimal> totalPagadoPorMi() {
        Integer userId = currentUserId();
        BigDecimal total = pagoService.totalPagadoPorUsuario(userId);
        return ResponseEntity.ok(total != null ? total : BigDecimal.ZERO);
    }
}
