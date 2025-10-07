package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "administradores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administrador_id")
    @Schema(description = "Identificador único del administrador", example = "1")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "Usuario asociado al administrador")
    private Usuario usuario;

    @Column(name = "nivel_acceso", nullable = false, length = 50)
    @NotBlank(message = "El nivel de acceso es obligatorio")
    @Pattern(regexp = "SUPER_ADMIN|ADMIN|MODERADOR", message = "Nivel de acceso inválido")
    @Schema(description = "Nivel de acceso del administrador", example = "SUPER_ADMIN")
    private String nivelAcceso;

    @Column(columnDefinition = "JSON")
    @Schema(description = "Permisos específicos en formato JSON")
    private String permisos;

    @Column(name = "fecha_asignacion", nullable = false)
    @Schema(description = "Fecha de asignación como administrador")
    private LocalDateTime fechaAsignacion;

    @PrePersist
    protected void onCreate() {
        if (fechaAsignacion == null) {
            fechaAsignacion = LocalDateTime.now();
        }
    }
}