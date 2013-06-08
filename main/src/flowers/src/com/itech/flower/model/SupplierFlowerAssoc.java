package com.itech.flower.model;

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
@Table(name="SUPPLIER_FLOWER_ASSOC")
public class SupplierFlowerAssoc extends PersistableEntity {

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

	public SupplierFlowerAssoc() {

	}
	public SupplierFlowerAssoc(Flower flower, Supplier supplier) {
		this.flower = flower;
		this.supplier = supplier;

	}

	@Override
	public boolean isTransient() {
		return id == null;
	}

	@Override
	public Long getId() {
		return null;
	}



	public Flower getFlower() {
		return flower;
	}

	public void setFlower(Flower flower) {
		this.flower = flower;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
