package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa a un administrador dentro de la plataforma.
 * Cada administrador está vinculado a un usuario general mediante una relación uno a uno.
 */

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
    @Schema(description = "Identificador único del administrador", example = "1")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @Schema(description = "Usuario asociado al administrador")
    private Usuario usuario;

    @Column(nullable = false, length = 50)
    @Schema(description = "Nivel de acceso del administrador", example = "SUPER_ADMIN")
    private String nivelAcceso;

    @Column(length = 255)
    @Schema(description = "Permisos específicos asignados al administrador", example = "GESTION_USUARIOS, REVISION_COMENTARIOS")
    private String permisos;

    @Column(name = "fecha_asignacion", nullable = false)
    @Schema(description = "Fecha en la que el usuario fue asignado como administrador", example = "2025-01-15T09:30:00")
    private LocalDateTime fechaAsignacion;

    @Column(name = "activo", nullable = false)
    @Schema(description = "Indica si el administrador está activo o suspendido", example = "true")
    private boolean activo;
}
