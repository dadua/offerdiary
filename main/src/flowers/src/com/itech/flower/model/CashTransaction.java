package com.itech.flower.model;

import java.sql.Date;

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
@Table(name="CASH_TRANSACTIONS")
public class CashTransaction extends  PersistableEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name="FLOW_TYPE")
	private CashFlowType flowType;


	@Column(name="UNIQUE_ID")
	private String uniqueId;

	@Enumerated(EnumType.STRING)
	@Column(name="TRANSACTION_TYPE")
	private CashTransactionType transactionType;

	@Column(name="AMOUNT")
	private float amount;

	@Column(name="COMMENT")
	private String comment;


	@ManyToOne
	@JoinColumn(name="CONTACT_ID")
	private Contact contact;

	@Column(name="DATE")
	private Date date;


	public enum CashFlowType {
		IN, OUT
	}

	public enum CashTransactionType {
		ADVANCE, DEBIT, CREDIT, SALARY, OTHER
	}

	@Override
	public boolean isTransient() {
		return id == null;
	}

	@Override
	public Long getId() {
		return id;
	}

	public CashFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(CashFlowType flowType) {
		this.flowType = flowType;
	}

	public CashTransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(CashTransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return amount;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUniqueId() {
		return uniqueId;
	}
}
