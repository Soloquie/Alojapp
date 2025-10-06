package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.ReservaDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CancelarReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;
import co.uniquindio.alojapp.persistencia.Mapper.ReservaMapper;
import co.uniquindio.alojapp.persistencia.Repository.ReservaRepository;
import co.uniquindio.alojapp.persistencia.Repository.AlojamientoRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operaciones de persistencia de reservas
 * Implementa RN9-RN15, RN20, RN30, RN32
 */
@Repository
@RequiredArgsConstructor
public class ReservaDAO {

    private final ReservaRepository reservaRepository;
    private final AlojamientoRepository alojamientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReservaMapper reservaMapper;

    /**
     * Crear nueva reserva
     * RN9: Fecha checkin debe ser futura
     * RN10: Checkout > Checkin
     * RN11: Mínimo 1 noche
     * RN14: Validar no solapamiento
     * RN15: No exceder capacidad
     */
    public ReservaDTO save(CrearReservaRequest request, Long usuarioId) {
        // Validaciones
        validarFechas(request.getFechaCheckin(), request.getFechaCheckout());

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alojamiento alojamiento = alojamientoRepository.findById(request.getAlojamientoId())
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        // RN15: Validar capacidad
        if (request.getNumeroHuespedes() > alojamiento.getCapacidadMaxima()) {
            throw new RuntimeException("Número de huéspedes excede la capacidad máxima");
        }

        // RN14: Validar disponibilidad
        if (reservaRepository.existeSolapamiento(
                alojamiento.getId(),
                request.getFechaCheckin(),
                request.getFechaCheckout())) {
            throw new RuntimeException("El alojamiento no está disponible en las fechas seleccionadas");
        }

        // Calcular precio total
        long noches = ChronoUnit.DAYS.between(request.getFechaCheckin(), request.getFechaCheckout());
        BigDecimal precioTotal = alojamiento.getPrecioNoche().multiply(BigDecimal.valueOf(noches));

        Reserva reserva = Reserva.builder()
                .huesped(usuario)
                .alojamiento(alojamiento)
                .fechaCheckin(request.getFechaCheckin())
                .fechaCheckout(request.getFechaCheckout())
                .numeroHuespedes(request.getNumeroHuespedes())
                .precioTotal(precioTotal)
                .estado(EstadoReserva.CONFIRMADA)
                .fechaCreacion(LocalDateTime.now())
                .build();

        Reserva saved = reservaRepository.save(reserva);
        return reservaMapper.toDTO(saved);
    }

    /**
     * Buscar reserva por ID
     */
    public Optional<ReservaDTO> findById(Long id) {
        return reservaRepository.findById(id)
                .map(reservaMapper::toDTO);
    }

    /**
     * Buscar entity por ID
     */
    public Optional<Reserva> findEntityById(Long id) {
        return reservaRepository.findById(id);
    }

    /**
     * Buscar reservas por huésped
     */
    public List<ReservaDTO> findByHuesped(Long huespedId) {
        return reservaMapper.toDTOList(
                reservaRepository.findByHuespedId(huespedId)
        );
    }

    public PaginacionResponse<ReservaDTO> findByHuesped(Long huespedId, int pagina, int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina, Sort.by("fechaCreacion").descending());
        Page<Reserva> page = reservaRepository.findByHuespedId(huespedId, pageable);

