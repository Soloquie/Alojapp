package co.uniquindio.alojapp.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para AdminController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AdminController - Unit Tests")
public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    // =========================
    // USUARIOS TESTS
    // =========================

    @Test
    @DisplayName("Listar usuarios - Retorna lista de usuarios correctamente")
    void listarUsuarios_DeberiaRetornarListaUsuarios() {
        // Act
        ResponseEntity<List<AdminController.UsuarioResponse>> response = adminController.listarUsuarios();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);

        AdminController.UsuarioResponse usuario = response.getBody().get(0);
        assertThat(usuario.id).isEqualTo("55");
        assertThat(usuario.email).isEqualTo("juan@correo.com");
        assertThat(usuario.rol).isEqualTo("HUESPED");
        assertThat(usuario.estado).isEqualTo("ACTIVO");
    }

    @Test
    @DisplayName("Cambiar estado usuario - Retorna mensaje de éxito")
    void cambiarEstadoUsuario_DeberiaRetornarMensajeExito() {
        // Arrange
        String usuarioId = "55";
        String nuevoEstado = "BLOQUEADO";

        // Act
        ResponseEntity<String> response = adminController.cambiarEstadoUsuario(usuarioId, nuevoEstado);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Usuario 55 cambiado a estado BLOQUEADO");
    }

    @Test
    @DisplayName("Cambiar estado usuario - Diferentes IDs y estados")
    void cambiarEstadoUsuario_DiferentesIdsYEstados_DeberiaRetornarMensajesCorrectos() {
        // Arrange & Act - Caso 1
        ResponseEntity<String> response1 = adminController.cambiarEstadoUsuario("1", "ACTIVO");

        // Assert - Caso 1
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody()).isEqualTo("Usuario 1 cambiado a estado ACTIVO");

        // Arrange & Act - Caso 2
        ResponseEntity<String> response2 = adminController.cambiarEstadoUsuario("999", "SUSPENDIDO");

        // Assert - Caso 2
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEqualTo("Usuario 999 cambiado a estado SUSPENDIDO");
    }

    // =========================
    // ALOJAMIENTOS TESTS
    // =========================

    @Test
    @DisplayName("Listar alojamientos - Retorna lista de alojamientos correctamente")
    void listarAlojamientos_DeberiaRetornarListaAlojamientos() {
        // Act
        ResponseEntity<List<AdminController.AlojamientoResponse>> response = adminController.listarAlojamientos();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);

        AdminController.AlojamientoResponse alojamiento = response.getBody().get(0);
        assertThat(alojamiento.id).isEqualTo("15");
        assertThat(alojamiento.nombre).isEqualTo("Casa en la playa");
        assertThat(alojamiento.anfitrionEmail).isEqualTo("juan@correo.com");
        assertThat(alojamiento.estado).isEqualTo("ACTIVO");
    }

    @Test
    @DisplayName("Cambiar estado alojamiento - Retorna mensaje de éxito")
    void cambiarEstadoAlojamiento_DeberiaRetornarMensajeExito() {
        // Arrange
        String alojamientoId = "15";
        String nuevoEstado = "BLOQUEADO";

        // Act
        ResponseEntity<String> response = adminController.cambiarEstadoAlojamiento(alojamientoId, nuevoEstado);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Alojamiento 15 cambiado a estado BLOQUEADO");
    }

    @Test
    @DisplayName("Cambiar estado alojamiento - Diferentes IDs y estados")
    void cambiarEstadoAlojamiento_DiferentesIdsYEstados_DeberiaRetornarMensajesCorrectos() {
        // Arrange & Act - Caso 1
        ResponseEntity<String> response1 = adminController.cambiarEstadoAlojamiento("25", "APROBADO");

        // Assert - Caso 1
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody()).isEqualTo("Alojamiento 25 cambiado a estado APROBADO");

        // Arrange & Act - Caso 2
        ResponseEntity<String> response2 = adminController.cambiarEstadoAlojamiento("50", "PENDIENTE");

        // Assert - Caso 2
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEqualTo("Alojamiento 50 cambiado a estado PENDIENTE");
    }

    // =========================
    // RESERVAS TESTS
    // =========================

    @Test
    @DisplayName("Listar reservas - Retorna lista de reservas correctamente")
    void listarReservas_DeberiaRetornarListaReservas() {
        // Act
        ResponseEntity<List<AdminController.ReservaResponse>> response = adminController.listarReservas();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);

        AdminController.ReservaResponse reserva = response.getBody().get(0);
        assertThat(reserva.id).isEqualTo("40");
        assertThat(reserva.alojamiento).isEqualTo("Casa en la playa");
        assertThat(reserva.huespedEmail).isEqualTo("huésped@correo.com");
        assertThat(reserva.fechaInicio).isEqualTo("2025-10-01");
        assertThat(reserva.fechaFin).isEqualTo("2025-10-05");
        assertThat(reserva.estado).isEqualTo("CONFIRMADA");
    }

    // =========================
    // PAGOS TESTS
    // =========================

    @Test
    @DisplayName("Listar pagos - Retorna lista de pagos correctamente")
    void listarPagos_DeberiaRetornarListaPagos() {
        // Act
        ResponseEntity<List<AdminController.PagoResponse>> response = adminController.listarPagos();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);

        AdminController.PagoResponse pago = response.getBody().get(0);
        assertThat(pago.id).isEqualTo("77");
        assertThat(pago.reservaId).isEqualTo("40");
        assertThat(pago.monto).isEqualTo(350000.0);
        assertThat(pago.metodo).isEqualTo("TARJETA");
        assertThat(pago.estado).isEqualTo("COMPLETADO");
    }

    // =========================
    // COMENTARIOS TESTS
    // =========================

    @Test
    @DisplayName("Eliminar comentario - Retorna estado 204 sin contenido")
    void eliminarComentario_DeberiaRetornarNoContent() {
        // Arrange
        String comentarioId = "100";

        // Act
        ResponseEntity<Void> response = adminController.eliminarComentario(comentarioId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("Eliminar comentario - Diferentes IDs retornan 204")
    void eliminarComentario_DiferentesIds_DeberiaRetornarNoContent() {
        // Arrange & Act - Caso 1
        ResponseEntity<Void> response1 = adminController.eliminarComentario("50");

        // Assert - Caso 1
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Arrange & Act - Caso 2
        ResponseEntity<Void> response2 = adminController.eliminarComentario("999");

        // Assert - Caso 2
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Arrange & Act - Caso 3
        ResponseEntity<Void> response3 = adminController.eliminarComentario("1");

        // Assert - Caso 3
        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    // =========================
    // CLASES INTERNAS TESTS
    // =========================

    @Test
    @DisplayName("Clase UsuarioResponse - Campos tienen valores correctos")
    void usuarioResponse_CamposTienenValoresCorrectos() {
        // Arrange
        AdminController.UsuarioResponse usuario = new AdminController.UsuarioResponse();

        // Act
        usuario.id = "123";
        usuario.email = "test@test.com";
        usuario.rol = "ANFITRION";
        usuario.estado = "BLOQUEADO";

        // Assert
        assertThat(usuario.id).isEqualTo("123");
        assertThat(usuario.email).isEqualTo("test@test.com");
        assertThat(usuario.rol).isEqualTo("ANFITRION");
        assertThat(usuario.estado).isEqualTo("BLOQUEADO");
    }

    @Test
    @DisplayName("Clase AlojamientoResponse - Campos tienen valores correctos")
    void alojamientoResponse_CamposTienenValoresCorrectos() {
        // Arrange
        AdminController.AlojamientoResponse alojamiento = new AdminController.AlojamientoResponse();

        // Act
        alojamiento.id = "456";
        alojamiento.nombre = "Apartamento en el centro";
        alojamiento.anfitrionEmail = "anfitrion@test.com";
        alojamiento.estado = "PENDIENTE";

        // Assert
        assertThat(alojamiento.id).isEqualTo("456");
        assertThat(alojamiento.nombre).isEqualTo("Apartamento en el centro");
        assertThat(alojamiento.anfitrionEmail).isEqualTo("anfitrion@test.com");
        assertThat(alojamiento.estado).isEqualTo("PENDIENTE");
    }

    @Test
    @DisplayName("Clase ReservaResponse - Campos tienen valores correctos")
    void reservaResponse_CamposTienenValoresCorrectos() {
        // Arrange
        AdminController.ReservaResponse reserva = new AdminController.ReservaResponse();

        // Act
        reserva.id = "789";
        reserva.alojamiento = "Cabaña en la montaña";
        reserva.huespedEmail = "huesped@test.com";
        reserva.fechaInicio = "2025-12-01";
        reserva.fechaFin = "2025-12-07";
        reserva.estado = "CANCELADA";

        // Assert
        assertThat(reserva.id).isEqualTo("789");
        assertThat(reserva.alojamiento).isEqualTo("Cabaña en la montaña");
        assertThat(reserva.huespedEmail).isEqualTo("huesped@test.com");
        assertThat(reserva.fechaInicio).isEqualTo("2025-12-01");
        assertThat(reserva.fechaFin).isEqualTo("2025-12-07");
        assertThat(reserva.estado).isEqualTo("CANCELADA");
    }

    @Test
    @DisplayName("Clase PagoResponse - Campos tienen valores correctos")
    void pagoResponse_CamposTienenValoresCorrectos() {
        // Arrange
        AdminController.PagoResponse pago = new AdminController.PagoResponse();

        // Act
        pago.id = "321";
        pago.reservaId = "789";
        pago.monto = 250000.0;
        pago.metodo = "EFECTIVO";
        pago.estado = "PENDIENTE";

        // Assert
        assertThat(pago.id).isEqualTo("321");
        assertThat(pago.reservaId).isEqualTo("789");
        assertThat(pago.monto).isEqualTo(250000.0);
        assertThat(pago.metodo).isEqualTo("EFECTIVO");
        assertThat(pago.estado).isEqualTo("PENDIENTE");
    }

    // =========================
    // CASOS BORDE Y VALIDACIONES
    // =========================

    @Test
    @DisplayName("IDs con formato especial - Maneja correctamente diferentes formatos")
    void idsConFormatoEspecial_ManejaCorrectamenteDiferentesFormatos() {
        // Arrange & Act - ID con caracteres especiales
        ResponseEntity<String> response1 = adminController.cambiarEstadoUsuario("ABC-123", "ACTIVO");

        // Assert
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody()).isEqualTo("Usuario ABC-123 cambiado a estado ACTIVO");

        // Arrange & Act - ID numérico largo
        ResponseEntity<String> response2 = adminController.cambiarEstadoAlojamiento("999999999", "BLOQUEADO");

        // Assert
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEqualTo("Alojamiento 999999999 cambiado a estado BLOQUEADO");
    }

    @Test
    @DisplayName("Estados con diferentes valores - Maneja correctamente diferentes estados")
    void estadosConDiferentesValores_ManejaCorrectamenteDiferentesEstados() {
        // Arrange & Act - Estados de usuario
        ResponseEntity<String> response1 = adminController.cambiarEstadoUsuario("1", "SUSPENDIDO");
        ResponseEntity<String> response2 = adminController.cambiarEstadoUsuario("2", "INACTIVO");

        // Assert
        assertThat(response1.getBody()).isEqualTo("Usuario 1 cambiado a estado SUSPENDIDO");
        assertThat(response2.getBody()).isEqualTo("Usuario 2 cambiado a estado INACTIVO");

        // Arrange & Act - Estados de alojamiento
        ResponseEntity<String> response3 = adminController.cambiarEstadoAlojamiento("1", "RECHAZADO");
        ResponseEntity<String> response4 = adminController.cambiarEstadoAlojamiento("2", "EN_REVISION");

        // Assert
        assertThat(response3.getBody()).isEqualTo("Alojamiento 1 cambiado a estado RECHAZADO");
        assertThat(response4.getBody()).isEqualTo("Alojamiento 2 cambiado a estado EN_REVISION");
    }

    @Test
    @DisplayName("Respuestas HTTP - Verifica códigos de estado correctos")
    void respuestasHttp_VerificaCodigosDeEstadoCorrectos() {
        // Act - Todos los endpoints GET deben retornar 200 OK
        ResponseEntity<?> usuariosResponse = adminController.listarUsuarios();
        ResponseEntity<?> alojamientosResponse = adminController.listarAlojamientos();
        ResponseEntity<?> reservasResponse = adminController.listarReservas();
        ResponseEntity<?> pagosResponse = adminController.listarPagos();

        // Assert
        assertThat(usuariosResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(alojamientosResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservasResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(pagosResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Act - PUT endpoints retornan 200 OK
        ResponseEntity<String> estadoUsuarioResponse = adminController.cambiarEstadoUsuario("1", "ACTIVO");
        ResponseEntity<String> estadoAlojamientoResponse = adminController.cambiarEstadoAlojamiento("1", "ACTIVO");

        // Assert
        assertThat(estadoUsuarioResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(estadoAlojamientoResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Act - DELETE endpoint retorna 204 NO_CONTENT
        ResponseEntity<Void> eliminarResponse = adminController.eliminarComentario("1");

        // Assert
        assertThat(eliminarResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Estructura de datos - Listas no son nulas y tienen estructura esperada")
    void estructuraDeDatos_ListasNoSonNulasYTienenEstructuraEsperada() {
        // Act
        ResponseEntity<List<AdminController.UsuarioResponse>> usuarios = adminController.listarUsuarios();
        ResponseEntity<List<AdminController.AlojamientoResponse>> alojamientos = adminController.listarAlojamientos();
        ResponseEntity<List<AdminController.ReservaResponse>> reservas = adminController.listarReservas();
        ResponseEntity<List<AdminController.PagoResponse>> pagos = adminController.listarPagos();

        // Assert - Verificar que las listas no son nulas
        assertThat(usuarios.getBody()).isNotNull();
        assertThat(alojamientos.getBody()).isNotNull();
        assertThat(reservas.getBody()).isNotNull();
        assertThat(pagos.getBody()).isNotNull();

        // Assert - Verificar que las listas tienen exactamente 1 elemento cada una
        assertThat(usuarios.getBody()).hasSize(1);
        assertThat(alojamientos.getBody()).hasSize(1);
        assertThat(reservas.getBody()).hasSize(1);
        assertThat(pagos.getBody()).hasSize(1);

        // Assert - Verificar que el primer elemento de cada lista no es nulo
        assertThat(usuarios.getBody().get(0)).isNotNull();
        assertThat(alojamientos.getBody().get(0)).isNotNull();
        assertThat(reservas.getBody().get(0)).isNotNull();
        assertThat(pagos.getBody().get(0)).isNotNull();
    }
}