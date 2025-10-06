package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.FavoritoDTO;
import co.uniquindio.alojapp.persistencia.Entity.Favorito;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.FavoritoMapper;
import co.uniquindio.alojapp.persistencia.Repository.FavoritoRepository;
import co.uniquindio.alojapp.persistencia.Repository.AlojamientoRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operaciones de persistencia de favoritos
 */
@Repository
@RequiredArgsConstructor
public class FavoritoDAO {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlojamientoRepository alojamientoRepository;
    private final FavoritoMapper favoritoMapper;

    /**
     * Agregar alojamiento a favoritos
     */
    public FavoritoDTO save(Long usuarioId, Long alojamientoId) {
        // Validar que no exista ya
        if (favoritoRepository.existsByUsuarioIdAndAlojamientoId(usuarioId, alojamientoId)) {
            throw new RuntimeException("El alojamiento ya está en favoritos");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        Favorito favorito = Favorito.builder()
                .usuario(usuario)
                .alojamiento(alojamiento)
                .fechaAgregado(LocalDateTime.now())
                .build();

        Favorito saved = favoritoRepository.save(favorito);
        return favoritoMapper.toDTO(saved);
    }

    /**
     * Buscar favoritos por usuario
     */
    public List<FavoritoDTO> findByUsuario(Long usuarioId) {
        return favoritoMapper.toDTOList(
                favoritoRepository.findByUsuarioIdOrderByFechaAgregadoDesc(usuarioId)
        );
    }

    /**
     * Verificar si es favorito
     */
    public boolean esFavorito(Long usuarioId, Long alojamientoId) {
        return favoritoRepository.existsByUsuarioIdAndAlojamientoId(usuarioId, alojamientoId);
    }

    /**
     * Eliminar de favoritos
     */
    @Transactional
    public boolean eliminar(Long usuarioId, Long alojamientoId) {
        if (favoritoRepository.existsByUsuarioIdAndAlojamientoId(usuarioId, alojamientoId)) {
            favoritoRepository.deleteByUsuarioIdAndAlojamientoId(usuarioId, alojamientoId);
            return true;
        }
        return false;
    }

    /**
     * Contar favoritos de un alojamiento
     */
    public Long countByAlojamiento(Long alojamientoId) {
        return favoritoRepository.countByAlojamientoId(alojamientoId);
    }

    /**
     * Alojamientos más favoritos
     */
    public List<Object[]> findAlojamientosMasFavoritos() {
        return favoritoRepository.findAlojamientosMasFavoritos();
    }
}