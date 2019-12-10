package com.pi_server.mdbServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import com.pi.server.models.CustomSequences;



@Service
public class NextSequenceService {
	
	public static String CONNECTION_SYSTEM_SEQ = "connectionSequence";
	public static String FARM_SYSTEM_SEQ = "SystemSequence";
	public static String SYSTEM_URL_SEC = "urlSequence";
	public static String USER_SEC = "userSequence";
	public static String FARMER_USER_SEQ = "farmerSequence";
	public static String Farm_STATUS_SEQ = "farmStatusSequence";
	
    @Autowired private MongoOperations mongo;

    public int getNextSequence(String seqName)
    {
        CustomSequences counter = mongo.findAndModify(
            query(where("_id").is(seqName)),
            new Update().inc("seq",1),
            options().returnNew(true).upsert(true),
            CustomSequences.class);
        return counter.getSeq();
    }
}