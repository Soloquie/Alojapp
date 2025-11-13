package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.AlojamientoDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.request.BuscarAlojamientosRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.AlojamientoService;
import co.uniquindio.alojapp.negocio.Service.ImagenService;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import co.uniquindio.alojapp.seguridad.SecurityUtils;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
    private final UsuarioRepository usuarioRepository;
    private final ImagenService imagenService;


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

    @PutMapping("/anfitrion/mis-alojamientos/{alojamientoId}")
    @Operation(summary = "Actualizar alojamiento (anfitrión) - solo dueño")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alojamiento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado o no pertenece al anfitrión", content = @Content)
    })
    public ResponseEntity<AlojamientoDTO> actualizarMiAlojamiento(
            @PathVariable Integer alojamientoId,
            @Valid @RequestBody ActualizarAlojamientoRequest request
    ) {
        Integer usuarioId = currentUserId();
        AlojamientoDTO dto = alojamientoService.actualizarDeUsuario(usuarioId, alojamientoId, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/anfitrion/mis-alojamientos/{alojamientoId}")
    @Operation(summary = "Eliminar (soft) alojamiento (anfitrión) - valida reservas futuras")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alojamiento eliminado"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado o no pertenece al anfitrión", content = @Content),
            @ApiResponse(responseCode = "409", description = "Tiene reservas futuras", content = @Content)
    })
    public ResponseEntity<Void> eliminarMiAlojamiento(@PathVariable Integer alojamientoId) {
        Integer userId = currentUserId();
        boolean ok = alojamientoService.eliminarDeUsuario(userId, alojamientoId);
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

    private Integer currentUserId() {
        String email = SecurityUtils.getEmailActual().orElse(null);
        if (!StringUtils.hasText(email)) {
            // El EntryPoint/JwtAccessDenied se encargan del 401; aquí dejamos una señal clara
            throw new RecursoNoEncontradoException("Usuario autenticado no encontrado");
        }
        return usuarioRepository.findByEmailIgnoreCase(email)
                .map(Usuario::getId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado por email"));
    }



    @PostMapping(
            value = "/anfitriones/{anfitrionId}/alojamientos",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Crear nuevo alojamiento (anfitrión) con imágenes (multipart)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alojamiento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Anfitrión no encontrado", content = @Content)
    })
    public ResponseEntity<AlojamientoDTO> crearAlojamientoMultipart(
            @Parameter(description = "ID del anfitrión", example = "2")
            @PathVariable Integer anfitrionId,
            @Valid @RequestPart("request") CrearAlojamientoRequest request,
            @RequestPart(value = "imagenes", required = false) List<MultipartFile> imagenes,
            @RequestPart(value = "descripciones", required = false) List<String> descripciones
    ) {
        request.setImagenPrincipalUrl("xd");
        AlojamientoDTO dto = alojamientoService.crear(currentUserId(), request);

        // 2) Si enviaron imágenes, súbelas a Cloudinary y persístelas
        if (imagenes != null && !imagenes.isEmpty()) {
            try {
                var creadas = imagenService.subirImagenes(dto.getId(), imagenes, descripciones);

                if (!creadas.isEmpty()) {
                    alojamientoService.actualizarPortada(dto.getId(), creadas.get(0).getUrl());
                }

                // Refrescar el DTO con todo (incluidas imágenes y portada)
                dto = alojamientoService.obtenerPorId(dto.getId());
            } catch (Exception ex) {
                // Si falla la subida, puedes:
                // a) eliminar el alojamiento para no violar RN3 (mínimo 1 imagen), o
                // b) dejarlo creado sin imágenes y que el host suba luego (menos “atómico”, pero más benigno)
                // Aquí te dejo la opción (a) comentada, úsala si tu RN3 lo exige estrictamente:

                alojamientoService.eliminar(anfitrionId ,dto.getId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudieron subir las imágenes", ex);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


}
