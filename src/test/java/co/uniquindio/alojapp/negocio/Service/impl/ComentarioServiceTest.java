package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.ComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ReportarComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.excepciones.AccesoNoAutorizadoException;
import co.uniquindio.alojapp.negocio.excepciones.ReglaNegocioException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.ComentarioDAO;
import co.uniquindio.alojapp.persistencia.Entity.Comentario;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.ComentarioMapper;
import co.uniquindio.alojapp.persistencia.Repository.ComentarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para ComentarioServiceIMPL
 *
 * OBJETIVO: Probar la lógica de negocio del servicio de comentarios de forma aislada
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ComentarioService - Unit Tests")
public class ComentarioServiceTest {

    @Mock
    private ComentarioDAO comentarioDAO;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private ComentarioMapper comentarioMapper;

    @InjectMocks
    private ComentarioServiceIMPL comentarioService;

    // DATOS DE PRUEBA
    private CrearComentarioRequest crearRequestValido;
    private ActualizarComentarioRequest actualizarRequestValido;
    private ReportarComentarioRequest reportarRequestValido;
    private Comentario comentarioMock;
    private ComentarioDTO comentarioDTOMock;
    private Usuario usuarioMock;

    private final Integer USUARIO_ID_VALIDO = 1;
    private final Integer USUARIO_ID_NO_AUTOR = 2;
    private final Integer COMENTARIO_ID_VALIDO = 1;
    private final Integer COMENTARIO_ID_INEXISTENTE = 999;
    private final Integer ALOJAMIENTO_ID_VALIDO = 1;
    private final Integer ANFITRION_ID_VALIDO = 3;
    private final Integer RESERVA_ID_VALIDO = 1;

    @BeforeEach
    void setUp() {
        // Configurar usuario mock
        usuarioMock = Usuario.builder()
                .id(USUARIO_ID_VALIDO)
                .nombre("Usuario Test")
                .build();

        // Configurar comentario mock
        comentarioMock = Comentario.builder()
                .id(COMENTARIO_ID_VALIDO)
                .comentarioTexto("Excelente alojamiento, muy limpio y cómodo")
                .calificacion(5)
                .fechaComentario(LocalDateTime.now().minusDays(1))
                .autor(usuarioMock)
                .build();

        // Configurar DTO mock - CORREGIDO para coincidir con ComentarioDTO real
        comentarioDTOMock = ComentarioDTO.builder()
                .id(COMENTARIO_ID_VALIDO)
                .comentarioTexto("Excelente alojamiento, muy limpio y cómodo")
                .calificacion(5)
                .fechaComentario(LocalDateTime.now().minusDays(1))
                .usuarioId(USUARIO_ID_VALIDO.longValue()) // CORREGIDO: usuarioId es Long
                .usuarioNombre("Usuario Test") // CORREGIDO: usuarioNombre en lugar de autorNombre
                .reservaId(RESERVA_ID_VALIDO.longValue()) // Añadido
                .alojamientoId(ALOJAMIENTO_ID_VALIDO.longValue()) // Añadido
                .respuestas(null) // Añadido
                .build();

        // Configurar requests válidos
        crearRequestValido = CrearComentarioRequest.builder()
                .reservaId(RESERVA_ID_VALIDO)
                .comentarioTexto("Excelente alojamiento, muy limpio y cómodo")
                .calificacion(5)
                .build();

        actualizarRequestValido = ActualizarComentarioRequest.builder()
                .comentarioTexto("Comentario actualizado - aún mejor de lo esperado")
                .calificacion(4)
                .build();

        reportarRequestValido = ReportarComentarioRequest.builder()
                .motivo("Contenido inapropiado")
                .build();
    }

    // ==================== CREAR COMENTARIO TESTS ====================

    @Test
    @DisplayName("CREAR - Datos válidos crea comentario exitosamente")
    void crear_DatosValidos_CreaComentarioExitosamente() {
        // ARRANGE
        when(comentarioDAO.save(any(CrearComentarioRequest.class), eq(USUARIO_ID_VALIDO)))
                .thenReturn(comentarioDTOMock);

        // ACT
        ComentarioDTO resultado = comentarioService.crear(USUARIO_ID_VALIDO, crearRequestValido);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(COMENTARIO_ID_VALIDO);
        assertThat(resultado.getComentarioTexto()).isEqualTo(crearRequestValido.getComentarioTexto());

        verify(comentarioDAO, times(1)).save(crearRequestValido, USUARIO_ID_VALIDO);
    }

