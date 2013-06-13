package com.itech.flower.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.itech.common.db.PersistableEntity;


@Entity
@Table(name="FLOWERS")
public class Flower extends PersistableEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="NAME")
	private String name;

	@Column(name="COLOR")
	private String color;

	@Column(name="QUANT_IN_STOCK")
	private Float quantityInStock;

	@Override
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public boolean isTransient() {
		return id == null;
	}

	public Float getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Float quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
}
