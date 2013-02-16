package com.itech.offer;

import com.itech.common.test.CommonTestUtil;

public class TestOfferSearchFilterUtil extends CommonTestUtil{

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub

	}

	public void testGetNumberOfDays() {
		int expectedNumberOfDays = 80;
		String filterString = OfferSearchFilterUtil.PREFIX_EXPIRY_FILTER + expectedNumberOfDays + OfferSearchFilterUtil.SUFFIX_DAYS_UNIT;
		int resultNumberOfDays = OfferSearchFilterUtil.getNumberOfDays(filterString);
		assertEquals(expectedNumberOfDays, resultNumberOfDays);
	}

}
