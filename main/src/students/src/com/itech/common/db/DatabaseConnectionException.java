package com.itech.common.db;

public class DatabaseConnectionException extends RuntimeException {

	private static final long serialVersionUID = 810264052813391856L;

	public DatabaseConnectionException(Throwable th) {
		super(th.getMessage(), th);
	}
}
