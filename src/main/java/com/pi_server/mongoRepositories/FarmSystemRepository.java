package com.pi_server.mongoRepositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.pi.server.models.FarmSystem;

public interface FarmSystemRepository extends MongoRepository<FarmSystem, String> {
	/* Optional  Retrieve */
	Optional<FarmSystem> findBySystemCode(String SystemCode);
	FarmSystem findBySystemID ( String systemID);
}
