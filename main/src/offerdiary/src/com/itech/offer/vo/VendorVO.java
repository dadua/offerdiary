package com.itech.offer.vo;

import com.itech.offer.model.Vendor;

public class VendorVO {

	private Long id;

	private String name;

	private String address;

	private String siteUrl;

	private boolean isAbsoluteSiteUrl;

	private String logoUrl;

	private String description;

	private String city;

	private String pinCode;

	private String phoneNumber;

	private Vendor parentVendor;


	public static VendorVO getVendorVOFor(Vendor vendor) {
		if (vendor == null) {
			return null;
		}

		VendorVO vendorVO = new VendorVO();

		vendorVO.setAddress(vendor.getAddress());
		vendorVO.setCity(vendor.getCity());
		vendorVO.setDescription(vendor.getDescription());
		vendorVO.setLogoUrl(vendor.getLogoUrl());
		vendorVO.setId(vendor.getId());
		vendorVO.setName(vendor.getName());
		vendorVO.setPinCode(vendor.getPinCode());
		if (vendor.getSiteUrl().startsWith("http")) {
			vendorVO.setAbsoluteSiteUrl(true);
		}
		vendorVO.setSiteUrl(vendor.getSiteUrl());
		return vendorVO;
	}


	public static void fillVendorFromVO(Vendor vendor, VendorVO vendorVO) {
		//TODO: update offer from offerVO
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Vendor getParentVendor() {
		return parentVendor;
	}

	public void setParentVendor(Vendor parentVendor) {
		this.parentVendor = parentVendor;
	}


	public boolean isAbsoluteSiteUrl() {
		return isAbsoluteSiteUrl;
	}


	public void setAbsoluteSiteUrl(boolean absoluteSiteUrl) {
		this.isAbsoluteSiteUrl = absoluteSiteUrl;
	}

}
