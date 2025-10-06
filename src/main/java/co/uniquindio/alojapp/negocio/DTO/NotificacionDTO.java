package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO que representa una notificación enviada a un usuario del sistema")
public class NotificacionDTO {

    @Schema(description = "Identificador único de la notificación", example = "101")
    private Long id;

    @Schema(description = "Título o asunto de la notificación", example = "Reserva confirmada")
    private String titulo;

    @Schema(description = "Contenido o mensaje de la notificación", example = "Tu reserva en Casa de Playa ha sido confirmada")
    private String mensaje;

    @Schema(description = "Fecha y hora en que se envió la notificación", example = "2025-09-10T14:35:00")
    private LocalDateTime fechaEnvio;

    @Schema(description = "Estado de lectura de la notificación (true = leída, false = no leída)", example = "false")
    private boolean leida;

    @Schema(description = "ID del usuario que recibió la notificación", example = "123")
    private Long usuarioId;
}
