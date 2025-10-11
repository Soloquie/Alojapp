package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request para actualizar un comentario")
public class ActualizarComentarioRequest {

    @Schema(description = "Nueva calificación (1-5)", example = "4")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer calificacion;

    @Schema(description = "Nuevo texto (máx 500 chars)", example = "Muy buen lugar, volvería.")
    @Size(max = 500, message = "El comentario no puede exceder 500 caracteres")
    private String comentarioTexto;
}
