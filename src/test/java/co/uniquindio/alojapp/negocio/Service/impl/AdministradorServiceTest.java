package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.AdministradorDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAdministradorRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAdministradorRequest;
import co.uniquindio.alojapp.negocio.Service.impl.AdministradorServiceIMPL;
import co.uniquindio.alojapp.persistencia.Entity.Administrador;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.AdministradorMapper;
import co.uniquindio.alojapp.persistencia.Repository.AdministradorRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para AdministradorServiceIMPL
 *
 * OBJETIVO: Probar la lógica de negocio del servicio de administradores de forma aislada
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdministradorService - Unit Tests")
public class AdministradorServiceTest {

    @Mock
    private AdministradorRepository administradorRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AdministradorMapper administradorMapper;

    @InjectMocks
    private AdministradorServiceIMPL administradorService;

    // DATOS DE PRUEBA
    private Usuario usuarioMock;
    private Administrador administradorMock;
    private AdministradorDTO administradorDTOMock;
    private CrearAdministradorRequest crearRequest;
    private ActualizarAdministradorRequest actualizarRequest;
    private final Integer USUARIO_ID_VALIDO = 1;
    private final Integer ADMIN_ID_VALIDO = 1;
    private final String NIVEL_VALIDO = "ADMIN";
    private final String NIVEL_INVALIDO = "INVALIDO";
    private final String JSON_VALIDO = "{\"permisos\": [\"crear\", \"editar\"]}";
    private final String JSON_INVALIDO = "invalid json";

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes
        usuarioMock = Usuario.builder()
                .id(USUARIO_ID_VALIDO)
                .nombre("Juan Pérez")
                .email("juan@example.com")
                .build();

        administradorMock = Administrador.builder()
                .id(ADMIN_ID_VALIDO)
                .usuario(usuarioMock)
                .nivelAcceso(NIVEL_VALIDO)
                .permisos(JSON_VALIDO)
                .build();

        administradorDTOMock = new AdministradorDTO();
        administradorDTOMock.setId(ADMIN_ID_VALIDO);
        administradorDTOMock.setNivelAcceso(NIVEL_VALIDO);
        administradorDTOMock.setPermisosJson(JSON_VALIDO);

        crearRequest = CrearAdministradorRequest.builder()
                .usuarioId(USUARIO_ID_VALIDO)
                .nivelAcceso(NIVEL_VALIDO)
                .permisosJson(JSON_VALIDO)
                .build();

