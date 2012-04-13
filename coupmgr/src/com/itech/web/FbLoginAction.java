package com.itech.web;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.ActionResponse;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.coupon.manager.UserManager;
import com.itech.coupon.manager.UserManagerImpl;
import com.itech.coupon.model.User;
import com.itech.fb.client.FbAdapter;
import com.itech.fb.model.FbCreds;
import com.itech.fb.services.FbAdapterFactory;
import com.itech.fb.services.FbService;

public class FbLoginAction extends CommonAction{

	/**
	 * @param req
	 * @param resp
	 * @return Redirect Response
	 * Send Redirect to FBQURL/oauth/authorize with:
	 *  1. APPID
	 *  2. PERMISSIONS_REQD
	 *  3. CALLBACKURL
	 */
	public Response doFbUserLoginToApp (HttpServletRequest req, HttpServletResponse resp) {

		String serverPath = ActionResponse.getServerPath(req, resp);
		String callbackUrl = serverPath + FbConstants.REDIRECT_URL;
		FbAdapter fbAdapter = FbAdapterFactory.getFbAdapter(callbackUrl, null);
		req.getSession().setAttribute(FbConstants.FB_ADAPTER_KEY, fbAdapter);

		return ActionResponse.redirect(fbAdapter.getAuthUrlString());
	}


	public Response setFbAdapter (HttpServletRequest req, HttpServletResponse resp) {
		String sessionJson = req.getParameter("fbData");
		Gson gson = new Gson();
		FbCreds fbCreds = gson.fromJson(sessionJson, FbCreds.class);
		UserManager userManager = new UserManagerImpl();
		FbService fbService = new FbService(fbCreds);
		User user = userManager.saveFbUser(fbService);
		updateLoggedInUser(req, user);
		Result<User> result = new Result<User>(true, user);
		Type userResultType = new TypeToken<Result<User>>() {
		}.getType();
		return new CommonBeanResponse(result, userResultType);
	}


	public Response doHandleFbUserCode (HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException {

		FbAdapter fbAdapter = (FbAdapter)request.getSession().getAttribute(FbConstants.FB_ADAPTER_KEY);
		if (fbAdapter == null) {
			String serverPath = ActionResponse.getServerPath(request, resp);
			String callbackUrl = serverPath + FbConstants.REDIRECT_URL;
			fbAdapter = FbAdapterFactory.getFbAdapter(callbackUrl, null);
			request.getSession().setAttribute(FbConstants.FB_ADAPTER_KEY, fbAdapter);
		}
		String userCode = request.getParameter("code");
		try {
			fbAdapter.authenticate(userCode);
		} catch (Exception e) {
			return ActionResponse.redirect("closePopup.jsp?success=0");
		}
		String accessToken= fbAdapter.getAccessToken();
		request.getSession().setAttribute(FbConstants.ACCESS_TOKEN, accessToken);
		String callbackUrl = (String) request.getSession().getAttribute(WebConstatnts.EXTERNAL_CALLBACK_URL_KEY);
		request.getSession().removeAttribute(WebConstatnts.EXTERNAL_CALLBACK_URL_KEY);
		if (callbackUrl != null && callbackUrl.length() > 0) {
			return ActionResponse.redirect(callbackUrl + "?accessPass=" + URLEncoder.encode(accessToken, "UTF-8"));
		} else {
			return ActionResponse.redirect("closePopup.jsp?success=1");
		}
	}

	public Response testExportToFacebook(HttpServletRequest request, HttpServletResponse response) {
		return ActionResponse.forward("testAjax.jsp");

	}

	public Response testFbAction(HttpServletRequest request, HttpServletResponse response) {
		return ActionResponse.forward("testResult.jsp");

	}

}
