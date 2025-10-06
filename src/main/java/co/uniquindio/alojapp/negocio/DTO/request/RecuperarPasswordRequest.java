package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para recuperación de contraseña")
public class RecuperarPasswordRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Schema(description = "Email del usuario", example = "juan.perez@correo.com")
    private String email;
}