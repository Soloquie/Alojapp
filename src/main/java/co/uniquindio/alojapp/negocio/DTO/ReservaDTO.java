package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de reserva sincronizado con BD")
public class ReservaDTO {

    @Schema(description = "ID de la reserva", example = "45")
    private Long id;

    @Schema(description = "ID del alojamiento", example = "10")
    private Long alojamientoId;

    @Schema(description = "Nombre del alojamiento", example = "Casa de playa en Cartagena")
    private String alojamientoNombre;

    @Schema(description = "ID del huésped", example = "25")
    private Long huespedId;

    @Schema(description = "Nombre del huésped", example = "Juan Pérez")
    private String huespedNombre;

    @Schema(description = "Fecha de check-in", example = "2025-09-20")
    private LocalDate fechaCheckin;

    @Schema(description = "Fecha de check-out", example = "2025-09-25")
    private LocalDate fechaCheckout;

    @Schema(description = "Número de huéspedes", example = "4")
    private Integer numeroHuespedes;

    @Schema(description = "Precio total", example = "1750000.00")
    private BigDecimal precioTotal;

    @Schema(description = "Estado de la reserva", example = "CONFIRMADA")
    private String estado;

    @Schema(description = "Fecha de creación")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de cancelación")
    private LocalDateTime fechaCancelacion;

    @Schema(description = "Motivo de cancelación")
    private String motivoCancelacion;

    @Schema(description = "Cantidad de noches", example = "5")
    private Long cantidadNoches;

    @Schema(description = "Puede ser cancelada", example = "true")
    private Boolean puedeCancelarse;
}