package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Schema(description = "Identificador único de la reserva", example = "25")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Fecha de inicio de la reserva", example = "2025-09-20")
    private LocalDate fechaInicio;

    @Column(nullable = false)
    @Schema(description = "Fecha de fin de la reserva", example = "2025-09-25")
    private LocalDate fechaFin;

    @Column(nullable = false)
    @Schema(description = "Número total de huéspedes incluidos en la reserva", example = "4")
    private int numeroHuespedes;

    @Column(nullable = false)
    @Schema(description = "Estado actual de la reserva", example = "CONFIRMADA")
    private String estado;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora en que se realizó la reserva", example = "2025-09-15T14:30:00")
    private LocalDateTime fechaCreacion;

    // ========= Relaciones =========

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuario huésped que realiza la reserva")
    private Usuario huesped;

    @ManyToOne
    @JoinColumn(name = "alojamiento_id", nullable = false)
    @Schema(description = "Alojamiento que fue reservado")
    private Alojamiento alojamiento;

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    @Schema(description = "Pago asociado a la reserva")
    private Pago pago;
}
