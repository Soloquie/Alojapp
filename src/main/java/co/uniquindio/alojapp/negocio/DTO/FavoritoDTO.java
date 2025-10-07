package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa un alojamiento marcado como favorito por un usuario.")
public class FavoritoDTO {

    @Schema(description = "Identificador único del favorito", example = "102")
    private Integer id;

    @Schema(description = "ID del usuario que marcó el alojamiento como favorito", example = "15")
    private Long usuarioId;

    @Schema(description = "ID del alojamiento marcado como favorito", example = "8")
    private Long alojamientoId;

    @Schema(description = "Nombre del alojamiento favorito", example = "Cabaña en el bosque")
    private String nombreAlojamiento;
}
