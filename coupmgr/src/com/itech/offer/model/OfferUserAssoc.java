package com.itech.offer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.itech.common.db.PersistableEntity;
import com.itech.coupon.model.User;
import com.itech.offer.model.enums.OfferOwnershipType;
import com.itech.offer.model.enums.OfferSharingType;

@Entity
@Table(name="OFFER_USER_ASSOC")
public class OfferUserAssoc extends PersistableEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;


	@ManyToOne
	@JoinColumn(name="OFFER_ID")
	private Offer offer;

	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	@ManyToOne
	@JoinColumn(name="ORIOGINAL_USER_ID")
	private User originalUser;

	@Enumerated(EnumType.STRING)
	@Column(name="OWNERSHIP_TYPE")
	private OfferOwnershipType ownershipType = OfferOwnershipType.CREATOR;

	@Enumerated(EnumType.STRING)
	@Column(name="OFFER_SHARING_TYPE")
	private OfferSharingType offerSharingType = OfferSharingType.PRIVATE;

	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getOriginalUser() {
		return originalUser;
	}
	public void setOriginalUser(User originalUser) {
		this.originalUser = originalUser;
	}
	public OfferOwnershipType getOwnershipType() {
		return ownershipType;
	}
	public void setOwnershipType(OfferOwnershipType ownershipType) {
		this.ownershipType = ownershipType;
	}
	public OfferSharingType getOfferSharingType() {
		return offerSharingType;
	}
	public void setOfferSharingType(OfferSharingType offerSharingType) {
		this.offerSharingType = offerSharingType;
	}
	@Override
	public boolean isTransient() {
		return id == null;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
}
