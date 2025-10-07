package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para crear una nueva reserva")
public class CrearReservaRequest {

    @NotNull(message = "El ID del alojamiento es obligatorio")
    @Schema(description = "ID del alojamiento a reservar", example = "10")
    private Integer alojamientoId;

    @NotNull(message = "La fecha de check-in es obligatoria")
    @FutureOrPresent(message = "La fecha de check-in debe ser presente o futura")
    @Schema(description = "Fecha de entrada", example = "2025-10-15")
    private LocalDate fechaCheckin;

    @NotNull(message = "La fecha de check-out es obligatoria")
    @Future(message = "La fecha de check-out debe ser futura")
    @Schema(description = "Fecha de salida", example = "2025-10-20")
    private LocalDate fechaCheckout;

    @NotNull(message = "El número de huéspedes es obligatorio")
    @Min(value = 1, message = "Debe haber al menos 1 huésped")
    @Schema(description = "Número de huéspedes", example = "4")
    private Integer numeroHuespedes;

    @Schema(description = "Método de pago", example = "TARJETA_CREDITO")
    private String metodoPago;
}