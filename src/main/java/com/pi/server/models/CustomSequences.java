package com.pi.server.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customSequences")
public class CustomSequences {
    @Id
    private String id;
    private int seq;
    
 // getters and setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}


    
}