package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de usuario sincronizado con BD")
public class UsuarioDTO {

    @Schema(description = "ID del usuario", example = "123")
    private Long id;

    @Schema(description = "Nombre completo", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Email único", example = "juanperez@correo.com")
    private String email;

    @Schema(description = "Teléfono", example = "+57 3001234567")
    private String telefono;

    @Schema(description = "Fecha de nacimiento", example = "1995-08-20")
    private LocalDate fechaNacimiento;

    @Schema(description = "Fecha de registro")
    private LocalDateTime fechaRegistro;

    @Schema(description = "Fecha de última conexión")
    private LocalDateTime fechaUltimaConexion;

    @Schema(description = "Estado de la cuenta", example = "ACTIVO")
    private String estado;

    @Schema(description = "URL de foto de perfil")
    private String fotoPerfilUrl;

    @Schema(description = "Indica si es anfitrión", example = "false")
    private Boolean esAnfitrion;

    @Schema(description = "Indica si es administrador", example = "false")
    private Boolean esAdministrador;
}