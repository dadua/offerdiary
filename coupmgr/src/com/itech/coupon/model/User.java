package com.itech.coupon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.itech.coupon.model.constants.UserModelConstants;

@Entity
@Table(name="USERS")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=UserModelConstants.COL_ID)
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


}
