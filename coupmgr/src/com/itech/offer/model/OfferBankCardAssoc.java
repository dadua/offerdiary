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
@Table(name=OfferBankCardAssocModelConstants.TABLE_OFFER_CARD_ASSOC)
public class OfferBankCardAssoc extends PersistableEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=OfferBankCardAssocModelConstants.COL_ID)
	private Long id;

	@ManyToOne
	@JoinColumn(name=OfferBankCardAssocModelConstants.COL_OFFER)
	private Offer offer;

	@ManyToOne
	@JoinColumn(name=OfferBankCardAssocModelConstants.COL_BANK_CARD)
	private BankCard bankCard;

	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public BankCard getBankCard() {
		return bankCard;
	}
	public void setBankCard(BankCard bankCard) {
		this.bankCard = bankCard;
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
