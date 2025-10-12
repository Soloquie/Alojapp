package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para RegistroAnfitrionRequest
 */
@DisplayName("RegistroAnfitrionRequest - Unit Tests")
public class RegistroAnfitrionRequestTest {

    private final LocalDate FECHA_REGISTRO_VALIDA = LocalDate.of(1995, 8, 15);
    private final String DESCRIPCION_PERSONAL_VALIDA = "Apasionado por el turismo y la hospitalidad en el Caribe colombiano";
    private final String DOCUMENTOS_LEGALES_URL_VALIDA = "https://ejemplo.com/docs/cedula.pdf";

    // Datos heredados de RegistroUsuarioRequest
    private final String NOMBRE_VALIDO = "Juan Carlos Pérez";
    private final String EMAIL_VALIDO = "juan.perez@correo.com";
    private final String PASSWORD_VALIDA = "MiClaveSegura123";
    private final String TELEFONO_VALIDO = "+57 3001234567";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getFechaRegistro()).isNull();
        assertThat(request.getDescripcionPersonal()).isNull();
        assertThat(request.getDocumentosLegalesUrl()).isNull();

        // Campos heredados
        assertThat(request.getNombre()).isNull();
        assertThat(request.getEmail()).isNull();
        assertThat(request.getPassword()).isNull();
        assertThat(request.getTelefono()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest(
                FECHA_REGISTRO_VALIDA,
                DESCRIPCION_PERSONAL_VALIDA,
                DOCUMENTOS_LEGALES_URL_VALIDA
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getFechaRegistro()).isEqualTo(FECHA_REGISTRO_VALIDA);
        assertThat(request.getDescripcionPersonal()).isEqualTo(DESCRIPCION_PERSONAL_VALIDA);
        assertThat(request.getDocumentosLegalesUrl()).isEqualTo(DOCUMENTOS_LEGALES_URL_VALIDA);
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente para campos propios")
    void gettersYSetters_FuncionanCorrectamenteParaCamposPropios() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Act
        request.setFechaRegistro(FECHA_REGISTRO_VALIDA);
        request.setDescripcionPersonal(DESCRIPCION_PERSONAL_VALIDA);
        request.setDocumentosLegalesUrl(DOCUMENTOS_LEGALES_URL_VALIDA);

        // Assert
        assertThat(request.getFechaRegistro()).isEqualTo(FECHA_REGISTRO_VALIDA);
        assertThat(request.getDescripcionPersonal()).isEqualTo(DESCRIPCION_PERSONAL_VALIDA);
        assertThat(request.getDocumentosLegalesUrl()).isEqualTo(DOCUMENTOS_LEGALES_URL_VALIDA);
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente para campos heredados")
    void gettersYSetters_FuncionanCorrectamenteParaCamposHeredados() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Act
        request.setNombre(NOMBRE_VALIDO);
        request.setEmail(EMAIL_VALIDO);
        request.setPassword(PASSWORD_VALIDA);
        request.setTelefono(TELEFONO_VALIDO);

        // Assert
        assertThat(request.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(request.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(request.getPassword()).isEqualTo(PASSWORD_VALIDA);
        assertThat(request.getTelefono()).isEqualTo(TELEFONO_VALIDO);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        RegistroAnfitrionRequest request1 = new RegistroAnfitrionRequest();
        request1.setFechaRegistro(FECHA_REGISTRO_VALIDA);
        request1.setDescripcionPersonal(DESCRIPCION_PERSONAL_VALIDA);
        request1.setDocumentosLegalesUrl(DOCUMENTOS_LEGALES_URL_VALIDA);
        request1.setNombre(NOMBRE_VALIDO);
        request1.setEmail(EMAIL_VALIDO);

        RegistroAnfitrionRequest request2 = new RegistroAnfitrionRequest();
        request2.setFechaRegistro(FECHA_REGISTRO_VALIDA);
        request2.setDescripcionPersonal(DESCRIPCION_PERSONAL_VALIDA);
        request2.setDocumentosLegalesUrl(DOCUMENTOS_LEGALES_URL_VALIDA);
        request2.setNombre(NOMBRE_VALIDO);
        request2.setEmail(EMAIL_VALIDO);

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getFechaRegistro()).isEqualTo(request2.getFechaRegistro());
        assertThat(request1.getDescripcionPersonal()).isEqualTo(request2.getDescripcionPersonal());
        assertThat(request1.getDocumentosLegalesUrl()).isEqualTo(request2.getDocumentosLegalesUrl());
        assertThat(request1.getNombre()).isEqualTo(request2.getNombre());
        assertThat(request1.getEmail()).isEqualTo(request2.getEmail());
    }

    @Test
    @DisplayName("Diferente fechaRegistro - Campos no son iguales")
    void diferenteFechaRegistro_CamposNoSonIguales() {
        // Arrange
        RegistroAnfitrionRequest request1 = new RegistroAnfitrionRequest();
        request1.setFechaRegistro(LocalDate.of(2020, 1, 1));

        RegistroAnfitrionRequest request2 = new RegistroAnfitrionRequest();
        request2.setFechaRegistro(LocalDate.of(2021, 1, 1));

        // Act & Assert
        assertThat(request1.getFechaRegistro()).isNotEqualTo(request2.getFechaRegistro());
    }

    @Test
    @DisplayName("Diferente descripcionPersonal - Campos no son iguales")
    void diferenteDescripcionPersonal_CamposNoSonIguales() {
        // Arrange
        RegistroAnfitrionRequest request1 = new RegistroAnfitrionRequest();
        request1.setDescripcionPersonal("Descripción 1");

        RegistroAnfitrionRequest request2 = new RegistroAnfitrionRequest();
        request2.setDescripcionPersonal("Descripción 2");

        // Act & Assert
        assertThat(request1.getDescripcionPersonal()).isNotEqualTo(request2.getDescripcionPersonal());
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();
        request.setDescripcionPersonal(DESCRIPCION_PERSONAL_VALIDA);
        request.setDocumentosLegalesUrl(DOCUMENTOS_LEGALES_URL_VALIDA);
        request.setNombre(NOMBRE_VALIDO);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("ToString - No expone información sensible de la contraseña")
    void toString_NoExponeInformacionSensible() {
        // Arrange
        String passwordSensible = "MiClaveSuperSecreta123";
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();
        request.setPassword(passwordSensible);
        request.setNombre(NOMBRE_VALIDO);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).doesNotContain(passwordSensible);
    }

    @Test
    @DisplayName("FechaRegistro - Campo opcional puede ser nulo")
    void fechaRegistro_CampoOpcionalPuedeSerNulo() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Act & Assert
        request.setFechaRegistro(null);
        assertThat(request.getFechaRegistro()).isNull();
    }

    @Test
    @DisplayName("FechaRegistro - Acepta diferentes fechas")
    void fechaRegistro_AceptaDiferentesFechas() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Act & Assert - Fecha pasada
        LocalDate fechaPasada = LocalDate.of(1990, 5, 15);
        request.setFechaRegistro(fechaPasada);
        assertThat(request.getFechaRegistro()).isEqualTo(fechaPasada);

        // Fecha presente
        LocalDate fechaPresente = LocalDate.now();
        request.setFechaRegistro(fechaPresente);
        assertThat(request.getFechaRegistro()).isEqualTo(fechaPresente);

        // Fecha futura
        LocalDate fechaFutura = LocalDate.now().plusDays(30);
        request.setFechaRegistro(fechaFutura);
        assertThat(request.getFechaRegistro()).isEqualTo(fechaFutura);
    }

    @Test
    @DisplayName("DescripcionPersonal - Acepta diferentes longitudes")
    void descripcionPersonal_AceptaDiferentesLongitudes() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Act & Assert - Descripción larga (hasta 1000 caracteres)
        String descripcionLarga = "D".repeat(1000);
        request.setDescripcionPersonal(descripcionLarga);
        assertThat(request.getDescripcionPersonal()).hasSize(1000);

        // Descripción corta
        request.setDescripcionPersonal("Anfitrión");
        assertThat(request.getDescripcionPersonal()).isEqualTo("Anfitrión");

        // Descripción vacía
        request.setDescripcionPersonal("");
        assertThat(request.getDescripcionPersonal()).isEmpty();

        // Descripción nula
        request.setDescripcionPersonal(null);
        assertThat(request.getDescripcionPersonal()).isNull();
    }

    @Test
    @DisplayName("DocumentosLegalesUrl - Acepta diferentes formatos de URL")
    void documentosLegalesUrl_AceptaDiferentesFormatosDeUrl() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Act & Assert - URL HTTPS
        request.setDocumentosLegalesUrl("https://drive.google.com/documentos/anfitrion.pdf");
        assertThat(request.getDocumentosLegalesUrl()).startsWith("https://");

        // URL HTTP
        request.setDocumentosLegalesUrl("http://mi-sitio.com/docs/licencia.pdf");
        assertThat(request.getDocumentosLegalesUrl()).startsWith("http://");

        // URL vacía
        request.setDocumentosLegalesUrl("");
        assertThat(request.getDocumentosLegalesUrl()).isEmpty();

        // URL nula
        request.setDocumentosLegalesUrl(null);
        assertThat(request.getDocumentosLegalesUrl()).isNull();
    }

    @Test
    @DisplayName("Escenario - Registro completo de anfitrión")
    void escenario_RegistroCompletoDeAnfitrion() {
        // Act
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();
        // Campos heredados
        request.setNombre("María García López");
        request.setEmail("maria.garcia@correo.com");
        request.setPassword("ClaveSegura456");
        request.setTelefono("+57 3105558899");
        // Campos propios
        request.setFechaRegistro(LocalDate.of(2024, 1, 15));
        request.setDescripcionPersonal("Anfitriona profesional con 3 años de experiencia en turismo rural");
        request.setDocumentosLegalesUrl("https://drive.google.com/docs/cedula_maria.pdf");

        // Assert
        assertThat(request.getNombre()).isEqualTo("María García López");
        assertThat(request.getEmail()).contains("maria.garcia");
        assertThat(request.getPassword()).isEqualTo("ClaveSegura456");
        assertThat(request.getTelefono()).contains("3105558899");
        assertThat(request.getFechaRegistro()).isEqualTo(LocalDate.of(2024, 1, 15));
        assertThat(request.getDescripcionPersonal()).contains("turismo rural");
        assertThat(request.getDocumentosLegalesUrl()).contains("cedula_maria");
    }

    @Test
    @DisplayName("Escenario - Registro mínimo sin campos opcionales")
    void escenario_RegistroMinimoSinCamposOpcionales() {
        // Act
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();
        // Solo campos obligatorios heredados
        request.setNombre("Carlos Rodríguez");
        request.setEmail("carlos@correo.com");
        request.setPassword("Password123");

        // Assert
        assertThat(request.getNombre()).isNotBlank();
        assertThat(request.getEmail()).isNotBlank();
        assertThat(request.getPassword()).isNotBlank();
        assertThat(request.getFechaRegistro()).isNull();
        assertThat(request.getDescripcionPersonal()).isNull();
        assertThat(request.getDocumentosLegalesUrl()).isNull();
        assertThat(request.getTelefono()).isNull();
    }

    @Test
    @DisplayName("Escenario - Registro sin fecha (se tomará la fecha actual)")
    void escenario_RegistroSinFecha() {
        // Act
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();
        request.setNombre("Ana Martínez");
        request.setEmail("ana@correo.com");
        request.setPassword("AnaClave789");
        request.setDescripcionPersonal("Me encanta compartir mi hogar con viajeros");

        // Assert
        assertThat(request.getNombre()).isEqualTo("Ana Martínez");
        assertThat(request.getEmail()).isEqualTo("ana@correo.com");
        assertThat(request.getDescripcionPersonal()).isNotBlank();
        assertThat(request.getFechaRegistro()).isNull(); // Se establecerá en el DAO
    }

    @Test
    @DisplayName("Casos borde - Descripción con longitud máxima exacta")
    void casosBorde_DescripcionConLongitudMaximaExacta() {
        // Arrange & Act
        String descripcionMaxima = "A".repeat(1000); // 1000 caracteres exactos
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();
        request.setDescripcionPersonal(descripcionMaxima);

        // Assert
        assertThat(request.getDescripcionPersonal()).hasSize(1000);
        assertThat(request.getDescripcionPersonal()).isEqualTo(descripcionMaxima);
    }

    @Test
    @DisplayName("Casos borde - Fecha muy antigua")
    void casosBorde_FechaMuyAntigua() {
        // Arrange & Act
        LocalDate fechaAntigua = LocalDate.of(1900, 1, 1);
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();
        request.setFechaRegistro(fechaAntigua);

        // Assert
        assertThat(request.getFechaRegistro()).isEqualTo(fechaAntigua);
        assertThat(request.getFechaRegistro().getYear()).isEqualTo(1900);
    }

    @Test
    @DisplayName("Casos borde - URL muy larga")
    void casosBorde_UrlMuyLarga() {
        // Arrange & Act
        String urlLarga = "https://" + "subdomain".repeat(50) + ".com/document.pdf";
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();
        request.setDocumentosLegalesUrl(urlLarga);

        // Assert
        assertThat(request.getDocumentosLegalesUrl()).isEqualTo(urlLarga);
        assertThat(request.getDocumentosLegalesUrl()).startsWith("https://");
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona para campos propios")
    void lombok_ConstructorConParametrosFuncionaParaCamposPropios() {
        // Act
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest(
                FECHA_REGISTRO_VALIDA,
                DESCRIPCION_PERSONAL_VALIDA,
                DOCUMENTOS_LEGALES_URL_VALIDA
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getFechaRegistro()).isEqualTo(FECHA_REGISTRO_VALIDA);
        assertThat(request.getDescripcionPersonal()).isEqualTo(DESCRIPCION_PERSONAL_VALIDA);
        assertThat(request.getDocumentosLegalesUrl()).isEqualTo(DOCUMENTOS_LEGALES_URL_VALIDA);
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Act - Campos propios
        request.setFechaRegistro(LocalDate.of(2024, 6, 1));
        request.setDescripcionPersonal("Test descripción");
        request.setDocumentosLegalesUrl("https://test.com/doc.pdf");

        // Act - Campos heredados
        request.setNombre("Test Nombre");
        request.setEmail("test@test.com");
        request.setPassword("test123");

        // Assert
        assertThat(request.getFechaRegistro()).isEqualTo(LocalDate.of(2024, 6, 1));
        assertThat(request.getDescripcionPersonal()).isEqualTo("Test descripción");
        assertThat(request.getDocumentosLegalesUrl()).isEqualTo("https://test.com/doc.pdf");
        assertThat(request.getNombre()).isEqualTo("Test Nombre");
        assertThat(request.getEmail()).isEqualTo("test@test.com");
        assertThat(request.getPassword()).isEqualTo("test123");
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Act & Assert
        assertThatCode(() -> request.hashCode()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Herencia - Es instancia de RegistroUsuarioRequest")
    void herencia_EsInstanciaDeRegistroUsuarioRequest() {
        // Arrange
        RegistroAnfitrionRequest request = new RegistroAnfitrionRequest();

        // Assert
        assertThat(request).isInstanceOf(RegistroUsuarioRequest.class);
    }
}