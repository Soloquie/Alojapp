package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.ImagenDTO;
import co.uniquindio.alojapp.negocio.Service.ImagenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/imagenes")
@Tag(name = "Imágenes", description = "Gestión de imágenes de alojamientos (Cloudinary)")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenService imagenService;

    // =========================
    // 1) Listar imágenes de un alojamiento
    // =========================
    @GetMapping("/alojamiento/{alojamientoId}")
    @Operation(summary = "Listar imágenes de un alojamiento",
            description = "Retorna las imágenes ordenadas ascendentemente por 'orden'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<ImagenDTO>> listarPorAlojamiento(
            @Parameter(description = "ID del alojamiento", example = "123")
            @PathVariable Integer alojamientoId) {

        return ResponseEntity.ok(imagenService.listarPorAlojamiento(alojamientoId));
    }

    // =========================
    // 2) Subir UNA imagen
    // =========================
    @PostMapping(
            path = "/alojamiento/{alojamientoId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Subir imagen a un alojamiento",
            description = "Sube la imagen a Cloudinary y la registra en BD (RN3: máx 10, orden secuencial)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Imagen creada",
                    content = @Content(schema = @Schema(implementation = ImagenDTO.class))),
            @ApiResponse(responseCode = "400", description = "Archivo inválido o RN3 incumplida"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<ImagenDTO> subirImagen(
            @PathVariable Integer alojamientoId,
            @Parameter(description = "Archivo de imagen") @RequestPart("file") MultipartFile file,
            @Parameter(description = "Descripción opcional") @RequestPart(value = "descripcion", required = false) String descripcion
    ) throws IOException {
        ImagenDTO dto = imagenService.subirImagen(alojamientoId, file, descripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // =========================
    // 3) Subir VARIAS imágenes (batch)
    // =========================
    @PostMapping(
            path = "/alojamiento/{alojamientoId}/batch",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Subir varias imágenes a un alojamiento",
            description = "Sube todas a Cloudinary; si una falla se hace rollback de las cargadas. Luego registra en BD (RN3).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Imágenes creadas",
                    content = @Content(schema = @Schema(implementation = ImagenDTO.class))),
            @ApiResponse(responseCode = "400", description = "Archivos inválidos o RN3 incumplida"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<List<ImagenDTO>> subirImagenes(
            @PathVariable Integer alojamientoId,
            @Parameter(description = "Archivos de imagen") @RequestPart("files") @NotEmpty List<MultipartFile> files,
            @Parameter(description = "Descripciones (mismo tamaño que files)") @RequestPart(value = "descripciones", required = false) List<String> descripciones
    ) throws IOException {
        List<ImagenDTO> creadas = imagenService.subirImagenes(alojamientoId, files, descripciones);
        return ResponseEntity.status(HttpStatus.CREATED).body(creadas);
    }

    // =========================
    // 4) Reordenar
    // =========================
    @PutMapping("/alojamiento/{alojamientoId}/orden")
    @Operation(summary = "Reordenar imágenes",
            description = "Recibe la lista de IDs de imágenes en el orden deseado (1..N).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden actualizado",
                    content = @Content(schema = @Schema(implementation = ImagenDTO.class))),
            @ApiResponse(responseCode = "400", description = "IDs no pertenecen al alojamiento o lista inválida"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<List<ImagenDTO>> reordenar(
            @PathVariable Integer alojamientoId,
            @RequestBody List<Integer> idsEnOrden
    ) {
        return ResponseEntity.ok(imagenService.reordenar(alojamientoId, idsEnOrden));
    }

    // =========================
    // 5) Eliminar una imagen
    // =========================
    @DeleteMapping("/{imagenId}")
    @Operation(summary = "Eliminar imagen",
            description = "Borra del Cloudinary y luego de BD. No permite dejar el alojamiento con 0 imágenes (RN3).")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminada"),
            @ApiResponse(responseCode = "400", description = "RN3 impediría dejar 0 imágenes"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Integer imagenId) throws IOException {
        imagenService.eliminarImagen(imagenId);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // 6) Reemplazar TODAS
    // =========================
    @PutMapping(
            path = "/alojamiento/{alojamientoId}/reemplazar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Reemplazar todas las imágenes",
            description = "Crea nuevas (al menos 1) y luego elimina las anteriores (Cloud+BD).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reemplazo OK",
                    content = @Content(schema = @Schema(implementation = ImagenDTO.class))),
            @ApiResponse(responseCode = "400", description = "Debes proporcionar al menos una imagen o RN3"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<List<ImagenDTO>> reemplazarTodas(
            @PathVariable Integer alojamientoId,
            @RequestPart("files") @NotEmpty List<MultipartFile> files,
            @RequestPart(value = "descripciones", required = false) List<String> descripciones
    ) throws IOException {
        List<ImagenDTO> result = imagenService.reemplazarTodas(alojamientoId, files, descripciones);
        return ResponseEntity.ok(result);
    }
}
