package co.uniquindio.Alojapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Comentarios", description = "Operaciones relacionadas con comentarios y calificaciones de alojamientos")
public class ComentarioController {

    // =========================
    // ENDPOINT 1: POST /api/alojamientos/{id}/comentarios
    // =========================
    @PostMapping("/alojamientos/{id}/comentarios")
    @Operation(
            summary = "Crear comentario sobre un alojamiento",
            description = "Permite a un huésped dejar un comentario y calificación sobre un alojamiento reservado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> crearComentario(
            @Parameter(description = "ID del alojamiento comentado", example = "15") @PathVariable String id,
            @Parameter(description = "Contenido del comentario", example = "Excelente alojamiento, muy limpio y cómodo") @RequestParam String contenido,
            @Parameter(description = "Calificación de 1 a 5 estrellas", example = "5") @RequestParam int calificacion
    ) {
        return ResponseEntity.status(201).body("Comentario creado para alojamiento " + id);
    }

    // =========================
    // ENDPOINT 2: GET /api/alojamientos/{id}/comentarios
    // =========================
    @GetMapping("/alojamientos/{id}/comentarios")
    @Operation(
            summary = "Listar comentarios de un alojamiento",
            description = "Devuelve todos los comentarios realizados sobre un alojamiento específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentarios obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> listarComentarios(
            @Parameter(description = "ID del alojamiento", example = "15") @PathVariable String id
    ) {
        return ResponseEntity.ok("Lista de comentarios para el alojamiento " + id);
    }

    // =========================
    // ENDPOINT 3: POST /api/comentarios/{id}/respuesta
    // =========================
    @PostMapping("/comentarios/{id}/respuesta")
    @Operation(
            summary = "Responder a un comentario",
            description = "Permite al anfitrión responder a un comentario hecho por un huésped"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Respuesta publicada correctamente"),
            @ApiResponse(responseCode = "404", description = "Comentario no encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> responderComentario(
            @Parameter(description = "ID del comentario a responder", example = "45") @PathVariable String id,
            @Parameter(description = "Texto de la respuesta del anfitrión", example = "Gracias por hospedarte con nosotros") @RequestParam String respuesta
    ) {
        return ResponseEntity.status(201).body("Respuesta enviada para comentario " + id);
    }
}