    // ==================== OBTENER POR ID TESTS ====================

    @Test
    @DisplayName("OBTENER POR ID - Comentario existe retorna DTO")
    void obtenerPorId_ComentarioExiste_RetornaDTO() {
        // ARRANGE
        when(comentarioDAO.findById(COMENTARIO_ID_VALIDO)).thenReturn(Optional.of(comentarioDTOMock));

        // ACT
        ComentarioDTO resultado = comentarioService.obtenerPorId(COMENTARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(COMENTARIO_ID_VALIDO);
        verify(comentarioDAO, times(1)).findById(COMENTARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("OBTENER POR ID - Comentario no existe lanza RecursoNoEncontradoException")
    void obtenerPorId_ComentarioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(comentarioDAO.findById(COMENTARIO_ID_INEXISTENTE)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> comentarioService.obtenerPorId(COMENTARIO_ID_INEXISTENTE))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Comentario no encontrado");

        verify(comentarioDAO, times(1)).findById(COMENTARIO_ID_INEXISTENTE);
    }

    // ==================== LISTAR POR ALOJAMIENTO TESTS ====================

    @Test
    @DisplayName("LISTAR POR ALOJAMIENTO - Retorna paginación correcta")
    void listarPorAlojamiento_RetornaPaginacionCorrecta() {
        // ARRANGE
        PaginacionResponse<ComentarioDTO> paginacionMock = PaginacionResponse.<ComentarioDTO>builder()
                .contenido(List.of(comentarioDTOMock))
                .paginaActual(0)
                .tamanoPagina(10)
                .totalElementos(1L)
                .totalPaginas(1)
                .esPrimera(true)
                .esUltima(true)
                .tieneSiguiente(false)
                .tieneAnterior(false)
                .build();

        when(comentarioDAO.findByAlojamiento(ALOJAMIENTO_ID_VALIDO, 0, 10))
                .thenReturn(paginacionMock);

        // ACT
        PaginacionResponse<ComentarioDTO> resultado = comentarioService
                .listarPorAlojamiento(ALOJAMIENTO_ID_VALIDO, 0, 10);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getContenido()).hasSize(1);
        assertThat(resultado.getContenido().get(0).getId()).isEqualTo(COMENTARIO_ID_VALIDO);
        assertThat(resultado.getEsPrimera()).isTrue();
        assertThat(resultado.getEsUltima()).isTrue();
        verify(comentarioDAO, times(1)).findByAlojamiento(ALOJAMIENTO_ID_VALIDO, 0, 10);
    }

    // ==================== LISTAR POR USUARIO TESTS ====================

    @Test
    @DisplayName("LISTAR POR USUARIO - Retorna lista de comentarios")
    void listarPorUsuario_RetornaListaComentarios() {
        // ARRANGE
        List<ComentarioDTO> comentariosMock = List.of(comentarioDTOMock);
        when(comentarioDAO.findByUsuario(USUARIO_ID_VALIDO)).thenReturn(comentariosMock);

        // ACT
        List<ComentarioDTO> resultado = comentarioService.listarPorUsuario(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO.longValue());
        verify(comentarioDAO, times(1)).findByUsuario(USUARIO_ID_VALIDO);
    }

    // ==================== LISTAR POR ANFITRION TESTS ====================

    @Test
    @DisplayName("LISTAR POR ANFITRION - Retorna paginación correcta")
    void listarPorAnfitrion_RetornaPaginacionCorrecta() {
        // ARRANGE
        PaginacionResponse<ComentarioDTO> paginacionMock = PaginacionResponse.<ComentarioDTO>builder()
                .contenido(List.of(comentarioDTOMock))
                .paginaActual(0)
                .tamanoPagina(10)
                .totalElementos(5L)
                .totalPaginas(1)
                .esPrimera(true)
                .esUltima(true)
                .tieneSiguiente(false)
                .tieneAnterior(false)
                .build();

        when(comentarioDAO.findByAnfitrion(ANFITRION_ID_VALIDO, 0, 10))
                .thenReturn(paginacionMock);

        // ACT
        PaginacionResponse<ComentarioDTO> resultado = comentarioService
                .listarPorAnfitrion(ANFITRION_ID_VALIDO, 0, 10);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getContenido()).hasSize(1);
        assertThat(resultado.getTotalElementos()).isEqualTo(5L);
        verify(comentarioDAO, times(1)).findByAnfitrion(ANFITRION_ID_VALIDO, 0, 10);
    }

