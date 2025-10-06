package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas_comentarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaComentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "respuesta_id")
    @Schema(description = "Identificador único de la respuesta", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comentario_id", nullable = false)
    @NotNull(message = "El comentario es obligatorio")
    @Schema(description = "Comentario al que se responde")
    private Comentario comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anfitrion_id", nullable = false)
    @NotNull(message = "El anfitrión es obligatorio")
    @Schema(description = "Anfitrión que responde")
    private Anfitrion anfitrion;

    @Column(name = "respuesta_texto", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "La respuesta no puede estar vacía")
    @Size(max = 1000, message = "La respuesta no puede exceder 1000 caracteres")
    @Schema(description = "Texto de la respuesta")
    private String respuestaTexto;

    @Column(name = "fecha_respuesta", nullable = false)
    @Schema(description = "Fecha de la respuesta")
    private LocalDateTime fechaRespuesta;

    @PrePersist
    protected void onCreate() {
        if (fechaRespuesta == null) {
            fechaRespuesta = LocalDateTime.now();
        }
    }
}