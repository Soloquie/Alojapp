package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "anfitrion_id")
    @Schema(description = "Identificador único del anfitrión", example = "1")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "Usuario asociado al anfitrión")
    private Usuario usuario;

    @Column(name = "descripcion_personal", columnDefinition = "TEXT")
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Schema(description = "Descripción personal del anfitrión")
    private String descripcionPersonal;

    @Column(name = "documentos_legales_url", length = 500)
    @Schema(description = "URL de documentos legales")
    private String documentosLegalesUrl;

    @Column(name = "fecha_registro_anfitrion", nullable = false)
    @Schema(description = "Fecha de registro como anfitrión")
    private LocalDateTime fechaRegistroAnfitrion;

    @Column(nullable = false)
    @Schema(description = "Indica si el anfitrión está verificado", example = "false")
    private Boolean verificado = false;

    @OneToMany(mappedBy = "anfitrion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alojamiento> alojamientos;

    @OneToMany(mappedBy = "anfitrion", cascade = CascadeType.ALL)
    private List<RespuestaComentario> respuestas;

    @PrePersist
    protected void onCreate() {
        if (fechaRegistroAnfitrion == null) {
            fechaRegistroAnfitrion = LocalDateTime.now();
        }
        if (verificado == null) {
            verificado = false;
        }
    }
}