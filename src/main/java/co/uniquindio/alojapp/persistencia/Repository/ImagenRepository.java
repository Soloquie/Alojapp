package co.uniquindio.alojapp.persistencia.Repository;

import co.uniquindio.alojapp.persistencia.Entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones con imágenes de alojamientos
 * Implementa RN3: Al menos 1 imagen, máximo 10 imágenes
 */
@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Integer> {

    /**
     * Buscar imágenes por alojamiento ordenadas
     */
    List<Imagen> findByAlojamientoIdOrderByOrden(Integer alojamientoId);

    /**
     * Contar imágenes de un alojamiento
     * RN3: Validar límite de 10 imágenes
     */
    Long countByAlojamientoId(Integer alojamientoId);

    /**
     * Verificar si un alojamiento tiene imágenes
     * RN3: Debe tener al menos 1 imagen
     */
    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Imagen i " +
            "WHERE i.alojamiento.id = :alojamientoId")
    boolean tieneImagenes(@Param("alojamientoId") Integer alojamientoId);

    /**
     * Eliminar todas las imágenes de un alojamiento
     */
    void deleteByAlojamientoId(Integer alojamientoId);

    /**
     * Buscar imagen por URL
     */
    List<Imagen> findByUrlImagen(String urlImagen);
}