/**
 * Interfície que representa el repositori per accedir a la col·lecció "comentaris" a MongoDB.
 * Proporciona mètodes per gestionar els comentaris emmagatzemats.
 */
package com.copernic.projecte2_openroad.repository.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mongodb.Comentari;

/**
 * Repositori MongoDB per a l'entitat {@link Comentari}.
 */
@Repository
public interface ComentariRepositoryMongo extends MongoRepository<Comentari, String> {

    /**
     * Retorna una llista de comentaris associats a una matrícula específica de vehicle.
     *
     * @param matriculaVehicle la matrícula del vehicle associada als comentaris.
     * @return una llista de comentaris associats a la matrícula del vehicle.
     */
    List<Comentari> findComentariByMatriculaVehicle(String matriculaVehicle);
}
