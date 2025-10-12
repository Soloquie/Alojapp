package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para LoginRequest
 */
@DisplayName("LoginRequest - Unit Tests")
public class LoginRequestTest {

    private final String EMAIL_VALIDO = "juan.perez@correo.com";
    private final String PASSWORD_VALIDO = "MiClave123";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        LoginRequest request = new LoginRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isNull();
        assertThat(request.getPassword()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        LoginRequest request = new LoginRequest(EMAIL_VALIDO, PASSWORD_VALIDO);

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(request.getPassword()).isEqualTo(PASSWORD_VALIDO);
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        LoginRequest request = new LoginRequest();

        // Act
        request.setEmail(EMAIL_VALIDO);
        request.setPassword(PASSWORD_VALIDO);

        // Assert
        assertThat(request.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(request.getPassword()).isEqualTo(PASSWORD_VALIDO);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        LoginRequest request1 = new LoginRequest(EMAIL_VALIDO, PASSWORD_VALIDO);
        LoginRequest request2 = new LoginRequest(EMAIL_VALIDO, PASSWORD_VALIDO);

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getEmail()).isEqualTo(request2.getEmail());
        assertThat(request1.getPassword()).isEqualTo(request2.getPassword());
    }

    @Test
    @DisplayName("Diferente email - Campos no son iguales")
    void diferenteEmail_CamposNoSonIguales() {
        // Arrange
        LoginRequest request1 = new LoginRequest("usuario1@correo.com", PASSWORD_VALIDO);
        LoginRequest request2 = new LoginRequest("usuario2@correo.com", PASSWORD_VALIDO);

        // Act & Assert
        assertThat(request1.getEmail()).isNotEqualTo(request2.getEmail());
    }

    @Test
    @DisplayName("Diferente password - Campos no son iguales")
    void diferentePassword_CamposNoSonIguales() {
        // Arrange
        LoginRequest request1 = new LoginRequest(EMAIL_VALIDO, "Password1");
        LoginRequest request2 = new LoginRequest(EMAIL_VALIDO, "Password2");

        // Act & Assert
        assertThat(request1.getPassword()).isNotEqualTo(request2.getPassword());
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        LoginRequest request = new LoginRequest(EMAIL_VALIDO, PASSWORD_VALIDO);

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
        LoginRequest request = new LoginRequest(EMAIL_VALIDO, PASSWORD_VALIDO);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains("LoginRequest");
        assertThat(resultado).contains("email");
        // No verificamos el contenido de password por seguridad
    }

