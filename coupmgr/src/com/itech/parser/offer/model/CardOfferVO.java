package com.itech.parser.offer.model;

public class CardOfferVO {

	private String merchantName;

	private String address;

	private String merchantWebAdress;

	private String description;

	private String validity;

	private String location;

	private String consumptionType;

	private String consumptionSubType;

	private String imageSrc;

	public CardOfferVO() {

	}

	public CardOfferVO(String validity, String description) {
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
		return "CardOfferVO [merchantName=" + this.merchantName + ", address=" + this.address
				+ ", description=" + this.description + ", validity=" + this.validity
				+ " imageSrc ="+this.imageSrc+", consumptionType = "+this.consumptionType
				+", consumptionSubType = "+this.consumptionSubType+", location="+this.location
				+", merchantWebAddress= "+this.merchantWebAdress+"]";
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getConsumptionType() {
		return consumptionType;
	}

	public void setConsumptionType(String consumptionType) {
		this.consumptionType = consumptionType;
	}

	public String getConsumptionSubType() {
		return consumptionSubType;
	}

	public void setConsumptionSubType(String consumptionSubType) {
		this.consumptionSubType = consumptionSubType;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getMerchantWebAdress() {
		return merchantWebAdress;
	}

	public void setMerchantWebAdress(String merchantWebAdress) {
		this.merchantWebAdress = merchantWebAdress;
	}
}
