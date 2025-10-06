package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "codigos_recuperacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodigoRecuperacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_id")
    @Schema(description = "Identificador único del código de recuperación", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "Usuario asociado al código de recuperación")
    private Usuario usuario;

    @Column(nullable = false, length = 10)
    @NotBlank(message = "El código es obligatorio")
    @Size(min = 6, max = 10, message = "El código debe tener entre 6 y 10 caracteres")
    @Schema(description = "Código de recuperación generado", example = "ABC123XYZ")
    private String codigo;

    @Column(name = "fecha_expiracion", nullable = false)
    @NotNull(message = "La fecha de expiración es obligatoria")
    @Future(message = "La fecha de expiración debe ser futura")
    @Schema(description = "Fecha y hora de expiración del código", example = "2025-10-05T15:45:00")
    private LocalDateTime fechaExpiracion;

    @Column(nullable = false)
    @Schema(description = "Indica si el código ya fue utilizado", example = "false")
    private Boolean usado = false;

    @PrePersist
    protected void onCreate() {
        if (fechaExpiracion == null) {
            fechaExpiracion = LocalDateTime.now().plusMinutes(15);
        }
        if (usado == null) {
            usado = false;
        }
    }

    public boolean esValido() {
        return !usado && LocalDateTime.now().isBefore(fechaExpiracion);
    }
}