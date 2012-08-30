package com.itech.common.db;

public class SearchCriteria {


	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final int DEFAULT_RESULTS_PER_PAGE = 10;
	private String searchString;
	private Integer pageNumber = DEFAULT_PAGE_NUMBER;
	private Integer resultsPerPage = DEFAULT_RESULTS_PER_PAGE;

	public SearchCriteria(String searchString, Integer pageNumber,
			Integer resultsPerPage) {
		super();
		this.searchString = searchString;
		this.pageNumber = pageNumber;
		this.resultsPerPage = resultsPerPage;
	}

	public SearchCriteria(){

	}

	public Integer getResultsPerPage() {
		return resultsPerPage;
	}
	public void setResultsPerPage(Integer resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
