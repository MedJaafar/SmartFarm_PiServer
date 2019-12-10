package com.pi.server.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FarmSystem")
public class FarmSystem {
	

	// System Identification Attributes
	@Id
	private String systemID;
	
	@Indexed(unique = true)
    @Field(value = "systemCode")
	private String systemCode;
	
	@Field
	private String systemName;
	
	// System Adress Attributes
	@Field
	private int buildingNumber;
	@Field
	private int postalCode;
	@Field
	private String city;
	@Field
	private String country;
	@Field
	private String voieName;
	@Field
	private boolean bEnableWatering;
	
	@Field
	private double [] localisation;
	
	// List of users Id's
	@Field
	private List<String> users;
	// Constructors 
	/**
	 * @param systemCode
	 * @param systemName
	 * @param buildingNumber
	 * @param postalCode
	 * @param city
	 * @param country
	 * @param voieName
	 * @param localisation
	 * @param connexions
	 */
	@PersistenceConstructor
	public FarmSystem(String systemCode, String systemName, int buildingNumber, int postalCode, String city,
			String country, String voieName, double[] localisation, boolean bEnableWatering) {
		super();
		this.systemCode = systemCode;
		this.systemName = systemName;
		this.buildingNumber = buildingNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.voieName = voieName;
		this.localisation = localisation;
		this.bEnableWatering = bEnableWatering;
	}
	/**
	 * 
	 */
	public FarmSystem() {
		super();
	}
	
	// Getters & Setters
	
	
	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}
	
	public List<String> getUsers() {
		return users;
	}
	public void setUsers(List<String> users) {
		this.users = users;
	}
	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public int getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(int buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getVoieName() {
		return voieName;
	}

	public void setVoieName(String voieName) {
		this.voieName = voieName;
	}

	public double[] getLocalisation() {
		return localisation;
	}

	public void setLocalisation(double[] localisation) {
		this.localisation = localisation;
	}

	public String getSystemID() {
		return systemID;
	}
	public boolean isbEnableWatering() {
		return bEnableWatering;
	}
	public void setbEnableWatering(boolean bEnableWatering) {
		this.bEnableWatering = bEnableWatering;
	}	
	
}
