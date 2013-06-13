package com.itech.flower.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.itech.common.db.PersistableEntity;


@Entity
@Table(name="DAILY_FLOWER_PRICE")
public class DailyFlowerPrice extends PersistableEntity{

	public enum PriceUnit {
		KG, UNIT, DOZEN, BUNDLE
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name="SUPPLIER")
	private Supplier supplier;

	@ManyToOne
	@JoinColumn(name="FLOWER")
	private Flower flower;

	@Column(name="DATE")
	private Date date;

	@Column(name="UNIT_PRICE")
	private float unitPrice;//price per kg or price per unit

	@Column(name="UNIT_KG")
	private PriceUnit priceUnit = PriceUnit.KG;

	@Override
	public boolean isTransient() {
		return getId() == null;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Flower getFlower() {
		return flower;
	}

	public void setFlower(Flower flower) {
		this.flower = flower;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public PriceUnit getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(PriceUnit priceUnit) {
		this.priceUnit = priceUnit;
	}

}
