package com.itech.common.db;

import java.sql.Connection;

public abstract class  CommonBaseDAOImpl<T> implements CommonBaseDAO<T>{
	private final ConnectionFactory connectionFactory = new ConnectionFactoryImpl();
	private Connection connection;

	public CommonBaseDAOImpl() {

	}


	public Connection getConnection() {
		if (connection == null) {
			return connectionFactory.getCurrentConnection();
		}
		return connection;
	}


	public void setConnection(Connection con) {
		connection = con;

	}

}
