package com.itech.web;

import com.itech.config.ProjectConfigs;

public class FbConstants {

	public static final String APP_ID_LIVE = "187105271327076";
	public static final String APP_SECRET_LIVE = "e67421b0f65d03bc9b72a17496864fde";

	public static final String APP_ID_DEV = "253396431422694";
	public static final String APP_SECRET_DEV = "e67421b0f65d03bc9b72a17496864fde";

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
