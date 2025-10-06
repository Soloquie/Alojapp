package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de código de recuperación")
public class CodigoRecuperacionDTO {

    @Schema(description = "ID del código", example = "1")
    private Long id;

    @Schema(description = "ID del usuario", example = "123")
    private Long usuarioId;

    @Schema(description = "Código generado", example = "ABC123XYZ")
    private String codigo;

    @Schema(description = "Fecha de expiración")
    private LocalDateTime fechaExpiracion;

    @Schema(description = "Indica si fue usado", example = "false")
    private Boolean usado;

    @Schema(description = "Indica si es válido", example = "true")
    private Boolean esValido;
}