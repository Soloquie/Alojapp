package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa los datos básicos de un alojamiento publicado por un anfitrión.")
public class AlojamientoDTO {

    @Schema(description = "Identificador único del alojamiento", example = "10")
    private Long id;

    @Schema(description = "Título o nombre del alojamiento", example = "Casa de playa en Cartagena")
    private String titulo;

    @Schema(description = "Descripción detallada del alojamiento", example = "Hermosa casa frente al mar con piscina y vista panorámica.")
    private String descripcion;

    @Schema(description = "Ubicación geográfica o ciudad donde se encuentra el alojamiento", example = "Cartagena")
    private String ubicacion;

    @Schema(description = "Precio por noche del alojamiento", example = "350000")
    private double precioPorNoche;

    @Schema(description = "Capacidad máxima de huéspedes que puede alojar", example = "5")
    private int capacidad;

    @Schema(description = "Estado actual del alojamiento (por ejemplo: ACTIVO, BLOQUEADO, INACTIVO)", example = "ACTIVO")
    private String estado;

    @Schema(description = "Identificador del anfitrión propietario del alojamiento", example = "25")
    private Long anfitrionId;

    @Schema(description = "Nombre del anfitrión propietario del alojamiento", example = "Juan Pérez")
    private String anfitrionNombre;

    @Schema(description = "Lista de URLs o nombres de imágenes asociadas al alojamiento")
    private List<String> imagenes;

    @Schema(description = "Promedio de calificación basado en los comentarios recibidos", example = "4.7")
    private double calificacionPromedio;
}
