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
@Table(name="CUSTOMER_FLOWER_ASSOC")
public class CustomerFlowerAssoc extends PersistableEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name="CUSTOMER")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name="FLOWER")
	private Flower flower;

	public CustomerFlowerAssoc() {

	}

	public CustomerFlowerAssoc(Flower flower, Customer customer) {
		this.flower = flower;
		this.customer = customer;
	}

	@Override
	public boolean isTransient() {
		return id == null;
	}

	@Override
	public Long getId() {
		return null;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Flower getFlower() {
		return flower;
	}

	public void setFlower(Flower flower) {
		this.flower = flower;
	}

}
