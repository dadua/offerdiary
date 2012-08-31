package com.itech.common.test.db.setup;

import junit.extensions.TestSetup;
import junit.framework.Test;

public abstract class CommonTestSetupBase extends TestSetup {
	public CommonTestSetupBase(Test suite) {
		super(suite);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
