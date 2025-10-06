package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    @Schema(description = "Identificador único del comentario", example = "100")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Contenido del comentario escrito por el huésped", example = "Excelente alojamiento, muy limpio y cómodo")
    private String contenido;

    @Column(nullable = false)
    @Schema(description = "Calificación dada por el huésped (1-5)", example = "5")
    private Integer calificacion;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora en que se realizó el comentario", example = "2025-09-10T15:30:00")
    private LocalDateTime fecha;

    // ========= Relaciones =========

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuario que realiza el comentario (huésped)")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "alojamiento_id", nullable = false)
    @Schema(description = "Alojamiento al que pertenece el comentario")
    private Alojamiento alojamiento;
}
