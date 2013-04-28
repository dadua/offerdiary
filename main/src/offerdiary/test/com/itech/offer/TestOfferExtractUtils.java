package com.itech.offer;

import com.itech.common.test.CommonTestUtil;

public class TestOfferExtractUtils extends CommonTestUtil {

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub

	}

	public void testIsOffer() {

		String buyOfferDescription = "Buy 1 get 2 free";
		assertEquals(true, OfferExtractUtils.isOffer(buyOfferDescription));

		assertEquals(true, OfferExtractUtils.isOffer("To avail use Code AAXYZ"));
		assertEquals(true, OfferExtractUtils.isOffer("Discount upto 20%"));
		assertEquals(false, OfferExtractUtils.isOffer("Having Product Code Book23234"));
	}

}
