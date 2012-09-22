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

@Entity
@Table(name=OfferOfferCardAssocModelConstants.TABLE_OFFER_OFFER_CARD_ASSOC)
public class OfferOfferCardAssoc extends PersistableEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=OfferOfferCardAssocModelConstants.COL_ID)
	private Long id;

	@ManyToOne
	@JoinColumn(name=OfferOfferCardAssocModelConstants.COL_OFFER)
	private Offer offer;

	@ManyToOne
	@JoinColumn(name=OfferOfferCardAssocModelConstants.COL_OFFER_CARD)
	private OfferCard offerCard;

	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
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

}
