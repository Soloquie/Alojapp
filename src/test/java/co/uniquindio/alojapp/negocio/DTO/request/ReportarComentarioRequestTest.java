package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ReportarComentarioRequest
 */
@DisplayName("ReportarComentarioRequest - Unit Tests")
public class ReportarComentarioRequestTest {

    private final String MOTIVO_VALIDO = "El comentario contiene lenguaje ofensivo.";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        ReportarComentarioRequest request = new ReportarComentarioRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getMotivo()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        ReportarComentarioRequest request = new ReportarComentarioRequest(MOTIVO_VALIDO);

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getMotivo()).isEqualTo(MOTIVO_VALIDO);
    }

    @Test
    @DisplayName("Builder - Crea instancia correctamente")
    void builder_CreaInstanciaCorrectamente() {
        // Act
        ReportarComentarioRequest request = ReportarComentarioRequest.builder()
                .motivo(MOTIVO_VALIDO)
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getMotivo()).isEqualTo(MOTIVO_VALIDO);
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        ReportarComentarioRequest request = new ReportarComentarioRequest();

        // Act
        request.setMotivo(MOTIVO_VALIDO);

        // Assert
        assertThat(request.getMotivo()).isEqualTo(MOTIVO_VALIDO);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        ReportarComentarioRequest request1 = new ReportarComentarioRequest(MOTIVO_VALIDO);
        ReportarComentarioRequest request2 = new ReportarComentarioRequest(MOTIVO_VALIDO);

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getMotivo()).isEqualTo(request2.getMotivo());
    }

    @Test
    @DisplayName("Diferente motivo - Campos no son iguales")
    void diferenteMotivo_CamposNoSonIguales() {
        // Arrange
        ReportarComentarioRequest request1 = new ReportarComentarioRequest("Motivo 1");
        ReportarComentarioRequest request2 = new ReportarComentarioRequest("Motivo 2");

        // Act & Assert
        assertThat(request1.getMotivo()).isNotEqualTo(request2.getMotivo());
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        ReportarComentarioRequest request = new ReportarComentarioRequest(MOTIVO_VALIDO);

        // Act & Assert
        assertThatCode(() -> request.hashCode()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        ReportarComentarioRequest request = new ReportarComentarioRequest(MOTIVO_VALIDO);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("ToString - Contiene información relevante")
    void toString_ContieneInformacionRelevante() {
        // Arrange
        ReportarComentarioRequest request = new ReportarComentarioRequest(MOTIVO_VALIDO);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).contains("ReportarComentarioRequest");
        assertThat(resultado).contains("motivo");
    }

    @Test
    @DisplayName("Motivo - Acepta diferentes tipos de reportes")
    void motivo_AceptaDiferentesTiposDeReportes() {
        // Arrange
        ReportarComentarioRequest request = new ReportarComentarioRequest();

        // Act & Assert - Reporte por lenguaje ofensivo
        request.setMotivo("El comentario contiene lenguaje ofensivo y groserías.");
        assertThat(request.getMotivo()).contains("ofensivo");

        // Reporte por información falsa
        request.setMotivo("La información proporcionada en el comentario es falsa.");
        assertThat(request.getMotivo()).contains("falsa");

        // Reporte por spam
        request.setMotivo("El comentario es spam con enlaces promocionales.");
        assertThat(request.getMotivo()).contains("spam");

        // Reporte por contenido inapropiado
        request.setMotivo("El comentario contiene contenido sexual explícito.");
        assertThat(request.getMotivo()).contains("sexual");

        // Motivo vacío
        request.setMotivo("");
        assertThat(request.getMotivo()).isEmpty();

        // Motivo nulo
        request.setMotivo(null);
        assertThat(request.getMotivo()).isNull();
    }

    @Test
    @DisplayName("Escenario - Reporte por lenguaje ofensivo")
    void escenario_ReportePorLenguajeOfensivo() {
        // Act
        ReportarComentarioRequest request = ReportarComentarioRequest.builder()
                .motivo("El usuario utilizó lenguaje ofensivo y groserías en su comentario.")
                .build();

        // Assert
        assertThat(request.getMotivo()).isNotBlank();
        assertThat(request.getMotivo()).contains("ofensivo");
        assertThat(request.getMotivo()).contains("groserías");
    }

    @Test
    @DisplayName("Escenario - Reporte por información falsa")
    void escenario_ReportePorInformacionFalsa() {
        // Act
        ReportarComentarioRequest request = new ReportarComentarioRequest();
        request.setMotivo("El comentario contiene afirmaciones falsas sobre el alojamiento.");

        // Assert
        assertThat(request.getMotivo()).isEqualTo("El comentario contiene afirmaciones falsas sobre el alojamiento.");
        assertThat(request.getMotivo()).contains("falsas");
    }

    @Test
    @DisplayName("Escenario - Reporte por spam")
    void escenario_ReportePorSpam() {
        // Act
        ReportarComentarioRequest request = new ReportarComentarioRequest(
                "Comentario repetitivo con enlaces promocionales no autorizados."
        );

        // Assert
        assertThat(request.getMotivo()).isNotNull();
        assertThat(request.getMotivo()).contains("promocionales");
        assertThat(request.getMotivo()).hasSizeGreaterThan(10);
    }

    @Test
    @DisplayName("Escenario - Reporte por acoso")
    void escenario_ReportePorAcoso() {
        // Act
        ReportarComentarioRequest request = ReportarComentarioRequest.builder()
                .motivo("El comentario contiene acoso personal hacia otro usuario.")
                .build();

        // Assert
        assertThat(request.getMotivo()).isNotBlank();
        assertThat(request.getMotivo()).contains("acoso");
        assertThat(request.getMotivo()).contains("personal");
    }

    @Test
    @DisplayName("Casos borde - Motivo con longitud extensa")
    void casosBorde_MotivoConLongitudExtensa() {
        // Arrange & Act
        String motivoLargo = "M".repeat(1000);
        ReportarComentarioRequest request = new ReportarComentarioRequest(motivoLargo);

        // Assert
        assertThat(request.getMotivo()).hasSize(1000);
        assertThat(request.getMotivo()).isEqualTo(motivoLargo);
    }

    @Test
    @DisplayName("Casos borde - Motivo con caracteres especiales")
    void casosBorde_MotivoConCaracteresEspeciales() {
        // Arrange & Act
        String motivoEspecial = "Motivo con caracteres: ¡@#$%^&*()_+{}[]:;<>?,./";
        ReportarComentarioRequest request = new ReportarComentarioRequest(motivoEspecial);

        // Assert
        assertThat(request.getMotivo()).isEqualTo(motivoEspecial);
        assertThat(request.getMotivo()).contains("¡@#$%");
    }

    @Test
    @DisplayName("Casos borde - Motivo con emojis")
    void casosBorde_MotivoConEmojis() {
        // Arrange & Act
        String motivoConEmojis = "Comentario inapropiado con contenido ofensivo 😠🚫";
        ReportarComentarioRequest request = new ReportarComentarioRequest(motivoConEmojis);

        // Assert
        assertThat(request.getMotivo()).isEqualTo(motivoConEmojis);
        assertThat(request.getMotivo()).contains("😠");
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        ReportarComentarioRequest request = new ReportarComentarioRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        ReportarComentarioRequest request = new ReportarComentarioRequest("Test motivo");

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getMotivo()).isEqualTo("Test motivo");
    }

    @Test
    @DisplayName("Lombok - Builder funciona")
    void lombok_BuilderFunciona() {
        // Act
        ReportarComentarioRequest request = ReportarComentarioRequest.builder()
                .motivo("Motivo de prueba")
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getMotivo()).isEqualTo("Motivo de prueba");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        ReportarComentarioRequest request = new ReportarComentarioRequest();

        // Act
        request.setMotivo("Nuevo motivo de prueba");

        // Assert
        assertThat(request.getMotivo()).isEqualTo("Nuevo motivo de prueba");
    }

    @Test
    @DisplayName("Validación - Motivo no está vacío cuando se establece")
    void validacion_MotivoNoEstaVacioCuandoSeEstablece() {
        // Arrange
        ReportarComentarioRequest request = new ReportarComentarioRequest();

        // Act
        request.setMotivo("Motivo válido para reporte");

        // Assert
        assertThat(request.getMotivo()).isNotBlank();
    }

    @Test
    @DisplayName("Validación - Campo motivo es obligatorio")
    void validacion_CampoMotivoEsObligatorio() {
        // Arrange
        ReportarComentarioRequest request = new ReportarComentarioRequest();

        // Act & Assert - El campo es obligatorio según la anotación @NotBlank
        // Esta validación se probaría en tests de integración con el validador
        request.setMotivo(" ");
        assertThat(request.getMotivo()).isBlank();
    }
}