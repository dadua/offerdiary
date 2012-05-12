package com.itech.offer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.itech.common.db.PersistableEntity;

@Entity
@Table(name="VENDORS")
public class Vendor extends PersistableEntity{

	@Column(name="NAME")
	private String name;

	@Column(name="ADDRESS")
	private String address;

	@Column(name="SITE_URL")
	private String siteUrl;

	@Column(name="LOGO_URL")
	private String logoUrl;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="CITY")
	private String city;

	@Column(name="PINCODE")
	private String pinCode;

	@Column(name="PHONE_NUMBER")
	private String phoneNumber;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
