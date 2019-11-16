package com.pi.server.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@SuppressWarnings("serial")
@Document( collection = "FarmUser")
public class FarmUser implements Serializable{
	
	@Id
	private String id;
	@Field
	@Indexed(unique = true)
	private String username;
	@Field
	private String first_name;
	@Field
	private String last_name;
	@Field
	private long telNumber;
	@Field
	private String role;
	@Field
	boolean activated;
	@Field
	private String email;
	@Field
	private List<String> system = new ArrayList<String>();

	//Getters & Setters
	
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getSystem() {
		return system;
	}
	public void setSystem(List<String> system) {
		this.system = system;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getId() {
		return id;
	}
	
	public long getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(long telNumber) {
		this.telNumber = telNumber;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	// Constructors 
	/**
	 * @param id
	 * @param username
	 * @param first_name
	 * @param last_name
	 * @param telNumber
	 * @param role
	 * @param activated
	 * @param email
	 */
	@PersistenceConstructor
	public FarmUser(String id, String username, String first_name, String last_name, long telNumber, String role,
			boolean activated, String email) {
		super();
		this.id = id;
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.telNumber = telNumber;
		this.role = role;
		this.activated = activated;
		this.email = email;
	}
		
}
