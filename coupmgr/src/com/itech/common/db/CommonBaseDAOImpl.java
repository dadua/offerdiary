package com.itech.common.db;

import java.sql.Connection;

public abstract class  CommonBaseDAOImpl<T> implements CommonBaseDAO<T>{
	private ConnectionFactory connectionFactory;
	private Connection connection;

	public CommonBaseDAOImpl() {

	}


	public Connection getConnection() {
		if (connection == null) {
			return getConnectionFactory().getCurrentConnection();
		}
		return connection;
	}


	public void setConnection(Connection con) {
		connection = con;

	}


	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}


	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

}
