package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa a un administrador del sistema.")
public class AdministradorDTO {

    @Schema(description = "Identificador único del administrador", example = "1")
    private Integer id;

    @Schema(description = "Nombre completo del administrador", example = "Carlos Gómez")
    private String nombre;

    @Schema(description = "Correo electrónico del administrador", example = "admin@alojapp.com")
    private String email;

    @Schema(description = "Teléfono de contacto del administrador", example = "+57 3019876543")
    private String telefono;

    @Schema(description = "Estado del administrador", example = "ACTIVO")
    private String estado;

    @Schema(description = "Nivel de acceso del administrador")
    private String nivelAcceso;

   @Schema(description = "Permisos del administrador")
    private String permisosJson;

   @Schema(description = "Fecha de ser administrador")
    private LocalDateTime fechaAsignacion;
}
