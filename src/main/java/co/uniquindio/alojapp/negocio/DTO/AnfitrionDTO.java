package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa la información pública de un anfitrión.")
public class AnfitrionDTO {

    @Schema(description = "Identificador único del anfitrión", example = "12")
    private Long id;

    @Schema(description = "Nombre completo del anfitrión", example = "María López")
    private String nombre;

    @Schema(description = "Correo electrónico del anfitrión", example = "maria.lopez@correo.com")
    private String email;

    @Schema(description = "Número de teléfono del anfitrión", example = "+57 3125556677")
    private String telefono;

    @Schema(description = "Cantidad de alojamientos publicados por el anfitrión", example = "5")
    private int cantidadAlojamientos;

    @Schema(description = "Calificación promedio de los alojamientos del anfitrión", example = "4.8")
    private double calificacionPromedio;
}
