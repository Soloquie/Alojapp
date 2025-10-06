package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa la respuesta a un comentario sobre un alojamiento.")
public class RespuestaComentarioDTO {

    @Schema(description = "Identificador único de la respuesta", example = "200")
    private Long id;

    @Schema(description = "Contenido de la respuesta", example = "Gracias por tu comentario, te esperamos nuevamente.")
    private String contenido;

    @Schema(description = "Fecha y hora de publicación de la respuesta", example = "2025-10-05T18:45:00")
    private LocalDateTime fechaRespuesta;

    @Schema(description = "ID del comentario al que responde", example = "105")
    private Long comentarioId;

    @Schema(description = "Nombre del autor de la respuesta (anfitrión o admin)", example = "Carlos Ramírez")
    private String autorNombre;
}