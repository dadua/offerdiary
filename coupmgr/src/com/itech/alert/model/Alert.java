package com.itech.alert.model;

import java.util.Date;

public class Alert {

	public enum AlertStatus {
		NEW, READ;
	}

	private long id;
	private String dataType;
	private long dataId;
	private AlertStatus alertStatus;
	private Date creationTime;
	private long userId;

	public Alert(long userId, String dataType, long dataId, AlertStatus alertStatus,
			Date creationTime) {
		super();
		this.userId = userId;
		this.dataType = dataType;
		this.dataId = dataId;
		this.alertStatus = alertStatus;
		this.creationTime = creationTime;
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

}
