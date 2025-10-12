package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.FavoritoDTO;

import java.util.List;

public interface FavoritoService {

    /** Agrega un alojamiento a favoritos del usuario dado. */
    FavoritoDTO agregar(Integer userId, Integer alojamientoId);

    /** Elimina un alojamiento de favoritos del usuario dado. */
    void eliminar(Integer userId, Integer alojamientoId);

    /** Lista de favoritos del usuario, más recientes primero. */
    List<FavoritoDTO> listarPorUsuario(Integer userId);

    /** ¿El alojamiento indicado es favorito de ese usuario? */
    boolean esFavorito(Integer userId, Integer alojamientoId);

    /** Cantidad de usuarios que marcaron como favorito el alojamiento. */
    Long contarFavoritosDeAlojamiento(Integer alojamientoId);

    /** Ranking global de alojamientos más favoritos (alojamientoId, total). */
    List<MasFavoritosItem> topMasFavoritos(int limit);

    /** Item simple para el ranking. */
    record MasFavoritosItem(Integer alojamientoId, Long total) {}
}
