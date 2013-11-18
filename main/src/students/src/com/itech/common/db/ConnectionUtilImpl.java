package com.itech.common.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ConnectionUtilImpl implements ConnectionUtil{
	private static final Logger logger = Logger.getLogger(ConnectionUtil.class);
	private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<Connection>();

	@Override
	public Connection getCurrentConnection() {
		return threadLocalConnection.get();
	}

	@Override
	public void createNewConnection(boolean fromPool) {
		try {
			Connection connection = threadLocalConnection.get();
			if (connection != null && !connection.isClosed()) {
				logger.error("A previously opened connection found in thread local.");
				releaseCurrentConnection();
			}
			connection = getNewConnectionFromDatabase(fromPool);
			if (connection == null) {
				logger.error("Unable to get connection from database, null connection provided by database.");
				throw new RuntimeException("Unable to get connection from database, null connection provided by database.");
			}
			threadLocalConnection.set(connection);
		} catch (SQLException e) {
			logger.error("Unable to get connection from database", e);
			throw new RuntimeException("Unable to get new database connection", e);
		}
	}

	@Override
	public void createNewConnection() {
		createNewConnection(true);
	}

	private Connection getNewConnectionFromDatabase(boolean pool) throws SQLException {
		return DBConnectionManager.getConnection();
	}

	@Override
	public void releaseCurrentConnection() {
		Connection connection = threadLocalConnection.get();
		if (connection == null) {
			logger.error("No connection object present in thread local store.");
		}
		threadLocalConnection.set(null);
		DBConnectionManager.rollback(connection);
		DBConnectionManager.closeConnection(connection);
	}

	@Override
	public void commitCurrentConnection() {
		try {
			getCurrentConnection().commit();
		} catch (SQLException ex) {
			logger.error("Error occoured while commiting SQL transaction.", ex);
			throw new RuntimeException("Error occoured while commiting SQL transaction.", ex);
		}
	}

	@Override
	public void rollbackCurrentConnection() {
		DBConnectionManager.rollback(getCurrentConnection());
	}

}
