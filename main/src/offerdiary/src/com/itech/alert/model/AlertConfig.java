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

import com.itech.common.db.PersistableEntity;
import com.itech.offer.model.OfferUserAssocModelConstants;
import com.itech.user.model.User;


@Entity
@Table(name="ALERT_CONFIG")
public class AlertConfig extends PersistableEntity{

	public enum ActivationStatus {
		ACTIVE, SUSPENDED, HANDLED;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=AlertConfigModelConstant.COL_ID)
	private Long id;

	@Column(name=AlertConfigModelConstant.COL_DATA_TYPE)
	private String dataType;

	@Column(name=AlertConfigModelConstant.COL_DATA_ID)
	private long dataId;

	@Column(name=AlertConfigModelConstant.COL_ALERT_TYPE)
	private String alertType;

	@Column(name=AlertConfigModelConstant.COL_TRIGGER_TIME)
	private Date triggerTime;

	@Column(name="TRIGGER_EXPIRY_TIME")
	private Date triggerExpiryTime;

	@Column(name=AlertConfigModelConstant.COL_CREATION_TIME)
	private Date creationTime;

	@Enumerated(EnumType.STRING)
	@Column(name=AlertConfigModelConstant.COL_STATUS)
	private ActivationStatus status;

	@Column(name=AlertConfigModelConstant.COL_FB_NOTIFICATION)
	private boolean fbNotification;


	@Column(name=AlertConfigModelConstant.COL_EMAIL_NOTIFICATION)
	private boolean emailNotification;


	@Column(name=AlertConfigModelConstant.COL_PERSIST_ALERT_IN_DB)
	private boolean persistAlertInDB;

	@ManyToOne
	@JoinColumn(name=OfferUserAssocModelConstants.COL_USER_ID)
	private User user;


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

	@Override
	public boolean isTransient() {
		return id == null;
	}

	public void setFbNotification(boolean fbNotification) {
		this.fbNotification = fbNotification;
	}

	public boolean isFbNotification() {
		return fbNotification;
	}

	public void setEmailNotification(boolean emailNotification) {
		this.emailNotification = emailNotification;
	}

	public boolean isEmailNotification() {
		return emailNotification;
	}

	public void setPersistAlertInDB(boolean persistAlertInDB) {
		this.persistAlertInDB = persistAlertInDB;
	}

	public boolean isPersistAlertInDB() {
		return persistAlertInDB;
	}

	public Date getTriggerExpiryTime() {
		return triggerExpiryTime;
	}

	public void setTriggerExpiryTime(Date triggerExpiryTime) {
		this.triggerExpiryTime = triggerExpiryTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}