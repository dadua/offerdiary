package com.itech.common.db;

import java.sql.Connection;

public interface ConnectionFactory {

	/**
	 * @return Current Database Connection available.
	 */
	public Connection getCurrentConnection();

	public void commitCurrentConnection();

	public void rollbackCurrentConnection();

}
