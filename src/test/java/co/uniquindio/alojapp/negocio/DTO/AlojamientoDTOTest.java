package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para AlojamientoDTO
 */
@DisplayName("AlojamientoDTO - Unit Tests")
public class AlojamientoDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 10;
    private final String TITULO_VALIDO = "Casa de playa en Cartagena";
    private final String DESCRIPCION_VALIDA = "Hermosa casa frente al mar con todas las comodidades";
    private final String CIUDAD_VALIDA = "Cartagena";
    private final String DIRECCION_VALIDA = "Calle 10 #5-20";
    private final BigDecimal LATITUD_VALIDA = new BigDecimal("10.3910485");
    private final BigDecimal LONGITUD_VALIDA = new BigDecimal("-75.4794257");
    private final BigDecimal PRECIO_NOCHE_VALIDO = new BigDecimal("350000.00");
    private final Integer CAPACIDAD_MAXIMA_VALIDA = 8;
    private final String IMAGEN_PRINCIPAL_URL_VALIDA = "https://example.com/imagen.jpg";
    private final String ESTADO_VALIDO = "ACTIVO";
    private final LocalDateTime FECHA_CREACION_VALIDA = LocalDateTime.of(2024, 1, 15, 10, 30);
    private final LocalDateTime FECHA_ACTUALIZACION_VALIDA = LocalDateTime.of(2024, 1, 20, 14, 15);
    private final Long ANFITRION_ID_VALIDO = 5L;
    private final String ANFITRION_NOMBRE_VALIDO = "María López";
    private final Double CALIFICACION_PROMEDIO_VALIDA = 4.7;
    private final Integer CANTIDAD_COMENTARIOS_VALIDA = 23;

    // ==================== BUILDER TESTS ====================

    @Test
    @DisplayName("BUILDER - Crea instancia con todos los campos correctamente")
    void builder_ConTodosLosCampos_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
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
                .fechaCreacion(FECHA_CREACION_VALIDA)
                .fechaActualizacion(FECHA_ACTUALIZACION_VALIDA)
                .anfitrionId(ANFITRION_ID_VALIDO)
                .anfitrionNombre(ANFITRION_NOMBRE_VALIDO)
                .calificacionPromedio(CALIFICACION_PROMEDIO_VALIDA)
                .cantidadComentarios(CANTIDAD_COMENTARIOS_VALIDA)
                .build();

        // ASSERT
        assertThat(alojamiento).isNotNull();
        assertThat(alojamiento.getId()).isEqualTo(ID_VALIDO);
        assertThat(alojamiento.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(alojamiento.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(alojamiento.getCiudad()).isEqualTo(CIUDAD_VALIDA);
        assertThat(alojamiento.getDireccion()).isEqualTo(DIRECCION_VALIDA);
        assertThat(alojamiento.getLatitud()).isEqualTo(LATITUD_VALIDA);
        assertThat(alojamiento.getLongitud()).isEqualTo(LONGITUD_VALIDA);
        assertThat(alojamiento.getPrecioNoche()).isEqualTo(PRECIO_NOCHE_VALIDO);
        assertThat(alojamiento.getCapacidadMaxima()).isEqualTo(CAPACIDAD_MAXIMA_VALIDA);
        assertThat(alojamiento.getImagenPrincipalUrl()).isEqualTo(IMAGEN_PRINCIPAL_URL_VALIDA);
        assertThat(alojamiento.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(alojamiento.getFechaCreacion()).isEqualTo(FECHA_CREACION_VALIDA);
        assertThat(alojamiento.getFechaActualizacion()).isEqualTo(FECHA_ACTUALIZACION_VALIDA);
        assertThat(alojamiento.getAnfitrionId()).isEqualTo(ANFITRION_ID_VALIDO);
        assertThat(alojamiento.getAnfitrionNombre()).isEqualTo(ANFITRION_NOMBRE_VALIDO);
        assertThat(alojamiento.getCalificacionPromedio()).isEqualTo(CALIFICACION_PROMEDIO_VALIDA);
        assertThat(alojamiento.getCantidadComentarios()).isEqualTo(CANTIDAD_COMENTARIOS_VALIDA);
    }

    @Test
    @DisplayName("BUILDER - Campos opcionales null se manejan correctamente")
    void builder_CamposOpcionalesNull_SeManejanCorrectamente() {
        // ARRANGE & ACT
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .ciudad(CIUDAD_VALIDA)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .capacidadMaxima(CAPACIDAD_MAXIMA_VALIDA)
                .estado(ESTADO_VALIDO)
                .build();

        // ASSERT
        assertThat(alojamiento).isNotNull();
        assertThat(alojamiento.getDescripcion()).isNull();
        assertThat(alojamiento.getDireccion()).isNull();
        assertThat(alojamiento.getLatitud()).isNull();
        assertThat(alojamiento.getLongitud()).isNull();
        assertThat(alojamiento.getImagenPrincipalUrl()).isNull();
        assertThat(alojamiento.getFechaCreacion()).isNull();
        assertThat(alojamiento.getFechaActualizacion()).isNull();
        assertThat(alojamiento.getAnfitrionId()).isNull();
        assertThat(alojamiento.getAnfitrionNombre()).isNull();
        assertThat(alojamiento.getCalificacionPromedio()).isNull();
        assertThat(alojamiento.getCantidadComentarios()).isNull();
        assertThat(alojamiento.getImagenes()).isNull();
        assertThat(alojamiento.getServicios()).isNull();
    }

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        AlojamientoDTO alojamiento = new AlojamientoDTO();

        // ASSERT
        assertThat(alojamiento).isNotNull();
        assertThat(alojamiento.getId()).isNull();
        assertThat(alojamiento.getTitulo()).isNull();
        assertThat(alojamiento.getPrecioNoche()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE
        List<String> imagenes = Arrays.asList("img1.jpg", "img2.jpg", "img3.jpg");
        List<ServicioAlojamientoDTO> servicios = Arrays.asList(
                new ServicioAlojamientoDTO(1, "Wi-Fi", "Conexión inalámbrica"),
                new ServicioAlojamientoDTO(2, "Piscina", "Piscina privada")
        );

        // ACT
        AlojamientoDTO alojamiento = new AlojamientoDTO(
                ID_VALIDO, TITULO_VALIDO, DESCRIPCION_VALIDA, CIUDAD_VALIDA, DIRECCION_VALIDA,
                LATITUD_VALIDA, LONGITUD_VALIDA, PRECIO_NOCHE_VALIDO, CAPACIDAD_MAXIMA_VALIDA,
                IMAGEN_PRINCIPAL_URL_VALIDA, ESTADO_VALIDO, FECHA_CREACION_VALIDA,
                FECHA_ACTUALIZACION_VALIDA, ANFITRION_ID_VALIDO, ANFITRION_NOMBRE_VALIDO,
                imagenes, servicios, CALIFICACION_PROMEDIO_VALIDA, CANTIDAD_COMENTARIOS_VALIDA
        );

        // ASSERT
        assertThat(alojamiento).isNotNull();
        assertThat(alojamiento.getId()).isEqualTo(ID_VALIDO);
        assertThat(alojamiento.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(alojamiento.getImagenes()).hasSize(3);
        assertThat(alojamiento.getServicios()).hasSize(2);
        assertThat(alojamiento.getCalificacionPromedio()).isEqualTo(CALIFICACION_PROMEDIO_VALIDA);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        AlojamientoDTO alojamiento = new AlojamientoDTO();
        List<String> imagenes = Arrays.asList("foto1.jpg", "foto2.jpg");
        List<ServicioAlojamientoDTO> servicios = Arrays.asList(
                new ServicioAlojamientoDTO(1, "Parking", "Estacionamiento privado")
        );

        // ACT
        alojamiento.setId(ID_VALIDO);
        alojamiento.setTitulo(TITULO_VALIDO);
        alojamiento.setDescripcion(DESCRIPCION_VALIDA);
        alojamiento.setCiudad(CIUDAD_VALIDA);
        alojamiento.setDireccion(DIRECCION_VALIDA);
        alojamiento.setLatitud(LATITUD_VALIDA);
        alojamiento.setLongitud(LONGITUD_VALIDA);
        alojamiento.setPrecioNoche(PRECIO_NOCHE_VALIDO);
        alojamiento.setCapacidadMaxima(CAPACIDAD_MAXIMA_VALIDA);
        alojamiento.setImagenPrincipalUrl(IMAGEN_PRINCIPAL_URL_VALIDA);
        alojamiento.setEstado(ESTADO_VALIDO);
        alojamiento.setFechaCreacion(FECHA_CREACION_VALIDA);
        alojamiento.setFechaActualizacion(FECHA_ACTUALIZACION_VALIDA);
        alojamiento.setAnfitrionId(ANFITRION_ID_VALIDO);
        alojamiento.setAnfitrionNombre(ANFITRION_NOMBRE_VALIDO);
        alojamiento.setImagenes(imagenes);
        alojamiento.setServicios(servicios);
        alojamiento.setCalificacionPromedio(CALIFICACION_PROMEDIO_VALIDA);
        alojamiento.setCantidadComentarios(CANTIDAD_COMENTARIOS_VALIDA);

        // ASSERT
        assertThat(alojamiento.getId()).isEqualTo(ID_VALIDO);
        assertThat(alojamiento.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(alojamiento.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(alojamiento.getCiudad()).isEqualTo(CIUDAD_VALIDA);
        assertThat(alojamiento.getDireccion()).isEqualTo(DIRECCION_VALIDA);
        assertThat(alojamiento.getLatitud()).isEqualTo(LATITUD_VALIDA);
        assertThat(alojamiento.getLongitud()).isEqualTo(LONGITUD_VALIDA);
        assertThat(alojamiento.getPrecioNoche()).isEqualTo(PRECIO_NOCHE_VALIDO);
        assertThat(alojamiento.getCapacidadMaxima()).isEqualTo(CAPACIDAD_MAXIMA_VALIDA);
        assertThat(alojamiento.getImagenPrincipalUrl()).isEqualTo(IMAGEN_PRINCIPAL_URL_VALIDA);
        assertThat(alojamiento.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(alojamiento.getFechaCreacion()).isEqualTo(FECHA_CREACION_VALIDA);
        assertThat(alojamiento.getFechaActualizacion()).isEqualTo(FECHA_ACTUALIZACION_VALIDA);
        assertThat(alojamiento.getAnfitrionId()).isEqualTo(ANFITRION_ID_VALIDO);
        assertThat(alojamiento.getAnfitrionNombre()).isEqualTo(ANFITRION_NOMBRE_VALIDO);
        assertThat(alojamiento.getImagenes()).isEqualTo(imagenes);
        assertThat(alojamiento.getServicios()).isEqualTo(servicios);
        assertThat(alojamiento.getCalificacionPromedio()).isEqualTo(CALIFICACION_PROMEDIO_VALIDA);
        assertThat(alojamiento.getCantidadComentarios()).isEqualTo(CANTIDAD_COMENTARIOS_VALIDA);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en todos los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        AlojamientoDTO alojamiento1 = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .build();

        AlojamientoDTO alojamiento2 = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(alojamiento1).isEqualTo(alojamiento2);
        assertThat(alojamiento1.hashCode()).isEqualTo(alojamiento2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        AlojamientoDTO alojamiento1 = AlojamientoDTO.builder()
                .id(1)
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .build();

        AlojamientoDTO alojamiento2 = AlojamientoDTO.builder()
                .id(2)
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(alojamiento1).isNotEqualTo(alojamiento2);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(alojamiento).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .build();

        String objetoDiferente = "Soy un String";

        // ACT & ASSERT
        assertThat(alojamiento).isNotEqualTo(objetoDiferente);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del alojamiento")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .ciudad(CIUDAD_VALIDA)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .calificacionPromedio(CALIFICACION_PROMEDIO_VALIDA)
                .build();

        // ACT
        String resultado = alojamiento.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(TITULO_VALIDO);
        assertThat(resultado).contains(CIUDAD_VALIDA);
        assertThat(resultado).contains(PRECIO_NOCHE_VALIDO.toString());
    }

    // ==================== COLECCIONES TESTS ====================

    @Test
    @DisplayName("COLECCIONES - Lista de imágenes se maneja correctamente")
    void colecciones_ListaImagenes_SeManejaCorrectamente() {
        // ARRANGE
        List<String> imagenes = Arrays.asList(
                "https://example.com/img1.jpg",
                "https://example.com/img2.jpg",
                "https://example.com/img3.jpg"
        );

        // ACT
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .imagenes(imagenes)
                .build();

        // ASSERT
        assertThat(alojamiento.getImagenes()).isNotNull();
        assertThat(alojamiento.getImagenes()).hasSize(3);
        assertThat(alojamiento.getImagenes()).containsExactly(
                "https://example.com/img1.jpg",
                "https://example.com/img2.jpg",
                "https://example.com/img3.jpg"
        );
    }

    @Test
    @DisplayName("COLECCIONES - Lista de servicios se maneja correctamente")
    void colecciones_ListaServicios_SeManejaCorrectamente() {
        // ARRANGE
        List<ServicioAlojamientoDTO> servicios = Arrays.asList(
                new ServicioAlojamientoDTO(1, "Wi-Fi", "Internet gratuito"),
                new ServicioAlojamientoDTO(2, "Aire acondicionado", "Climatización"),
                new ServicioAlojamientoDTO(3, "TV", "Televisor por cable")
        );

        // ACT
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .servicios(servicios)
                .build();

        // ASSERT
        assertThat(alojamiento.getServicios()).isNotNull();
        assertThat(alojamiento.getServicios()).hasSize(3);
        assertThat(alojamiento.getServicios().get(0).getNombre()).isEqualTo("Wi-Fi");
        assertThat(alojamiento.getServicios().get(1).getNombre()).isEqualTo("Aire acondicionado");
        assertThat(alojamiento.getServicios().get(2).getNombre()).isEqualTo("TV");
    }

    @Test
    @DisplayName("COLECCIONES - Listas vacías se manejan correctamente")
    void colecciones_ListasVacias_SeManejanCorrectamente() {
        // ARRANGE
        List<String> imagenesVacias = Arrays.asList();
        List<ServicioAlojamientoDTO> serviciosVacios = Arrays.asList();

        // ACT
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .imagenes(imagenesVacias)
                .servicios(serviciosVacios)
                .build();

        // ASSERT
        assertThat(alojamiento.getImagenes()).isEmpty();
        assertThat(alojamiento.getServicios()).isEmpty();
    }

    // ==================== VALIDACIÓN DE DATOS TESTS ====================

    @Test
    @DisplayName("VALIDACIÓN - Coordenadas geográficas válidas se aceptan")
    void validacion_CoordenadasGeograficasValidas_SeAceptan() {
        // ARRANGE
        BigDecimal latitudNegativa = new BigDecimal("-33.459229");
        BigDecimal longitudPositiva = new BigDecimal("-70.645348");

        // ACT
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .latitud(latitudNegativa)
                .longitud(longitudPositiva)
                .build();

        // ASSERT
        assertThat(alojamiento.getLatitud()).isEqualTo(latitudNegativa);
        assertThat(alojamiento.getLongitud()).isEqualTo(longitudPositiva);
    }

    @Test
    @DisplayName("VALIDACIÓN - Precios con diferentes escalas se manejan correctamente")
    void validacion_PreciosConDiferentesEscalas_SeManejanCorrectamente() {
        // ARRANGE
        BigDecimal precioEntero = new BigDecimal("250000");
        BigDecimal precioDecimal = new BigDecimal("275000.50");
        BigDecimal precioAlto = new BigDecimal("1000000.00");

        // ACT & ASSERT
        AlojamientoDTO alojamiento1 = AlojamientoDTO.builder()
                .id(1)
                .titulo("Alojamiento 1")
                .precioNoche(precioEntero)
                .build();

        AlojamientoDTO alojamiento2 = AlojamientoDTO.builder()
                .id(2)
                .titulo("Alojamiento 2")
                .precioNoche(precioDecimal)
                .build();

        AlojamientoDTO alojamiento3 = AlojamientoDTO.builder()
                .id(3)
                .titulo("Alojamiento 3")
                .precioNoche(precioAlto)
                .build();

        assertThat(alojamiento1.getPrecioNoche()).isEqualTo(precioEntero);
        assertThat(alojamiento2.getPrecioNoche()).isEqualTo(precioDecimal);
        assertThat(alojamiento3.getPrecioNoche()).isEqualTo(precioAlto);
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Título muy largo se maneja correctamente")
    void casoBorde_TituloMuyLargo_SeManejaCorrectamente() {
        // ARRANGE
        String tituloLargo = "A".repeat(500);

        // ACT
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(tituloLargo)
                .build();

        // ASSERT
        assertThat(alojamiento.getTitulo()).isEqualTo(tituloLargo);
        assertThat(alojamiento.getTitulo()).hasSize(500);
    }

    @Test
    @DisplayName("CASO BORDE - Capacidad máxima cero se maneja correctamente")
    void casoBorde_CapacidadMaximaCero_SeManejaCorrectamente() {
        // ARRANGE & ACT
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .capacidadMaxima(0)
                .build();

        // ASSERT
        assertThat(alojamiento.getCapacidadMaxima()).isZero();
    }

    @Test
    @DisplayName("CASO BORDE - Calificación promedio en límites se maneja correctamente")
    void casoBorde_CalificacionPromedioEnLimites_SeManejaCorrectamente() {
        // ARRANGE & ACT
        AlojamientoDTO alojamientoMin = AlojamientoDTO.builder()
                .id(1)
                .titulo("Alojamiento Min")
                .calificacionPromedio(0.0)
                .build();

        AlojamientoDTO alojamientoMax = AlojamientoDTO.builder()
                .id(2)
                .titulo("Alojamiento Max")
                .calificacionPromedio(5.0)
                .build();

        AlojamientoDTO alojamientoMedio = AlojamientoDTO.builder()
                .id(3)
                .titulo("Alojamiento Medio")
                .calificacionPromedio(2.5)
                .build();

        // ASSERT
        assertThat(alojamientoMin.getCalificacionPromedio()).isEqualTo(0.0);
        assertThat(alojamientoMax.getCalificacionPromedio()).isEqualTo(5.0);
        assertThat(alojamientoMedio.getCalificacionPromedio()).isEqualTo(2.5);
    }

    @Test
    @DisplayName("CASO BORDE - Fechas extremas se manejan correctamente")
    void casoBorde_FechasExtremas_SeManejanCorrectamente() {
        // ARRANGE
        LocalDateTime fechaMinima = LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime fechaMaxima = LocalDateTime.of(2030, 12, 31, 23, 59);

        // ACT
        AlojamientoDTO alojamiento = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .fechaCreacion(fechaMinima)
                .fechaActualizacion(fechaMaxima)
                .build();

        // ASSERT
        assertThat(alojamiento.getFechaCreacion()).isEqualTo(fechaMinima);
        assertThat(alojamiento.getFechaActualizacion()).isEqualTo(fechaMaxima);
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE & ACT
        AlojamientoDTO alojamiento1 = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .build();

        AlojamientoDTO alojamiento2 = AlojamientoDTO.builder()
                .id(ID_VALIDO)
                .titulo(TITULO_VALIDO)
                .precioNoche(PRECIO_NOCHE_VALIDO)
                .build();

        // ASSERT - Verificar que @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(alojamiento1).isEqualTo(alojamiento2);
        assertThat(alojamiento1.toString()).isNotNull();
        assertThat(alojamiento1.hashCode()).isEqualTo(alojamiento2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        AlojamientoDTO alojamientoVacio = new AlojamientoDTO();
        assertThat(alojamientoVacio).isNotNull();

        // Verificar que el builder funciona
        assertThat(alojamiento1).isInstanceOf(AlojamientoDTO.class);
    }
}