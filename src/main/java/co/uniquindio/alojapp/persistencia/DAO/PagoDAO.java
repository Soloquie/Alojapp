package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.PagoDTO;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.excepciones.ReglaNegocioException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.Entity.Pago;
import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.PagoMapper;
import co.uniquindio.alojapp.persistencia.Repository.PagoRepository;
import co.uniquindio.alojapp.persistencia.Repository.ReservaRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * DAO de pagos: orquesta la persistencia, mapeo y validaciones básicas
 * relacionadas a pagos de reservas (sin acoplar reglas de pasarela).
 *
 * Convenciones de excepciones:
 *  - RecursoNoEncontradoException -> 404 (pago / reserva / usuario inexistentes)
 *  - ReglaNegocioException        -> 400 (ya tiene pago, no pertenece al usuario, transición de estado inválida, etc.)
 */
@Repository
@RequiredArgsConstructor
public class PagoDAO {

    private final PagoRepository pagoRepository;
    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagoMapper pagoMapper;

    // =============================
    // Crear / Registrar pago
    // =============================

    /**
     * Registra un pago para una reserva (sin integrar pasarela).
     *
     * Validaciones mínimas:
     *  - reserva existe
     *  - usuario existe
     *  - la reserva pertenece al usuario (huésped)
     *  - la reserva no tiene ya un pago registrado
     *
     * @param reservaId    id de la reserva
     * @param usuarioId    id del huésped que paga
     * @param monto        monto a registrar (BigDecimal para evitar pérdida; se almacena en double en la entidad)
     * @param metodoPago   método (p.e. "TARJETA_CREDITO", "PSE")
     * @param estadoInicial estado a registrar (por defecto "APROBADO" si es null/blank)
     */
    public PagoDTO crearParaReserva(Integer reservaId,
                                    Integer usuarioId,
                                    BigDecimal monto,
                                    String metodoPago,
                                    String estadoInicial) {

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if (!Objects.equals(reserva.getHuesped().getId(), usuarioId)) {
            throw new ReglaNegocioException("La reserva no pertenece al usuario autenticado");
        }

        if (pagoRepository.findByReservaId(reservaId).isPresent()) {
            throw new ReglaNegocioException("La reserva ya tiene un pago registrado");
        }

        String estado = (estadoInicial == null || estadoInicial.isBlank())
                ? "APROBADO"
                : estadoInicial.toUpperCase(Locale.ROOT);

        Pago pago = Pago.builder()
                .monto(monto != null ? monto.doubleValue() : 0d)
                .metodoPago(metodoPago)
                .estado(estado)
                .fechaPago(LocalDateTime.now())
                .reserva(reserva)
                .usuario(usuario)
                .build();

        Pago saved = pagoRepository.save(pago);
        return pagoMapper.pagoToDTO(saved);
    }

    // =============================
    // Consultas básicas
    // =============================

    public Optional<PagoDTO> findById(Integer pagoId) {
        return pagoRepository.findById(pagoId).map(pagoMapper::pagoToDTO);
    }

    public Optional<PagoDTO> findByReserva(Integer reservaId) {
        return pagoRepository.findByReservaId(reservaId).map(pagoMapper::pagoToDTO);
    }

    public List<PagoDTO> listarPorUsuario(Integer usuarioId) {
        return pagoMapper.pagosToDTO(pagoRepository.findByUsuarioIdOrderByFechaPagoDesc(usuarioId));
    }

    public PaginacionResponse<PagoDTO> listarPorUsuario(Integer usuarioId, int pagina, int tamano) {
        Pageable pageable = PageRequest.of(pagina, tamano, Sort.by("fechaPago").descending());
        Page<Pago> page = new PageImpl<>(
                pagoRepository.findByUsuarioIdOrderByFechaPagoDesc(usuarioId),
                pageable,
                // como el repo devuelve lista, aproximamos total=lista.size()
                // si prefieres verdadero paginado, crea un método en repo que devuelva Page<Pago>
                pagoRepository.findByUsuarioIdOrderByFechaPagoDesc(usuarioId).size()
        );
        return buildPaginacion(page);
    }

    public List<PagoDTO> listarPorEstado(String estado) {
        return pagoMapper.pagosToDTO(pagoRepository.findByEstado(estado));
    }

    public List<PagoDTO> listarPorMetodo(String metodo) {
        return pagoMapper.pagosToDTO(pagoRepository.findByMetodoPago(metodo));
    }

    public List<PagoDTO> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return pagoMapper.pagosToDTO(pagoRepository.findByFechaPagoBetween(inicio, fin));
    }

    public List<PagoDTO> listarAprobadosPorAnfitrion(Integer anfitrionId) {
        return pagoMapper.pagosToDTO(pagoRepository.findPagosAprobadosByAnfitrion(anfitrionId));
    }

    // =============================
    // Agregaciones
    // =============================

    public BigDecimal totalPagadoPorUsuario(Integer usuarioId) {
        BigDecimal total = pagoRepository.calcularTotalPagadoPorUsuario(usuarioId);
        return total != null ? total : BigDecimal.ZERO;
    }

    public Long contarPorEstado(String estado) {
        return pagoRepository.countByEstado(estado);
    }

    // =============================
    // Operaciones de actualización
    // =============================

    /**
     * Cambia el estado de un pago (p.e., APROBADO → REEMBOLSADO).
     * Define aquí transiciones válidas mínimas. Ajusta según tu dominio.
     */
    public PagoDTO actualizarEstado(Integer pagoId, String nuevoEstado) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado"));

        String destino = (nuevoEstado == null) ? "" : nuevoEstado.toUpperCase(Locale.ROOT);

        // Transiciones válidas simples
        Set<String> validos = Set.of("APROBADO", "RECHAZADO", "REEMBOLSADO", "PENDIENTE");
        if (!validos.contains(destino)) {
            throw new ReglaNegocioException("Estado de pago inválido: " + nuevoEstado);
        }

        // Ejemplos de restricción (ajusta a tus RN):
        // - Si ya está REEMBOLSADO, no permitir otros cambios
        if ("REEMBOLSADO".equalsIgnoreCase(pago.getEstado())) {
            throw new ReglaNegocioException("El pago ya fue reembolsado");
        }

        pago.setEstado(destino);
        Pago actualizado = pagoRepository.save(pago);
        return pagoMapper.pagoToDTO(actualizado);
    }

    // =============================
    // Helpers
    // =============================

    private PaginacionResponse<PagoDTO> buildPaginacion(Page<Pago> page) {
        List<PagoDTO> contenido = pagoMapper.pagosToDTO(page.getContent());
        return PaginacionResponse.<PagoDTO>builder()
                .contenido(contenido)
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
