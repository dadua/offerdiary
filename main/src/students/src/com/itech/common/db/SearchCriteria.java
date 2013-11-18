package com.itech.common.db;

public class SearchCriteria {


	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final int DEFAULT_RESULTS_PER_PAGE = 10;
	private String searchString;
	private String searchType;
	private String q;
	private String uniqueFilter;
	private Boolean privateSearchOnly = Boolean.TRUE;
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

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getUniqueFilter() {
		return uniqueFilter;
	}

	public void setUniqueFilter(String uniqueFilter) {
		this.uniqueFilter = uniqueFilter;
	}

	public Boolean getPrivateSearchOnly() {
		return privateSearchOnly;
	}

	public void setPrivateSearchOnly(Boolean privateSearchOnly) {
		this.privateSearchOnly = privateSearchOnly;
	}
}
