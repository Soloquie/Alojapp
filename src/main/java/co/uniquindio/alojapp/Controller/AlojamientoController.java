package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.AlojamientoDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.request.BuscarAlojamientosRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.AlojamientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Alojamientos", description = "Gestión de publicaciones de alojamientos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AlojamientoController {

    private final AlojamientoService alojamientoService;

    // =========================================================================
    // Rutas para ANFITRIÓN (crear / actualizar / eliminar / listar propios)
    // =========================================================================

    @PostMapping("/anfitriones/{anfitrionId}/alojamientos")
    @Operation(summary = "Crear nuevo alojamiento (anfitrión)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alojamiento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Anfitrión no encontrado", content = @Content)
    })
    public ResponseEntity<AlojamientoDTO> crearAlojamiento(
            @Parameter(description = "ID del anfitrión", example = "2")
            @PathVariable Integer anfitrionId,
            @Valid @RequestBody CrearAlojamientoRequest request
    ) {
        AlojamientoDTO dto = alojamientoService.crear(anfitrionId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/anfitriones/{anfitrionId}/alojamientos/{alojamientoId}")
    @Operation(summary = "Actualizar alojamiento (anfitrión) - solo dueño")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alojamiento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado o no pertenece al anfitrión", content = @Content)
    })
    public ResponseEntity<AlojamientoDTO> actualizarAlojamiento(
            @Parameter(description = "ID del anfitrión", example = "2")
            @PathVariable Integer anfitrionId,
            @Parameter(description = "ID del alojamiento", example = "10")
            @PathVariable Integer alojamientoId,
            @Valid @RequestBody ActualizarAlojamientoRequest request
    ) {
        AlojamientoDTO dto = alojamientoService.actualizar(alojamientoId, anfitrionId, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/anfitriones/{anfitrionId}/alojamientos/{alojamientoId}")
    @Operation(summary = "Eliminar (soft) alojamiento (anfitrión) - valida reservas futuras")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alojamiento eliminado"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado o no pertenece al anfitrión", content = @Content),
            @ApiResponse(responseCode = "409", description = "Tiene reservas futuras", content = @Content)
    })
    public ResponseEntity<Void> eliminarAlojamiento(
            @Parameter(description = "ID del anfitrión", example = "2")
            @PathVariable Integer anfitrionId,
            @Parameter(description = "ID del alojamiento", example = "10")
            @PathVariable Integer alojamientoId
    ) {
        boolean ok = alojamientoService.eliminar(alojamientoId, anfitrionId);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/anfitriones/{anfitrionId}/alojamientos")
    @Operation(summary = "Listar alojamientos del anfitrión (paginado)")
    public ResponseEntity<PaginacionResponse<AlojamientoDTO>> listarAlojamientosDelAnfitrion(
            @Parameter(description = "ID del anfitrión", example = "2")
            @PathVariable Integer anfitrionId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano
    ) {
        return ResponseEntity.ok(alojamientoService.listarPorAnfitrion(anfitrionId, pagina, tamano));
    }

    // =========================================================================
    // Rutas PÚBLICAS (ver detalle, listar activos, buscar con filtros)
    // =========================================================================

    @GetMapping("/alojamientos/{id}")
    @Operation(summary = "Obtener alojamiento por ID (público)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alojamiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado", content = @Content)
    })
    public ResponseEntity<AlojamientoDTO> obtenerAlojamiento(
            @Parameter(description = "ID del alojamiento", example = "10")
            @PathVariable Integer id
    ) {
        Optional<AlojamientoDTO> dto = Optional.ofNullable(alojamientoService.obtenerPorId(id));
        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/alojamientos")
    @Operation(summary = "Listar alojamientos. Si hay filtros, busca; si no, lista activos")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<PaginacionResponse<AlojamientoDTO>> listarOBuscar(
            @Parameter(description = "Ciudad", example = "Cartagena")
            @RequestParam(required = false) String ciudad,
            @Parameter(description = "Precio mínimo", example = "100000.00")
            @RequestParam(required = false) BigDecimal precioMin,
            @Parameter(description = "Precio máximo", example = "500000.00")
            @RequestParam(required = false) BigDecimal precioMax,
            @Parameter(description = "Capacidad mínima", example = "4")
            @RequestParam(required = false) Integer capacidadMinima,
            @Parameter(description = "IDs de servicios", example = "1,3,5")
            @RequestParam(required = false) List<Integer> serviciosIds,
            @Parameter(description = "Check-in", example = "2025-11-10")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @Parameter(description = "Check-out", example = "2025-11-15")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
            @Parameter(description = "Ordenar por (campo entidad)", example = "precioNoche")
            @RequestParam(required = false, defaultValue = "fechaCreacion") String ordenarPor,
            @Parameter(description = "ASC|DESC", example = "DESC")
            @RequestParam(required = false, defaultValue = "DESC") String direccionOrden,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano
    ) {
        boolean hayFiltros =
                !ObjectUtils.isEmpty(ciudad) ||
                        precioMin != null || precioMax != null ||
                        capacidadMinima != null ||
                        (serviciosIds != null && !serviciosIds.isEmpty()) ||
                        (checkin != null && checkout != null);

        if (!hayFiltros) {
            return ResponseEntity.ok(alojamientoService.listarActivos(pagina, tamano));
        }

        BuscarAlojamientosRequest req = BuscarAlojamientosRequest.builder()
                .ciudad(ciudad)
                .precioMin(precioMin)
                .precioMax(precioMax)
                .capacidadMinima(capacidadMinima)
                .serviciosIds(serviciosIds)
                .fechaCheckin(checkin)
                .fechaCheckout(checkout)
                .ordenarPor(ordenarPor)
                .direccionOrden(direccionOrden)
                .pagina(pagina)
                .tamanoPagina(tamano)
                .build();

        return ResponseEntity.ok(alojamientoService.buscar(req));
    }

    @PostMapping("/alojamientos/buscar")
    @Operation(summary = "Buscar con filtros completos (body)")
    public ResponseEntity<PaginacionResponse<AlojamientoDTO>> buscar(@Valid @RequestBody BuscarAlojamientosRequest request) {
        return ResponseEntity.ok(alojamientoService.buscar(request));
    }

    @GetMapping("/alojamientos/{id}/disponibilidad")
    @Operation(summary = "Verificar disponibilidad por fechas")
    public ResponseEntity<Boolean> disponibilidad(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout
    ) {
        return ResponseEntity.ok(alojamientoService.estaDisponible(id, checkin, checkout));
    }
}
