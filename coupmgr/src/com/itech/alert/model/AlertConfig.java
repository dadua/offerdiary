package com.itech.alert.model;

import java.sql.Date;

public class AlertConfig {
	public enum ActivationStatus {
		ACTIVE, SUSPENDED, HANDLED;
	}

	private long id;
	private String dataType;
	private long dataId;
	private String alertType;
	private Date triggerTime;
	private Date creationTime;
	private ActivationStatus status;

	public AlertConfig() {

	}

	public AlertConfig(String dataType, long dataId, Date triggerTime,
			ActivationStatus status) {
		super();
		this.dataType = dataType;
		this.dataId = dataId;
		this.triggerTime = triggerTime;
		this.status = status;
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
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public long getDataId() {
		return dataId;
	}
	public void setDataId(long dataId) {
		this.dataId = dataId;
	}
	public Date getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(Date triggerTime) {
		this.triggerTime = triggerTime;
	}
	public ActivationStatus getStatus() {
		return status;
	}
	public void setStatus(ActivationStatus status) {
		this.status = status;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

}