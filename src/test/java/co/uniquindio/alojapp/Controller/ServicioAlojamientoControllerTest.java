package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.ServicioAlojamientoDTO;
import co.uniquindio.alojapp.negocio.Service.ServicioAlojamientoService;
import co.uniquindio.alojapp.negocio.Service.ServicioAlojamientoService.ServicioUsoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para ServicioAlojamientoController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ServicioAlojamientoController - Unit Tests")
public class ServicioAlojamientoControllerTest {

    @Mock
    private ServicioAlojamientoService servicioService;

    @InjectMocks
    private ServicioAlojamientoController servicioController;

    private ServicioAlojamientoDTO servicioDTO;
    private ServicioUsoDTO servicioUsoDTO;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes
        servicioDTO = new ServicioAlojamientoDTO(7, "Piscina privada", "Piscina climatizada con vista al mar");

        // Crear ServicioUsoDTO según la definición real en el service
        ServicioAlojamientoDTO servicioParaUso = new ServicioAlojamientoDTO(7, "Wi-Fi", "Internet de alta velocidad");
        servicioUsoDTO = new ServicioUsoDTO(servicioParaUso, 25L);
    }

    // =========================================================================
    // TESTS PARA CREAR SERVICIO
    // =========================================================================

    @Test
    @DisplayName("Crear servicio - Debería retornar 201 CREATED con URI")
    void crear_DeberiaRetornar201CreatedConUri() {
        // Arrange
        ServicioAlojamientoController.CrearServicioRequest request =
                new ServicioAlojamientoController.CrearServicioRequest(
                        "Piscina privada",
                        "Piscina climatizada con vista al mar",
                        "https://cdn.ejemplo.com/icons/pool.svg"
                );

        when(servicioService.crear("Piscina privada", "Piscina climatizada con vista al mar", "https://cdn.ejemplo.com/icons/pool.svg"))
                .thenReturn(servicioDTO);

        // Act
        ResponseEntity<ServicioAlojamientoDTO> response = servicioController.crear(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(servicioDTO);
        assertThat(response.getBody().getId()).isEqualTo(7);
        assertThat(response.getBody().getNombre()).isEqualTo("Piscina privada");
        assertThat(response.getHeaders().getLocation()).isEqualTo(URI.create("/api/servicios/7"));

        verify(servicioService, times(1)).crear("Piscina privada", "Piscina climatizada con vista al mar", "https://cdn.ejemplo.com/icons/pool.svg");
    }

    @Test
    @DisplayName("Crear servicio - Sin iconoUrl debería procesar correctamente")
    void crear_SinIconoUrl_DeberiaProcesarCorrectamente() {
        // Arrange
        ServicioAlojamientoController.CrearServicioRequest request =
                new ServicioAlojamientoController.CrearServicioRequest(
                        "Wi-Fi",
                        "Internet de alta velocidad",
                        null
                );

        ServicioAlojamientoDTO servicioSinIcono = new ServicioAlojamientoDTO(8, "Wi-Fi", "Internet de alta velocidad");

        when(servicioService.crear("Wi-Fi", "Internet de alta velocidad", null))
                .thenReturn(servicioSinIcono);

        // Act
        ResponseEntity<ServicioAlojamientoDTO> response = servicioController.crear(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getNombre()).isEqualTo("Wi-Fi");
        assertThat(response.getBody().getDescripcion()).isEqualTo("Internet de alta velocidad");

        verify(servicioService, times(1)).crear("Wi-Fi", "Internet de alta velocidad", null);
    }

    // =========================================================================
    // TESTS PARA LISTAR SERVICIOS
    // =========================================================================

    @Test
    @DisplayName("Listar servicios - Debería retornar 200 OK con lista")
    void listar_DeberiaRetornar200OkConLista() {
        // Arrange
        List<ServicioAlojamientoDTO> servicios = Arrays.asList(servicioDTO);
        when(servicioService.listar()).thenReturn(servicios);

        // Act
        ResponseEntity<List<ServicioAlojamientoDTO>> response = servicioController.listar();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(servicios);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getNombre()).isEqualTo("Piscina privada");

        verify(servicioService, times(1)).listar();
    }

    @Test
    @DisplayName("Listar servicios - Lista vacía debería retornar 200 OK")
    void listar_ListaVacia_DeberiaRetornar200Ok() {
        // Arrange
        List<ServicioAlojamientoDTO> serviciosVacias = Arrays.asList();
        when(servicioService.listar()).thenReturn(serviciosVacias);

        // Act
        ResponseEntity<List<ServicioAlojamientoDTO>> response = servicioController.listar();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();

        verify(servicioService, times(1)).listar();
    }

    // =========================================================================
    // TESTS PARA OBTENER SERVICIO POR ID
    // =========================================================================

    @Test
    @DisplayName("Obtener servicio por ID - Debería retornar 200 OK")
    void obtenerPorId_DeberiaRetornar200Ok() {
        // Arrange
        Integer servicioId = 7;
        when(servicioService.obtenerPorId(servicioId)).thenReturn(servicioDTO);

        // Act
        ResponseEntity<ServicioAlojamientoDTO> response = servicioController.obtenerPorId(servicioId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(servicioDTO);
        assertThat(response.getBody().getId()).isEqualTo(7);
        assertThat(response.getBody().getDescripcion()).isEqualTo("Piscina climatizada con vista al mar");

        verify(servicioService, times(1)).obtenerPorId(servicioId);
    }

    // =========================================================================
    // TESTS PARA BUSCAR POR NOMBRE
    // =========================================================================

    @Test
    @DisplayName("Buscar por nombre - Debería retornar 200 OK")
    void buscarPorNombre_DeberiaRetornar200Ok() {
        // Arrange
        String nombre = "Wi-Fi";
        ServicioAlojamientoDTO servicioWiFi = new ServicioAlojamientoDTO(1, "Wi-Fi", "Internet de alta velocidad");

        when(servicioService.buscarPorNombre(nombre)).thenReturn(servicioWiFi);

        // Act
        ResponseEntity<ServicioAlojamientoDTO> response = servicioController.buscarPorNombre(nombre);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(servicioWiFi);
        assertThat(response.getBody().getNombre()).isEqualTo("Wi-Fi");

        verify(servicioService, times(1)).buscarPorNombre(nombre);
    }

    // =========================================================================
    // TESTS PARA ACTUALIZAR SERVICIO
    // =========================================================================

    @Test
    @DisplayName("Actualizar servicio - Debería retornar 200 OK")
    void actualizar_DeberiaRetornar200Ok() {
        // Arrange
        Integer servicioId = 7;
        ServicioAlojamientoController.ActualizarServicioRequest request =
                new ServicioAlojamientoController.ActualizarServicioRequest(
                        "Wi-Fi Premium",
                        "Internet de alta velocidad con repetidores",
                        "https://cdn.ejemplo.com/icons/wifi-premium.svg"
                );

        ServicioAlojamientoDTO servicioActualizado = new ServicioAlojamientoDTO(7, "Wi-Fi Premium", "Internet de alta velocidad con repetidores");

        when(servicioService.actualizar(servicioId, "Wi-Fi Premium", "Internet de alta velocidad con repetidores", "https://cdn.ejemplo.com/icons/wifi-premium.svg"))
                .thenReturn(servicioActualizado);

        // Act
        ResponseEntity<ServicioAlojamientoDTO> response = servicioController.actualizar(servicioId, request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(servicioActualizado);
        assertThat(response.getBody().getNombre()).isEqualTo("Wi-Fi Premium");
        assertThat(response.getBody().getDescripcion()).isEqualTo("Internet de alta velocidad con repetidores");

        verify(servicioService, times(1)).actualizar(servicioId, "Wi-Fi Premium", "Internet de alta velocidad con repetidores", "https://cdn.ejemplo.com/icons/wifi-premium.svg");
    }

    @Test
    @DisplayName("Actualizar servicio - Campos parciales deberían procesarse correctamente")
    void actualizar_CamposParciales_DeberianProcesarseCorrectamente() {
        // Arrange
        Integer servicioId = 7;
        ServicioAlojamientoController.ActualizarServicioRequest request =
                new ServicioAlojamientoController.ActualizarServicioRequest(
                        "Wi-Fi Premium",
                        null, // descripcion no se actualiza
                        null  // iconoUrl no se actualiza
                );

        when(servicioService.actualizar(servicioId, "Wi-Fi Premium", null, null))
                .thenReturn(servicioDTO);

        // Act
        ResponseEntity<ServicioAlojamientoDTO> response = servicioController.actualizar(servicioId, request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(servicioService, times(1)).actualizar(servicioId, "Wi-Fi Premium", null, null);
    }

    // =========================================================================
    // TESTS PARA ELIMINAR SERVICIO
    // =========================================================================

    @Test
    @DisplayName("Eliminar servicio - Debería retornar 204 NO_CONTENT")
    void eliminar_DeberiaRetornar204NoContent() {
        // Arrange
        Integer servicioId = 7;
        doNothing().when(servicioService).eliminar(servicioId);

        // Act
        ResponseEntity<Void> response = servicioController.eliminar(servicioId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();

        verify(servicioService, times(1)).eliminar(servicioId);
    }

    // =========================================================================
    // TESTS PARA SERVICIOS MÁS USADOS
    // =========================================================================

    @Test
    @DisplayName("Servicios más usados - Debería retornar 200 OK con ranking")
    void serviciosMasUsados_DeberiaRetornar200OkConRanking() {
        // Arrange
        List<ServicioUsoDTO> serviciosMasUsados = Arrays.asList(servicioUsoDTO);
        when(servicioService.serviciosMasUtilizados()).thenReturn(serviciosMasUsados);

        // Act
        ResponseEntity<List<ServicioUsoDTO>> response = servicioController.serviciosMasUsados();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(serviciosMasUsados);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getServicio().getNombre()).isEqualTo("Wi-Fi");
        assertThat(response.getBody().get(0).getTotalAlojamientos()).isEqualTo(25L);

        verify(servicioService, times(1)).serviciosMasUtilizados();
    }

    // =========================================================================
    // TESTS PARA CONTAR ALOJAMIENTOS POR SERVICIO
    // =========================================================================

    @Test
    @DisplayName("Contar alojamientos por servicio - Debería retornar 200 OK con count")
    void contarAlojamientos_DeberiaRetornar200OkConCount() {
        // Arrange
        Integer servicioId = 7;
        Long count = 15L;
        when(servicioService.contarAlojamientosPorServicio(servicioId)).thenReturn(count);

        // Act
        ResponseEntity<Long> response = servicioController.contarAlojamientos(servicioId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(15L);

        verify(servicioService, times(1)).contarAlojamientosPorServicio(servicioId);
    }

    @Test
    @DisplayName("Contar alojamientos por servicio - Sin alojamientos debería retornar 0")
    void contarAlojamientos_SinAlojamientos_DeberiaRetornar0() {
        // Arrange
        Integer servicioId = 99;
        when(servicioService.contarAlojamientosPorServicio(servicioId)).thenReturn(0L);

        // Act
        ResponseEntity<Long> response = servicioController.contarAlojamientos(servicioId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(0L);

        verify(servicioService, times(1)).contarAlojamientosPorServicio(servicioId);
    }

    // =========================================================================
    // TESTS DE CASOS BORDE Y VALIDACIONES
    // =========================================================================

    @Test
    @DisplayName("ServicioAlojamientoDTO - Campos completos tienen valores correctos")
    void servicioAlojamientoDTO_CamposCompletos_TienenValoresCorrectos() {
        // Arrange & Act
        ServicioAlojamientoDTO dtoCompleto = new ServicioAlojamientoDTO(10, "Parqueadero gratuito", "Estacionamiento privado dentro de la propiedad");

        // Assert
        assertThat(dtoCompleto.getId()).isEqualTo(10);
        assertThat(dtoCompleto.getNombre()).isEqualTo("Parqueadero gratuito");
        assertThat(dtoCompleto.getDescripcion()).isEqualTo("Estacionamiento privado dentro de la propiedad");
    }

    @Test
    @DisplayName("ServicioUsoDTO - Campos tienen valores correctos")
    void servicioUsoDTO_Campos_TienenValoresCorrectos() {
        // Arrange & Act
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(5, "Piscina", "Piscina climatizada");
        ServicioUsoDTO usoDTO = new ServicioUsoDTO(servicio, 30L);

        // Assert
        assertThat(usoDTO.getServicio().getId()).isEqualTo(5);
        assertThat(usoDTO.getServicio().getNombre()).isEqualTo("Piscina");
        assertThat(usoDTO.getTotalAlojamientos()).isEqualTo(30L);
    }

    @Test
    @DisplayName("CrearServicioRequest - Campos tienen valores correctos")
    void crearServicioRequest_Campos_TienenValoresCorrectos() {
        // Arrange & Act
        ServicioAlojamientoController.CrearServicioRequest request =
                new ServicioAlojamientoController.CrearServicioRequest(
                        "Desayuno incluido",
                        "Desayuno continental servido en la habitación",
                        "https://cdn.ejemplo.com/icons/breakfast.svg"
                );

        // Assert
        assertThat(request.nombre()).isEqualTo("Desayuno incluido");
        assertThat(request.descripcion()).isEqualTo("Desayuno continental servido en la habitación");
        assertThat(request.iconoUrl()).isEqualTo("https://cdn.ejemplo.com/icons/breakfast.svg");
    }

    @Test
    @DisplayName("ActualizarServicioRequest - Campos tienen valores correctos")
    void actualizarServicioRequest_Campos_TienenValoresCorrectos() {
        // Arrange & Act
        ServicioAlojamientoController.ActualizarServicioRequest request =
                new ServicioAlojamientoController.ActualizarServicioRequest(
                        "Desayuno buffet",
                        "Buffet con variedad de opciones",
                        "https://cdn.ejemplo.com/icons/buffet.svg"
                );

        // Assert
        assertThat(request.nombre()).isEqualTo("Desayuno buffet");
        assertThat(request.descripcion()).isEqualTo("Buffet con variedad de opciones");
        assertThat(request.iconoUrl()).isEqualTo("https://cdn.ejemplo.com/icons/buffet.svg");
    }

    @Test
    @DisplayName("Múltiples operaciones con diferentes servicios")
    void multiplesOperaciones_DiferentesServicios_DeberianProcesarseCorrectamente() {
        // Arrange
        ServicioAlojamientoDTO servicio1 = new ServicioAlojamientoDTO(1, "Wi-Fi", null);
        ServicioAlojamientoDTO servicio2 = new ServicioAlojamientoDTO(2, "Piscina", null);

        when(servicioService.listar()).thenReturn(Arrays.asList(servicio1, servicio2));
        when(servicioService.obtenerPorId(1)).thenReturn(servicio1);
        when(servicioService.obtenerPorId(2)).thenReturn(servicio2);

        // Act
        ResponseEntity<List<ServicioAlojamientoDTO>> listarResponse = servicioController.listar();
        ResponseEntity<ServicioAlojamientoDTO> obtener1Response = servicioController.obtenerPorId(1);
        ResponseEntity<ServicioAlojamientoDTO> obtener2Response = servicioController.obtenerPorId(2);

        // Assert
        assertThat(listarResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listarResponse.getBody()).hasSize(2);
        assertThat(obtener1Response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(obtener1Response.getBody().getNombre()).isEqualTo("Wi-Fi");
        assertThat(obtener2Response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(obtener2Response.getBody().getNombre()).isEqualTo("Piscina");

        verify(servicioService, times(1)).listar();
        verify(servicioService, times(1)).obtenerPorId(1);
        verify(servicioService, times(1)).obtenerPorId(2);
    }
}