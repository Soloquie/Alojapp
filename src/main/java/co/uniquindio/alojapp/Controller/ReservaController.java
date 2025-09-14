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
@Tag(name = "Reservas", description = "Operaciones relacionadas con la gestión de reservas de huéspedes y anfitriones")
@SecurityRequirement(name = "bearerAuth")
public class ReservaController {

    // ===== DTOs simulados para ejemplos =====
    static class ReservaResponse {
        @Schema(example = "25")
        public String id;
        @Schema(example = "15")
        public String alojamientoId;
        @Schema(example = "usuario@correo.com")
        public String usuarioEmail;
        @Schema(example = "2025-09-20")
        public String fechaInicio;
        @Schema(example = "2025-09-25")
        public String fechaFin;
        @Schema(example = "Pendiente")
        public String estado;
    }

    // =========================
    // ENDPOINT 1: POST /api/reservas
    // =========================
    @PostMapping
    @Operation(
            summary = "Crear nueva reserva",
            description = "Permite a un huésped crear una reserva para un alojamiento disponible"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Reserva creada correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReservaResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": \"25\", \"alojamientoId\": \"15\", \"usuarioEmail\": \"usuario@correo.com\", \"fechaInicio\": \"2025-09-20\", \"fechaFin\": \"2025-09-25\", \"estado\": \"Pendiente\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado")
    })
    public ResponseEntity<ReservaResponse> crearReserva(
            @Parameter(description = "ID del alojamiento a reservar", example = "15") @RequestParam String alojamientoId,
            @Parameter(description = "Fecha de inicio de la reserva", example = "2025-09-20") @RequestParam String fechaInicio,
            @Parameter(description = "Fecha de fin de la reserva", example = "2025-09-25") @RequestParam String fechaFin
    ) {
        ReservaResponse r = new ReservaResponse();
        r.id = "25";
        r.alojamientoId = alojamientoId;
        r.usuarioEmail = "usuario@correo.com";
        r.fechaInicio = fechaInicio;
        r.fechaFin = fechaFin;
        r.estado = "Pendiente";
        return ResponseEntity.status(201).body(r);
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
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas correctamente")
    })
    public ResponseEntity<List<ReservaResponse>> listarReservas() {
        List<ReservaResponse> lista = new ArrayList<>();
        ReservaResponse r = new ReservaResponse();
        r.id = "25";
        r.alojamientoId = "15";
        r.usuarioEmail = "usuario@correo.com";
        r.fechaInicio = "2025-09-20";
        r.fechaFin = "2025-09-25";
        r.estado = "Confirmada";
        lista.add(r);
        return ResponseEntity.ok(lista);
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
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<ReservaResponse> obtenerReserva(
            @Parameter(description = "ID de la reserva a consultar", example = "25") @PathVariable String id
    ) {
        ReservaResponse r = new ReservaResponse();
        r.id = id;
        r.alojamientoId = "15";
        r.usuarioEmail = "usuario@correo.com";
        r.fechaInicio = "2025-09-20";
        r.fechaFin = "2025-09-25";
        r.estado = "Pendiente";
        return ResponseEntity.ok(r);
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
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<String> cancelarReserva(
            @Parameter(description = "ID de la reserva a cancelar", example = "25") @PathVariable String id
    ) {
        return ResponseEntity.ok("Reserva cancelada con ID: " + id);
    }

    // =========================
    // ENDPOINT 5: GET /api/anfitrion/reservas
    // =========================
    @GetMapping("/anfitrion")
    @Operation(
            summary = "Listar reservas de los alojamientos del anfitrión",
            description = "Devuelve todas las reservas realizadas por huéspedes en los alojamientos publicados por el anfitrión"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas correctamente")
    })
    public ResponseEntity<List<ReservaResponse>> listarReservasAnfitrion() {
        List<ReservaResponse> lista = new ArrayList<>();
        ReservaResponse r = new ReservaResponse();
        r.id = "40";
        r.alojamientoId = "10";
        r.usuarioEmail = "huésped@correo.com";
        r.fechaInicio = "2025-09-21";
        r.fechaFin = "2025-09-25";
        r.estado = "Confirmada";
        lista.add(r);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // ENDPOINT 6: GET /api/anfitrion/reservas (con filtros)
    // =========================
    @GetMapping("/anfitrion/filtrar")
    @Operation(
            summary = "Filtrar reservas de anfitrión por estado y fechas",
            description = "Permite al anfitrión filtrar sus reservas por estado y rango de fechas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas filtradas correctamente"),

    })
    public ResponseEntity<List<ReservaResponse>> filtrarReservasAnfitrion(
            @Parameter(description = "Estado de la reserva", example = "Pendiente") @RequestParam(required = false) String estado,
            @Parameter(description = "Fecha desde", example = "2025-09-20") @RequestParam(required = false) String fechaDesde,
            @Parameter(description = "Fecha hasta", example = "2025-09-30") @RequestParam(required = false) String fechaHasta
    ) {
        List<ReservaResponse> lista = new ArrayList<>();
        ReservaResponse r = new ReservaResponse();
        r.id = "41";
        r.alojamientoId = "12";
        r.usuarioEmail = "otro@correo.com";
        r.fechaInicio = "2025-09-22";
        r.fechaFin = "2025-09-25";
        r.estado = estado != null ? estado : "Confirmada";
        lista.add(r);
        return ResponseEntity.ok(lista);
    }
}
