package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.CodigoRecuperacionDTO;
import co.uniquindio.alojapp.negocio.Service.CodigoRecuperacionService;
import co.uniquindio.alojapp.persistencia.DAO.CodigoRecuperacionDAO;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodigoRecuperacionServiceIMPL implements CodigoRecuperacionService {

    private final CodigoRecuperacionDAO codigoDAO;
    private final UsuarioRepository usuarioRepository;
    private final JavaMailSender mailSender;

    // ====== Parámetros de negocio/config ======
    @Value("${app.recuperacion.max-codigos-activos:3}")
    private int maxCodigosActivos; // p.ej. permitir hasta 3 códigos vigentes

    @Value("${app.recuperacion.cooldown-segundos:60}")
    private int cooldownSegundos;  // no permitir regenerar antes de X seg.

    @Value("${app.mail.from:no-reply@alojapp.com}")
    private String fromEmail;

    @Value("${app.frontend.reset-url:https://app.tudominio.com/reset-password}")
    private String resetUrlBase;

    // ====== API ======

    @Override
    @Transactional
    public CodigoRecuperacionDTO solicitarCodigo(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Debes proporcionar un email");
        }

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con ese email"));

        // RN: limitar cantidad de códigos activos
        long activos = codigoDAO.countCodigosValidos(usuario.getId());
        if (activos >= maxCodigosActivos) {
            throw new IllegalStateException("Límite de códigos activos alcanzado. Intenta más tarde.");
        }

        // Anti-spam: cooldown entre generaciones
        var historial = codigoDAO.findByUsuario(usuario.getId());
        if (!historial.isEmpty()) {
            var ultimo = historial.getFirst(); // vienen desc por fecha_expiracion
            // Para saber cuándo se creó, inferimos a partir de la expiración (exp = creado + 15m)
            // Si cambias la ventana de expiración, actualiza este cálculo.
            LocalDateTime creadoAprox = ultimo.getFechaExpiracion().minusMinutes(15);
            if (Duration.between(creadoAprox, LocalDateTime.now()).getSeconds() < cooldownSegundos) {
                throw new IllegalStateException("Solicitud muy frecuente. Intenta nuevamente en unos segundos.");
            }
        }

        // Generar y persistir
        CodigoRecuperacionDTO dto = codigoDAO.generarCodigo(usuario.getId());

        // Enviar correo real
        enviarCorreoCodigo(usuario.getNombre(), email, dto.getCodigo());

        log.info("Código de recuperación generado y enviado. userId={}, email={}", usuario.getId(), email);
        return dto;
    }

    @Override
    public boolean validarCodigo(Integer usuarioId, String codigo) {
        if (usuarioId == null || !StringUtils.hasText(codigo)) return false;
        return codigoDAO.findCodigoValido(usuarioId, codigo).isPresent();
    }

    @Override
    @Transactional
    public void consumirCodigo(Integer usuarioId, String codigo) {
        var validoOpt = codigoDAO.findCodigoValido(usuarioId, codigo);
        if (validoOpt.isEmpty()) {
            throw new IllegalArgumentException("Código inválido o expirado");
        }
        boolean ok = codigoDAO.marcarComoUsado(validoOpt.get().getId());
        if (!ok) {
            throw new IllegalStateException("No fue posible consumir el código");
        }
        log.info("Código consumido. userId={}, codigoId={}", usuarioId, validoOpt.get().getId());
    }

    @Override
    public List<CodigoRecuperacionDTO> listarCodigos(Integer usuarioId) {
        if (usuarioId == null) throw new IllegalArgumentException("usuarioId es obligatorio");
        return codigoDAO.findByUsuario(usuarioId);
    }

    @Override
    @Transactional
    public void limpiarExpirados() {
        codigoDAO.limpiarCodigosExpirados();
    }

    // ====== Envío de correo ======

    private void enviarCorreoCodigo(String nombre, String toEmail, String codigo) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Código de recuperación de contraseña");

            String resetLink = resetUrlBase + "?code=" + codigo + "&email=" + toEmail;

            String html = """
                <div style="font-family:Arial,sans-serif;line-height:1.5">
                  <h2>Hola %s,</h2>
                  <p>Recibimos una solicitud para restablecer tu contraseña en <b>Alojapp</b>.</p>
                  <p>Tu código de recuperación es:</p>
                  <p style="font-size:22px;font-weight:bold;letter-spacing:2px">%s</p>
                  <p>Este código expira en <b>15 minutos</b>.</p>
                  <p>Puedes introducirlo en la app o hacer clic aquí para continuar:</p>
                  <p><a href="%s" target="_blank">%s</a></p>
                  <hr/>
                  <p style="color:#666">Si no fuiste tú, ignora este mensaje.</p>
                </div>
                """.formatted(escape(nombre), codigo, resetLink, resetLink);

            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error enviando correo de recuperación a {}: {}", toEmail, e.getMessage());
            // Importante: decide si quieres fallar la solicitud cuando el mail no sale.
            // Aquí lanzamos excepción para que el cliente lo sepa.
            throw new IllegalStateException("No fue posible enviar el correo de recuperación");
        }
    }

    private String escape(String s) {
        return (s == null) ? "" : s.replace("<", "&lt;").replace(">", "&gt;");
    }
}
