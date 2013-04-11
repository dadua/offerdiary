package com.itech.common;

public class UrlUtils {


	private static final String HTTP_PREFIX = "http://";
	private static final String HTTPS_PREFIX = "https://";

	public static String getHttpPrefixedUrl (String url) {
		if (CommonUtilities.isNullOrEmpty(url)) {
			return url;
		}

		if (url.toLowerCase().trim().startsWith(HTTP_PREFIX) || url.toLowerCase().trim().startsWith(HTTPS_PREFIX)) {
			return url;
		}

		return HTTP_PREFIX + url.trim();
	}

}
