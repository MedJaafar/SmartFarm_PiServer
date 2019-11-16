package com.pi_server.mongoRepositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.pi.server.models.FarmUser;

public interface FarmUserRepository extends MongoRepository<FarmUser, String>{
	public Optional <FarmUser> findByUsername(String username);
	//public FarUser findByUsername(String username);
}
