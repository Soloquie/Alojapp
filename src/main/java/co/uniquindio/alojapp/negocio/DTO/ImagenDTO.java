package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa una imagen asociada a un alojamiento")
public class ImagenDTO {

    @Schema(description = "Identificador único de la imagen", example = "501")
    private Integer id;

    @Schema(description = "URL pública o ruta de la imagen almacenada", example = "https://alojapp.com/images/alojamientos/501.jpg")
    private String url;

    @Schema(description = "Texto alternativo o descripción breve de la imagen", example = "Vista frontal de la casa")
    private String descripcion;

    @Schema(description = "ID del alojamiento al que pertenece la imagen", example = "15")
    private Long alojamientoId;
}