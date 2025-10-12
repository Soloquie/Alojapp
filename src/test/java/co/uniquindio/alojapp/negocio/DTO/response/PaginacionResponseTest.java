package co.uniquindio.alojapp.negocio.DTO.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para PaginacionResponse
 */
@DisplayName("PaginacionResponse - Unit Tests")
public class PaginacionResponseTest {

    private final List<String> CONTENIDO_VALIDO = Arrays.asList("item1", "item2", "item3");
    private final Integer PAGINA_ACTUAL_VALIDA = 0;
    private final Integer TAMANO_PAGINA_VALIDO = 10;
    private final Long TOTAL_ELEMENTOS_VALIDO = 150L;
    private final Integer TOTAL_PAGINAS_VALIDO = 15;
    private final Boolean ES_PRIMERA_VALIDO = true;
    private final Boolean ES_ULTIMA_VALIDO = false;
    private final Boolean TIENE_SIGUIENTE_VALIDO = true;
    private final Boolean TIENE_ANTERIOR_VALIDO = false;

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getContenido()).isNull();
        assertThat(response.getPaginaActual()).isNull();
        assertThat(response.getTamanoPagina()).isNull();
        assertThat(response.getTotalElementos()).isNull();
        assertThat(response.getTotalPaginas()).isNull();
        assertThat(response.getEsPrimera()).isNull();
        assertThat(response.getEsUltima()).isNull();
        assertThat(response.getTieneSiguiente()).isNull();
        assertThat(response.getTieneAnterior()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        PaginacionResponse<String> response = new PaginacionResponse<>(
                CONTENIDO_VALIDO, PAGINA_ACTUAL_VALIDA, TAMANO_PAGINA_VALIDO,
                TOTAL_ELEMENTOS_VALIDO, TOTAL_PAGINAS_VALIDO, ES_PRIMERA_VALIDO,
                ES_ULTIMA_VALIDO, TIENE_SIGUIENTE_VALIDO, TIENE_ANTERIOR_VALIDO
        );

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getContenido()).isEqualTo(CONTENIDO_VALIDO);
        assertThat(response.getPaginaActual()).isEqualTo(PAGINA_ACTUAL_VALIDA);
        assertThat(response.getTamanoPagina()).isEqualTo(TAMANO_PAGINA_VALIDO);
        assertThat(response.getTotalElementos()).isEqualTo(TOTAL_ELEMENTOS_VALIDO);
        assertThat(response.getTotalPaginas()).isEqualTo(TOTAL_PAGINAS_VALIDO);
        assertThat(response.getEsPrimera()).isEqualTo(ES_PRIMERA_VALIDO);
        assertThat(response.getEsUltima()).isEqualTo(ES_ULTIMA_VALIDO);
        assertThat(response.getTieneSiguiente()).isEqualTo(TIENE_SIGUIENTE_VALIDO);
        assertThat(response.getTieneAnterior()).isEqualTo(TIENE_ANTERIOR_VALIDO);
    }

    @Test
    @DisplayName("Builder - Crea instancia correctamente")
    void builder_CreaInstanciaCorrectamente() {
        // Act
        PaginacionResponse<String> response = PaginacionResponse.<String>builder()
                .contenido(CONTENIDO_VALIDO)
                .paginaActual(PAGINA_ACTUAL_VALIDA)
                .tamanoPagina(TAMANO_PAGINA_VALIDO)
                .totalElementos(TOTAL_ELEMENTOS_VALIDO)
                .totalPaginas(TOTAL_PAGINAS_VALIDO)
                .esPrimera(ES_PRIMERA_VALIDO)
                .esUltima(ES_ULTIMA_VALIDO)
                .tieneSiguiente(TIENE_SIGUIENTE_VALIDO)
                .tieneAnterior(TIENE_ANTERIOR_VALIDO)
                .build();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getContenido()).isEqualTo(CONTENIDO_VALIDO);
        assertThat(response.getPaginaActual()).isEqualTo(PAGINA_ACTUAL_VALIDA);
        assertThat(response.getTamanoPagina()).isEqualTo(TAMANO_PAGINA_VALIDO);
        assertThat(response.getTotalElementos()).isEqualTo(TOTAL_ELEMENTOS_VALIDO);
        assertThat(response.getTotalPaginas()).isEqualTo(TOTAL_PAGINAS_VALIDO);
        assertThat(response.getEsPrimera()).isEqualTo(ES_PRIMERA_VALIDO);
        assertThat(response.getEsUltima()).isEqualTo(ES_ULTIMA_VALIDO);
        assertThat(response.getTieneSiguiente()).isEqualTo(TIENE_SIGUIENTE_VALIDO);
        assertThat(response.getTieneAnterior()).isEqualTo(TIENE_ANTERIOR_VALIDO);
    }

    @Test
    @DisplayName("Builder - Permite construcción parcial")
    void builder_PermiteConstruccionParcial() {
        // Act
        PaginacionResponse<String> response = PaginacionResponse.<String>builder()
                .contenido(CONTENIDO_VALIDO)
                .paginaActual(PAGINA_ACTUAL_VALIDA)
                .tamanoPagina(TAMANO_PAGINA_VALIDO)
                .build();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getContenido()).isEqualTo(CONTENIDO_VALIDO);
        assertThat(response.getPaginaActual()).isEqualTo(PAGINA_ACTUAL_VALIDA);
        assertThat(response.getTamanoPagina()).isEqualTo(TAMANO_PAGINA_VALIDO);
        assertThat(response.getTotalElementos()).isNull();
        assertThat(response.getTotalPaginas()).isNull();
        assertThat(response.getEsPrimera()).isNull();
        assertThat(response.getEsUltima()).isNull();
        assertThat(response.getTieneSiguiente()).isNull();
        assertThat(response.getTieneAnterior()).isNull();
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act
        response.setContenido(CONTENIDO_VALIDO);
        response.setPaginaActual(PAGINA_ACTUAL_VALIDA);
        response.setTamanoPagina(TAMANO_PAGINA_VALIDO);
        response.setTotalElementos(TOTAL_ELEMENTOS_VALIDO);
        response.setTotalPaginas(TOTAL_PAGINAS_VALIDO);
        response.setEsPrimera(ES_PRIMERA_VALIDO);
        response.setEsUltima(ES_ULTIMA_VALIDO);
        response.setTieneSiguiente(TIENE_SIGUIENTE_VALIDO);
        response.setTieneAnterior(TIENE_ANTERIOR_VALIDO);

        // Assert
        assertThat(response.getContenido()).isEqualTo(CONTENIDO_VALIDO);
        assertThat(response.getPaginaActual()).isEqualTo(PAGINA_ACTUAL_VALIDA);
        assertThat(response.getTamanoPagina()).isEqualTo(TAMANO_PAGINA_VALIDO);
        assertThat(response.getTotalElementos()).isEqualTo(TOTAL_ELEMENTOS_VALIDO);
        assertThat(response.getTotalPaginas()).isEqualTo(TOTAL_PAGINAS_VALIDO);
        assertThat(response.getEsPrimera()).isEqualTo(ES_PRIMERA_VALIDO);
        assertThat(response.getEsUltima()).isEqualTo(ES_ULTIMA_VALIDO);
        assertThat(response.getTieneSiguiente()).isEqualTo(TIENE_SIGUIENTE_VALIDO);
        assertThat(response.getTieneAnterior()).isEqualTo(TIENE_ANTERIOR_VALIDO);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        PaginacionResponse<String> response1 = new PaginacionResponse<>(
                CONTENIDO_VALIDO, PAGINA_ACTUAL_VALIDA, TAMANO_PAGINA_VALIDO,
                TOTAL_ELEMENTOS_VALIDO, TOTAL_PAGINAS_VALIDO, ES_PRIMERA_VALIDO,
                ES_ULTIMA_VALIDO, TIENE_SIGUIENTE_VALIDO, TIENE_ANTERIOR_VALIDO
        );
        PaginacionResponse<String> response2 = new PaginacionResponse<>(
                CONTENIDO_VALIDO, PAGINA_ACTUAL_VALIDA, TAMANO_PAGINA_VALIDO,
                TOTAL_ELEMENTOS_VALIDO, TOTAL_PAGINAS_VALIDO, ES_PRIMERA_VALIDO,
                ES_ULTIMA_VALIDO, TIENE_SIGUIENTE_VALIDO, TIENE_ANTERIOR_VALIDO
        );

        // Act & Assert - Verificar manualmente cada campo
        assertThat(response1.getContenido()).isEqualTo(response2.getContenido());
        assertThat(response1.getPaginaActual()).isEqualTo(response2.getPaginaActual());
        assertThat(response1.getTamanoPagina()).isEqualTo(response2.getTamanoPagina());
        assertThat(response1.getTotalElementos()).isEqualTo(response2.getTotalElementos());
        assertThat(response1.getTotalPaginas()).isEqualTo(response2.getTotalPaginas());
        assertThat(response1.getEsPrimera()).isEqualTo(response2.getEsPrimera());
        assertThat(response1.getEsUltima()).isEqualTo(response2.getEsUltima());
        assertThat(response1.getTieneSiguiente()).isEqualTo(response2.getTieneSiguiente());
        assertThat(response1.getTieneAnterior()).isEqualTo(response2.getTieneAnterior());
    }

    @Test
    @DisplayName("Diferente contenido - Campos no son iguales")
    void diferenteContenido_CamposNoSonIguales() {
        // Arrange
        PaginacionResponse<String> response1 = new PaginacionResponse<>();
        response1.setContenido(Arrays.asList("item1", "item2"));

        PaginacionResponse<String> response2 = new PaginacionResponse<>();
        response2.setContenido(Arrays.asList("item3", "item4"));

        // Act & Assert
        assertThat(response1.getContenido()).isNotEqualTo(response2.getContenido());
    }

    @Test
    @DisplayName("Diferente paginaActual - Campos no son iguales")
    void diferentePaginaActual_CamposNoSonIguales() {
        // Arrange
        PaginacionResponse<String> response1 = new PaginacionResponse<>();
        response1.setPaginaActual(0);

        PaginacionResponse<String> response2 = new PaginacionResponse<>();
        response2.setPaginaActual(1);

        // Act & Assert
        assertThat(response1.getPaginaActual()).isNotEqualTo(response2.getPaginaActual());
    }

    @Test
    @DisplayName("Diferente esPrimera - Campos no son iguales")
    void diferenteEsPrimera_CamposNoSonIguales() {
        // Arrange
        PaginacionResponse<String> response1 = new PaginacionResponse<>();
        response1.setEsPrimera(true);

        PaginacionResponse<String> response2 = new PaginacionResponse<>();
        response2.setEsPrimera(false);

        // Act & Assert
        assertThat(response1.getEsPrimera()).isNotEqualTo(response2.getEsPrimera());
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>(
                CONTENIDO_VALIDO, PAGINA_ACTUAL_VALIDA, TAMANO_PAGINA_VALIDO,
                TOTAL_ELEMENTOS_VALIDO, TOTAL_PAGINAS_VALIDO, ES_PRIMERA_VALIDO,
                ES_ULTIMA_VALIDO, TIENE_SIGUIENTE_VALIDO, TIENE_ANTERIOR_VALIDO
        );

        // Act & Assert
        assertThatCode(() -> response.hashCode()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>(
                CONTENIDO_VALIDO, PAGINA_ACTUAL_VALIDA, TAMANO_PAGINA_VALIDO,
                TOTAL_ELEMENTOS_VALIDO, TOTAL_PAGINAS_VALIDO, ES_PRIMERA_VALIDO,
                ES_ULTIMA_VALIDO, TIENE_SIGUIENTE_VALIDO, TIENE_ANTERIOR_VALIDO
        );

        // Act
        String resultado = response.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("Contenido - Acepta diferentes tipos de listas")
    void contenido_AceptaDiferentesTiposDeListas() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act & Assert - Lista con elementos
        response.setContenido(Arrays.asList("a", "b", "c"));
        assertThat(response.getContenido()).hasSize(3);

        // Lista vacía
        response.setContenido(Collections.emptyList());
        assertThat(response.getContenido()).isEmpty();

        // Lista nula
        response.setContenido(null);
        assertThat(response.getContenido()).isNull();
    }

    @Test
    @DisplayName("PaginaActual - Acepta diferentes valores")
    void paginaActual_AceptaDiferentesValores() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act & Assert - Página 0
        response.setPaginaActual(0);
        assertThat(response.getPaginaActual()).isEqualTo(0);

        // Página positiva
        response.setPaginaActual(5);
        assertThat(response.getPaginaActual()).isEqualTo(5);

        // Nulo
        response.setPaginaActual(null);
        assertThat(response.getPaginaActual()).isNull();
    }

    @Test
    @DisplayName("TamanoPagina - Acepta diferentes tamaños")
    void tamanoPagina_AceptaDiferentesTamaños() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act & Assert - Tamaño pequeño
        response.setTamanoPagina(5);
        assertThat(response.getTamanoPagina()).isEqualTo(5);

        // Tamaño estándar
        response.setTamanoPagina(20);
        assertThat(response.getTamanoPagina()).isEqualTo(20);

        // Tamaño grande
        response.setTamanoPagina(100);
        assertThat(response.getTamanoPagina()).isEqualTo(100);

        // Nulo
        response.setTamanoPagina(null);
        assertThat(response.getTamanoPagina()).isNull();
    }

    @Test
    @DisplayName("TotalElementos - Acepta diferentes cantidades")
    void totalElementos_AceptaDiferentesCantidades() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act & Assert - Cero elementos
        response.setTotalElementos(0L);
        assertThat(response.getTotalElementos()).isEqualTo(0L);

        // Pocos elementos
        response.setTotalElementos(15L);
        assertThat(response.getTotalElementos()).isEqualTo(15L);

        // Muchos elementos
        response.setTotalElementos(10000L);
        assertThat(response.getTotalElementos()).isEqualTo(10000L);

        // Nulo
        response.setTotalElementos(null);
        assertThat(response.getTotalElementos()).isNull();
    }

    @Test
    @DisplayName("TotalPaginas - Acepta diferentes cantidades")
    void totalPaginas_AceptaDiferentesCantidades() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act & Assert - Una página
        response.setTotalPaginas(1);
        assertThat(response.getTotalPaginas()).isEqualTo(1);

        // Múltiples páginas
        response.setTotalPaginas(50);
        assertThat(response.getTotalPaginas()).isEqualTo(50);

        // Nulo
        response.setTotalPaginas(null);
        assertThat(response.getTotalPaginas()).isNull();
    }

    @Test
    @DisplayName("EsPrimera - Acepta diferentes valores booleanos")
    void esPrimera_AceptaDiferentesValoresBooleanos() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act & Assert - true
        response.setEsPrimera(true);
        assertThat(response.getEsPrimera()).isTrue();

        // false
        response.setEsPrimera(false);
        assertThat(response.getEsPrimera()).isFalse();

        // Nulo
        response.setEsPrimera(null);
        assertThat(response.getEsPrimera()).isNull();
    }

    @Test
    @DisplayName("EsUltima - Acepta diferentes valores booleanos")
    void esUltima_AceptaDiferentesValoresBooleanos() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act & Assert - true
        response.setEsUltima(true);
        assertThat(response.getEsUltima()).isTrue();

        // false
        response.setEsUltima(false);
        assertThat(response.getEsUltima()).isFalse();

        // Nulo
        response.setEsUltima(null);
        assertThat(response.getEsUltima()).isNull();
    }

    @Test
    @DisplayName("TieneSiguiente - Acepta diferentes valores booleanos")
    void tieneSiguiente_AceptaDiferentesValoresBooleanos() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act & Assert - true
        response.setTieneSiguiente(true);
        assertThat(response.getTieneSiguiente()).isTrue();

        // false
        response.setTieneSiguiente(false);
        assertThat(response.getTieneSiguiente()).isFalse();

        // Nulo
        response.setTieneSiguiente(null);
        assertThat(response.getTieneSiguiente()).isNull();
    }

    @Test
    @DisplayName("TieneAnterior - Acepta diferentes valores booleanos")
    void tieneAnterior_AceptaDiferentesValoresBooleanos() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act & Assert - true
        response.setTieneAnterior(true);
        assertThat(response.getTieneAnterior()).isTrue();

        // false
        response.setTieneAnterior(false);
        assertThat(response.getTieneAnterior()).isFalse();

        // Nulo
        response.setTieneAnterior(null);
        assertThat(response.getTieneAnterior()).isNull();
    }

    @Test
    @DisplayName("Escenario - Primera página con múltiples elementos")
    void escenario_PrimeraPaginaConMultiplesElementos() {
        // Act
        PaginacionResponse<String> response = PaginacionResponse.<String>builder()
                .contenido(Arrays.asList("item1", "item2", "item3", "item4", "item5"))
                .paginaActual(0)
                .tamanoPagina(5)
                .totalElementos(25L)
                .totalPaginas(5)
                .esPrimera(true)
                .esUltima(false)
                .tieneSiguiente(true)
                .tieneAnterior(false)
                .build();

        // Assert
        assertThat(response.getContenido()).hasSize(5);
        assertThat(response.getPaginaActual()).isEqualTo(0);
        assertThat(response.getTamanoPagina()).isEqualTo(5);
        assertThat(response.getTotalElementos()).isEqualTo(25L);
        assertThat(response.getTotalPaginas()).isEqualTo(5);
        assertThat(response.getEsPrimera()).isTrue();
        assertThat(response.getEsUltima()).isFalse();
        assertThat(response.getTieneSiguiente()).isTrue();
        assertThat(response.getTieneAnterior()).isFalse();
    }

    @Test
    @DisplayName("Escenario - Página intermedia")
    void escenario_PaginaIntermedia() {
        // Act
        PaginacionResponse<Integer> response = PaginacionResponse.<Integer>builder()
                .contenido(Arrays.asList(11, 12, 13, 14, 15))
                .paginaActual(2)
                .tamanoPagina(5)
                .totalElementos(25L)
                .totalPaginas(5)
                .esPrimera(false)
                .esUltima(false)
                .tieneSiguiente(true)
                .tieneAnterior(true)
                .build();

        // Assert
        assertThat(response.getContenido()).containsExactly(11, 12, 13, 14, 15);
        assertThat(response.getPaginaActual()).isEqualTo(2);
        assertThat(response.getEsPrimera()).isFalse();
        assertThat(response.getEsUltima()).isFalse();
        assertThat(response.getTieneSiguiente()).isTrue();
        assertThat(response.getTieneAnterior()).isTrue();
    }

    @Test
    @DisplayName("Escenario - Última página")
    void escenario_UltimaPagina() {
        // Act
        PaginacionResponse<String> response = PaginacionResponse.<String>builder()
                .contenido(Arrays.asList("item21", "item22", "item23"))
                .paginaActual(4)
                .tamanoPagina(5)
                .totalElementos(23L)
                .totalPaginas(5)
                .esPrimera(false)
                .esUltima(true)
                .tieneSiguiente(false)
                .tieneAnterior(true)
                .build();

        // Assert
        assertThat(response.getContenido()).hasSize(3);
        assertThat(response.getPaginaActual()).isEqualTo(4);
        assertThat(response.getEsPrimera()).isFalse();
        assertThat(response.getEsUltima()).isTrue();
        assertThat(response.getTieneSiguiente()).isFalse();
        assertThat(response.getTieneAnterior()).isTrue();
        assertThat(response.getTotalElementos()).isEqualTo(23L);
    }

    @Test
    @DisplayName("Escenario - Página única")
    void escenario_PaginaUnica() {
        // Act
        PaginacionResponse<String> response = PaginacionResponse.<String>builder()
                .contenido(Arrays.asList("item1", "item2"))
                .paginaActual(0)
                .tamanoPagina(10)
                .totalElementos(2L)
                .totalPaginas(1)
                .esPrimera(true)
                .esUltima(true)
                .tieneSiguiente(false)
                .tieneAnterior(false)
                .build();

        // Assert
        assertThat(response.getContenido()).hasSize(2);
        assertThat(response.getTotalPaginas()).isEqualTo(1);
        assertThat(response.getEsPrimera()).isTrue();
        assertThat(response.getEsUltima()).isTrue();
        assertThat(response.getTieneSiguiente()).isFalse();
        assertThat(response.getTieneAnterior()).isFalse();
    }

    @Test
    @DisplayName("Escenario - Página vacía")
    void escenario_PaginaVacia() {
        // Act
        PaginacionResponse<String> response = PaginacionResponse.<String>builder()
                .contenido(Collections.emptyList())
                .paginaActual(0)
                .tamanoPagina(10)
                .totalElementos(0L)
                .totalPaginas(0)
                .esPrimera(true)
                .esUltima(true)
                .tieneSiguiente(false)
                .tieneAnterior(false)
                .build();

        // Assert
        assertThat(response.getContenido()).isEmpty();
        assertThat(response.getTotalElementos()).isEqualTo(0L);
        assertThat(response.getTotalPaginas()).isEqualTo(0);
        assertThat(response.getEsPrimera()).isTrue();
        assertThat(response.getEsUltima()).isTrue();
    }

    @Test
    @DisplayName("Casos borde - Contenido con muchos elementos")
    void casosBorde_ContenidoConMuchosElementos() {
        // Arrange & Act
        List<Integer> muchosElementos = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        PaginacionResponse<Integer> response = new PaginacionResponse<>();
        response.setContenido(muchosElementos);

        // Assert
        assertThat(response.getContenido()).hasSize(15);
        assertThat(response.getContenido()).contains(1, 15);
    }

    @Test
    @DisplayName("Casos borde - Página actual muy grande")
    void casosBorde_PaginaActualMuyGrande() {
        // Arrange & Act
        PaginacionResponse<String> response = new PaginacionResponse<>();
        response.setPaginaActual(Integer.MAX_VALUE);

        // Assert
        assertThat(response.getPaginaActual()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("Casos borde - Total elementos muy grande")
    void casosBorde_TotalElementosMuyGrande() {
        // Arrange & Act
        PaginacionResponse<String> response = new PaginacionResponse<>();
        response.setTotalElementos(Long.MAX_VALUE);

        // Assert
        assertThat(response.getTotalElementos()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("Casos borde - Total páginas muy grande")
    void casosBorde_TotalPaginasMuyGrande() {
        // Arrange & Act
        PaginacionResponse<String> response = new PaginacionResponse<>();
        response.setTotalPaginas(Integer.MAX_VALUE);

        // Assert
        assertThat(response.getTotalPaginas()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Assert
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        PaginacionResponse<String> response = new PaginacionResponse<>(
                Arrays.asList("a", "b"), 1, 10, 100L, 10, false, false, true, true
        );

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getContenido()).hasSize(2);
        assertThat(response.getPaginaActual()).isEqualTo(1);
        assertThat(response.getTamanoPagina()).isEqualTo(10);
        assertThat(response.getTotalElementos()).isEqualTo(100L);
        assertThat(response.getTotalPaginas()).isEqualTo(10);
        assertThat(response.getEsPrimera()).isFalse();
        assertThat(response.getEsUltima()).isFalse();
        assertThat(response.getTieneSiguiente()).isTrue();
        assertThat(response.getTieneAnterior()).isTrue();
    }

    @Test
    @DisplayName("Lombok - Builder funciona con genéricos")
    void lombok_BuilderFuncionaConGenericos() {
        // Act
        PaginacionResponse<Double> response = PaginacionResponse.<Double>builder()
                .contenido(Arrays.asList(1.1, 2.2, 3.3))
                .paginaActual(2)
                .tamanoPagina(3)
                .totalElementos(10L)
                .totalPaginas(4)
                .esPrimera(false)
                .esUltima(false)
                .tieneSiguiente(true)
                .tieneAnterior(true)
                .build();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getContenido()).containsExactly(1.1, 2.2, 3.3);
        assertThat(response.getPaginaActual()).isEqualTo(2);
        assertThat(response.getTamanoPagina()).isEqualTo(3);
        assertThat(response.getTotalElementos()).isEqualTo(10L);
        assertThat(response.getTotalPaginas()).isEqualTo(4);
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act
        response.setContenido(Arrays.asList("x", "y", "z"));
        response.setPaginaActual(3);
        response.setTamanoPagina(15);
        response.setTotalElementos(45L);
        response.setTotalPaginas(3);
        response.setEsPrimera(false);
        response.setEsUltima(true);
        response.setTieneSiguiente(false);
        response.setTieneAnterior(true);

        // Assert
        assertThat(response.getContenido()).hasSize(3);
        assertThat(response.getPaginaActual()).isEqualTo(3);
        assertThat(response.getTamanoPagina()).isEqualTo(15);
        assertThat(response.getTotalElementos()).isEqualTo(45L);
        assertThat(response.getTotalPaginas()).isEqualTo(3);
        assertThat(response.getEsPrimera()).isFalse();
        assertThat(response.getEsUltima()).isTrue();
        assertThat(response.getTieneSiguiente()).isFalse();
        assertThat(response.getTieneAnterior()).isTrue();
    }

    @Test
    @DisplayName("Relaciones lógicas - Coherencia entre campos booleanos")
    void relacionesLogicas_CoherenciaEntreCamposBooleanos() {
        // Arrange
        PaginacionResponse<String> response = new PaginacionResponse<>();

        // Act - Cuando es primera página
        response.setEsPrimera(true);
        response.setTieneAnterior(false);

        // Assert
        assertThat(response.getEsPrimera()).isTrue();
        assertThat(response.getTieneAnterior()).isFalse();

        // Act - Cuando es última página
        response.setEsUltima(true);
        response.setTieneSiguiente(false);

        // Assert
        assertThat(response.getEsUltima()).isTrue();
        assertThat(response.getTieneSiguiente()).isFalse();

        // Act - Cuando es página intermedia
        response.setEsPrimera(false);
        response.setEsUltima(false);
        response.setTieneAnterior(true);
        response.setTieneSiguiente(true);

        // Assert
        assertThat(response.getEsPrimera()).isFalse();
        assertThat(response.getEsUltima()).isFalse();
        assertThat(response.getTieneAnterior()).isTrue();
        assertThat(response.getTieneSiguiente()).isTrue();
    }
}