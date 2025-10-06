package co.uniquindio.alojapp.persistencia.Entity;

import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoAlojamiento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "alojamientos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del alojamiento", example = "10")
    private Long id;

    @Column(nullable = false, length = 150)
    @Schema(description = "Título del alojamiento", example = "Casa de playa en Cartagena")
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Descripción detallada del alojamiento", example = "Hermosa casa frente al mar con piscina privada y acceso directo a la playa")
    private String descripcion;

    @Column(nullable = false, length = 100)
    @Schema(description = "Ubicación principal del alojamiento", example = "Cartagena")
    private String ubicacion;

    @Column(nullable = false)
    @Schema(description = "Capacidad máxima de huéspedes", example = "8")
    private Integer capacidad;

    @Column(nullable = false)
    @Schema(description = "Precio por noche del alojamiento", example = "350000")
    private Double precioPorNoche;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Estado actual del alojamiento", example = "DISPONIBLE")
    private EstadoAlojamiento estado;

    // ========= Relaciones =========

    @ManyToOne
    @JoinColumn(name = "anfitrion_id", nullable = false)
    @Schema(description = "Usuario que publica el alojamiento (anfitrión)")
    private Usuario anfitrion;

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes;
}
