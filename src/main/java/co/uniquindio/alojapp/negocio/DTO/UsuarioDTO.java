package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    @Schema(description = "Identificador único del usuario", example = "123")
    private String id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario (debe ser único)", example = "juanperez@correo.com")
    private String email;

    @Schema(description = "Contraseña del usuario (mínimo 8 caracteres, incluir mayúsculas y números)",
            example = "ClaveSegura123", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @Schema(description = "Número de teléfono de contacto", example = "+57 3001234567")
    private String telefono;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1995-08-20")
    private LocalDate fechaNacimiento;

    @Schema(description = "Rol del usuario en la plataforma", example = "HUESPED")
    private String rol;

    @Schema(description = "Estado de la cuenta del usuario", example = "ACTIVO")
    private String estado;
}
