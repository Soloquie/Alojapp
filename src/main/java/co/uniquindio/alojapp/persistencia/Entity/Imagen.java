package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la imagen", example = "200")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "URL de la imagen del alojamiento", example = "https://miapp.com/imagenes/alojamiento1.jpg")
    private String url;

    // ========= Relaciones =========

    @ManyToOne
    @JoinColumn(name = "alojamiento_id", nullable = false)
    @Schema(description = "Alojamiento al que pertenece la imagen")
    private Alojamiento alojamiento;
}
