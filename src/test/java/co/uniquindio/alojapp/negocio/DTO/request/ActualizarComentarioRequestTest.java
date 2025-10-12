package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ActualizarComentarioRequest
 *
 * OBJETIVO: Probar el DTO de actualización de comentario
 * - Validar constructor, builder, getters/setters
 * - Verificar equals/hashCode/toString
 * - Probar diferentes escenarios de actualización
 */
@DisplayName("ActualizarComentarioRequest - Unit Tests")
public class ActualizarComentarioRequestTest {

    // DATOS DE PRUEBA
    private final Integer CALIFICACION_VALIDA = 4;
    private final String COMENTARIO_TEXTO_VALIDO = "Muy buen lugar, volvería.";

    // ==================== BUILDER TESTS ====================

    @Test
    @DisplayName("BUILDER - Crea instancia con todos los campos correctamente")
    void builder_ConTodosLosCampos_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(request.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
    }

    @Test
    @DisplayName("BUILDER - Campos opcionales null se manejan correctamente")
    void builder_CamposOpcionalesNull_SeManejanCorrectamente() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(request.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("BUILDER - Solo comentarioTexto se maneja correctamente")
    void builder_SoloComentarioTexto_SeManejaCorrectamente() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCalificacion()).isNull();
        assertThat(request.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
    }

