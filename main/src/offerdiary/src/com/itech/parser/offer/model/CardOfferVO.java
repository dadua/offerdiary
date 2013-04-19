package com.itech.parser.offer.model;

import java.sql.Date;

import com.itech.common.CommonUtilities;
import com.itech.offer.model.Offer;
import com.itech.offer.model.Vendor;
import com.itech.offer.model.Vendor.SourceType;
import com.itech.offer.model.enums.VendorType;

public class CardOfferVO {

	private String merchantName;

	private String merchantImageUrl;

	private String address;

	private String merchantDescription;

	private String merchantWebAdress;

	private String description;

	private String title;

	private String terms;

	private String howToAvail;

	private String details;

	private String validity;

	private Date expiry;

	private String location;

	private String consumptionType;

	private String consumptionSubType;

	private String imageSrc;

	private String offerUrl;

	private String offerCode;

	private String cardTypeTag; //e.g., CREDIT, CREDIT_VISA etc (all provider specific provider know about that);

	public CardOfferVO() {

	}

	public static void fillOfferFromVO(Offer offer, CardOfferVO cardOfferVO) {
		offer.setDescription(cardOfferVO.getDescription());
		offer.setExpiry(cardOfferVO.getExpiry());
		offer.setImageSource(cardOfferVO.getImageSrc());
		offer.setOfferCode(cardOfferVO.getOfferCode());
		offer.setOfferLink(cardOfferVO.getOfferUrl());
		offer.setTitle(cardOfferVO.getTitle());
		Vendor targetVendor = getVendorFrom(cardOfferVO);
		offer.setTargetVendor(targetVendor);


		offer.setTermsAndConditions(cardOfferVO.getTerms());
	}


	private static Vendor getVendorFrom(CardOfferVO cardOfferVO) {
		String merchantName = cardOfferVO.getMerchantName();
		String vendorUrl = null;
		String vendorName = merchantName;
		if (CommonUtilities.isNullOrEmpty(merchantName)) {
			return null;
		}

		if (merchantName.endsWith(".com") || merchantName.endsWith(".in") || merchantName.endsWith(".org") || merchantName.endsWith(".co.in")) {
			if (CommonUtilities.isNullOrEmpty(cardOfferVO.getMerchantWebAdress())) {
				vendorUrl = "http://" + merchantName.toLowerCase();
			}
			String[] strings = merchantName.split("\\.");
			vendorName = strings[0];
		}


		Vendor vendor = new Vendor();
		vendor.setName(vendorName);
		vendor.setCity(cardOfferVO.getLocation());
		vendor.setLogoUrl(cardOfferVO.getMerchantImageUrl());
		vendor.setSiteUrl(vendorUrl);
		vendor.setDescription(cardOfferVO.getMerchantDescription());

		vendor.setVendorType(VendorType.GLOBAL);
		vendor.setSourceType(SourceType.OD_BANK_OFFER_PARSER);
		return vendor;
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
				+", merchantWebAddress= "+this.merchantWebAdress + ", offerUrl= " + offerUrl + "]";
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

	public String getOfferUrl() {
		return offerUrl;
	}

	public void setOfferUrl(String offerUrl) {
		this.offerUrl = offerUrl;
	}

	public String getCardTypeTag() {
		return cardTypeTag;
	}

	public void setCardTypeTag(String cardTypeTag) {
		this.cardTypeTag = cardTypeTag;
	}

	public String getMerchantImageUrl() {
		return merchantImageUrl;
	}

	public void setMerchantImageUrl(String merchantImageUrl) {
		this.merchantImageUrl = merchantImageUrl;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMerchantDescription() {
		return merchantDescription;
	}

	public void setMerchantDescription(String merchantDescription) {
		this.merchantDescription = merchantDescription;
	}

	public String getHowToAvail() {
		return howToAvail;
	}

	public void setHowToAvail(String howToAvail) {
		this.howToAvail = howToAvail;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}
}
