package com.itech.alert.model;

import java.util.Date;

public class Alert {

	public enum AlertStatus {
		NEW, READ;
	}

	private long id;
	private final String dataType;
	private final long dataId;
	private final AlertStatus alertStatus;
	private final Date creationTime;

	public Alert(String dataType, long dataId, AlertStatus alertStatus,
			Date creationTime) {
		super();
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

}
