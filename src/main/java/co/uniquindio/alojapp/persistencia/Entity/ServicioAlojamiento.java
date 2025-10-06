package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "servicios_alojamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa un servicio disponible en los alojamientos (ej. Wi-Fi, Piscina, Parqueadero).")
public class ServicioAlojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del servicio", example = "1")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "Nombre del servicio ofrecido", example = "Wi-Fi")
    private String nombre;

    @Column(length = 255)
    @Schema(description = "Descripción opcional del servicio", example = "Conexión a internet de alta velocidad disponible en todo el alojamiento.")
    private String descripcion;

    // ========= Relaciones =========

    @ManyToMany(mappedBy = "servicios")
    @Schema(description = "Lista de alojamientos que ofrecen este servicio.")
    private List<Alojamiento> alojamientos;
}
