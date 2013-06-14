package com.itech.flower.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name="FLOW_TYEP")
	private CashFlowType flowType;

	@Enumerated(EnumType.STRING)
	@Column(name="TRANSACTION_TYPE")
	private CashTransactionType transactionType;

	@Column(name="AMMOUNT")
	private float ammount;

	@Column(name="COMMENT")
	private String comment;

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

	public float getAmmount() {
		return ammount;
	}

	public void setAmmount(float ammount) {
		this.ammount = ammount;
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
}
