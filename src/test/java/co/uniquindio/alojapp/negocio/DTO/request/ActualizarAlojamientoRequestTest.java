package co.uniquindio.alojapp.negocio.DTO.request;

import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoAlojamiento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ActualizarAlojamientoRequest
 *
 * OBJETIVO: Probar el DTO de actualización parcial de alojamiento
 * - Validar constructor, builder, getters/setters
 * - Verificar equals/hashCode/toString
 * - Probar diferentes escenarios de actualización
 */
@DisplayName("ActualizarAlojamientoRequest - Unit Tests")
public class ActualizarAlojamientoRequestTest {

    // DATOS DE PRUEBA
    private final String TITULO_VALIDO = "Casa de playa en Cartagena";
    private final String DESCRIPCION_VALIDA = "Hermosa casa frente al mar con amplios espacios, piscina privada y vista al océano. Perfecta para vacaciones familiares.";
    private final String CIUDAD_VALIDA = "Cartagena";
    private final String DIRECCION_VALIDA = "Calle 10 #5-20, Bocagrande";
    private final BigDecimal LATITUD_VALIDA = new BigDecimal("10.3910485");
    private final BigDecimal LONGITUD_VALIDA = new BigDecimal("-75.4794257");
    private final BigDecimal PRECIO_NOCHE_VALIDO = new BigDecimal("350000.00");
    private final Integer CAPACIDAD_MAXIMA_VALIDA = 8;
    private final String IMAGEN_PRINCIPAL_URL_VALIDA = "https://ejemplo.com/img1.jpg";
    private final EstadoAlojamiento ESTADO_VALIDO = EstadoAlojamiento.ACTIVO;
    private final List<Integer> SERVICIOS_IDS_VALIDOS = Arrays.asList(1, 3, 5);

    // ==================== BUILDER TESTS ====================

