package com.copernic.projecte2_openroad.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mongodb.HistoricComentari;

@Repository
public interface ComentariRepositoryMongo extends MongoRepository<HistoricComentari, String> {
}
