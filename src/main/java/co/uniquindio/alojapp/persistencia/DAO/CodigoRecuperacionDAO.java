package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.CodigoRecuperacionDTO;
import co.uniquindio.alojapp.persistencia.Entity.CodigoRecuperacion;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.CodigoRecuperacionMapper;
import co.uniquindio.alojapp.persistencia.Repository.CodigoRecuperacionRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * DAO para operaciones de persistencia de códigos de recuperación
 * Implementa RN13: Los códigos expiran en 15 minutos
 */
@Repository
@RequiredArgsConstructor
public class CodigoRecuperacionDAO {

    private final CodigoRecuperacionRepository codigoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CodigoRecuperacionMapper codigoMapper;

    /**
     * Generar y guardar código de recuperación
     * RN13: Validez de 15 minutos
     */
    public CodigoRecuperacionDTO generarCodigo(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar código aleatorio de 6 dígitos
        String codigo = generarCodigoAleatorio();

        CodigoRecuperacion codigoRecuperacion = CodigoRecuperacion.builder()
                .usuario(usuario)
                .codigo(codigo)
                .fechaExpiracion(LocalDateTime.now().plusMinutes(15))
                .usado(false)
                .build();

        CodigoRecuperacion saved = codigoRepository.save(codigoRecuperacion);
        return codigoMapper.toDTO(saved);
    }

    /**
     * Buscar código válido
     */
    public Optional<CodigoRecuperacionDTO> findCodigoValido(Long usuarioId, String codigo) {
        return codigoRepository.findCodigoValido(usuarioId, codigo, LocalDateTime.now())
                .map(codigoMapper::toDTO);
    }

    /**
     * Marcar código como usado
     */
    public boolean marcarComoUsado(Long codigoId) {
        return codigoRepository.findById(codigoId)
                .map(codigo -> {
                    codigo.setUsado(true);
                    codigoRepository.save(codigo);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Buscar códigos por usuario
     */
    public List<CodigoRecuperacionDTO> findByUsuario(Long usuarioId) {
        return codigoMapper.toDTOList(
                codigoRepository.findByUsuarioIdOrderByFechaExpiracionDesc(usuarioId)
        );
    }

    /**
     * Eliminar códigos expirados
     */
    public void limpiarCodigosExpirados() {
        codigoRepository.eliminarCodigosExpirados(LocalDateTime.now());
    }

    /**
     * Contar códigos válidos de un usuario
     */
    public Long countCodigosValidos(Long usuarioId) {
        return codigoRepository.countCodigosValidosByUsuario(usuarioId, LocalDateTime.now());
    }

    // Método auxiliar para generar código aleatorio
    private String generarCodigoAleatorio() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }
}