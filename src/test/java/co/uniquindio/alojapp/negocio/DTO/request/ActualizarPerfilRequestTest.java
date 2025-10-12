package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ActualizarPerfilRequest
 *
 * OBJETIVO: Probar el DTO de actualización de perfil de usuario
 * - Validar constructor, getters/setters
 * - Verificar equals/hashCode/toString
 * - Probar diferentes escenarios de actualización
 */
@DisplayName("ActualizarPerfilRequest - Unit Tests")
public class ActualizarPerfilRequestTest {

    // DATOS DE PRUEBA
    private final String NOMBRE_VALIDO = "Juan Carlos Pérez";
    private final String TELEFONO_VALIDO = "+57 3001234567";
    private final LocalDate FECHA_NACIMIENTO_VALIDA = LocalDate.of(1995, 8, 15);
    private final String FOTO_PERFIL_URL_VALIDA = "https://ejemplo.com/nueva-foto.jpg";


    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getNombre()).isNull();
        assertThat(request.getTelefono()).isNull();
        assertThat(request.getFechaNacimiento()).isNull();
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(request.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(request.getFechaNacimiento()).isEqualTo(FECHA_NACIMIENTO_VALIDA);
        assertThat(request.getFotoPerfilUrl()).isEqualTo(FOTO_PERFIL_URL_VALIDA);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();

        // ACT
        request.setNombre(NOMBRE_VALIDO);
        request.setTelefono(TELEFONO_VALIDO);
        request.setFechaNacimiento(FECHA_NACIMIENTO_VALIDA);
        request.setFotoPerfilUrl(FOTO_PERFIL_URL_VALIDA);

        // ASSERT
        assertThat(request.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(request.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(request.getFechaNacimiento()).isEqualTo(FECHA_NACIMIENTO_VALIDA);
        assertThat(request.getFotoPerfilUrl()).isEqualTo(FOTO_PERFIL_URL_VALIDA);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en TODOS los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        ActualizarPerfilRequest request1 = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        ActualizarPerfilRequest request2 = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ACT & ASSERT
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Ambos objetos con todos campos null son iguales")
    void equals_AmbosObjetosConTodosCamposNull_SonIguales() {
        // ARRANGE
        ActualizarPerfilRequest request1 = new ActualizarPerfilRequest();
        ActualizarPerfilRequest request2 = new ActualizarPerfilRequest();

        // ACT & ASSERT
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente nombre retorna false")
    void equals_DiferenteNombre_RetornaFalse() {
        // ARRANGE
        ActualizarPerfilRequest request1 = new ActualizarPerfilRequest(
                "Juan Pérez", TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        ActualizarPerfilRequest request2 = new ActualizarPerfilRequest(
                "María García", TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ACT & ASSERT
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @DisplayName("EQUALS - Diferente teléfono retorna false")
    void equals_DiferenteTelefono_RetornaFalse() {
        // ARRANGE
        ActualizarPerfilRequest request1 = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, "+57 3001112233", FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        ActualizarPerfilRequest request2 = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, "+57 3004445566", FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ACT & ASSERT
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ACT & ASSERT
        assertThat(request).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ACT & ASSERT
        assertThat(request).isNotEqualTo("No soy un request");
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del request")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ACT
        String resultado = request.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(NOMBRE_VALIDO);
        assertThat(resultado).contains(TELEFONO_VALIDO);
        assertThat(resultado).contains(FECHA_NACIMIENTO_VALIDA.toString());
        assertThat(resultado).contains(FOTO_PERFIL_URL_VALIDA);
    }

    // ==================== CAMPOS INDIVIDUALES TESTS ====================

    @Test
    @DisplayName("NOMBRE - Diferentes longitudes de nombre son aceptadas")
    void nombre_DiferentesLongitudesDeNombre_SonAceptadas() {
        // ARRANGE
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();

        // ACT & ASSERT
        request.setNombre("Ana"); // 3 caracteres
        assertThat(request.getNombre()).isEqualTo("Ana");

        request.setNombre("Carlos José Antonio de la Santísima Trinidad Rodríguez Pérez");
        assertThat(request.getNombre()).hasSizeGreaterThan(30);

        request.setNombre("");
        assertThat(request.getNombre()).isEmpty();

        request.setNombre(null);
        assertThat(request.getNombre()).isNull();
    }

    @Test
    @DisplayName("TELÉFONO - Diferentes formatos de teléfono son aceptados")
    void telefono_DiferentesFormatosDeTelefono_SonAceptados() {
        // ARRANGE
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();

        // ACT & ASSERT
        request.setTelefono("3001234567");
        assertThat(request.getTelefono()).isEqualTo("3001234567");

        request.setTelefono("+573001234567");
        assertThat(request.getTelefono()).isEqualTo("+573001234567");

        request.setTelefono("+1 (800) 555-1234");
        assertThat(request.getTelefono()).isEqualTo("+1 (800) 555-1234");

        request.setTelefono("");
        assertThat(request.getTelefono()).isEmpty();

        request.setTelefono(null);
        assertThat(request.getTelefono()).isNull();
    }

    @Test
    @DisplayName("FECHA NACIMIENTO - Diferentes fechas son aceptadas")
    void fechaNacimiento_DiferentesFechas_SonAceptadas() {
        // ARRANGE
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();

        // ACT & ASSERT
        LocalDate fechaReciente = LocalDate.now().minusYears(20);
        request.setFechaNacimiento(fechaReciente);
        assertThat(request.getFechaNacimiento()).isEqualTo(fechaReciente);

        LocalDate fechaAntigua = LocalDate.of(1950, 1, 1);
        request.setFechaNacimiento(fechaAntigua);
        assertThat(request.getFechaNacimiento()).isEqualTo(fechaAntigua);

        request.setFechaNacimiento(null);
        assertThat(request.getFechaNacimiento()).isNull();
    }

    @Test
    @DisplayName("FOTO PERFIL - Diferentes URLs son aceptadas")
    void fotoPerfil_DiferentesUrls_SonAceptadas() {
        // ARRANGE
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();

        // ACT & ASSERT
        request.setFotoPerfilUrl("https://cloudinary.com/usuario123.jpg");
        assertThat(request.getFotoPerfilUrl()).isEqualTo("https://cloudinary.com/usuario123.jpg");

        request.setFotoPerfilUrl("/ruta/local/foto.png");
        assertThat(request.getFotoPerfilUrl()).isEqualTo("/ruta/local/foto.png");

        request.setFotoPerfilUrl("");
        assertThat(request.getFotoPerfilUrl()).isEmpty();

        request.setFotoPerfilUrl(null);
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización solo de nombre")
    void scenarioUsoReal_ActualizacionSoloDeNombre() {
        // ARRANGE & ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setNombre("María González");

        // ASSERT - Solo se actualiza el nombre
        assertThat(request.getNombre()).isEqualTo("María González");
        assertThat(request.getTelefono()).isNull();
        assertThat(request.getFechaNacimiento()).isNull();
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización solo de teléfono")
    void scenarioUsoReal_ActualizacionSoloDeTelefono() {
        // ARRANGE & ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setTelefono("+57 3105558899");

        // ASSERT - Solo se actualiza el teléfono
        assertThat(request.getTelefono()).isEqualTo("+57 3105558899");
        assertThat(request.getNombre()).isNull();
        assertThat(request.getFechaNacimiento()).isNull();
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización de información personal")
    void scenarioUsoReal_ActualizacionDeInformacionPersonal() {
        // ARRANGE & ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setNombre("Carlos Andrés Ramírez");
        request.setFechaNacimiento(LocalDate.of(1990, 5, 20));
        request.setTelefono("3204446677");

        // ASSERT
        assertThat(request.getNombre()).isEqualTo("Carlos Andrés Ramírez");
        assertThat(request.getFechaNacimiento()).isEqualTo(LocalDate.of(1990, 5, 20));
        assertThat(request.getTelefono()).isEqualTo("3204446677");
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización de foto de perfil")
    void scenarioUsoReal_ActualizacionDeFotoDePerfil() {
        // ARRANGE & ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setFotoPerfilUrl("https://cloudinary.com/nueva-foto-perfil.jpg");

        // ASSERT - Solo se actualiza la foto
        assertThat(request.getFotoPerfilUrl()).isEqualTo("https://cloudinary.com/nueva-foto-perfil.jpg");
        assertThat(request.getNombre()).isNull();
        assertThat(request.getTelefono()).isNull();
        assertThat(request.getFechaNacimiento()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización completa de perfil")
    void scenarioUsoReal_ActualizacionCompletaDePerfil() {
        // ARRANGE & ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                "Ana María Rodríguez López",
                "+57 3157778888",
                LocalDate.of(1988, 12, 3),
                "https://storage.googleapis.com/perfiles/ana-maria-2024.jpg"
        );

        // ASSERT
        assertThat(request.getNombre()).isEqualTo("Ana María Rodríguez López");
        assertThat(request.getTelefono()).isEqualTo("+57 3157778888");
        assertThat(request.getFechaNacimiento()).isEqualTo(LocalDate.of(1988, 12, 3));
        assertThat(request.getFotoPerfilUrl()).contains("ana-maria-2024");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Usuario joven actualizando perfil")
    void scenarioUsoReal_UsuarioJovenActualizandoPerfil() {
        // ARRANGE & ACT
        LocalDate fechaNacimientoJoven = LocalDate.now().minusYears(18).plusDays(1);
        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                "Laura Martínez",
                "3001112233",
                fechaNacimientoJoven,
                null
        );

        // ASSERT
        assertThat(request.getNombre()).isEqualTo("Laura Martínez");
        assertThat(request.getTelefono()).isEqualTo("3001112233");
        assertThat(request.getFechaNacimiento()).isAfter(LocalDate.now().minusYears(18));
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Usuario mayor actualizando perfil")
    void scenarioUsoReal_UsuarioMayorActualizandoPerfil() {
        // ARRANGE & ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                "Roberto Silva",
                "+1 800 555 1234",
                LocalDate.of(1960, 3, 15),
                "https://ejemplo.com/foto-vintage.jpg"
        );

        // ASSERT
        assertThat(request.getNombre()).isEqualTo("Roberto Silva");
        assertThat(request.getTelefono()).isEqualTo("+1 800 555 1234");
        assertThat(request.getFechaNacimiento().getYear()).isEqualTo(1960);
        assertThat(request.getFotoPerfilUrl()).contains("vintage");
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Request completamente vacío")
    void casoBorde_RequestCompletamenteVacio() {
        // ARRANGE & ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();

        // ASSERT
        assertThat(request.getNombre()).isNull();
        assertThat(request.getTelefono()).isNull();
        assertThat(request.getFechaNacimiento()).isNull();
        assertThat(request.getFotoPerfilUrl()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Nombre con longitud mínima")
    void casoBorde_NombreConLongitudMinima() {
        // ARRANGE & ACT
        String nombreMinimo = "Li"; // 2 caracteres
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setNombre(nombreMinimo);

        // ASSERT
        assertThat(request.getNombre()).hasSize(2);
        assertThat(request.getNombre()).isEqualTo(nombreMinimo);
    }

    @Test
    @DisplayName("CASO BORDE - Nombre con longitud máxima")
    void casoBorde_NombreConLongitudMaxima() {
        // ARRANGE & ACT
        String nombreMaximo = "A".repeat(80); // 80 caracteres exactos
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setNombre(nombreMaximo);

        // ASSERT
        assertThat(request.getNombre()).hasSize(80);
        assertThat(request.getNombre()).isEqualTo(nombreMaximo);
    }

    @Test
    @DisplayName("CASO BORDE - Teléfono con formato internacional largo")
    void casoBorde_TelefonoConFormatoInternacionalLargo() {
        // ARRANGE & ACT
        String telefonoLargo = "+441632960123"; // Formato UK
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setTelefono(telefonoLargo);

        // ASSERT
        assertThat(request.getTelefono()).isEqualTo(telefonoLargo);
        assertThat(request.getTelefono()).startsWith("+44");
    }

    @Test
    @DisplayName("CASO BORDE - Fecha de nacimiento muy antigua")
    void casoBorde_FechaDeNacimientoMuyAntigua() {
        // ARRANGE & ACT
        LocalDate fechaMuyAntigua = LocalDate.of(1900, 1, 1);
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setFechaNacimiento(fechaMuyAntigua);

        // ASSERT
        assertThat(request.getFechaNacimiento()).isEqualTo(fechaMuyAntigua);
        assertThat(request.getFechaNacimiento().getYear()).isEqualTo(1900);
    }

    @Test
    @DisplayName("CASO BORDE - Fecha de nacimiento reciente")
    void casoBorde_FechaDeNacimientoReciente() {
        // ARRANGE & ACT
        LocalDate fechaReciente = LocalDate.now().minusDays(1);
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setFechaNacimiento(fechaReciente);

        // ASSERT
        assertThat(request.getFechaNacimiento()).isEqualTo(fechaReciente);
        assertThat(request.getFechaNacimiento()).isBefore(LocalDate.now());
    }

    @Test
    @DisplayName("CASO BORDE - URL de foto con ruta local")
    void casoBorde_UrlDeFotoConRutaLocal() {
        // ARRANGE & ACT
        String rutaLocal = "file:///C:/Users/Usuario/Fotos/perfil.jpg";
        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setFotoPerfilUrl(rutaLocal);

        // ASSERT
        assertThat(request.getFotoPerfilUrl()).isEqualTo(rutaLocal);
        assertThat(request.getFotoPerfilUrl()).startsWith("file://");
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE - Crear dos objetos IDÉNTICOS
        ActualizarPerfilRequest request1 = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        ActualizarPerfilRequest request2 = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ASSERT - Verificar que @Data, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.toString()).isNotNull();
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        ActualizarPerfilRequest requestVacio = new ActualizarPerfilRequest();
        assertThat(requestVacio).isNotNull();

        // Verificar que los getters y setters funcionan
        requestVacio.setNombre("Test");
        assertThat(requestVacio.getNombre()).isEqualTo("Test");
    }

    // ==================== VALIDACIÓN DE ESTRUCTURA TESTS ====================

    @Test
    @DisplayName("ESTRUCTURA - Campos tienen los tipos de datos correctos")
    void estructura_CamposTienenLosTiposDeDatosCorrectos() {
        // ARRANGE & ACT
        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ASSERT
        assertThat(request.getNombre()).isInstanceOf(String.class);
        assertThat(request.getTelefono()).isInstanceOf(String.class);
        assertThat(request.getFechaNacimiento()).isInstanceOf(LocalDate.class);
        assertThat(request.getFotoPerfilUrl()).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("ESTRUCTURA - Instancia puede ser serializada correctamente")
    void estructura_InstanciaPuedeSerSerializadaCorrectamente() {
        // ARRANGE
        ActualizarPerfilRequest request = new ActualizarPerfilRequest(
                NOMBRE_VALIDO, TELEFONO_VALIDO, FECHA_NACIMIENTO_VALIDA, FOTO_PERFIL_URL_VALIDA
        );

        // ACT - Simular serialización/deserialización
        ActualizarPerfilRequest copia = new ActualizarPerfilRequest(
                request.getNombre(),
                request.getTelefono(),
                request.getFechaNacimiento(),
                request.getFotoPerfilUrl()
        );

        // ASSERT
        assertThat(copia).isEqualTo(request);
        assertThat(copia.getNombre()).isEqualTo(request.getNombre());
        assertThat(copia.getTelefono()).isEqualTo(request.getTelefono());
        assertThat(copia.getFechaNacimiento()).isEqualTo(request.getFechaNacimiento());
        assertThat(copia.getFotoPerfilUrl()).isEqualTo(request.getFotoPerfilUrl());
    }

    // ==================== BUSINESS LOGIC TESTS ====================

    @Test
    @DisplayName("BUSINESS - Request permite actualización parcial flexible")
    void business_RequestPermiteActualizacionParcialFlexible() {
        // ARRANGE & ACT - Actualizar solo algunos campos
        ActualizarPerfilRequest requestParcial = new ActualizarPerfilRequest();
        requestParcial.setTelefono("3112223344");
        requestParcial.setFotoPerfilUrl("https://nueva-foto.jpg");

        // ASSERT - Solo los campos especificados deben tener valor
        assertThat(requestParcial.getTelefono()).isNotNull();
        assertThat(requestParcial.getFotoPerfilUrl()).isNotNull();
        assertThat(requestParcial.getNombre()).isNull();
        assertThat(requestParcial.getFechaNacimiento()).isNull();
    }

    @Test
    @DisplayName("BUSINESS - Diferentes métodos de construcción producen objetos iguales")
    void business_DiferentesMetodosDeConstruccion_ProducenObjetosIguales() {
        // ARRANGE
        ActualizarPerfilRequest viaConstructor = new ActualizarPerfilRequest(
                "Pedro Navarro", "3225556677", LocalDate.of(1985, 7, 10), "https://foto.jpg"
        );

        ActualizarPerfilRequest viaSetters = new ActualizarPerfilRequest();
        viaSetters.setNombre("Pedro Navarro");
        viaSetters.setTelefono("3225556677");
        viaSetters.setFechaNacimiento(LocalDate.of(1985, 7, 10));
        viaSetters.setFotoPerfilUrl("https://foto.jpg");

        // ACT & ASSERT - Ambos deben ser iguales
        assertThat(viaConstructor).isEqualTo(viaSetters);
        assertThat(viaConstructor.hashCode()).isEqualTo(viaSetters.hashCode());
    }
}