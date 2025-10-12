package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para CambiarPasswordRequest
 */
@DisplayName("CambiarPasswordRequest - Unit Tests")
public class CambiarPasswordRequestTest {

    private final String PASSWORD_ACTUAL_VALIDA = "ClaveActual1";
    private final String NUEVA_PASSWORD_VALIDA = "ClaveNueva2";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        CambiarPasswordRequest request = new CambiarPasswordRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getPasswordActual()).isNull();
        assertThat(request.getNuevaPassword()).isNull();
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        CambiarPasswordRequest request = new CambiarPasswordRequest();

        // Act
        request.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        // Assert
        assertThat(request.getPasswordActual()).isEqualTo(PASSWORD_ACTUAL_VALIDA);
        assertThat(request.getNuevaPassword()).isEqualTo(NUEVA_PASSWORD_VALIDA);
    }

    @Test
    @DisplayName("Equals - Mismos valores retorna true")
    void equals_MismosValores_RetornaTrue() {
        // Arrange
        CambiarPasswordRequest request1 = new CambiarPasswordRequest();
        request1.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request1.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        CambiarPasswordRequest request2 = new CambiarPasswordRequest();
        request2.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request2.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        // Act & Assert
        assertThat(request1).isEqualTo(request2);
    }

    @Test
    @DisplayName("Equals - Diferente password actual retorna false")
    void equals_DiferentePasswordActual_RetornaFalse() {
        // Arrange
        CambiarPasswordRequest request1 = new CambiarPasswordRequest();
        request1.setPasswordActual("Password1");
        request1.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        CambiarPasswordRequest request2 = new CambiarPasswordRequest();
        request2.setPasswordActual("Password2");
        request2.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        // Act & Assert
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @DisplayName("Equals - Diferente nueva password retorna false")
    void equals_DiferenteNuevaPassword_RetornaFalse() {
        // Arrange
        CambiarPasswordRequest request1 = new CambiarPasswordRequest();
        request1.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request1.setNuevaPassword("Nueva1");

        CambiarPasswordRequest request2 = new CambiarPasswordRequest();
        request2.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request2.setNuevaPassword("Nueva2");

        // Act & Assert
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @DisplayName("HashCode - Mismos valores mismo hashCode")
    void hashCode_MismosValores_MismoHashCode() {
        // Arrange
        CambiarPasswordRequest request1 = new CambiarPasswordRequest();
        request1.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request1.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        CambiarPasswordRequest request2 = new CambiarPasswordRequest();
        request2.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request2.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        // Act & Assert
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        CambiarPasswordRequest request = new CambiarPasswordRequest();
        request.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("Campos - Aceptan diferentes formatos de contraseña")
    void campos_AceptanDiferentesFormatosDePassword() {
        // Arrange
        CambiarPasswordRequest request = new CambiarPasswordRequest();

        // Act & Assert
        request.setPasswordActual("clave123");
        assertThat(request.getPasswordActual()).isEqualTo("clave123");

        request.setNuevaPassword("NUEVA_CLAVE");
        assertThat(request.getNuevaPassword()).isEqualTo("NUEVA_CLAVE");

        request.setPasswordActual("clave.con.puntos");
        assertThat(request.getPasswordActual()).isEqualTo("clave.con.puntos");

        request.setNuevaPassword("");
        assertThat(request.getNuevaPassword()).isEmpty();

        request.setPasswordActual(null);
        assertThat(request.getPasswordActual()).isNull();
    }

    @Test
    @DisplayName("Escenario - Cambio de contraseña normal")
    void escenario_CambioDePasswordNormal() {
        // Act
        CambiarPasswordRequest request = new CambiarPasswordRequest();
        request.setPasswordActual("MiClaveActual123");
        request.setNuevaPassword("MiNuevaClave456");

        // Assert
        assertThat(request.getPasswordActual()).isEqualTo("MiClaveActual123");
        assertThat(request.getNuevaPassword()).isEqualTo("MiNuevaClave456");
        assertThat(request.getNuevaPassword()).isNotEqualTo(request.getPasswordActual());
    }

    @Test
    @DisplayName("Equals - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // Arrange
        CambiarPasswordRequest request = new CambiarPasswordRequest();
        request.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        // Act & Assert
        assertThat(request).isNotEqualTo(null);
    }

    @Test
    @DisplayName("Equals - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // Arrange
        CambiarPasswordRequest request = new CambiarPasswordRequest();
        request.setPasswordActual(PASSWORD_ACTUAL_VALIDA);
        request.setNuevaPassword(NUEVA_PASSWORD_VALIDA);

        // Act & Assert
        assertThat(request).isNotEqualTo("No soy un request");
    }
}