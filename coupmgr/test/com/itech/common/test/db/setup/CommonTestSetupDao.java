package com.itech.common.test.db.setup;

import junit.framework.Test;

import com.itech.common.test.db.CommonTestDatabase;


public abstract class CommonTestSetupDao extends CommonTestSetupBase {
	private static CommonTestDatabase tDb = CommonTestDatabase.getInstance();

	public CommonTestSetupDao(Test suite) {
		super(suite);
	}

	protected void setup() throws Exception {
		super.setUp();

		if (!tDb.isSetupComplete()) {
			tDb.setup();
		}
	}

	protected void teardown() throws Exception {
		super.tearDown();
	}
}
