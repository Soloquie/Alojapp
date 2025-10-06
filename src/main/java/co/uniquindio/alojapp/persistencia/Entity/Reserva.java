package co.uniquindio.alojapp.persistencia.Entity;

import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    @Schema(description = "Identificador único de la reserva", example = "25")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "Usuario huésped que realiza la reserva")
    private Usuario huesped;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alojamiento_id", nullable = false)
    @NotNull(message = "El alojamiento es obligatorio")
    @Schema(description = "Alojamiento reservado")
    private Alojamiento alojamiento;

    @Column(name = "fecha_checkin", nullable = false)
    @NotNull(message = "La fecha de check-in es obligatoria")
    @FutureOrPresent(message = "La fecha de check-in debe ser presente o futura")
    @Schema(description = "Fecha de entrada", example = "2025-09-20")
    private LocalDate fechaCheckin;

    @Column(name = "fecha_checkout", nullable = false)
    @NotNull(message = "La fecha de check-out es obligatoria")
    @Future(message = "La fecha de check-out debe ser futura")
    @Schema(description = "Fecha de salida", example = "2025-09-25")
    private LocalDate fechaCheckout;

    @Column(name = "numero_huespedes", nullable = false)
    @NotNull(message = "El número de huéspedes es obligatorio")
    @Min(value = 1, message = "Debe haber al menos 1 huésped")
    @Schema(description = "Número de huéspedes", example = "4")
    private Integer numeroHuespedes;

    @Column(name = "precio_total", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El precio total es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio total debe ser mayor a 0")
    @Schema(description = "Precio total de la reserva", example = "1750000.00")
    private BigDecimal precioTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Estado de la reserva", example = "PENDIENTE")
    private EstadoReserva estado;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación de la reserva")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_cancelacion")
    @Schema(description = "Fecha de cancelación")
    private LocalDateTime fechaCancelacion;

    @Column(name = "motivo_cancelacion", columnDefinition = "TEXT")
    @Schema(description = "Motivo de cancelación")
    private String motivoCancelacion;

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    private Pago pago;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoReserva.PENDIENTE;
        }
    }

    // Métodos de utilidad
    public long calcularCantidadNoches() {
        return ChronoUnit.DAYS.between(fechaCheckin, fechaCheckout);
    }

    public boolean puedeSerCancelada() {
        if (estado != EstadoReserva.CONFIRMADA && estado != EstadoReserva.PENDIENTE) {
            return false;
        }
        return LocalDate.now().plusDays(2).isBefore(fechaCheckin);
    }
}