package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ImagenDTO
 */
@DisplayName("ImagenDTO - Unit Tests")
public class ImagenDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 501;
    private final String URL_VALIDA = "https://alojapp.com/images/alojamientos/501.jpg";
    private final String DESCRIPCION_VALIDA = "Vista frontal de la casa";
    private final Long ALOJAMIENTO_ID_VALIDO = 15L;

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros correctamente")
    void constructorAllArgs_ConTodosLosParametros_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(
                ID_VALIDO, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        // ASSERT
        assertThat(imagen).isNotNull();
        assertThat(imagen.getId()).isEqualTo(ID_VALIDO);
        assertThat(imagen.getUrl()).isEqualTo(URL_VALIDA);
        assertThat(imagen.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(imagen.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Con parámetros null se maneja correctamente")
    void constructorAllArgs_ConParametrosNull_SeManejaCorrectamente() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(null, null, null, null);

        // ASSERT
        assertThat(imagen).isNotNull();
        assertThat(imagen.getId()).isNull();
        assertThat(imagen.getUrl()).isNull();
        assertThat(imagen.getDescripcion()).isNull();
        assertThat(imagen.getAlojamientoId()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        ImagenDTO imagen = new ImagenDTO();

        // ASSERT
        assertThat(imagen).isNotNull();
        assertThat(imagen.getId()).isNull();
        assertThat(imagen.getUrl()).isNull();
        assertThat(imagen.getDescripcion()).isNull();
        assertThat(imagen.getAlojamientoId()).isNull();
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO();

        // ACT
        imagen.setId(ID_VALIDO);
        imagen.setUrl(URL_VALIDA);
        imagen.setDescripcion(DESCRIPCION_VALIDA);
        imagen.setAlojamientoId(ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(imagen.getId()).isEqualTo(ID_VALIDO);
        assertThat(imagen.getUrl()).isEqualTo(URL_VALIDA);
        assertThat(imagen.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(imagen.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos vacíos se manejan correctamente")
    void gettersYSetters_CamposVacios_SeManejanCorrectamente() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO();

        // ACT
        imagen.setUrl("");
        imagen.setDescripcion("");

        // ASSERT
        assertThat(imagen.getUrl()).isEmpty();
        assertThat(imagen.getDescripcion()).isEmpty();
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos con espacios en blanco se mantienen")
    void gettersYSetters_CamposConEspaciosEnBlanco_SeMantienen() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO();
        String urlConEspacios = "  https://example.com/image.jpg  ";
        String descripcionConEspacios = "  Vista frontal de la casa  ";

        // ACT
        imagen.setUrl(urlConEspacios);
        imagen.setDescripcion(descripcionConEspacios);

        // ASSERT
        assertThat(imagen.getUrl()).isEqualTo(urlConEspacios);
        assertThat(imagen.getDescripcion()).isEqualTo(descripcionConEspacios);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en todos los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        ImagenDTO imagen1 = new ImagenDTO(
                ID_VALIDO, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        ImagenDTO imagen2 = new ImagenDTO(
                ID_VALIDO, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        // ACT & ASSERT
        assertThat(imagen1).isEqualTo(imagen2);
        assertThat(imagen1.hashCode()).isEqualTo(imagen2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        ImagenDTO imagen1 = new ImagenDTO(
                1, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        ImagenDTO imagen2 = new ImagenDTO(
                2, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        // ACT & ASSERT
        assertThat(imagen1).isNotEqualTo(imagen2);
    }

    @Test
    @DisplayName("EQUALS - Misma instancia retorna true")
    void equals_MismaInstancia_RetornaTrue() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO(
                ID_VALIDO, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        // ACT & ASSERT
        assertThat(imagen).isEqualTo(imagen);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO(
                ID_VALIDO, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        // ACT & ASSERT
        assertThat(imagen).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO(
                ID_VALIDO, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        String objetoDiferente = "Soy un String";

        // ACT & ASSERT
        assertThat(imagen).isNotEqualTo(objetoDiferente);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante de la imagen")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO(
                ID_VALIDO, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        // ACT
        String resultado = imagen.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(URL_VALIDA);
        assertThat(resultado).contains(DESCRIPCION_VALIDA);
        assertThat(resultado).contains(String.valueOf(ALOJAMIENTO_ID_VALIDO));
    }

    @Test
    @DisplayName("TO STRING - Con campos null se maneja correctamente")
    void toString_ConCamposNull_SeManejaCorrectamente() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO(null, null, null, null);

        // ACT
        String resultado = imagen.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotEmpty();
    }

    // ==================== URL TESTS ====================

    @Test
    @DisplayName("URL - Diferentes formatos de URL son aceptados")
    void url_DiferentesFormatosDeUrl_SonAceptados() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO();

        // ACT & ASSERT
        imagen.setUrl("https://example.com/image.jpg");
        assertThat(imagen.getUrl()).isEqualTo("https://example.com/image.jpg");

        imagen.setUrl("http://example.com/image.png");
        assertThat(imagen.getUrl()).isEqualTo("http://example.com/image.png");

        imagen.setUrl("/images/local-file.jpg");
        assertThat(imagen.getUrl()).isEqualTo("/images/local-file.jpg");

        imagen.setUrl("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAA...");
        assertThat(imagen.getUrl()).contains("data:image/jpeg");

        imagen.setUrl("https://cdn.alojapp.com/images/alojamientos/123/principal.webp");
        assertThat(imagen.getUrl()).contains(".webp");
    }

    @Test
    @DisplayName("URL - URL muy larga se maneja correctamente")
    void url_UrlMuyLarga_SeManejaCorrectamente() {
        // ARRANGE
        String urlLarga = "https://example.com/" + "a".repeat(1000) + ".jpg";
        ImagenDTO imagen = new ImagenDTO();

        // ACT
        imagen.setUrl(urlLarga);

        // ASSERT
        assertThat(imagen.getUrl()).isEqualTo(urlLarga);
        assertThat(imagen.getUrl()).hasSizeGreaterThan(1000);
    }

    @Test
    @DisplayName("URL - URL con parámetros de consulta se maneja correctamente")
    void url_UrlConParametrosDeConsulta_SeManejaCorrectamente() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO();
        String urlConParametros = "https://example.com/image.jpg?width=800&height=600&quality=85";

        // ACT
        imagen.setUrl(urlConParametros);

        // ASSERT
        assertThat(imagen.getUrl()).isEqualTo(urlConParametros);
        assertThat(imagen.getUrl()).contains("width=800");
        assertThat(imagen.getUrl()).contains("height=600");
    }

    // ==================== DESCRIPCIÓN TESTS ====================

    @Test
    @DisplayName("DESCRIPCIÓN - Texto descriptivo con diferentes longitudes se maneja correctamente")
    void descripcion_TextoDescriptivoConDiferentesLongitudes_SeManejaCorrectamente() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO();

        // ACT & ASSERT
        imagen.setDescripcion("Vista frontal");
        assertThat(imagen.getDescripcion()).isEqualTo("Vista frontal");

        imagen.setDescripcion("Hermosa vista del atardecer desde la terraza principal con la montaña de fondo");
        assertThat(imagen.getDescripcion()).contains("atardecer");
        assertThat(imagen.getDescripcion()).contains("terraza");

        imagen.setDescripcion("Foto de la piscina, jardín y área de BBQ durante el verano");
        assertThat(imagen.getDescripcion()).contains("piscina");
        assertThat(imagen.getDescripcion()).contains("BBQ");
    }

    @Test
    @DisplayName("DESCRIPCIÓN - Descripción muy larga se maneja correctamente")
    void descripcion_DescripcionMuyLarga_SeManejaCorrectamente() {
        // ARRANGE
        String descripcionLarga = "Esta imagen muestra ".repeat(100) + "el alojamiento.";
        ImagenDTO imagen = new ImagenDTO();

        // ACT
        imagen.setDescripcion(descripcionLarga);

        // ASSERT
        assertThat(imagen.getDescripcion()).isEqualTo(descripcionLarga);
        assertThat(imagen.getDescripcion()).hasSizeGreaterThan(1000);
    }

    @Test
    @DisplayName("DESCRIPCIÓN - Descripción con caracteres especiales se maneja correctamente")
    void descripcion_DescripcionConCaracteresEspeciales_SeManejaCorrectamente() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO();
        String descripcionEspecial = "Vista de la piscina 🏊‍♂️ y jardín 🌸 - ¡Hermoso! ¿No crees?";

        // ACT
        imagen.setDescripcion(descripcionEspecial);

        // ASSERT
        assertThat(imagen.getDescripcion()).isEqualTo(descripcionEspecial);
        assertThat(imagen.getDescripcion()).contains("🏊‍♂️");
        assertThat(imagen.getDescripcion()).contains("🌸");
    }

    // ==================== RELACIONES TESTS ====================

    @Test
    @DisplayName("RELACIONES - IDs con valores límite se manejan correctamente")
    void relaciones_IdsConValoresLimite_SeManejanCorrectamente() {
        // ARRANGE
        ImagenDTO imagen = new ImagenDTO();

        // ACT & ASSERT
        imagen.setId(1);
        imagen.setAlojamientoId(1L);

        assertThat(imagen.getId()).isEqualTo(1);
        assertThat(imagen.getAlojamientoId()).isEqualTo(1L);

        imagen.setId(Integer.MAX_VALUE);
        imagen.setAlojamientoId(Long.MAX_VALUE);

        assertThat(imagen.getId()).isEqualTo(Integer.MAX_VALUE);
        assertThat(imagen.getAlojamientoId()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("RELACIONES - Múltiples imágenes para un mismo alojamiento")
    void relaciones_MultiplesImagenesParaUnMismoAlojamiento() {
        // ARRANGE & ACT
        ImagenDTO imagen1 = new ImagenDTO(1, "https://example.com/img1.jpg", "Vista frontal", 15L);
        ImagenDTO imagen2 = new ImagenDTO(2, "https://example.com/img2.jpg", "Vista interior", 15L);
        ImagenDTO imagen3 = new ImagenDTO(3, "https://example.com/img3.jpg", "Vista del jardín", 15L);

        // ASSERT
        assertThat(imagen1.getAlojamientoId()).isEqualTo(15L);
        assertThat(imagen2.getAlojamientoId()).isEqualTo(15L);
        assertThat(imagen3.getAlojamientoId()).isEqualTo(15L);

        assertThat(imagen1.getId()).isNotEqualTo(imagen2.getId());
        assertThat(imagen2.getId()).isNotEqualTo(imagen3.getId());

        assertThat(imagen1.getUrl()).isNotEqualTo(imagen2.getUrl());
        assertThat(imagen2.getUrl()).isNotEqualTo(imagen3.getUrl());
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Solo ID establecido")
    void casoBorde_SoloIdEstablecido() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(ID_VALIDO, null, null, null);

        // ASSERT
        assertThat(imagen).isNotNull();
        assertThat(imagen.getId()).isEqualTo(ID_VALIDO);
        assertThat(imagen.getUrl()).isNull();
        assertThat(imagen.getDescripcion()).isNull();
        assertThat(imagen.getAlojamientoId()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Solo URL establecida")
    void casoBorde_SoloUrlEstablecida() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(null, URL_VALIDA, null, null);

        // ASSERT
        assertThat(imagen.getUrl()).isEqualTo(URL_VALIDA);
        assertThat(imagen.getId()).isNull();
        assertThat(imagen.getDescripcion()).isNull();
        assertThat(imagen.getAlojamientoId()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Solo alojamientoId establecido")
    void casoBorde_SoloAlojamientoIdEstablecido() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(null, null, null, ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(imagen.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(imagen.getId()).isNull();
        assertThat(imagen.getUrl()).isNull();
        assertThat(imagen.getDescripcion()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Imagen sin descripción pero con URL")
    void casoBorde_ImagenSinDescripcionPeroConUrl() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(ID_VALIDO, URL_VALIDA, null, ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(imagen.getUrl()).isEqualTo(URL_VALIDA);
        assertThat(imagen.getDescripcion()).isNull();
        assertThat(imagen.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Imagen principal de un alojamiento")
    void scenarioUsoReal_ImagenPrincipalDeUnAlojamiento() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(
                501,
                "https://alojapp.com/images/alojamientos/15/principal.jpg",
                "Vista frontal de la cabaña con el jardín y entrada principal",
                15L
        );

        // ASSERT
        assertThat(imagen.getUrl()).contains("principal.jpg");
        assertThat(imagen.getDescripcion()).contains("Vista frontal");
        assertThat(imagen.getAlojamientoId()).isEqualTo(15L);
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Imagen de galería con descripción detallada")
    void scenarioUsoReal_ImagenDeGaleriaConDescripcionDetallada() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(
                502,
                "https://alojapp.com/images/alojamientos/15/sala-estar.jpg",
                "Sala de estar moderna con sofá de cuero, chimenea y televisor de 55 pulgadas",
                15L
        );

        // ASSERT
        assertThat(imagen.getUrl()).contains("sala-estar.jpg");
        assertThat(imagen.getDescripcion()).contains("Sala de estar");
        assertThat(imagen.getDescripcion()).contains("chimenea");
        assertThat(imagen.getDescripcion()).contains("televisor");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Imagen de ambiente exterior")
    void scenarioUsoReal_ImagenDeAmbienteExterior() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(
                503,
                "https://alojapp.com/images/alojamientos/15/piscina-noche.jpg",
                "Piscina iluminada por la noche con luces LED azules y área de descanso",
                15L
        );

        // ASSERT
        assertThat(imagen.getUrl()).contains("piscina-noche.jpg");
        assertThat(imagen.getDescripcion()).contains("Piscina iluminada");
        assertThat(imagen.getDescripcion()).contains("luces LED");
        assertThat(imagen.getDescripcion()).contains("noche");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Nueva imagen subida por el anfitrión")
    void scenarioUsoReal_NuevaImagenSubidaPorElAnfitrion() {
        // ARRANGE & ACT
        ImagenDTO nuevaImagen = new ImagenDTO(
                null, // ID se genera en BD
                "https://alojapp.com/uploads/temp/image-12345.jpg",
                "Nueva foto del jardín remodelado",
                15L
        );

        // ASSERT - Para nueva imagen, ID es null
        assertThat(nuevaImagen.getId()).isNull();
        assertThat(nuevaImagen.getUrl()).contains("temp/image-12345.jpg");
        assertThat(nuevaImagen.getDescripcion()).contains("jardín remodelado");
        assertThat(nuevaImagen.getAlojamientoId()).isEqualTo(15L);
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE & ACT
        ImagenDTO imagen1 = new ImagenDTO(
                ID_VALIDO, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        ImagenDTO imagen2 = new ImagenDTO(
                ID_VALIDO, URL_VALIDA, DESCRIPCION_VALIDA, ALOJAMIENTO_ID_VALIDO
        );

        // ASSERT - Verificar que @Data, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(imagen1).isEqualTo(imagen2);
        assertThat(imagen1.toString()).isNotNull();
        assertThat(imagen1.hashCode()).isEqualTo(imagen2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        ImagenDTO imagenVacia = new ImagenDTO();
        assertThat(imagenVacia).isNotNull();
    }

    // ==================== ACCESIBILIDAD TESTS ====================

    @Test
    @DisplayName("ACCESIBILIDAD - Descripción como texto alternativo para accesibilidad")
    void accesibilidad_DescripcionComoTextoAlternativoParaAccesibilidad() {
        // ARRANGE & ACT
        ImagenDTO imagen = new ImagenDTO(
                501,
                "https://alojapp.com/images/cocina-moderna.jpg",
                "Cocina moderna con isla central, electrodomésticos de acero inoxidable y gabinetes blancos",
                15L
        );

        // ASSERT - La descripción funciona como alt text para accesibilidad
        assertThat(imagen.getDescripcion()).isNotNull();
        assertThat(imagen.getDescripcion()).isNotEmpty();
        assertThat(imagen.getDescripcion()).contains("Cocina moderna");
    }
}