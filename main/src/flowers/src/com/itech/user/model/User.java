package com.itech.user.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.itech.common.db.PersistableEntity;
import com.itech.user.constants.UserModelConstants;

@Entity
@Table(name=UserModelConstants.TABLE_USER)
public class User extends PersistableEntity{

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name=UserModelConstants.COL_USER_ID)
	private String userId;

	@Column(name=UserModelConstants.COL_PASSWORD)
	private String password;

	@Column(name=UserModelConstants.COL_NAME)
	private String name;

	@Column(name=UserModelConstants.COL_EMAIL_ID)
	private String emailId;

	@Enumerated(EnumType.STRING)
	@Column(name=UserModelConstants.COL_USER_ROLE)
	private UserRole userRole = UserRole.ADMIN;

	@Column(name=UserModelConstants.COL_REGISTRATION_TIME)
	private Date registrationTime;

	@Column(name=UserModelConstants.COL_LAST_LOGIN_TIME)
	private Date lastLoginTime;

	public enum ActivationStatus {
		ACTIVE, INACTIVE;
	}

	@Enumerated(EnumType.STRING)
	@Column(name=UserModelConstants.COL_ACTIVATION_STATUS)
	private ActivationStatus activationStatus = ActivationStatus.ACTIVE;

	@Column(name=UserModelConstants.COL_CITY)
	private String city;

	@Column(name=UserModelConstants.COL_MOBILE_NUMBER)
	private String mobileNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public ActivationStatus getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(ActivationStatus activationStatus) {
		this.activationStatus = activationStatus;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public boolean isTransient() {
		return id == null;
	}

}
