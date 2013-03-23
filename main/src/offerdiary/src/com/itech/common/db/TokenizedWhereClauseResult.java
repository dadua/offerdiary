package com.itech.common.db;

import java.util.HashMap;
import java.util.Map;

public class TokenizedWhereClauseResult {

	private String tokenizedWhereClause;

	private Map<String, String> tokenNameValueParams = new HashMap<String, String>();

	public String getTokenizedWhereClause() {
		return tokenizedWhereClause;
	}

	public void setTokenizedWhereClause(String tokenizedWhereClause) {
		this.tokenizedWhereClause = tokenizedWhereClause;
	}

	public void setTokenNameValueParams(Map<String, String> tokenNameValueParams) {
		this.tokenNameValueParams = tokenNameValueParams;
	}

	public Map<String, String> getTokenNameValueMap() {
		return tokenNameValueParams;
	}

}
