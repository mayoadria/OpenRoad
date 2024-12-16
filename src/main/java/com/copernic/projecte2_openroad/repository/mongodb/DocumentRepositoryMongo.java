package com.copernic.projecte2_openroad.repository.mongodb;

import com.copernic.projecte2_openroad.model.mongodb.DocumentClient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepositoryMongo extends MongoRepository<DocumentClient, String> {
}
