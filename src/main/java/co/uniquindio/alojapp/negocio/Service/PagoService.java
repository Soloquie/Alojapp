package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.PagoDTO;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PagoService {

    // Registrar un pago para una reserva del huésped autenticado
    PagoDTO pagarReserva(Integer usuarioId,
                         Integer reservaId,
                         String metodoPago,
                         BigDecimal monto);

    // Consultas
    PagoDTO obtenerPorId(Integer pagoId);
    PagoDTO obtenerPorReserva(Integer reservaId);

    List<PagoDTO> listarPorUsuario(Integer usuarioId);
    PaginacionResponse<PagoDTO> listarPorUsuario(Integer usuarioId, int pagina, int tamano);

    List<PagoDTO> listarPorEstado(String estado);
    List<PagoDTO> listarPorMetodo(String metodo);
    List<PagoDTO> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);
    List<PagoDTO> listarAprobadosPorAnfitrion(Integer anfitrionId);

    // Agregaciones
    BigDecimal totalPagadoPorUsuario(Integer usuarioId);
    Long contarPorEstado(String estado);

    // Gestión de estado del pago (APROBADO, RECHAZADO, REEMBOLSADO, PENDIENTE)
    PagoDTO actualizarEstado(Integer pagoId, String nuevoEstado);

    PagoDTO obtenerDeUsuarioPorReserva(Integer userId, Integer reservaId);

    PagoDTO registrarPago(Integer userId, Integer reservaId, String metodoPago, double monto, LocalDateTime now);
}
