// co.uniquindio.alojapp.negocio.Service.impl.FavoritoServiceIMPL
package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.FavoritoDTO;
import co.uniquindio.alojapp.negocio.Service.FavoritoService;
import co.uniquindio.alojapp.persistencia.DAO.FavoritoDAO;
import co.uniquindio.alojapp.negocio.excepciones.BadRequestException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritoServiceIMPL implements FavoritoService {

    private final FavoritoDAO favoritoDAO;

    // ==== Validaciones comunes ====
    private void validarIdPositivo(String nombre, Integer id) {
        if (id == null) throw new BadRequestException(nombre + " es obligatorio");
        if (id <= 0) throw new BadRequestException(nombre + " debe ser positivo");
    }

    private int normalizarLimite(int limit) {
        if (limit <= 0) return 10;
        return Math.min(limit, 50);
    }

    @Override
    @Transactional
    public FavoritoDTO agregar(Integer userId, Integer alojamientoId) {
        validarIdPositivo("userId", userId);
        validarIdPositivo("alojamientoId", alojamientoId);

        try {
            // El DAO valida duplicados y existencia de usuario/alojamiento.
            return favoritoDAO.save(userId, alojamientoId);
        } catch (RuntimeException e) {
            // Normalizamos a 400 cuando es una regla de negocio conocida (duplicado, inexistente, etc.)
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void eliminar(Integer userId, Integer alojamientoId) {
        validarIdPositivo("userId", userId);
        validarIdPositivo("alojamientoId", alojamientoId);

        boolean eliminado = favoritoDAO.eliminar(userId, alojamientoId);
        if (!eliminado) {
            // Si prefieres que sea silencioso (idempotente), borra esta excepción y simplemente retorna.
            throw new RecursoNoEncontradoException("El alojamiento no estaba en tus favoritos");
        }
    }

    @Override
    public List<FavoritoDTO> listarPorUsuario(Integer userId) {
        validarIdPositivo("userId", userId);
        return favoritoDAO.findByUsuario(userId);
    }

    @Override
    public boolean esFavorito(Integer userId, Integer alojamientoId) {
        validarIdPositivo("userId", userId);
        validarIdPositivo("alojamientoId", alojamientoId);
        return favoritoDAO.esFavorito(userId, alojamientoId);
    }

    @Override
    public Long contarFavoritosDeAlojamiento(Integer alojamientoId) {
        validarIdPositivo("alojamientoId", alojamientoId);
        return favoritoDAO.countByAlojamiento(alojamientoId);
    }

    @Override
    public List<MasFavoritosItem> topMasFavoritos(int limit) {
        int lim = normalizarLimite(limit);
        var filas = favoritoDAO.findAlojamientosMasFavoritos();

        List<MasFavoritosItem> salida = new ArrayList<>(Math.min(lim, filas.size()));
        for (Object[] r : filas) {
            // Query retorna: [alojamientoId(Integer), total(Long)]
            Integer alojId = (Integer) r[0];
            Long total = (Long) r[1];
            salida.add(new MasFavoritosItem(alojId, total));
            if (salida.size() >= lim) break;
        }
        return salida;
    }
}
