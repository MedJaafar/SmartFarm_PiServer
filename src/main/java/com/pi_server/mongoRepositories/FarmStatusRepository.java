package com.pi_server.mongoRepositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.pi.server.models.FarmStatus;

public interface FarmStatusRepository extends MongoRepository<FarmStatus, String>{
	FarmStatus findTopBySystemIdOrderByDateInsertionDesc(String systemId);
}