    @Test
    @DisplayName("BUILDER - Crea instancia con todos los campos correctamente")
    void builder_ConTodosLosCampos_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .descripcion(DESCRIPCION_VALIDA)
                .ciudad(CIUDAD_VALIDA)
                .direccion(DIRECCION_VALIDA)
                .latitud(LATITUD_VALIDA)
                .longitud(LONGITUD_VALIDA)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .capacidadMaxima(CAPACIDAD_MAXIMA_VALIDA)
                .imagenPrincipalUrl(IMAGEN_PRINCIPAL_URL_VALIDA)
                .estado(ESTADO_VALIDO)
                .serviciosIds(SERVICIOS_IDS_VALIDOS)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(request.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(request.getCiudad()).isEqualTo(CIUDAD_VALIDA);
        assertThat(request.getDireccion()).isEqualTo(DIRECCION_VALIDA);
        assertThat(request.getLatitud()).isEqualTo(LATITUD_VALIDA);
        assertThat(request.getLongitud()).isEqualTo(LONGITUD_VALIDA);
        assertThat(request.getPrecioNoche()).isEqualTo(PRECIO_NOCHE_VALIDO);
        assertThat(request.getCapacidadMaxima()).isEqualTo(CAPACIDAD_MAXIMA_VALIDA);
        assertThat(request.getImagenPrincipalUrl()).isEqualTo(IMAGEN_PRINCIPAL_URL_VALIDA);
        assertThat(request.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(request.getServiciosIds()).isEqualTo(SERVICIOS_IDS_VALIDOS);
    }

    @Test
    @DisplayName("BUILDER - Campos opcionales null se manejan correctamente")
    void builder_CamposOpcionalesNull_SeManejanCorrectamente() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .descripcion(DESCRIPCION_VALIDA)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .capacidadMaxima(CAPACIDAD_MAXIMA_VALIDA)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(request.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(request.getPrecioNoche()).isEqualTo(PRECIO_NOCHE_VALIDO);
        assertThat(request.getCapacidadMaxima()).isEqualTo(CAPACIDAD_MAXIMA_VALIDA);

        // Campos opcionales null
        assertThat(request.getCiudad()).isNull();
        assertThat(request.getDireccion()).isNull();
        assertThat(request.getLatitud()).isNull();
        assertThat(request.getLongitud()).isNull();
        assertThat(request.getImagenPrincipalUrl()).isNull();
        assertThat(request.getEstado()).isNull();
        assertThat(request.getServiciosIds()).isNull();
    }

    @Test
    @DisplayName("BUILDER - Builder vacío crea instancia con campos null")
    void builder_BuilderVacio_CreaInstanciaConCamposNull() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder().build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getTitulo()).isNull();
        assertThat(request.getDescripcion()).isNull();
        assertThat(request.getCiudad()).isNull();
        assertThat(request.getDireccion()).isNull();
        assertThat(request.getLatitud()).isNull();
        assertThat(request.getLongitud()).isNull();
        assertThat(request.getPrecioNoche()).isNull();
        assertThat(request.getCapacidadMaxima()).isNull();
        assertThat(request.getImagenPrincipalUrl()).isNull();
        assertThat(request.getEstado()).isNull();
        assertThat(request.getServiciosIds()).isNull();
    }

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getTitulo()).isNull();
        assertThat(request.getDescripcion()).isNull();
        assertThat(request.getCiudad()).isNull();
        assertThat(request.getDireccion()).isNull();
        assertThat(request.getLatitud()).isNull();
        assertThat(request.getLongitud()).isNull();
        assertThat(request.getPrecioNoche()).isNull();
        assertThat(request.getCapacidadMaxima()).isNull();
        assertThat(request.getImagenPrincipalUrl()).isNull();
        assertThat(request.getEstado()).isNull();
        assertThat(request.getServiciosIds()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest(
                TITULO_VALIDO, DESCRIPCION_VALIDA, CIUDAD_VALIDA, DIRECCION_VALIDA,
                LATITUD_VALIDA, LONGITUD_VALIDA, PRECIO_NOCHE_VALIDO, CAPACIDAD_MAXIMA_VALIDA,
                IMAGEN_PRINCIPAL_URL_VALIDA, ESTADO_VALIDO, SERVICIOS_IDS_VALIDOS
        );

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(request.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(request.getCiudad()).isEqualTo(CIUDAD_VALIDA);
        assertThat(request.getDireccion()).isEqualTo(DIRECCION_VALIDA);
        assertThat(request.getLatitud()).isEqualTo(LATITUD_VALIDA);
        assertThat(request.getLongitud()).isEqualTo(LONGITUD_VALIDA);
        assertThat(request.getPrecioNoche()).isEqualTo(PRECIO_NOCHE_VALIDO);
        assertThat(request.getCapacidadMaxima()).isEqualTo(CAPACIDAD_MAXIMA_VALIDA);
        assertThat(request.getImagenPrincipalUrl()).isEqualTo(IMAGEN_PRINCIPAL_URL_VALIDA);
        assertThat(request.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(request.getServiciosIds()).isEqualTo(SERVICIOS_IDS_VALIDOS);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest();

        // ACT
        request.setTitulo(TITULO_VALIDO);
        request.setDescripcion(DESCRIPCION_VALIDA);
        request.setCiudad(CIUDAD_VALIDA);
        request.setDireccion(DIRECCION_VALIDA);
        request.setLatitud(LATITUD_VALIDA);
        request.setLongitud(LONGITUD_VALIDA);
        request.setPrecioNoche(PRECIO_NOCHE_VALIDO);
        request.setCapacidadMaxima(CAPACIDAD_MAXIMA_VALIDA);
        request.setImagenPrincipalUrl(IMAGEN_PRINCIPAL_URL_VALIDA);
        request.setEstado(ESTADO_VALIDO);
        request.setServiciosIds(SERVICIOS_IDS_VALIDOS);

        // ASSERT
        assertThat(request.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(request.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(request.getCiudad()).isEqualTo(CIUDAD_VALIDA);
        assertThat(request.getDireccion()).isEqualTo(DIRECCION_VALIDA);
        assertThat(request.getLatitud()).isEqualTo(LATITUD_VALIDA);
        assertThat(request.getLongitud()).isEqualTo(LONGITUD_VALIDA);
        assertThat(request.getPrecioNoche()).isEqualTo(PRECIO_NOCHE_VALIDO);
        assertThat(request.getCapacidadMaxima()).isEqualTo(CAPACIDAD_MAXIMA_VALIDA);
        assertThat(request.getImagenPrincipalUrl()).isEqualTo(IMAGEN_PRINCIPAL_URL_VALIDA);
        assertThat(request.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(request.getServiciosIds()).isEqualTo(SERVICIOS_IDS_VALIDOS);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en TODOS los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        ActualizarAlojamientoRequest request1 = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .descripcion(DESCRIPCION_VALIDA)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .capacidadMaxima(CAPACIDAD_MAXIMA_VALIDA)
                .estado(ESTADO_VALIDO)
                .serviciosIds(SERVICIOS_IDS_VALIDOS)
                .build();

        ActualizarAlojamientoRequest request2 = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .descripcion(DESCRIPCION_VALIDA)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .capacidadMaxima(CAPACIDAD_MAXIMA_VALIDA)
                .estado(ESTADO_VALIDO)
                .serviciosIds(SERVICIOS_IDS_VALIDOS)
                .build();

        // ACT & ASSERT
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Ambos objetos con todos campos null son iguales")
    void equals_AmbosObjetosConTodosCamposNull_SonIguales() {
        // ARRANGE
        ActualizarAlojamientoRequest request1 = new ActualizarAlojamientoRequest();
        ActualizarAlojamientoRequest request2 = new ActualizarAlojamientoRequest();

        // ACT & ASSERT
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente título retorna false")
    void equals_DiferenteTitulo_RetornaFalse() {
        // ARRANGE
        ActualizarAlojamientoRequest request1 = ActualizarAlojamientoRequest.builder()
                .titulo("Casa en Cartagena")
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .build();

        ActualizarAlojamientoRequest request2 = ActualizarAlojamientoRequest.builder()
                .titulo("Apartamento en Medellín")
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @DisplayName("EQUALS - Diferentes serviciosIds retorna false")
    void equals_DiferentesServiciosIds_RetornaFalse() {
        // ARRANGE
        ActualizarAlojamientoRequest request1 = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .serviciosIds(Arrays.asList(1, 2, 3))
                .build();

        ActualizarAlojamientoRequest request2 = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .serviciosIds(Arrays.asList(4, 5, 6))
                .build();

        // ACT & ASSERT
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(request).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(request).isNotEqualTo("No soy un request");
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del request")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .capacidadMaxima(CAPACIDAD_MAXIMA_VALIDA)
                .estado(ESTADO_VALIDO)
                .build();

        // ACT
        String resultado = request.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(TITULO_VALIDO);
        assertThat(resultado).contains(PRECIO_NOCHE_VALIDO.toString());
        assertThat(resultado).contains(String.valueOf(CAPACIDAD_MAXIMA_VALIDA));
        assertThat(resultado).contains(ESTADO_VALIDO.toString());
    }

    // ==================== CAMPOS INDIVIDUALES TESTS ====================

    @Test
    @DisplayName("TÍTULO - Diferentes longitudes de título son aceptadas")
    void titulo_DiferentesLongitudesDeTitulo_SonAceptadas() {
        // ARRANGE
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest();

        // ACT & ASSERT
        request.setTitulo("Casa pequeña"); // 12 caracteres
        assertThat(request.getTitulo()).isEqualTo("Casa pequeña");

        request.setTitulo("Hermosa casa de playa con vista al mar en Cartagena de Indias - Colombia");
        assertThat(request.getTitulo()).hasSizeGreaterThan(30);

        request.setTitulo("");
        assertThat(request.getTitulo()).isEmpty();

        request.setTitulo(null);
        assertThat(request.getTitulo()).isNull();
    }

    @Test
    @DisplayName("DESCRIPCIÓN - Diferentes longitudes de descripción son aceptadas")
    void descripcion_DiferentesLongitudesDeDescripcion_SonAceptadas() {
        // ARRANGE
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest();

        // ACT & ASSERT
        request.setDescripcion("Casa cómoda y bien ubicada.");
        assertThat(request.getDescripcion()).isEqualTo("Casa cómoda y bien ubicada.");

        String descripcionLarga = "Esta espectacular propiedad ofrece " +
                "amplios espacios, diseño moderno y todas las comodidades " +
                "que usted y su familia necesitan para unas vacaciones inolvidables. " +
                "Ubicada en una de las mejores zonas de la ciudad, cerca de " +
                "centros comerciales, restaurantes y atracciones turísticas.";
        request.setDescripcion(descripcionLarga);
        assertThat(request.getDescripcion()).hasSizeGreaterThan(100);

        request.setDescripcion("");
        assertThat(request.getDescripcion()).isEmpty();

        request.setDescripcion(null);
        assertThat(request.getDescripcion()).isNull();
    }

    @Test
    @DisplayName("UBICACIÓN - Diferentes valores de ubicación son aceptados")
    void ubicacion_DiferentesValoresDeUbicacion_SonAceptados() {
        // ARRANGE
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest();

        // ACT & ASSERT
        request.setCiudad("Medellín");
        request.setDireccion("Carrera 43A #1-50, El Poblado");
        request.setLatitud(new BigDecimal("6.244203"));
        request.setLongitud(new BigDecimal("-75.581211"));

        assertThat(request.getCiudad()).isEqualTo("Medellín");
        assertThat(request.getDireccion()).contains("El Poblado");
        assertThat(request.getLatitud()).isEqualByComparingTo("6.244203");
        assertThat(request.getLongitud()).isEqualByComparingTo("-75.581211");

        request.setCiudad(null);
        request.setDireccion(null);
        request.setLatitud(null);
        request.setLongitud(null);

        assertThat(request.getCiudad()).isNull();
        assertThat(request.getDireccion()).isNull();
        assertThat(request.getLatitud()).isNull();
        assertThat(request.getLongitud()).isNull();
    }

    @Test
    @DisplayName("PRECIO Y CAPACIDAD - Diferentes valores numéricos son aceptados")
    void precioYCapacidad_DiferentesValoresNumericos_SonAceptados() {
        // ARRANGE
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest();

        // ACT & ASSERT
        request.setPrecioNoche(new BigDecimal("150000.50"));
        request.setCapacidadMaxima(4);

        assertThat(request.getPrecioNoche()).isEqualByComparingTo("150000.50");
        assertThat(request.getCapacidadMaxima()).isEqualTo(4);

        request.setPrecioNoche(new BigDecimal("1000000.00"));
        request.setCapacidadMaxima(20);

        assertThat(request.getPrecioNoche()).isEqualByComparingTo("1000000.00");
        assertThat(request.getCapacidadMaxima()).isEqualTo(20);

        request.setPrecioNoche(null);
        request.setCapacidadMaxima(null);

        assertThat(request.getPrecioNoche()).isNull();
        assertThat(request.getCapacidadMaxima()).isNull();
    }

    @Test
    @DisplayName("ESTADO - Todos los estados de alojamiento son aceptados")
    void estado_TodosLosEstadosDeAlojamiento_SonAceptados() {
        // ARRANGE
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest();

        // ACT & ASSERT
        request.setEstado(EstadoAlojamiento.ACTIVO);
        assertThat(request.getEstado()).isEqualTo(EstadoAlojamiento.ACTIVO);

        request.setEstado(EstadoAlojamiento.INACTIVO);
        assertThat(request.getEstado()).isEqualTo(EstadoAlojamiento.INACTIVO);

        request.setEstado(EstadoAlojamiento.DISPONIBLE);
        assertThat(request.getEstado()).isEqualTo(EstadoAlojamiento.DISPONIBLE);

        request.setEstado(EstadoAlojamiento.RESERVADO);
        assertThat(request.getEstado()).isEqualTo(EstadoAlojamiento.RESERVADO);

        request.setEstado(EstadoAlojamiento.ELIMINADO);
        assertThat(request.getEstado()).isEqualTo(EstadoAlojamiento.ELIMINADO);

        request.setEstado(null);
        assertThat(request.getEstado()).isNull();
    }
    @Test
    @DisplayName("SERVICIOS - Diferentes listas de servicios son aceptadas")
    void servicios_DiferentesListasDeServicios_SonAceptadas() {
        // ARRANGE
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest();

        // ACT & ASSERT
        request.setServiciosIds(Arrays.asList(1));
        assertThat(request.getServiciosIds()).containsExactly(1);

        request.setServiciosIds(Arrays.asList(1, 2, 3, 4, 5));
        assertThat(request.getServiciosIds()).hasSize(5);
        assertThat(request.getServiciosIds()).containsExactly(1, 2, 3, 4, 5);

        request.setServiciosIds(Arrays.asList());
        assertThat(request.getServiciosIds()).isEmpty();

        request.setServiciosIds(null);
        assertThat(request.getServiciosIds()).isNull();
    }

    @Test
    @DisplayName("IMAGEN - Diferentes URLs de imagen son aceptadas")
    void imagen_DiferentesUrlsDeImagen_SonAceptadas() {
        // ARRANGE
        ActualizarAlojamientoRequest request = new ActualizarAlojamientoRequest();

        // ACT & ASSERT
        request.setImagenPrincipalUrl("https://cloudinary.com/alojamiento1.jpg");
        assertThat(request.getImagenPrincipalUrl()).isEqualTo("https://cloudinary.com/alojamiento1.jpg");

        request.setImagenPrincipalUrl("/ruta/local/imagen.png");
        assertThat(request.getImagenPrincipalUrl()).isEqualTo("/ruta/local/imagen.png");

        request.setImagenPrincipalUrl("");
        assertThat(request.getImagenPrincipalUrl()).isEmpty();

        request.setImagenPrincipalUrl(null);
        assertThat(request.getImagenPrincipalUrl()).isNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización solo de precio")
    void scenarioUsoReal_ActualizacionSoloDePrecio() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .precioNoche(new BigDecimal("400000.00"))
                .build();

        // ASSERT - Solo se actualiza el precio
        assertThat(request.getPrecioNoche()).isEqualByComparingTo("400000.00");
        assertThat(request.getTitulo()).isNull();
        assertThat(request.getDescripcion()).isNull();
        assertThat(request.getEstado()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización de información básica")
    void scenarioUsoReal_ActualizacionDeInformacionBasica() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo("Casa moderna en El Poblado")
                .descripcion("Recién renovada con acabados de lujo y amenities exclusivos")
                .ciudad("Medellín")
                .precioNoche(new BigDecimal("550000.00"))
                .capacidadMaxima(6)
                .build();

        // ASSERT
        assertThat(request.getTitulo()).isEqualTo("Casa moderna en El Poblado");
        assertThat(request.getDescripcion()).contains("renovada", "lujo");
        assertThat(request.getCiudad()).isEqualTo("Medellín");
        assertThat(request.getPrecioNoche()).isEqualByComparingTo("550000.00");
        assertThat(request.getCapacidadMaxima()).isEqualTo(6);
        assertThat(request.getEstado()).isNull();
        assertThat(request.getServiciosIds()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización de estado y servicios")
    void scenarioUsoReal_ActualizacionDeEstadoYServicios() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .estado(EstadoAlojamiento.ACTIVO)
                .serviciosIds(Arrays.asList(2, 4, 7, 9))
                .imagenPrincipalUrl("https://cloudinary.com/nueva-imagen.jpg")
                .build();

        // ASSERT
        assertThat(request.getEstado()).isEqualTo(EstadoAlojamiento.ACTIVO);
        assertThat(request.getServiciosIds()).containsExactly(2, 4, 7, 9);
        assertThat(request.getImagenPrincipalUrl()).isEqualTo("https://cloudinary.com/nueva-imagen.jpg");
        assertThat(request.getTitulo()).isNull();
        assertThat(request.getPrecioNoche()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización completa de alojamiento")
    void scenarioUsoReal_ActualizacionCompletaDeAlojamiento() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo("Lujoso penthouse con vista panorámica")
                .descripcion("Exclusivo penthouse en torre de lujo con amenities premium, piscina infinita, gimnasio privado y servicio de conserjería 24/7.")
                .ciudad("Bogotá")
                .direccion("Carrera 7 #115-60, Torre B, Piso 25")
                .latitud(new BigDecimal("4.6979798"))
                .longitud(new BigDecimal("-74.0688356"))
                .precioNoche(new BigDecimal("1200000.00"))
                .capacidadMaxima(4)
                .imagenPrincipalUrl("https://cloudinary.com/penthouse-lujo.jpg")
                .estado(EstadoAlojamiento.ACTIVO)
                .serviciosIds(Arrays.asList(1, 3, 5, 7, 9, 11))
                .build();

        // ASSERT
        assertThat(request.getTitulo()).isEqualTo("Lujoso penthouse con vista panorámica");
        assertThat(request.getDescripcion()).hasSizeGreaterThan(50);
        assertThat(request.getCiudad()).isEqualTo("Bogotá");
        assertThat(request.getDireccion()).contains("Torre B");
        assertThat(request.getLatitud()).isNotNull();
        assertThat(request.getLongitud()).isNotNull();
        assertThat(request.getPrecioNoche()).isEqualByComparingTo("1200000.00");
        assertThat(request.getCapacidadMaxima()).isEqualTo(4);
        assertThat(request.getImagenPrincipalUrl()).contains("penthouse-lujo");
        assertThat(request.getEstado()).isEqualTo(EstadoAlojamiento.ACTIVO);
        assertThat(request.getServiciosIds()).hasSize(6);
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización para suspender alojamiento")
    void scenarioUsoReal_ActualizacionParaSuspenderAlojamiento() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                // CAMBIO AQUÍ:
                .estado(EstadoAlojamiento.INACTIVO)  // Antes: SUSPENDIDO
                .build();

        // ASSERT - Solo se actualiza el estado a inactivo
        assertThat(request.getEstado()).isEqualTo(EstadoAlojamiento.INACTIVO);
        assertThat(request.getTitulo()).isNull();
        assertThat(request.getPrecioNoche()).isNull();
        assertThat(request.getServiciosIds()).isNull();
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Título con longitud mínima")
    void casoBorde_TituloConLongitudMinima() {
        // ARRANGE & ACT
        String tituloMinimo = "Casa bonita"; // 11 caracteres
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo(tituloMinimo)
                .build();

        // ASSERT
        assertThat(request.getTitulo()).hasSize(11);
        assertThat(request.getTitulo()).isEqualTo(tituloMinimo);
    }

    @Test
    @DisplayName("CASO BORDE - Descripción con longitud mínima")
    void casoBorde_DescripcionConLongitudMinima() {
        // ARRANGE & ACT
        String descripcionMinima = "Esta es una descripción mínima con 50 caracteres. ";
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .descripcion(descripcionMinima)
                .build();

        // ASSERT
        assertThat(request.getDescripcion()).hasSize(50);
        assertThat(request.getDescripcion()).isEqualTo(descripcionMinima);
    }

    @Test
    @DisplayName("CASO BORDE - Coordenadas en límites del planeta")
    void casoBorde_CoordenadasEnLimitesDelPlaneta() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .latitud(new BigDecimal("90.0"))   // Polo Norte
                .longitud(new BigDecimal("-180.0")) // Límite oeste
                .build();

        // ASSERT
        assertThat(request.getLatitud()).isEqualByComparingTo("90.0");
        assertThat(request.getLongitud()).isEqualByComparingTo("-180.0");
    }

    @Test
    @DisplayName("CASO BORDE - Capacidad mínima")
    void casoBorde_CapacidadMinima() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .capacidadMaxima(1)
                .build();

        // ASSERT
        assertThat(request.getCapacidadMaxima()).isEqualTo(1);
    }

    @Test
    @DisplayName("CASO BORDE - Precio con valor mínimo")
    void casoBorde_PrecioConValorMinimo() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .precioNoche(new BigDecimal("0.01"))
                .build();

        // ASSERT
        assertThat(request.getPrecioNoche()).isEqualByComparingTo("0.01");
    }

    @Test
    @DisplayName("CASO BORDE - Lista de servicios vacía")
    void casoBorde_ListaDeServiciosVacia() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .serviciosIds(Arrays.asList())
                .build();

        // ASSERT
        assertThat(request.getServiciosIds()).isEmpty();
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE - Crear dos objetos IDÉNTICOS
        ActualizarAlojamientoRequest request1 = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .estado(ESTADO_VALIDO)
                .serviciosIds(SERVICIOS_IDS_VALIDOS)
                .build();

        ActualizarAlojamientoRequest request2 = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .estado(ESTADO_VALIDO)
                .serviciosIds(SERVICIOS_IDS_VALIDOS)
                .build();

        // ASSERT - Verificar que @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.toString()).isNotNull();
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        ActualizarAlojamientoRequest requestVacio = new ActualizarAlojamientoRequest();
        assertThat(requestVacio).isNotNull();

        // Verificar que los getters y setters funcionan
        requestVacio.setTitulo("Test");
        assertThat(requestVacio.getTitulo()).isEqualTo("Test");

        // Verificar que el builder funciona
        assertThat(request1).isInstanceOf(ActualizarAlojamientoRequest.class);
    }

    // ==================== VALIDACIÓN DE ESTRUCTURA TESTS ====================

    @Test
    @DisplayName("ESTRUCTURA - Campos tienen los tipos de datos correctos")
    void estructura_CamposTienenLosTiposDeDatosCorrectos() {
        // ARRANGE & ACT
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .descripcion(DESCRIPCION_VALIDA)
                .ciudad(CIUDAD_VALIDA)
                .direccion(DIRECCION_VALIDA)
                .latitud(LATITUD_VALIDA)
                .longitud(LONGITUD_VALIDA)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .capacidadMaxima(CAPACIDAD_MAXIMA_VALIDA)
                .imagenPrincipalUrl(IMAGEN_PRINCIPAL_URL_VALIDA)
                .estado(ESTADO_VALIDO)
                .serviciosIds(SERVICIOS_IDS_VALIDOS)
                .build();

        // ASSERT
        assertThat(request.getTitulo()).isInstanceOf(String.class);
        assertThat(request.getDescripcion()).isInstanceOf(String.class);
        assertThat(request.getCiudad()).isInstanceOf(String.class);
        assertThat(request.getDireccion()).isInstanceOf(String.class);
        assertThat(request.getLatitud()).isInstanceOf(BigDecimal.class);
        assertThat(request.getLongitud()).isInstanceOf(BigDecimal.class);
        assertThat(request.getPrecioNoche()).isInstanceOf(BigDecimal.class);
        assertThat(request.getCapacidadMaxima()).isInstanceOf(Integer.class);
        assertThat(request.getImagenPrincipalUrl()).isInstanceOf(String.class);
        assertThat(request.getEstado()).isInstanceOf(EstadoAlojamiento.class);
        assertThat(request.getServiciosIds()).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("ESTRUCTURA - Instancia puede ser serializada correctamente")
    void estructura_InstanciaPuedeSerSerializadaCorrectamente() {
        // ARRANGE
        ActualizarAlojamientoRequest request = ActualizarAlojamientoRequest.builder()
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .estado(ESTADO_VALIDO)
                .serviciosIds(SERVICIOS_IDS_VALIDOS)
                .build();

        // ACT - Simular serialización/deserialización
        ActualizarAlojamientoRequest copia = new ActualizarAlojamientoRequest(
                request.getTitulo(),
                request.getDescripcion(),
                request.getCiudad(),
                request.getDireccion(),
                request.getLatitud(),
                request.getLongitud(),
                request.getPrecioNoche(),
                request.getCapacidadMaxima(),
                request.getImagenPrincipalUrl(),
                request.getEstado(),
                request.getServiciosIds()
        );

        // ASSERT
        assertThat(copia).isEqualTo(request);
        assertThat(copia.getTitulo()).isEqualTo(request.getTitulo());
        assertThat(copia.getPrecioNoche()).isEqualTo(request.getPrecioNoche());
        assertThat(copia.getEstado()).isEqualTo(request.getEstado());
        assertThat(copia.getServiciosIds()).isEqualTo(request.getServiciosIds());
    }

    // ==================== BUSINESS LOGIC TESTS ====================

    @Test
    @DisplayName("BUSINESS - Request permite actualización parcial flexible")
    void business_RequestPermiteActualizacionParcialFlexible() {
        // ARRANGE & ACT - Actualizar solo algunos campos
        ActualizarAlojamientoRequest requestParcial = ActualizarAlojamientoRequest.builder()
                .precioNoche(new BigDecimal("275000.00"))
                .imagenPrincipalUrl("https://nueva-imagen.jpg")
                .build();

        // ASSERT - Solo los campos especificados deben tener valor
        assertThat(requestParcial.getPrecioNoche()).isNotNull();
        assertThat(requestParcial.getImagenPrincipalUrl()).isNotNull();
        assertThat(requestParcial.getTitulo()).isNull();
        assertThat(requestParcial.getDescripcion()).isNull();
        assertThat(requestParcial.getEstado()).isNull();
        assertThat(requestParcial.getServiciosIds()).isNull();
    }

    @Test
    @DisplayName("BUSINESS - Diferentes métodos de construcción producen objetos iguales")
    void business_DiferentesMetodosDeConstruccion_ProducenObjetosIguales() {
        // ARRANGE
        ActualizarAlojamientoRequest viaBuilder = ActualizarAlojamientoRequest.builder()
                .titulo("Casa Test")
                .precioNoche(new BigDecimal("200000.00"))
                .capacidadMaxima(4)
                .estado(EstadoAlojamiento.ACTIVO)
                .serviciosIds(Arrays.asList(1, 2))
                .build();

        ActualizarAlojamientoRequest viaConstructor = new ActualizarAlojamientoRequest(
                "Casa Test", null, null, null, null, null,
                new BigDecimal("200000.00"), 4, null, EstadoAlojamiento.ACTIVO, Arrays.asList(1, 2)
        );

        ActualizarAlojamientoRequest viaSetters = new ActualizarAlojamientoRequest();
        viaSetters.setTitulo("Casa Test");
        viaSetters.setPrecioNoche(new BigDecimal("200000.00"));
        viaSetters.setCapacidadMaxima(4);
        viaSetters.setEstado(EstadoAlojamiento.ACTIVO);
        viaSetters.setServiciosIds(Arrays.asList(1, 2));

        // ACT & ASSERT - Todos deben ser iguales
        assertThat(viaBuilder).isEqualTo(viaConstructor);
        assertThat(viaBuilder).isEqualTo(viaSetters);
        assertThat(viaConstructor).isEqualTo(viaSetters);
    }
}