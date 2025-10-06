package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la notificación", example = "301")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Título o asunto de la notificación", example = "Reserva Confirmada")
    private String titulo;

    @Column(nullable = false, length = 500)
    @Schema(description = "Mensaje detallado de la notificación", example = "Tu reserva con ID 25 ha sido confirmada exitosamente.")
    private String mensaje;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora en que se generó la notificación", example = "2025-09-20T15:30:00")
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
    @Schema(description = "Indica si el usuario ya leyó la notificación", example = "false")
    private boolean leida;

    // ========= Relaciones =========

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuario destinatario de la notificación")
    private Usuario usuario;
}
