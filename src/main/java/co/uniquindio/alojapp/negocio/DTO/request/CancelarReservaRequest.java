package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para cancelar una reserva")
public class CancelarReservaRequest {

    @NotBlank(message = "El motivo de cancelación es obligatorio")
    @Size(min = 10, max = 500, message = "El motivo debe tener entre 10 y 500 caracteres")
    @Schema(description = "Motivo de la cancelación",
            example = "Cambio de planes por motivos personales")
    private String motivoCancelacion;
}