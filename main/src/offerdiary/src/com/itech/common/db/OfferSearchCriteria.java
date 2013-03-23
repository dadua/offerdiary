package com.itech.common.db;


public class OfferSearchCriteria extends SearchCriteria {

	private boolean cardOffersOnly = false;
	private boolean offersOnMyCardsOnly = false;
	private String cardId;


	public boolean isCardOffersOnly() {
		return cardOffersOnly;
	}
	public void setCardOffersOnly(boolean cardOffersOnly) {
		this.cardOffersOnly = cardOffersOnly;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public boolean isOffersOnMyCardsOnly() {
		return offersOnMyCardsOnly;
	}
	public void setOffersOnMyCardsOnly(boolean offersOnMyCardsOnly) {
		this.offersOnMyCardsOnly = offersOnMyCardsOnly;
	}

}
