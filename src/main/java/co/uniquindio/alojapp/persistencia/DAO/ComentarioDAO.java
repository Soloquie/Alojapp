package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.ComentarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearComentarioRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.persistencia.Entity.Comentario;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.ComentarioMapper;
import co.uniquindio.alojapp.persistencia.Repository.ComentarioRepository;
import co.uniquindio.alojapp.persistencia.Repository.AlojamientoRepository;
import co.uniquindio.alojapp.persistencia.Repository.ReservaRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operaciones de persistencia de comentarios
 * Implementa RN7, RN8, RN16, RN17
 */
@Repository
@RequiredArgsConstructor
public class ComentarioDAO {

    private final ComentarioRepository comentarioRepository;
    private final ReservaRepository reservaRepository;
    private final AlojamientoRepository alojamientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioMapper comentarioMapper;

    /**
     * Crear nuevo comentario
     * RN16: Solo reservas completadas
     * RN17: Máximo un comentario por reserva
     * RN7: Máximo 500 caracteres
     * RN8: Calificación 1-5
     */
    public ComentarioDTO save(CrearComentarioRequest request, Integer usuarioId) {
        // Validar que no exista comentario para esta reserva
        if (comentarioRepository.existsByReservaId(request.getReservaId())) {
            throw new RuntimeException("Ya existe un comentario para esta reserva");
        }

        Reserva reserva = reservaRepository.findById(request.getReservaId())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Validar que la reserva pertenezca al usuario
        if (!reserva.getHuesped().getId().equals(usuarioId)) {
            throw new RuntimeException("No tienes permiso para comentar esta reserva");
        }


        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alojamiento alojamiento = reserva.getAlojamiento();

        Comentario comentario = Comentario.builder()
                .reserva(reserva)
                .autor(usuario)
                .alojamiento(alojamiento)
                .calificacion(request.getCalificacion())
                .comentarioTexto(request.getComentarioTexto())
                .fechaComentario(LocalDateTime.now())
                .build();

        Comentario saved = comentarioRepository.save(comentario);
        return comentarioMapper.toDTO(saved);
    }

    /**
     * Buscar comentario por ID
     */
    public Optional<ComentarioDTO> findById(Integer id) {
        return comentarioRepository.findById(id)
                .map(comentarioMapper::toDTO);
    }

    /**
     * Buscar entity por ID
     */
    public Optional<Comentario> findEntityById(Integer id) {
        return comentarioRepository.findById(id);
    }

    /**
     * Buscar comentarios por alojamiento
     */
    public List<ComentarioDTO> findByAlojamiento(Integer alojamientoId) {
        return comentarioMapper.toDTOList(
                comentarioRepository.findByAlojamientoId(alojamientoId)
        );
    }

    public PaginacionResponse<ComentarioDTO> findByAlojamiento(Integer alojamientoId, int pagina, int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        Page<Comentario> page = comentarioRepository.findByAlojamientoIdOrderByFechaComentarioDesc(
                alojamientoId, pageable
        );

        return buildPaginacionResponse(page);
    }

    /**
     * Buscar comentarios por usuario
     */
    public List<ComentarioDTO> findByUsuario(Integer usuarioId) {
        return comentarioMapper.toDTOList(
                comentarioRepository.findByAutorId(usuarioId)
        );
    }

    /**
     * Buscar comentarios por anfitrión (todos sus alojamientos)
     */
    public List<ComentarioDTO> findByAnfitrion(Integer anfitrionId) {
        return comentarioMapper.toDTOList(
                comentarioRepository.findByAnfitrionId(anfitrionId)
        );
    }

    public PaginacionResponse<ComentarioDTO> findByAnfitrion(Integer anfitrionId, int pagina, int tamanoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanoPagina);
        Page<Comentario> page = comentarioRepository.findByAnfitrionIdOrderByFechaDesc(
                anfitrionId, pageable
        );

        return buildPaginacionResponse(page);
    }

    /**
     * Buscar comentarios sin respuesta de un anfitrión
     */
    public List<ComentarioDTO> findSinRespuestaByAnfitrion(Integer anfitrionId) {
        return comentarioMapper.toDTOList(
                comentarioRepository.findComentariosSinRespuestaByAnfitrion(anfitrionId)
        );
    }

    /**
     * Calcular calificación promedio de un alojamiento
     */
    public Double calcularCalificacionPromedio(Integer alojamientoId) {
        Double promedio = comentarioRepository.calcularCalificacionPromedio(alojamientoId);
        return promedio != null ? promedio : 0.0;
    }

    /**
     * Contar comentarios por alojamiento
     */
    public Long countByAlojamiento(Integer alojamientoId) {
        return comentarioRepository.countByAlojamientoId(alojamientoId);
    }

    /**
     * Verificar si existe comentario para una reserva
     */
    public boolean existeComentarioParaReserva(Integer reservaId) {
        return comentarioRepository.existsByReservaId(reservaId);
    }

    /**
     * Buscar mejores comentarios (calificación 4-5)
     */
    public List<ComentarioDTO> findMejoresComentarios(Integer alojamientoId) {
        return comentarioMapper.toDTOList(
                comentarioRepository.findMejoresComentarios(alojamientoId)
        );
    }

    // Helper para paginación
    private PaginacionResponse<ComentarioDTO> buildPaginacionResponse(Page<Comentario> page) {
        List<ComentarioDTO> dtos = comentarioMapper.toDTOList(page.getContent());

        return PaginacionResponse.<ComentarioDTO>builder()
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