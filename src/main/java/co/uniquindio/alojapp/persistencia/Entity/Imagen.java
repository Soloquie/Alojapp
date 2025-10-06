package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "imagenes_alojamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imagen_id")
    @Schema(description = "Identificador único de la imagen", example = "200")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alojamiento_id", nullable = false)
    @NotNull(message = "El alojamiento es obligatorio")
    @Schema(description = "Alojamiento al que pertenece la imagen")
    private Alojamiento alojamiento;

    @Column(name = "url_imagen", nullable = false, length = 500)
    @NotBlank(message = "La URL es obligatoria")
    @Schema(description = "URL de la imagen", example = "https://miapp.com/imagenes/alojamiento1.jpg")
    private String urlImagen;

    @Column(nullable = false)
    @NotNull(message = "El orden es obligatorio")
    @Min(value = 1, message = "El orden debe ser al menos 1")
    @Schema(description = "Orden de visualización", example = "1")
    private Integer orden;

    @Column(length = 255)
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    @Schema(description = "Descripción de la imagen")
    private String descripcion;
}