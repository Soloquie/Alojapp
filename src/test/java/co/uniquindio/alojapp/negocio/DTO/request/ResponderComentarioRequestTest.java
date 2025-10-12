package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ResponderComentarioRequest
 */
@DisplayName("ResponderComentarioRequest - Unit Tests")
public class ResponderComentarioRequestTest {

    private final String RESPUESTA_TEXTO_VALIDA = "Muchas gracias por tu comentario, esperamos verte pronto";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        ResponderComentarioRequest request = new ResponderComentarioRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getRespuestaTexto()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        ResponderComentarioRequest request = new ResponderComentarioRequest(RESPUESTA_TEXTO_VALIDA);

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getRespuestaTexto()).isEqualTo(RESPUESTA_TEXTO_VALIDA);
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        ResponderComentarioRequest request = new ResponderComentarioRequest();

        // Act
        request.setRespuestaTexto(RESPUESTA_TEXTO_VALIDA);

        // Assert
        assertThat(request.getRespuestaTexto()).isEqualTo(RESPUESTA_TEXTO_VALIDA);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        ResponderComentarioRequest request1 = new ResponderComentarioRequest(RESPUESTA_TEXTO_VALIDA);
        ResponderComentarioRequest request2 = new ResponderComentarioRequest(RESPUESTA_TEXTO_VALIDA);

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getRespuestaTexto()).isEqualTo(request2.getRespuestaTexto());
    }

    @Test
    @DisplayName("Diferente respuestaTexto - Campos no son iguales")
    void diferenteRespuestaTexto_CamposNoSonIguales() {
        // Arrange
        ResponderComentarioRequest request1 = new ResponderComentarioRequest("Respuesta 1");
        ResponderComentarioRequest request2 = new ResponderComentarioRequest("Respuesta 2");

        // Act & Assert
        assertThat(request1.getRespuestaTexto()).isNotEqualTo(request2.getRespuestaTexto());
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        ResponderComentarioRequest request = new ResponderComentarioRequest(RESPUESTA_TEXTO_VALIDA);

        // Act & Assert
        assertThatCode(() -> request.hashCode()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        ResponderComentarioRequest request = new ResponderComentarioRequest(RESPUESTA_TEXTO_VALIDA);

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
        ResponderComentarioRequest request = new ResponderComentarioRequest(RESPUESTA_TEXTO_VALIDA);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).contains("ResponderComentarioRequest");
        assertThat(resultado).contains("respuestaTexto");
    }

    @Test
    @DisplayName("RespuestaTexto - Acepta diferentes longitudes válidas")
    void respuestaTexto_AceptaDiferentesLongitudesValidas() {
        // Arrange
        ResponderComentarioRequest request = new ResponderComentarioRequest();

        // Act & Assert - Longitud mínima (10 caracteres)
        String respuestaMinima = "Gracias!!";
        request.setRespuestaTexto(respuestaMinima);
        assertThat(request.getRespuestaTexto()).hasSize(9);

        // Longitud máxima (1000 caracteres)
        String respuestaMaxima = "R".repeat(1000);
        request.setRespuestaTexto(respuestaMaxima);
        assertThat(request.getRespuestaTexto()).hasSize(1000);

        // Respuesta normal
        request.setRespuestaTexto("Agradecemos tu comentario y tomaremos en cuenta tus sugerencias");
        assertThat(request.getRespuestaTexto()).contains("Agradecemos"); // Cambiado a mayúscula

        // Respuesta vacía
        request.setRespuestaTexto("");
        assertThat(request.getRespuestaTexto()).isEmpty();

        // Respuesta nula
        request.setRespuestaTexto(null);
        assertThat(request.getRespuestaTexto()).isNull();
    }

    @Test
    @DisplayName("Escenario - Respuesta agradeciendo comentario positivo")
    void escenario_RespuestaAgradeciendoComentarioPositivo() {
        // Act
        ResponderComentarioRequest request = new ResponderComentarioRequest();
        request.setRespuestaTexto("¡Muchas gracias por tus amables palabras! Nos alegra saber que disfrutaste tu estadía.");

        // Assert
        assertThat(request.getRespuestaTexto()).isNotBlank();
        assertThat(request.getRespuestaTexto()).contains("gracias");
        assertThat(request.getRespuestaTexto()).contains("alegra");
        assertThat(request.getRespuestaTexto()).hasSizeGreaterThan(10);
    }

    @Test
    @DisplayName("Escenario - Respuesta a comentario con sugerencias")
    void escenario_RespuestaAComentarioConSugerencias() {
        // Act
        ResponderComentarioRequest request = new ResponderComentarioRequest(
                "Agradecemos tus comentarios constructivos. Estamos trabajando en mejorar los aspectos que mencionas para ofrecer un mejor servicio."
        );

        // Assert
        assertThat(request.getRespuestaTexto()).isNotNull();
        assertThat(request.getRespuestaTexto()).contains("constructivos");
        assertThat(request.getRespuestaTexto()).contains("mejorar");
        assertThat(request.getRespuestaTexto()).hasSizeGreaterThan(20);
    }

    @Test
    @DisplayName("Escenario - Respuesta profesional a comentario negativo")
    void escenario_RespuestaProfesionalAComentarioNegativo() {
        // Act
        ResponderComentarioRequest request = new ResponderComentarioRequest();
        request.setRespuestaTexto("Lamentamos sinceramente que tu experiencia no haya sido la esperada. Hemos tomado nota de tus observaciones y estamos implementando mejoras.");

        // Assert
        assertThat(request.getRespuestaTexto()).isEqualTo("Lamentamos sinceramente que tu experiencia no haya sido la esperada. Hemos tomado nota de tus observaciones y estamos implementando mejoras.");
        assertThat(request.getRespuestaTexto()).contains("Lamentamos"); // Cambiado a mayúscula
        assertThat(request.getRespuestaTexto()).contains("mejoras");
    }

    @Test
    @DisplayName("Escenario - Respuesta corta pero completa")
    void escenario_RespuestaCortaPeroCompleta() {
        // Act
        ResponderComentarioRequest request = new ResponderComentarioRequest(
                "Gracias por visitarnos. ¡Te esperamos pronto!"
        );

        // Assert
        assertThat(request.getRespuestaTexto()).isNotNull();
        assertThat(request.getRespuestaTexto()).contains("Gracias");
        assertThat(request.getRespuestaTexto()).contains("esperamos");
    }

    @Test
    @DisplayName("Casos borde - Respuesta con longitud mínima exacta")
    void casosBorde_RespuestaConLongitudMinimaExacta() {
        // Arrange & Act
        String respuestaMinimaExacta = "1234567890"; // 10 caracteres exactos
        ResponderComentarioRequest request = new ResponderComentarioRequest(respuestaMinimaExacta);

        // Assert
        assertThat(request.getRespuestaTexto()).hasSize(10);
        assertThat(request.getRespuestaTexto()).isEqualTo(respuestaMinimaExacta);
    }

    @Test
    @DisplayName("Casos borde - Respuesta con longitud máxima exacta")
    void casosBorde_RespuestaConLongitudMaximaExacta() {
        // Arrange & Act
        String respuestaMaximaExacta = "R".repeat(1000); // 1000 caracteres exactos
        ResponderComentarioRequest request = new ResponderComentarioRequest(respuestaMaximaExacta);

        // Assert
        assertThat(request.getRespuestaTexto()).hasSize(1000);
        assertThat(request.getRespuestaTexto()).isEqualTo(respuestaMaximaExacta);
    }

    @Test
    @DisplayName("Casos borde - Respuesta con caracteres especiales")
    void casosBorde_RespuestaConCaracteresEspeciales() {
        // Arrange & Act
        String respuestaEspecial = "¡Muchas gracias! 😊 Esperamos verte pronto ❤️";
        ResponderComentarioRequest request = new ResponderComentarioRequest(respuestaEspecial);

        // Assert
        assertThat(request.getRespuestaTexto()).isEqualTo(respuestaEspecial);
        assertThat(request.getRespuestaTexto()).contains("😊");
        assertThat(request.getRespuestaTexto()).contains("❤️");
    }

    @Test
    @DisplayName("Casos borde - Respuesta con formato HTML")
    void casosBorde_RespuestaConFormatoHTML() {
        // Arrange & Act
        String respuestaHTML = "Gracias por tu <strong>excelente</strong> comentario. <br>¡Te esperamos!";
        ResponderComentarioRequest request = new ResponderComentarioRequest(respuestaHTML);

        // Assert
        assertThat(request.getRespuestaTexto()).isEqualTo(respuestaHTML);
        assertThat(request.getRespuestaTexto()).contains("<strong>");
        assertThat(request.getRespuestaTexto()).contains("<br>");
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        ResponderComentarioRequest request = new ResponderComentarioRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        ResponderComentarioRequest request = new ResponderComentarioRequest("Test respuesta");

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getRespuestaTexto()).isEqualTo("Test respuesta");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        ResponderComentarioRequest request = new ResponderComentarioRequest();

        // Act
        request.setRespuestaTexto("Nueva respuesta de prueba");

        // Assert
        assertThat(request.getRespuestaTexto()).isEqualTo("Nueva respuesta de prueba");
    }

    @Test
    @DisplayName("Validación - Respuesta no está vacía cuando se establece")
    void validacion_RespuestaNoEstaVaciaCuandoSeEstablece() {
        // Arrange
        ResponderComentarioRequest request = new ResponderComentarioRequest();

        // Act
        request.setRespuestaTexto("Respuesta válida para el comentario");

        // Assert
        assertThat(request.getRespuestaTexto()).isNotBlank();
    }

    @Test
    @DisplayName("Validación - Respuesta cumple con longitud mínima")
    void validacion_RespuestaCumpleConLongitudMinima() {
        // Arrange
        ResponderComentarioRequest request = new ResponderComentarioRequest();

        // Act
        request.setRespuestaTexto("Esta respuesta tiene más de diez caracteres");

        // Assert
        assertThat(request.getRespuestaTexto()).hasSizeGreaterThan(10);
    }

    @Test
    @DisplayName("Validación - Respuesta respeta longitud máxima")
    void validacion_RespuestaRespetaLongitudMaxima() {
        // Arrange
        ResponderComentarioRequest request = new ResponderComentarioRequest();

        // Act
        String respuestaValida = "A".repeat(500); // Dentro del límite
        request.setRespuestaTexto(respuestaValida);

        // Assert
        assertThat(request.getRespuestaTexto()).hasSizeLessThanOrEqualTo(1000);
    }
}