package com.copernic.projecte2_openroad.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.copernic.projecte2_openroad.model.mongodb.HistoricDocument;

@Repository
public interface DocumentRepositoryMongo extends MongoRepository<HistoricDocument, String>{

}
