package com.itech.user.vos;

public class UserInfoVO {
	private String name;
	private int age;
	private String city;
	private String emailId;
	private String nickname;
	private String mobileNumber;
	private String pinCode;
	private boolean isLinkedUser;


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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public boolean isLinkedUser() {
		return isLinkedUser;
	}
	public void setLinkedUser(boolean isLinkedUser) {
		this.isLinkedUser = isLinkedUser;
	}



}
