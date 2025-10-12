package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para RegistroUsuarioRequest
 */
@DisplayName("RegistroUsuarioRequest - Unit Tests")
public class RegistroUsuarioRequestTest {

    private final String NOMBRE_VALIDO = "Juan Pérez";
    private final String EMAIL_VALIDO = "juan.perez@correo.com";
    private final String PASSWORD_VALIDA = "MiClave123";
    private final String TELEFONO_VALIDO = "+57 3001234567";
    private final LocalDate FECHA_NACIMIENTO_VALIDA = LocalDate.of(1995, 8, 15);
    private final String FOTO_PERFIL_URL_VALIDA = "https://ejemplo.com/foto.jpg";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getNombre()).isNull();
        assertThat(request.getEmail()).isNull();
        assertThat(request.getPassword()).isNull();
        assertThat(request.getTelefono()).isNull();
        assertThat(request.getFechaNacimiento()).isNull();
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        RegistroUsuarioRequest request = new RegistroUsuarioRequest(
                NOMBRE_VALIDO, EMAIL_VALIDO, PASSWORD_VALIDA, TELEFONO_VALIDO,
                FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(request.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(request.getPassword()).isEqualTo(PASSWORD_VALIDA);
        assertThat(request.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(request.getFechaNacimiento()).isEqualTo(FECHA_NACIMIENTO_VALIDA);
        assertThat(request.getFotoPerfilUrl()).isEqualTo(FOTO_PERFIL_URL_VALIDA);
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Act
        request.setNombre(NOMBRE_VALIDO);
        request.setEmail(EMAIL_VALIDO);
        request.setPassword(PASSWORD_VALIDA);
        request.setTelefono(TELEFONO_VALIDO);
        request.setFechaNacimiento(FECHA_NACIMIENTO_VALIDA);
        request.setFotoPerfilUrl(FOTO_PERFIL_URL_VALIDA);

        // Assert
        assertThat(request.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(request.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(request.getPassword()).isEqualTo(PASSWORD_VALIDA);
        assertThat(request.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(request.getFechaNacimiento()).isEqualTo(FECHA_NACIMIENTO_VALIDA);
        assertThat(request.getFotoPerfilUrl()).isEqualTo(FOTO_PERFIL_URL_VALIDA);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        RegistroUsuarioRequest request1 = new RegistroUsuarioRequest(
                NOMBRE_VALIDO, EMAIL_VALIDO, PASSWORD_VALIDA, TELEFONO_VALIDO,
                FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );
        RegistroUsuarioRequest request2 = new RegistroUsuarioRequest(
                NOMBRE_VALIDO, EMAIL_VALIDO, PASSWORD_VALIDA, TELEFONO_VALIDO,
                FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getNombre()).isEqualTo(request2.getNombre());
        assertThat(request1.getEmail()).isEqualTo(request2.getEmail());
        assertThat(request1.getPassword()).isEqualTo(request2.getPassword());
        assertThat(request1.getTelefono()).isEqualTo(request2.getTelefono());
        assertThat(request1.getFechaNacimiento()).isEqualTo(request2.getFechaNacimiento());
        assertThat(request1.getFotoPerfilUrl()).isEqualTo(request2.getFotoPerfilUrl());
    }

    @Test
    @DisplayName("Diferente nombre - Campos no son iguales")
    void diferenteNombre_CamposNoSonIguales() {
        // Arrange
        RegistroUsuarioRequest request1 = new RegistroUsuarioRequest();
        request1.setNombre("Juan Pérez");

        RegistroUsuarioRequest request2 = new RegistroUsuarioRequest();
        request2.setNombre("María García");

        // Act & Assert
        assertThat(request1.getNombre()).isNotEqualTo(request2.getNombre());
    }

    @Test
    @DisplayName("Diferente email - Campos no son iguales")
    void diferenteEmail_CamposNoSonIguales() {
        // Arrange
        RegistroUsuarioRequest request1 = new RegistroUsuarioRequest();
        request1.setEmail("usuario1@correo.com");

        RegistroUsuarioRequest request2 = new RegistroUsuarioRequest();
        request2.setEmail("usuario2@correo.com");

        // Act & Assert
        assertThat(request1.getEmail()).isNotEqualTo(request2.getEmail());
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest(
                NOMBRE_VALIDO, EMAIL_VALIDO, PASSWORD_VALIDA, TELEFONO_VALIDO,
                FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("ToString - Contiene información básica")
    void toString_ContieneInformacionBasica() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();
        request.setNombre(NOMBRE_VALIDO);
        request.setEmail(EMAIL_VALIDO);
        request.setPassword(PASSWORD_VALIDA);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains("RegistroUsuarioRequest");
        assertThat(resultado).contains("nombre");
        assertThat(resultado).contains("email");
        // No verificamos el contenido de password por seguridad
    }

    @Test
    @DisplayName("Nombre - Acepta diferentes longitudes válidas")
    void nombre_AceptaDiferentesLongitudesValidas() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Act & Assert - Longitud mínima (3 caracteres)
        String nombreMinimo = "Ana";
        request.setNombre(nombreMinimo);
        assertThat(request.getNombre()).hasSize(3);

        // Longitud máxima (100 caracteres)
        String nombreMaximo = "A".repeat(100);
        request.setNombre(nombreMaximo);
        assertThat(request.getNombre()).hasSize(100);

        // Nombre normal
        request.setNombre("Carlos José Martínez López");
        assertThat(request.getNombre()).contains("Martínez");

        // Nombre vacío
        request.setNombre("");
        assertThat(request.getNombre()).isEmpty();

        // Nombre nulo
        request.setNombre(null);
        assertThat(request.getNombre()).isNull();
    }

    @Test
    @DisplayName("Email - Acepta diferentes formatos válidos")
    void email_AceptaDiferentesFormatosValidos() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Act & Assert - Email estándar
        request.setEmail("usuario@dominio.com");
        assertThat(request.getEmail()).isEqualTo("usuario@dominio.com");

        // Email con subdominio
        request.setEmail("usuario@sub.dominio.com");
        assertThat(request.getEmail()).isEqualTo("usuario@sub.dominio.com");

        // Email con guiones
        request.setEmail("usuario-nombre@dominio.com");
        assertThat(request.getEmail()).isEqualTo("usuario-nombre@dominio.com");

        // Email con puntos
        request.setEmail("usuario.nombre@dominio.com");
        assertThat(request.getEmail()).isEqualTo("usuario.nombre@dominio.com");

        // Email vacío
        request.setEmail("");
        assertThat(request.getEmail()).isEmpty();

        // Email nulo
        request.setEmail(null);
        assertThat(request.getEmail()).isNull();
    }

    @Test
    @DisplayName("Password - Acepta formatos que cumplen con los requisitos")
    void password_AceptaFormatosQueCumplenConLosRequisitos() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Act & Assert - Password con mayúscula y número
        request.setPassword("Clave1234");
        assertThat(request.getPassword()).isEqualTo("Clave1234");

        // Password con caracteres especiales
        request.setPassword("Cl@ve123!");
        assertThat(request.getPassword()).isEqualTo("Cl@ve123!");

        // Password con longitud mínima
        request.setPassword("Abc12345");
        assertThat(request.getPassword()).hasSize(8);

        // Password vacía
        request.setPassword("");
        assertThat(request.getPassword()).isEmpty();

        // Password nula
        request.setPassword(null);
        assertThat(request.getPassword()).isNull();
    }

    @Test
    @DisplayName("Telefono - Acepta diferentes formatos válidos")
    void telefono_AceptaDiferentesFormatosValidos() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Act & Assert - Teléfono con código internacional
        request.setTelefono("+573001234567");
        assertThat(request.getTelefono()).isEqualTo("+573001234567");

        // Teléfono sin código internacional
        request.setTelefono("3001234567");
        assertThat(request.getTelefono()).isEqualTo("3001234567");

        // Teléfono con espacios
        request.setTelefono("+57 300 123 4567");
        assertThat(request.getTelefono()).isEqualTo("+57 300 123 4567");

        // Teléfono vacío
        request.setTelefono("");
        assertThat(request.getTelefono()).isEmpty();

        // Teléfono nulo
        request.setTelefono(null);
        assertThat(request.getTelefono()).isNull();
    }

    @Test
    @DisplayName("FechaNacimiento - Acepta fechas pasadas")
    void fechaNacimiento_AceptaFechasPasadas() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Act & Assert - Fecha reciente
        LocalDate fechaReciente = LocalDate.now().minusYears(18);
        request.setFechaNacimiento(fechaReciente);
        assertThat(request.getFechaNacimiento()).isEqualTo(fechaReciente);

        // Fecha antigua
        LocalDate fechaAntigua = LocalDate.of(1950, 1, 1);
        request.setFechaNacimiento(fechaAntigua);
        assertThat(request.getFechaNacimiento()).isEqualTo(fechaAntigua);

        // Fecha nula
        request.setFechaNacimiento(null);
        assertThat(request.getFechaNacimiento()).isNull();
    }

    @Test
    @DisplayName("FotoPerfilUrl - Acepta diferentes formatos de URL")
    void fotoPerfilUrl_AceptaDiferentesFormatosDeUrl() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Act & Assert - URL HTTPS
        request.setFotoPerfilUrl("https://cloudinary.com/usuario/foto.jpg");
        assertThat(request.getFotoPerfilUrl()).startsWith("https://");

        // URL HTTP
        request.setFotoPerfilUrl("http://misitio.com/fotos/perfil.png");
        assertThat(request.getFotoPerfilUrl()).startsWith("http://");

        // Ruta local
        request.setFotoPerfilUrl("/ruta/local/foto.jpg");
        assertThat(request.getFotoPerfilUrl()).isEqualTo("/ruta/local/foto.jpg");

        // URL vacía
        request.setFotoPerfilUrl("");
        assertThat(request.getFotoPerfilUrl()).isEmpty();

        // URL nula
        request.setFotoPerfilUrl(null);
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("Escenario - Registro completo de usuario")
    void escenario_RegistroCompletoDeUsuario() {
        // Act
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();
        request.setNombre("María García López");
        request.setEmail("maria.garcia@correo.com");
        request.setPassword("MariaClave456");
        request.setTelefono("+57 3105558899");
        request.setFechaNacimiento(LocalDate.of(1990, 5, 20));
        request.setFotoPerfilUrl("https://storage.com/fotos/maria-perfil.jpg");

        // Assert
        assertThat(request.getNombre()).isEqualTo("María García López");
        assertThat(request.getEmail()).contains("maria.garcia");
        assertThat(request.getPassword()).isEqualTo("MariaClave456");
        assertThat(request.getTelefono()).contains("3105558899");
        assertThat(request.getFechaNacimiento()).isEqualTo(LocalDate.of(1990, 5, 20));
        assertThat(request.getFotoPerfilUrl()).contains("maria-perfil");
    }

    @Test
    @DisplayName("Escenario - Registro mínimo sin campos opcionales")
    void escenario_RegistroMinimoSinCamposOpcionales() {
        // Act
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();
        // Solo campos obligatorios
        request.setNombre("Carlos Rodríguez");
        request.setEmail("carlos@correo.com");
        request.setPassword("Carlos123");
        request.setFechaNacimiento(LocalDate.of(1985, 10, 15));

        // Assert
        assertThat(request.getNombre()).isNotBlank();
        assertThat(request.getEmail()).isNotBlank();
        assertThat(request.getPassword()).isNotBlank();
        assertThat(request.getFechaNacimiento()).isNotNull();
        assertThat(request.getTelefono()).isNull();
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("Escenario - Registro de usuario joven")
    void escenario_RegistroDeUsuarioJoven() {
        // Act
        LocalDate fechaNacimientoJoven = LocalDate.now().minusYears(18).plusDays(1);
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();
        request.setNombre("Laura Martínez");
        request.setEmail("laura@correo.com");
        request.setPassword("Laura1234");
        request.setFechaNacimiento(fechaNacimientoJoven);

        // Assert
        assertThat(request.getNombre()).isEqualTo("Laura Martínez");
        assertThat(request.getFechaNacimiento()).isAfter(LocalDate.now().minusYears(18));
    }

    @Test
    @DisplayName("Casos borde - Nombre con longitud mínima exacta")
    void casosBorde_NombreConLongitudMinimaExacta() {
        // Arrange & Act
        String nombreMinimo = "Ana"; // 3 caracteres exactos
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();
        request.setNombre(nombreMinimo);

        // Assert
        assertThat(request.getNombre()).hasSize(3);
        assertThat(request.getNombre()).isEqualTo(nombreMinimo);
    }

    @Test
    @DisplayName("Casos borde - Nombre con longitud máxima exacta")
    void casosBorde_NombreConLongitudMaximaExacta() {
        // Arrange & Act
        String nombreMaximo = "A".repeat(100); // 100 caracteres exactos
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();
        request.setNombre(nombreMaximo);

        // Assert
        assertThat(request.getNombre()).hasSize(100);
        assertThat(request.getNombre()).isEqualTo(nombreMaximo);
    }

    @Test
    @DisplayName("Casos borde - Email con longitud máxima")
    void casosBorde_EmailConLongitudMaxima() {
        // Arrange & Act
        String usuario = "u".repeat(200);
        String dominio = "d".repeat(50);
        String emailLargo = usuario + "@" + dominio + ".com";

        RegistroUsuarioRequest request = new RegistroUsuarioRequest();
        request.setEmail(emailLargo);

        // Assert
        assertThat(request.getEmail()).isEqualTo(emailLargo);
        assertThat(request.getEmail()).contains("@");
    }

    @Test
    @DisplayName("Casos borde - Password con longitud mínima exacta")
    void casosBorde_PasswordConLongitudMinimaExacta() {
        // Arrange & Act
        String passwordMinimo = "Ab123456"; // 8 caracteres exactos con mayúscula y número
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();
        request.setPassword(passwordMinimo);

        // Assert
        assertThat(request.getPassword()).hasSize(8);
        assertThat(request.getPassword()).isEqualTo(passwordMinimo);
    }

    @Test
    @DisplayName("Casos borde - Fecha de nacimiento muy antigua")
    void casosBorde_FechaDeNacimientoMuyAntigua() {
        // Arrange & Act
        LocalDate fechaMuyAntigua = LocalDate.of(1900, 1, 1);
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();
        request.setFechaNacimiento(fechaMuyAntigua);

        // Assert
        assertThat(request.getFechaNacimiento()).isEqualTo(fechaMuyAntigua);
        assertThat(request.getFechaNacimiento().getYear()).isEqualTo(1900);
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        RegistroUsuarioRequest request = new RegistroUsuarioRequest(
                "Test Nombre", "test@test.com", "Test1234", "3001234567",
                LocalDate.of(1990, 1, 1), "https://test.com/foto.jpg"
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getNombre()).isEqualTo("Test Nombre");
        assertThat(request.getEmail()).isEqualTo("test@test.com");
        assertThat(request.getPassword()).isEqualTo("Test1234");
        assertThat(request.getTelefono()).isEqualTo("3001234567");
        assertThat(request.getFechaNacimiento()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(request.getFotoPerfilUrl()).isEqualTo("https://test.com/foto.jpg");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Act
        request.setNombre("Test");
        request.setEmail("test@test.com");
        request.setPassword("Test1234");
        request.setTelefono("3001234567");
        request.setFechaNacimiento(LocalDate.of(1995, 5, 15));
        request.setFotoPerfilUrl("https://test.com/foto.jpg");

        // Assert
        assertThat(request.getNombre()).isEqualTo("Test");
        assertThat(request.getEmail()).isEqualTo("test@test.com");
        assertThat(request.getPassword()).isEqualTo("Test1234");
        assertThat(request.getTelefono()).isEqualTo("3001234567");
        assertThat(request.getFechaNacimiento()).isEqualTo(LocalDate.of(1995, 5, 15));
        assertThat(request.getFotoPerfilUrl()).isEqualTo("https://test.com/foto.jpg");
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        RegistroUsuarioRequest request = new RegistroUsuarioRequest();

        // Act & Assert
        assertThatCode(() -> request.hashCode()).doesNotThrowAnyException();
    }
}