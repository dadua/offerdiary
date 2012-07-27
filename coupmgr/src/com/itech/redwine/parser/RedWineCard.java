package com.itech.redwine.parser;

import java.util.List;

import org.jsoup.nodes.Element;
public class RedWineCard {
	
	private String cardName;
	private List<RedWineOffer> offers;
	private int offersCount;
	private String imageUrl;
	private String cardUrl;
	private Element cardElement;
	private String cardType;
	
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public List<RedWineOffer> getOffers() {
		return offers;
	}
	public void setOffers(List<RedWineOffer> offers) {
		this.offers = offers;
	}
	public int getOffersCount() {
		return offersCount;
	}
	public void setOffersCount(int offersCount) {
		this.offersCount = offersCount;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCardUrl() {
		return cardUrl;
	}
	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}
	public Element getCardElement() {
		return cardElement;
	}
	public void setCardElement(Element cardElement) {
		this.cardElement = cardElement;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
}