    @Test
    @DisplayName("BUILDER - Builder vacío crea instancia con campos null")
    void builder_BuilderVacio_CreaInstanciaConCamposNull() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder().build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCalificacion()).isNull();
        assertThat(request.getComentarioTexto()).isNull();
    }

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        ActualizarComentarioRequest request = new ActualizarComentarioRequest();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCalificacion()).isNull();
        assertThat(request.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = new ActualizarComentarioRequest(
                CALIFICACION_VALIDA, COMENTARIO_TEXTO_VALIDO
        );

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(request.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        ActualizarComentarioRequest request = new ActualizarComentarioRequest();

        // ACT
        request.setCalificacion(CALIFICACION_VALIDA);
        request.setComentarioTexto(COMENTARIO_TEXTO_VALIDO);

        // ASSERT
        assertThat(request.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(request.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en TODOS los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        ActualizarComentarioRequest request1 = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        ActualizarComentarioRequest request2 = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Ambos campos null son iguales")
    void equals_AmbosCamposNull_SonIguales() {
        // ARRANGE
        ActualizarComentarioRequest request1 = new ActualizarComentarioRequest(null, null);
        ActualizarComentarioRequest request2 = new ActualizarComentarioRequest(null, null);

        // ACT & ASSERT
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente calificación retorna false")
    void equals_DiferenteCalificacion_RetornaFalse() {
        // ARRANGE
        ActualizarComentarioRequest request1 = ActualizarComentarioRequest.builder()
                .calificacion(4)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        ActualizarComentarioRequest request2 = ActualizarComentarioRequest.builder()
                .calificacion(5)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @DisplayName("EQUALS - Diferente comentarioTexto retorna false")
    void equals_DiferenteComentarioTexto_RetornaFalse() {
        // ARRANGE
        ActualizarComentarioRequest request1 = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto("Buen lugar")
                .build();

        ActualizarComentarioRequest request2 = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto("Excelente lugar")
                .build();

        // ACT & ASSERT
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .build();

        // ACT & ASSERT
        assertThat(request).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .build();

        // ACT & ASSERT
        assertThat(request).isNotEqualTo("No soy un request");
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del request")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // ACT
        String resultado = request.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(CALIFICACION_VALIDA));
        assertThat(resultado).contains(COMENTARIO_TEXTO_VALIDO);
    }

    // ==================== CALIFICACIÓN TESTS ====================

    @Test
    @DisplayName("CALIFICACIÓN - Todas las calificaciones válidas son aceptadas")
    void calificacion_TodasLasCalificacionesValidas_SonAceptadas() {
        // ARRANGE
        ActualizarComentarioRequest request = new ActualizarComentarioRequest();

        // ACT & ASSERT
        request.setCalificacion(1);
        assertThat(request.getCalificacion()).isEqualTo(1);

        request.setCalificacion(2);
        assertThat(request.getCalificacion()).isEqualTo(2);

        request.setCalificacion(3);
        assertThat(request.getCalificacion()).isEqualTo(3);

        request.setCalificacion(4);
        assertThat(request.getCalificacion()).isEqualTo(4);

        request.setCalificacion(5);
        assertThat(request.getCalificacion()).isEqualTo(5);

        request.setCalificacion(null);
        assertThat(request.getCalificacion()).isNull();
    }

    // ==================== COMENTARIO TEXTO TESTS ====================

    @Test
    @DisplayName("COMENTARIO TEXTO - Diferentes longitudes de comentario son aceptadas")
    void comentarioTexto_DiferentesLongitudesDeComentario_SonAceptadas() {
        // ARRANGE
        ActualizarComentarioRequest request = new ActualizarComentarioRequest();

        // ACT & ASSERT
        request.setComentarioTexto("OK");
        assertThat(request.getComentarioTexto()).isEqualTo("OK");

        String comentarioLargo = "Excelente servicio, el personal fue muy amable y atento. " +
                "Las instalaciones están impecables y la ubicación es perfecta. " +
                "Sin duda alguna recomendaría este lugar a todos mis amigos y familiares. " +
                "Volveremos pronto para disfrutar nuevamente de esta experiencia maravillosa.";
        request.setComentarioTexto(comentarioLargo);
        assertThat(request.getComentarioTexto()).hasSizeGreaterThan(100);

        request.setComentarioTexto("");
        assertThat(request.getComentarioTexto()).isEmpty();

        request.setComentarioTexto(null);
        assertThat(request.getComentarioTexto()).isNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización solo de calificación")
    void scenarioUsoReal_ActualizacionSoloDeCalificacion() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(5)
                .build();

        // ASSERT - Solo se actualiza la calificación
        assertThat(request.getCalificacion()).isEqualTo(5);
        assertThat(request.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización solo de texto")
    void scenarioUsoReal_ActualizacionSoloDeTexto() {
        // ARRANGE & ACT
        String nuevoComentario = "El servicio mejoró considerablemente desde mi última visita. Muy satisfecho.";
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .comentarioTexto(nuevoComentario)
                .build();

        // ASSERT - Solo se actualiza el texto
        assertThat(request.getCalificacion()).isNull();
        assertThat(request.getComentarioTexto()).isEqualTo(nuevoComentario);
        assertThat(request.getComentarioTexto()).contains("mejoró");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización completa de comentario")
    void scenarioUsoReal_ActualizacionCompletaDeComentario() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(5)
                .comentarioTexto("¡Experiencia excepcional! Todo superó mis expectativas. " +
                        "Desde el check-in hasta la salida, el servicio fue impecable. " +
                        "Las instalaciones son de primera calidad y la atención al detalle es notable. " +
                        "Definitivamente mi lugar favorito para hospedarme.")
                .build();

        // ASSERT - Actualización completa
        assertThat(request.getCalificacion()).isEqualTo(5);
        assertThat(request.getComentarioTexto()).hasSizeGreaterThan(50);
        assertThat(request.getComentarioTexto()).contains("excepcional", "expectativas");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Mejora de calificación sin cambiar comentario")
    void scenarioUsoReal_MejoraDeCalificacionSinCambiarComentario() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(5) // Mejoró de 4 a 5
                .build();

        // ASSERT - Solo mejora la calificación
        assertThat(request.getCalificacion()).isEqualTo(5);
        assertThat(request.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Ampliación de comentario sin cambiar calificación")
    void scenarioUsoReal_AmpliacionDeComentarioSinCambiarCalificacion() {
        // ARRANGE & ACT
        String comentarioAmpliado = "Después de reflexionar, quiero agregar que el desayuno incluido fue delicioso " +
                "y variado. El área de la piscina está muy bien mantenida y los horarios de acceso son convenientes. " +
                "El personal de limpieza hace un trabajo excelente manteniendo todo impecable.";

        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .comentarioTexto(comentarioAmpliado)
                .build();

        // ASSERT - Solo se amplía el comentario
        assertThat(request.getCalificacion()).isNull();
        assertThat(request.getComentarioTexto()).hasSizeGreaterThan(100);
        assertThat(request.getComentarioTexto()).contains("desayuno", "piscina", "limpieza");
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Request completamente vacío")
    void casoBorde_RequestCompletamenteVacio() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = new ActualizarComentarioRequest();

        // ASSERT
        assertThat(request.getCalificacion()).isNull();
        assertThat(request.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Comentario con longitud máxima")
    void casoBorde_ComentarioConLongitudMaxima() {
        // ARRANGE & ACT
        String comentarioMaximo = "A".repeat(500); // 500 caracteres exactos
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .comentarioTexto(comentarioMaximo)
                .build();

        // ASSERT
        assertThat(request.getComentarioTexto()).hasSize(500);
        assertThat(request.getComentarioTexto()).isEqualTo(comentarioMaximo);
    }

    @Test
    @DisplayName("CASO BORDE - Comentario con un solo carácter")
    void casoBorde_ComentarioConUnSoloCaracter() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .comentarioTexto("!")
                .build();

        // ASSERT
        assertThat(request.getComentarioTexto()).hasSize(1);
        assertThat(request.getComentarioTexto()).isEqualTo("!");
    }

    @Test
    @DisplayName("CASO BORDE - Calificación mínima")
    void casoBorde_CalificacionMinima() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(1)
                .build();

        // ASSERT
        assertThat(request.getCalificacion()).isEqualTo(1);
    }

    @Test
    @DisplayName("CASO BORDE - Calificación máxima")
    void casoBorde_CalificacionMaxima() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(5)
                .build();

        // ASSERT
        assertThat(request.getCalificacion()).isEqualTo(5);
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE - Crear dos objetos IDÉNTICOS
        ActualizarComentarioRequest request1 = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        ActualizarComentarioRequest request2 = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // ASSERT - Verificar que @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.toString()).isNotNull();
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        ActualizarComentarioRequest requestVacio = new ActualizarComentarioRequest();
        assertThat(requestVacio).isNotNull();

        // Verificar que los getters y setters funcionan
        requestVacio.setCalificacion(3);
        assertThat(requestVacio.getCalificacion()).isEqualTo(3);

        // Verificar que el builder funciona
        assertThat(request1).isInstanceOf(ActualizarComentarioRequest.class);
    }

    // ==================== VALIDACIÓN DE ESTRUCTURA TESTS ====================

    @Test
    @DisplayName("ESTRUCTURA - Campos tienen los tipos de datos correctos")
    void estructura_CamposTienenLosTiposDeDatosCorrectos() {
        // ARRANGE & ACT
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // ASSERT
        assertThat(request.getCalificacion()).isInstanceOf(Integer.class);
        assertThat(request.getComentarioTexto()).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("ESTRUCTURA - Instancia puede ser serializada correctamente")
    void estructura_InstanciaPuedeSerSerializadaCorrectamente() {
        // ARRANGE
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // ACT - Simular serialización/deserialización
        ActualizarComentarioRequest copia = new ActualizarComentarioRequest(
                request.getCalificacion(),
                request.getComentarioTexto()
        );

        // ASSERT
        assertThat(copia).isEqualTo(request);
        assertThat(copia.getCalificacion()).isEqualTo(request.getCalificacion());
        assertThat(copia.getComentarioTexto()).isEqualTo(request.getComentarioTexto());
    }

    // ==================== BUSINESS LOGIC TESTS ====================

    @Test
    @DisplayName("BUSINESS - Request permite actualización parcial")
    void business_RequestPermiteActualizacionParcial() {
        // ARRANGE & ACT - Solo actualizar comentario, mantener calificación existente
        ActualizarComentarioRequest requestParcial = ActualizarComentarioRequest.builder()
                .comentarioTexto("Agrego más detalles a mi comentario anterior.")
                .build();

        // ASSERT - Campos no incluidos deben ser null (indicando que no se actualizan)
        assertThat(requestParcial.getCalificacion()).isNull();
        assertThat(requestParcial.getComentarioTexto()).isNotNull();
    }

    @Test
    @DisplayName("BUSINESS - Diferentes métodos de construcción producen objetos iguales")
    void business_DiferentesMetodosDeConstruccion_ProducenObjetosIguales() {
        // ARRANGE
        ActualizarComentarioRequest viaBuilder = ActualizarComentarioRequest.builder()
                .calificacion(4)
                .comentarioTexto("Muy buen servicio")
                .build();

        ActualizarComentarioRequest viaConstructor = new ActualizarComentarioRequest(
                4, "Muy buen servicio"
        );

        ActualizarComentarioRequest viaSetters = new ActualizarComentarioRequest();
        viaSetters.setCalificacion(4);
        viaSetters.setComentarioTexto("Muy buen servicio");

        // ACT & ASSERT - Todos deben ser iguales
        assertThat(viaBuilder).isEqualTo(viaConstructor);
        assertThat(viaBuilder).isEqualTo(viaSetters);
        assertThat(viaConstructor).isEqualTo(viaSetters);
    }
}