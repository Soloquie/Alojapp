package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del pago", example = "501")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Monto total pagado por la reserva", example = "700000.00")
    private double monto;

    @Column(nullable = false)
    @Schema(description = "Método de pago utilizado", example = "Tarjeta de crédito")
    private String metodoPago;

    @Column(nullable = false)
    @Schema(description = "Estado del pago", example = "APROBADO")
    private String estado;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora en la que se procesó el pago", example = "2025-09-15T15:45:00")
    private LocalDateTime fechaPago;

    // ========= Relaciones =========

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    @Schema(description = "Reserva asociada a este pago")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuario (huésped) que realiza el pago")
    private Usuario usuario;
}
