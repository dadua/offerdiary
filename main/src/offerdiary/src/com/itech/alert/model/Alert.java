package com.itech.alert.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.itech.common.db.PersistableEntity;
import com.itech.user.model.User;

@Entity
@Table(name="ALERT")
public class Alert extends PersistableEntity{

	public enum AlertStatus {
		NEW, READ;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=AlertModelConstant.COL_ID)
	private Long id;

	@Column(name=AlertModelConstant.COL_DATA_TYPE)
	private String dataType;

	@Column(name=AlertModelConstant.COL_DATA_ID)
	private long dataId;

	@Column(name=AlertModelConstant.COL_ALERT_TYPE)
	private String alertType;

	@Enumerated(EnumType.STRING)
	@Column(name=AlertModelConstant.COL_ALERT_STATUS)
	private AlertStatus alertStatus;

	@Column(name=AlertModelConstant.COL_CREATION_TIME)
	private Date creationTime;

	@ManyToOne
	@JoinColumn(name=AlertModelConstant.COL_USER_ID)
	private User user;

	@Column(name=AlertModelConstant.COL_MESSAGE)
	private String message;

	@Column(name=AlertModelConstant.COL_HTML_MESSAGE)
	private String messageHTML;

	@Transient
	private AlertConfig alertConfig;

	public Alert(User user, String dataType, long dataId, AlertStatus alertStatus,
			Date creationTime, String message, String messageHTML) {
		super();
		this.setUser(user);
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

	@Override
	public boolean isTransient() {
		return id == null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAlertConfig(AlertConfig alertConfig) {
		this.alertConfig = alertConfig;
	}

	public AlertConfig getAlertConfig() {
		return alertConfig;
	}

}
