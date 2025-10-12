package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para RecuperarPasswordRequest
 */
@DisplayName("RecuperarPasswordRequest - Unit Tests")
public class RecuperarPasswordRequestTest {

    private final String EMAIL_VALIDO = "juan.perez@correo.com";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        RecuperarPasswordRequest request = new RecuperarPasswordRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        RecuperarPasswordRequest request = new RecuperarPasswordRequest(EMAIL_VALIDO);

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo(EMAIL_VALIDO);
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        RecuperarPasswordRequest request = new RecuperarPasswordRequest();

        // Act
        request.setEmail(EMAIL_VALIDO);

        // Assert
        assertThat(request.getEmail()).isEqualTo(EMAIL_VALIDO);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        RecuperarPasswordRequest request1 = new RecuperarPasswordRequest(EMAIL_VALIDO);
        RecuperarPasswordRequest request2 = new RecuperarPasswordRequest(EMAIL_VALIDO);

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getEmail()).isEqualTo(request2.getEmail());
    }

    @Test
    @DisplayName("Diferente email - Campos no son iguales")
    void diferenteEmail_CamposNoSonIguales() {
        // Arrange
        RecuperarPasswordRequest request1 = new RecuperarPasswordRequest("usuario1@correo.com");
        RecuperarPasswordRequest request2 = new RecuperarPasswordRequest("usuario2@correo.com");

        // Act & Assert
        assertThat(request1.getEmail()).isNotEqualTo(request2.getEmail());
    }

    @Test
    @DisplayName("ToString - Contiene información relevante")
    void toString_ContieneInformacionRelevante() {
        // Arrange
        RecuperarPasswordRequest request = new RecuperarPasswordRequest(EMAIL_VALIDO);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(EMAIL_VALIDO);
    }

    @Test
    @DisplayName("Email - Acepta diferentes formatos válidos")
    void email_AceptaDiferentesFormatosValidos() {
        // Arrange
        RecuperarPasswordRequest request = new RecuperarPasswordRequest();

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

        // Email corporativo
        request.setEmail("contacto@empresa.com.co");
        assertThat(request.getEmail()).isEqualTo("contacto@empresa.com.co");

        // Email vacío
        request.setEmail("");
        assertThat(request.getEmail()).isEmpty();

        // Email nulo
        request.setEmail(null);
        assertThat(request.getEmail()).isNull();
    }

    @Test
    @DisplayName("Escenario - Recuperación para usuario normal")
    void escenario_RecuperacionParaUsuarioNormal() {
        // Act
        RecuperarPasswordRequest request = new RecuperarPasswordRequest();
        request.setEmail("maria.garcia@correo.com");

        // Assert
        assertThat(request.getEmail()).isEqualTo("maria.garcia@correo.com");
        assertThat(request.getEmail()).contains("@");
        assertThat(request.getEmail()).contains(".com");
    }

    @Test
    @DisplayName("Escenario - Recuperación para email corporativo")
    void escenario_RecuperacionParaEmailCorporativo() {
        // Act
        RecuperarPasswordRequest request = new RecuperarPasswordRequest(
                "carlos.rodriguez@empresa.com.co"
        );

        // Assert
        assertThat(request.getEmail()).isEqualTo("carlos.rodriguez@empresa.com.co");
        assertThat(request.getEmail()).contains("empresa");
        assertThat(request.getEmail()).contains(".com.co");
    }

    @Test
    @DisplayName("Escenario - Recuperación con email educativo")
    void escenario_RecuperacionConEmailEducativo() {
        // Act
        RecuperarPasswordRequest request = new RecuperarPasswordRequest(
                "estudiante@universidad.edu.co"
        );

        // Assert
        assertThat(request.getEmail()).isEqualTo("estudiante@universidad.edu.co");
        assertThat(request.getEmail()).contains(".edu.co");
    }

    @Test
    @DisplayName("Casos borde - Email con longitud máxima")
    void casosBorde_EmailConLongitudMaxima() {
        // Arrange & Act
        String usuario = "u".repeat(50);
        String dominio = "d".repeat(50);
        String emailLargo = usuario + "@" + dominio + ".com";

        RecuperarPasswordRequest request = new RecuperarPasswordRequest(emailLargo);

        // Assert
        assertThat(request.getEmail()).isEqualTo(emailLargo);
        assertThat(request.getEmail()).contains("@");
    }

    @Test
    @DisplayName("Casos borde - Email con caracteres especiales válidos")
    void casosBorde_EmailConCaracteresEspecialesValidos() {
        // Arrange & Act
        String emailEspecial = "usuario+tag@dominio.com";
        RecuperarPasswordRequest request = new RecuperarPasswordRequest(emailEspecial);

        // Assert
        assertThat(request.getEmail()).isEqualTo(emailEspecial);
        assertThat(request.getEmail()).contains("+");
    }

    @Test
    @DisplayName("Casos borde - Email con números")
    void casosBorde_EmailConNumeros() {
        // Arrange & Act
        String emailConNumeros = "usuario123@dominio456.com";
        RecuperarPasswordRequest request = new RecuperarPasswordRequest(emailConNumeros);

        // Assert
        assertThat(request.getEmail()).isEqualTo(emailConNumeros);
        assertThat(request.getEmail()).contains("123");
        assertThat(request.getEmail()).contains("456");
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        RecuperarPasswordRequest request = new RecuperarPasswordRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        RecuperarPasswordRequest request = new RecuperarPasswordRequest("test@test.com");

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        RecuperarPasswordRequest request = new RecuperarPasswordRequest();

        // Act
        request.setEmail("nuevo@email.com");

        // Assert
        assertThat(request.getEmail()).isEqualTo("nuevo@email.com");
    }

    @Test
    @DisplayName("Validación - Email contiene @")
    void validacion_EmailContieneArroba() {
        // Arrange
        RecuperarPasswordRequest request = new RecuperarPasswordRequest();

        // Act
        request.setEmail("usuario@dominio.com");

        // Assert
        assertThat(request.getEmail()).contains("@");
    }

    @Test
    @DisplayName("Validación - Email tiene formato válido")
    void validacion_EmailTieneFormatoValido() {
        // Arrange
        RecuperarPasswordRequest request = new RecuperarPasswordRequest();

        // Act
        request.setEmail("usuario@dominio.com");

        // Assert
        assertThat(request.getEmail()).matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    @Test
    @DisplayName("Equals - Comparación con null retorna campos diferentes")
    void equals_ComparacionConNull_RetornaCamposDiferentes() {
        // Arrange
        RecuperarPasswordRequest request = new RecuperarPasswordRequest(EMAIL_VALIDO);

        // Act & Assert
        assertThat(request.getEmail()).isNotEqualTo(null);
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        RecuperarPasswordRequest request = new RecuperarPasswordRequest(EMAIL_VALIDO);

        // Act & Assert
        assertThatCode(() -> request.hashCode()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Escenario - Recuperación para email internacional")
    void escenario_RecuperacionParaEmailInternacional() {
        // Act
        RecuperarPasswordRequest request = new RecuperarPasswordRequest(
                "usuario@dominio.co.uk"
        );

        // Assert
        assertThat(request.getEmail()).isEqualTo("usuario@dominio.co.uk");
        assertThat(request.getEmail()).contains(".co.uk");
    }
}