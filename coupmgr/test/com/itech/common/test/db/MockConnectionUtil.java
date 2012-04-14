package com.itech.common.test.db;

import java.sql.Connection;

import com.itech.common.db.ConnectionUtil;

/**
 * Mock implementation of connection util. No implementation of functions required.
 */

public class MockConnectionUtil implements ConnectionUtil{

	@Override
	public void commitCurrentConnection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void createNewConnection(boolean fromPool) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createNewConnection() {
		// TODO Auto-generated method stub

	}

	@Override
	public Connection getCurrentConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void releaseCurrentConnection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollbackCurrentConnection() {
		// TODO Auto-generated method stub

	}

}
