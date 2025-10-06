package co.uniquindio.alojapp.persistencia.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favoritos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa los alojamientos marcados como favoritos por un usuario.")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del registro de favorito", example = "1")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuario que marcó el alojamiento como favorito")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "alojamiento_id", nullable = false)
    @Schema(description = "Alojamiento marcado como favorito por el usuario")
    private Alojamiento alojamiento;

    @Column(name = "fecha_agregado", nullable = false)
    @Schema(description = "Fecha en la que el usuario agregó el alojamiento a sus favoritos", example = "2025-10-05T15:30:00")
    private LocalDateTime fechaAgregado;
}
