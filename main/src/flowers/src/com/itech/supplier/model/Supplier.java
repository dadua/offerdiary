package com.itech.supplier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.itech.common.db.PersistableEntity;

@Entity
@Table(name="SUPPLIERS")
public class Supplier extends PersistableEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="UNIQUE_ID")
	private String uniqueId;

	@Column(name="NAME")
	private String name;

	@Column(name="NICK_NAME")
	private String nickName;

	@Column(name="BANK_ACCOUNT_DETAILS")
	private String bankAccountDetails;

	@Column(name="BILLING_NAME")
	private String billingName;

	@Column(name="PHONE_NO")
	private String phoneNo;

	@Column(name="ALTERNATIVE_PHONE_NO")
	private String alternativePhoneNo;

	@Column(name="ADDRESS")
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setAlternativePhoneNo(String alternativePhoneNo) {
		this.alternativePhoneNo = alternativePhoneNo;
	}

	public String getAlternativePhoneNo() {
		return alternativePhoneNo;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setBankAccountDetails(String bankAccountDetails) {
		this.bankAccountDetails = bankAccountDetails;
	}

	public String getBankAccountDetails() {
		return bankAccountDetails;
	}

	@Override
	public boolean isTransient() {
		return id == null;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
