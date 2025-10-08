package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.ReservaDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CancelarReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReservaService {

    ReservaDTO crear(Integer usuarioId, CrearReservaRequest request);

    ReservaDTO cancelar(Integer usuarioId, Integer reservaId, CancelarReservaRequest request);

    ReservaDTO obtenerPorId(Integer reservaId);

    PaginacionResponse<ReservaDTO> listarPorHuesped(Integer huespedId, int pagina, int tamanoPagina);

    PaginacionResponse<ReservaDTO> listarPorAnfitrion(Integer anfitrionId, int pagina, int tamanoPagina);

    List<ReservaDTO> listarCompletadasSinComentario(Integer huespedId);

    Long contarPorEstado(EstadoReserva estado);

    BigDecimal calcularIngresosTotales(Integer anfitrionId);

    BigDecimal calcularIngresosPorPeriodo(Integer anfitrionId, LocalDate inicio, LocalDate fin);

    void completarReservasVencidas();
}
