package com.itech.customer.model;

public class Customer {

	private Long id;

	private String name;

	private String bankAccountNumber;

	private String billingName;

	private String phoneNo;

	private String alternativePhoneNo;

	private String advance;

	private String address;

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

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBillingName() {
		return billingName;
	}

	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAlternativePhoneNo() {
		return alternativePhoneNo;
	}

	public void setAlternativePhoneNo(String alternativePhoneNo) {
		this.alternativePhoneNo = alternativePhoneNo;
	}

	public String getAdvance() {
		return advance;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
