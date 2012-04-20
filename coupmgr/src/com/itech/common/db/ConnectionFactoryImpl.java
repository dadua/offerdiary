package com.itech.common.db;

import java.sql.Connection;

public class ConnectionFactoryImpl implements ConnectionFactory{
	private ConnectionUtil connectionUtil;

	@Override
	public Connection getCurrentConnection() {
		return getConnectionUtil().getCurrentConnection();
	}

	@Override
	public void commitCurrentConnection() {
		getConnectionUtil().commitCurrentConnection();
	}

	@Override
	public void rollbackCurrentConnection() {
		getConnectionUtil().rollbackCurrentConnection();
	}

	public void setConnectionUtil(ConnectionUtil connectionUtil) {
		this.connectionUtil = connectionUtil;
	}

	public ConnectionUtil getConnectionUtil() {
		return connectionUtil;
	}

}
