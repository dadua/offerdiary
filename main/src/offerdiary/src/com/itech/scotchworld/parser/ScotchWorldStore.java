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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((storeName == null) ? 0 : storeName.hashCode());
		result = prime * result
				+ ((storeURL == null) ? 0 : storeURL.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ScotchWorldStore other = (ScotchWorldStore) obj;
		if (storeName == null) {
			if (other.storeName != null) {
				return false;
			}
		} else if (!storeName.equals(other.storeName)) {
			return false;
		}
		if (storeURL == null) {
			if (other.storeURL != null) {
				return false;
			}
		} else if (!storeURL.equals(other.storeURL)) {
			return false;
		}
		return true;
	}
}
