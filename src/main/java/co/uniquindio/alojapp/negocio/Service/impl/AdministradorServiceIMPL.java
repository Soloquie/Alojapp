package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.AdministradorDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAdministradorRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAdministradorRequest;
import co.uniquindio.alojapp.negocio.Service.AdministradorService;
import co.uniquindio.alojapp.persistencia.Entity.Administrador;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.AdministradorMapper;
import co.uniquindio.alojapp.persistencia.Repository.AdministradorRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdministradorServiceIMPL implements AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final UsuarioRepository usuarioRepository;
    private final AdministradorMapper administradorMapper;
    private final ObjectMapper objectMapper = new ObjectMapper(); // para validar JSON si llega

    private static final Set<String> NIVELES_VALIDOS =
            Set.of("SUPER_ADMIN", "ADMIN", "MODERADOR");

    @Override
    public AdministradorDTO asignar(CrearAdministradorRequest request) {
        Integer usuarioId = Objects.requireNonNull(request.getUsuarioId(), "usuarioId es obligatorio");
        log.info("Asignando rol administrador a usuario {}", usuarioId);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe: " + usuarioId));

        if (administradorRepository.existsByUsuarioId(usuarioId)) {
            throw new IllegalStateException("El usuario ya es administrador");
        }

        String nivel = normalizarNivel(Optional.ofNullable(request.getNivelAcceso()).orElse("ADMIN"));
        validarNivel(nivel);

        String permisos = validarJsonONull(request.getPermisosJson()); // puede ser null

        Administrador admin = Administrador.builder()
                .usuario(usuario)
                .nivelAcceso(nivel)
                .permisos(permisos)
                // fechaAsignacion la setea @PrePersist si es null
                .build();

        Administrador saved = administradorRepository.save(admin);
        return administradorMapper.toDTO(saved);
    }

    @Override
    public void revocarPorUsuario(Integer usuarioId) {
        log.info("Revocando rol administrador de usuario {}", usuarioId);
        if (!administradorRepository.existsByUsuarioId(usuarioId)) return; // idempotente
        administradorRepository.deleteByUsuarioId(usuarioId); // <- ver nota de repos abajo
    }

    @Override
    public AdministradorDTO actualizar(Integer administradorId, ActualizarAdministradorRequest request) {
        log.info("Actualizando administrador {}", administradorId);

        Administrador admin = administradorRepository.findById(administradorId)
                .orElseThrow(() -> new IllegalArgumentException("Administrador no existe: " + administradorId));

        if (request.getNivelAcceso() != null) {
            String nivel = normalizarNivel(request.getNivelAcceso());
            validarNivel(nivel);
            admin.setNivelAcceso(nivel);
        }
        if (request.getPermisosJson() != null) {
            admin.setPermisos(validarJsonONull(request.getPermisosJson()));
        }

        Administrador updated = administradorRepository.save(admin);
        return administradorMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public AdministradorDTO obtenerPorId(Integer administradorId) {
        return administradorRepository.findById(administradorId)
                .map(administradorMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado: " + administradorId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministradorDTO> obtenerPorUsuarioId(Integer usuarioId) {
        return administradorRepository.findByUsuarioId(usuarioId).map(administradorMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdministradorDTO> listar() {
        return administradorMapper.toDTOList(administradorRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdministradorDTO> listarPorNivel(String nivelAcceso) {
        String nivel = normalizarNivel(nivelAcceso);
        validarNivel(nivel);
        return administradorMapper.toDTOList(administradorRepository.findByNivelAcceso(nivel));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean esAdministrador(Integer usuarioId) {
        return administradorRepository.existsByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public long contar() {
        return administradorRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long contarPorNivel(String nivelAcceso) {
        String nivel = normalizarNivel(nivelAcceso);
        validarNivel(nivel);
        return Optional.ofNullable(administradorRepository.countByNivelAcceso(nivel)).orElse(0L);
    }

    // ==== helpers ====

    private String normalizarNivel(String nivel) {
        return (nivel == null) ? null : nivel.trim().toUpperCase(Locale.ROOT);
    }

    private void validarNivel(String nivel) {
        if (nivel == null || !NIVELES_VALIDOS.contains(nivel)) {
            throw new IllegalArgumentException("Nivel de acceso inválido: " + nivel +
                    " (válidos: " + String.join(", ", NIVELES_VALIDOS) + ")");
        }
    }

    private String validarJsonONull(String json) {
        if (json == null || json.isBlank()) return null;
        try {
            objectMapper.readTree(json); // valida
            return json;
        } catch (Exception e) {
            throw new IllegalArgumentException("permisosJson no es un JSON válido");
        }
    }
}
