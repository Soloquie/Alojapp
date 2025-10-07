package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para crear un comentario sobre un alojamiento")
public class CrearComentarioRequest {

    @NotNull(message = "El ID de la reserva es obligatorio")
    @Schema(description = "ID de la reserva completada", example = "25")
    private Integer reservaId;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    @Schema(description = "Calificación del alojamiento (1-5)", example = "5")
    private Integer calificacion;

    @Size(max = 500, message = "El comentario no puede exceder 500 caracteres")
    @Schema(description = "Texto del comentario",
            example = "Excelente alojamiento, muy limpio y cómodo")
    private String comentarioTexto;
}