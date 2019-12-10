package com.pi.server.models;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FarmStatus")
public class FarmStatus {
	
	@Id
	private String statusId;
	@Field
	private Date dateInsertion;
	
	// Enumeration
	public enum InsertionType { ENUM_TYPE_SCHEDULED(1),ENUM_TYPE_USER(2),ENUM_TYPE_AUTO_WATERING(3);
	public final int value;
	private InsertionType(int value) {
	this.value = value;
	    }
	}
	
	@Field
	private int type;
	// MJ -> Foreign Id's Begin 
	@Field
	private String systemId;
	@Field
	private String userId;
	// MJ -> Foreign Id's End 

	// Sensors Values
	@Field
	private double valTempreture;
	
	public String getDateInsertionStr() {
		return dateInsertionStr;
	}

	public void setDateInsertionStr(String dateInsertionStr) {
		this.dateInsertionStr = dateInsertionStr;
	}

	@Field
	private double valHumidityAir;
	
	@Field 
	private double valHumiditySoil;
	
	@Field
	private double valLuminosity;
	
	@Field
	private boolean bWatering;
	
	@Field
	private int wateringDuration;
	
	@Transient
	private String dateInsertionStr;
	
	// Constructor & Getters-Setters
	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public Date getDateInsertion() {
		return dateInsertion;
	}

	public void setDateInsertion(Date dateInsertion) {
		this.dateInsertion = dateInsertion;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public double getValTempreture() {
		return valTempreture;
	}

	public void setValTempreture(double valTempreture) {
		this.valTempreture = valTempreture;
	}

	public double getValHumidityAir() {
		return valHumidityAir;
	}

	public void setValHumidityAir(double valHumidityAir) {
		this.valHumidityAir = valHumidityAir;
	}

	public double getValHumiditySoil() {
		return valHumiditySoil;
	}

	public void setValHumiditySoil(double valHumiditySoil) {
		this.valHumiditySoil = valHumiditySoil;
	}

	public double getValLuminosity() {
		return valLuminosity;
	}

	public void setValLuminosity(double valLuminosity) {
		this.valLuminosity = valLuminosity;
	}

	public boolean isbWatering() {
		return bWatering;
	}

	public void setbWatering(boolean bWatering) {
		this.bWatering = bWatering;
	}

	public int getWateringDuration() {
		return wateringDuration;
	}

	public void setWateringDuration(int wateringDuration) {
		this.wateringDuration = wateringDuration;
	}

	/**
	 * @param statusId
	 * @param dateInsertion
	 * @param type
	 * @param systemId
	 * @param userId
	 * @param valTempreture
	 * @param valHumidityAir
	 * @param valHumiditySoil
	 * @param valLuminosity
	 * @param bWatering
	 * @param wateringDuration
	 */
	@PersistenceConstructor
	public FarmStatus(String statusId, Date dateInsertion, int type, String systemId, String userId,
			double valTempreture, double valHumidityAir, double valHumiditySoil, double valLuminosity,
			boolean bWatering, int wateringDuration) {
		super();
		this.statusId = statusId;
		this.dateInsertion = dateInsertion;
		this.type = type;
		this.systemId = systemId;
		this.userId = userId;
		this.valTempreture = valTempreture;
		this.valHumidityAir = valHumidityAir;
		this.valHumiditySoil = valHumiditySoil;
		this.valLuminosity = valLuminosity;
		this.bWatering = bWatering;
		this.wateringDuration = wateringDuration;
	}

	/**
	 * 
	 */
	public FarmStatus() {
		super();
	}
}
