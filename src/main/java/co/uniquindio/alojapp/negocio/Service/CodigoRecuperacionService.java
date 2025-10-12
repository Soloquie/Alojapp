package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.CodigoRecuperacionDTO;

import java.util.List;

/**
 * Servicio de códigos de recuperación de contraseña.
 * RN13: los códigos expiran en 15 minutos.
 * Incluye envío de correo con el código.
 */
public interface CodigoRecuperacionService {

    /**
     * Solicita/genera un nuevo código de recuperación para el usuario con ese email
     * y envía el código por correo.
     *
     * Validaciones:
     *  - Usuario debe existir y estar activo.
     *  - Rate limit: no más de N códigos válidos activos por usuario.
     *  - Anti-spam: no permitir una nueva generación hasta transcurridos X segundos
     *    desde el último código creado.
     *
     * @param email Email del usuario
     * @return DTO del código generado (para trazabilidad/diagnóstico)
     */
    CodigoRecuperacionDTO solicitarCodigo(String email);

    /**
     * Verifica si el código es válido (existe, no usado, no expirado).
     *
     * @param usuarioId Id del usuario
     * @param codigo    Código a verificar
     * @return true si es válido; false en caso contrario
     */
    boolean validarCodigo(Integer usuarioId, String codigo);

    /**
     * Consume el código: lo marca como usado.
     * Lanza excepción si no existe o no es válido.
     *
     * @param usuarioId Id del usuario
     * @param codigo    Código a consumir
     */
    void consumirCodigo(Integer usuarioId, String codigo);

    /**
     * Lista los códigos del usuario (más recientes primero).
     */
    List<CodigoRecuperacionDTO> listarCodigos(Integer usuarioId);

    /**
     * Limpia en BD los códigos expirados.
     */
    void limpiarExpirados();
}
