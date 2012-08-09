package com.itech.common.test.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.itech.common.db.DBConnectionManager;
import com.itech.common.server.ProjectLoader;

/**
 * TODO: put class summary here.
 */
public class CommonTestDatabase {
	private static final String TEST_SERVER_PROPERTIES = "test_db.properties";

	private static Logger logger = Logger.getLogger(CommonTestDatabase.class);

	private static CommonTestDatabase singleton = new CommonTestDatabase();

	private boolean isSetupComplete = false;

	private DBConnectionManager db;

	private CommonTestDatabase() {
		setup();
	}

	public String propPrefix() {
		return "scortest";
	}

	public static CommonTestDatabase getInstance() {
		return singleton;
	}

	public void setup() {
		try {
			ProjectLoader.init(TEST_SERVER_PROPERTIES);
			isSetupComplete = true;
		} catch (Exception ex) {
			logger.error("TestDatabase setup could not be completed", ex);
			isSetupComplete = false;
		}
	}

	public Connection getConnection() throws SQLException {
		assert (isSetupComplete());
		return db.getConnection();
	}

	public boolean isSetupComplete() {
		return isSetupComplete;
	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				logger.error("Error Closing test connection", ex);
			}
			con = null;
		}
	}

}