package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Entidad que representa a un anfitrión dentro de la plataforma.
 * El anfitrión está asociado a un usuario general y puede tener múltiples alojamientos.
 */

@Entity
@Table(name = "anfitriones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anfitrion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del anfitrión", example = "1")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @Schema(description = "Usuario asociado al anfitrión")
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    @Schema(description = "Número de identificación o documento del anfitrión", example = "1098765432")
    private String documentoIdentidad;

    @Column(nullable = false, length = 20)
    @Schema(description = "Tipo de documento de identificación", example = "CÉDULA")
    private String tipoDocumento;

    @Column(nullable = false)
    @Schema(description = "Fecha de registro del anfitrión en la plataforma", example = "2024-10-01")
    private LocalDate fechaRegistro;

    @Column(length = 255)
    @Schema(description = "Descripción breve del anfitrión o presentación pública", example = "Apasionado por ofrecer experiencias únicas en el Caribe colombiano.")
    private String descripcion;

    @OneToMany(mappedBy = "anfitrion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Lista de alojamientos publicados por el anfitrión")
    private List<Alojamiento> alojamientos;

    @Column(nullable = false)
    @Schema(description = "Indica si el anfitrión está activo o suspendido", example = "true")
    private boolean activo;
}