        return buildPaginacionResponse(page);
    }

    /**
     * Buscar reservas por huésped y estado
     */
    public List<ReservaDTO> findByHuespedAndEstado(Long huespedId, EstadoReserva estado) {
        return reservaMapper.toDTOList(
                reservaRepository.findByHuespedIdAndEstado(huespedId, estado)
        );
    }

    /**
     * Buscar reservas por anfitrión
     */
    public List<ReservaDTO> findByAnfitrion(Long anfitrionId) {
        return reservaMapper.toDTOList(
                reservaRepository.findByAnfitrionId(anfitrionId)
        );
    }

    public PaginacionResponse<ReservaDTO> findByAnfitrion(Long anfitrionId, int pagina, int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina, Sort.by("fechaCreacion").descending());
        Page<Reserva> page = reservaRepository.findByAnfitrionId(anfitrionId, pageable);

        return buildPaginacionResponse(page);
    }

    /**
     * Buscar reservas por anfitrión y estado
     */
    public List<ReservaDTO> findByAnfitrionAndEstado(Long anfitrionId, EstadoReserva estado) {
        return reservaMapper.toDTOList(
                reservaRepository.findByAnfitrionIdAndEstado(anfitrionId, estado)
        );
    }

    /**
     * Cancelar reserva
     * RN12: Mínimo 48 horas de anticipación
     * RN20: Solo el propietario puede cancelar
     */
    public Optional<ReservaDTO> cancelar(Long reservaId, Long usuarioId, CancelarReservaRequest request) {
        return reservaRepository.findById(reservaId)
                .filter(r -> r.getHuesped().getId().equals(usuarioId)) // Validar propietario
                .map(reserva -> {
                    // Validar estado
                    if (reserva.getEstado() != EstadoReserva.CONFIRMADA &&
                            reserva.getEstado() != EstadoReserva.PENDIENTE) {
                        throw new RuntimeException("Solo se pueden cancelar reservas confirmadas o pendientes");
                    }

                    // RN12: Validar 48 horas de anticipación
                    if (LocalDate.now().plusDays(2).isAfter(reserva.getFechaCheckin())) {
                        throw new RuntimeException("No se puede cancelar con menos de 48 horas de anticipación");
                    }

                    reserva.setEstado(EstadoReserva.CANCELADA);
                    reserva.setFechaCancelacion(LocalDateTime.now());
                    reserva.setMotivoCancelacion(request.getMotivoCancelacion());

                    Reserva updated = reservaRepository.save(reserva);
                    return reservaMapper.toDTO(updated);
                });
    }

    /**
     * Marcar reserva como completada
     * RN32: Automático después del checkout
     */
    public void completarReservasVencidas() {
        List<Reserva> reservasParaCompletar = reservaRepository.findReservasParaCompletar();

        reservasParaCompletar.forEach(reserva -> {
            reserva.setEstado(EstadoReserva.COMPLETADA);
            reservaRepository.save(reserva);
        });
    }

    /**
     * Buscar reservas completadas sin comentario
     * RN16: Para poder comentar
     */
    public List<ReservaDTO> findCompletadasSinComentario(Long huespedId) {
        return reservaMapper.toDTOList(
                reservaRepository.findReservasCompletadasSinComentario(huespedId)
        );
    }

    /**
     * Contar reservas por estado
     */
    public Long countByEstado(EstadoReserva estado) {
        return reservaRepository.countByEstado(estado);
    }

    /**
     * Calcular ingresos de anfitrión
     */
    public BigDecimal calcularIngresosTotales(Long anfitrionId) {
        return reservaRepository.calcularIngresosTotales(anfitrionId);
    }

    public BigDecimal calcularIngresosPorPeriodo(Long anfitrionId, LocalDate inicio, LocalDate fin) {
        return reservaRepository.calcularIngresosPorPeriodo(anfitrionId, inicio, fin);
    }

    // Validación de fechas
    private void validarFechas(LocalDate checkin, LocalDate checkout) {
        // RN9: Checkin debe ser futura
        if (checkin.isBefore(LocalDate.now())) {
            throw new RuntimeException("La fecha de check-in debe ser futura");
        }

        // RN10: Checkout > Checkin
        if (!checkout.isAfter(checkin)) {
            throw new RuntimeException("La fecha de check-out debe ser posterior al check-in");
        }

        // RN11: Mínimo 1 noche
        long noches = ChronoUnit.DAYS.between(checkin, checkout);
        if (noches < 1) {
            throw new RuntimeException("La reserva debe tener mínimo 1 noche");
        }
    }

    // Helper para paginación
    private PaginacionResponse<ReservaDTO> buildPaginacionResponse(Page<Reserva> page) {
        List<ReservaDTO> dtos = reservaMapper.toDTOList(page.getContent());

        return PaginacionResponse.<ReservaDTO>builder()
                .contenido(dtos)
                .paginaActual(page.getNumber())
                .tamanoPagina(page.getSize())
                .totalElementos(page.getTotalElements())
                .totalPaginas(page.getTotalPages())
                .esPrimera(page.isFirst())
                .esUltima(page.isLast())
                .tieneSiguiente(page.hasNext())
                .tieneAnterior(page.hasPrevious())
                .build();
    }
}