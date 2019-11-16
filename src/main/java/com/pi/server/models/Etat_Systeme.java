package com.pi.server.models;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Etat")
public class Etat_Systeme {
	
	@Id
	private int idEtat;
	
	@Field(value="temperature")
	private double temperature;
	
	@Field(value="humidity")
	private float humidity;
	
	@Field(value="date")
	private Date date;
	
	@Field(value="time")
	private Date time;
	
	@Field(value="watering")
	private boolean water;
	
	@Field(value="posttype")
	private int postType ;

	//Contructors
	/**
	 * @param idEtat
	 * @param temperature
	 * @param humidity
	 * @param date_heure
	 * @param water
	 * @param postType
	 */
	@PersistenceConstructor
	public Etat_Systeme(int idEtat, double temperature, float humidity, Date date_heure, boolean water, int postType) {
		super();
		this.idEtat = idEtat;
		this.temperature = temperature;
		this.humidity = humidity;
		this.date = date_heure;
		this.water = water;
		this.postType = postType;
	}

	
	/**
	 * 
	 */
	public Etat_Systeme() {
		super();
	}


	//Gettes & Setters
	public int getIdEtat() {
		return idEtat;
	}
	
	public void setIdEtat(int idEtat) {
		this.idEtat = idEtat;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public Date getDate_heure() {
		return date;
	}

	public void setDate_heure(Date date_heure) {
		this.date = date_heure;
	}

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public boolean isWater() {
		return water;
	}

	public void setWater(boolean water) {
		this.water = water;
	}

	public int getPostType() {
		return postType;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}
	
	
	
	
}
