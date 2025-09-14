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
@RequestMapping("/api/alojamientos")
@Tag(name = "Alojamientos", description = "Gestión de publicaciones de alojamientos")
@SecurityRequirement(name = "bearerAuth") // requiere JWT
public class AlojamientoController {

    // ===== DTOs simulados para ejemplos =====
    static class AlojamientoRequest {
        @Schema(example = "Casa de playa en Cartagena")
        public String titulo;
        @Schema(example = "Hermosa casa frente al mar con piscina")
        public String descripcion;
        @Schema(example = "Cartagena")
        public String ubicacion;
        @Schema(example = "350000")
        public double precioPorNoche;
    }

    static class AlojamientoResponse {
        @Schema(example = "10")
        public String id;
        @Schema(example = "Casa de playa en Cartagena")
        public String titulo;
        @Schema(example = "Hermosa casa frente al mar con piscina")
        public String descripcion;
        @Schema(example = "Cartagena")
        public String ubicacion;
        @Schema(example = "350000")
        public double precioPorNoche;
        @Schema(example = "Disponible")
        public String estado;
    }

    // =========================
    // ENDPOINT 1: POST /api/alojamientos
    // =========================
    @PostMapping
    @Operation(
            summary = "Crear nuevo alojamiento",
            description = "Permite a un anfitrión publicar un nuevo alojamiento en la plataforma"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Alojamiento creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AlojamientoResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": \"10\", \"titulo\": \"Casa de playa en Cartagena\", \"descripcion\": \"Hermosa casa frente al mar con piscina\", \"ubicacion\": \"Cartagena\", \"precioPorNoche\": 350000, \"estado\": \"Disponible\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<AlojamientoResponse> crearAlojamiento(
            @RequestBody AlojamientoRequest datos
    ) {
        AlojamientoResponse r = new AlojamientoResponse();
        r.id = "10";
        r.titulo = datos.titulo;
        r.descripcion = datos.descripcion;
        r.ubicacion = datos.ubicacion;
        r.precioPorNoche = datos.precioPorNoche;
        r.estado = "Disponible";
        return ResponseEntity.status(201).body(r);
    }

    // =========================
    // ENDPOINT 2: GET /api/alojamientos
    // =========================
    @GetMapping
    @Operation(
            summary = "Listar alojamientos publicados",
            description = "Obtiene todos los alojamientos publicados en la plataforma"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    public ResponseEntity<List<AlojamientoResponse>> listarAlojamientos() {
        List<AlojamientoResponse> lista = new ArrayList<>();
        AlojamientoResponse r = new AlojamientoResponse();
        r.id = "10";
        r.titulo = "Casa de playa en Cartagena";
        r.descripcion = "Hermosa casa frente al mar con piscina";
        r.ubicacion = "Cartagena";
        r.precioPorNoche = 350000;
        r.estado = "Disponible";
        lista.add(r);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // ENDPOINT 3: GET /api/alojamientos/{id}
    // =========================
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener alojamiento por ID",
            description = "Obtiene el detalle completo de un alojamiento específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alojamiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado")
    })
    public ResponseEntity<AlojamientoResponse> obtenerAlojamiento(
            @Parameter(description = "ID del alojamiento a buscar", example = "10")
            @PathVariable String id
    ) {
        AlojamientoResponse r = new AlojamientoResponse();
        r.id = id;
        r.titulo = "Casa de playa en Cartagena";
        r.descripcion = "Hermosa casa frente al mar con piscina";
        r.ubicacion = "Cartagena";
        r.precioPorNoche = 350000;
        r.estado = "Disponible";
        return ResponseEntity.ok(r);
    }

    // =========================
    // ENDPOINT 4: PUT /api/alojamientos/{id}
    // =========================
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar alojamiento",
            description = "Permite modificar los datos de un alojamiento existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alojamiento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado")
    })
    public ResponseEntity<String> actualizarAlojamiento(
            @Parameter(description = "ID del alojamiento a actualizar", example = "10") @PathVariable String id,
            @RequestBody AlojamientoRequest datos
    ) {
        return ResponseEntity.ok("Alojamiento " + id + " actualizado correctamente");
    }

    // =========================
    // ENDPOINT 5: DELETE /api/alojamientos/{id}
    // =========================
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar alojamiento",
            description = "Permite eliminar un alojamiento publicado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alojamiento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado")
    })
    public ResponseEntity<Void> eliminarAlojamiento(
            @Parameter(description = "ID del alojamiento a eliminar", example = "10") @PathVariable String id
    ) {
        return ResponseEntity.noContent().build();
    }
}
