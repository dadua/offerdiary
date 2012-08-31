package com.itech.fb.services;

import com.itech.fb.client.FbAdapter;
import com.itech.web.FbConstants;

public class FbAdapterFactory {
	public static FbAdapter getFbAdapter(String callbackUrl, String accessToken) {
		FbAdapter fbAdapter = new FbAdapter(FbConstants.APP_ID, FbConstants.APP_SECRET, callbackUrl, accessToken);
		return fbAdapter;
	}

	public static FbAdapter getFbAdapter(String accessToken) {
		FbAdapter fbAdapter = new FbAdapter(FbConstants.APP_ID, FbConstants.APP_SECRET,
				FbConstants.REDIRECT_URL, accessToken);
		return fbAdapter;
	}
}
