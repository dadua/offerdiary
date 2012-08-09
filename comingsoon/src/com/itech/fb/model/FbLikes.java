package com.itech.fb.model;

import java.util.List;

public class FbLikes {
	
	private List<FbLike> data;
	
	private FbPagination paging;

	public void setData(List<FbLike> data) {
		this.data = data;
	}

	public List<FbLike> getData() {
		return data;
	}

	public void setPaging(FbPagination paging) {
		this.paging = paging;
	}

	public FbPagination getPaging() {
		return paging;
	}

}
