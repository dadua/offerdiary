package com.itech.offer.model;

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
@Table(name=OfferShareModelConstant.TABLE_OFFER_SHARE)
public class OfferShare extends PersistableEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=OfferModelConstants.COL_ID)
	private Long id;

	@ManyToOne
	@JoinColumn(name=OfferShareModelConstant.COL_OFFER_ID)
	private Offer offer;

	@Column(name=OfferShareModelConstant.COL_CREATION_DATE)
	private Date creationDate;

	@Column(name=OfferShareModelConstant.COL_ACCESS_TOKEN)
	private String accessToken;

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public boolean isTransient() {
		return getId() == null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
