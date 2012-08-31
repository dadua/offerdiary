package com.itech.offer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.itech.common.db.PersistableEntity;
import com.itech.coupon.model.User;

@Entity
@Table(name=OfferCardUserAssocModelConstants.TABLE_OFFER_CARD_USER_ASSOC)
public class OfferCardUserAssoc extends PersistableEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=OfferCardUserAssocModelConstants.COL_ID)
	private Long id;

	@ManyToOne
	@JoinColumn(name=OfferCardUserAssocModelConstants.COL_USER)
	private User user;

	@ManyToOne
	@JoinColumn(name=OfferCardUserAssocModelConstants.COL_OFFER_CARD)
	private OfferCard offerCard;

	public OfferCard getOfferCard() {
		return offerCard;
	}
	public void setOfferCard(OfferCard offerCard) {
		this.offerCard = offerCard;
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
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}

}
