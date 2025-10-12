package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para RestablecerPasswordRequest
 */
@DisplayName("RestablecerPasswordRequest - Unit Tests")
public class RestablecerPasswordRequestTest {

    private final String EMAIL_VALIDO = "juan.perez@correo.com";
    private final String CODIGO_VALIDO = "ABC123XYZ";
    private final String NUEVA_PASSWORD_VALIDA = "NuevaClave123";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isNull();
        assertThat(request.getCodigo()).isNull();
        assertThat(request.getNuevaPassword()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        RestablecerPasswordRequest request = new RestablecerPasswordRequest(
                EMAIL_VALIDO, CODIGO_VALIDO, NUEVA_PASSWORD_VALIDA
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(request.getCodigo()).isEqualTo(CODIGO_VALIDO);
        assertThat(request.getNuevaPassword()).isEqualTo(NUEVA_PASSWORD_VALIDA);
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();

        // Act
        request.setEmail(EMAIL_VALIDO);
        request.setCodigo(CODIGO_VALIDO);
        request.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        // Assert
        assertThat(request.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(request.getCodigo()).isEqualTo(CODIGO_VALIDO);
        assertThat(request.getNuevaPassword()).isEqualTo(NUEVA_PASSWORD_VALIDA);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        RestablecerPasswordRequest request1 = new RestablecerPasswordRequest(
                EMAIL_VALIDO, CODIGO_VALIDO, NUEVA_PASSWORD_VALIDA
        );
        RestablecerPasswordRequest request2 = new RestablecerPasswordRequest(
                EMAIL_VALIDO, CODIGO_VALIDO, NUEVA_PASSWORD_VALIDA
        );

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getEmail()).isEqualTo(request2.getEmail());
        assertThat(request1.getCodigo()).isEqualTo(request2.getCodigo());
        assertThat(request1.getNuevaPassword()).isEqualTo(request2.getNuevaPassword());
    }

    @Test
    @DisplayName("Diferente email - Campos no son iguales")
    void diferenteEmail_CamposNoSonIguales() {
        // Arrange
        RestablecerPasswordRequest request1 = new RestablecerPasswordRequest();
        request1.setEmail("usuario1@correo.com");

        RestablecerPasswordRequest request2 = new RestablecerPasswordRequest();
        request2.setEmail("usuario2@correo.com");

        // Act & Assert
        assertThat(request1.getEmail()).isNotEqualTo(request2.getEmail());
    }

    @Test
    @DisplayName("Diferente codigo - Campos no son iguales")
    void diferenteCodigo_CamposNoSonIguales() {
        // Arrange
        RestablecerPasswordRequest request1 = new RestablecerPasswordRequest();
        request1.setCodigo("CODIGO123");

        RestablecerPasswordRequest request2 = new RestablecerPasswordRequest();
        request2.setCodigo("CODIGO456");

        // Act & Assert
        assertThat(request1.getCodigo()).isNotEqualTo(request2.getCodigo());
    }

    @Test
    @DisplayName("Diferente nuevaPassword - Campos no son iguales")
    void diferenteNuevaPassword_CamposNoSonIguales() {
        // Arrange
        RestablecerPasswordRequest request1 = new RestablecerPasswordRequest();
        request1.setNuevaPassword("Password1");

        RestablecerPasswordRequest request2 = new RestablecerPasswordRequest();
        request2.setNuevaPassword("Password2");

        // Act & Assert
        assertThat(request1.getNuevaPassword()).isNotEqualTo(request2.getNuevaPassword());
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        RestablecerPasswordRequest request = new RestablecerPasswordRequest(
                EMAIL_VALIDO, CODIGO_VALIDO, NUEVA_PASSWORD_VALIDA
        );

        // Act & Assert
        assertThatCode(() -> request.hashCode()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        RestablecerPasswordRequest request = new RestablecerPasswordRequest(
                EMAIL_VALIDO, CODIGO_VALIDO, NUEVA_PASSWORD_VALIDA
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
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();
        request.setEmail(EMAIL_VALIDO);
        request.setCodigo(CODIGO_VALIDO);
        request.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains("RestablecerPasswordRequest");
        assertThat(resultado).contains("email");
        assertThat(resultado).contains("codigo");
    }

    @Test
    @DisplayName("Email - Acepta diferentes formatos válidos")
    void email_AceptaDiferentesFormatosValidos() {
        // Arrange
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();

        // Act & Assert - Email estándar
        request.setEmail("usuario@dominio.com");
        assertThat(request.getEmail()).isEqualTo("usuario@dominio.com");

        // Email con subdominio
        request.setEmail("usuario@sub.dominio.com");
        assertThat(request.getEmail()).isEqualTo("usuario@sub.dominio.com");

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
    @DisplayName("Codigo - Acepta diferentes longitudes válidas")
    void codigo_AceptaDiferentesLongitudesValidas() {
        // Arrange
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();

        // Act & Assert - Longitud mínima (6 caracteres)
        String codigoMinimo = "123456";
        request.setCodigo(codigoMinimo);
        assertThat(request.getCodigo()).hasSize(6);

        // Longitud máxima (10 caracteres)
        String codigoMaximo = "ABCDEF1234";
        request.setCodigo(codigoMaximo);
        assertThat(request.getCodigo()).hasSize(10);

        // Código alfanumérico
        request.setCodigo("A1B2C3D4");
        assertThat(request.getCodigo()).isEqualTo("A1B2C3D4");

        // Código vacío
        request.setCodigo("");
        assertThat(request.getCodigo()).isEmpty();

        // Código nulo
        request.setCodigo(null);
        assertThat(request.getCodigo()).isNull();
    }

    @Test
    @DisplayName("NuevaPassword - Acepta formatos que cumplen con los requisitos")
    void nuevaPassword_AceptaFormatosQueCumplenConLosRequisitos() {
        // Arrange
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();

        // Act & Assert - Password con mayúscula y número
        request.setNuevaPassword("ClaveNueva123");
        assertThat(request.getNuevaPassword()).isEqualTo("ClaveNueva123");

        // Password con caracteres especiales
        request.setNuevaPassword("Cl@veNuev@123!");
        assertThat(request.getNuevaPassword()).isEqualTo("Cl@veNuev@123!");

        // Password con longitud mínima
        request.setNuevaPassword("Abc12345");
        assertThat(request.getNuevaPassword()).hasSize(8);

        // Password vacía
        request.setNuevaPassword("");
        assertThat(request.getNuevaPassword()).isEmpty();

        // Password nula
        request.setNuevaPassword(null);
        assertThat(request.getNuevaPassword()).isNull();
    }

    @Test
    @DisplayName("Escenario - Restablecimiento completo exitoso")
    void escenario_RestablecimientoCompletoExitoso() {
        // Act
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();
        request.setEmail("maria.garcia@correo.com");
        request.setCodigo("MAR123XYZ");
        request.setNuevaPassword("MariaNuevaClave456");

        // Assert
        assertThat(request.getEmail()).isEqualTo("maria.garcia@correo.com");
        assertThat(request.getCodigo()).isEqualTo("MAR123XYZ");
        assertThat(request.getNuevaPassword()).isEqualTo("MariaNuevaClave456");
        assertThat(request.getNuevaPassword()).hasSizeGreaterThanOrEqualTo(8);
    }

    @Test
    @DisplayName("Escenario - Restablecimiento con código numérico")
    void escenario_RestablecimientoConCodigoNumerico() {
        // Act
        RestablecerPasswordRequest request = new RestablecerPasswordRequest(
                "carlos@correo.com",
                "987654",
                "CarlosClave789"
        );

        // Assert
        assertThat(request.getEmail()).contains("carlos");
        assertThat(request.getCodigo()).isEqualTo("987654");
        assertThat(request.getNuevaPassword()).contains("Carlos");
        assertThat(request.getCodigo()).hasSize(6);
    }

    @Test
    @DisplayName("Escenario - Restablecimiento con contraseña compleja")
    void escenario_RestablecimientoConPasswordCompleja() {
        // Act
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();
        request.setEmail("admin@empresa.com");
        request.setCodigo("ADM1N2024");
        request.setNuevaPassword("P@ssw0rd!Segur@2024");

        // Assert
        assertThat(request.getEmail()).isEqualTo("admin@empresa.com");
        assertThat(request.getCodigo()).isEqualTo("ADM1N2024");
        assertThat(request.getNuevaPassword()).isEqualTo("P@ssw0rd!Segur@2024");
        assertThat(request.getNuevaPassword()).hasSizeGreaterThan(15);
    }

    @Test
    @DisplayName("Casos borde - Código con longitud mínima exacta")
    void casosBorde_CodigoConLongitudMinimaExacta() {
        // Arrange & Act
        String codigoMinimoExacto = "123456"; // 6 caracteres exactos
        RestablecerPasswordRequest request = new RestablecerPasswordRequest(
                EMAIL_VALIDO, codigoMinimoExacto, NUEVA_PASSWORD_VALIDA
        );

        // Assert
        assertThat(request.getCodigo()).hasSize(6);
        assertThat(request.getCodigo()).isEqualTo(codigoMinimoExacto);
    }

    @Test
    @DisplayName("Casos borde - Código con longitud máxima exacta")
    void casosBorde_CodigoConLongitudMaximaExacta() {
        // Arrange & Act
        String codigoMaximoExacto = "ABCDEF1234"; // 10 caracteres exactos
        RestablecerPasswordRequest request = new RestablecerPasswordRequest(
                EMAIL_VALIDO, codigoMaximoExacto, NUEVA_PASSWORD_VALIDA
        );

        // Assert
        assertThat(request.getCodigo()).hasSize(10);
        assertThat(request.getCodigo()).isEqualTo(codigoMaximoExacto);
    }

    @Test
    @DisplayName("Casos borde - Password con longitud mínima exacta")
    void casosBorde_PasswordConLongitudMinimaExacta() {
        // Arrange & Act
        String passwordMinimoExacto = "Ab123456"; // 8 caracteres exactos con mayúscula y número
        RestablecerPasswordRequest request = new RestablecerPasswordRequest(
                EMAIL_VALIDO, CODIGO_VALIDO, passwordMinimoExacto
        );

        // Assert
        assertThat(request.getNuevaPassword()).hasSize(8);
        assertThat(request.getNuevaPassword()).isEqualTo(passwordMinimoExacto);
    }

    @Test
    @DisplayName("Casos borde - Email con longitud extensa")
    void casosBorde_EmailConLongitudExtensa() {
        // Arrange & Act
        String usuario = "u".repeat(100);
        String dominio = "d".repeat(100);
        String emailLargo = usuario + "@" + dominio + ".com";

        RestablecerPasswordRequest request = new RestablecerPasswordRequest();
        request.setEmail(emailLargo);

        // Assert
        assertThat(request.getEmail()).isEqualTo(emailLargo);
        assertThat(request.getEmail()).contains("@");
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        RestablecerPasswordRequest request = new RestablecerPasswordRequest(
                "test@test.com", "TEST123", "TestPassword123"
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo("test@test.com");
        assertThat(request.getCodigo()).isEqualTo("TEST123");
        assertThat(request.getNuevaPassword()).isEqualTo("TestPassword123");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();

        // Act
        request.setEmail("nuevo@email.com");
        request.setCodigo("NUEVO456");
        request.setNuevaPassword("NuevaClave789");

        // Assert
        assertThat(request.getEmail()).isEqualTo("nuevo@email.com");
        assertThat(request.getCodigo()).isEqualTo("NUEVO456");
        assertThat(request.getNuevaPassword()).isEqualTo("NuevaClave789");
    }

    @Test
    @DisplayName("Validación - Campos obligatorios no están vacíos cuando se establecen")
    void validacion_CamposObligatoriosNoEstanVaciosCuandoSeEstablecen() {
        // Arrange
        RestablecerPasswordRequest request = new RestablecerPasswordRequest();

        // Act
        request.setEmail("usuario@correo.com");
        request.setCodigo("COD123");
        request.setNuevaPassword("ClaveValida1");

        // Assert
        assertThat(request.getEmail()).isNotBlank();
        assertThat(request.getCodigo()).isNotBlank();
        assertThat(request.getNuevaPassword()).isNotBlank();
    }
}