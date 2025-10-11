package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.config.CloudinaryProperties;
import co.uniquindio.alojapp.negocio.DTO.ImagenDTO;
import co.uniquindio.alojapp.negocio.Service.ImagenService;
import co.uniquindio.alojapp.persistencia.DAO.ImagenDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ImagenServiceIMPL implements ImagenService {

    private final Cloudinary cloudinary;
    private final CloudinaryProperties props;
    private final ImagenDAO imagenDAO;

    // Regex para extraer public_id desde URL:
    // https://res.cloudinary.com/<cloud>/image/upload/<opcs>/vNN/<folder...>/<publicId>.<ext>
    private static final Pattern PUBLIC_ID_FROM_URL =
            Pattern.compile(".*/upload/(?:v\\d+/)?(.+?)\\.[a-zA-Z0-9]+$");

    @Override
    @Transactional
    public ImagenDTO subirImagen(Integer alojamientoId, MultipartFile file, String descripcion) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Archivo de imagen vacío");
        }

        String folder = buildFolder(alojamientoId);
        String publicId = buildPublicId(alojamientoId, UUID.randomUUID().toString());

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
            throw new IOException("No se recibió URL segura desde Cloudinary");
        }

        // Persistir
        return imagenDAO.addImagen(alojamientoId, secureUrl, descripcion, null);
    }

    @Override
    @Transactional
    public List<ImagenDTO> subirImagenes(Integer alojamientoId, List<MultipartFile> files, List<String> descripciones) throws IOException {
        if (files == null || files.isEmpty()) {
            return List.of();
        }
        if (descripciones != null && descripciones.size() != files.size()) {
            throw new IllegalArgumentException("El tamaño de descripciones no coincide con el de archivos");
        }

        String folder = buildFolder(alojamientoId);
        List<ImagenDTO> creadas = new ArrayList<>();

        // Subimos primero a Cloudinary (para evitar RN3 rotas si falla a mitad)
        List<String> urlsSubidas = new ArrayList<>();
        try {
            for (int i = 0; i < files.size(); i++) {
                MultipartFile f = files.get(i);
                if (f == null || f.isEmpty()) {
                    throw new IllegalArgumentException("Archivo vacío en índice " + i);
                }
                String publicId = buildPublicId(alojamientoId, UUID.randomUUID().toString());
                Map<?, ?> result = cloudinary.uploader().upload(
                        f.getBytes(),
                        ObjectUtils.asMap(
                                "folder", folder,
                                "public_id", publicId,
                                "overwrite", true,
                                "resource_type", "image"
                        )
                );
                String secureUrl = (String) result.get("secure_url");
                if (!StringUtils.hasText(secureUrl)) {
                    throw new IOException("Fallo al recibir secure_url para índice " + i);
                }
                urlsSubidas.add(secureUrl);
            }
        } catch (Exception e) {
            // Si falla alguna subida, intentamos limpiar lo que ya subió en esta tanda
            rollbackUploads(urlsSubidas);
            throw e;
        }

        // Ahora persistimos en BD (RN3 valida máximo)
        for (int i = 0; i < urlsSubidas.size(); i++) {
            String desc = (descripciones != null) ? descripciones.get(i) : null;
            creadas.add(imagenDAO.addImagen(alojamientoId, urlsSubidas.get(i), desc, null));
        }
        return creadas;
    }

    @Override
    public List<ImagenDTO> listarPorAlojamiento(Integer alojamientoId) {
        return imagenDAO.findByAlojamiento(alojamientoId);
    }

    @Override
    @Transactional
    public List<ImagenDTO> reordenar(Integer alojamientoId, List<Integer> idsEnOrden) {
        return imagenDAO.reordenar(alojamientoId, idsEnOrden);
    }

    @Override
    @Transactional
    public void eliminarImagen(Integer imagenId) throws IOException {
        // Obtenemos la URL para borrar en Cloudinary y luego borrar en BD
        var imgOpt = imagenDAO.findById(imagenId);
        if (imgOpt.isEmpty()) return;

        String url = imgOpt.get().getUrl();
        String publicId = extractPublicId(url);

        // Primero Cloudinary (si queda 1 en BD, el DAO lanzará excepción al borrar la fila)
        if (StringUtils.hasText(publicId)) {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }
        // Después BD (respeta RN3 "no dejar 0")
        imagenDAO.delete(imagenId);
    }

    @Override
    @Transactional
    public List<ImagenDTO> reemplazarTodas(Integer alojamientoId, List<MultipartFile> nuevas, List<String> descripciones) throws IOException {
        if (nuevas == null || nuevas.isEmpty()) {
            throw new IllegalArgumentException("Debes proporcionar al menos una imagen (RN3)");
        }
        if (descripciones != null && descripciones.size() != nuevas.size()) {
            throw new IllegalArgumentException("El tamaño de descripciones no coincide con el de archivos");
        }

        // 1) Subir todas las nuevas a Cloudinary (para atomicidad)
        String folder = buildFolder(alojamientoId);
        List<String> urlsNuevas = new ArrayList<>();
        try {
            for (int i = 0; i < nuevas.size(); i++) {
                MultipartFile f = nuevas.get(i);
                if (f == null || f.isEmpty()) {
                    throw new IllegalArgumentException("Archivo vacío en índice " + i);
                }
                String publicId = buildPublicId(alojamientoId, UUID.randomUUID().toString());
                Map<?, ?> result = cloudinary.uploader().upload(
                        f.getBytes(),
                        ObjectUtils.asMap(
                                "folder", folder,
                                "public_id", publicId,
                                "overwrite", true,
                                "resource_type", "image"
                        )
                );
                String secureUrl = (String) result.get("secure_url");
                if (!StringUtils.hasText(secureUrl)) {
                    throw new IOException("Fallo al recibir secure_url para índice " + i);
                }
                urlsNuevas.add(secureUrl);
            }
        } catch (Exception e) {
            rollbackUploads(urlsNuevas);
            throw e;
        }

        // 2) Bajar actuales (Cloudinary + BD) y crear nuevas en BD.
        //    Para no violar RN3, hacemos: (a) crear 1 nueva para garantizar >=1, (b) borrar todas las viejas,
        //    (c) crear el resto. Pero es más simple: borrar viejas en Cloudinary al final,
        //    y en BD usar el flujo del DAO (delete impide 0).
        //    Así que: guardamos todas las nuevas en BD, luego eliminamos las viejas (ahora habrá >0 siempre).
        var actuales = imagenDAO.findByAlojamiento(alojamientoId);

        // 2a) Persistir nuevas
        List<ImagenDTO> creadas = new ArrayList<>();
        for (int i = 0; i < urlsNuevas.size(); i++) {
            String desc = (descripciones != null) ? descripciones.get(i) : null;
            creadas.add(imagenDAO.addImagen(alojamientoId, urlsNuevas.get(i), desc, null));
        }

        // 2b) Borrar antiguas (Cloud + BD)
        for (var img : actuales) {
            try {
                String publicId = extractPublicId(img.getUrl());
                if (StringUtils.hasText(publicId)) {
                    cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                }
                imagenDAO.delete(img.getId());
            } catch (Exception ex) {
                // Si falla una, seguimos con las demás (ya hay >=1 nuevas guardadas, no rompemos RN3)
            }
        }

        return imagenDAO.findByAlojamiento(alojamientoId);
    }

    // =========================
    // Helpers
    // =========================

    private String buildFolder(Integer alojamientoId) {
        // p.ej. alojapp/alojamientos/123
        String base = StringUtils.hasText(props.getFolder()) ? props.getFolder() : "alojapp";
        return base + "/" + alojamientoId;
    }

    private String buildPublicId(Integer alojamientoId, String uuid) {
        // p.ej. alojapp/alojamientos/123/<uuid>
        return buildFolder(alojamientoId) + "/" + uuid;
    }

    /** Borra en Cloudinary una lista de URLs subidas si hay que hacer rollback */
    private void rollbackUploads(List<String> secureUrls) {
        for (String url : secureUrls) {
            try {
                String publicId = extractPublicId(url);
                if (StringUtils.hasText(publicId)) {
                    cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                }
            } catch (Exception ignored) {}
        }
    }

    /**
     * Intenta extraer el public_id desde la URL segura.
     * Recomendación: a futuro, añade un campo `publicId` en la entidad Imagen y guarda ese valor directo.
     */
    private String extractPublicId(String secureUrl) {
        if (!StringUtils.hasText(secureUrl)) return null;
        Matcher m = PUBLIC_ID_FROM_URL.matcher(secureUrl);
        if (!m.find()) return null;
        // Si tu `folder` cambia a futuro, igual funciona porque capturamos todo el path relativo tras /upload/
        return m.group(1);
    }
}
