package com.itech.flower.vo;

import java.util.ArrayList;
import java.util.List;

import com.itech.flower.model.Contact;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.FlowerTransaction.FlowerTransactionType;
import com.itech.flower.model.FlowerTransactionEntry;

public class FlowerTransactionVO {

	private Long id;

	private String uniqueId;

	private Contact contact;

	//TODO: Do we need this??
	//This isn't sent from UI or shown at UI
	//private Date date;
	//private Date lastUpdated;

	//TODO: Enums in VOs,
	//Check Gson behavior,
	//Should this be string itself??
	private FlowerTransactionType transactionType;

	private float totalCost;

	private float payment;

	private String comment;

	private List<FlowerTransactionEntryVO> flowerTransactionEntries;

	public static FlowerTransactionVO toVO(FlowerTransaction flowerTransaction) {
		FlowerTransactionVO flowerTransactionVO = new FlowerTransactionVO();

		flowerTransactionVO.setId(flowerTransaction.getId());
		flowerTransactionVO.setContact(flowerTransaction.getContact());
		flowerTransactionVO.setUniqueId(flowerTransaction.getUniqueId());
		flowerTransactionVO.setTransactionType(flowerTransaction.getTransactionType());
		flowerTransactionVO.setComment(flowerTransaction.getComment());
		flowerTransactionVO.setPayment(flowerTransaction.getPayment());
		flowerTransactionVO.setTotalCost(flowerTransaction.getTotalCost());
		flowerTransactionVO.setFlowerTransactionEntries(getFlowerTransactionEntryVOs(flowerTransaction.getFlowerTransactionEntries()));

		return flowerTransactionVO;

	}

	public static FlowerTransaction fromVO (FlowerTransactionVO flowerTransactionVO) {
		FlowerTransaction flowerTransaction = new FlowerTransaction();

		flowerTransaction.setId(flowerTransactionVO.getId());
		flowerTransaction.setContact(flowerTransactionVO.getContact());
		flowerTransaction.setUniqueId(flowerTransactionVO.getUniqueId());
		flowerTransaction.setTransactionType(flowerTransactionVO.getTransactionType());
		flowerTransaction.setComment(flowerTransactionVO.getComment());
		flowerTransaction.setPayment(flowerTransactionVO.getPayment());
		flowerTransaction.setTotalCost(flowerTransactionVO.getTotalCost());
		flowerTransaction.setFlowerTransactionEntries(getFlowerTransactionEntries(flowerTransactionVO.getFlowerTransactionEntries()));

		return flowerTransaction;
	}

	private static List<FlowerTransactionEntry> getFlowerTransactionEntries(
			List<FlowerTransactionEntryVO> flowerTransactionEntriesVOs) {
		List<FlowerTransactionEntry> flowerTransactionEntries = new ArrayList<FlowerTransactionEntry>();
		for(FlowerTransactionEntryVO flowerTransactionEntryVO: flowerTransactionEntriesVOs) {
			flowerTransactionEntries.add(FlowerTransactionEntryVO.fromVO(flowerTransactionEntryVO));
		}
		return flowerTransactionEntries;
	}

	private static List<FlowerTransactionEntryVO> getFlowerTransactionEntryVOs(
			List<FlowerTransactionEntry> flowerTransactionEntries2) {
		List<FlowerTransactionEntryVO> flowerTransactionEntryVOs = new ArrayList<FlowerTransactionEntryVO>();
		for (FlowerTransactionEntry flowerTransactionEntry : flowerTransactionEntries2) {
			flowerTransactionEntryVOs.add(FlowerTransactionEntryVO.toVO(flowerTransactionEntry));
		}
		return flowerTransactionEntryVOs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	/*
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	 */

	public FlowerTransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(FlowerTransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public float getPayment() {
		return payment;
	}

	public void setPayment(float payment) {
		this.payment = payment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<FlowerTransactionEntryVO> getFlowerTransactionEntries() {
		return flowerTransactionEntries;
	}

	public void setFlowerTransactionEntries(
			List<FlowerTransactionEntryVO> flowerTransactionEntries) {
		this.flowerTransactionEntries = flowerTransactionEntries;
	}


}
