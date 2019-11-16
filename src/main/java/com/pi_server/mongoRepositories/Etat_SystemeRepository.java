package com.pi_server.mongoRepositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pi.server.models.Etat_Systeme;

public interface Etat_SystemeRepository extends MongoRepository<Etat_Systeme, int[]> {

}
