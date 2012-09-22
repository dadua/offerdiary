package com.itech.redwine.parser;

import com.itech.common.test.CommonTestUtil;

public class TestRedWIneParserUtil extends CommonTestUtil{

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub

	}

	public void testGetIdFromCard_validUrl() {
		String cardUrl = "http://redanar.com/mobile/badge/view/88";
		String actualResultId = RedWineParserUtil.getIdFromCard(cardUrl);
		String expectedId = "88";
		assertEquals(expectedId, actualResultId);
	}

	public void testGetIdFromCard_invalidUrl() {
		String cardUrl = "http://redanar.com/mobile/badge/view/88/add";
		String actualResultId = RedWineParserUtil.getIdFromCard(cardUrl);
		String expectedId = null;
		assertEquals(expectedId, actualResultId);
	}

	public void testGetIdFromCard_nullUrl() {
		String cardUrl = null;
		String actualResultId = RedWineParserUtil.getIdFromCard(cardUrl);
		String expectedId = null;
		assertEquals(expectedId, actualResultId);
	}

}
