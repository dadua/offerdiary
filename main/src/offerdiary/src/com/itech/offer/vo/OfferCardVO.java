package com.itech.offer.vo;

import java.util.List;

import com.itech.offer.model.OfferCard;

public class OfferCardVO {

	private List<OfferCard> cards;

	private Long totalCount;

	private Integer perPageCount;

	public List<OfferCard> getCards() {
		return cards;
	}

	public void setCards(List<OfferCard> cards) {
		this.cards = cards;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public void setPerPageCount(Integer perPageCount) {
		this.perPageCount = perPageCount;
	}

	public Integer getPerPageCount() {
		return perPageCount;
	}

}
