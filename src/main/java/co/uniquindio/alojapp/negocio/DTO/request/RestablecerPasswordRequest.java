package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para restablecer contraseña con código")
public class RestablecerPasswordRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Schema(description = "Email del usuario", example = "juan.perez@correo.com")
    private String email;

    @NotBlank(message = "El código es obligatorio")
    @Size(min = 6, max = 10, message = "El código debe tener entre 6 y 10 caracteres")
    @Schema(description = "Código de recuperación", example = "ABC123XYZ")
    private String codigo;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$",
            message = "La contraseña debe incluir al menos una mayúscula y un número")
    @Schema(description = "Nueva contraseña", example = "NuevaClave123")
    private String nuevaPassword;
}