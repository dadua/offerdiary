package com.itech.flower.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.itech.common.db.PersistableEntity;

@Entity
@Table(name="FLOWER_TRANSACTIONS")
public class FlowerTransaction extends PersistableEntity {


	public enum FlowerTransactionType {
		IN,OUT
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="UNIQUE_ID")
	private String uniqueId;

	@ManyToOne
	@JoinColumn(name="CONTACT_ID")
	private Contact contact;

	@Column(name="DATE")
	private Date date;

	@Column(name="LAST_UPDATED")
	private Date lastUpdated;

	@Enumerated(EnumType.STRING)
	@Column(name="TRANSACTION_TYPE")
	private FlowerTransactionType transactionType;

	@Column(name="TOTAL_COST")
	private float totalCost;

	@OneToMany
	private List<FlowerTransactionEntry> flowerTransactionEntries;

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

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

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

	public List<FlowerTransactionEntry> getFlowerTransactionEntries() {
		return flowerTransactionEntries;
	}

	public void setFlowerTransactionEntries(
			List<FlowerTransactionEntry> flowerTransactionEntries) {
		this.flowerTransactionEntries = flowerTransactionEntries;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}
