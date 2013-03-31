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

	@Enumerated(EnumType.STRING)
	@Column(name=UserModelConstants.COL_LOGIN_TYPE)
	private LoginType loginType = LoginType.UNKNOWN;

	@Column(name=UserModelConstants.COL_PASSWORD)
	private String password;

	@Column(name=UserModelConstants.COL_NAME)
	private String name;

	@Column(name=UserModelConstants.COL_AGE)
	private int age;

	@Enumerated(EnumType.STRING)
	@Column(name=UserModelConstants.COL_GENDER)
	private Gender gender = Gender.UNKNOWN;

	@Column(name=UserModelConstants.COL_LOCATION)
	private String location;

	@Column(name=UserModelConstants.COL_LANGUAGE)
	private String language;

	@Column(name=UserModelConstants.COL_EMAIL_ID)
	private String emailId;

	@Enumerated(EnumType.STRING)
	@Column(name=UserModelConstants.COL_USER_ROLE)
	private UserRole userRole = UserRole.CONSUMER;

	@Column(name=UserModelConstants.COL_REGISTRATION_TIME)
	private Date registrationTime;

	@Column(name=UserModelConstants.COL_LAST_NOTIFY_PASSWORD_EMAIL_TIME)
	private Date notifyPasswordTime;

	@Column(name="IS_EMAIL_VARIFIED")
	private Boolean emailVarified;

	@Column(name="EMAIL_VARIFICATION_CODE")
	private String emailVarficationAccessCode;


	public enum ActivationStatus {
		ACTIVE, INACTIVE;
	}

	@Enumerated(EnumType.STRING)
	@Column(name=UserModelConstants.COL_ACTIVATION_STATUS)
	private ActivationStatus activationStatus = ActivationStatus.INACTIVE;

	@Column(name=UserModelConstants.COL_CITY)
	private String city;

	@Column(name=UserModelConstants.COL_MOBILE_NUMBER)
	private String mobileNumber;

	@Column(name=UserModelConstants.COL_PIN_CODE)
	private String pinCode;


	@Column(name=UserModelConstants.COL_DOB)
	private Date dob;

	@Column(name=UserModelConstants.COL_NICKNAME)
	private String nickname;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LoginType getLoginType() {
		return loginType;
	}
	public void setLoginType(LoginType userType) {
		loginType = userType;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmailId() {
		return emailId;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	@Override
	public boolean isTransient() {
		return id == null;
	}
	public Date getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}
	public Date getNotifyPasswordTime() {
		return notifyPasswordTime;
	}
	public void setNotifyPasswordTime(Date notifyPasswordTime) {
		this.notifyPasswordTime = notifyPasswordTime;
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
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickName) {
		nickname = nickName;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getDob() {
		return dob;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public boolean isODAdmin() {
		return UserRole.OD_ADMIN.equals(getUserRole());
	}
	public boolean isEmailVarified() {
		return emailVarified;
	}
	public void setEmailVarified(boolean emailVarified) {
		this.emailVarified = emailVarified;
	}
	public String getEmailVarficationAccessCode() {
		return emailVarficationAccessCode;
	}
	public void setEmailVarficationAccessCode(String emailVarficationAccessCode) {
		this.emailVarficationAccessCode = emailVarficationAccessCode;
	}

}
