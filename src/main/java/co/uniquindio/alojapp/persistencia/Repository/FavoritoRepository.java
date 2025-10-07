package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones con favoritos
 */
@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

    /**
     * Buscar favoritos de un usuario
     */
    List<Favorito> findByUsuarioIdOrderByFechaAgregadoDesc(Integer usuarioId);

    /**
     * Buscar favorito específico por usuario y alojamiento
     */
    Optional<Favorito> findByUsuarioIdAndAlojamientoId(Integer usuarioId, Integer alojamientoId);

    /**
     * Verificar si un alojamiento es favorito de un usuario
     */
    boolean existsByUsuarioIdAndAlojamientoId(Integer usuarioId, Integer alojamientoId);

    /**
     * Contar favoritos de un alojamiento (popularidad)
     */
    Long countByAlojamientoId(Integer alojamientoId);

    /**
     * Buscar alojamientos más agregados a favoritos
     */
    @Query("SELECT f.alojamiento.id, COUNT(f) as total FROM Favorito f " +
            "GROUP BY f.alojamiento.id " +
            "ORDER BY total DESC")
    List<Object[]> findAlojamientosMasFavoritos();

    /**
     * Eliminar favorito por usuario y alojamiento
     */
    void deleteByUsuarioIdAndAlojamientoId(Integer usuarioId, Integer alojamientoId);
}