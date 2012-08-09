package com.itech.fb.model;

public class FbCreds {
	private String signedRequest;

	private String userID;

	private String expiresIn;

	private String accessToken;

	public void setSignedRequest(String signedRequest) {
		this.signedRequest = signedRequest;
	}

	public String getSignedRequest() {
		return signedRequest;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserID() {
		return userID;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

}
