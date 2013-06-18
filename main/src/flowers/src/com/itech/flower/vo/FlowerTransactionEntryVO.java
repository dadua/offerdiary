package com.itech.flower.vo;

import java.sql.Date;

import com.itech.flower.model.Flower;
import com.itech.flower.model.FlowerTransactionEntry;

public class FlowerTransactionEntryVO {

	private Long id;
	private float totalCost;
	private float quantity;
	private float unitPrice;
	private Flower flower;
	private Long dateMillis;
	private String uniqueId;


	public static FlowerTransactionEntryVO toVO(FlowerTransactionEntry flowerTransactionEntry) {
		FlowerTransactionEntryVO flowerTransactionEntryVO = new FlowerTransactionEntryVO();
		flowerTransactionEntryVO.setId(flowerTransactionEntry.getId());
		flowerTransactionEntryVO.setTotalCost(flowerTransactionEntry.getTotalCost());
		flowerTransactionEntryVO.setQuantity(flowerTransactionEntry.getQuantity());
		flowerTransactionEntryVO.setUnitPrice(flowerTransactionEntry.getUnitPrice());
		flowerTransactionEntryVO.setFlower(flowerTransactionEntry.getFlower());
		flowerTransactionEntryVO.setDateMillis(flowerTransactionEntry.getDate().getTime());
		flowerTransactionEntryVO.setUniqueId(flowerTransactionEntry.getUniqueId());
		return flowerTransactionEntryVO;
	}

	public static FlowerTransactionEntry fromVO(FlowerTransactionEntryVO flowerTransactionEntryVO) {
		FlowerTransactionEntry flowerTransactionEntry = new FlowerTransactionEntry();

		flowerTransactionEntry.setDate(new Date(flowerTransactionEntryVO.getDateMillis()));
		flowerTransactionEntry.setId(flowerTransactionEntryVO.getId());
		flowerTransactionEntry.setTotalCost(flowerTransactionEntryVO.getTotalCost());
		flowerTransactionEntry.setQuantity(flowerTransactionEntryVO.getQuantity());
		flowerTransactionEntry.setUnitPrice(flowerTransactionEntryVO.getUnitPrice());
		flowerTransactionEntry.setFlower(flowerTransactionEntryVO.getFlower());
		flowerTransactionEntry.setUniqueId(flowerTransactionEntryVO.getUniqueId());

		return flowerTransactionEntry;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Flower getFlower() {
		return flower;
	}
	public void setFlower(Flower flower) {
		this.flower = flower;
	}
	public Long getDateMillis() {
		return dateMillis;
	}
	public void setDateMillis(Long dateMillis) {
		this.dateMillis = dateMillis;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

}
