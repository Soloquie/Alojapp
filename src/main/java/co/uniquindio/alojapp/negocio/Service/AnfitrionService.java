// co.uniquindio.alojapp.negocio.Service.AnfitrionService
package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;

public interface AnfitrionService {

    /** Crea (o asegura) el perfil de anfitrión para el usuario. */
    UsuarioDTO habilitarPerfil(Integer usuarioId, String descripcionPersonal, String documentosLegalesUrl);

    /** Actualiza campos del perfil de anfitrión (parcial). */
    UsuarioDTO actualizarPerfil(Integer usuarioId, String descripcionPersonal, String documentosLegalesUrl);

    /** Quita el perfil de anfitrión (no borra el usuario). */
    UsuarioDTO deshabilitarPerfil(Integer usuarioId);

    /** Marca/verifica al anfitrión (acción de admin). */
    UsuarioDTO marcarVerificado(Integer usuarioId, boolean verificado, boolean adminOverride);

    /** Consulta rápida. */
    boolean esAnfitrion(Integer usuarioId);
}
