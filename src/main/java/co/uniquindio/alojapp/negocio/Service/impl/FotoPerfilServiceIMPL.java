package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.config.CloudinaryProperties;
import co.uniquindio.alojapp.negocio.Service.FotoPerfilService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.regex.*;

@Service
@RequiredArgsConstructor
public class FotoPerfilServiceIMPL implements FotoPerfilService {

    private final Cloudinary cloudinary;
    private final CloudinaryProperties props;

    private static final Pattern PUBLIC_ID_FROM_URL =
            Pattern.compile(".*/upload/(?:v\\d+/)?(.+?)\\.[a-zA-Z0-9]+$");

    // FotoPerfilServiceIMPL.subirFotoPerfil
    @Override
    public String subirFotoPerfil(Integer usuarioId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty())
            throw new IllegalArgumentException("Archivo vacío");

        String folder = (StringUtils.hasText(props.getFolder()) ? props.getFolder() : "alojapp")
                + "/usuarios/" + usuarioId;
        String publicId = folder + "/" + UUID.randomUUID();

        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "public_id", publicId,
                            "overwrite", true,
                            "resource_type", "image"
                    )
            );
            String secureUrl = (String) result.get("secure_url");
            if (!StringUtils.hasText(secureUrl)) {
                throw new IOException("Cloudinary no devolvió secure_url");
            }
            return secureUrl;
        } catch (Exception e) {
            // Aquí verás la causa exacta en la respuesta
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Error subiendo a Cloudinary: " + e.getClass().getSimpleName() + " - " + e.getMessage(),
                    e
            );
        }
    }



    @Override
    public void eliminarFotoPerfil(String currentUrl) throws IOException {
        if (!StringUtils.hasText(currentUrl)) return;
        Matcher m = PUBLIC_ID_FROM_URL.matcher(currentUrl);
        if (m.find()) {
            cloudinary.uploader().destroy(m.group(1), ObjectUtils.emptyMap());
        }
    }
}
