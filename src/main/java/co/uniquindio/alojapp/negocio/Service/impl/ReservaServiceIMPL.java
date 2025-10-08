// src/main/java/co/uniquindio/alojapp/negocio/Service/impl/ReservaServiceIMPL.java
package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.ReservaDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CancelarReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.ReservaService;
import co.uniquindio.alojapp.negocio.excepciones.AccesoNoAutorizadoException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.negocio.excepciones.ReglaNegocioException;
import co.uniquindio.alojapp.persistencia.DAO.ReservaDAO;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.AlojamientoRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservaServiceIMPL implements ReservaService {

    private final ReservaDAO reservaDAO;
    private final UsuarioRepository usuarioRepository;
    private final AlojamientoRepository alojamientoRepository;

    @Override
    public ReservaDTO crear(Integer usuarioId, CrearReservaRequest request) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

            Alojamiento alojamiento = alojamientoRepository.findById(request.getAlojamientoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Alojamiento no encontrado"));
            return reservaDAO.save(request, usuarioId);
        } catch (RuntimeException ex) {
            // El DAO hoy lanza RuntimeException para RN9-RN15; las mapeamos a dominio
            throw new ReglaNegocioException(ex.getMessage());
        }
    }

    @Override
    public ReservaDTO cancelar(Integer usuarioId, Integer reservaId, CancelarReservaRequest request) {
        // 1) Verificar existencia
        Reserva entidad = reservaDAO.findEntityById(reservaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro el recurso"));

        // 2) Verificar propiedad (RN20)
        if (!entidad.getHuesped().getId().equals(usuarioId)) {
            throw new AccesoNoAutorizadoException("No puede cancelar reservas de otro usuario");
        }

        // 3) Delegar al DAO (valida estado y 48h RN12) y mapear errores a dominio
        try {
            return reservaDAO.cancelar(reservaId, usuarioId, request)
                    .orElseThrow(() -> new ReglaNegocioException("No fue posible cancelar la reserva"));
        } catch (RuntimeException ex) {
            throw new ReglaNegocioException(ex.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaDTO obtenerPorId(Integer reservaId) {
        return reservaDAO.findById(reservaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public PaginacionResponse<ReservaDTO> listarPorHuesped(Integer huespedId, int pagina, int tamanoPagina) {
        return reservaDAO.findByHuesped(huespedId, pagina, tamanoPagina);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginacionResponse<ReservaDTO> listarPorAnfitrion(Integer anfitrionId, int pagina, int tamanoPagina) {
        return reservaDAO.findByAnfitrion(anfitrionId, pagina, tamanoPagina);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> listarCompletadasSinComentario(Integer huespedId) {
        return reservaDAO.findCompletadasSinComentario(huespedId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarPorEstado(EstadoReserva estado) {
        return reservaDAO.countByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularIngresosTotales(Integer anfitrionId) {
        BigDecimal total = reservaDAO.calcularIngresosTotales(anfitrionId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularIngresosPorPeriodo(Integer anfitrionId, LocalDate inicio, LocalDate fin) {
        BigDecimal total = reservaDAO.calcularIngresosPorPeriodo(anfitrionId, inicio, fin);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public void completarReservasVencidas() {
        reservaDAO.completarReservasVencidas();
    }
}
