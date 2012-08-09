package com.itech.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.itech.common.server.ServerConfig;



public final class DBConnectionManager {

	private static final Logger logger = Logger.getLogger(DBConnectionManager.class);
	public static void init() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	}

	private DBConnectionManager() {}

	public static void main(String[] args) throws ClassNotFoundException {
		//test connection
		DBConnectionManager.init();
		DBConnectionManager.getConnection();
	}

	public static Connection getConnection() throws DatabaseConnectionException {
		try {
			Connection conn = DriverManager.getConnection(ServerConfig.dbURL);
			conn.setAutoCommit(false);
			return conn;
		} catch(Exception e) {
			throw new DatabaseConnectionException(e);
		}
	}

	public static void cleanDb(Connection connection) {
		Statement ps = null;
		ResultSet rs = null;
		try {
			logger.info("Cleaning database tables");
			ps = connection.createStatement();
			ps.executeUpdate("delete from TRAIN_INFOS");
			ps.executeUpdate("delete from STATION_INFOS");
			ps.executeUpdate("delete from TRAIN_STATION_ASSOC");
			logger.info("Databse tables cleaned.");
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}

	}
	public static void testDb() {
		logger.info("Testing db connection");
		Connection connection = getConnection();
		closeConnection(connection);
		logger.info("db connection tested succesfully.");
	}

	/**
	 * It's the caller's responsibility to explicitly close the connection, after usage
	 *
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn("Could not close DB Connection");
			}
		}
	}

	public static void cleanDbPsRS(Statement ps, ResultSet rs) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			logger.debug("", e);
		}
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			logger.debug("", e);
		}
	}

	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				logger.warn("Could not close DB Connection");
			}
		}
	}
}

