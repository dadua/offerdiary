package com.itech.common.test.db.setup;


import junit.framework.Test;

public abstract class CommonTestSetupUtil extends CommonTestSetupBase {
	public CommonTestSetupUtil(Test suite) {
		super(suite);
	}

	protected void setup() throws Exception {
		super.setUp();
	}

	protected void teardown() throws Exception {
		super.tearDown();
	}
}
