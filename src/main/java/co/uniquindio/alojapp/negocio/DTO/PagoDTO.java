package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    @Schema(description = "Identificador único del pago", example = "501")
    private String id;

    @Schema(description = "Identificador de la reserva asociada al pago", example = "25")
    private String reservaId;

    @Schema(description = "Correo electrónico del usuario que realizó el pago", example = "usuario@correo.com")
    private String usuarioEmail;

    @Schema(description = "Monto total del pago realizado", example = "1750000")
    private double monto;

    @Schema(description = "Método de pago utilizado (Tarjeta, Transferencia, Efectivo, etc.)", example = "Tarjeta de crédito")
    private String metodo;

    @Schema(description = "Fecha y hora en que se realizó el pago", example = "2025-09-18T15:30:00")
    private LocalDateTime fechaPago;

    @Schema(description = "Estado del pago (Pendiente, Completado, Fallido, Reembolsado)", example = "Completado")
    private String estado;

    @Schema(description = "Código o referencia de la transacción", example = "TXN-98456231")
    private String referenciaTransaccion;

    @Schema(description = "Metodo por el cual se paga", example = "Tarjeta, Efectivo")
    private String metodoPago;
}
