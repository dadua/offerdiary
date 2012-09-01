package com.itech.offer.web;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.offer.model.Vendor;

public class VendorAction extends CommonAction{

	private static final String VENDOR_JSON_KEY = "vendorJson";
	private static final int MAX_RESULT_COUNT = 10;
	private static final String VENDOR_NAME_SEARCH_KEY = "searchKey";

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	public Response addVendor (HttpServletRequest req, HttpServletResponse resp) {
		String vendorJson = req.getParameter(VENDOR_JSON_KEY);
		Gson gson = new Gson();
		Type vendorJsonType = new TypeToken<Vendor>() { }.getType();
		Vendor vendor = gson.fromJson(vendorJson, vendorJsonType);
		Vendor processedVendor = getVendorManager().saveOrUpdateVendor(vendor);
		Result<Vendor> result = new Result<Vendor>(processedVendor);
		Type resultOffersType = new TypeToken<Result<Vendor>>() {
		}.getType();
		return new CommonBeanResponse(result, resultOffersType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	public Response searchVendors (HttpServletRequest req, HttpServletResponse resp) {
		String vendorSearchString = req.getParameter(VENDOR_NAME_SEARCH_KEY);
		List<Vendor> vendors = getVendorManager().getVendorsFor(vendorSearchString, MAX_RESULT_COUNT);
		Result<List<Vendor>> result = new Result<List<Vendor>>(vendors);
		Type type = new TypeToken<Result<List<Vendor>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}
}
