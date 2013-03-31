package com.itech.scotchworld.parser;

import com.itech.common.test.CommonTestBase;

public class TestScotchWorldUtil extends CommonTestBase{

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub

	}

	public void testFormatSiteURL() {
		String inputURL1 = "location: http://www.dealsfive.com/index.aspx";
		String expectedURL1 = "www.dealsfive.com";

		String inputURL2 = "location: http://www.s2d6.com/x/?x=c&z=s&v=3407682";
		String expectedURL2 = "www.s2d6.com";

		String inputURL3 = "http://www.s2d6.com/x/?x=c&z=s&v=3407682";
		String expectedURL3 = "www.s2d6.com";

		String inputURL4 = "location: http://www.jdoqocy.com/click-4320459-10933625?url=http%3A%2F%2Fwww.emirates.com%2";
		String expectedURL4 = "www.jdoqocy.com";

		String inputURL5 = "location: http://www.dealsfive.com/index.aspx";
		String expectedURL5 = "www.dealsfive.com";

		String actualResult1 = ScotchWorldUtil.formatSiteURL(inputURL1);
		assertEquals(expectedURL1, actualResult1);

		String actualResult2 = ScotchWorldUtil.formatSiteURL(inputURL2);
		assertEquals(expectedURL2, actualResult2);

		String actualResult3 = ScotchWorldUtil.formatSiteURL(inputURL3);
		assertEquals(expectedURL3, actualResult3);

		String actualResult4 = ScotchWorldUtil.formatSiteURL(inputURL4);
		assertEquals(expectedURL4, actualResult4);

		String actualResult5 = ScotchWorldUtil.formatSiteURL(inputURL5);
		assertEquals(expectedURL5, actualResult5);
	}


}
