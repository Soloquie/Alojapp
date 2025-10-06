package co.uniquindio.alojapp.persistencia.Entity;

import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import co.uniquindio.alojapp.persistencia.Entity.Enum.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Column(nullable = false, unique = true, length = 120)
    @Schema(description = "Correo electrónico único del usuario", example = "juan.perez@correo.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Contraseña encriptada del usuario", example = "$2a$10$hashEjemplo")
    private String password;

    @Column(length = 20)
    @Schema(description = "Número de teléfono del usuario", example = "+57 3001234567")
    private String telefono;

    @Column(name = "fecha_nacimiento")
    @Schema(description = "Fecha de nacimiento del usuario", example = "1995-08-15")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Rol del usuario en la plataforma", example = "HUESPED")
    private Rol rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Estado actual de la cuenta del usuario", example = "ACTIVO")
    private EstadoUsuario estado;

    // ========== Relaciones ==========
    @OneToMany(mappedBy = "huesped", cascade = CascadeType.ALL)
    private List<Reserva> reservasRealizadas;

    @OneToMany(mappedBy = "anfitrion", cascade = CascadeType.ALL)
    private List<Alojamiento> alojamientos;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Notificacion> notificaciones;
}
