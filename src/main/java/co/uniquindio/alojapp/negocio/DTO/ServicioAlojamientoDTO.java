package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa un servicio disponible dentro de un alojamiento.")
public class ServicioAlojamientoDTO {

    @Schema(description = "Identificador único del servicio", example = "7")
    private Long id;

    @Schema(description = "Nombre del servicio", example = "Piscina privada")
    private String nombre;

    @Schema(description = "Descripción del servicio", example = "Piscina climatizada con vista al mar")
    private String descripcion;
}