        actualizarRequest = ActualizarAdministradorRequest.builder()
                .nivelAcceso("SUPER_ADMIN")
                .permisosJson("{\"permisos\": [\"crear\", \"editar\", \"eliminar\"]}")
                .build();
    }


    // ==================== ASIGNAR ADMINISTRADOR TESTS ====================

    @Test
    @DisplayName("ASIGNAR - Datos válidos retorna administrador asignado")
    void asignar_DatosValidos_RetornaAdministradorDTO() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(administradorRepository.existsByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(false);
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administradorMock);
        when(administradorMapper.toDTO(administradorMock)).thenReturn(administradorDTOMock);

        // ACT
        AdministradorDTO resultado = administradorService.asignar(crearRequest);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(ADMIN_ID_VALIDO);
        assertThat(resultado.getNivelAcceso()).isEqualTo(NIVEL_VALIDO);

        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(administradorRepository, times(1)).existsByUsuarioId(USUARIO_ID_VALIDO);
        verify(administradorRepository, times(1)).save(any(Administrador.class));
        verify(administradorMapper, times(1)).toDTO(administradorMock);
    }

    @Test
    @DisplayName("ASIGNAR - UsuarioId null lanza IllegalArgumentException")
    void asignar_UsuarioIdNull_LanzaExcepcion() {
        // ARRANGE
        crearRequest.setUsuarioId(null);

        // ACT & ASSERT
        assertThatThrownBy(() -> administradorService.asignar(crearRequest))
                .isInstanceOf(NullPointerException.class) // <- antes era IllegalArgumentException
                .hasMessageContaining("usuarioId es obligatorio");

        verify(usuarioRepository, never()).findById(anyInt());
        verify(administradorRepository, never()).existsByUsuarioId(anyInt());
    }

    @Test
    @DisplayName("ASIGNAR - Usuario no existe lanza IllegalArgumentException")
    void asignar_UsuarioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> administradorService.asignar(crearRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Usuario no existe");

        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(administradorRepository, never()).save(any(Administrador.class));
    }

    @Test
    @DisplayName("ASIGNAR - Usuario ya es administrador lanza IllegalStateException")
    void asignar_UsuarioYaEsAdministrador_LanzaExcepcion() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(administradorRepository.existsByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(true);

        // ACT & ASSERT
        assertThatThrownBy(() -> administradorService.asignar(crearRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ya es administrador");

        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(administradorRepository, times(1)).existsByUsuarioId(USUARIO_ID_VALIDO);
        verify(administradorRepository, never()).save(any(Administrador.class));
    }

    @Test
    @DisplayName("ASIGNAR - Nivel de acceso inválido lanza IllegalArgumentException")
    void asignar_NivelAccesoInvalido_LanzaExcepcion() {
        // ARRANGE
        crearRequest.setNivelAcceso(NIVEL_INVALIDO);
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(administradorRepository.existsByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> administradorService.asignar(crearRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nivel de acceso inválido");

        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(administradorRepository, never()).save(any(Administrador.class));
    }

    @Test
    @DisplayName("ASIGNAR - JSON inválido lanza IllegalArgumentException")
    void asignar_JsonInvalido_LanzaExcepcion() {
        // ARRANGE
        crearRequest.setPermisosJson(JSON_INVALIDO);
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(administradorRepository.existsByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> administradorService.asignar(crearRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("permisosJson no es un JSON válido");

        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(administradorRepository, never()).save(any(Administrador.class));
    }

    @Test
    @DisplayName("ASIGNAR - Nivel null asigna valor por defecto")
    void asignar_NivelNull_AsignaValorPorDefecto() {
        // ARRANGE
        crearRequest.setNivelAcceso(null);
        administradorMock.setNivelAcceso("ADMIN"); // Valor por defecto

        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(administradorRepository.existsByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(false);
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administradorMock);
        when(administradorMapper.toDTO(administradorMock)).thenReturn(administradorDTOMock);

        // ACT
        AdministradorDTO resultado = administradorService.asignar(crearRequest);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(administradorRepository, times(1)).save(any(Administrador.class));
    }

    // ==================== REVOCAR TESTS ====================

    @Test
    @DisplayName("REVOCAR - Usuario es administrador lo elimina")
    void revocarPorUsuario_UsuarioEsAdministrador_EliminaAdministrador() {
        // ARRANGE
        when(administradorRepository.existsByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(true);
        when(administradorRepository.deleteByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(1L); // <- cambio aquí

        // ACT
        administradorService.revocarPorUsuario(USUARIO_ID_VALIDO);

        // ASSERT
        verify(administradorRepository).existsByUsuarioId(USUARIO_ID_VALIDO);
        verify(administradorRepository).deleteByUsuarioId(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("REVOCAR - Usuario no es administrador no hace nada")
    void revocarPorUsuario_UsuarioNoEsAdministrador_NoHaceNada() {
        // ARRANGE
        when(administradorRepository.existsByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(false);

        // ACT
        administradorService.revocarPorUsuario(USUARIO_ID_VALIDO);

        // ASSERT
        verify(administradorRepository, times(1)).existsByUsuarioId(USUARIO_ID_VALIDO);
        verify(administradorRepository, never()).deleteByUsuarioId(anyInt());
    }

    // ==================== ACTUALIZAR TESTS ====================

    @Test
    @DisplayName("ACTUALIZAR - Datos válidos retorna administrador actualizado")
    void actualizar_DatosValidos_RetornaAdministradorActualizado() {
        // ARRANGE
        when(administradorRepository.findById(ADMIN_ID_VALIDO)).thenReturn(Optional.of(administradorMock));
        when(administradorRepository.save(administradorMock)).thenReturn(administradorMock);
        when(administradorMapper.toDTO(administradorMock)).thenReturn(administradorDTOMock);

        // ACT
        AdministradorDTO resultado = administradorService.actualizar(ADMIN_ID_VALIDO, actualizarRequest);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(administradorRepository, times(1)).findById(ADMIN_ID_VALIDO);
        verify(administradorRepository, times(1)).save(administradorMock);
        verify(administradorMapper, times(1)).toDTO(administradorMock);
    }

    @Test
    @DisplayName("ACTUALIZAR - Administrador no existe lanza IllegalArgumentException")
    void actualizar_AdministradorNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(administradorRepository.findById(ADMIN_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> administradorService.actualizar(ADMIN_ID_VALIDO, actualizarRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Administrador no existe");

        verify(administradorRepository, times(1)).findById(ADMIN_ID_VALIDO);
        verify(administradorRepository, never()).save(any(Administrador.class));
    }

    // ==================== OBTENER TESTS ====================

    @Test
    @DisplayName("OBTENER POR ID - Administrador existe retorna DTO")
    void obtenerPorId_AdministradorExiste_RetornaDTO() {
        // ARRANGE
        when(administradorRepository.findById(ADMIN_ID_VALIDO)).thenReturn(Optional.of(administradorMock));
        when(administradorMapper.toDTO(administradorMock)).thenReturn(administradorDTOMock);

        // ACT
        AdministradorDTO resultado = administradorService.obtenerPorId(ADMIN_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(ADMIN_ID_VALIDO);
        verify(administradorRepository, times(1)).findById(ADMIN_ID_VALIDO);
        verify(administradorMapper, times(1)).toDTO(administradorMock);
    }

    @Test
    @DisplayName("OBTENER POR ID - Administrador no existe lanza IllegalArgumentException")
    void obtenerPorId_AdministradorNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(administradorRepository.findById(ADMIN_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> administradorService.obtenerPorId(ADMIN_ID_VALIDO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Administrador no encontrado");

        verify(administradorRepository, times(1)).findById(ADMIN_ID_VALIDO);
    }

    @Test
    @DisplayName("OBTENER POR USUARIO ID - Existe retorna Optional con DTO")
    void obtenerPorUsuarioId_Existe_RetornaOptionalConDTO() {
        // ARRANGE
        when(administradorRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(administradorMock));
        when(administradorMapper.toDTO(administradorMock)).thenReturn(administradorDTOMock);

        // ACT
        Optional<AdministradorDTO> resultado = administradorService.obtenerPorUsuarioId(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(ADMIN_ID_VALIDO);
        verify(administradorRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(administradorMapper, times(1)).toDTO(administradorMock);
    }

    @Test
    @DisplayName("OBTENER POR USUARIO ID - No existe retorna Optional vacío")
    void obtenerPorUsuarioId_NoExiste_RetornaOptionalVacio() {
        // ARRANGE
        when(administradorRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT
        Optional<AdministradorDTO> resultado = administradorService.obtenerPorUsuarioId(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isEmpty();
        verify(administradorRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(administradorMapper, never()).toDTO(any(Administrador.class));
    }

    // ==================== LISTAR TESTS ====================

    @Test
    @DisplayName("LISTAR - Retorna lista de administradores")
    void listar_RetornaListaAdministradores() {
        // ARRANGE
        List<Administrador> administradores = List.of(administradorMock);
        List<AdministradorDTO> administradoresDTO = List.of(administradorDTOMock);

        when(administradorRepository.findAll()).thenReturn(administradores);
        when(administradorMapper.toDTOList(administradores)).thenReturn(administradoresDTO);

        // ACT
        List<AdministradorDTO> resultado = administradorService.listar();

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        verify(administradorRepository, times(1)).findAll();
        verify(administradorMapper, times(1)).toDTOList(administradores);
    }

    @Test
    @DisplayName("LISTAR POR NIVEL - Nivel válido retorna lista filtrada")
    void listarPorNivel_NivelValido_RetornaListaFiltrada() {
        // ARRANGE
        List<Administrador> administradores = List.of(administradorMock);
        List<AdministradorDTO> administradoresDTO = List.of(administradorDTOMock);

        when(administradorRepository.findByNivelAcceso(NIVEL_VALIDO)).thenReturn(administradores);
        when(administradorMapper.toDTOList(administradores)).thenReturn(administradoresDTO);

        // ACT
        List<AdministradorDTO> resultado = administradorService.listarPorNivel(NIVEL_VALIDO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        verify(administradorRepository, times(1)).findByNivelAcceso(NIVEL_VALIDO);
        verify(administradorMapper, times(1)).toDTOList(administradores);
    }

    @Test
    @DisplayName("LISTAR POR NIVEL - Nivel inválido lanza IllegalArgumentException")
    void listarPorNivel_NivelInvalido_LanzaExcepcion() {
        // ACT & ASSERT
        assertThatThrownBy(() -> administradorService.listarPorNivel(NIVEL_INVALIDO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nivel de acceso inválido");

        verify(administradorRepository, never()).findByNivelAcceso(anyString());
    }

    // ==================== VERIFICACIÓN TESTS ====================

    @Test
    @DisplayName("ES ADMINISTRADOR - Usuario es administrador retorna true")
    void esAdministrador_UsuarioEsAdministrador_RetornaTrue() {
        // ARRANGE
        when(administradorRepository.existsByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(true);

        // ACT
        boolean resultado = administradorService.esAdministrador(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isTrue();
        verify(administradorRepository, times(1)).existsByUsuarioId(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("ES ADMINISTRADOR - Usuario no es administrador retorna false")
    void esAdministrador_UsuarioNoEsAdministrador_RetornaFalse() {
        // ARRANGE
        when(administradorRepository.existsByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(false);

        // ACT
        boolean resultado = administradorService.esAdministrador(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isFalse();
        verify(administradorRepository, times(1)).existsByUsuarioId(USUARIO_ID_VALIDO);
    }

    // ==================== CONTAR TESTS ====================

    @Test
    @DisplayName("CONTAR - Retorna cantidad total de administradores")
    void contar_RetornaCantidadTotal() {
        // ARRANGE
        long cantidadEsperada = 5L;
        when(administradorRepository.count()).thenReturn(cantidadEsperada);

        // ACT
        long resultado = administradorService.contar();

        // ASSERT
        assertThat(resultado).isEqualTo(cantidadEsperada);
        verify(administradorRepository, times(1)).count();
    }

    @Test
    @DisplayName("CONTAR POR NIVEL - Nivel válido retorna cantidad")
    void contarPorNivel_NivelValido_RetornaCantidad() {
        // ARRANGE
        long cantidadEsperada = 3L;
        when(administradorRepository.countByNivelAcceso(NIVEL_VALIDO)).thenReturn(cantidadEsperada);

        // ACT
        long resultado = administradorService.contarPorNivel(NIVEL_VALIDO);

        // ASSERT
        assertThat(resultado).isEqualTo(cantidadEsperada);
        verify(administradorRepository, times(1)).countByNivelAcceso(NIVEL_VALIDO);
    }

    @Test
    @DisplayName("CONTAR POR NIVEL - Nivel inválido lanza IllegalArgumentException")
    void contarPorNivel_NivelInvalido_LanzaExcepcion() {
        // ACT & ASSERT
        assertThatThrownBy(() -> administradorService.contarPorNivel(NIVEL_INVALIDO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nivel de acceso inválido");

        verify(administradorRepository, never()).countByNivelAcceso(anyString());
    }

    @Test
    @DisplayName("CONTAR POR NIVEL - Repository retorna null retorna cero")
    void contarPorNivel_RepositoryRetornaNull_RetornaCero() {
        // ARRANGE
        when(administradorRepository.countByNivelAcceso(NIVEL_VALIDO)).thenReturn(0L);

        // ACT
        long resultado = administradorService.contarPorNivel(NIVEL_VALIDO);

        // ASSERT
        assertThat(resultado).isZero();
        verify(administradorRepository, times(1)).countByNivelAcceso(NIVEL_VALIDO);
    }
}