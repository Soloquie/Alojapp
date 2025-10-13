package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.CodigoRecuperacionDTO;
import co.uniquindio.alojapp.persistencia.DAO.CodigoRecuperacionDAO;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CodigoRecuperacionServiceIMPLTest {

    @Mock private CodigoRecuperacionDAO codigoDAO;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private JavaMailSender mailSender;

    @InjectMocks
    private CodigoRecuperacionServiceIMPL service;

    // Mocks comunes
    private Usuario usuario;
    private MimeMessage mimeMessage;

    @BeforeEach
    void init() {
        // Inyectar valores @Value
        ReflectionTestUtils.setField(service, "maxCodigosActivos", 3);
        ReflectionTestUtils.setField(service, "cooldownSegundos", 60);
        ReflectionTestUtils.setField(service, "fromEmail", "no-reply@alojapp.com");
        ReflectionTestUtils.setField(service, "resetUrlBase", "https://front/reset");

        // Usuario simulado
        usuario = mock(Usuario.class);
        when(usuario.getId()).thenReturn(10);
        when(usuario.getNombre()).thenReturn("Ana Gómez");

        // MimeMessage real (vacío) para no romper MimeMessageHelper
        mimeMessage = new MimeMessage((Session) null);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    // -------- solicitarCodigo --------

    @Test
    @DisplayName("solicitarCodigo OK: genera, persiste y envía correo")
    void solicitar_ok() {
        String email = "ana@example.com";

        // repo usuario
        given(usuarioRepository.findByEmailIgnoreCase(email)).willReturn(Optional.of(usuario));

        // límites
        given(codigoDAO.countCodigosValidos(usuario.getId())).willReturn(0L);
        given(codigoDAO.findByUsuario(usuario.getId())).willReturn(Collections.emptyList());

        // dto generado (mock para no depender de su implementación)
        CodigoRecuperacionDTO dto = mock(CodigoRecuperacionDTO.class);
        given(dto.getCodigo()).willReturn("ABC123");
        given(codigoDAO.generarCodigo(usuario.getId())).willReturn(dto);

        // act
        CodigoRecuperacionDTO out = service.solicitarCodigo(email);

        // assert
        assertNotNull(out);
        assertEquals("ABC123", out.getCodigo());
        verify(usuarioRepository).findByEmailIgnoreCase(email);
        verify(codigoDAO).countCodigosValidos(usuario.getId());
        verify(codigoDAO).findByUsuario(usuario.getId());
        verify(codigoDAO).generarCodigo(usuario.getId());
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    @DisplayName("solicitarCodigo falla si email vacío")
    void solicitar_emailVacio() {
        assertThrows(IllegalArgumentException.class, () -> service.solicitarCodigo("  "));
        verifyNoInteractions(usuarioRepository, codigoDAO, mailSender);
    }

    @Test
    @DisplayName("solicitarCodigo falla si usuario no existe")
    void solicitar_usuarioNoExiste() {
        String email = "nadie@example.com";
        given(usuarioRepository.findByEmailIgnoreCase(email)).willReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> service.solicitarCodigo(email));
        assertTrue(ex.getMessage().toLowerCase().contains("usuario"));
        verify(usuarioRepository).findByEmailIgnoreCase(email);
        verifyNoMoreInteractions(usuarioRepository);
        verifyNoInteractions(codigoDAO, mailSender);
    }

    @Test
    @DisplayName("solicitarCodigo falla si alcanzó límite de códigos activos")
    void solicitar_limiteActivos() {
        String email = "ana@example.com";
        given(usuarioRepository.findByEmailIgnoreCase(email)).willReturn(Optional.of(usuario));
        given(codigoDAO.countCodigosValidos(usuario.getId())).willReturn(3L); // = max

        IllegalStateException ex = assertThrows(
                IllegalStateException.class, () -> service.solicitarCodigo(email));
        assertTrue(ex.getMessage().toLowerCase().contains("límite"));
        verify(codigoDAO, never()).generarCodigo(any());
        verifyNoInteractions(mailSender);
    }

    @Test
    @DisplayName("solicitarCodigo falla por cooldown (solicitud muy frecuente)")
    void solicitar_cooldown() {
        String email = "ana@example.com";
        given(usuarioRepository.findByEmailIgnoreCase(email)).willReturn(Optional.of(usuario));
        given(codigoDAO.countCodigosValidos(usuario.getId())).willReturn(0L);

        // historial con 1 código cuyo exp = ahora+15 -> creado ≈ ahora (dentro del cooldown)
        CodigoRecuperacionDTO ultimo = mock(CodigoRecuperacionDTO.class);
        given(ultimo.getFechaExpiracion()).willReturn(LocalDateTime.now().plusMinutes(15));
        given(codigoDAO.findByUsuario(usuario.getId())).willReturn(List.of(ultimo));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class, () -> service.solicitarCodigo(email));
        assertTrue(ex.getMessage().toLowerCase().contains("frecuente"));
        verify(codigoDAO, never()).generarCodigo(any());
        verifyNoInteractions(mailSender);
    }

    @Test
    @DisplayName("solicitarCodigo propaga error de envío de correo (MailException)")
    void solicitar_fallaEnvioCorreo() {
        String email = "ana@example.com";
        given(usuarioRepository.findByEmailIgnoreCase(email)).willReturn(Optional.of(usuario));
        given(codigoDAO.countCodigosValidos(usuario.getId())).willReturn(0L);
        given(codigoDAO.findByUsuario(usuario.getId())).willReturn(Collections.emptyList());

        CodigoRecuperacionDTO dto = mock(CodigoRecuperacionDTO.class);
        given(dto.getCodigo()).willReturn("ABC123");
        given(codigoDAO.generarCodigo(usuario.getId())).willReturn(dto);

        // Simular que el mail sender falla al enviar
        doThrow(new MailSendException("smtp down")).when(mailSender).send(any(MimeMessage.class));

        // MailException es unchecked y no está capturada -> se propaga
        assertThrows(MailException.class, () -> service.solicitarCodigo(email));

        verify(mailSender).send(any(MimeMessage.class));
    }

    // -------- validarCodigo --------

    @Test
    @DisplayName("validarCodigo true cuando existe y está vigente")
    void validar_true() {
        given(codigoDAO.findCodigoValido(10, "ABC")).willReturn(Optional.of(mock(CodigoRecuperacionDTO.class)));
        assertTrue(service.validarCodigo(10, "ABC"));
    }

    @Test
    @DisplayName("validarCodigo false para entradas inválidas o no encontrado")
    void validar_false() {
        assertFalse(service.validarCodigo(null, "X"));
        assertFalse(service.validarCodigo(10, " "));
        given(codigoDAO.findCodigoValido(10, "NOPE")).willReturn(Optional.empty());
        assertFalse(service.validarCodigo(10, "NOPE"));
    }

    // -------- consumirCodigo --------

    @Test
    @DisplayName("consumirCodigo OK: marca como usado")
    void consumir_ok() {
        CodigoRecuperacionDTO dto = mock(CodigoRecuperacionDTO.class);
        given(dto.getId()).willReturn(99);
        given(codigoDAO.findCodigoValido(10, "OK")).willReturn(Optional.of(dto));
        given(codigoDAO.marcarComoUsado(99)).willReturn(true);

        assertDoesNotThrow(() -> service.consumirCodigo(10, "OK"));
        verify(codigoDAO).marcarComoUsado(99);
    }

    @Test
    @DisplayName("consumirCodigo lanza si código inválido o expirado")
    void consumir_invalido() {
        given(codigoDAO.findCodigoValido(10, "BAD")).willReturn(Optional.empty());
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> service.consumirCodigo(10, "BAD"));
        assertTrue(ex.getMessage().toLowerCase().contains("inválido"));
        verify(codigoDAO, never()).marcarComoUsado(anyInt());
    }

    @Test
    @DisplayName("consumirCodigo lanza si no se pudo marcar como usado")
    void consumir_noMarca() {
        CodigoRecuperacionDTO dto = mock(CodigoRecuperacionDTO.class);
        given(dto.getId()).willReturn(77);
        given(codigoDAO.findCodigoValido(10, "X")).willReturn(Optional.of(dto));
        given(codigoDAO.marcarComoUsado(77)).willReturn(false);

        assertThrows(IllegalStateException.class, () -> service.consumirCodigo(10, "X"));
    }

    // -------- listar / limpieza --------

    @Test
    @DisplayName("listarCodigos OK")
    void listar_ok() {
        List<CodigoRecuperacionDTO> lista = List.of(mock(CodigoRecuperacionDTO.class));
        given(codigoDAO.findByUsuario(10)).willReturn(lista);
        assertEquals(1, service.listarCodigos(10).size());
    }

    @Test
    @DisplayName("listarCodigos lanza si usuarioId es null")
    void listar_idNull() {
        assertThrows(IllegalArgumentException.class, () -> service.listarCodigos(null));
        verifyNoInteractions(codigoDAO);
    }

    @Test
    @DisplayName("limpiarExpirados delega en el DAO")
    void limpiarExpirados_ok() {
        assertDoesNotThrow(() -> service.limpiarExpirados());
        verify(codigoDAO).limpiarCodigosExpirados();
    }
}
