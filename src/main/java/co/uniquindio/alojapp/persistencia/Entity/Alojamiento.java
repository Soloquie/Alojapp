package co.uniquindio.alojapp.persistencia.Entity;

import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoAlojamiento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Column(name = "alojamiento_id")
    @Schema(description = "Identificador único del alojamiento", example = "10")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anfitrion_id", nullable = false)
    @NotNull(message = "El anfitrión es obligatorio")
    @Schema(description = "Anfitrión propietario del alojamiento")
    private Anfitrion anfitrion;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    @Schema(description = "Título del alojamiento", example = "Casa de playa en Cartagena")
    private String titulo;

    @Column(columnDefinition = "TEXT")
    @Size(max = 5000, message = "La descripción no puede exceder 5000 caracteres")
    @Schema(description = "Descripción detallada del alojamiento")
    private String descripcion;

    @Column(length = 100)
    @Schema(description = "Ciudad donde se ubica", example = "Cartagena")
    private String ciudad;

    @Column(length = 255)
    @Schema(description = "Dirección completa", example = "Calle 10 #5-20")
    private String direccion;

    @Column(precision = 10, scale = 7)
    @DecimalMin(value = "-90.0", message = "Latitud inválida")
    @DecimalMax(value = "90.0", message = "Latitud inválida")
    @Schema(description = "Latitud de ubicación", example = "10.3910485")
    private BigDecimal latitud;

    @Column(precision = 10, scale = 7)
    @DecimalMin(value = "-180.0", message = "Longitud inválida")
    @DecimalMax(value = "180.0", message = "Longitud inválida")
    @Schema(description = "Longitud de ubicación", example = "-75.4794257")
    private BigDecimal longitud;

    @Column(name = "precio_noche", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El precio por noche es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio por noche", example = "350000.00")
    private BigDecimal precioNoche;

    @Column(name = "capacidad_maxima", nullable = false)
    @NotNull(message = "La capacidad máxima es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Schema(description = "Capacidad máxima de huéspedes", example = "8")
    private Integer capacidadMaxima;

    @Column(name = "imagen_principal_url", length = 500)
    @Schema(description = "URL de la imagen principal")
    private String imagenPrincipalUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Estado del alojamiento", example = "ACTIVO")
    private EstadoAlojamiento estado;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    @Schema(description = "Fecha de última actualización")
    private LocalDateTime fechaActualizacion;

    // ========= Relaciones =========

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Reserva> reservas = new ArrayList<>();

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Imagen> imagenes = new ArrayList<>();

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Favorito> favoritos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "alojamiento_servicios",
            joinColumns = @JoinColumn(name = "alojamiento_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    @Builder.Default
    private List<ServicioAlojamiento> servicios = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoAlojamiento.ACTIVO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    // Métodos de utilidad
    public Double calcularCalificacionPromedio() {
        if (comentarios == null || comentarios.isEmpty()) {
            return 0.0;
        }
        return comentarios.stream()
                .mapToInt(Comentario::getCalificacion)
                .average()
                .orElse(0.0);
    }
}