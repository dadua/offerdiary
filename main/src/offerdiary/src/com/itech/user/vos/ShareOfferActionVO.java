package com.itech.user.vos;

public class ShareOfferActionVO {
	private boolean mailShare;
	private boolean fbShare;
	private String message;
	private String emailIds;
	private String accessToken;
	private String shareOfferURL;
	private OfferAccessBy accessBy = OfferAccessBy.ACCESS_TOKEN;

	public enum OfferAccessBy {
		ACCESS_TOKEN, OFFER_PUBLIC_ID, OFFER_UNIQUE_ID
	}

	public boolean isMailShare() {
		return mailShare;
	}
	public void setMailShare(boolean isMailShare) {
		this.mailShare = isMailShare;
	}
	public boolean isFbShare() {
		return fbShare;
	}
	public void setFbShare(boolean isFbShare) {
		this.fbShare = isFbShare;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmailIds() {
		return emailIds;
	}
	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public OfferAccessBy getAccessBy() {
		return accessBy;
	}
	public void setAccessBy(OfferAccessBy accessBy) {
		this.accessBy = accessBy;
	}
	public String getShareOfferURL() {
		return shareOfferURL;
	}
	public void setShareOfferURL(String shareOfferURL) {
		this.shareOfferURL = shareOfferURL;
	}


}
