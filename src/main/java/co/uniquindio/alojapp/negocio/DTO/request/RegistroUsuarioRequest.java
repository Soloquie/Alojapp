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
@Schema(description = "Request para registro de nuevo usuario")
public class RegistroUsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;@NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    @Schema(description = "Correo electrónico único", example = "juan.perez@correo.com")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$",
            message = "La contraseña debe incluir al menos una mayúscula y un número")
    @Schema(description = "Contraseña del usuario", example = "MiClave123")
    private String password;

    @Pattern(regexp = "^\\+?[0-9]{10,20}$", message = "Formato de teléfono inválido")
    @Schema(description = "Número de teléfono", example = "+57 3001234567")
    private String telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Schema(description = "Fecha de nacimiento", example = "1995-08-15")
    private LocalDate fechaNacimiento;

    @Schema(description = "URL de foto de perfil", example = "https://ejemplo.com/foto.jpg")
    private String fotoPerfilUrl;
}