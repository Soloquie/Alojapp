package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para UsuarioDTO
 *
 * OBJETIVO: Probar el DTO de usuario con todos sus campos
 * - Validar constructor, builder, getters/setters
 * - Verificar equals/hashCode
 * - Probar diferentes escenarios de uso
 */
@DisplayName("UsuarioDTO - Unit Tests")
public class UsuarioDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 123;
    private final String NOMBRE_VALIDO = "Juan Pérez";
    private final String EMAIL_VALIDO = "juanperez@correo.com";
    private final String TELEFONO_VALIDO = "+57 3001234567";
    private final LocalDate FECHA_NACIMIENTO_VALIDA = LocalDate.of(1995, 8, 20);
    private final LocalDateTime FECHA_REGISTRO_VALIDA = LocalDateTime.of(2024, 1, 15, 10, 30);
    private final LocalDateTime FECHA_ULTIMA_CONEXION_VALIDA = LocalDateTime.of(2024, 10, 11, 14, 45);
    private final String ESTADO_VALIDO = "ACTIVO";
    private final String FOTO_PERFIL_URL_VALIDA = "https://cloudinary.com/perfil123.jpg";
    private final Boolean ES_ANFITRION_VALIDO = false;
    private final Boolean ES_ADMIN_VALIDO = false;

    // ==================== BUILDER TESTS ====================

    @Test
    @DisplayName("BUILDER - Crea instancia con todos los campos correctamente")
    void builder_ConTodosLosCampos_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .telefono(TELEFONO_VALIDO)
                .fechaNacimiento(FECHA_NACIMIENTO_VALIDA)
                .fechaRegistro(FECHA_REGISTRO_VALIDA)
                .fechaUltimaConexion(FECHA_ULTIMA_CONEXION_VALIDA)
                .estado(ESTADO_VALIDO)
                .fotoPerfilUrl(FOTO_PERFIL_URL_VALIDA)
                .esAnfitrion(ES_ANFITRION_VALIDO)
                .esAdmin(ES_ADMIN_VALIDO)
                .build();

        // ASSERT
        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo(ID_VALIDO);
        assertThat(usuario.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(usuario.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(usuario.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(usuario.getFechaNacimiento()).isEqualTo(FECHA_NACIMIENTO_VALIDA);
        assertThat(usuario.getFechaRegistro()).isEqualTo(FECHA_REGISTRO_VALIDA);
        assertThat(usuario.getFechaUltimaConexion()).isEqualTo(FECHA_ULTIMA_CONEXION_VALIDA);
        assertThat(usuario.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(usuario.getFotoPerfilUrl()).isEqualTo(FOTO_PERFIL_URL_VALIDA);
        assertThat(usuario.getEsAnfitrion()).isEqualTo(ES_ANFITRION_VALIDO);
        assertThat(usuario.getEsAdmin()).isEqualTo(ES_ADMIN_VALIDO);
    }

    @Test
    @DisplayName("BUILDER - Campos opcionales null se manejan correctamente")
    void builder_CamposOpcionalesNull_SeManejanCorrectamente() {
        // ARRANGE & ACT
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        // ASSERT
        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo(ID_VALIDO);
        assertThat(usuario.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(usuario.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(usuario.getEstado()).isEqualTo(ESTADO_VALIDO);

        // Campos opcionales null
        assertThat(usuario.getTelefono()).isNull();
        assertThat(usuario.getFechaNacimiento()).isNull();
        assertThat(usuario.getFechaRegistro()).isNull();
        assertThat(usuario.getFechaUltimaConexion()).isNull();
        assertThat(usuario.getFotoPerfilUrl()).isNull();
        assertThat(usuario.getEsAnfitrion()).isNull();
        assertThat(usuario.getEsAdmin()).isNull();
    }

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        UsuarioDTO usuario = new UsuarioDTO();

        // ASSERT
        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isNull();
        assertThat(usuario.getNombre()).isNull();
        assertThat(usuario.getEmail()).isNull();
        assertThat(usuario.getTelefono()).isNull();
        assertThat(usuario.getFechaNacimiento()).isNull();
        assertThat(usuario.getFechaRegistro()).isNull();
        assertThat(usuario.getFechaUltimaConexion()).isNull();
        assertThat(usuario.getEstado()).isNull();
        assertThat(usuario.getFotoPerfilUrl()).isNull();
        assertThat(usuario.getEsAnfitrion()).isNull();
        assertThat(usuario.getEsAdmin()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        UsuarioDTO usuario = new UsuarioDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                FECHA_NACIMIENTO_VALIDA, FECHA_REGISTRO_VALIDA, FECHA_ULTIMA_CONEXION_VALIDA,
                ESTADO_VALIDO, FOTO_PERFIL_URL_VALIDA, ES_ANFITRION_VALIDO, ES_ADMIN_VALIDO
        );

        // ASSERT
        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo(ID_VALIDO);
        assertThat(usuario.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(usuario.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(usuario.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(usuario.getFechaNacimiento()).isEqualTo(FECHA_NACIMIENTO_VALIDA);
        assertThat(usuario.getFechaRegistro()).isEqualTo(FECHA_REGISTRO_VALIDA);
        assertThat(usuario.getFechaUltimaConexion()).isEqualTo(FECHA_ULTIMA_CONEXION_VALIDA);
        assertThat(usuario.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(usuario.getFotoPerfilUrl()).isEqualTo(FOTO_PERFIL_URL_VALIDA);
        assertThat(usuario.getEsAnfitrion()).isEqualTo(ES_ANFITRION_VALIDO);
        assertThat(usuario.getEsAdmin()).isEqualTo(ES_ADMIN_VALIDO);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        UsuarioDTO usuario = new UsuarioDTO();

        // ACT
        usuario.setId(ID_VALIDO);
        usuario.setNombre(NOMBRE_VALIDO);
        usuario.setEmail(EMAIL_VALIDO);
        usuario.setTelefono(TELEFONO_VALIDO);
        usuario.setFechaNacimiento(FECHA_NACIMIENTO_VALIDA);
        usuario.setFechaRegistro(FECHA_REGISTRO_VALIDA);
        usuario.setFechaUltimaConexion(FECHA_ULTIMA_CONEXION_VALIDA);
        usuario.setEstado(ESTADO_VALIDO);
        usuario.setFotoPerfilUrl(FOTO_PERFIL_URL_VALIDA);
        usuario.setEsAnfitrion(ES_ANFITRION_VALIDO);
        usuario.setEsAdmin(ES_ADMIN_VALIDO);

        // ASSERT
        assertThat(usuario.getId()).isEqualTo(ID_VALIDO);
        assertThat(usuario.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(usuario.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(usuario.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(usuario.getFechaNacimiento()).isEqualTo(FECHA_NACIMIENTO_VALIDA);
        assertThat(usuario.getFechaRegistro()).isEqualTo(FECHA_REGISTRO_VALIDA);
        assertThat(usuario.getFechaUltimaConexion()).isEqualTo(FECHA_ULTIMA_CONEXION_VALIDA);
        assertThat(usuario.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(usuario.getFotoPerfilUrl()).isEqualTo(FOTO_PERFIL_URL_VALIDA);
        assertThat(usuario.getEsAnfitrion()).isEqualTo(ES_ANFITRION_VALIDO);
        assertThat(usuario.getEsAdmin()).isEqualTo(ES_ADMIN_VALIDO);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en TODOS los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        UsuarioDTO usuario1 = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        UsuarioDTO usuario2 = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(usuario1).isEqualTo(usuario2);
        assertThat(usuario1.hashCode()).isEqualTo(usuario2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        UsuarioDTO usuario1 = UsuarioDTO.builder()
                .id(1)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .build();

        UsuarioDTO usuario2 = UsuarioDTO.builder()
                .id(2)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    @DisplayName("EQUALS - Diferente email retorna false")
    void equals_DiferenteEmail_RetornaFalse() {
        // ARRANGE
        UsuarioDTO usuario1 = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email("usuario1@correo.com")
                .build();

        UsuarioDTO usuario2 = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email("usuario2@correo.com")
                .build();

        // ACT & ASSERT
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(usuario).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(usuario).isNotEqualTo("No soy un usuario");
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del usuario")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        // ACT
        String resultado = usuario.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(NOMBRE_VALIDO);
        assertThat(resultado).contains(EMAIL_VALIDO);
        assertThat(resultado).contains(ESTADO_VALIDO);
    }

    // ==================== CAMPOS INDIVIDUALES TESTS ====================

    @Test
    @DisplayName("NOMBRE - Diferentes formatos de nombre son aceptados")
    void nombre_DiferentesFormatosDeNombre_SonAceptados() {
        // ARRANGE
        UsuarioDTO usuario = new UsuarioDTO();

        // ACT & ASSERT
        usuario.setNombre("Ana García");
        assertThat(usuario.getNombre()).isEqualTo("Ana García");

        usuario.setNombre("Carlos José Ramírez Pérez");
        assertThat(usuario.getNombre()).hasSizeGreaterThan(20);

        usuario.setNombre("");
        assertThat(usuario.getNombre()).isEmpty();

        usuario.setNombre(null);
        assertThat(usuario.getNombre()).isNull();
    }

    @Test
    @DisplayName("EMAIL - Diferentes formatos de email son aceptados")
    void email_DiferentesFormatosDeEmail_SonAceptados() {
        // ARRANGE
        UsuarioDTO usuario = new UsuarioDTO();

        // ACT & ASSERT
        usuario.setEmail("usuario@dominio.com");
        assertThat(usuario.getEmail()).isEqualTo("usuario@dominio.com");

        usuario.setEmail("usuario.nombre+tag@sub.dominio.co");
        assertThat(usuario.getEmail()).contains("@");

        usuario.setEmail("");
        assertThat(usuario.getEmail()).isEmpty();

        usuario.setEmail(null);
        assertThat(usuario.getEmail()).isNull();
    }

    @Test
    @DisplayName("TELÉFONO - Diferentes formatos de teléfono son aceptados")
    void telefono_DiferentesFormatosDeTelefono_SonAceptados() {
        // ARRANGE
        UsuarioDTO usuario = new UsuarioDTO();

        // ACT & ASSERT
        usuario.setTelefono("+57 3001234567");
        assertThat(usuario.getTelefono()).isEqualTo("+57 3001234567");

        usuario.setTelefono("3001234567");
        assertThat(usuario.getTelefono()).isEqualTo("3001234567");

        usuario.setTelefono("(601) 123-4567");
        assertThat(usuario.getTelefono()).contains("601");

        usuario.setTelefono("");
        assertThat(usuario.getTelefono()).isEmpty();

        usuario.setTelefono(null);
        assertThat(usuario.getTelefono()).isNull();
    }

    @Test
    @DisplayName("FECHAS - Diferentes fechas son aceptadas")
    void fechas_DiferentesFechas_SonAceptadas() {
        // ARRANGE
        UsuarioDTO usuario = new UsuarioDTO();

        // ACT & ASSERT
        LocalDate fechaNacimiento = LocalDate.of(1990, 1, 1);
        usuario.setFechaNacimiento(fechaNacimiento);
        assertThat(usuario.getFechaNacimiento()).isEqualTo(fechaNacimiento);

        LocalDateTime fechaRegistro = LocalDateTime.now().minusDays(1);
        usuario.setFechaRegistro(fechaRegistro);
        assertThat(usuario.getFechaRegistro()).isEqualTo(fechaRegistro);

        LocalDateTime fechaConexion = LocalDateTime.now();
        usuario.setFechaUltimaConexion(fechaConexion);
        assertThat(usuario.getFechaUltimaConexion()).isEqualTo(fechaConexion);

        usuario.setFechaNacimiento(null);
        assertThat(usuario.getFechaNacimiento()).isNull();
    }

    @Test
    @DisplayName("ESTADO - Diferentes estados de cuenta son aceptados")
    void estado_DiferentesEstadosDeCuenta_SonAceptados() {
        // ARRANGE
        UsuarioDTO usuario = new UsuarioDTO();

        // ACT & ASSERT
        usuario.setEstado("ACTIVO");
        assertThat(usuario.getEstado()).isEqualTo("ACTIVO");

        usuario.setEstado("INACTIVO");
        assertThat(usuario.getEstado()).isEqualTo("INACTIVO");

        usuario.setEstado("SUSPENDIDO");
        assertThat(usuario.getEstado()).isEqualTo("SUSPENDIDO");

        usuario.setEstado("BLOQUEADO");
        assertThat(usuario.getEstado()).isEqualTo("BLOQUEADO");

        usuario.setEstado("");
        assertThat(usuario.getEstado()).isEmpty();

        usuario.setEstado(null);
        assertThat(usuario.getEstado()).isNull();
    }

    @Test
    @DisplayName("FOTO PERFIL - Diferentes URLs son aceptadas")
    void fotoPerfil_DiferentesUrls_SonAceptadas() {
        // ARRANGE
        UsuarioDTO usuario = new UsuarioDTO();

        // ACT & ASSERT
        usuario.setFotoPerfilUrl("https://ejemplo.com/foto.jpg");
        assertThat(usuario.getFotoPerfilUrl()).isEqualTo("https://ejemplo.com/foto.jpg");

        usuario.setFotoPerfilUrl("/ruta/local/foto.png");
        assertThat(usuario.getFotoPerfilUrl()).isEqualTo("/ruta/local/foto.png");

        usuario.setFotoPerfilUrl("");
        assertThat(usuario.getFotoPerfilUrl()).isEmpty();

        usuario.setFotoPerfilUrl(null);
        assertThat(usuario.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("ROLES - Diferentes combinaciones de roles son aceptadas")
    void roles_DiferentesCombinacionesDeRoles_SonAceptadas() {
        // ARRANGE
        UsuarioDTO usuario = new UsuarioDTO();

        // ACT & ASSERT
        usuario.setEsAnfitrion(true);
        usuario.setEsAdmin(false);
        assertThat(usuario.getEsAnfitrion()).isTrue();
        assertThat(usuario.getEsAdmin()).isFalse();

        usuario.setEsAnfitrion(false);
        usuario.setEsAdmin(true);
        assertThat(usuario.getEsAnfitrion()).isFalse();
        assertThat(usuario.getEsAdmin()).isTrue();

        usuario.setEsAnfitrion(true);
        usuario.setEsAdmin(true);
        assertThat(usuario.getEsAnfitrion()).isTrue();
        assertThat(usuario.getEsAdmin()).isTrue();

        usuario.setEsAnfitrion(null);
        usuario.setEsAdmin(null);
        assertThat(usuario.getEsAnfitrion()).isNull();
        assertThat(usuario.getEsAdmin()).isNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Usuario huésped regular")
    void scenarioUsoReal_UsuarioHuespedRegular() {
        // ARRANGE & ACT
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(100)
                .nombre("María González")
                .email("maria.gonzalez@email.com")
                .telefono("+57 3105551234")
                .fechaNacimiento(LocalDate.of(1990, 5, 15))
                .fechaRegistro(LocalDateTime.of(2024, 1, 10, 9, 0))
                .fechaUltimaConexion(LocalDateTime.now().minusHours(2))
                .estado("ACTIVO")
                .fotoPerfilUrl("https://cloudinary.com/perfil100.jpg")
                .esAnfitrion(false)
                .esAdmin(false)
                .build();

        // ASSERT
        assertThat(usuario.getId()).isEqualTo(100);
        assertThat(usuario.getNombre()).isEqualTo("María González");
        assertThat(usuario.getEmail()).contains("@email.com");
        assertThat(usuario.getEsAnfitrion()).isFalse();
        assertThat(usuario.getEsAdmin()).isFalse();
        assertThat(usuario.getEstado()).isEqualTo("ACTIVO");
        assertThat(usuario.getFechaRegistro()).isBefore(LocalDateTime.now());
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Usuario anfitrión")
    void scenarioUsoReal_UsuarioAnfitrion() {
        // ARRANGE & ACT
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(200)
                .nombre("Carlos Rodríguez")
                .email("carlos.anfitrion@alojapp.com")
                .telefono("+57 3204445678")
                .fechaNacimiento(LocalDate.of(1985, 12, 3))
                .fechaRegistro(LocalDateTime.of(2023, 11, 20, 14, 30))
                .fechaUltimaConexion(LocalDateTime.now().minusMinutes(30))
                .estado("ACTIVO")
                .fotoPerfilUrl("https://cloudinary.com/anfitrion200.jpg")
                .esAnfitrion(true)
                .esAdmin(false)
                .build();

        // ASSERT
        assertThat(usuario.getEsAnfitrion()).isTrue();
        assertThat(usuario.getEsAdmin()).isFalse();
        assertThat(usuario.getEmail()).endsWith("@alojapp.com");
        assertThat(usuario.getFechaRegistro().getYear()).isEqualTo(2023);
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Usuario administrador")
    void scenarioUsoReal_UsuarioAdministrador() {
        // ARRANGE & ACT
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(1)
                .nombre("Admin Sistema")
                .email("admin@alojapp.com")
                .fechaRegistro(LocalDateTime.of(2023, 1, 1, 0, 0))
                .fechaUltimaConexion(LocalDateTime.now())
                .estado("ACTIVO")
                .esAnfitrion(false)
                .esAdmin(true)
                .build();

        // ASSERT
        assertThat(usuario.getEsAdmin()).isTrue();
        assertThat(usuario.getEsAnfitrion()).isFalse();
        assertThat(usuario.getEmail()).isEqualTo("admin@alojapp.com");
        assertThat(usuario.getEstado()).isEqualTo("ACTIVO");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Usuario inactivo")
    void scenarioUsoReal_UsuarioInactivo() {
        // ARRANGE & ACT
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(300)
                .nombre("Laura Martínez")
                .email("laura.m@antiguo.com")
                .fechaRegistro(LocalDateTime.of(2024, 3, 1, 10, 0))
                .fechaUltimaConexion(LocalDateTime.of(2024, 6, 15, 16, 20))
                .estado("INACTIVO")
                .esAnfitrion(false)
                .esAdmin(false)
                .build();

        // ASSERT
        assertThat(usuario.getEstado()).isEqualTo("INACTIVO");
        assertThat(usuario.getFechaUltimaConexion()).isBefore(LocalDateTime.now().minusMonths(1));
        assertThat(usuario.getEsAnfitrion()).isFalse();
        assertThat(usuario.getEsAdmin()).isFalse();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Nuevo usuario sin ID")
    void scenarioUsoReal_NuevoUsuarioSinId() {
        // ARRANGE & ACT
        UsuarioDTO nuevoUsuario = UsuarioDTO.builder()
                .nombre("Nuevo Usuario")
                .email("nuevo@correo.com")
                .telefono("3001112233")
                .fechaNacimiento(LocalDate.of(2000, 1, 1))
                .fechaRegistro(LocalDateTime.now())
                .estado("ACTIVO")
                .esAnfitrion(false)
                .esAdmin(false)
                .build();

        // ASSERT - Nuevo usuario sin ID asignado
        assertThat(nuevoUsuario.getId()).isNull();
        assertThat(nuevoUsuario.getNombre()).isEqualTo("Nuevo Usuario");
        assertThat(nuevoUsuario.getEmail()).isEqualTo("nuevo@correo.com");
        assertThat(nuevoUsuario.getEstado()).isEqualTo("ACTIVO");
        assertThat(nuevoUsuario.getEsAnfitrion()).isFalse();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Usuario con datos mínimos")
    void scenarioUsoReal_UsuarioConDatosMinimos() {
        // ARRANGE & ACT
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(400)
                .nombre("Usuario Mínimo")
                .email("minimo@test.com")
                .estado("ACTIVO")
                .build();

        // ASSERT
        assertThat(usuario.getId()).isEqualTo(400);
        assertThat(usuario.getNombre()).isEqualTo("Usuario Mínimo");
        assertThat(usuario.getEmail()).isEqualTo("minimo@test.com");
        assertThat(usuario.getEstado()).isEqualTo("ACTIVO");
        assertThat(usuario.getTelefono()).isNull();
        assertThat(usuario.getFechaNacimiento()).isNull();
        assertThat(usuario.getFotoPerfilUrl()).isNull();
        assertThat(usuario.getEsAnfitrion()).isNull();
        assertThat(usuario.getEsAdmin()).isNull();
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Usuario con nombre muy largo")
    void casoBorde_UsuarioConNombreMuyLargo() {
        // ARRANGE & ACT
        String nombreLargo = "Juan Carlos Antonio de la Santísima Trinidad Pérez Rodríguez González";
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(500)
                .nombre(nombreLargo)
                .email("largo@nombre.com")
                .estado("ACTIVO")
                .build();

        // ASSERT
        assertThat(usuario.getNombre()).hasSizeGreaterThan(50);
        assertThat(usuario.getNombre()).isEqualTo(nombreLargo);
        assertThat(usuario.getEstado()).isEqualTo("ACTIVO");
    }

    @Test
    @DisplayName("CASO BORDE - Usuario sin foto de perfil")
    void casoBorde_UsuarioSinFotoDePerfil() {
        // ARRANGE & ACT
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(600)
                .nombre("Usuario Sin Foto")
                .email("sinfoto@test.com")
                .estado("ACTIVO")
                .fotoPerfilUrl(null)
                .build();

        // ASSERT
        assertThat(usuario.getFotoPerfilUrl()).isNull();
        assertThat(usuario.getNombre()).isEqualTo("Usuario Sin Foto");
        assertThat(usuario.getEstado()).isEqualTo("ACTIVO");
    }

    @Test
    @DisplayName("CASO BORDE - Usuario con fecha de nacimiento futura")
    void casoBorde_UsuarioConFechaNacimientoFutura() {
        // ARRANGE & ACT
        LocalDate fechaFutura = LocalDate.now().plusYears(1);
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(700)
                .nombre("Usuario Futuro")
                .email("futuro@test.com")
                .fechaNacimiento(fechaFutura)
                .estado("ACTIVO")
                .build();

        // ASSERT
        assertThat(usuario.getFechaNacimiento()).isAfter(LocalDate.now());
        assertThat(usuario.getEstado()).isEqualTo("ACTIVO");
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE - Crear dos objetos IDÉNTICOS
        UsuarioDTO usuario1 = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        UsuarioDTO usuario2 = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        // ASSERT - Verificar que @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(usuario1).isEqualTo(usuario2);
        assertThat(usuario1.toString()).isNotNull();
        assertThat(usuario1.hashCode()).isEqualTo(usuario2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        UsuarioDTO usuarioVacio = new UsuarioDTO();
        assertThat(usuarioVacio).isNotNull();

        // Verificar que el builder funciona
        assertThat(usuario1).isInstanceOf(UsuarioDTO.class);
    }

    // ==================== VALIDACIÓN DE ESTRUCTURA TESTS ====================

    @Test
    @DisplayName("ESTRUCTURA - Campos tienen los tipos de datos correctos")
    void estructura_CamposTienenLosTiposDeDatosCorrectos() {
        // ARRANGE & ACT
        UsuarioDTO usuario = UsuarioDTO.builder()
                .id(ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .telefono(TELEFONO_VALIDO)
                .fechaNacimiento(FECHA_NACIMIENTO_VALIDA)
                .fechaRegistro(FECHA_REGISTRO_VALIDA)
                .fechaUltimaConexion(FECHA_ULTIMA_CONEXION_VALIDA)
                .estado(ESTADO_VALIDO)
                .fotoPerfilUrl(FOTO_PERFIL_URL_VALIDA)
                .esAnfitrion(ES_ANFITRION_VALIDO)
                .esAdmin(ES_ADMIN_VALIDO)
                .build();

        // ASSERT
        assertThat(usuario.getId()).isInstanceOf(Integer.class);
        assertThat(usuario.getNombre()).isInstanceOf(String.class);
        assertThat(usuario.getEmail()).isInstanceOf(String.class);
        assertThat(usuario.getTelefono()).isInstanceOf(String.class);
        assertThat(usuario.getFechaNacimiento()).isInstanceOf(LocalDate.class);
        assertThat(usuario.getFechaRegistro()).isInstanceOf(LocalDateTime.class);
        assertThat(usuario.getFechaUltimaConexion()).isInstanceOf(LocalDateTime.class);
        assertThat(usuario.getEstado()).isInstanceOf(String.class);
        assertThat(usuario.getFotoPerfilUrl()).isInstanceOf(String.class);
        assertThat(usuario.getEsAnfitrion()).isInstanceOf(Boolean.class);
        assertThat(usuario.getEsAdmin()).isInstanceOf(Boolean.class);
    }
}