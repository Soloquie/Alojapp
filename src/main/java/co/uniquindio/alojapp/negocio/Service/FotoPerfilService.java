package co.uniquindio.alojapp.negocio.Service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FotoPerfilService {
    String subirFotoPerfil(Integer usuarioId, MultipartFile file) throws IOException; // retorna secure_url
    void eliminarFotoPerfil(String currentUrl) throws IOException; // opcional
}
