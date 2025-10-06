package co.uniquindio.alojapp.persistencia.Entity;

import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Column(name = "usuario_id")
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Column(nullable = false, unique = true, length = 255)
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    @Schema(description = "Correo electrónico único del usuario", example = "juan.perez@correo.com")
    private String email;

    @Column(name = "contrasena_hash", nullable = false, length = 255)
    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Contraseña encriptada del usuario")
    private String contrasenaHash;

    @Column(length = 20)
    @Pattern(regexp = "^\\+?[0-9]{10,20}$", message = "Formato de teléfono inválido")
    @Schema(description = "Número de teléfono del usuario", example = "+57 3001234567")
    private String telefono;

    @Column(name = "fecha_nacimiento")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Schema(description = "Fecha de nacimiento del usuario", example = "1995-08-15")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_registro", nullable = false)
    @Schema(description = "Fecha de registro en el sistema")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_ultima_conexion")
    @Schema(description = "Última fecha de conexión")
    private LocalDateTime fechaUltimaConexion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Estado actual de la cuenta del usuario", example = "ACTIVO")
    private EstadoUsuario estado;

    @Column(name = "foto_perfil_url", length = 500)
    @Schema(description = "URL de la foto de perfil")
    private String fotoPerfilUrl;

    // ========== Relaciones ==========

    @OneToMany(mappedBy = "huesped", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Reserva> reservasRealizadas = new ArrayList<>();

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Notificacion> notificaciones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Favorito> favoritos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CodigoRecuperacion> codigosRecuperacion = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Pago> pagos = new ArrayList<>();

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Anfitrion perfilAnfitrion;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Administrador perfilAdministrador;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoUsuario.ACTIVO;
        }
    }
}