package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.PagoDTO;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.PagoService;
import co.uniquindio.alojapp.negocio.excepciones.AccesoNoAutorizadoException;
import co.uniquindio.alojapp.negocio.excepciones.ReglaNegocioException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.PagoDAO;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;
import co.uniquindio.alojapp.persistencia.Entity.Pago;
import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.PagoMapper;
import co.uniquindio.alojapp.persistencia.Repository.PagoRepository;
import co.uniquindio.alojapp.persistencia.Repository.ReservaRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PagoServiceIMPL implements PagoService {

    private final PagoDAO pagoDAO;
    private final ReservaRepository reservaRepository;
    private final PagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagoMapper pagoMapper;

    private static final Set<String> METODOS_VALIDOS = Set.of(
            "TARJETA_CREDITO", "TARJETA_DEBITO", "PSE", "NEQUI", "DAVIPLATA"
    );

    // =========================================================
    // Registrar pago de una reserva (reglas de negocio)
    // =========================================================
    @Override
    @Transactional
    public PagoDTO pagarReserva(Integer usuarioId,
                                Integer reservaId,
                                String metodoPago,
                                BigDecimal monto) {

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada"));

        // RN: La reserva debe pertenecer al huésped autenticado
        if (!reserva.getHuesped().getId().equals(usuarioId)) {
            throw new ReglaNegocioException("La reserva no pertenece al usuario autenticado");
        }

        // RN: La reserva no puede estar CANCELADA o COMPLETADA
        if (reserva.getEstado() == EstadoReserva.CANCELADA || reserva.getEstado() == EstadoReserva.COMPLETADA) {
            throw new ReglaNegocioException("No se puede pagar una reserva cancelada o completada");
        }

        // RN: No permitir pago luego del check-in
        if (!LocalDate.now().isBefore(reserva.getFechaCheckin())) { // hoy >= check-in -> no
            throw new ReglaNegocioException("El pago debe realizarse antes del check-in");
        }

        // RN: Validar método de pago
        String metodo = (metodoPago == null) ? "" : metodoPago.toUpperCase(Locale.ROOT);
        if (!METODOS_VALIDOS.contains(metodo)) {
            throw new ReglaNegocioException("Método de pago no soportado: " + metodoPago);
        }

        // RN: Validar monto exactamente igual al precio total de la reserva
        BigDecimal precio = reserva.getPrecioTotal() == null ? BigDecimal.ZERO : reserva.getPrecioTotal();
        if (monto == null || precio.setScale(2).compareTo(monto.setScale(2)) != 0) {
            throw new ReglaNegocioException("El monto del pago no coincide con el precio total de la reserva");
        }

        // RN: La reserva no debe tener ya un pago
        if (pagoRepository.findByReservaId(reservaId).isPresent()) {
            throw new ReglaNegocioException("La reserva ya tiene un pago registrado");
        }

        // Persistencia vía DAO
        PagoDTO dto = pagoDAO.crearParaReserva(reservaId, usuarioId, monto, metodo, "APROBADO");

        // Efecto colateral opcional: si estaba PENDIENTE, pasar a CONFIRMADA
        if (reserva.getEstado() == EstadoReserva.PENDIENTE) {
            reserva.setEstado(EstadoReserva.CONFIRMADA);
            reservaRepository.save(reserva);
        }

        return dto;
    }

    // =========================================================
    // Consultas
    // =========================================================

    @Override
    public PagoDTO obtenerPorId(Integer pagoId) {
        return pagoDAO.findById(pagoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado"));
    }

    @Override
    public PagoDTO obtenerPorReserva(Integer reservaId) {
        return pagoDAO.findByReserva(reservaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado para la reserva"));
    }

    @Override
    public List<PagoDTO> listarPorUsuario(Integer usuarioId) {
        return pagoDAO.listarPorUsuario(usuarioId);
    }

    @Override
    public PaginacionResponse<PagoDTO> listarPorUsuario(Integer usuarioId, int pagina, int tamano) {
        return pagoDAO.listarPorUsuario(usuarioId, pagina, tamano);
    }

    @Override
    public List<PagoDTO> listarPorEstado(String estado) {
        return pagoDAO.listarPorEstado(estado);
    }

    @Override
    public List<PagoDTO> listarPorMetodo(String metodo) {
        return pagoDAO.listarPorMetodo(metodo);
    }

    @Override
    public List<PagoDTO> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return pagoDAO.listarPorRangoFechas(inicio, fin);
    }

    @Override
    public List<PagoDTO> listarAprobadosPorAnfitrion(Integer anfitrionId) {
        return pagoDAO.listarAprobadosPorAnfitrion(anfitrionId);
    }

    // =========================================================
    // Agregaciones
    // =========================================================
    @Override
    public BigDecimal totalPagadoPorUsuario(Integer usuarioId) {
        return pagoDAO.totalPagadoPorUsuario(usuarioId);
    }

    @Override
    public Long contarPorEstado(String estado) {
        return pagoDAO.contarPorEstado(estado);
    }

    // =========================================================
    // Estado del pago
    // =========================================================
    @Override
    @Transactional
    public PagoDTO actualizarEstado(Integer pagoId, String nuevoEstado) {
        return pagoDAO.actualizarEstado(pagoId, nuevoEstado);
    }

    @Override
    public PagoDTO registrarPago(Integer usuarioId,
                                 Integer reservaId,
                                 String metodoPago,
                                 double monto,
                                 LocalDateTime fechaPago) {

        // --- Validaciones base ---
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada"));

        // La reserva debe pertenecer al usuario que paga
        if (!reserva.getHuesped().getId().equals(usuario.getId())) {
            throw new AccesoNoAutorizadoException("La reserva no pertenece al usuario");
        }

        // No permitir pago duplicado si ya existe un pago APROBADO o PENDIENTE
        pagoRepository.findByReservaId(reservaId).ifPresent(p -> {
            if ("APROBADO".equalsIgnoreCase(p.getEstado()) || "PENDIENTE".equalsIgnoreCase(p.getEstado())) {
                throw new ReglaNegocioException("La reserva ya tiene un pago registrado");
            }
        });

        // (Opcional) valida montos con tu lógica de negocio (precio * noches, etc.)
        if (monto <= 0) {
            throw new ReglaNegocioException("El monto del pago debe ser mayor a cero");
        }

        // --- Construcción y persistencia ---
        Pago nuevo = Pago.builder()
                .monto(monto)
                .metodoPago(metodoPago)
                .estado("PENDIENTE")              // o “APROBADO” si tu pasarela ya confirma
                .fechaPago(fechaPago != null ? fechaPago : LocalDateTime.now())
                .reserva(reserva)
                .usuario(usuario)
                .build();

        Pago guardado = pagoRepository.save(nuevo);

        // (Opcional) actualizar estado de reserva si así lo define tu regla
        // reserva.setEstado(EstadoReserva.PAGADA);
        // reservaRepository.save(reserva);

        return pagoMapper.pagoToDTO(guardado);
    }

    @Override
    @Transactional
    public PagoDTO obtenerDeUsuarioPorReserva(Integer usuarioId, Integer reservaId) {
        // Verificar que la reserva exista y pertenezca al usuario
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada"));

        if (!reserva.getHuesped().getId().equals(usuarioId)) {
            throw new AccesoNoAutorizadoException("La reserva no pertenece al usuario");
        }

        Pago pago = pagoRepository.findByReservaId(reservaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado para la reserva"));

        return pagoMapper.pagoToDTO(pago);
    }
}
