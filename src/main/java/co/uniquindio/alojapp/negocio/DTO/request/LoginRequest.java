package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para inicio de sesión")
public class LoginRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@correo.com")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Contraseña del usuario", example = "MiClave123")
    private String password;
}