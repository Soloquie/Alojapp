package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.ComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ReportarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.ComentarioService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comentarios")
@Tag(name = "Comentarios", description = "Gestión de comentarios y calificaciones sobre los alojamientos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final UsuarioRepository usuarioRepository;

    // ======================================================
    // Helpers de autenticación
    // ======================================================
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

    // ======================================================
    // 1) Crear comentario (RN16/RN17/RN7/RN8 validados en Service/DAO)
    // ======================================================
    @PostMapping
    @Operation(
            summary = "Crear comentario",
            description = "Crea un comentario sobre una reserva COMPLETADA del usuario. RN16 y RN17 aplican."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comentario creado"),
            @ApiResponse(responseCode = "400", description = "Regla de negocio o datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "La reserva no pertenece al usuario", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content)
    })
    public ResponseEntity<ComentarioDTO> crear(@Valid @RequestBody CrearComentarioRequest body) {
        Integer userId = currentUserId();
        ComentarioDTO creado = comentarioService.crear(userId, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ======================================================
    // 2) Detalle comentario
    // ======================================================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener comentario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Comentario no encontrado", content = @Content)
    })
    public ResponseEntity<ComentarioDTO> obtener(
            @Parameter(description = "ID del comentario", example = "100") @PathVariable Integer id) {
        return ResponseEntity.ok(comentarioService.obtenerPorId(id));
    }

    // ======================================================
    // 3) Listar por alojamiento (paginado)
    // ======================================================
    @GetMapping("/alojamiento/{alojamientoId}")
    @Operation(summary = "Listar comentarios de un alojamiento (paginado)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<PaginacionResponse<ComentarioDTO>> listarPorAlojamiento(
            @Parameter(description = "ID del alojamiento", example = "15") @PathVariable Integer alojamientoId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamano
    ) {
        return ResponseEntity.ok(comentarioService.listarPorAlojamiento(alojamientoId, pagina, tamano));
    }

    // ======================================================
    // 4) Mis comentarios (usuario autenticado)
    // ======================================================
    @GetMapping("/mios")
    @Operation(summary = "Listar mis comentarios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
    public ResponseEntity<java.util.List<ComentarioDTO>> misComentarios() {
        Integer userId = currentUserId();
        return ResponseEntity.ok(comentarioService.listarPorUsuario(userId));
    }

    // ======================================================
    // 5) Listar comentarios de un anfitrión (paginado)
    // ======================================================
    @GetMapping("/anfitrion/{anfitrionId}")
    @Operation(summary = "Listar comentarios de todos los alojamientos de un anfitrión (paginado)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<PaginacionResponse<ComentarioDTO>> listarPorAnfitrion(
            @Parameter(description = "ID del anfitrión", example = "7") @PathVariable Integer anfitrionId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamano
    ) {
        return ResponseEntity.ok(comentarioService.listarPorAnfitrion(anfitrionId, pagina, tamano));
    }

    // ======================================================
    // 6) Comentarios sin respuesta (anfitrión)
    // ======================================================
    @GetMapping("/anfitrion/{anfitrionId}/sin-respuesta")
    @Operation(summary = "Listar comentarios sin respuesta de un anfitrión")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<java.util.List<ComentarioDTO>> listarSinRespuesta(
            @Parameter(description = "ID del anfitrión", example = "7") @PathVariable Integer anfitrionId
    ) {
        return ResponseEntity.ok(comentarioService.listarSinRespuestaPorAnfitrion(anfitrionId));
    }

    // ======================================================
    // 7) Métricas de un alojamiento
    // ======================================================
    @GetMapping("/alojamiento/{alojamientoId}/stats")
    @Operation(summary = "Métricas de comentarios de un alojamiento (promedio y total)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<Map<String, Object>> statsAlojamiento(
            @Parameter(description = "ID del alojamiento", example = "15") @PathVariable Integer alojamientoId
    ) {
        Double promedio = comentarioService.calificacionPromedio(alojamientoId);
        Long total = comentarioService.contarPorAlojamiento(alojamientoId);
        return ResponseEntity.ok(Map.of(
                "alojamientoId", alojamientoId,
                "promedio", promedio != null ? promedio : 0.0,
                "total", total != null ? total : 0L
        ));
    }

    // ======================================================
    // 8) Mejores comentarios (4-5)
    // ======================================================
    @GetMapping("/alojamiento/{alojamientoId}/top")
    @Operation(summary = "Mejores comentarios de un alojamiento (calificación 4-5)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<java.util.List<ComentarioDTO>> topAlojamiento(
            @Parameter(description = "ID del alojamiento", example = "15") @PathVariable Integer alojamientoId
    ) {
        return ResponseEntity.ok(comentarioService.mejoresComentarios(alojamientoId));
    }

    // ======================================================
    // 9) Actualizar comentario (solo autor)
    // ======================================================
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar comentario (solo autor)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentario actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comentario no encontrado", content = @Content)
    })
    public ResponseEntity<ComentarioDTO> actualizar(
            @Parameter(description = "ID del comentario", example = "100") @PathVariable Integer id,
            @Valid @RequestBody ActualizarComentarioRequest body
    ) {
        Integer userId = currentUserId();
        ComentarioDTO dto = comentarioService.actualizar(userId, id, body);
        return ResponseEntity.ok(dto);
    }

    // ======================================================
    // 10) Eliminar comentario (autor o adminOverride futuro)
    // ======================================================
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar comentario")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comentario eliminado"),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comentario no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del comentario", example = "100") @PathVariable Integer id
    ) {
        Integer userId = currentUserId();
        comentarioService.eliminar(userId, id, false);
        return ResponseEntity.noContent().build();
    }

    // ======================================================
    // 11) Reportar comentario (501 hasta persistencia)
    // ======================================================
    @PostMapping("/{id}/reportes")
    @Operation(summary = "Reportar comentario", description = "Crea un reporte por contenido inapropiado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reporte creado (cuando haya persistencia)"),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comentario no encontrado", content = @Content),
            @ApiResponse(responseCode = "501", description = "No implementado (por ahora)", content = @Content)
    })
    public ResponseEntity<?> reportar(
            @Parameter(description = "ID del comentario", example = "100") @PathVariable Integer id,
            @Valid @RequestBody ReportarComentarioRequest body
    ) {
        Integer userId = currentUserId();
        comentarioService.reportar(userId, id, body);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(Map.of("message", "Reporte de comentario pendiente de implementación"));
    }
}
