package com.itech.web;

import com.itech.config.ProjectConfigs;

public class FbConstants {

	public static final String APP_ID_LIVE = "187105271327076";
	public static final String APP_SECRET_LIVE = "79023ae97184cf3543bfdd2c1272beb0";

	public static final String APP_ID_DEV = "253396431422694";
	public static final String APP_SECRET_DEV = "9a7a6e1257c1032e42ad501aca459992";

	/**
	 * This gives access to all public data, and the data of users of the Dev app
	 * Fetching procedure: https://developers.facebook.com/docs/howtos/login/login-as-app/
	 */
	public static final String APP_DEV_SECRET_ACCESS_TOKEN = "253396431422694|aQ-xkGC2r8UMArmRv7PptOI0PIA";



	public static final String FB_GRAPH_URL = "graph.facebook.com";
	public static final String REDIRECT_URL = "/facebookCallbackUrl.do";
	public static final String APP_PERMISSIONS_REQUIRED = "read_stream,user_photos,publish_stream";//TODO: Have to make perms configurable
	public static final String ACCESS_TOKEN = "fbAccessToken";
	public static final String FB_ADAPTER_KEY = "fbAdapter";

	public static String getFbAppId() {
		if("DEV".equalsIgnoreCase(ProjectConfigs.serverMode.get())) {
			return APP_ID_DEV;
		} else {
			return APP_ID_LIVE;
		}
	}

	public static String getFbAppSecret() {
		if("DEV".equalsIgnoreCase(ProjectConfigs.serverMode.get())) {
			return APP_SECRET_DEV;
		} else {
			return APP_SECRET_LIVE;
		}
	}


}
