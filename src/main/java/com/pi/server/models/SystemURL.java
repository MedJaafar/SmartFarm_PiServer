package com.pi.server.models;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "SystemUrl")
public class SystemURL {
	
	@Id
	private String idUrl;
	@Field
	private String url; 
	@Field
	private Date updateTime;
	
	// MJ -> Foreign Id's Begin
	@Field String systemId;
	// MJ -> Foreign Id's End
	
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getIdUrl() {
		return idUrl;
	}
	public void setIdUrl(String idUrl) {
		this.idUrl = idUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @param idUrl
	 * @param url
	 * @param updateTime
	 */
	@PersistenceConstructor
	public SystemURL(String idUrl, String url, Date updateTime) {
		super();
		this.idUrl = idUrl;
		this.url = url;
		this.updateTime = updateTime;
	}
	
	/**
	 * 
	 */
	public SystemURL() {
		super();
	}
	
	
	
}
