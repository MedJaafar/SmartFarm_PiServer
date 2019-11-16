package com.pi.server.models;

import java.io.Serializable;
import java.util.Date;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@SuppressWarnings("serial")
@Document(collection = "ConnectionSystem")
public class ConnectionSystem implements Serializable {
	
	// Attributes
	@Id
	private String connectionId;
	
	public enum connectionState { ENUM_CONNECTION_OK,ENUM_CONNECTION_REFUSED };
	
	@Field
	private connectionState connectionState;
	@Field
	private Date dateConnection;	
	
	// MJ -> Foreign Id's Begin 
	@Field
	private String systemId;
	@Field
	private String userId;
	// MJ -> Foreign Id's End 
	
	// Getters & Setters
	/**
	 * @param connectionId
	 * @param connectionState
	 * @param dateConnection
	 */
	@PersistenceConstructor
	public ConnectionSystem(String connectionId, ConnectionSystem.connectionState connectionState,
			Date dateConnection) {
		super();
		this.connectionId = connectionId;
		this.connectionState = connectionState;
		this.dateConnection = dateConnection;
	}

	public ConnectionSystem() {
		super();
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}


	public connectionState getConnectionState() {
		return connectionState;
	}


	public void setConnectionState(connectionState connectionState) {
		this.connectionState = connectionState;
	}


	public Date getDateConnection() {
		return dateConnection;
	}
	
	public void setDateConnection(Date dateConnection) {
		this.dateConnection = dateConnection;
	}
	
	
	
}
