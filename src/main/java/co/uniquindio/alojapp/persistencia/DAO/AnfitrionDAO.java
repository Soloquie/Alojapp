package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.AnfitrionDTO;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroAnfitrionRequest;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.AnfitrionMapper;
import co.uniquindio.alojapp.persistencia.Repository.AnfitrionRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operaciones de persistencia de anfitriones
 */
@Repository
@RequiredArgsConstructor
public class AnfitrionDAO {

    private final AnfitrionRepository anfitrionRepository;
    private final UsuarioRepository usuarioRepository;
    private final AnfitrionMapper anfitrionMapper;

    /**
     * Crear nuevo anfitrión
     */
    public AnfitrionDTO save(RegistroAnfitrionRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Anfitrion anfitrion = Anfitrion.builder()
                .usuario(usuario)
                .descripcionPersonal(request.getDescripcionPersonal())
                .documentosLegalesUrl(request.getDocumentosLegalesUrl())
                .fechaRegistroAnfitrion(LocalDateTime.now())
                .verificado(false)
                .build();

        Anfitrion saved = anfitrionRepository.save(anfitrion);
        return anfitrionMapper.toDTO(saved);
    }

    /**
     * Buscar anfitrión por ID
     */
    public Optional<AnfitrionDTO> findById(Integer id) {
        return anfitrionRepository.findById(id)
                .map(anfitrionMapper::toDTO);
    }

    /**
     * Buscar entity por ID (uso interno)
     */
    public Optional<Anfitrion> findEntityById(Integer id) {
        return anfitrionRepository.findById(id);
    }

    /**
     * Buscar anfitrión por usuario ID
     */
    public Optional<AnfitrionDTO> findByUsuarioId(Integer usuarioId) {
        return anfitrionRepository.findByUsuarioId(usuarioId)
                .map(anfitrionMapper::toDTO);
    }

    /**
     * Verificar si usuario es anfitrión
     */
    public boolean existsByUsuarioId(Integer usuarioId) {
        return anfitrionRepository.existsByUsuarioId(usuarioId);
    }

    /**
     * Buscar todos los anfitriones
     */
    public List<AnfitrionDTO> findAll() {
        return anfitrionMapper.toDTOList(anfitrionRepository.findAll());
    }

    /**
     * Buscar anfitriones verificados
     */
    public List<AnfitrionDTO> findVerificados() {
        return anfitrionMapper.toDTOList(anfitrionRepository.findByVerificado(true));
    }

    /**
     * Actualizar anfitrión
     */
    public Optional<AnfitrionDTO> actualizar(Integer id, String descripcion, String documentosUrl) {
        return anfitrionRepository.findById(id)
                .map(anfitrion -> {
                    if (descripcion != null) {
                        anfitrion.setDescripcionPersonal(descripcion);
                    }
                    if (documentosUrl != null) {
                        anfitrion.setDocumentosLegalesUrl(documentosUrl);
                    }
                    Anfitrion updated = anfitrionRepository.save(anfitrion);
                    return anfitrionMapper.toDTO(updated);
                });
    }

    /**
     * Verificar anfitrión
     */
    public Optional<AnfitrionDTO> verificar(Integer id) {
        return anfitrionRepository.findById(id)
                .map(anfitrion -> {
                    anfitrion.setVerificado(true);
                    Anfitrion updated = anfitrionRepository.save(anfitrion);
                    return anfitrionMapper.toDTO(updated);
                });
    }

    /**
     * Contar alojamientos del anfitrión
     */
    public Long countAlojamientos(Integer anfitrionId) {
        return anfitrionRepository.countAlojamientosByAnfitrionId(anfitrionId);
    }

    /**
     * Contar reservas del anfitrión
     */
    public Long countReservas(Integer anfitrionId) {
        return anfitrionRepository.countReservasByAnfitrionId(anfitrionId);
    }

    /**
     * Buscar anfitriones por ciudad
     */
    public List<AnfitrionDTO> findByCiudad(String ciudad) {
        return anfitrionMapper.toDTOList(
                anfitrionRepository.findByCiudadAlojamientos(ciudad)
        );
    }

    /**
     * Contar total de anfitriones
     */
    public long count() {
        return anfitrionRepository.count();
    }
}