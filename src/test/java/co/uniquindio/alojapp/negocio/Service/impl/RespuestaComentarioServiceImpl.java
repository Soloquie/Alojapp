package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.RespuestaComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ResponderComentarioRequest;
import co.uniquindio.alojapp.negocio.excepciones.ConflictoNegocioException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.RespuestaComentarioDAO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RespuestaComentarioServiceImplTest {

    @Mock
    private RespuestaComentarioDAO respuestaDAO;

    @InjectMocks
    private RespuestaComentarioServiceImpl service;

    private ResponderComentarioRequest request;
    private RespuestaComentarioDTO dto;

    private final Integer COMENTARIO_ID = 100;
    private final Integer ANFITRION_ID = 77;

    @BeforeEach
    void setUp() {
        request = new ResponderComentarioRequest(
                "Muchas gracias por tu comentario, ¡te esperamos pronto!"
        );

        dto = new RespuestaComentarioDTO(
                200,
                "Muchas gracias por tu comentario, ¡te esperamos pronto!",
                LocalDateTime.of(2025, 10, 5, 18, 45, 0),
                Long.valueOf(COMENTARIO_ID),
                "Carlos Ramírez"
        );
    }

    @Test
    @DisplayName("responder(): crea una respuesta cuando no existe duplicado")
    void responder_ok() {
        when(respuestaDAO.existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID)).thenReturn(false);
        when(respuestaDAO.save(eq(COMENTARIO_ID), any(ResponderComentarioRequest.class), eq(ANFITRION_ID)))
                .thenReturn(dto);

        RespuestaComentarioDTO result = service.responder(COMENTARIO_ID, request, ANFITRION_ID);

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getContenido(), result.getContenido());
        verify(respuestaDAO).existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID);
        verify(respuestaDAO).save(COMENTARIO_ID, request, ANFITRION_ID);
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("responder(): lanza 409 si el anfitrión ya respondió ese comentario")
    void responder_conflictoDuplicado() {
        when(respuestaDAO.existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID)).thenReturn(true);

        ConflictoNegocioException ex = assertThrows(
                ConflictoNegocioException.class,
                () -> service.responder(COMENTARIO_ID, request, ANFITRION_ID)
        );

        assertTrue(ex.getMessage().toLowerCase().contains("ya respondió"));
        verify(respuestaDAO).existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID);
        verify(respuestaDAO, never()).save(any(), any(), any());
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("obtenerPorId(): devuelve DTO cuando existe")
    void obtenerPorId_ok() {
        when(respuestaDAO.findById(200)).thenReturn(Optional.of(dto));

        RespuestaComentarioDTO result = service.obtenerPorId(200);

        assertNotNull(result);
        assertEquals(200, result.getId());
        verify(respuestaDAO).findById(200);
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("obtenerPorId(): lanza 404 cuando no existe")
    void obtenerPorId_noEncontrado() {
        when(respuestaDAO.findById(999)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(999));

        verify(respuestaDAO).findById(999);
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("listarPorComentario(): retorna lista de respuestas")
    void listarPorComentario_ok() {
        when(respuestaDAO.findByComentario(COMENTARIO_ID)).thenReturn(List.of(dto));

        List<RespuestaComentarioDTO> list = service.listarPorComentario(COMENTARIO_ID);

        assertEquals(1, list.size());
        assertEquals(dto.getId(), list.get(0).getId());
        verify(respuestaDAO).findByComentario(COMENTARIO_ID);
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("listarPorAnfitrion(): retorna lista de respuestas del anfitrión")
    void listarPorAnfitrion_ok() {
        when(respuestaDAO.findByAnfitrion(ANFITRION_ID)).thenReturn(List.of(dto));

        List<RespuestaComentarioDTO> list = service.listarPorAnfitrion(ANFITRION_ID);

        assertEquals(1, list.size());
        verify(respuestaDAO).findByAnfitrion(ANFITRION_ID);
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("contarPorAnfitrion(): retorna el total de respuestas del anfitrión")
    void contarPorAnfitrion_ok() {
        when(respuestaDAO.countByAnfitrion(ANFITRION_ID)).thenReturn(5L);

        Long total = service.contarPorAnfitrion(ANFITRION_ID);

        assertEquals(5L, total);
        verify(respuestaDAO).countByAnfitrion(ANFITRION_ID);
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("RN19: lanza error si el anfitrión NO es propietario del alojamiento del comentario")
    void responder_anfitrionNoPropietario() {
        // No hay duplicado
        given(respuestaDAO.existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID)).willReturn(false);
        // El DAO valida RN19 y lanza
        given(respuestaDAO.save(eq(COMENTARIO_ID), any(ResponderComentarioRequest.class), eq(ANFITRION_ID)))
                .willThrow(new RuntimeException("Solo el anfitrión propietario puede responder comentarios"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.responder(COMENTARIO_ID, request, ANFITRION_ID)
        );

        assertTrue(ex.getMessage().contains("propietario"));
        verify(respuestaDAO).existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID);
        verify(respuestaDAO).save(COMENTARIO_ID, request, ANFITRION_ID);
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("Duplicado: lanza 409 si el anfitrión ya respondió ese comentario")
    void responder_duplicado() {
        given(respuestaDAO.existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID)).willReturn(true);

        ConflictoNegocioException ex = assertThrows(
                ConflictoNegocioException.class,
                () -> service.responder(COMENTARIO_ID, request, ANFITRION_ID)
        );

        assertTrue(ex.getMessage().toLowerCase().contains("ya respondió"));
        verify(respuestaDAO).existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID);
        verify(respuestaDAO, never()).save(any(), any(), any());
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("Comentario no existe: el DAO lanza y el service propaga")
    void responder_comentarioNoExiste() {
        given(respuestaDAO.existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID)).willReturn(false);
        given(respuestaDAO.save(eq(COMENTARIO_ID), any(ResponderComentarioRequest.class), eq(ANFITRION_ID)))
                .willThrow(new RuntimeException("Comentario no encontrado"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.responder(COMENTARIO_ID, request, ANFITRION_ID)
        );

        assertTrue(ex.getMessage().contains("Comentario no encontrado"));
        verify(respuestaDAO).existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID);
        verify(respuestaDAO).save(COMENTARIO_ID, request, ANFITRION_ID);
        verifyNoMoreInteractions(respuestaDAO);
    }

    @Test
    @DisplayName("Anfitrión no existe: el DAO lanza y el service propaga")
    void responder_anfitrionNoExiste() {
        given(respuestaDAO.existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID)).willReturn(false);
        given(respuestaDAO.save(eq(COMENTARIO_ID), any(ResponderComentarioRequest.class), eq(ANFITRION_ID)))
                .willThrow(new RuntimeException("Anfitrión no encontrado"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.responder(COMENTARIO_ID, request, ANFITRION_ID)
        );

        assertTrue(ex.getMessage().contains("Anfitrión no encontrado"));
        verify(respuestaDAO).existeRespuestaAnfitrion(COMENTARIO_ID, ANFITRION_ID);
        verify(respuestaDAO).save(COMENTARIO_ID, request, ANFITRION_ID);
        verifyNoMoreInteractions(respuestaDAO);
    }
}
