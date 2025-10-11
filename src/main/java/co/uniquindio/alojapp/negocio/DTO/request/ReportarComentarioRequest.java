package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request para reportar un comentario")
public class ReportarComentarioRequest {

    @NotBlank(message = "El motivo del reporte es obligatorio")
    @Schema(description = "Motivo del reporte", example = "El comentario contiene lenguaje ofensivo.")
    private String motivo;
}
