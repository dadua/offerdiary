package com.itech.common.db;

import java.sql.Connection;

public class ConnectionFactoryImpl implements ConnectionFactory{
	private ConnectionUtil scorConnectionUtil;

	@Override
	public Connection getCurrentConnection() {
		return getScorConnectionUtil().getCurrentConnection();
	}

	@Override
	public void commitCurrentConnection() {
		getScorConnectionUtil().commitCurrentConnection();
	}

	@Override
	public void rollbackCurrentConnection() {
		getScorConnectionUtil().rollbackCurrentConnection();
	}

	public void setScorConnectionUtil(ConnectionUtil scorConnectionUtil) {
		this.scorConnectionUtil = scorConnectionUtil;
	}

	public ConnectionUtil getScorConnectionUtil() {
		if (scorConnectionUtil == null) {
			scorConnectionUtil = new ConnectionUtilImpl();
		}
		return scorConnectionUtil;
	}

}
