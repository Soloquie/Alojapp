package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CambiarPasswordRequest {
    @Schema(example = "ClaveActual1")
    @NotBlank
    private String passwordActual;

    @Schema(example = "ClaveNueva2")
    @NotBlank
    private String nuevaPassword;
}
