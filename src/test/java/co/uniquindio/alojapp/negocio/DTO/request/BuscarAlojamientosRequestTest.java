package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para BuscarAlojamientosRequest
 *
 * OBJETIVO: Probar el DTO de búsqueda de alojamientos con filtros
 * - Validar constructor, getters/setters y builder
 * - Verificar validaciones y valores por defecto
 * - Probar diferentes escenarios de búsqueda
 */
@DisplayName("BuscarAlojamientosRequest - Unit Tests")
public class BuscarAlojamientosRequestTest {

    // DATOS DE PRUEBA
    private final String CIUDAD_VALIDA = "Cartagena";
    private final LocalDate FECHA_CHECKIN_VALIDA = LocalDate.now().plusDays(7);
    private final LocalDate FECHA_CHECKOUT_VALIDA = LocalDate.now().plusDays(14);
    private final BigDecimal PRECIO_MIN_VALIDO = new BigDecimal("100000.00");
    private final BigDecimal PRECIO_MAX_VALIDO = new BigDecimal("500000.00");
    private final Integer CAPACIDAD_MINIMA_VALIDA = 4;
    private final List<Integer> SERVICIOS_IDS_VALIDOS = Arrays.asList(1, 3, 7);
    private final Integer PAGINA_VALIDA = 0;
    private final Integer TAMANO_PAGINA_VALIDO = 10;
    private final String DIRECCION_ORDEN_VALIDA = "ASC";
    private final String ORDENAR_POR_VALIDO = "precio_noche";

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        BuscarAlojamientosRequest request = new BuscarAlojamientosRequest();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCiudad()).isNull();
        assertThat(request.getFechaCheckin()).isNull();
        assertThat(request.getFechaCheckout()).isNull();
        assertThat(request.getPrecioMin()).isNull();
        assertThat(request.getPrecioMax()).isNull();
        assertThat(request.getCapacidadMinima()).isNull();
        assertThat(request.getServiciosIds()).isNull();
        assertThat(request.getPagina()).isEqualTo(0);
        assertThat(request.getTamanoPagina()).isEqualTo(10);
        assertThat(request.getDireccionOrden()).isEqualTo("ASC");
        assertThat(request.getOrdenarPor()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = new BuscarAlojamientosRequest(
                CIUDAD_VALIDA, FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA,
                PRECIO_MIN_VALIDO, PRECIO_MAX_VALIDO, CAPACIDAD_MINIMA_VALIDA,
                SERVICIOS_IDS_VALIDOS, PAGINA_VALIDA, TAMANO_PAGINA_VALIDO,
                DIRECCION_ORDEN_VALIDA, ORDENAR_POR_VALIDO
        );

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCiudad()).isEqualTo(CIUDAD_VALIDA);
        assertThat(request.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(request.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(request.getPrecioMin()).isEqualTo(PRECIO_MIN_VALIDO);
        assertThat(request.getPrecioMax()).isEqualTo(PRECIO_MAX_VALIDO);
        assertThat(request.getCapacidadMinima()).isEqualTo(CAPACIDAD_MINIMA_VALIDA);
        assertThat(request.getServiciosIds()).isEqualTo(SERVICIOS_IDS_VALIDOS);
        assertThat(request.getPagina()).isEqualTo(PAGINA_VALIDA);
        assertThat(request.getTamanoPagina()).isEqualTo(TAMANO_PAGINA_VALIDO);
        assertThat(request.getDireccionOrden()).isEqualTo(DIRECCION_ORDEN_VALIDA);
        assertThat(request.getOrdenarPor()).isEqualTo(ORDENAR_POR_VALIDO);
    }

    // ==================== BUILDER TESTS ====================

    @Test
    @DisplayName("BUILDER - Crea instancia correctamente")
    void builder_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .ciudad(CIUDAD_VALIDA)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .precioMin(PRECIO_MIN_VALIDO)
                .precioMax(PRECIO_MAX_VALIDO)
                .capacidadMinima(CAPACIDAD_MINIMA_VALIDA)
                .serviciosIds(SERVICIOS_IDS_VALIDOS)
                .pagina(PAGINA_VALIDA)
                .tamanoPagina(TAMANO_PAGINA_VALIDO)
                .direccionOrden(DIRECCION_ORDEN_VALIDA)
                .ordenarPor(ORDENAR_POR_VALIDO)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCiudad()).isEqualTo(CIUDAD_VALIDA);
        assertThat(request.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(request.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(request.getPrecioMin()).isEqualTo(PRECIO_MIN_VALIDO);
        assertThat(request.getPrecioMax()).isEqualTo(PRECIO_MAX_VALIDO);
        assertThat(request.getCapacidadMinima()).isEqualTo(CAPACIDAD_MINIMA_VALIDA);
        assertThat(request.getServiciosIds()).isEqualTo(SERVICIOS_IDS_VALIDOS);
        assertThat(request.getPagina()).isEqualTo(PAGINA_VALIDA);
        assertThat(request.getTamanoPagina()).isEqualTo(TAMANO_PAGINA_VALIDO);
        assertThat(request.getDireccionOrden()).isEqualTo(DIRECCION_ORDEN_VALIDA);
        assertThat(request.getOrdenarPor()).isEqualTo(ORDENAR_POR_VALIDO);
    }

    @Test
    @DisplayName("BUILDER - Usa valores por defecto correctamente")
    void builder_UsaValoresPorDefectoCorrectamente() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .ciudad(CIUDAD_VALIDA)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getCiudad()).isEqualTo(CIUDAD_VALIDA);
        assertThat(request.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(request.getPagina()).isEqualTo(0);
        assertThat(request.getTamanoPagina()).isEqualTo(10);
        assertThat(request.getDireccionOrden()).isEqualTo("ASC");
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        BuscarAlojamientosRequest request = new BuscarAlojamientosRequest();

        // ACT
        request.setCiudad(CIUDAD_VALIDA);
        request.setFechaCheckin(FECHA_CHECKIN_VALIDA);
        request.setFechaCheckout(FECHA_CHECKOUT_VALIDA);
        request.setPrecioMin(PRECIO_MIN_VALIDO);
        request.setPrecioMax(PRECIO_MAX_VALIDO);
        request.setCapacidadMinima(CAPACIDAD_MINIMA_VALIDA);
        request.setServiciosIds(SERVICIOS_IDS_VALIDOS);
        request.setPagina(1);
        request.setTamanoPagina(20);
        request.setDireccionOrden("DESC");
        request.setOrdenarPor("capacidad");

        // ASSERT
        assertThat(request.getCiudad()).isEqualTo(CIUDAD_VALIDA);
        assertThat(request.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(request.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(request.getPrecioMin()).isEqualTo(PRECIO_MIN_VALIDO);
        assertThat(request.getPrecioMax()).isEqualTo(PRECIO_MAX_VALIDO);
        assertThat(request.getCapacidadMinima()).isEqualTo(CAPACIDAD_MINIMA_VALIDA);
        assertThat(request.getServiciosIds()).isEqualTo(SERVICIOS_IDS_VALIDOS);
        assertThat(request.getPagina()).isEqualTo(1);
        assertThat(request.getTamanoPagina()).isEqualTo(20);
        assertThat(request.getDireccionOrden()).isEqualTo("DESC");
        assertThat(request.getOrdenarPor()).isEqualTo("capacidad");
    }

    // ==================== VALORES POR DEFECTO TESTS ====================

    @Test
    @DisplayName("VALORES POR DEFECTO - Se asignan correctamente")
    void valoresPorDefecto_SeAsignanCorrectamente() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = new BuscarAlojamientosRequest();

        // ASSERT
        assertThat(request.getPagina()).isEqualTo(0);
        assertThat(request.getTamanoPagina()).isEqualTo(10);
        assertThat(request.getDireccionOrden()).isEqualTo("ASC");
    }

    @Test
    @DisplayName("VALORES POR DEFECTO - Pueden ser sobrescritos")
    void valoresPorDefecto_PuedenSerSobrescritos() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .pagina(2)
                .tamanoPagina(25)
                .direccionOrden("DESC")
                .build();

        // ASSERT
        assertThat(request.getPagina()).isEqualTo(2);
        assertThat(request.getTamanoPagina()).isEqualTo(25);
        assertThat(request.getDireccionOrden()).isEqualTo("DESC");
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en TODOS los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        BuscarAlojamientosRequest request1 = new BuscarAlojamientosRequest(
                CIUDAD_VALIDA, FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA,
                PRECIO_MIN_VALIDO, PRECIO_MAX_VALIDO, CAPACIDAD_MINIMA_VALIDA,
                SERVICIOS_IDS_VALIDOS, PAGINA_VALIDA, TAMANO_PAGINA_VALIDO,
                DIRECCION_ORDEN_VALIDA, ORDENAR_POR_VALIDO
        );

        BuscarAlojamientosRequest request2 = new BuscarAlojamientosRequest(
                CIUDAD_VALIDA, FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA,
                PRECIO_MIN_VALIDO, PRECIO_MAX_VALIDO, CAPACIDAD_MINIMA_VALIDA,
                SERVICIOS_IDS_VALIDOS, PAGINA_VALIDA, TAMANO_PAGINA_VALIDO,
                DIRECCION_ORDEN_VALIDA, ORDENAR_POR_VALIDO
        );

        // ACT & ASSERT
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ciudad retorna false")
    void equals_DiferenteCiudad_RetornaFalse() {
        // ARRANGE
        BuscarAlojamientosRequest request1 = new BuscarAlojamientosRequest(
                "Cartagena", FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA,
                PRECIO_MIN_VALIDO, PRECIO_MAX_VALIDO, CAPACIDAD_MINIMA_VALIDA,
                SERVICIOS_IDS_VALIDOS, PAGINA_VALIDA, TAMANO_PAGINA_VALIDO,
                DIRECCION_ORDEN_VALIDA, ORDENAR_POR_VALIDO
        );

        BuscarAlojamientosRequest request2 = new BuscarAlojamientosRequest(
                "Medellín", FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA,
                PRECIO_MIN_VALIDO, PRECIO_MAX_VALIDO, CAPACIDAD_MINIMA_VALIDA,
                SERVICIOS_IDS_VALIDOS, PAGINA_VALIDA, TAMANO_PAGINA_VALIDO,
                DIRECCION_ORDEN_VALIDA, ORDENAR_POR_VALIDO
        );

        // ACT & ASSERT
        assertThat(request1).isNotEqualTo(request2);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del request")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        BuscarAlojamientosRequest request = new BuscarAlojamientosRequest(
                CIUDAD_VALIDA, FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA,
                PRECIO_MIN_VALIDO, PRECIO_MAX_VALIDO, CAPACIDAD_MINIMA_VALIDA,
                SERVICIOS_IDS_VALIDOS, PAGINA_VALIDA, TAMANO_PAGINA_VALIDO,
                DIRECCION_ORDEN_VALIDA, ORDENAR_POR_VALIDO
        );

        // ACT
        String resultado = request.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(CIUDAD_VALIDA);
        assertThat(resultado).contains(FECHA_CHECKIN_VALIDA.toString());
        assertThat(resultado).contains(PRECIO_MIN_VALIDO.toString());
        assertThat(resultado).contains(CAPACIDAD_MINIMA_VALIDA.toString());
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Búsqueda básica por ciudad")
    void scenarioUsoReal_BusquedaBasicaPorCiudad() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .ciudad("Cartagena")
                .build();

        // ASSERT
        assertThat(request.getCiudad()).isEqualTo("Cartagena");
        assertThat(request.getFechaCheckin()).isNull();
        assertThat(request.getFechaCheckout()).isNull();
        assertThat(request.getPrecioMin()).isNull();
        assertThat(request.getPrecioMax()).isNull();
        assertThat(request.getCapacidadMinima()).isNull();
        assertThat(request.getServiciosIds()).isNull();
        assertThat(request.getPagina()).isEqualTo(0);
        assertThat(request.getTamanoPagina()).isEqualTo(10);
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Búsqueda con fechas y capacidad")
    void scenarioUsoReal_BusquedaConFechasYCapacidad() {
        // ARRANGE & ACT
        LocalDate checkin = LocalDate.now().plusDays(15);
        LocalDate checkout = LocalDate.now().plusDays(22);

        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .ciudad("Medellín")
                .fechaCheckin(checkin)
                .fechaCheckout(checkout)
                .capacidadMinima(6)
                .build();

        // ASSERT
        assertThat(request.getCiudad()).isEqualTo("Medellín");
        assertThat(request.getFechaCheckin()).isEqualTo(checkin);
        assertThat(request.getFechaCheckout()).isEqualTo(checkout);
        assertThat(request.getCapacidadMinima()).isEqualTo(6);
        assertThat(request.getFechaCheckout()).isAfter(request.getFechaCheckin());
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Búsqueda con rango de precios")
    void scenarioUsoReal_BusquedaConRangoDePrecios() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .ciudad("Bogotá")
                .precioMin(new BigDecimal("150000"))
                .precioMax(new BigDecimal("300000"))
                .build();

        // ASSERT
        assertThat(request.getCiudad()).isEqualTo("Bogotá");
        assertThat(request.getPrecioMin()).isEqualByComparingTo("150000");
        assertThat(request.getPrecioMax()).isEqualByComparingTo("300000");
        assertThat(request.getPrecioMax()).isGreaterThan(request.getPrecioMin());
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Búsqueda con servicios específicos")
    void scenarioUsoReal_BusquedaConServiciosEspecificos() {
        // ARRANGE & ACT
        List<Integer> servicios = Arrays.asList(1, 2, 5, 8);

        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .ciudad("Santa Marta")
                .serviciosIds(servicios)
                .build();

        // ASSERT
        assertThat(request.getCiudad()).isEqualTo("Santa Marta");
        assertThat(request.getServiciosIds()).hasSize(4);
        assertThat(request.getServiciosIds()).containsExactly(1, 2, 5, 8);
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Búsqueda con paginación y ordenamiento")
    void scenarioUsoReal_BusquedaConPaginacionYOrdenamiento() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .ciudad("Cali")
                .pagina(2)
                .tamanoPagina(15)
                .ordenarPor("precio_noche")
                .direccionOrden("DESC")
                .build();

        // ASSERT
        assertThat(request.getCiudad()).isEqualTo("Cali");
        assertThat(request.getPagina()).isEqualTo(2);
        assertThat(request.getTamanoPagina()).isEqualTo(15);
        assertThat(request.getOrdenarPor()).isEqualTo("precio_noche");
        assertThat(request.getDireccionOrden()).isEqualTo("DESC");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Búsqueda completa con todos los filtros")
    void scenarioUsoReal_BusquedaCompletaConTodosLosFiltros() {
        // ARRANGE & ACT
        LocalDate checkin = LocalDate.now().plusDays(30);
        LocalDate checkout = LocalDate.now().plusDays(37);
        List<Integer> servicios = Arrays.asList(1, 3, 4, 7);

        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .ciudad("Cartagena")
                .fechaCheckin(checkin)
                .fechaCheckout(checkout)
                .precioMin(new BigDecimal("200000"))
                .precioMax(new BigDecimal("600000"))
                .capacidadMinima(4)
                .serviciosIds(servicios)
                .pagina(1)
                .tamanoPagina(20)
                .ordenarPor("calificacion")
                .direccionOrden("DESC")
                .build();

        // ASSERT
        assertThat(request.getCiudad()).isEqualTo("Cartagena");
        assertThat(request.getFechaCheckin()).isAfter(LocalDate.now());
        assertThat(request.getFechaCheckout()).isAfter(request.getFechaCheckin());
        assertThat(request.getPrecioMin()).isEqualByComparingTo("200000");
        assertThat(request.getPrecioMax()).isEqualByComparingTo("600000");
        assertThat(request.getCapacidadMinima()).isEqualTo(4);
        assertThat(request.getServiciosIds()).hasSize(4);
        assertThat(request.getPagina()).isEqualTo(1);
        assertThat(request.getTamanoPagina()).isEqualTo(20);
        assertThat(request.getOrdenarPor()).isEqualTo("calificacion");
        assertThat(request.getDireccionOrden()).isEqualTo("DESC");
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Request completamente vacío")
    void casoBorde_RequestCompletamenteVacio() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = new BuscarAlojamientosRequest();

        // ASSERT
        assertThat(request.getCiudad()).isNull();
        assertThat(request.getFechaCheckin()).isNull();
        assertThat(request.getFechaCheckout()).isNull();
        assertThat(request.getPrecioMin()).isNull();
        assertThat(request.getPrecioMax()).isNull();
        assertThat(request.getCapacidadMinima()).isNull();
        assertThat(request.getServiciosIds()).isNull();
        assertThat(request.getOrdenarPor()).isNull();
        // Valores por defecto
        assertThat(request.getPagina()).isEqualTo(0);
        assertThat(request.getTamanoPagina()).isEqualTo(10);
        assertThat(request.getDireccionOrden()).isEqualTo("ASC");
    }

    @Test
    @DisplayName("CASO BORDE - Ciudad con longitud máxima")
    void casoBorde_CiudadConLongitudMaxima() {
        // ARRANGE & ACT
        String ciudadMaxima = "A".repeat(100); // 100 caracteres exactos
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .ciudad(ciudadMaxima)
                .build();

        // ASSERT
        assertThat(request.getCiudad()).hasSize(100);
        assertThat(request.getCiudad()).isEqualTo(ciudadMaxima);
    }

    @Test
    @DisplayName("CASO BORDE - Precio mínimo cero")
    void casoBorde_PrecioMinimoCero() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .precioMin(BigDecimal.ZERO)
                .build();

        // ASSERT
        assertThat(request.getPrecioMin()).isEqualByComparingTo("0");
    }

    @Test
    @DisplayName("CASO BORDE - Capacidad mínima 1")
    void casoBorde_CapacidadMinimaUno() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .capacidadMinima(1)
                .build();

        // ASSERT
        assertThat(request.getCapacidadMinima()).isEqualTo(1);
    }

    @Test
    @DisplayName("CASO BORDE - Lista de servicios vacía")
    void casoBorde_ListaDeServiciosVacia() {
        // ARRANGE & ACT
        List<Integer> serviciosVacios = Arrays.asList();
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .serviciosIds(serviciosVacios)
                .build();

        // ASSERT
        assertThat(request.getServiciosIds()).isEmpty();
    }

    @Test
    @DisplayName("CASO BORDE - Tamaño de página máximo")
    void casoBorde_TamanoPaginaMaximo() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .tamanoPagina(50)
                .build();

        // ASSERT
        assertThat(request.getTamanoPagina()).isEqualTo(50);
    }

    @Test
    @DisplayName("CASO BORDE - Fecha checkin presente")
    void casoBorde_FechaCheckinPresente() {
        // ARRANGE & ACT
        LocalDate fechaPresente = LocalDate.now();
        BuscarAlojamientosRequest request = BuscarAlojamientosRequest.builder()
                .fechaCheckin(fechaPresente)
                .build();

        // ASSERT
        assertThat(request.getFechaCheckin()).isEqualTo(fechaPresente);
    }

    // ==================== TIPOS DE DATOS TESTS ====================

    @Test
    @DisplayName("TIPOS DE DATOS - Campos tienen los tipos correctos")
    void tiposDeDatos_CamposTienenLosTiposCorrectos() {
        // ARRANGE & ACT
        BuscarAlojamientosRequest request = new BuscarAlojamientosRequest(
                CIUDAD_VALIDA, FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA,
                PRECIO_MIN_VALIDO, PRECIO_MAX_VALIDO, CAPACIDAD_MINIMA_VALIDA,
                SERVICIOS_IDS_VALIDOS, PAGINA_VALIDA, TAMANO_PAGINA_VALIDO,
                DIRECCION_ORDEN_VALIDA, ORDENAR_POR_VALIDO
        );

        // ASSERT
        assertThat(request.getCiudad()).isInstanceOf(String.class);
        assertThat(request.getFechaCheckin()).isInstanceOf(LocalDate.class);
        assertThat(request.getFechaCheckout()).isInstanceOf(LocalDate.class);
        assertThat(request.getPrecioMin()).isInstanceOf(BigDecimal.class);
        assertThat(request.getPrecioMax()).isInstanceOf(BigDecimal.class);
        assertThat(request.getCapacidadMinima()).isInstanceOf(Integer.class);
        assertThat(request.getServiciosIds()).isInstanceOf(List.class);
        assertThat(request.getPagina()).isInstanceOf(Integer.class);
        assertThat(request.getTamanoPagina()).isInstanceOf(Integer.class);
        assertThat(request.getDireccionOrden()).isInstanceOf(String.class);
        assertThat(request.getOrdenarPor()).isInstanceOf(String.class);
    }

    // ==================== BUSINESS LOGIC TESTS ====================

    @Test
    @DisplayName("BUSINESS - Request permite búsqueda flexible con filtros opcionales")
    void business_RequestPermiteBusquedaFlexibleConFiltrosOpcionales() {
        // ARRANGE & ACT - Solo algunos filtros
        BuscarAlojamientosRequest requestFlexible = BuscarAlojamientosRequest.builder()
                .ciudad("Cartagena")
                .capacidadMinima(2)
                .pagina(0)
                .build();

        // ASSERT - Solo los campos especificados deben tener valor
        assertThat(requestFlexible.getCiudad()).isNotNull();
        assertThat(requestFlexible.getCapacidadMinima()).isNotNull();
        assertThat(requestFlexible.getPagina()).isNotNull();
        assertThat(requestFlexible.getFechaCheckin()).isNull();
        assertThat(requestFlexible.getPrecioMin()).isNull();
        assertThat(requestFlexible.getServiciosIds()).isNull();
    }

    @Test
    @DisplayName("BUSINESS - Diferentes métodos de construcción producen objetos iguales")
    void business_DiferentesMetodosDeConstruccion_ProducenObjetosIguales() {
        // ARRANGE
        BuscarAlojamientosRequest viaConstructor = new BuscarAlojamientosRequest(
                "Medellín", FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA,
                PRECIO_MIN_VALIDO, PRECIO_MAX_VALIDO, 2,
                Arrays.asList(1, 2), 1, 15, "DESC", "precio_noche"
        );

        BuscarAlojamientosRequest viaBuilder = BuscarAlojamientosRequest.builder()
                .ciudad("Medellín")
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .precioMin(PRECIO_MIN_VALIDO)
                .precioMax(PRECIO_MAX_VALIDO)
                .capacidadMinima(2)
                .serviciosIds(Arrays.asList(1, 2))
                .pagina(1)
                .tamanoPagina(15)
                .direccionOrden("DESC")
                .ordenarPor("precio_noche")
                .build();

        // ACT & ASSERT - Ambos deben ser iguales
        assertThat(viaConstructor).isEqualTo(viaBuilder);
        assertThat(viaConstructor.hashCode()).isEqualTo(viaBuilder.hashCode());
    }

    @Test
    @DisplayName("BUSINESS - Instancia puede ser clonada correctamente")
    void business_InstanciaPuedeSerClonadaCorrectamente() {
        // ARRANGE
        BuscarAlojamientosRequest original = BuscarAlojamientosRequest.builder()
                .ciudad("Bogotá")
                .fechaCheckin(LocalDate.now().plusDays(10))
                .fechaCheckout(LocalDate.now().plusDays(17))
                .precioMin(new BigDecimal("180000"))
                .precioMax(new BigDecimal("350000"))
                .capacidadMinima(3)
                .serviciosIds(Arrays.asList(2, 4, 6))
                .pagina(0)
                .tamanoPagina(12)
                .direccionOrden("ASC")
                .ordenarPor("nombre")
                .build();

        // ACT - Simular clonación
        BuscarAlojamientosRequest clon = BuscarAlojamientosRequest.builder()
                .ciudad(original.getCiudad())
                .fechaCheckin(original.getFechaCheckin())
                .fechaCheckout(original.getFechaCheckout())
                .precioMin(original.getPrecioMin())
                .precioMax(original.getPrecioMax())
                .capacidadMinima(original.getCapacidadMinima())
                .serviciosIds(original.getServiciosIds())
                .pagina(original.getPagina())
                .tamanoPagina(original.getTamanoPagina())
                .direccionOrden(original.getDireccionOrden())
                .ordenarPor(original.getOrdenarPor())
                .build();

        // ASSERT
        assertThat(clon).isEqualTo(original);
        assertThat(clon.getCiudad()).isEqualTo(original.getCiudad());
        assertThat(clon.getFechaCheckin()).isEqualTo(original.getFechaCheckin());
        assertThat(clon.getPrecioMin()).isEqualTo(original.getPrecioMin());
        assertThat(clon.getServiciosIds()).isEqualTo(original.getServiciosIds());
    }
}