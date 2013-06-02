package com.itech.common.db;

import java.sql.Connection;

public interface ConnectionUtil {
	public Connection getCurrentConnection();
	public void createNewConnection(boolean fromPool);
	public void createNewConnection();
	public void releaseCurrentConnection();
	public void commitCurrentConnection();
	public void rollbackCurrentConnection();
}
