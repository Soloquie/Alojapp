// co.uniquindio.alojapp.negocio.Service.impl.AnfitrionServiceIMPL
package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.Service.AnfitrionService;
import co.uniquindio.alojapp.persistencia.DAO.UsuarioDAO;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.AnfitrionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AnfitrionServiceIMPL implements AnfitrionService {

    private final UsuarioDAO usuarioDAO;
    private final AnfitrionRepository anfitrionRepository;

    @Override
    public UsuarioDTO habilitarPerfil(Integer usuarioId, String descripcionPersonal, String documentosLegalesUrl) {
        log.info("[ANFITRION] Habilitando perfil para usuario {}", usuarioId);

        // Asegura que el usuario exista
        Usuario u = usuarioDAO.findEntityById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + usuarioId));

        // Si ya existe, solo actualiza (idempotente)
        Anfitrion existente = anfitrionRepository.findByUsuarioId(usuarioId).orElse(null);
        if (existente != null) {
            if (descripcionPersonal != null) existente.setDescripcionPersonal(descripcionPersonal);
            if (documentosLegalesUrl != null) existente.setDocumentosLegalesUrl(documentosLegalesUrl);
            anfitrionRepository.save(existente);
        } else {
            // Usa helpers del DAO que ya agregaste
            usuarioDAO.crearAnfitrion(usuarioId, descripcionPersonal, documentosLegalesUrl, null);
        }

        return usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se pudo leer el usuario luego de habilitar perfil"));
    }

    @Override
    public UsuarioDTO actualizarPerfil(Integer usuarioId, String descripcionPersonal, String documentosLegalesUrl) {
        log.info("[ANFITRION] Actualizando perfil para usuario {}", usuarioId);

        Anfitrion an = anfitrionRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no tiene perfil de anfitrión"));

        if (descripcionPersonal != null) an.setDescripcionPersonal(descripcionPersonal);
        if (documentosLegalesUrl != null) an.setDocumentosLegalesUrl(documentosLegalesUrl);

        anfitrionRepository.save(an);

        return usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se pudo leer el usuario luego de actualizar perfil"));
    }

    @Override
    public UsuarioDTO deshabilitarPerfil(Integer usuarioId) {
        log.info("[ANFITRION] Deshabilitando perfil para usuario {}", usuarioId);

        usuarioDAO.eliminarAnfitrion(usuarioId); // idempotente

        return usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se pudo leer el usuario luego de deshabilitar perfil"));
    }

    @Override
    public UsuarioDTO marcarVerificado(Integer usuarioId, boolean verificado, boolean adminOverride) {
        log.info("[ANFITRION] Marcando verificado={} para usuario {} (adminOverride={})",
                verificado, usuarioId, adminOverride);

        if (!adminOverride) {
            throw new IllegalArgumentException("No autorizado: se requiere privilegio de administrador.");
        }

        Anfitrion an = anfitrionRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no tiene perfil de anfitrión"));

        an.setVerificado(verificado);
        anfitrionRepository.save(an);

        return usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se pudo leer el usuario luego de cambiar verificación"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean esAnfitrion(Integer usuarioId) {
        return usuarioDAO.esAnfitrion(usuarioId);
    }
}
