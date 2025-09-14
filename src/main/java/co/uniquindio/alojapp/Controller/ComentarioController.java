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
@RequestMapping("/api/comentarios")
@Tag(name = "Comentarios", description = "Gestión de comentarios y calificaciones sobre los alojamientos")
@SecurityRequirement(name = "bearerAuth")
public class ComentarioController {

    // ===== DTOs simulados para ejemplos =====
    static class ComentarioRequest {
        @Schema(example = "Excelente alojamiento, muy limpio y cómodo")
        public String contenido;
        @Schema(example = "5")
        public int calificacion;
    }

    static class ComentarioResponse {
        @Schema(example = "100")
        public String id;
        @Schema(example = "15")
        public String alojamientoId;
        @Schema(example = "usuario@correo.com")
        public String autorEmail;
        @Schema(example = "Excelente alojamiento, muy limpio y cómodo")
        public String contenido;
        @Schema(example = "5")
        public int calificacion;
        @Schema(example = "2025-09-10")
        public String fecha;
    }

    // =========================
    // ENDPOINT 1: POST /api/comentarios/{alojamientoId}
    // =========================
    @PostMapping("/{alojamientoId}")
    @Operation(
            summary = "Publicar comentario en un alojamiento",
            description = "Permite a un huésped dejar un comentario y calificación en un alojamiento después de su estadía"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Comentario creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComentarioResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": \"100\", \"alojamientoId\": \"15\", \"autorEmail\": \"usuario@correo.com\", \"contenido\": \"Excelente alojamiento\", \"calificacion\": 5, \"fecha\": \"2025-09-10\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado")
    })
    public ResponseEntity<ComentarioResponse> crearComentario(
            @Parameter(description = "ID del alojamiento al que se hará el comentario", example = "15")
            @PathVariable String alojamientoId,
            @RequestBody ComentarioRequest datos
    ) {
        ComentarioResponse c = new ComentarioResponse();
        c.id = "100";
        c.alojamientoId = alojamientoId;
        c.autorEmail = "usuario@correo.com";
        c.contenido = datos.contenido;
        c.calificacion = datos.calificacion;
        c.fecha = "2025-09-10";
        return ResponseEntity.status(201).body(c);
    }

    // =========================
    // ENDPOINT 2: GET /api/comentarios/{alojamientoId}
    // =========================
    @GetMapping("/{alojamientoId}")
    @Operation(
            summary = "Listar comentarios de un alojamiento",
            description = "Devuelve todos los comentarios y calificaciones asociados a un alojamiento específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comentarios obtenidos correctamente")
    })
    public ResponseEntity<List<ComentarioResponse>> listarComentariosPorAlojamiento(
            @Parameter(description = "ID del alojamiento", example = "15")
            @PathVariable String alojamientoId
    ) {
        List<ComentarioResponse> lista = new ArrayList<>();
        ComentarioResponse c = new ComentarioResponse();
        c.id = "100";
        c.alojamientoId = alojamientoId;
        c.autorEmail = "usuario@correo.com";
        c.contenido = "Excelente alojamiento, muy limpio y cómodo";
        c.calificacion = 5;
        c.fecha = "2025-09-10";
        lista.add(c);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // ENDPOINT 3: DELETE /api/comentarios/{id}
    // =========================
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar comentario",
            description = "Permite a un administrador o al autor eliminar un comentario existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comentario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Comentario no encontrado")
    })
    public ResponseEntity<Void> eliminarComentario(
            @Parameter(description = "ID del comentario a eliminar", example = "100")
            @PathVariable String id
    ) {
        return ResponseEntity.noContent().build();
    }
}
