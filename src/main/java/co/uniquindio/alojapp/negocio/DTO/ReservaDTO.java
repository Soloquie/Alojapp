package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa los datos de una reserva realizada por un huésped en un alojamiento.")
public class ReservaDTO {

    @Schema(description = "Identificador único de la reserva", example = "45")
    private Long id;

    @Schema(description = "Identificador del alojamiento reservado", example = "10")
    private Long alojamientoId;

    @Schema(description = "Nombre o título del alojamiento reservado", example = "Casa de playa en Cartagena")
    private String alojamientoNombre;

    @Schema(description = "Identificador del huésped que realizó la reserva", example = "25")
    private Long huespedId;

    @Schema(description = "Nombre completo del huésped", example = "Juan Pérez")
    private String huespedNombre;

    @Schema(description = "Fecha y hora de inicio de la reserva", example = "2025-09-20T15:00:00")
    private LocalDateTime fechaInicio;

    @Schema(description = "Fecha y hora de fin de la reserva", example = "2025-09-25T11:00:00")
    private LocalDateTime fechaFin;

    @Schema(description = "Número total de noches reservadas", example = "5")
    private int cantidadNoches;

    @Schema(description = "Costo total de la reserva (en pesos colombianos)", example = "1750000")
    private double total;

    @Schema(description = "Estado actual de la reserva (por ejemplo: PENDIENTE, CONFIRMADA, CANCELADA, FINALIZADA)", example = "CONFIRMADA")
    private String estado;

    @Schema(description = "Fecha y hora en que se creó la reserva", example = "2025-09-01T10:30:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Método de pago utilizado (por ejemplo: TARJETA, TRANSFERENCIA)", example = "TARJETA")
    private String metodoPago;

    @Schema(description = "ID del pago asociado a la reserva, si aplica", example = "90")
    private Long pagoId;
}
