package com.itech.offer.web;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.offer.model.Vendor;
import com.itech.offer.vo.VendorVO;

@Controller
public class VendorAction extends CommonAction{

	private static final String VENDOR_JSON_KEY = "vendorJson";
	//TODO: Send the no. of records wanted from the ui
	// also consider this as the default count..
	private static final int MAX_RESULT_COUNT = 9;
	private static final String VENDOR_NAME_SEARCH_KEY = "searchKey";

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="addVendor.do")
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
	@ActionMapping(value="searchVendor.do")
	public Response searchVendors (HttpServletRequest req, HttpServletResponse resp) {
		String vendorSearchString = req.getParameter(VENDOR_NAME_SEARCH_KEY);
		List<Vendor> vendors = getVendorManager().getVendorsFor(vendorSearchString, MAX_RESULT_COUNT);
		List<VendorVO> vendorVOs = getVendorVOsfrom(vendors);
		Result<List<VendorVO>> result = new Result<List<VendorVO>>(vendorVOs);
		Type type = new TypeToken<Result<List<Vendor>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	private List<VendorVO> getVendorVOsfrom(List<Vendor> vendors) {
		List<VendorVO> vendorVOs = new ArrayList<VendorVO>();
		for (Vendor vendor : vendors) {
			vendorVOs.add(VendorVO.getVendorVOFor(vendor));
		}
		return vendorVOs;
		return null;
	}
}
