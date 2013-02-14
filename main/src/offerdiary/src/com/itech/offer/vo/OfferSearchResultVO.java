package com.itech.offer.vo;

import java.util.List;

import com.itech.offer.model.Offer;

public class OfferSearchResultVO {
	private List<Offer> offers;

	private Long totalCount;

	private Integer perPageCount;

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPerPageCount() {
		return perPageCount;
	}

	public void setPerPageCount(Integer perPageCount) {
		this.perPageCount = perPageCount;
	}
}
