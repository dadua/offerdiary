package com.itech.web;

import com.itech.config.ProjectConfigs;



public class WebConstatnts {
	public static final String CONSUMER_KEY = "orkud2facebook.appspot.com";
	public static final String CONSUMER_SECRET = "NHH04EkpzhE+cSM+mJ0hgud+";
	public static final String BASE_URL = "http://localhost:8080/orkut";
	public static final String CALLBACK_URL = "/orkutCallback.do";


	public static final String ORKUT_ADAPTER_KEY = "orkutAdapter";
	public static final String ORKUT_ACCESSPASS_KEY = "accessPass";
	public static final String EXTERNAL_CALLBACK_URL_KEY = "externalCallback";
	public static final String FB_ALBUMS_EXPORTED_KEY = "fbAlbumsExported";
	public static final String EXPORT_JOB_ID_KEY = "exportJobIdKey";
	public static final String SOCIAL_PORTAL_URL = "http://www.socialbaba.com";

	public static final String STATS_DATA_DUMP_FILE_DEV = "/home/ec2-user/orkut2fb-log";

	public static final String STATS_DATA_DUMP_FILE_TEST = "c:\\orkut\\orkut2fb-log";


	public static final String FB_APP_ID_ATTRIBUTE_KEY = "fbAppId";



	public static final String getOfferDetailPageBaseURL() {
		return ProjectConfigs.defaultServerUrl.get() + "/getOfferDetail.do?id=";
	}


	public static final String getVarifyEmailPageBaseURL() {
		return ProjectConfigs.defaultServerUrl.get() + "/varifyEmail.do?code=";
	}


}
