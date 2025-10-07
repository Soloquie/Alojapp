package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicios_alojamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioAlojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servicio_id")
    @Schema(description = "Identificador único del servicio", example = "1")
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Schema(description = "Nombre del servicio", example = "Wi-Fi")
    private String nombre;

    @Column(length = 255)
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    @Schema(description = "Descripción del servicio")
    private String descripcion;

    @Column(name = "icono_url", length = 500)
    @Schema(description = "URL del icono del servicio")
    private String iconoUrl;

    @ManyToMany(mappedBy = "servicios")
    @Builder.Default
    private List<Alojamiento> alojamientos = new ArrayList<>();
}