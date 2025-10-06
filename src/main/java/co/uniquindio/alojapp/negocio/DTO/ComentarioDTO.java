package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {

    @Schema(description = "Identificador único del comentario", example = "100")
    private String id;

    @Schema(description = "Identificador del alojamiento al que pertenece el comentario", example = "15")
    private String alojamientoId;

    @Schema(description = "Correo electrónico del autor del comentario", example = "usuario@correo.com")
    private String autorEmail;

    @Schema(description = "Contenido del comentario escrito por el usuario", example = "Excelente alojamiento, muy limpio y cómodo.")
    private String contenido;

    @Schema(description = "Calificación otorgada por el huésped al alojamiento (1-5)", example = "5")
    private int calificacion;

    @Schema(description = "Fecha y hora en que se realizó el comentario", example = "2025-09-10T14:30:00")
    private LocalDateTime fechaPublicacion;

    @Schema(description = "Indica si el comentario ha sido reportado por otros usuarios", example = "false")
    private boolean reportado;
}
