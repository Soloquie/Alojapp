package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.FavoritoDTO;
import co.uniquindio.alojapp.negocio.Service.FavoritoService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favoritos")
@Tag(name = "Favoritos", description = "Gestión de alojamientos favoritos de los usuarios")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;
    private final UsuarioRepository usuarioRepository;

    // === Helper para sacar el userId del JWT (ajusta a tu helper si ya lo tienes centralizado)
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

    // =========================
    // 1) Agregar a favoritos
    // =========================
    @PostMapping("/{alojamientoId}")
    @Operation(summary = "Agregar alojamiento a mis favoritos")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agregado a favoritos",
                    content = @Content(schema = @Schema(implementation = FavoritoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida / ya estaba en favoritos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<FavoritoDTO> agregar(
            Authentication auth,
            @Parameter(description = "ID del alojamiento a marcar", example = "123")
            @PathVariable Integer alojamientoId
    ) {
        Integer userId = currentUserId();
        FavoritoDTO dto = favoritoService.agregar(userId, alojamientoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // =========================
    // 2) Eliminar de favoritos
    // =========================
    @DeleteMapping("/{alojamientoId}")
    @Operation(summary = "Eliminar alojamiento de mis favoritos")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "No estaba en favoritos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<Void> eliminar(
            Authentication auth,
            @Parameter(description = "ID del alojamiento a quitar", example = "123")
            @PathVariable Integer alojamientoId
    ) {
        Integer userId = currentUserId();
        favoritoService.eliminar(userId, alojamientoId);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // 3) Mis favoritos
    // =========================
    @GetMapping("/mios")
    @Operation(summary = "Listar mis alojamientos favoritos (más recientes primero)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<FavoritoDTO>> misFavoritos(Authentication auth) {
        Integer userId = currentUserId();
        return ResponseEntity.ok(favoritoService.listarPorUsuario(userId));
    }

    // =========================
    // 4) ¿Es favorito?
    // =========================
    @GetMapping("/mios/check")
    @Operation(summary = "Verificar si un alojamiento está en mis favoritos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<Map<String, Object>> esFavorito(
            Authentication auth,
            @Parameter(description = "ID del alojamiento", example = "123")
            @RequestParam Integer alojamientoId
    ) {
        Integer userId = currentUserId();
        boolean favorito = favoritoService.esFavorito(userId, alojamientoId);
        return ResponseEntity.ok(Map.of("alojamientoId", alojamientoId, "favorito", favorito));
    }

    // =========================
    // 5) Conteo de favoritos de un alojamiento
    // =========================
    @GetMapping("/alojamientos/{alojamientoId}/count")
    @Operation(summary = "Contar cuántos usuarios marcaron como favorito un alojamiento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<Map<String, Object>> contar(
            @Parameter(description = "ID del alojamiento", example = "123")
            @PathVariable Integer alojamientoId
    ) {
        Long total = favoritoService.contarFavoritosDeAlojamiento(alojamientoId);
        return ResponseEntity.ok(Map.of("alojamientoId", alojamientoId, "total", total));
    }

    // =========================
    // 6) Top de alojamientos más favoritos
    // =========================
    @GetMapping("/top")
    @Operation(summary = "Ranking global de alojamientos más marcados como favoritos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<FavoritoService.MasFavoritosItem>> top(
            @Parameter(description = "Límite de resultados (1..50). Por defecto 10", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        return ResponseEntity.ok(favoritoService.topMasFavoritos(limit == null ? 10 : limit));
    }

    @GetMapping("/ping")
    public String ping() { return "pong"; }

    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNoResource(NoResourceFoundException ex) {
        Map<String,Object> body = Map.of(
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(404).body(body);
    }

    // Opcional: si tienes activado throw-exception-if-no-handler-found
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNoHandler(NoHandlerFoundException ex) {
        Map<String,Object> body = Map.of(
                "status", 404,
                "error", "Not Found",
                "message", ex.getRequestURL() + " not mapped"
        );
        return ResponseEntity.status(404).body(body);
    }


}
