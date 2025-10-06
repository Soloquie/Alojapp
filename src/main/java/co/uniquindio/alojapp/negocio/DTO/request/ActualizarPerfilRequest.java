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
@Schema(description = "Request para actualizar perfil de usuario")
public class ActualizarPerfilRequest {

    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Schema(description = "Nombre completo", example = "Juan Carlos Pérez")
    private String nombre;

    @Pattern(regexp = "^\\+?[0-9]{10,20}$", message = "Formato de teléfono inválido")
    @Schema(description = "Número de teléfono", example = "+57 3001234567")
    private String telefono;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Schema(description = "Fecha de nacimiento", example = "1995-08-15")
    private LocalDate fechaNacimiento;

    @Schema(description = "URL de foto de perfil", example = "https://ejemplo.com/nueva-foto.jpg")
    private String fotoPerfilUrl;
}