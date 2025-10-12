package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para FavoritoDTO
 */
@DisplayName("FavoritoDTO - Unit Tests")
public class FavoritoDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 102;
    private final Long USUARIO_ID_VALIDO = 15L;
    private final Long ALOJAMIENTO_ID_VALIDO = 8L;
    private final String NOMBRE_ALOJAMIENTO_VALIDO = "Cabaña en el bosque";

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros correctamente")
    void constructorAllArgs_ConTodosLosParametros_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        FavoritoDTO favorito = new FavoritoDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        // ASSERT
        assertThat(favorito).isNotNull();
        assertThat(favorito.getId()).isEqualTo(ID_VALIDO);
        assertThat(favorito.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(favorito.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(favorito.getNombreAlojamiento()).isEqualTo(NOMBRE_ALOJAMIENTO_VALIDO);
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Con parámetros null se maneja correctamente")
    void constructorAllArgs_ConParametrosNull_SeManejaCorrectamente() {
        // ARRANGE & ACT
        FavoritoDTO favorito = new FavoritoDTO(null, null, null, null);

        // ASSERT
        assertThat(favorito).isNotNull();
        assertThat(favorito.getId()).isNull();
        assertThat(favorito.getUsuarioId()).isNull();
        assertThat(favorito.getAlojamientoId()).isNull();
        assertThat(favorito.getNombreAlojamiento()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        FavoritoDTO favorito = new FavoritoDTO();

        // ASSERT
        assertThat(favorito).isNotNull();
        assertThat(favorito.getId()).isNull();
        assertThat(favorito.getUsuarioId()).isNull();
        assertThat(favorito.getAlojamientoId()).isNull();
        assertThat(favorito.getNombreAlojamiento()).isNull();
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO();

        // ACT
        favorito.setId(ID_VALIDO);
        favorito.setUsuarioId(USUARIO_ID_VALIDO);
        favorito.setAlojamientoId(ALOJAMIENTO_ID_VALIDO);
        favorito.setNombreAlojamiento(NOMBRE_ALOJAMIENTO_VALIDO);

        // ASSERT
        assertThat(favorito.getId()).isEqualTo(ID_VALIDO);
        assertThat(favorito.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(favorito.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(favorito.getNombreAlojamiento()).isEqualTo(NOMBRE_ALOJAMIENTO_VALIDO);
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos vacíos se manejan correctamente")
    void gettersYSetters_CamposVacios_SeManejanCorrectamente() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO();

        // ACT
        favorito.setNombreAlojamiento("");

        // ASSERT
        assertThat(favorito.getNombreAlojamiento()).isEmpty();
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos con espacios en blanco se mantienen")
    void gettersYSetters_CamposConEspaciosEnBlanco_SeMantienen() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO();
        String nombreConEspacios = "  Cabaña en el bosque  ";

        // ACT
        favorito.setNombreAlojamiento(nombreConEspacios);

        // ASSERT
        assertThat(favorito.getNombreAlojamiento()).isEqualTo(nombreConEspacios);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en todos los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        FavoritoDTO favorito1 = new FavoritoDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        FavoritoDTO favorito2 = new FavoritoDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        // ACT & ASSERT
        assertThat(favorito1).isEqualTo(favorito2);
        assertThat(favorito1.hashCode()).isEqualTo(favorito2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        FavoritoDTO favorito1 = new FavoritoDTO(
                1, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        FavoritoDTO favorito2 = new FavoritoDTO(
                2, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        // ACT & ASSERT
        assertThat(favorito1).isNotEqualTo(favorito2);
    }

    @Test
    @DisplayName("EQUALS - Misma instancia retorna true")
    void equals_MismaInstancia_RetornaTrue() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        // ACT & ASSERT
        assertThat(favorito).isEqualTo(favorito);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        // ACT & ASSERT
        assertThat(favorito).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        String objetoDiferente = "Soy un String";

        // ACT & ASSERT
        assertThat(favorito).isNotEqualTo(objetoDiferente);
    }

    @Test
    @DisplayName("EQUALS - Diferente usuarioId retorna false")
    void equals_DiferenteUsuarioId_RetornaFalse() {
        // ARRANGE
        FavoritoDTO favorito1 = new FavoritoDTO(
                ID_VALIDO, 1L, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        FavoritoDTO favorito2 = new FavoritoDTO(
                ID_VALIDO, 2L, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        // ACT & ASSERT
        assertThat(favorito1).isNotEqualTo(favorito2);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del favorito")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        // ACT
        String resultado = favorito.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(String.valueOf(USUARIO_ID_VALIDO));
        assertThat(resultado).contains(String.valueOf(ALOJAMIENTO_ID_VALIDO));
        assertThat(resultado).contains(NOMBRE_ALOJAMIENTO_VALIDO);
    }

    @Test
    @DisplayName("TO STRING - Con campos null se maneja correctamente")
    void toString_ConCamposNull_SeManejaCorrectamente() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO(null, null, null, null);

        // ACT
        String resultado = favorito.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotEmpty();
    }

    // ==================== VALIDACIÓN DE DATOS TESTS ====================

    @Test
    @DisplayName("VALIDACIÓN - Nombre de alojamiento con diferentes formatos es aceptado")
    void validacion_NombreAlojamientoConDiferentesFormatos_EsAceptado() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO();

        // ACT & ASSERT
        favorito.setNombreAlojamiento("Cabaña Rústica");
        assertThat(favorito.getNombreAlojamiento()).isEqualTo("Cabaña Rústica");

        favorito.setNombreAlojamiento("Departamento Moderno #123");
        assertThat(favorito.getNombreAlojamiento()).isEqualTo("Departamento Moderno #123");

        favorito.setNombreAlojamiento("Casa con piscina y jardín");
        assertThat(favorito.getNombreAlojamiento()).isEqualTo("Casa con piscina y jardín");
    }

    @Test
    @DisplayName("VALIDACIÓN - IDs con valores límite se manejan correctamente")
    void validacion_IdsConValoresLimite_SeManejanCorrectamente() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO();

        // ACT & ASSERT
        favorito.setId(1);
        favorito.setUsuarioId(1L);
        favorito.setAlojamientoId(1L);

        assertThat(favorito.getId()).isEqualTo(1);
        assertThat(favorito.getUsuarioId()).isEqualTo(1L);
        assertThat(favorito.getAlojamientoId()).isEqualTo(1L);
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Nombre de alojamiento muy largo se maneja correctamente")
    void casoBorde_NombreAlojamientoMuyLargo_SeManejaCorrectamente() {
        // ARRANGE
        String nombreLargo = "A".repeat(500);
        FavoritoDTO favorito = new FavoritoDTO();

        // ACT
        favorito.setNombreAlojamiento(nombreLargo);

        // ASSERT
        assertThat(favorito.getNombreAlojamiento()).isEqualTo(nombreLargo);
        assertThat(favorito.getNombreAlojamiento()).hasSize(500);
    }

    @Test
    @DisplayName("CASO BORDE - Valores máximo de IDs se manejan correctamente")
    void casoBorde_ValoresMaximoDeIds_SeManejanCorrectamente() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO();

        // ACT
        favorito.setId(Integer.MAX_VALUE);
        favorito.setUsuarioId(Long.MAX_VALUE);
        favorito.setAlojamientoId(Long.MAX_VALUE);

        // ASSERT
        assertThat(favorito.getId()).isEqualTo(Integer.MAX_VALUE);
        assertThat(favorito.getUsuarioId()).isEqualTo(Long.MAX_VALUE);
        assertThat(favorito.getAlojamientoId()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("CASO BORDE - Solo ID establecido")
    void casoBorde_SoloIdEstablecido() {
        // ARRANGE & ACT
        FavoritoDTO favorito = new FavoritoDTO(ID_VALIDO, null, null, null);

        // ASSERT
        assertThat(favorito).isNotNull();
        assertThat(favorito.getId()).isEqualTo(ID_VALIDO);
        assertThat(favorito.getUsuarioId()).isNull();
        assertThat(favorito.getAlojamientoId()).isNull();
        assertThat(favorito.getNombreAlojamiento()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Solo relación usuario-alojamiento establecida")
    void casoBorde_SoloRelacionUsuarioAlojamientoEstablecida() {
        // ARRANGE & ACT
        FavoritoDTO favorito = new FavoritoDTO(null, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, null);

        // ASSERT
        assertThat(favorito.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(favorito.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(favorito.getId()).isNull();
        assertThat(favorito.getNombreAlojamiento()).isNull();
    }

    // ==================== RELACIONES TESTS ====================

    @Test
    @DisplayName("RELACIONES - Mismo usuario puede tener múltiples favoritos")
    void relaciones_MismoUsuarioPuedeTenerMultiplesFavoritos() {
        // ARRANGE & ACT
        FavoritoDTO favorito1 = new FavoritoDTO(1, USUARIO_ID_VALIDO, 1L, "Cabaña 1");
        FavoritoDTO favorito2 = new FavoritoDTO(2, USUARIO_ID_VALIDO, 2L, "Cabaña 2");
        FavoritoDTO favorito3 = new FavoritoDTO(3, USUARIO_ID_VALIDO, 3L, "Cabaña 3");

        // ASSERT
        assertThat(favorito1.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(favorito2.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(favorito3.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(favorito1.getAlojamientoId()).isNotEqualTo(favorito2.getAlojamientoId());
        assertThat(favorito2.getAlojamientoId()).isNotEqualTo(favorito3.getAlojamientoId());
    }

    @Test
    @DisplayName("RELACIONES - Mismo alojamiento puede ser favorito de múltiples usuarios")
    void relaciones_MismoAlojamientoPuedeSerFavoritoDeMultiplesUsuarios() {
        // ARRANGE & ACT
        FavoritoDTO favorito1 = new FavoritoDTO(1, 1L, ALOJAMIENTO_ID_VALIDO, "Cabaña Popular");
        FavoritoDTO favorito2 = new FavoritoDTO(2, 2L, ALOJAMIENTO_ID_VALIDO, "Cabaña Popular");
        FavoritoDTO favorito3 = new FavoritoDTO(3, 3L, ALOJAMIENTO_ID_VALIDO, "Cabaña Popular");

        // ASSERT
        assertThat(favorito1.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(favorito2.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(favorito3.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(favorito1.getUsuarioId()).isNotEqualTo(favorito2.getUsuarioId());
        assertThat(favorito2.getUsuarioId()).isNotEqualTo(favorito3.getUsuarioId());
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Usuario marca alojamiento como favorito")
    void scenarioUsoReal_UsuarioMarcaAlojamientoComoFavorito() {
        // ARRANGE & ACT
        FavoritoDTO favorito = new FavoritoDTO(
                null, // ID se genera en BD
                15L,
                8L,
                "Cabaña acogedora en el bosque"
        );

        // ASSERT - Para nuevo favorito, ID es null
        assertThat(favorito.getId()).isNull();
        assertThat(favorito.getUsuarioId()).isEqualTo(15L);
        assertThat(favorito.getAlojamientoId()).isEqualTo(8L);
        assertThat(favorito.getNombreAlojamiento()).contains("Cabaña");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Favorito recuperado de base de datos")
    void scenarioUsoReal_FavoritoRecuperadoDeBaseDeDatos() {
        // ARRANGE & ACT
        FavoritoDTO favorito = new FavoritoDTO(
                102,
                15L,
                8L,
                "Cabaña en el bosque"
        );

        // ASSERT - Favorito existente tiene ID
        assertThat(favorito.getId()).isEqualTo(102);
        assertThat(favorito.getUsuarioId()).isEqualTo(15L);
        assertThat(favorito.getAlojamientoId()).isEqualTo(8L);
        assertThat(favorito.getNombreAlojamiento()).isEqualTo("Cabaña en el bosque");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Lista de favoritos de un usuario")
    void scenarioUsoReal_ListaDeFavoritosDeUnUsuario() {
        // ARRANGE & ACT
        FavoritoDTO favorito1 = new FavoritoDTO(101, 15L, 8L, "Cabaña en el bosque");
        FavoritoDTO favorito2 = new FavoritoDTO(102, 15L, 12L, "Departamento céntrico");
        FavoritoDTO favorito3 = new FavoritoDTO(103, 15L, 25L, "Casa con piscina");

        // ASSERT - Mismo usuario, diferentes alojamientos
        assertThat(favorito1.getUsuarioId()).isEqualTo(15L);
        assertThat(favorito2.getUsuarioId()).isEqualTo(15L);
        assertThat(favorito3.getUsuarioId()).isEqualTo(15L);

        assertThat(favorito1.getAlojamientoId()).isNotEqualTo(favorito2.getAlojamientoId());
        assertThat(favorito2.getAlojamientoId()).isNotEqualTo(favorito3.getAlojamientoId());

        assertThat(favorito1.getNombreAlojamiento()).isNotEqualTo(favorito2.getNombreAlojamiento());
        assertThat(favorito2.getNombreAlojamiento()).isNotEqualTo(favorito3.getNombreAlojamiento());
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE & ACT
        FavoritoDTO favorito1 = new FavoritoDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        FavoritoDTO favorito2 = new FavoritoDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, NOMBRE_ALOJAMIENTO_VALIDO
        );

        // ASSERT - Verificar que @Data, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(favorito1).isEqualTo(favorito2);
        assertThat(favorito1.toString()).isNotNull();
        assertThat(favorito1.hashCode()).isEqualTo(favorito2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        FavoritoDTO favoritoVacio = new FavoritoDTO();
        assertThat(favoritoVacio).isNotNull();
    }

    // ==================== INTEGRIDAD DE DATOS TESTS ====================

    @Test
    @DisplayName("INTEGRIDAD - Los datos se mantienen consistentes después de múltiples operaciones")
    void integridad_DatosSeMantienenConsistentes() {
        // ARRANGE
        FavoritoDTO favorito = new FavoritoDTO();

        // ACT - Múltiples operaciones de setters
        favorito.setId(1);
        favorito.setUsuarioId(10L);
        favorito.setAlojamientoId(20L);
        favorito.setNombreAlojamiento("Nombre Inicial");

        // Cambiar algunos valores
        favorito.setNombreAlojamiento("Nombre Final");
        favorito.setUsuarioId(15L);

        // ASSERT - Verificar que los valores finales son correctos
        assertThat(favorito.getId()).isEqualTo(1);
        assertThat(favorito.getUsuarioId()).isEqualTo(15L);
        assertThat(favorito.getAlojamientoId()).isEqualTo(20L); // No cambió
        assertThat(favorito.getNombreAlojamiento()).isEqualTo("Nombre Final");
    }
}