package com.itech.common;

public class UrlUtils {


	private static final String HTTP_PREFIX = "http://";

	public static String getHttpPrefixedUrl (String url) {
		if (CommonUtilities.isNullOrEmpty(url)) {
			return url;
		} else {
			if (url.toLowerCase().trim().startsWith(HTTP_PREFIX)) {
				return url;
			} else {
				return HTTP_PREFIX + url.trim();
			}
		}
	}

}
