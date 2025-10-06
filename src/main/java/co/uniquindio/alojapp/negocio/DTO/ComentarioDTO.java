package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de comentario sincronizado con BD")
public class ComentarioDTO {

    @Schema(description = "ID del comentario", example = "100")
    private Long id;

    @Schema(description = "ID de la reserva", example = "25")
    private Long reservaId;

    @Schema(description = "ID del usuario", example = "15")
    private Long usuarioId;

    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String usuarioNombre;

    @Schema(description = "ID del alojamiento", example = "10")
    private Long alojamientoId;

    @Schema(description = "Calificación (1-5)", example = "5")
    private Integer calificacion;

    @Schema(description = "Texto del comentario")
    private String comentarioTexto;

    @Schema(description = "Fecha del comentario")
    private LocalDateTime fechaComentario;

    @Schema(description = "Lista de respuestas del anfitrión")
    private List<RespuestaComentarioDTO> respuestas;
}