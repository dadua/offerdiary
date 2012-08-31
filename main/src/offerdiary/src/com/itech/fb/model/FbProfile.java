package com.itech.fb.model;

public class FbProfile {

	/*
	   {
	  		id: "100001451727305"
			name: "Crocy Hanks"
			first_name: "Crocy"
			last_name: "Hanks"
			link: "http://www.facebook.com/profile.php?id=100001451727305"
			gender: "male"
			timezone: 5.5
			locale: "en_US"
			updated_time: "2011-03-24T19:30:35+0000"
		}
	 */

	private String id;

	private String fullName;

	private String emailId;

	private String locale;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getLocale() {
		return locale;
	}

	private String firstName;

	private String lastName;

	private String gender;

	private String timeZone;

	private String profileUrl;


}
