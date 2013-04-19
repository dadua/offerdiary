package com.itech.hdfc.parser;

import com.itech.common.test.CommonTestUtil;
import com.itech.offer.hdfc.parser.HdfcOfferParserUtil;

public class TestHdfcOfferParserUtil extends CommonTestUtil {

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub

	}

	public void testGetMerchantNameFrom() {
		String fullOfferText1 = "ebay - Get 7.5 % off on your purchase at www.ebay.in ( max discount amount Rs.750 ) with HDFC Bank Debit Cards.";
		String fullOfferText2 = "Monginis.net: Exclusive 20% off on all Monginis Products available at monginis.net with your HDFC Bank Debit Card!";
		String fullOfferText3 = "Exclusive 20% off on all Monginis Products available at monginis.net with your HDFC Bank Debit Card!";
		String fullOfferText4 = "Monginis.net- Exclusive 20% off on all Monginis Products available at monginis.net with your HDFC Bank Debit Card!";

		String expectedMerchantNameForText1 = "ebay";
		String expectedMerchantNameForText2 = "Monginis.net";
		String expectedMerchantNameForText3 =  null;
		String expectedMerchantNameForText4 = "Monginis.net";

		String actualMerchantNameForText1 = HdfcOfferParserUtil.getMerchantNameFrom(fullOfferText1);
		String actualMerchantNameForText2 = HdfcOfferParserUtil.getMerchantNameFrom(fullOfferText2);
		String actualMerchantNameForText3 = HdfcOfferParserUtil.getMerchantNameFrom(fullOfferText3);
		String actualMerchantNameForText4 = HdfcOfferParserUtil.getMerchantNameFrom(fullOfferText4);

		assertEquals(expectedMerchantNameForText1, actualMerchantNameForText1);
		assertEquals(expectedMerchantNameForText2, actualMerchantNameForText2);
		assertEquals(expectedMerchantNameForText3, actualMerchantNameForText3);
		assertEquals(expectedMerchantNameForText4, actualMerchantNameForText4);

	}

	public void testGetOfferDescriptionFrom() {
		String fullOfferText1 = "ebay - Get 7.5 % off on your purchase at www.ebay.in ( max discount amount Rs.750 ) with HDFC Bank Debit Cards.";
		String fullOfferText2 = "Monginis.net: Exclusive 20% off on all Monginis Products available at monginis.net with your HDFC Bank Debit Card!";
		String fullOfferText3 = "Exclusive 20% off on all Monginis Products available at monginis.net with your HDFC Bank Debit Card!";
		String fullOfferText4 = "Monginis.net- Exclusive 20% off on all Monginis Products available at monginis.net with your HDFC Bank Debit Card!";

		String expectedOfferDescForText1 = "Get 7.5 % off on your purchase at www.ebay.in ( max discount amount Rs.750 ) with HDFC Bank Debit Cards.";
		String expectedOfferDescForText2 = "Exclusive 20% off on all Monginis Products available at monginis.net with your HDFC Bank Debit Card!";
		String expectedOfferDescForText3 =  "Exclusive 20% off on all Monginis Products available at monginis.net with your HDFC Bank Debit Card!";
		String expectedOfferDescForText4 = "Exclusive 20% off on all Monginis Products available at monginis.net with your HDFC Bank Debit Card!";

		String actualOfferDescForText1 = HdfcOfferParserUtil.getOfferDescriptionFrom(fullOfferText1);
		String actualOfferDescForText2 = HdfcOfferParserUtil.getOfferDescriptionFrom(fullOfferText2);
		String actualOfferDescForText3 = HdfcOfferParserUtil.getOfferDescriptionFrom(fullOfferText3);
		String actualOfferDescForText4 = HdfcOfferParserUtil.getOfferDescriptionFrom(fullOfferText4);

		assertEquals(expectedOfferDescForText1, actualOfferDescForText1);
		assertEquals(expectedOfferDescForText2, actualOfferDescForText2);
		assertEquals(expectedOfferDescForText3, actualOfferDescForText3);
		assertEquals(expectedOfferDescForText4, actualOfferDescForText4);

	}
}
