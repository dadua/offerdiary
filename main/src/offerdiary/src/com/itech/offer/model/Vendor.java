package com.itech.offer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.itech.common.db.PersistableEntity;
import com.itech.coupon.model.User;
import com.itech.offer.model.enums.VendorType;

@Entity
@Table(name=VendorModelContants.TABLE_VENDOR)
public class Vendor extends PersistableEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=VendorModelContants.COL_ID)
	private Long id;

	@Column(name=VendorModelContants.COL_NAME)
	private String name;

	@Column(name=VendorModelContants.COL_ADDRESS)
	private String address;

	@Column(name=VendorModelContants.COL_SITE_URL)
	@Type(type="text")
	private String siteUrl;


	//Image URL on our our server
	@Column(name=VendorModelContants.COL_LOGO_URL)
	private String logoUrl;

	@Column(name=VendorModelContants.COL_DESCRIPTION)
	@Type(type="text")
	private String description;

	@Column(name=VendorModelContants.COL_CITY)
	private String city;

	@Column(name=VendorModelContants.COL_PIN_CODE)
	private String pinCode;

	@Column(name=VendorModelContants.COL_PHONE_NUMBER)
	private String phoneNumber;

	@ManyToOne
	@JoinColumn(name=VendorModelContants.COL_PARENT_VENDOR)
	private Vendor parentVendor;

	@Column(name=VendorModelContants.COL_VENDOR_TYPE)
	@Enumerated(EnumType.STRING)
	private VendorType vendorType = VendorType.USER_DEFINED;

	@ManyToOne
	@JoinColumn(name=VendorModelContants.COL_OWNER)
	private User owner;

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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean isTransient() {
		return id == null;
	}

	public void setParentVendor(Vendor parentVendor) {
		this.parentVendor = parentVendor;
	}

	public Vendor getParentVendor() {
		return parentVendor;
	}

	public void setVendorType(VendorType vendorType) {
		this.vendorType = vendorType;
	}

	public VendorType getVendorType() {
		return vendorType;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getOwner() {
		return owner;
	}

}
