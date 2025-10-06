package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comentarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comentario_id")
    @Schema(description = "Identificador único del comentario", example = "100")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false)
    @NotNull(message = "La reserva es obligatoria")
    @Schema(description = "Reserva asociada al comentario")
    private Reserva reserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "Usuario que realiza el comentario")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alojamiento_id", nullable = false)
    @NotNull(message = "El alojamiento es obligatorio")
    @Schema(description = "Alojamiento comentado")
    private Alojamiento alojamiento;

    @Column(nullable = false)
    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    @Schema(description = "Calificación (1-5)", example = "5")
    private Integer calificacion;

    @Column(name = "comentario_texto", columnDefinition = "TEXT")
    @Size(max = 500, message = "El comentario no puede exceder 500 caracteres")
    @Schema(description = "Texto del comentario")
    private String comentarioTexto;

    @Column(name = "fecha_comentario", nullable = false)
    @Schema(description = "Fecha del comentario")
    private LocalDateTime fechaComentario;

    @OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL)
    private List<RespuestaComentario> respuestas;

    @PrePersist
    protected void onCreate() {
        if (fechaComentario == null) {
            fechaComentario = LocalDateTime.now();
        }
    }
}