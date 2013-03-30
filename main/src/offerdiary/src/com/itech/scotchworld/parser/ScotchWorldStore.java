package com.itech.scotchworld.parser;

public class ScotchWorldStore {
	private String storeName;
	private String description;
	private String imageSrc;
	private String storeURL;
	private String affiliateUrl;

	@Override
	public String toString(){
		return "[storeName= "+getStoreName()+", description= "+getDescription()+", imageSrc= "+getImageSrc()+", storeURL= "+getStoreURL()+"]";
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	public String getImageSrc() {
		return imageSrc;
	}
	public String getStoreURL() {
		return storeURL;
	}
	public void setStoreURL(String storeURL) {
		this.storeURL = storeURL;
	}
	public String getAffiliateUrl() {
		return affiliateUrl;
	}
	public void setAffiliateUrl(String affiliateUrl) {
		this.affiliateUrl = affiliateUrl;
	}
}
