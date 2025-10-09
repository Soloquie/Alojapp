package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import java.time.LocalDateTime;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import co.uniquindio.alojapp.negocio.Service.impl.AnfitrionServiceIMPL;
import co.uniquindio.alojapp.persistencia.DAO.UsuarioDAO;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.AnfitrionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para AnfitrionServiceIMPL
 *
 * OBJETIVO: Probar la lógica de negocio del servicio de anfitriones de forma aislada
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AnfitrionService - Unit Tests")
public class AnfitrionServiceTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private AnfitrionRepository anfitrionRepository;

    @InjectMocks
    private AnfitrionServiceIMPL anfitrionService;

    // DATOS DE PRUEBA
    private Usuario usuarioMock;
    private UsuarioDTO usuarioDTOMock;
    private Anfitrion anfitrionMock;

    private final Integer USUARIO_ID_VALIDO = 1;
    private final Integer USUARIO_ID_INEXISTENTE = 999;
    private final String DESCRIPCION_VALIDA = "Anfitrión amable con experiencia";
    private final String DOCUMENTOS_URL_VALIDA = "https://example.com/docs/legal.pdf";

    @BeforeEach
    void setUp() {
        usuarioMock = Usuario.builder()
                .id(USUARIO_ID_VALIDO)
                .email("correo@valido.com")
                .contrasenaHash("Password123")
                .nombre("Juan Pérez")
                .telefono("+573001234567")
                .estado(EstadoUsuario.ACTIVO)
                .build();

        anfitrionMock = Anfitrion.builder()
                .id(1)
                .usuario(usuarioMock)
                .descripcionPersonal(DESCRIPCION_VALIDA)
                .documentosLegalesUrl(DOCUMENTOS_URL_VALIDA)
                .verificado(false)
                .fechaRegistroAnfitrion(LocalDateTime.now())
                .build();

        usuarioDTOMock = new UsuarioDTO();
        usuarioDTOMock.setId(USUARIO_ID_VALIDO);
        usuarioDTOMock.setEsAnfitrion(true);
        usuarioDTOMock.setEmail("correo@valido.com");
    }



    // ==================== HABILITAR PERFIL TESTS ====================

    @Test
    @DisplayName("HABILITAR PERFIL - Usuario existe y no es anfitrión crea perfil")
    void habilitarPerfil_UsuarioExisteNoEsAnfitrion_CreaPerfil() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());
        doAnswer(invocation -> null)
                .when(usuarioDAO)
                .crearAnfitrion(anyInt(), anyString(), anyString(), any());
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = anfitrionService.habilitarPerfil(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(resultado.getEsAnfitrion()).isTrue();

        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).crearAnfitrion(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA, null);
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("HABILITAR PERFIL - Usuario existe y ya es anfitrión actualiza perfil")
    void habilitarPerfil_UsuarioYaEsAnfitrion_ActualizaPerfil() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(anfitrionRepository.save(anfitrionMock)).thenReturn(anfitrionMock);
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = anfitrionService.habilitarPerfil(USUARIO_ID_VALIDO, "Nueva descripción", "nueva_url.pdf");

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(anfitrionRepository, times(1)).save(anfitrionMock);
        verify(usuarioDAO, never()).crearAnfitrion(anyInt(), anyString(), anyString(), any());
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("HABILITAR PERFIL - Usuario no existe lanza RuntimeException")
    void habilitarPerfil_UsuarioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_INEXISTENTE)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> anfitrionService.habilitarPerfil(USUARIO_ID_INEXISTENTE, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");

        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_INEXISTENTE);
        verify(anfitrionRepository, never()).findByUsuarioId(anyInt());
        verify(usuarioDAO, never()).crearAnfitrion(anyInt(), anyString(), anyString(), any());
    }

    @Test
    @DisplayName("HABILITAR PERFIL - No puede leer usuario después de habilitar lanza RuntimeException")
    void habilitarPerfil_NoPuedeLeerUsuarioDespuesHabilitar_LanzaExcepcion() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());
        doAnswer(invocation -> null)
                .when(usuarioDAO)
                .crearAnfitrion(anyInt(), anyString(), anyString(), any());
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> anfitrionService.habilitarPerfil(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se pudo leer el usuario luego de habilitar perfil");

        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).crearAnfitrion(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA, null);
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
    }

    // ==================== ACTUALIZAR PERFIL TESTS ====================

    @Test
    @DisplayName("ACTUALIZAR PERFIL - Datos válidos actualiza perfil existente")
    void actualizarPerfil_DatosValidos_ActualizaPerfil() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(anfitrionRepository.save(anfitrionMock)).thenReturn(anfitrionMock);
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = anfitrionService.actualizarPerfil(USUARIO_ID_VALIDO, "Descripción actualizada", "nuevo_documento.pdf");

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getEsAnfitrion()).isTrue();

        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(anfitrionRepository, times(1)).save(anfitrionMock);
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("ACTUALIZAR PERFIL - Usuario no es anfitrión lanza IllegalArgumentException")
    void actualizarPerfil_UsuarioNoEsAnfitrion_LanzaExcepcion() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> anfitrionService.actualizarPerfil(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario no tiene perfil de anfitrión");

        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(anfitrionRepository, never()).save(any(Anfitrion.class));
        verify(usuarioDAO, never()).findById(anyInt());
    }

    @Test
    @DisplayName("ACTUALIZAR PERFIL - Campos null no actualizan valores existentes")
    void actualizarPerfil_CamposNull_NoActualizaValoresExistentes() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(anfitrionRepository.save(anfitrionMock)).thenReturn(anfitrionMock);
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = anfitrionService.actualizarPerfil(USUARIO_ID_VALIDO, null, null);

        // ASSERT
        assertThat(resultado).isNotNull();
        // Verificar que los valores originales se mantienen
        assertThat(anfitrionMock.getDescripcionPersonal()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(anfitrionMock.getDocumentosLegalesUrl()).isEqualTo(DOCUMENTOS_URL_VALIDA);

        verify(anfitrionRepository, times(1)).save(anfitrionMock);
    }

    // ==================== DESHABILITAR PERFIL TESTS ====================

    @Test
    @DisplayName("DESHABILITAR PERFIL - Usuario existe elimina perfil anfitrión")
    void deshabilitarPerfil_UsuarioExiste_EliminaPerfil() {
        // ARRANGE
        doAnswer(invocation -> null)
                .when(usuarioDAO)
                .eliminarAnfitrion(anyInt());
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = anfitrionService.deshabilitarPerfil(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(usuarioDAO, times(1)).eliminarAnfitrion(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("DESHABILITAR PERFIL - No puede leer usuario después de deshabilitar lanza RuntimeException")
    void deshabilitarPerfil_NoPuedeLeerUsuarioDespuesDeshabilitar_LanzaExcepcion() {
        // ARRANGE
        doAnswer(invocation -> null)
                .when(usuarioDAO)
                .eliminarAnfitrion(anyInt());
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> anfitrionService.deshabilitarPerfil(USUARIO_ID_VALIDO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se pudo leer el usuario luego de deshabilitar perfil");

        verify(usuarioDAO, times(1)).eliminarAnfitrion(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
    }

    // ==================== MARCAR VERIFICADO TESTS ====================

    @Test
    @DisplayName("MARCAR VERIFICADO - Admin override true marca como verificado")
    void marcarVerificado_AdminOverrideTrue_MarcaComoVerificado() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(anfitrionRepository.save(anfitrionMock)).thenReturn(anfitrionMock);
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = anfitrionService.marcarVerificado(USUARIO_ID_VALIDO, true, true);

        // ASSERT
        assertThat(resultado).isNotNull();
        // Usar el getter generado por Lombok para Boolean
        assertThat(anfitrionMock.getVerificado()).isTrue();

        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(anfitrionRepository, times(1)).save(anfitrionMock);
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
    }
    @Test
    @DisplayName("MARCAR VERIFICADO - Admin override false lanza IllegalArgumentException")
    void marcarVerificado_AdminOverrideFalse_LanzaExcepcion() {
        // ACT & ASSERT
        assertThatThrownBy(() -> anfitrionService.marcarVerificado(USUARIO_ID_VALIDO, true, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No autorizado: se requiere privilegio de administrador");

        verify(anfitrionRepository, never()).findByUsuarioId(anyInt());
        verify(anfitrionRepository, never()).save(any(Anfitrion.class));
        verify(usuarioDAO, never()).findById(anyInt());
    }

    @Test
    @DisplayName("MARCAR VERIFICADO - Usuario no es anfitrión lanza IllegalArgumentException")
    void marcarVerificado_UsuarioNoEsAnfitrion_LanzaExcepcion() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> anfitrionService.marcarVerificado(USUARIO_ID_VALIDO, true, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario no tiene perfil de anfitrión");

        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(anfitrionRepository, never()).save(any(Anfitrion.class));
        verify(usuarioDAO, never()).findById(anyInt());
    }

    @Test
    @DisplayName("MARCAR VERIFICADO - Marcar como no verificado actualiza correctamente")
    void marcarVerificado_MarcarComoNoVerificado_ActualizaCorrectamente() {
        // ARRANGE
        anfitrionMock.setVerificado(true); // Inicialmente verificado
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(anfitrionRepository.save(anfitrionMock)).thenReturn(anfitrionMock);
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = anfitrionService.marcarVerificado(USUARIO_ID_VALIDO, false, true);

        // ASSERT
        assertThat(resultado).isNotNull();
        // Usar el getter generado por Lombok para Boolean
        assertThat(anfitrionMock.getVerificado()).isFalse();

        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(anfitrionRepository, times(1)).save(anfitrionMock);
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
    }

    // ==================== ES ANFITRION TESTS ====================

    @Test
    @DisplayName("ES ANFITRION - Usuario es anfitrión retorna true")
    void esAnfitrion_UsuarioEsAnfitrion_RetornaTrue() {
        // ARRANGE
        when(usuarioDAO.esAnfitrion(USUARIO_ID_VALIDO)).thenReturn(true);

        // ACT
        boolean resultado = anfitrionService.esAnfitrion(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isTrue();
        verify(usuarioDAO, times(1)).esAnfitrion(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("ES ANFITRION - Usuario no es anfitrión retorna false")
    void esAnfitrion_UsuarioNoEsAnfitrion_RetornaFalse() {
        // ARRANGE
        when(usuarioDAO.esAnfitrion(USUARIO_ID_VALIDO)).thenReturn(false);

        // ACT
        boolean resultado = anfitrionService.esAnfitrion(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isFalse();
        verify(usuarioDAO, times(1)).esAnfitrion(USUARIO_ID_VALIDO);
    }

    // ==================== CREAR PERFIL TESTS ====================

    @Test
    @DisplayName("CREAR PERFIL - Datos válidos crea perfil anfitrión")
    void crearPerfil_DatosValidos_CreaPerfilAnfitrion() {
        // ARRANGE
        LocalDate fechaRegistro = LocalDate.now();
        doAnswer(invocation -> null)
                .when(usuarioDAO)
                .crearAnfitrion(anyInt(), anyString(), anyString(), any());

        // ACT
        anfitrionService.crearPerfil(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA, fechaRegistro);

        // ASSERT
        verify(usuarioDAO, times(1)).crearAnfitrion(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA, fechaRegistro);
    }

    @Test
    @DisplayName("CREAR PERFIL - Fecha registro null se pasa correctamente")
    void crearPerfil_FechaRegistroNull_SePasaCorrectamente() {
        // ARRANGE
        doAnswer(invocation -> null)
                .when(usuarioDAO)
                .crearAnfitrion(anyInt(), anyString(), anyString(), any());

        // ACT
        anfitrionService.crearPerfil(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA, null);

        // ASSERT
        verify(usuarioDAO, times(1)).crearAnfitrion(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA, null);
    }

    // ==================== COMPORTAMIENTO IDEMPOTENTE TESTS ====================

    @Test
    @DisplayName("HABILITAR PERFIL - Llamadas múltiples son idempotentes")
    void habilitarPerfil_LlamadasMultiples_ComportamientoIdempotente() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.empty()) // Primera llamada: no existe
                .thenReturn(Optional.of(anfitrionMock)); // Segunda llamada: ya existe

        doAnswer(invocation -> null)
                .when(usuarioDAO)
                .crearAnfitrion(anyInt(), anyString(), anyString(), any());
        when(anfitrionRepository.save(anfitrionMock)).thenReturn(anfitrionMock);
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT - Primera llamada (crea)
        UsuarioDTO resultado1 = anfitrionService.habilitarPerfil(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA);

        // ACT - Segunda llamada (actualiza)
        UsuarioDTO resultado2 = anfitrionService.habilitarPerfil(USUARIO_ID_VALIDO, "Nueva desc", "nuevo_doc");

        // ASSERT
        assertThat(resultado1).isNotNull();
        assertThat(resultado2).isNotNull();

        // Verificar que la primera llamada crea y la segunda actualiza
        verify(usuarioDAO, times(1)).crearAnfitrion(USUARIO_ID_VALIDO, DESCRIPCION_VALIDA, DOCUMENTOS_URL_VALIDA, null);
        verify(anfitrionRepository, times(1)).save(anfitrionMock);
    }

    @Test
    @DisplayName("DESHABILITAR PERFIL - Llamadas múltiples son idempotentes")
    void deshabilitarPerfil_LlamadasMultiples_ComportamientoIdempotente() {
        // ARRANGE
        doAnswer(invocation -> null)
                .when(usuarioDAO)
                .eliminarAnfitrion(anyInt());
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT - Llamadas múltiples
        anfitrionService.deshabilitarPerfil(USUARIO_ID_VALIDO);
        anfitrionService.deshabilitarPerfil(USUARIO_ID_VALIDO);
        anfitrionService.deshabilitarPerfil(USUARIO_ID_VALIDO);

        // ASSERT - No importa cuántas veces se llame, siempre funciona igual
        verify(usuarioDAO, times(3)).eliminarAnfitrion(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(3)).findById(USUARIO_ID_VALIDO);
    }
}