package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.ComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ReportarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.ComentarioService;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import co.uniquindio.alojapp.seguridad.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para ComentarioController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ComentarioController - Unit Tests")
public class ComentarioControllerTest {

    @Mock
    private ComentarioService comentarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ComentarioController comentarioController;

    private CrearComentarioRequest crearRequest;
    private ActualizarComentarioRequest actualizarRequest;
    private ReportarComentarioRequest reportarRequest;
    private ComentarioDTO comentarioDTO;
    private Usuario usuario;
    private PaginacionResponse<ComentarioDTO> paginacionResponse;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes
        crearRequest = CrearComentarioRequest.builder()
                .reservaId(25)
                .calificacion(5)
                .comentarioTexto("Excelente alojamiento, muy recomendado")
                .build();

        actualizarRequest = ActualizarComentarioRequest.builder()
                .calificacion(4)
                .comentarioTexto("Contenido actualizado del comentario")
                .build();

        reportarRequest = ReportarComentarioRequest.builder()
                .motivo("Contenido inapropiado")
                .build();

        comentarioDTO = ComentarioDTO.builder()
                .id(100)
                .reservaId(25L)
                .usuarioId(15L)
                .usuarioNombre("Juan Pérez")
                .alojamientoId(10L)
                .calificacion(5)
                .comentarioTexto("Excelente alojamiento, muy recomendado")
                .fechaComentario(LocalDateTime.now())
                .respuestas(Collections.emptyList())
                .build();

        usuario = Usuario.builder()
                .id(15)
                .nombre("Juan Pérez")
                .email("juan@correo.com")
                .build();

        paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(comentarioDTO));
        paginacionResponse.setPaginaActual(0);
        paginacionResponse.setTamanoPagina(20);
        paginacionResponse.setTotalElementos(1L);
        paginacionResponse.setTotalPaginas(1);
    }

    // =========================================================================
    // TESTS PARA CREAR COMENTARIO
    // =========================================================================

    @Test
    @DisplayName("Crear comentario - Debería retornar 201 CREATED")
    void crear_DeberiaRetornar201Created() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));
            when(comentarioService.crear(15, crearRequest)).thenReturn(comentarioDTO);

            // Act
            ResponseEntity<ComentarioDTO> response = comentarioController.crear(crearRequest);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isEqualTo(comentarioDTO);
            assertThat(response.getBody().getId()).isEqualTo(100);
            assertThat(response.getBody().getComentarioTexto()).isEqualTo("Excelente alojamiento, muy recomendado");
            assertThat(response.getBody().getCalificacion()).isEqualTo(5);
            assertThat(response.getBody().getUsuarioId()).isEqualTo(15L);
            assertThat(response.getBody().getAlojamientoId()).isEqualTo(10L);

            verify(comentarioService, times(1)).crear(15, crearRequest);
        }
    }

    @Test
    @DisplayName("Crear comentario - Usuario no autenticado debería lanzar excepción")
    void crear_UsuarioNoAutenticado_DeberiaLanzarExcepcion() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> comentarioController.crear(crearRequest))
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario autenticado no encontrado");

            verifyNoInteractions(comentarioService);
        }
    }

    @Test
    @DisplayName("Crear comentario - Campos del request tienen valores correctos")
    void crear_CamposRequest_TienenValoresCorrectos() {
        // Arrange
        CrearComentarioRequest requestCompleto = CrearComentarioRequest.builder()
                .reservaId(30)
                .calificacion(4)
                .comentarioTexto("Muy buena ubicación y servicio")
                .build();

        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));
            when(comentarioService.crear(15, requestCompleto)).thenReturn(comentarioDTO);

            // Act
            ResponseEntity<ComentarioDTO> response = comentarioController.crear(requestCompleto);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            verify(comentarioService, times(1)).crear(15, requestCompleto);
        }
    }

    // =========================================================================
    // TESTS PARA OBTENER COMENTARIO
    // =========================================================================

    @Test
    @DisplayName("Obtener comentario por ID - Debería retornar 200 OK")
    void obtener_DeberiaRetornar200Ok() {
        // Arrange
        Integer comentarioId = 100;
        when(comentarioService.obtenerPorId(comentarioId)).thenReturn(comentarioDTO);

        // Act
        ResponseEntity<ComentarioDTO> response = comentarioController.obtener(comentarioId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(comentarioDTO);
        assertThat(response.getBody().getUsuarioNombre()).isEqualTo("Juan Pérez");
        assertThat(response.getBody().getAlojamientoId()).isEqualTo(10L);
        assertThat(response.getBody().getRespuestas()).isEmpty();

        verify(comentarioService, times(1)).obtenerPorId(comentarioId);
    }

    // =========================================================================
    // TESTS PARA LISTAR POR ALOJAMIENTO
    // =========================================================================

    @Test
    @DisplayName("Listar comentarios por alojamiento - Debería retornar paginación")
    void listarPorAlojamiento_DeberiaRetornarPaginacion() {
        // Arrange
        Integer alojamientoId = 10;
        int pagina = 0;
        int tamano = 20;
        when(comentarioService.listarPorAlojamiento(alojamientoId, pagina, tamano)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<ComentarioDTO>> response =
                comentarioController.listarPorAlojamiento(alojamientoId, pagina, tamano);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(paginacionResponse);
        assertThat(response.getBody().getContenido()).hasSize(1);
        assertThat(response.getBody().getContenido().get(0).getAlojamientoId()).isEqualTo(10L);

        verify(comentarioService, times(1)).listarPorAlojamiento(alojamientoId, pagina, tamano);
    }

    @Test
    @DisplayName("Listar comentarios por alojamiento - Parámetros por defecto")
    void listarPorAlojamiento_ParametrosPorDefecto_DeberiaUsarValoresPorDefecto() {
        // Arrange
        Integer alojamientoId = 10;
        when(comentarioService.listarPorAlojamiento(alojamientoId, 0, 20)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<ComentarioDTO>> response =
                comentarioController.listarPorAlojamiento(alojamientoId, 0, 20);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(comentarioService, times(1)).listarPorAlojamiento(alojamientoId, 0, 20);
    }

    // =========================================================================
    // TESTS PARA MIS COMENTARIOS
    // =========================================================================

    @Test
    @DisplayName("Mis comentarios - Debería retornar lista de comentarios del usuario")
    void misComentarios_DeberiaRetornarLista() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));

            List<ComentarioDTO> comentarios = Arrays.asList(comentarioDTO);
            when(comentarioService.listarPorUsuario(15)).thenReturn(comentarios);

            // Act
            ResponseEntity<List<ComentarioDTO>> response = comentarioController.misComentarios();

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(comentarios);
            assertThat(response.getBody()).hasSize(1);
            assertThat(response.getBody().get(0).getUsuarioId()).isEqualTo(15L);

            verify(comentarioService, times(1)).listarPorUsuario(15);
        }
    }

    // =========================================================================
    // TESTS PARA LISTAR POR ANFITRIÓN
    // =========================================================================

    @Test
    @DisplayName("Listar comentarios por anfitrión - Debería retornar paginación")
    void listarPorAnfitrion_DeberiaRetornarPaginacion() {
        // Arrange
        Integer anfitrionId = 7;
        int pagina = 0;
        int tamano = 20;
        when(comentarioService.listarPorAnfitrion(anfitrionId, pagina, tamano)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<ComentarioDTO>> response =
                comentarioController.listarPorAnfitrion(anfitrionId, pagina, tamano);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(paginacionResponse);
        assertThat(response.getBody().getContenido().get(0).getAlojamientoId()).isEqualTo(10L);

        verify(comentarioService, times(1)).listarPorAnfitrion(anfitrionId, pagina, tamano);
    }

    // =========================================================================
    // TESTS PARA COMENTARIOS SIN RESPUESTA
    // =========================================================================

    @Test
    @DisplayName("Listar comentarios sin respuesta - Debería retornar lista")
    void listarSinRespuesta_DeberiaRetornarLista() {
        // Arrange
        Integer anfitrionId = 7;
        List<ComentarioDTO> comentariosSinRespuesta = Arrays.asList(comentarioDTO);
        when(comentarioService.listarSinRespuestaPorAnfitrion(anfitrionId)).thenReturn(comentariosSinRespuesta);

        // Act
        ResponseEntity<List<ComentarioDTO>> response =
                comentarioController.listarSinRespuesta(anfitrionId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(comentariosSinRespuesta);
        assertThat(response.getBody()).hasSize(1);

        verify(comentarioService, times(1)).listarSinRespuestaPorAnfitrion(anfitrionId);
    }

    // =========================================================================
    // TESTS PARA MÉTRICAS DE ALOJAMIENTO
    // =========================================================================

    @Test
    @DisplayName("Stats alojamiento - Con datos existentes debería retornar métricas")
    void statsAlojamiento_ConDatos_DeberiaRetornarMetricas() {
        // Arrange
        Integer alojamientoId = 10;
        Double promedio = 4.5;
        Long total = 10L;

        when(comentarioService.calificacionPromedio(alojamientoId)).thenReturn(promedio);
        when(comentarioService.contarPorAlojamiento(alojamientoId)).thenReturn(total);

        // Act
        ResponseEntity<Map<String, Object>> response = comentarioController.statsAlojamiento(alojamientoId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("alojamientoId")).isEqualTo(10);
        assertThat(response.getBody().get("promedio")).isEqualTo(4.5);
        assertThat(response.getBody().get("total")).isEqualTo(10L);

        verify(comentarioService, times(1)).calificacionPromedio(alojamientoId);
        verify(comentarioService, times(1)).contarPorAlojamiento(alojamientoId);
    }

    @Test
    @DisplayName("Stats alojamiento - Sin datos debería retornar valores por defecto")
    void statsAlojamiento_SinDatos_DeberiaRetornarValoresPorDefecto() {
        // Arrange
        Integer alojamientoId = 10;

        when(comentarioService.calificacionPromedio(alojamientoId)).thenReturn(null);
        when(comentarioService.contarPorAlojamiento(alojamientoId)).thenReturn(null);

        // Act
        ResponseEntity<Map<String, Object>> response = comentarioController.statsAlojamiento(alojamientoId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("alojamientoId")).isEqualTo(10);
        assertThat(response.getBody().get("promedio")).isEqualTo(0.0);
        assertThat(response.getBody().get("total")).isEqualTo(0L);
    }

    // =========================================================================
    // TESTS PARA MEJORES COMENTARIOS
    // =========================================================================

    @Test
    @DisplayName("Top comentarios - Debería retornar mejores comentarios")
    void topAlojamiento_DeberiaRetornarMejoresComentarios() {
        // Arrange
        Integer alojamientoId = 10;
        List<ComentarioDTO> mejoresComentarios = Arrays.asList(comentarioDTO);
        when(comentarioService.mejoresComentarios(alojamientoId)).thenReturn(mejoresComentarios);

        // Act
        ResponseEntity<List<ComentarioDTO>> response = comentarioController.topAlojamiento(alojamientoId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mejoresComentarios);
        assertThat(response.getBody()).hasSize(1);

        verify(comentarioService, times(1)).mejoresComentarios(alojamientoId);
    }

    // =========================================================================
    // TESTS PARA ACTUALIZAR COMENTARIO
    // =========================================================================

    @Test
    @DisplayName("Actualizar comentario - Debería retornar 200 OK")
    void actualizar_DeberiaRetornar200Ok() {
        // Arrange
        Integer comentarioId = 100;
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));
            when(comentarioService.actualizar(15, comentarioId, actualizarRequest)).thenReturn(comentarioDTO);

            // Act
            ResponseEntity<ComentarioDTO> response = comentarioController.actualizar(comentarioId, actualizarRequest);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(comentarioDTO);
            assertThat(response.getBody().getCalificacion()).isEqualTo(5);

            verify(comentarioService, times(1)).actualizar(15, comentarioId, actualizarRequest);
        }
    }

    @Test
    @DisplayName("Actualizar comentario - Campos del request tienen valores correctos")
    void actualizar_CamposRequest_TienenValoresCorrectos() {
        // Arrange
        Integer comentarioId = 100;
        ActualizarComentarioRequest request = ActualizarComentarioRequest.builder()
                .calificacion(3)
                .comentarioTexto("Actualización del comentario")
                .build();

        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));
            when(comentarioService.actualizar(15, comentarioId, request)).thenReturn(comentarioDTO);

            // Act
            ResponseEntity<ComentarioDTO> response = comentarioController.actualizar(comentarioId, request);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(comentarioService, times(1)).actualizar(15, comentarioId, request);
        }
    }

    // =========================================================================
    // TESTS PARA ELIMINAR COMENTARIO
    // =========================================================================

    @Test
    @DisplayName("Eliminar comentario - Debería retornar 204 NO_CONTENT")
    void eliminar_DeberiaRetornar204NoContent() {
        // Arrange
        Integer comentarioId = 100;
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));

            // Act
            ResponseEntity<Void> response = comentarioController.eliminar(comentarioId);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
            assertThat(response.getBody()).isNull();

            verify(comentarioService, times(1)).eliminar(15, comentarioId, false);
        }
    }

    // =========================================================================
    // TESTS PARA REPORTAR COMENTARIO
    // =========================================================================

    @Test
    @DisplayName("Reportar comentario - Debería retornar 501 NOT_IMPLEMENTED")
    void reportar_DeberiaRetornar501NotImplemented() {
        // Arrange
        Integer comentarioId = 100;
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));

            // Act
            ResponseEntity<?> response = comentarioController.reportar(comentarioId, reportarRequest);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_IMPLEMENTED);
            assertThat(response.getBody()).isInstanceOf(Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            assertThat(body.get("message")).isEqualTo("Reporte de comentario pendiente de implementación");

            verify(comentarioService, times(1)).reportar(15, comentarioId, reportarRequest);
        }
    }

    @Test
    @DisplayName("Reportar comentario - Campos del request tienen valores correctos")
    void reportar_CamposRequest_TienenValoresCorrectos() {
        // Arrange
        Integer comentarioId = 100;
        ReportarComentarioRequest request = ReportarComentarioRequest.builder()
                .motivo("Lenguaje ofensivo")
                .build();

        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));

            // Act
            ResponseEntity<?> response = comentarioController.reportar(comentarioId, request);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_IMPLEMENTED);
            verify(comentarioService, times(1)).reportar(15, comentarioId, request);
        }
    }

    // =========================================================================
    // TESTS DE CASOS BORDE Y VALIDACIONES
    // =========================================================================

    @Test
    @DisplayName("CurrentUserId - Usuario no encontrado en BD debería lanzar excepción")
    void currentUserId_UsuarioNoEncontradoEnBD_DeberiaLanzarExcepcion() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("noexiste@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("noexiste@correo.com")).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> comentarioController.misComentarios())
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario no encontrado por email");
        }
    }

    @Test
    @DisplayName("ComentarioDTO - Campos completos tienen valores correctos")
    void comentarioDTO_CamposCompletos_TienenValoresCorrectos() {
        // Arrange & Act
        ComentarioDTO dtoCompleto = ComentarioDTO.builder()
                .id(200)
                .reservaId(50L)
                .usuarioId(25L)
                .usuarioNombre("María López")
                .alojamientoId(30L)
                .calificacion(4)
                .comentarioTexto("Muy buena ubicación y comodidad")
                .fechaComentario(LocalDateTime.of(2025, 1, 15, 10, 30))
                .respuestas(Collections.emptyList())
                .build();

        // Assert
        assertThat(dtoCompleto.getId()).isEqualTo(200);
        assertThat(dtoCompleto.getReservaId()).isEqualTo(50L);
        assertThat(dtoCompleto.getUsuarioId()).isEqualTo(25L);
        assertThat(dtoCompleto.getUsuarioNombre()).isEqualTo("María López");
        assertThat(dtoCompleto.getAlojamientoId()).isEqualTo(30L);
        assertThat(dtoCompleto.getCalificacion()).isEqualTo(4);
        assertThat(dtoCompleto.getComentarioTexto()).isEqualTo("Muy buena ubicación y comodidad");
        assertThat(dtoCompleto.getFechaComentario()).isEqualTo(LocalDateTime.of(2025, 1, 15, 10, 30));
        assertThat(dtoCompleto.getRespuestas()).isEmpty();
    }

    @Test
    @DisplayName("Listar con diferentes parámetros de paginación")
    void listarConDiferentesParametrosPaginacion_DeberiaPasarParametrosCorrectos() {
        // Arrange
        Integer alojamientoId = 10;
        int pagina = 2;
        int tamano = 50;
        when(comentarioService.listarPorAlojamiento(alojamientoId, pagina, tamano)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<ComentarioDTO>> response =
                comentarioController.listarPorAlojamiento(alojamientoId, pagina, tamano);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(comentarioService, times(1)).listarPorAlojamiento(alojamientoId, pagina, tamano);
    }

    @Test
    @DisplayName("Múltiples endpoints autenticados - Verificar consistencia")
    void multiplesEndpointsAutenticados_DeberianUsarMismoUsuario() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));

            when(comentarioService.listarPorUsuario(15)).thenReturn(Arrays.asList(comentarioDTO));
            when(comentarioService.crear(15, crearRequest)).thenReturn(comentarioDTO);

            // Act
            comentarioController.misComentarios();
            comentarioController.crear(crearRequest);

            // Assert
            verify(comentarioService, times(1)).listarPorUsuario(15);
            verify(comentarioService, times(1)).crear(15, crearRequest);
            verify(usuarioRepository, times(2)).findByEmailIgnoreCase("juan@correo.com");
        }
    }

    @Test
    @DisplayName("Request DTOs - Builders funcionan correctamente")
    void requestDTOs_Builders_FuncionanCorrectamente() {
        // Arrange & Act
        CrearComentarioRequest crear = CrearComentarioRequest.builder()
                .reservaId(100)
                .calificacion(5)
                .comentarioTexto("Excelente")
                .build();

        ActualizarComentarioRequest actualizar = ActualizarComentarioRequest.builder()
                .calificacion(4)
                .comentarioTexto("Muy bueno")
                .build();

        ReportarComentarioRequest reportar = ReportarComentarioRequest.builder()
                .motivo("Contenido inapropiado")
                .build();

        // Assert
        assertThat(crear.getReservaId()).isEqualTo(100);
        assertThat(crear.getCalificacion()).isEqualTo(5);
        assertThat(crear.getComentarioTexto()).isEqualTo("Excelente");

        assertThat(actualizar.getCalificacion()).isEqualTo(4);
        assertThat(actualizar.getComentarioTexto()).isEqualTo("Muy bueno");

        assertThat(reportar.getMotivo()).isEqualTo("Contenido inapropiado");
    }
}