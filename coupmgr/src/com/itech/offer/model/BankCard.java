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

import com.itech.common.db.PersistableEntity;

@Entity
@Table(name="BANK_CARDS")
public class BankCard extends PersistableEntity{
	public enum BankCardType{
		CREDIT, DEBIT
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name="ISSUING_BANK")
	private Vendor issuingBank;

	@Enumerated(EnumType.STRING)
	@Column(name="CARD_TYPE")
	private BankCardType cardType;//DEbit/Credit

	@Column(name="PAYMENT_SYSTEM_TYPE")
	private String paymentSystemType;//VISA,MASTER etc

	@Column(name="PRIVILEGE")
	private String privilege;//Platinum, Gold

	@Column(name="DESCRUPTION")
	private String description;



	public Vendor getIssuingBank() {
		return issuingBank;
	}

	public void setIssuingBank(Vendor issuingBank) {
		this.issuingBank = issuingBank;
	}

	public BankCardType getCardType() {
		return cardType;
	}

	public void setCardType(BankCardType cardType) {
		this.cardType = cardType;
	}

	public String getPaymentSystemType() {
		return paymentSystemType;
	}

	public void setPaymentSystemType(String paymentSystemType) {
		this.paymentSystemType = paymentSystemType;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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




}
