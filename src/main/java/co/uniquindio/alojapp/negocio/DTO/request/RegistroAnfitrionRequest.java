package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para registro de anfitrión")
public class RegistroAnfitrionRequest extends RegistroUsuarioRequest {

    @Schema(example = "1995-08-15")
    private LocalDate fechaRegistro; // opcional; si es null se toma hoy

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Schema(description = "Descripción personal del anfitrión",
            example = "Apasionado por el turismo y la hospitalidad en el Caribe colombiano")
    private String descripcionPersonal;

    @Schema(description = "URL de documentos legales", example = "https://ejemplo.com/docs/cedula.pdf")
    private String documentosLegalesUrl;
}