    @Test
    @DisplayName("Email - Acepta diferentes formatos válidos")
    void email_AceptaDiferentesFormatosValidos() {
        // Arrange
        LoginRequest request = new LoginRequest();

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
    @DisplayName("Password - Acepta diferentes formatos")
    void password_AceptaDiferentesFormatos() {
        // Arrange
        LoginRequest request = new LoginRequest();

        // Act & Assert - Password con números
        request.setPassword("clave123");
        assertThat(request.getPassword()).isEqualTo("clave123");

        // Password con mayúsculas y minúsculas
        request.setPassword("ClaveConMayusculas");
        assertThat(request.getPassword()).isEqualTo("ClaveConMayusculas");

        // Password con caracteres especiales
        request.setPassword("clave@especial#123");
        assertThat(request.getPassword()).isEqualTo("clave@especial#123");

        // Password corta
        request.setPassword("a");
        assertThat(request.getPassword()).hasSize(1);

        // Password vacía
        request.setPassword("");
        assertThat(request.getPassword()).isEmpty();

        // Password nula
        request.setPassword(null);
        assertThat(request.getPassword()).isNull();
    }

    @Test
    @DisplayName("Escenario - Login normal")
    void escenario_LoginNormal() {
        // Act
        LoginRequest request = new LoginRequest();
        request.setEmail("maria.garcia@correo.com");
        request.setPassword("PasswordSegura456");

        // Assert
        assertThat(request.getEmail()).isEqualTo("maria.garcia@correo.com");
        assertThat(request.getPassword()).isEqualTo("PasswordSegura456");
        assertThat(request.getEmail()).contains("@");
        assertThat(request.getPassword()).isNotBlank();
    }

    @Test
    @DisplayName("Escenario - Login con email corporativo")
    void escenario_LoginConEmailCorporativo() {
        // Act
        LoginRequest request = new LoginRequest(
                "carlos.rodriguez@empresa.com.co",
                "ClaveCorporativa2024!"
        );

        // Assert
        assertThat(request.getEmail()).isEqualTo("carlos.rodriguez@empresa.com.co");
        assertThat(request.getPassword()).isEqualTo("ClaveCorporativa2024!");
        assertThat(request.getEmail()).contains("empresa");
        assertThat(request.getPassword()).contains("2024");
    }

    @Test
    @DisplayName("Escenario - Login con credenciales mínimas")
    void escenario_LoginConCredencialesMinimas() {
        // Act
        LoginRequest request = new LoginRequest("a@b.c", "1");

        // Assert
        assertThat(request.getEmail()).isEqualTo("a@b.c");
        assertThat(request.getPassword()).isEqualTo("1");
        assertThat(request.getEmail()).hasSize(5);
        assertThat(request.getPassword()).hasSize(1);
    }

    @Test
    @DisplayName("Casos borde - Email con longitud máxima")
    void casosBorde_EmailConLongitudMaxima() {
        // Arrange & Act
        String usuario = "u".repeat(50);
        String dominio = "d".repeat(50);
        String emailLargo = usuario + "@" + dominio + ".com";

        LoginRequest request = new LoginRequest(emailLargo, PASSWORD_VALIDO);

        // Assert
        assertThat(request.getEmail()).isEqualTo(emailLargo);
        assertThat(request.getEmail()).contains("@");
    }

    @Test
    @DisplayName("Casos borde - Password con longitud extensa")
    void casosBorde_PasswordConLongitudExtensa() {
        // Arrange & Act
        String passwordLargo = "P".repeat(1000);
        LoginRequest request = new LoginRequest(EMAIL_VALIDO, passwordLargo);

        // Assert
        assertThat(request.getPassword()).hasSize(1000);
        assertThat(request.getPassword()).isEqualTo(passwordLargo);
    }

    @Test
    @DisplayName("Casos borde - Email con caracteres especiales válidos")
    void casosBorde_EmailConCaracteresEspecialesValidos() {
        // Arrange & Act
        String emailEspecial = "usuario+tag@dominio.com";
        LoginRequest request = new LoginRequest(emailEspecial, PASSWORD_VALIDO);

        // Assert
        assertThat(request.getEmail()).isEqualTo(emailEspecial);
        assertThat(request.getEmail()).contains("+");
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        LoginRequest request = new LoginRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        LoginRequest request = new LoginRequest("test@test.com", "test123");

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo("test@test.com");
        assertThat(request.getPassword()).isEqualTo("test123");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        LoginRequest request = new LoginRequest();

        // Act
        request.setEmail("nuevo@email.com");
        request.setPassword("nuevaClave456");

        // Assert
        assertThat(request.getEmail()).isEqualTo("nuevo@email.com");
        assertThat(request.getPassword()).isEqualTo("nuevaClave456");
    }

    @Test
    @DisplayName("Validación - Email contiene @")
    void validacion_EmailContieneArroba() {
        // Arrange
        LoginRequest request = new LoginRequest();

        // Act
        request.setEmail("usuario@dominio.com");

        // Assert
        assertThat(request.getEmail()).contains("@");
    }

    @Test
    @DisplayName("Validación - Password no está vacío cuando se establece")
    void validacion_PasswordNoEstaVacioCuandoSeEstablece() {
        // Arrange
        LoginRequest request = new LoginRequest();

        // Act
        request.setPassword("claveNoVacia");

        // Assert
        assertThat(request.getPassword()).isNotBlank();
    }

    @Test
    @DisplayName("Equals - Comparación con null retorna campos diferentes")
    void equals_ComparacionConNull_RetornaCamposDiferentes() {
        // Arrange
        LoginRequest request = new LoginRequest(EMAIL_VALIDO, PASSWORD_VALIDO);

        // Act & Assert
        assertThat(request.getEmail()).isNotEqualTo(null);
        assertThat(request.getPassword()).isNotEqualTo(null);
    }
}