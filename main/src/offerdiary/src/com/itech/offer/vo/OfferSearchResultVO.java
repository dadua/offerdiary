package com.itech.offer.vo;

import java.util.List;

public class OfferSearchResultVO {
	private List<OfferVO> offers;

	private Long totalCount;

	private Integer perPageCount;

	public List<OfferVO> getOffers() {
		return offers;
	}

	public void setOffers(List<OfferVO> offers) {
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
