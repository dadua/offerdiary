package com.itech.flower.vo;

import java.sql.Date;

import com.itech.flower.model.CashTransaction;
import com.itech.flower.model.CashTransaction.CashFlowType;
import com.itech.flower.model.CashTransaction.CashTransactionType;

public class CashTransactionVO {

	private Long id;

	private CashFlowType flowType;

	private String uniqueId;

	private CashTransactionType transactionType;

	private float amount;

	private String comment;

	private ContactVO contact;

	private Long dateMillis;

	public static CashTransactionVO toVO(CashTransaction cashTransaction) {
		CashTransactionVO cashTransactionVO = new CashTransactionVO();

		cashTransactionVO.setId(cashTransaction.getId());
		cashTransactionVO.setContact(ContactVO.toVO(cashTransaction.getContact()));
		cashTransactionVO.setUniqueId(cashTransaction.getUniqueId());
		cashTransactionVO.setTransactionType(cashTransaction.getTransactionType());
		cashTransactionVO.setComment(cashTransaction.getComment());
		cashTransactionVO.setFlowType(cashTransaction.getFlowType());
		cashTransactionVO.setDateMillis(cashTransaction.getDate().getTime());
		return cashTransactionVO;

	}

	public static CashTransaction fromVO (CashTransactionVO cashTransactionVO) {
		CashTransaction cashTransaction = new CashTransaction();

		cashTransaction.setDate(new Date(cashTransactionVO.getDateMillis()));
		cashTransaction.setId(cashTransactionVO.getId());
		cashTransaction.setContact(ContactVO.fromVO(cashTransactionVO.getContact()));
		cashTransaction.setUniqueId(cashTransactionVO.getUniqueId());
		cashTransaction.setTransactionType(cashTransactionVO.getTransactionType());
		cashTransaction.setComment(cashTransactionVO.getComment());
		cashTransaction.setAmount(cashTransactionVO.getAmount());

		return cashTransaction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CashFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(CashFlowType flowType) {
		this.flowType = flowType;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public CashTransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(CashTransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ContactVO getContact() {
		return contact;
	}

	public void setContact(ContactVO contact) {
		this.contact = contact;
	}

	public Long getDateMillis() {
		return dateMillis;
	}

	public void setDateMillis(Long dateMillis) {
		this.dateMillis = dateMillis;
	}
}
