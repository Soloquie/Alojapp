package co.uniquindio.alojapp.negocio.DTO.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para MensajeResponse
 */
@DisplayName("MensajeResponse - Unit Tests")
public class MensajeResponseTest {

    private final Boolean EXITO_VALIDO = true;
    private final String MENSAJE_VALIDO = "Operación completada exitosamente";
    private final Integer CODIGO_HTTP_VALIDO = 200;
    private final LocalDateTime TIMESTAMP_VALIDO = LocalDateTime.now();
    private final Object DATOS_VALIDOS = new Object() {
        @Override
        public String toString() {
            return "Datos de prueba";
        }
    };

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        MensajeResponse response = new MensajeResponse();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getExito()).isNull();
        assertThat(response.getMensaje()).isNull();
        assertThat(response.getCodigoHttp()).isNull();
        assertThat(response.getTimestamp()).isNull();
        assertThat(response.getDatos()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        MensajeResponse response = new MensajeResponse(
                EXITO_VALIDO, MENSAJE_VALIDO, CODIGO_HTTP_VALIDO, TIMESTAMP_VALIDO, DATOS_VALIDOS
        );

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getExito()).isEqualTo(EXITO_VALIDO);
        assertThat(response.getMensaje()).isEqualTo(MENSAJE_VALIDO);
        assertThat(response.getCodigoHttp()).isEqualTo(CODIGO_HTTP_VALIDO);
        assertThat(response.getTimestamp()).isEqualTo(TIMESTAMP_VALIDO);
        assertThat(response.getDatos()).isEqualTo(DATOS_VALIDOS);
    }

    @Test
    @DisplayName("Builder - Crea instancia correctamente")
    void builder_CreaInstanciaCorrectamente() {
        // Act
        MensajeResponse response = MensajeResponse.builder()
                .exito(EXITO_VALIDO)
                .mensaje(MENSAJE_VALIDO)
                .codigoHttp(CODIGO_HTTP_VALIDO)
                .timestamp(TIMESTAMP_VALIDO)
                .datos(DATOS_VALIDOS)
                .build();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getExito()).isEqualTo(EXITO_VALIDO);
        assertThat(response.getMensaje()).isEqualTo(MENSAJE_VALIDO);
        assertThat(response.getCodigoHttp()).isEqualTo(CODIGO_HTTP_VALIDO);
        assertThat(response.getTimestamp()).isEqualTo(TIMESTAMP_VALIDO);
        assertThat(response.getDatos()).isEqualTo(DATOS_VALIDOS);
    }

    @Test
    @DisplayName("Builder - Permite construcción parcial")
    void builder_PermiteConstruccionParcial() {
        // Act
        MensajeResponse response = MensajeResponse.builder()
                .exito(EXITO_VALIDO)
                .mensaje(MENSAJE_VALIDO)
                .codigoHttp(CODIGO_HTTP_VALIDO)
                .build();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getExito()).isEqualTo(EXITO_VALIDO);
        assertThat(response.getMensaje()).isEqualTo(MENSAJE_VALIDO);
        assertThat(response.getCodigoHttp()).isEqualTo(CODIGO_HTTP_VALIDO);
        assertThat(response.getTimestamp()).isNull();
        assertThat(response.getDatos()).isNull();
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        MensajeResponse response = new MensajeResponse();

        // Act
        response.setExito(EXITO_VALIDO);
        response.setMensaje(MENSAJE_VALIDO);
        response.setCodigoHttp(CODIGO_HTTP_VALIDO);
        response.setTimestamp(TIMESTAMP_VALIDO);
        response.setDatos(DATOS_VALIDOS);

        // Assert
        assertThat(response.getExito()).isEqualTo(EXITO_VALIDO);
        assertThat(response.getMensaje()).isEqualTo(MENSAJE_VALIDO);
        assertThat(response.getCodigoHttp()).isEqualTo(CODIGO_HTTP_VALIDO);
        assertThat(response.getTimestamp()).isEqualTo(TIMESTAMP_VALIDO);
        assertThat(response.getDatos()).isEqualTo(DATOS_VALIDOS);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        LocalDateTime timestampFijo = LocalDateTime.of(2024, 1, 1, 10, 0);

        MensajeResponse response1 = new MensajeResponse(
                true, "Mensaje", 200, timestampFijo, "Datos"
        );
        MensajeResponse response2 = new MensajeResponse(
                true, "Mensaje", 200, timestampFijo, "Datos"
        );

        // Act & Assert - Verificar manualmente cada campo
        assertThat(response1.getExito()).isEqualTo(response2.getExito());
        assertThat(response1.getMensaje()).isEqualTo(response2.getMensaje());
        assertThat(response1.getCodigoHttp()).isEqualTo(response2.getCodigoHttp());
        assertThat(response1.getTimestamp()).isEqualTo(response2.getTimestamp());
        assertThat(response1.getDatos()).isEqualTo(response2.getDatos());
    }

    @Test
    @DisplayName("Diferente exito - Campos no son iguales")
    void diferenteExito_CamposNoSonIguales() {
        // Arrange
        MensajeResponse response1 = new MensajeResponse();
        response1.setExito(true);

        MensajeResponse response2 = new MensajeResponse();
        response2.setExito(false);

        // Act & Assert
        assertThat(response1.getExito()).isNotEqualTo(response2.getExito());
    }

    @Test
    @DisplayName("Diferente mensaje - Campos no son iguales")
    void diferenteMensaje_CamposNoSonIguales() {
        // Arrange
        MensajeResponse response1 = new MensajeResponse();
        response1.setMensaje("Mensaje 1");

        MensajeResponse response2 = new MensajeResponse();
        response2.setMensaje("Mensaje 2");

        // Act & Assert
        assertThat(response1.getMensaje()).isNotEqualTo(response2.getMensaje());
    }

    @Test
    @DisplayName("Diferente codigoHttp - Campos no son iguales")
    void diferenteCodigoHttp_CamposNoSonIguales() {
        // Arrange
        MensajeResponse response1 = new MensajeResponse();
        response1.setCodigoHttp(200);

        MensajeResponse response2 = new MensajeResponse();
        response2.setCodigoHttp(400);

        // Act & Assert
        assertThat(response1.getCodigoHttp()).isNotEqualTo(response2.getCodigoHttp());
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        MensajeResponse response = new MensajeResponse(
                EXITO_VALIDO, MENSAJE_VALIDO, CODIGO_HTTP_VALIDO, TIMESTAMP_VALIDO, DATOS_VALIDOS
        );

        // Act & Assert
        assertThatCode(() -> response.hashCode()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        MensajeResponse response = new MensajeResponse(
                EXITO_VALIDO, MENSAJE_VALIDO, CODIGO_HTTP_VALIDO, TIMESTAMP_VALIDO, DATOS_VALIDOS
        );

        // Act
        String resultado = response.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("Exito - Acepta diferentes valores booleanos")
    void exito_AceptaDiferentesValoresBooleanos() {
        // Arrange
        MensajeResponse response = new MensajeResponse();

        // Act & Assert - true
        response.setExito(true);
        assertThat(response.getExito()).isTrue();

        // false
        response.setExito(false);
        assertThat(response.getExito()).isFalse();

        // Nulo
        response.setExito(null);
        assertThat(response.getExito()).isNull();
    }

    @Test
    @DisplayName("Mensaje - Acepta diferentes textos")
    void mensaje_AceptaDiferentesTextos() {
        // Arrange
        MensajeResponse response = new MensajeResponse();

        // Act & Assert - Mensaje corto
        response.setMensaje("Éxito");
        assertThat(response.getMensaje()).isEqualTo("Éxito");

        // Mensaje largo
        String mensajeLargo = "La operación se completó exitosamente después de procesar todos los datos requeridos";
        response.setMensaje(mensajeLargo);
        assertThat(response.getMensaje()).hasSizeGreaterThan(20);

        // Mensaje vacío
        response.setMensaje("");
        assertThat(response.getMensaje()).isEmpty();

        // Mensaje nulo
        response.setMensaje(null);
        assertThat(response.getMensaje()).isNull();
    }

    @Test
    @DisplayName("CodigoHttp - Acepta diferentes códigos de estado")
    void codigoHttp_AceptaDiferentesCodigosDeEstado() {
        // Arrange
        MensajeResponse response = new MensajeResponse();

        // Act & Assert - 200 OK
        response.setCodigoHttp(200);
        assertThat(response.getCodigoHttp()).isEqualTo(200);

        // 201 Created
        response.setCodigoHttp(201);
        assertThat(response.getCodigoHttp()).isEqualTo(201);

        // 400 Bad Request
        response.setCodigoHttp(400);
        assertThat(response.getCodigoHttp()).isEqualTo(400);

        // 404 Not Found
        response.setCodigoHttp(404);
        assertThat(response.getCodigoHttp()).isEqualTo(404);

        // 500 Internal Server Error
        response.setCodigoHttp(500);
        assertThat(response.getCodigoHttp()).isEqualTo(500);

        // Nulo
        response.setCodigoHttp(null);
        assertThat(response.getCodigoHttp()).isNull();
    }

    @Test
    @DisplayName("Timestamp - Acepta diferentes fechas")
    void timestamp_AceptaDiferentesFechas() {
        // Arrange
        MensajeResponse response = new MensajeResponse();

        // Act & Assert - Fecha pasada
        LocalDateTime fechaPasada = LocalDateTime.of(2020, 1, 1, 10, 0);
        response.setTimestamp(fechaPasada);
        assertThat(response.getTimestamp()).isEqualTo(fechaPasada);

        // Fecha presente
        LocalDateTime fechaPresente = LocalDateTime.now();
        response.setTimestamp(fechaPresente);
        assertThat(response.getTimestamp()).isEqualTo(fechaPresente);

        // Fecha futura
        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(1);
        response.setTimestamp(fechaFutura);
        assertThat(response.getTimestamp()).isEqualTo(fechaFutura);

        // Nulo
        response.setTimestamp(null);
        assertThat(response.getTimestamp()).isNull();
    }

    @Test
    @DisplayName("Datos - Acepta diferentes tipos de objetos")
    void datos_AceptaDiferentesTiposDeObjetos() {
        // Arrange
        MensajeResponse response = new MensajeResponse();

        // Act & Assert - String
        response.setDatos("Texto simple");
        assertThat(response.getDatos()).isEqualTo("Texto simple");

        // Integer
        response.setDatos(123);
        assertThat(response.getDatos()).isEqualTo(123);

        // Lista
        response.setDatos(java.util.List.of("item1", "item2"));
        assertThat(response.getDatos()).asList().hasSize(2);

        // Objeto personalizado
        Object objetoPersonalizado = new Object() {
            @Override
            public String toString() {
                return "Objeto personalizado";
            }
        };
        response.setDatos(objetoPersonalizado);
        assertThat(response.getDatos()).isEqualTo(objetoPersonalizado);

        // Nulo
        response.setDatos(null);
        assertThat(response.getDatos()).isNull();
    }

    @Test
    @DisplayName("Método estático - exitoso(String) crea respuesta exitosa")
    void metodoEstatico_ExitosoString_CreaRespuestaExitosa() {
        // Act
        MensajeResponse response = MensajeResponse.exitoso("Operación completada");

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getExito()).isTrue();
        assertThat(response.getMensaje()).isEqualTo("Operación completada");
        assertThat(response.getCodigoHttp()).isEqualTo(200);
        assertThat(response.getTimestamp()).isNotNull();
        assertThat(response.getDatos()).isNull();
    }

    @Test
    @DisplayName("Método estático - exitoso(String, Object) crea respuesta exitosa con datos")
    void metodoEstatico_ExitosoStringObject_CreaRespuestaExitosaConDatos() {
        // Arrange
        Object datos = new Object() {
            @Override
            public String toString() {
                return "Datos de prueba";
            }
        };

        // Act
        MensajeResponse response = MensajeResponse.exitoso("Datos obtenidos", datos);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getExito()).isTrue();
        assertThat(response.getMensaje()).isEqualTo("Datos obtenidos");
        assertThat(response.getCodigoHttp()).isEqualTo(200);
        assertThat(response.getTimestamp()).isNotNull();
        assertThat(response.getDatos()).isEqualTo(datos);
    }

    @Test
    @DisplayName("Método estático - error(String, Integer) crea respuesta de error")
    void metodoEstatico_ErrorStringInteger_CreaRespuestaDeError() {
        // Act
        MensajeResponse response = MensajeResponse.error("Error en la operación", 400);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getExito()).isFalse();
        assertThat(response.getMensaje()).isEqualTo("Error en la operación");
        assertThat(response.getCodigoHttp()).isEqualTo(400);
        assertThat(response.getTimestamp()).isNotNull();
        assertThat(response.getDatos()).isNull();
    }

    @Test
    @DisplayName("Escenario - Respuesta exitosa sin datos")
    void escenario_RespuestaExitosaSinDatos() {
        // Act
        MensajeResponse response = MensajeResponse.builder()
                .exito(true)
                .mensaje("Usuario creado exitosamente")
                .codigoHttp(201)
                .timestamp(LocalDateTime.now())
                .build();

        // Assert
        assertThat(response.getExito()).isTrue();
        assertThat(response.getMensaje()).isEqualTo("Usuario creado exitosamente");
        assertThat(response.getCodigoHttp()).isEqualTo(201);
        assertThat(response.getTimestamp()).isNotNull();
        assertThat(response.getDatos()).isNull();
    }

    @Test
    @DisplayName("Escenario - Respuesta exitosa con datos")
    void escenario_RespuestaExitosaConDatos() {
        // Arrange
        Object usuario = new Object() {
            public Integer id = 1;
            public String nombre = "Juan Pérez";

            @Override
            public String toString() {
                return "Usuario{id=" + id + ", nombre='" + nombre + "'}";
            }
        };

        // Act
        MensajeResponse response = MensajeResponse.builder()
                .exito(true)
                .mensaje("Usuario encontrado")
                .codigoHttp(200)
                .timestamp(LocalDateTime.now())
                .datos(usuario)
                .build();

        // Assert
        assertThat(response.getExito()).isTrue();
        assertThat(response.getMensaje()).isEqualTo("Usuario encontrado");
        assertThat(response.getCodigoHttp()).isEqualTo(200);
        assertThat(response.getTimestamp()).isNotNull();
        assertThat(response.getDatos()).isEqualTo(usuario);
    }

    @Test
    @DisplayName("Escenario - Respuesta de error")
    void escenario_RespuestaDeError() {
        // Act
        MensajeResponse response = MensajeResponse.builder()
                .exito(false)
                .mensaje("Recurso no encontrado")
                .codigoHttp(404)
                .timestamp(LocalDateTime.now())
                .build();

        // Assert
        assertThat(response.getExito()).isFalse();
        assertThat(response.getMensaje()).isEqualTo("Recurso no encontrado");
        assertThat(response.getCodigoHttp()).isEqualTo(404);
        assertThat(response.getTimestamp()).isNotNull();
        assertThat(response.getDatos()).isNull();
    }

    @Test
    @DisplayName("Casos borde - Mensaje muy largo")
    void casosBorde_MensajeMuyLargo() {
        // Arrange & Act
        String mensajeLargo = "M".repeat(1000);
        MensajeResponse response = new MensajeResponse();
        response.setMensaje(mensajeLargo);

        // Assert
        assertThat(response.getMensaje()).hasSize(1000);
        assertThat(response.getMensaje()).isEqualTo(mensajeLargo);
    }

    @Test
    @DisplayName("Casos borde - Código HTTP extremo")
    void casosBorde_CodigoHttpExtremo() {
        // Arrange & Act
        MensajeResponse response = new MensajeResponse();
        response.setCodigoHttp(999);

        // Assert
        assertThat(response.getCodigoHttp()).isEqualTo(999);
    }

    @Test
    @DisplayName("Casos borde - Timestamp muy antiguo")
    void casosBorde_TimestampMuyAntiguo() {
        // Arrange & Act
        LocalDateTime timestampAntiguo = LocalDateTime.of(1900, 1, 1, 0, 0);
        MensajeResponse response = new MensajeResponse();
        response.setTimestamp(timestampAntiguo);

        // Assert
        assertThat(response.getTimestamp()).isEqualTo(timestampAntiguo);
        assertThat(response.getTimestamp().getYear()).isEqualTo(1900);
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        MensajeResponse response = new MensajeResponse();

        // Assert
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        LocalDateTime timestamp = LocalDateTime.of(2024, 6, 1, 10, 30);
        MensajeResponse response = new MensajeResponse(
                true, "Test", 200, timestamp, "Test Data"
        );

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getExito()).isTrue();
        assertThat(response.getMensaje()).isEqualTo("Test");
        assertThat(response.getCodigoHttp()).isEqualTo(200);
        assertThat(response.getTimestamp()).isEqualTo(timestamp);
        assertThat(response.getDatos()).isEqualTo("Test Data");
    }

    @Test
    @DisplayName("Lombok - Builder funciona")
    void lombok_BuilderFunciona() {
        // Act
        LocalDateTime timestamp = LocalDateTime.of(2024, 6, 1, 10, 30);
        MensajeResponse response = MensajeResponse.builder()
                .exito(false)
                .mensaje("Error test")
                .codigoHttp(500)
                .timestamp(timestamp)
                .datos("Error details")
                .build();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getExito()).isFalse();
        assertThat(response.getMensaje()).isEqualTo("Error test");
        assertThat(response.getCodigoHttp()).isEqualTo(500);
        assertThat(response.getTimestamp()).isEqualTo(timestamp);
        assertThat(response.getDatos()).isEqualTo("Error details");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        MensajeResponse response = new MensajeResponse();

        // Act
        response.setExito(true);
        response.setMensaje("Setter test");
        response.setCodigoHttp(201);
        response.setTimestamp(LocalDateTime.now());
        response.setDatos("Setter data");

        // Assert
        assertThat(response.getExito()).isTrue();
        assertThat(response.getMensaje()).isEqualTo("Setter test");
        assertThat(response.getCodigoHttp()).isEqualTo(201);
        assertThat(response.getTimestamp()).isNotNull();
        assertThat(response.getDatos()).isEqualTo("Setter data");
    }

    @Test
    @DisplayName("Métodos estáticos - Timestamp se establece automáticamente")
    void metodosEstaticos_TimestampSeEstableceAutomaticamente() {
        // Act
        MensajeResponse response1 = MensajeResponse.exitoso("Test 1");
        MensajeResponse response2 = MensajeResponse.exitoso("Test 2", "Datos");
        MensajeResponse response3 = MensajeResponse.error("Error", 400);

        // Assert
        assertThat(response1.getTimestamp()).isNotNull();
        assertThat(response2.getTimestamp()).isNotNull();
        assertThat(response3.getTimestamp()).isNotNull();

        // Los timestamps deberían ser muy cercanos en el tiempo
        assertThat(response1.getTimestamp()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(response2.getTimestamp()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(response3.getTimestamp()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}