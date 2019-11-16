package com.pi_server.mongoRepositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pi.server.models.ConnectionSystem;

public interface ConnectionSystemRepository extends MongoRepository<ConnectionSystem, String> {

}
