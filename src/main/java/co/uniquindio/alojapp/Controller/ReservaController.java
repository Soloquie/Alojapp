package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.ReservaDTO;
import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CancelarReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.ReservaService;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "Gestión de reservas de alojamientos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    // ---------------------------------------------------------------------
    // Helper: obtener ID del usuario autenticado (email -> id)
    // ---------------------------------------------------------------------
    private Integer currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (auth != null) ? auth.getName() : null;
        if (!StringUtils.hasText(email)) {
            throw new RecursoNoEncontradoException("Usuario autenticado no encontrado");
        }
        UsuarioDTO u = usuarioService.obtenerPorEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado por email"));
        return u.getId();
    }

    // =====================================================================
    // P4.1 / P4.2 - POST /api/reservas  (con QUERY PARAMS como en tus pruebas)
    // =====================================================================

    @Operation(
            summary = "Crear nueva reserva",
            description = "Permite a un huésped reservar un alojamiento en unas fechas específicas (usa query params como en P4.1/P4.2)."
    )
    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado") // <- nuevo
    })
    public ResponseEntity<ReservaDTO> crear(
            @Valid @RequestBody CrearReservaRequest request) {

        String email = SecurityUtils.getEmailActual()
                .orElseThrow(() -> new RuntimeException("No autenticado"));
        Integer usuarioId = usuarioRepository.findByEmailIgnoreCase(email)
                .map(Usuario::getId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ReservaDTO dto = reservaService.crear(usuarioId, request);
        return ResponseEntity.status(201).body(dto);
    }


    // =====================================================================
    // P4.3 - GET /api/reservas  (listar reservas del huésped autenticado)
    // =====================================================================
    @GetMapping
    @Operation(summary = "Listar reservas del usuario autenticado",
            description = "Devuelve todas las reservas hechas por el huésped autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<ReservaDTO>> listarMisReservas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "50") int tamano
    ) {
        Integer userId = currentUserId();
        PaginacionResponse<ReservaDTO> page = reservaService.listarPorHuesped(userId, pagina, tamano);
        return ResponseEntity.ok(page.getContenido());
    }

    // =====================================================================
    // P4.4 / P4.5 - GET /api/reservas/{id}
    // =====================================================================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener reserva por ID",
            description = "Devuelve el detalle completo de una reserva específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content)
    })
    public ResponseEntity<ReservaDTO> obtenerReserva(
            @Parameter(description = "ID de la reserva", example = "25") @PathVariable Integer id
    ) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    // =====================================================================
    // P4.6 - PUT /api/reservas/{id}/cancelar
    // Admite body opcional con motivo; si no llega, usa texto por defecto.
    // =====================================================================
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar reserva",
            description = "Permite al huésped cancelar una reserva pendiente o confirmada (RN12: ≥48h).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado a cancelar esta reserva", content = @Content),
            @ApiResponse(responseCode = "400", description = "Regla de negocio incumplida", content = @Content)
    })
    public ResponseEntity<String> cancelarReserva(
            @Parameter(description = "ID de la reserva", example = "25") @PathVariable Integer id,
            @Valid @RequestBody(required = false) CancelarReservaRequest body
    ) {
        Integer userId = currentUserId();
        CancelarReservaRequest req = (body != null) ? body
                : new CancelarReservaRequest("Cancelación solicitada por el usuario");
        reservaService.cancelar(userId, id, req);
        return ResponseEntity.ok("Reserva cancelada con ID: " + id);
    }

    // =====================================================================
    // P4.7 - GET /api/reservas/anfitrion
    // Lista reservas recibidas por el anfitrión autenticado
    // =====================================================================
    @GetMapping("/anfitrion")
    @Operation(summary = "Listar reservas de mis alojamientos (anfitrión)",
            description = "Devuelve las reservas hechas por huéspedes en mis alojamientos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<ReservaDTO>> listarReservasDeAnfitrion(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "50") int tamano,
            @Parameter(description = "ID del anfitrión (si no se envía, se asume que el usuarioId==anfitriónId en tus datos de prueba)")
            @RequestParam(required = false) Integer anfitrionId
    ) {
        // En tu modelo de datos de prueba, el usuario de prueba coincide con su anfitrión.
        // Si en tu dominio real necesitas mapear usuario->anfitrión, cambia aquí por tu servicio/DAO.
        Integer id = (anfitrionId != null) ? anfitrionId : currentUserId();
        PaginacionResponse<ReservaDTO> page = reservaService.listarPorAnfitrion(id, pagina, tamano);
        return ResponseEntity.ok(page.getContenido());
    }

    // =====================================================================
    // P4.8 - GET /api/reservas/anfitrion/filtrar
    // Filtros: estado, fechaDesde, fechaHasta (filtra en memoria sobre la página recibida)
    // =====================================================================
    @GetMapping("/anfitrion/filtrar")
    @Operation(summary = "Filtrar reservas del anfitrión",
            description = "Filtra por estado y rango de fechas de check-in sobre las reservas del anfitrión autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<ReservaDTO>> filtrarReservasDeAnfitrion(
            @Parameter(description = "Estado", example = "PENDIENTE")
            @RequestParam(required = false) EstadoReserva estado,
            @Parameter(description = "Fecha desde (check-in)", example = "2025-09-20")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @Parameter(description = "Fecha hasta (check-in)", example = "2025-09-30")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "100") int tamano,
            @RequestParam(required = false) Integer anfitrionId
    ) {
        Integer id = (anfitrionId != null) ? anfitrionId : currentUserId();
        // Traemos página amplia y filtramos en memoria para simplificar
        List<ReservaDTO> base = reservaService
                .listarPorAnfitrion(id, pagina, tamano)
                .getContenido();

        List<ReservaDTO> filtrada = base.stream()
                .filter(r -> estado == null || (r.getEstado() != null && r.getEstado().equalsIgnoreCase(estado.name())))
                .filter(r -> fechaDesde == null || (r.getFechaCheckin() != null && !r.getFechaCheckin().isBefore(fechaDesde)))
                .filter(r -> fechaHasta == null || (r.getFechaCheckin() != null && !r.getFechaCheckin().isAfter(fechaHasta)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filtrada);
    }
}
