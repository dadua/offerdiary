package com.itech.alert.model;

import java.sql.Date;

public class Alert {

	public enum AlertStatus {
		NEW, READ;
	}

	private long id;
	private String dataType;
	private long dataId;
	private String alertType;
	private AlertStatus alertStatus;
	private Date creationTime;
	private long userId;
	private String message;
	private String messageHTML;

	public Alert(long userId, String dataType, long dataId, AlertStatus alertStatus,
			Date creationTime, String message, String messageHTML) {
		super();
		this.userId = userId;
		this.dataType = dataType;
		this.dataId = dataId;
		this.alertStatus = alertStatus;
		this.creationTime = creationTime;
		this.message = message;
		this.messageHTML = messageHTML;
	}
	
	public Alert(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDataType() {
		return dataType;
	}

	public long getDataId() {
		return dataId;
	}

	public AlertStatus getAlertStatus() {
		return alertStatus;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public void setAlertStatus(AlertStatus alertStatus) {
		this.alertStatus = alertStatus;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessageHTML(String messageHTML) {
		this.messageHTML = messageHTML;
	}

	public String getMessageHTML() {
		return messageHTML;
	}
	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

}