    // ==================== LISTAR SIN RESPUESTA TESTS ====================

    @Test
    @DisplayName("LISTAR SIN RESPUESTA - Retorna comentarios sin respuesta")
    void listarSinRespuestaPorAnfitrion_RetornaComentariosSinRespuesta() {
        // ARRANGE
        List<ComentarioDTO> comentariosMock = List.of(comentarioDTOMock);
        when(comentarioDAO.findSinRespuestaByAnfitrion(ANFITRION_ID_VALIDO))
                .thenReturn(comentariosMock);

        // ACT
        List<ComentarioDTO> resultado = comentarioService
                .listarSinRespuestaPorAnfitrion(ANFITRION_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        verify(comentarioDAO, times(1)).findSinRespuestaByAnfitrion(ANFITRION_ID_VALIDO);
    }

    // ==================== MÉTRICAS TESTS ====================

    @Test
    @DisplayName("CALIFICACION PROMEDIO - Calcula promedio correctamente")
    void calificacionPromedio_CalculaPromedioCorrectamente() {
        // ARRANGE
        Double promedioEsperado = 4.5;
        when(comentarioDAO.calcularCalificacionPromedio(ALOJAMIENTO_ID_VALIDO))
                .thenReturn(promedioEsperado);

        // ACT
        Double resultado = comentarioService.calificacionPromedio(ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isEqualTo(promedioEsperado);
        verify(comentarioDAO, times(1)).calcularCalificacionPromedio(ALOJAMIENTO_ID_VALIDO);
    }

    @Test
    @DisplayName("CONTAR POR ALOJAMIENTO - Retorna conteo correcto")
    void contarPorAlojamiento_RetornaConteoCorrecto() {
        // ARRANGE
        Long conteoEsperado = 10L;
        when(comentarioDAO.countByAlojamiento(ALOJAMIENTO_ID_VALIDO))
                .thenReturn(conteoEsperado);

        // ACT
        Long resultado = comentarioService.contarPorAlojamiento(ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isEqualTo(conteoEsperado);
        verify(comentarioDAO, times(1)).countByAlojamiento(ALOJAMIENTO_ID_VALIDO);
    }

    @Test
    @DisplayName("MEJORES COMENTARIOS - Retorna lista de mejores comentarios")
    void mejoresComentarios_RetornaListaMejoresComentarios() {
        // ARRANGE
        List<ComentarioDTO> mejoresComentariosMock = List.of(comentarioDTOMock);
        when(comentarioDAO.findMejoresComentarios(ALOJAMIENTO_ID_VALIDO))
                .thenReturn(mejoresComentariosMock);

        // ACT
        List<ComentarioDTO> resultado = comentarioService.mejoresComentarios(ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        verify(comentarioDAO, times(1)).findMejoresComentarios(ALOJAMIENTO_ID_VALIDO);
    }

    // ==================== ACTUALIZAR COMENTARIO TESTS ====================

    @Test
    @DisplayName("ACTUALIZAR - Autor actualiza comentario exitosamente")
    void actualizar_AutorActualizaComentario_Exitosamente() {
        // ARRANGE
        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));
        when(comentarioRepository.save(any(Comentario.class)))
                .thenReturn(comentarioMock);
        when(comentarioMapper.toDTO(comentarioMock))
                .thenReturn(comentarioDTOMock);

        // ACT
        ComentarioDTO resultado = comentarioService.actualizar(
                USUARIO_ID_VALIDO, COMENTARIO_ID_VALIDO, actualizarRequestValido);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_VALIDO);
        verify(comentarioRepository, times(1)).save(comentarioMock);
    }

    @Test
    @DisplayName("ACTUALIZAR - Usuario no autor lanza AccesoNoAutorizadoException")
    void actualizar_UsuarioNoAutor_LanzaExcepcion() {
        // ARRANGE
        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> comentarioService.actualizar(
                USUARIO_ID_NO_AUTOR, COMENTARIO_ID_VALIDO, actualizarRequestValido))
                .isInstanceOf(AccesoNoAutorizadoException.class)
                .hasMessageContaining("No estás autorizado a modificar este comentario");

        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_VALIDO);
        verify(comentarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("ACTUALIZAR - Calificación inválida lanza ReglaNegocioException")
    void actualizar_CalificacionInvalida_LanzaExcepcion() {
        // ARRANGE
        ActualizarComentarioRequest requestInvalido = ActualizarComentarioRequest.builder()
                .calificacion(6) // Calificación fuera de rango
                .build();

        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> comentarioService.actualizar(
                USUARIO_ID_VALIDO, COMENTARIO_ID_VALIDO, requestInvalido))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("La calificación debe estar entre 1 y 5");

        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_VALIDO);
        verify(comentarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("ACTUALIZAR - Comentario muy largo lanza ReglaNegocioException")
    void actualizar_ComentarioMuyLargo_LanzaExcepcion() {
        // ARRANGE
        String comentarioLargo = "a".repeat(501);
        ActualizarComentarioRequest requestInvalido = ActualizarComentarioRequest.builder()
                .comentarioTexto(comentarioLargo)
                .build();

        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> comentarioService.actualizar(
                USUARIO_ID_VALIDO, COMENTARIO_ID_VALIDO, requestInvalido))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("El comentario no puede exceder 500 caracteres");

        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_VALIDO);
        verify(comentarioRepository, never()).save(any());
    }

    // ==================== ELIMINAR COMENTARIO TESTS ====================

    @Test
    @DisplayName("ELIMINAR - Autor elimina comentario exitosamente")
    void eliminar_AutorEliminaComentario_Exitosamente() {
        // ARRANGE
        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));

        // ACT & ASSERT
        assertThatCode(() -> comentarioService.eliminar(USUARIO_ID_VALIDO, COMENTARIO_ID_VALIDO, false))
                .doesNotThrowAnyException();

        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_VALIDO);
        verify(comentarioRepository, times(1)).delete(comentarioMock);
    }

    @Test
    @DisplayName("ELIMINAR - Admin elimina comentario con override")
    void eliminar_AdminEliminaComentario_ConOverride() {
        // ARRANGE
        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));

        // ACT & ASSERT
        assertThatCode(() -> comentarioService.eliminar(USUARIO_ID_NO_AUTOR, COMENTARIO_ID_VALIDO, true))
                .doesNotThrowAnyException();

        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_VALIDO);
        verify(comentarioRepository, times(1)).delete(comentarioMock);
    }

    @Test
    @DisplayName("ELIMINAR - Usuario no autor sin override lanza excepción")
    void eliminar_UsuarioNoAutorSinOverride_LanzaExcepcion() {
        // ARRANGE
        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> comentarioService.eliminar(USUARIO_ID_NO_AUTOR, COMENTARIO_ID_VALIDO, false))
                .isInstanceOf(AccesoNoAutorizadoException.class)
                .hasMessageContaining("No estás autorizado a eliminar este comentario");

        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_VALIDO);
        verify(comentarioRepository, never()).delete(any());
    }

    @Test
    @DisplayName("ELIMINAR - Comentario no existe lanza RecursoNoEncontradoException")
    void eliminar_ComentarioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(comentarioDAO.findEntityById(COMENTARIO_ID_INEXISTENTE))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> comentarioService.eliminar(USUARIO_ID_VALIDO, COMENTARIO_ID_INEXISTENTE, false))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Comentario no encontrado");

        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_INEXISTENTE);
        verify(comentarioRepository, never()).delete(any());
    }

    // ==================== REPORTAR COMENTARIO TESTS ====================

    @Test
    @DisplayName("REPORTAR - Comentario existe registra reporte")
    void reportar_ComentarioExiste_RegistraReporte() {
        // ARRANGE
        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));

        // ACT & ASSERT
        assertThatCode(() -> comentarioService.reportar(
                USUARIO_ID_VALIDO, COMENTARIO_ID_VALIDO, reportarRequestValido))
                .doesNotThrowAnyException();

        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("REPORTAR - Comentario no existe lanza RecursoNoEncontradoException")
    void reportar_ComentarioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(comentarioDAO.findEntityById(COMENTARIO_ID_INEXISTENTE))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> comentarioService.reportar(
                USUARIO_ID_VALIDO, COMENTARIO_ID_INEXISTENTE, reportarRequestValido))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Comentario no encontrado");

        verify(comentarioDAO, times(1)).findEntityById(COMENTARIO_ID_INEXISTENTE);
    }

    // ==================== CASOS BORDE Y VALIDACIONES TESTS ====================

    @Test
    @DisplayName("ACTUALIZAR - Calificación mínima válida (1)")
    void actualizar_CalificacionMinimaValida_Exitosamente() {
        // ARRANGE
        ActualizarComentarioRequest requestCalificacionMinima = ActualizarComentarioRequest.builder()
                .calificacion(1)
                .build();

        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));
        when(comentarioRepository.save(any(Comentario.class)))
                .thenReturn(comentarioMock);
        when(comentarioMapper.toDTO(comentarioMock))
                .thenReturn(comentarioDTOMock);

        // ACT & ASSERT
        assertThatCode(() -> comentarioService.actualizar(
                USUARIO_ID_VALIDO, COMENTARIO_ID_VALIDO, requestCalificacionMinima))
                .doesNotThrowAnyException();

        verify(comentarioRepository, times(1)).save(comentarioMock);
    }

    @Test
    @DisplayName("ACTUALIZAR - Calificación máxima válida (5)")
    void actualizar_CalificacionMaximaValida_Exitosamente() {
        // ARRANGE
        ActualizarComentarioRequest requestCalificacionMaxima = ActualizarComentarioRequest.builder()
                .calificacion(5)
                .build();

        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));
        when(comentarioRepository.save(any(Comentario.class)))
                .thenReturn(comentarioMock);
        when(comentarioMapper.toDTO(comentarioMock))
                .thenReturn(comentarioDTOMock);

        // ACT & ASSERT
        assertThatCode(() -> comentarioService.actualizar(
                USUARIO_ID_VALIDO, COMENTARIO_ID_VALIDO, requestCalificacionMaxima))
                .doesNotThrowAnyException();

        verify(comentarioRepository, times(1)).save(comentarioMock);
    }

    @Test
    @DisplayName("ACTUALIZAR - Comentario en límite de caracteres (500) es válido")
    void actualizar_ComentarioEnLimiteCaracteres_Exitosamente() {
        // ARRANGE
        String comentarioEnLimite = "a".repeat(500);
        ActualizarComentarioRequest requestEnLimite = ActualizarComentarioRequest.builder()
                .comentarioTexto(comentarioEnLimite)
                .build();

        when(comentarioDAO.findEntityById(COMENTARIO_ID_VALIDO))
                .thenReturn(Optional.of(comentarioMock));
        when(comentarioRepository.save(any(Comentario.class)))
                .thenReturn(comentarioMock);
        when(comentarioMapper.toDTO(comentarioMock))
                .thenReturn(comentarioDTOMock);

        // ACT & ASSERT
        assertThatCode(() -> comentarioService.actualizar(
                USUARIO_ID_VALIDO, COMENTARIO_ID_VALIDO, requestEnLimite))
                .doesNotThrowAnyException();

        verify(comentarioRepository, times(1)).save(comentarioMock);
    }

    @Test
    @DisplayName("LISTAR POR ALOJAMIENTO - Página con múltiples elementos")
    void listarPorAlojamiento_PaginaConMultiplesElementos_RetornaPaginacionCompleta() {
        // ARRANGE
        ComentarioDTO otroComentarioDTO = ComentarioDTO.builder()
                .id(2)
                .comentarioTexto("Muy buen servicio")
                .calificacion(4)
                .usuarioId(2L)
                .usuarioNombre("Otro Usuario")
                .reservaId(2L)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO.longValue())
                .fechaComentario(LocalDateTime.now().minusDays(2))
                .respuestas(null)
                .build();

        PaginacionResponse<ComentarioDTO> paginacionMock = PaginacionResponse.<ComentarioDTO>builder()
                .contenido(List.of(comentarioDTOMock, otroComentarioDTO))
                .paginaActual(0)
                .tamanoPagina(10)
                .totalElementos(15L)
                .totalPaginas(2)
                .esPrimera(true)
                .esUltima(false)
                .tieneSiguiente(true)
                .tieneAnterior(false)
                .build();

        when(comentarioDAO.findByAlojamiento(ALOJAMIENTO_ID_VALIDO, 0, 10))
                .thenReturn(paginacionMock);

        // ACT
        PaginacionResponse<ComentarioDTO> resultado = comentarioService
                .listarPorAlojamiento(ALOJAMIENTO_ID_VALIDO, 0, 10);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getContenido()).hasSize(2);
        assertThat(resultado.getTotalElementos()).isEqualTo(15L);
        assertThat(resultado.getTotalPaginas()).isEqualTo(2);
        assertThat(resultado.getTieneSiguiente()).isTrue();
        verify(comentarioDAO, times(1)).findByAlojamiento(ALOJAMIENTO_ID_VALIDO, 0, 10);
    }
}