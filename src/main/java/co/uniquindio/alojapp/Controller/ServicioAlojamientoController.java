package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.ServicioAlojamientoDTO;
import co.uniquindio.alojapp.negocio.Service.ServicioAlojamientoService;
import co.uniquindio.alojapp.negocio.Service.ServicioAlojamientoService.ServicioUsoDTO;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicios de Alojamiento", description = "Catálogo de servicios (Wi-Fi, Piscina, Parqueadero, etc.)")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ServicioAlojamientoController {

    private final ServicioAlojamientoService servicioService;

    // ===== Requests =====
    public record CrearServicioRequest(
            @NotBlank @Size(max = 100)
            @Schema(example = "Wi-Fi") String nombre,

            @Size(max = 255)
            @Schema(example = "Internet de alta velocidad") String descripcion,

            @Schema(example = "https://cdn.ejemplo.com/icons/wifi.svg") String iconoUrl
    ) {}

    public record ActualizarServicioRequest(
            @Size(max = 100)
            @Schema(example = "Wi-Fi Premium") String nombre,

            @Size(max = 255)
            @Schema(example = "Internet de alta velocidad con repetidores") String descripcion,

            @Schema(example = "https://cdn.ejemplo.com/icons/wifi-premium.svg") String iconoUrl
    ) {}

    // =========================
    // POST /api/servicios
    // =========================
    @PostMapping
    @Operation(summary = "Crear servicio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServicioAlojamientoDTO.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": 7, \"nombre\": \"Piscina privada\", \"descripcion\": \"Piscina climatizada con vista al mar\" }"
                            ))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Nombre duplicado", content = @Content)
    })
    public ResponseEntity<ServicioAlojamientoDTO> crear(@Valid @RequestBody CrearServicioRequest req) {
        ServicioAlojamientoDTO creado =
                servicioService.crear(req.nombre(), req.descripcion(), req.iconoUrl());
        return ResponseEntity
                .created(URI.create("/api/servicios/" + creado.getId()))
                .body(creado);
    }

    // =========================
    // GET /api/servicios
    // =========================
    @GetMapping
    @Operation(summary = "Listar servicios")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<ServicioAlojamientoDTO>> listar() {
        return ResponseEntity.ok(servicioService.listar());
    }

    // =========================
    // GET /api/servicios/{id}
    // =========================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener servicio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    public ResponseEntity<ServicioAlojamientoDTO> obtenerPorId(
            @Parameter(example = "7") @PathVariable Integer id) {
        return ResponseEntity.ok(servicioService.obtenerPorId(id));
    }

    // =========================
    // GET /api/servicios/buscar?nombre=Wi
    // =========================
    @GetMapping("/buscar")
    @Operation(summary = "Buscar por nombre (exacto)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    public ResponseEntity<ServicioAlojamientoDTO> buscarPorNombre(
            @Parameter(example = "Wi-Fi") @RequestParam String nombre) {
        return ResponseEntity.ok(servicioService.buscarPorNombre(nombre));
    }

    // =========================
    // PUT /api/servicios/{id}
    // =========================
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar servicio")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    public ResponseEntity<ServicioAlojamientoDTO> actualizar(
            @Parameter(example = "7") @PathVariable Integer id,
            @Valid @RequestBody ActualizarServicioRequest req) {
        ServicioAlojamientoDTO actualizado =
                servicioService.actualizar(id, req.nombre(), req.descripcion(), req.iconoUrl());
        return ResponseEntity.ok(actualizado);
    }

    // =========================
    // DELETE /api/servicios/{id}
    // =========================
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar servicio")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(example = "7") @PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // GET /api/servicios/mas-usados
    // =========================
    @GetMapping("/mas-usados")
    @Operation(summary = "Ranking: servicios más utilizados",
            description = "Devuelve el servicio y el total de alojamientos que lo incluyen (orden desc).")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<ServicioUsoDTO>> serviciosMasUsados() {
        return ResponseEntity.ok(servicioService.serviciosMasUtilizados());
    }

    // =========================
    // GET /api/servicios/{id}/alojamientos/count
    // =========================
    @GetMapping("/{id}/alojamientos/count")
    @Operation(summary = "Contar alojamientos que usan el servicio")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Long> contarAlojamientos(
            @Parameter(example = "7") @PathVariable Integer id) {
        return ResponseEntity.ok(servicioService.contarAlojamientosPorServicio(id));
    }
}
