package com.itech.user.vos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.itech.common.CommonUtilities;
import com.itech.user.model.Gender;
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
	private boolean emailLoginAllowed;
	private String profileImgUrl;
	private String yearOfPassing;
	private String address;
	private String designation;
	private String companyName;

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
		userInfoVO.setYearOfPassing(user.getYearOfPassing());
		if (!LoginType.EMAIL.equals(user.getLoginType())) {
			userInfoVO.setLinkedUser(true);
			if (LoginType.FACEBOOK.equals(user.getLoginType()) || LoginType.MULTI.equals(user.getLoginType()) ) {
				userInfoVO.setProfileImgUrl("https://graph.facebook.com/" +  user.getUserId() + "/picture?type=large");
			}

		}

		if (LoginType.EMAIL.equals(user.getLoginType()) || LoginType.MULTI.equals(user.getLoginType())) {
			userInfoVO.setEmailLoginAllowed(true);
		}

		if (LoginType.EMAIL.equals(user.getLoginType())){
			if (CommonUtilities.isNullOrEmpty(user.getProfileImgPath())) {

				if (Gender.UNKNOWN.equals(user.getGender())) {
					userInfoVO.setProfileImgUrl("images/profile/batman.jpg");
				} else if (Gender.M.equals(user.getGender())){
					userInfoVO.setProfileImgUrl("images/profile/male.jpg");
				} else {
					userInfoVO.setProfileImgUrl("images/profile/female.jpg");
				}
			} else {
				userInfoVO.setProfileImgUrl(user.getProfileImgPath());
			}
		}

		userInfoVO.setAddress(user.getAddress());

		userInfoVO.setMobileNumber(user.getMobileNumber());
		userInfoVO.setName(user.getName());
		userInfoVO.setNickname(user.getNickname());
		userInfoVO.setDesignation(user.getDesignation());
		userInfoVO.setCompanyName(user.getCompanyName());
		userInfoVO.setPinCode(user.getPinCode());
		if (user.getDob()!=null) {
			userInfoVO.setDobMillis(user.getDob().getTime());
		}
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
		user.setYearOfPassing(userInfoVO.getYearOfPassing());
		user.setAddress(userInfoVO.getAddress());
		user.setCompanyName(userInfoVO.getCompanyName());
		user.setDesignation(userInfoVO.getDesignation());
	}


	public static List<UserInfoVO> getUserInfoVOs(List<User> users) {
		List<UserInfoVO> userInfoVOs = new ArrayList<UserInfoVO>();
		for (User user: users) {
			UserInfoVO userInfoVO = UserInfoVO.getUserInfoVOFor(user);
			userInfoVOs.add(userInfoVO);
		}
		return userInfoVOs;
	}

	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}

	public String getProfileImgUrl() {
		return profileImgUrl;
	}
	public boolean isEmailLoginAllowed() {
		return emailLoginAllowed;
	}
	public void setEmailLoginAllowed(boolean emailLoginAllowed) {
		this.emailLoginAllowed = emailLoginAllowed;
	}
	public void setYearOfPassing(String yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}
	public String getYearOfPassing() {
		return yearOfPassing;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDesignation() {
		return designation;
	}



}
