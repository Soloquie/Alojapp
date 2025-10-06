package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para responder a un comentario")
public class ResponderComentarioRequest {

    @NotBlank(message = "La respuesta no puede estar vacía")
    @Size(min = 10, max = 1000, message = "La respuesta debe tener entre 10 y 1000 caracteres")
    @Schema(description = "Texto de la respuesta",
            example = "Muchas gracias por tu comentario, esperamos verte pronto")
    private String respuestaTexto;
}