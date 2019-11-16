package com.pi_server.mongoRepositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.pi.server.models.SystemURL;

public interface SystemUrlRepository extends MongoRepository<SystemURL, String> {
	// Returns last url by date search
	SystemURL findTopByOrderByUpdateTimeDesc();
	SystemURL findTopBySystemIdOrderByUpdateTimeDesc(String systemId);
}
