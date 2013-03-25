package com.itech.offer.vo;

import java.util.List;

public class OfferSearchResultVO {
	private List<OfferVO> offers;

	private Long totalCount;

	private Integer resultsPerPage;

	private Integer pageNumber;

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

	public Integer getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(Integer perPageCount) {
		this.resultsPerPage = perPageCount;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
}
