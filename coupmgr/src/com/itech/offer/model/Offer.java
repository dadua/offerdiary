package com.itech.offer.model;

public class Offer {

	private String merchantName;

	private String address;

	private String description;

	private String validity;


	public Offer() {

	}

	public Offer(String validity, String description) {
		this.validity = validity;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValidity() {
		return this.validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	@Override
	public String toString() {
		return "Offer [merchantName=" + this.merchantName + ", address=" + this.address
		+ ", description=" + this.description + ", validity=" + this.validity
		+ "]";
	}

	public String getMerchantName() {
		return this.merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
