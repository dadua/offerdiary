package com.itech.scotchworld.parser;

import org.apache.log4j.Logger;

public class ScotchWorldUtil {
	private static final Logger logger = Logger.getLogger(ScotchWorldUtil.class);

	public static String formatSiteURL(String storeURLOrig) {
		String storeURL = storeURLOrig;
		if (storeURL  == null || storeURL.trim().length() == 0) {
			return null;
		}

		if (storeURL.indexOf("location:") != -1) {
			storeURL = storeURL.substring("location:".length()).trim();
		}

		if (storeURL.indexOf("/", "http://".length()) != -1) {
			storeURL = storeURL.substring(0, storeURL.indexOf("/", "http://".length()));
		}

		if (storeURL.indexOf("?", "http://".length()) != -1) {
			storeURL = storeURL.substring(0, storeURL.indexOf("?", "http://".length()));
		}
		logger.debug("orig -" + storeURLOrig + " ,  formated--" + storeURL);
		return storeURL;
	}

}
