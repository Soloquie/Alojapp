package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para CrearComentarioRequest
 */
@DisplayName("CrearComentarioRequest - Unit Tests")
public class CrearComentarioRequestTest {

    private final Integer RESERVA_ID_VALIDO = 25;
    private final Integer CALIFICACION_VALIDA = 5;
    private final String COMENTARIO_TEXTO_VALIDO = "Excelente alojamiento, muy limpio y cómodo";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        CrearComentarioRequest request = new CrearComentarioRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getReservaId()).isNull();
        assertThat(request.getCalificacion()).isNull();
        assertThat(request.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        CrearComentarioRequest request = new CrearComentarioRequest(
                RESERVA_ID_VALIDO,
                CALIFICACION_VALIDA,
                COMENTARIO_TEXTO_VALIDO
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(request.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(request.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
    }

    @Test
    @DisplayName("Builder - Crea instancia correctamente")
    void builder_CreaInstanciaCorrectamente() {
        // Act
        CrearComentarioRequest request = CrearComentarioRequest.builder()
                .reservaId(RESERVA_ID_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(request.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(request.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
    }

    @Test
    @DisplayName("Builder - Permite construcción parcial")
    void builder_PermiteConstruccionParcial() {
        // Act
        CrearComentarioRequest request = CrearComentarioRequest.builder()
                .reservaId(RESERVA_ID_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(request.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(request.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        CrearComentarioRequest request = new CrearComentarioRequest();

        // Act
        request.setReservaId(RESERVA_ID_VALIDO);
        request.setCalificacion(CALIFICACION_VALIDA);
        request.setComentarioTexto(COMENTARIO_TEXTO_VALIDO);

        // Assert
        assertThat(request.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(request.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(request.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        CrearComentarioRequest request1 = new CrearComentarioRequest(
                RESERVA_ID_VALIDO,
                CALIFICACION_VALIDA,
                COMENTARIO_TEXTO_VALIDO
        );
        CrearComentarioRequest request2 = new CrearComentarioRequest(
                RESERVA_ID_VALIDO,
                CALIFICACION_VALIDA,
                COMENTARIO_TEXTO_VALIDO
        );

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getReservaId()).isEqualTo(request2.getReservaId());
        assertThat(request1.getCalificacion()).isEqualTo(request2.getCalificacion());
        assertThat(request1.getComentarioTexto()).isEqualTo(request2.getComentarioTexto());
    }

    @Test
    @DisplayName("Diferente reservaId - Campos no son iguales")
    void diferenteReservaId_CamposNoSonIguales() {
        // Arrange
        CrearComentarioRequest request1 = new CrearComentarioRequest(1, CALIFICACION_VALIDA, COMENTARIO_TEXTO_VALIDO);
        CrearComentarioRequest request2 = new CrearComentarioRequest(2, CALIFICACION_VALIDA, COMENTARIO_TEXTO_VALIDO);

        // Act & Assert
        assertThat(request1.getReservaId()).isNotEqualTo(request2.getReservaId());
    }

    @Test
    @DisplayName("Diferente calificacion - Campos no son iguales")
    void diferenteCalificacion_CamposNoSonIguales() {
        // Arrange
        CrearComentarioRequest request1 = new CrearComentarioRequest(RESERVA_ID_VALIDO, 4, COMENTARIO_TEXTO_VALIDO);
        CrearComentarioRequest request2 = new CrearComentarioRequest(RESERVA_ID_VALIDO, 5, COMENTARIO_TEXTO_VALIDO);

        // Act & Assert
        assertThat(request1.getCalificacion()).isNotEqualTo(request2.getCalificacion());
    }

    @Test
    @DisplayName("Diferente comentarioTexto - Campos no son iguales")
    void diferenteComentarioTexto_CamposNoSonIguales() {
        // Arrange
        CrearComentarioRequest request1 = new CrearComentarioRequest(RESERVA_ID_VALIDO, CALIFICACION_VALIDA, "Bueno");
        CrearComentarioRequest request2 = new CrearComentarioRequest(RESERVA_ID_VALIDO, CALIFICACION_VALIDA, "Excelente");

        // Act & Assert
        assertThat(request1.getComentarioTexto()).isNotEqualTo(request2.getComentarioTexto());
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        CrearComentarioRequest request = new CrearComentarioRequest(
                RESERVA_ID_VALIDO,
                CALIFICACION_VALIDA,
                COMENTARIO_TEXTO_VALIDO
        );

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("ReservaId - Acepta diferentes valores válidos")
    void reservaId_AceptaDiferentesValoresValidos() {
        // Arrange
        CrearComentarioRequest request = new CrearComentarioRequest();

        // Act & Assert
        request.setReservaId(1);
        assertThat(request.getReservaId()).isEqualTo(1);

        request.setReservaId(1000);
        assertThat(request.getReservaId()).isEqualTo(1000);

        request.setReservaId(null);
        assertThat(request.getReservaId()).isNull();
    }

    @Test
    @DisplayName("Calificacion - Acepta valores dentro del rango 1-5")
    void calificacion_AceptaValoresDentroDelRango() {
        // Arrange
        CrearComentarioRequest request = new CrearComentarioRequest();

        // Act & Assert - Valor mínimo
        request.setCalificacion(1);
        assertThat(request.getCalificacion()).isEqualTo(1);

        // Valor máximo
        request.setCalificacion(5);
        assertThat(request.getCalificacion()).isEqualTo(5);

        // Valores intermedios
        request.setCalificacion(3);
        assertThat(request.getCalificacion()).isEqualTo(3);

        request.setCalificacion(null);
        assertThat(request.getCalificacion()).isNull();
    }

    @Test
    @DisplayName("ComentarioTexto - Acepta diferentes longitudes válidas")
    void comentarioTexto_AceptaDiferentesLongitudesValidas() {
        // Arrange
        CrearComentarioRequest request = new CrearComentarioRequest();

        // Act & Assert - Comentario vacío
        request.setComentarioTexto("");
        assertThat(request.getComentarioTexto()).isEmpty();

        // Comentario corto
        request.setComentarioTexto("Bueno");
        assertThat(request.getComentarioTexto()).isEqualTo("Bueno");

        // Comentario largo (máximo 500 caracteres)
        String comentarioLargo = "M".repeat(500);
        request.setComentarioTexto(comentarioLargo);
        assertThat(request.getComentarioTexto()).hasSize(500);

        // Comentario nulo
        request.setComentarioTexto(null);
        assertThat(request.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("Escenario - Comentario con calificación máxima")
    void escenario_ComentarioConCalificacionMaxima() {
        // Act
        CrearComentarioRequest request = CrearComentarioRequest.builder()
                .reservaId(30)
                .calificacion(5)
                .comentarioTexto("Increíble experiencia, todo perfecto y el personal muy amable")
                .build();

        // Assert
        assertThat(request.getReservaId()).isEqualTo(30);
        assertThat(request.getCalificacion()).isEqualTo(5);
        assertThat(request.getComentarioTexto()).contains("perfecto");
    }

    @Test
    @DisplayName("Escenario - Comentario con calificación mínima")
    void escenario_ComentarioConCalificacionMinima() {
        // Act
        CrearComentarioRequest request = new CrearComentarioRequest();
        request.setReservaId(15);
        request.setCalificacion(1);
        request.setComentarioTexto("Muy mala experiencia, no lo recomiendo");

        // Assert
        assertThat(request.getReservaId()).isEqualTo(15);
        assertThat(request.getCalificacion()).isEqualTo(1);
        assertThat(request.getComentarioTexto()).contains("mala");
    }

    @Test
    @DisplayName("Escenario - Comentario sin texto")
    void escenario_ComentarioSinTexto() {
        // Act
        CrearComentarioRequest request = CrearComentarioRequest.builder()
                .reservaId(42)
                .calificacion(4)
                .build();

        // Assert
        assertThat(request.getReservaId()).isEqualTo(42);
        assertThat(request.getCalificacion()).isEqualTo(4);
        assertThat(request.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("Casos borde - Calificación en límites del rango")
    void casosBorde_CalificacionEnLimitesDelRango() {
        // Arrange
        CrearComentarioRequest request = new CrearComentarioRequest();

        // Act & Assert - Límite inferior
        request.setCalificacion(1);
        assertThat(request.getCalificacion()).isEqualTo(1);

        // Límite superior
        request.setCalificacion(5);
        assertThat(request.getCalificacion()).isEqualTo(5);
    }

    @Test
    @DisplayName("Casos borde - Comentario con longitud máxima")
    void casosBorde_ComentarioConLongitudMaxima() {
        // Arrange & Act
        String comentarioMaximo = "C".repeat(500); // 500 caracteres exactos
        CrearComentarioRequest request = new CrearComentarioRequest(
                RESERVA_ID_VALIDO,
                CALIFICACION_VALIDA,
                comentarioMaximo
        );

        // Assert
        assertThat(request.getComentarioTexto()).hasSize(500);
        assertThat(request.getComentarioTexto()).isEqualTo(comentarioMaximo);
    }

    @Test
    @DisplayName("Casos borde - Comentario con longitud mínima")
    void casosBorde_ComentarioConLongitudMinima() {
        // Arrange & Act
        String comentarioMinimo = "A"; // 1 carácter
        CrearComentarioRequest request = new CrearComentarioRequest(
                RESERVA_ID_VALIDO,
                CALIFICACION_VALIDA,
                comentarioMinimo
        );

        // Assert
        assertThat(request.getComentarioTexto()).hasSize(1);
        assertThat(request.getComentarioTexto()).isEqualTo(comentarioMinimo);
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        CrearComentarioRequest request = new CrearComentarioRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        CrearComentarioRequest request = new CrearComentarioRequest(10, 3, "Regular");

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getReservaId()).isEqualTo(10);
        assertThat(request.getCalificacion()).isEqualTo(3);
        assertThat(request.getComentarioTexto()).isEqualTo("Regular");
    }

    @Test
    @DisplayName("Lombok - Builder funciona")
    void lombok_BuilderFunciona() {
        // Act
        CrearComentarioRequest request = CrearComentarioRequest.builder()
                .reservaId(99)
                .calificacion(2)
                .comentarioTexto("Podría mejorar")
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getReservaId()).isEqualTo(99);
        assertThat(request.getCalificacion()).isEqualTo(2);
        assertThat(request.getComentarioTexto()).isEqualTo("Podría mejorar");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        CrearComentarioRequest request = new CrearComentarioRequest();

        // Act
        request.setReservaId(55);
        request.setCalificacion(4);
        request.setComentarioTexto("Muy buena experiencia");

        // Assert
        assertThat(request.getReservaId()).isEqualTo(55);
        assertThat(request.getCalificacion()).isEqualTo(4);
        assertThat(request.getComentarioTexto()).isEqualTo("Muy buena experiencia");
    }
}