package com.copernic.projecte2_openroad.repository.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mongodb.Comentari;

@Repository
public interface ComentariRepositoryMongo extends MongoRepository<Comentari, String> {
    List<Comentari> findComentariByMatriculaVehicle(String matriculaVehicle);
}