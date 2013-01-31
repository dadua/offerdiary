package com.itech.user.vos;

import java.sql.Date;

import com.itech.user.model.LoginType;
import com.itech.user.model.User;


public class UserInfoVO {
	private String name;
	private int age;
	private Long dobMillis;
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

	public void setDobMillis(Long dobMillis) {
		this.dobMillis = dobMillis;
	}
	public Long getDobMillis() {
		return dobMillis;
	}


	public static UserInfoVO getUserInfoVOFor(User user) {
		UserInfoVO userInfoVO = new UserInfoVO();
		userInfoVO.setAge(user.getAge());
		userInfoVO.setCity(user.getCity());
		userInfoVO.setEmailId(user.getEmailId());
		if (!LoginType.EMAIL.equals(user.getLoginType())) {
			userInfoVO.setLinkedUser(true);
		}
		userInfoVO.setMobileNumber(user.getMobileNumber());
		userInfoVO.setName(user.getName());
		userInfoVO.setNickname(user.getNickname());
		userInfoVO.setPinCode(user.getPinCode());
		userInfoVO.setDobMillis(user.getDob().getTime());
		return userInfoVO;
	}


	public static void fillUserFromUserVO(User user, UserInfoVO userInfoVO) {
		if ((userInfoVO.getDobMillis()!= null) && (userInfoVO.getDobMillis() !=0)) {
			user.setDob(new Date(userInfoVO.getDobMillis()));
		}
		user.setAge(userInfoVO.getAge());
		user.setCity(userInfoVO.getCity());
		user.setEmailId(userInfoVO.getEmailId());
		user.setMobileNumber(userInfoVO.getMobileNumber());
		user.setName(userInfoVO.getName());
		user.setNickname(userInfoVO.getNickname());
		user.setPinCode(userInfoVO.getPinCode());
	}



}
