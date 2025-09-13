package co.uniquindio.Alojapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alojamientos")
@Tag(name = "Alojamientos", description = "Operaciones relacionadas con la gestión de alojamientos")
public class AlojamientoController {

    // =========================
    // ENDPOINT 1: POST /api/alojamientos
    // =========================
    @PostMapping
    @Operation(
            summary = "Crear un nuevo alojamiento",
            description = "Permite a un anfitrión registrar un nuevo alojamiento en la plataforma"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alojamiento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> crearAlojamiento(
            @Parameter(description = "Título del alojamiento", example = "Casa en la playa") @RequestParam String titulo,
            @Parameter(description = "Ciudad donde se ubica el alojamiento", example = "Cartagena") @RequestParam String ciudad,
            @Parameter(description = "Precio por noche", example = "150000") @RequestParam double precio
    ) {
        return ResponseEntity.status(201).body("Alojamiento creado: " + titulo);
    }

    // =========================
    // ENDPOINT 2: GET /api/alojamientos
    // =========================
    @GetMapping
    @Operation(
            summary = "Listar alojamientos disponibles",
            description = "Obtiene una lista de alojamientos disponibles, opcionalmente filtrados por ciudad o precio"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> listarAlojamientos(
            @Parameter(description = "Ciudad para filtrar resultados", example = "Medellín") @RequestParam(required = false) String ciudad,
            @Parameter(description = "Precio máximo", example = "200000") @RequestParam(required = false) Double precioMax
    ) {
        return ResponseEntity.ok("Lista de alojamientos (filtros aplicados si existen)");
    }

    // =========================
    // ENDPOINT 3: GET /api/alojamientos/{id}
    // =========================
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener detalle de un alojamiento",
            description = "Muestra la información completa de un alojamiento según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alojamiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> obtenerAlojamiento(
            @Parameter(description = "ID del alojamiento a consultar", example = "10") @PathVariable String id
    ) {
        return ResponseEntity.ok("Detalle del alojamiento con ID: " + id);
    }

    // =========================
    // ENDPOINT 4: PUT /api/alojamientos/{id}
    // =========================
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un alojamiento existente",
            description = "Permite modificar los datos de un alojamiento publicado por el anfitrión"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alojamiento actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> actualizarAlojamiento(
            @Parameter(description = "ID del alojamiento a actualizar", example = "10") @PathVariable String id,
            @Parameter(description = "Nuevo título del alojamiento", example = "Apartamento remodelado") @RequestParam(required = false) String titulo
    ) {
        return ResponseEntity.ok("Alojamiento actualizado: " + id);
    }

    // =========================
    // ENDPOINT 5: DELETE /api/alojamientos/{id}
    // =========================
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un alojamiento",
            description = "Elimina un alojamiento publicado por el anfitrión (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alojamiento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alojamiento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarAlojamiento(
            @Parameter(description = "ID del alojamiento a eliminar", example = "10") @PathVariable String id
    ) {
        return ResponseEntity.noContent().build();
    }
}
