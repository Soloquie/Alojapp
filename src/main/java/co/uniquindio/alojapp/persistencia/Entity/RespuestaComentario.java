package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas_comentarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa las respuestas realizadas a comentarios de alojamientos.")
public class RespuestaComentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la respuesta al comentario", example = "1")
    private Long id;

    @Column(nullable = false, length = 500)
    @Schema(description = "Contenido de la respuesta realizada al comentario", example = "Gracias por tu opinión, siempre trabajamos para mejorar.")
    private String contenido;

    @Column(name = "fecha_respuesta", nullable = false)
    @Schema(description = "Fecha y hora en que se realizó la respuesta", example = "2025-10-05T18:45:00")
    private LocalDateTime fechaRespuesta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    @Schema(description = "Usuario que escribió la respuesta (anfitrión o administrador)")
    private Usuario autor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comentario_id", nullable = false)
    @Schema(description = "Comentario al cual pertenece esta respuesta")
    private Comentario comentario;
}
