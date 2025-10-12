package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para CancelarReservaRequest
 */
@DisplayName("CancelarReservaRequest - Unit Tests")
public class CancelarReservaRequestTest {

    private final String MOTIVO_VALIDO = "Cambio de planes por motivos personales";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        CancelarReservaRequest request = new CancelarReservaRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getMotivoCancelacion()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        CancelarReservaRequest request = new CancelarReservaRequest(MOTIVO_VALIDO);

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getMotivoCancelacion()).isEqualTo(MOTIVO_VALIDO);
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        CancelarReservaRequest request = new CancelarReservaRequest();

        // Act
        request.setMotivoCancelacion(MOTIVO_VALIDO);

        // Assert
        assertThat(request.getMotivoCancelacion()).isEqualTo(MOTIVO_VALIDO);
    }

    @Test
    @DisplayName("Equals - Mismos valores retorna true")
    void equals_MismosValores_RetornaTrue() {
        // Arrange
        CancelarReservaRequest request1 = new CancelarReservaRequest(MOTIVO_VALIDO);
        CancelarReservaRequest request2 = new CancelarReservaRequest(MOTIVO_VALIDO);

        // Act & Assert
        assertThat(request1).isEqualTo(request2);
    }

    @Test
    @DisplayName("Equals - Diferente motivo retorna false")
    void equals_DiferenteMotivo_RetornaFalse() {
        // Arrange
        CancelarReservaRequest request1 = new CancelarReservaRequest("Motivo 1");
        CancelarReservaRequest request2 = new CancelarReservaRequest("Motivo 2");

        // Act & Assert
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @DisplayName("HashCode - Mismos valores mismo hashCode")
    void hashCode_MismosValores_MismoHashCode() {
        // Arrange
        CancelarReservaRequest request1 = new CancelarReservaRequest(MOTIVO_VALIDO);
        CancelarReservaRequest request2 = new CancelarReservaRequest(MOTIVO_VALIDO);

        // Act & Assert
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    @DisplayName("ToString - Contiene información relevante")
    void toString_ContieneInformacionRelevante() {
        // Arrange
        CancelarReservaRequest request = new CancelarReservaRequest(MOTIVO_VALIDO);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(MOTIVO_VALIDO);
    }

    @Test
    @DisplayName("Motivo cancelación - Acepta diferentes longitudes válidas")
    void motivoCancelacion_AceptaDiferentesLongitudesValidas() {
        // Arrange
        CancelarReservaRequest request = new CancelarReservaRequest();

        // Act & Assert - Longitud mínima (10 caracteres)
        String motivoMinimo = "A".repeat(10);
        request.setMotivoCancelacion(motivoMinimo);
        assertThat(request.getMotivoCancelacion()).hasSize(10);

        // Longitud máxima (500 caracteres)
        String motivoMaximo = "B".repeat(500);
        request.setMotivoCancelacion(motivoMaximo);
        assertThat(request.getMotivoCancelacion()).hasSize(500);

        // Motivo normal
        request.setMotivoCancelacion("Problemas de salud imprevistos");
        assertThat(request.getMotivoCancelacion()).contains("salud");

        // Vacío
        request.setMotivoCancelacion("");
        assertThat(request.getMotivoCancelacion()).isEmpty();

        // Null
        request.setMotivoCancelacion(null);
        assertThat(request.getMotivoCancelacion()).isNull();
    }

    @Test
    @DisplayName("Escenario - Cancelación por motivos personales")
    void escenario_CancelacionPorMotivosPersonales() {
        // Act
        CancelarReservaRequest request = new CancelarReservaRequest();
        request.setMotivoCancelacion("Emergencia familiar que requiere mi atención inmediata");

        // Assert
        assertThat(request.getMotivoCancelacion()).isNotBlank();
        assertThat(request.getMotivoCancelacion()).hasSizeGreaterThan(10);
        assertThat(request.getMotivoCancelacion()).contains("familiar");
    }

    @Test
    @DisplayName("Escenario - Cancelación por cambio de planes")
    void escenario_CancelacionPorCambioDePlanes() {
        // Act
        CancelarReservaRequest request = new CancelarReservaRequest(
                "He decidido cambiar mis fechas de viaje debido a compromisos laborales imprevistos"
        );

        // Assert
        assertThat(request.getMotivoCancelacion()).isNotNull();
        assertThat(request.getMotivoCancelacion()).hasSizeGreaterThan(20);
    }

    @Test
    @DisplayName("Equals - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // Arrange
        CancelarReservaRequest request = new CancelarReservaRequest(MOTIVO_VALIDO);

        // Act & Assert
        assertThat(request).isNotEqualTo(null);
    }

    @Test
    @DisplayName("Equals - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // Arrange
        CancelarReservaRequest request = new CancelarReservaRequest(MOTIVO_VALIDO);

        // Act & Assert
        assertThat(request).isNotEqualTo("No soy un request");
    }

    @Test
    @DisplayName("Casos borde - Motivo con longitud exacta mínima")
    void casosBorde_MotivoConLongitudExactaMinima() {
        // Arrange & Act
        String motivoMinimoExacto = "1234567890"; // 10 caracteres exactos
        CancelarReservaRequest request = new CancelarReservaRequest(motivoMinimoExacto);

        // Assert
        assertThat(request.getMotivoCancelacion()).hasSize(10);
        assertThat(request.getMotivoCancelacion()).isEqualTo(motivoMinimoExacto);
    }

    @Test
    @DisplayName("Casos borde - Motivo con longitud exacta máxima")
    void casosBorde_MotivoConLongitudExactaMaxima() {
        // Arrange & Act
        String motivoMaximoExacto = "M".repeat(500); // 500 caracteres exactos
        CancelarReservaRequest request = new CancelarReservaRequest(motivoMaximoExacto);

        // Assert
        assertThat(request.getMotivoCancelacion()).hasSize(500);
        assertThat(request.getMotivoCancelacion()).isEqualTo(motivoMaximoExacto);
    }

    @Test
    @DisplayName("Lombok - Anotaciones funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // Arrange
        CancelarReservaRequest request1 = new CancelarReservaRequest(MOTIVO_VALIDO);
        CancelarReservaRequest request2 = new CancelarReservaRequest(MOTIVO_VALIDO);

        // Assert
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.toString()).isNotNull();
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());

        // Verificar constructor sin parámetros
        CancelarReservaRequest requestVacio = new CancelarReservaRequest();
        assertThat(requestVacio).isNotNull();

        // Verificar getters y setters
        requestVacio.setMotivoCancelacion("Test");
        assertThat(requestVacio.getMotivoCancelacion()).isEqualTo("Test");
    }
}