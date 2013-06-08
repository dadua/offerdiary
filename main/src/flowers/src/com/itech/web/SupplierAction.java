package com.itech.web;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.flower.model.Supplier;

public class SupplierAction extends CommonAction{
	private static final String SUPPLIER_PARAM = "supplier";
	private static final String SUPPLIER_ID_PARAM = "id";
	private static final String SUPPLIER_SEARCH_STRING_PARAM = "q";


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addSupplier.do")
	public Response addSupplier (HttpServletRequest req, HttpServletResponse resp) {
		String supplierJson = req.getParameter(SUPPLIER_PARAM);
		Gson gson = new Gson();
		Type supplierType = new TypeToken<Supplier>() { }.getType();
		Supplier supplier = gson.fromJson(supplierJson, supplierType);
		getSupplierManager().addOrUpdate(supplier);
		Result<Supplier> result = new Result<Supplier>(supplier);
		Type resultSuppliersType = new TypeToken<Result<Supplier>>() {
		}.getType();

		return new CommonBeanResponse(result, resultSuppliersType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteSupplier.do")
	public Response deleteSupplier (HttpServletRequest req, HttpServletResponse resp) {
		String supplierId = req.getParameter(SUPPLIER_ID_PARAM);
		Supplier supplier = getSupplierManager().getById(Long.parseLong(supplierId));
		getSupplierManager().delete(supplier);
		Result<String> result = new Result<String>("Successfully removed the supplier " + supplier.getName());
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getSuppliers.do")
	public Response getSuppliers (HttpServletRequest req, HttpServletResponse resp) {
		String searchString = req.getParameter(SUPPLIER_SEARCH_STRING_PARAM);
		List<Supplier> suppliers = getSupplierManager().searchByName(searchString);
		Result<List<Supplier>> result = new Result<List<Supplier>>(suppliers);
		Type resultStringType = new TypeToken<Result<List<Supplier>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getSupplierDetails.do")
	public Response getSupplierDetails (HttpServletRequest req, HttpServletResponse resp) {
		String supplierId = req.getParameter(SUPPLIER_ID_PARAM);
		Supplier supplier = getSupplierManager().getById(Long.parseLong(supplierId));
		Result<Supplier> result = new Result<Supplier>(supplier);
		Type resultStringType = new TypeToken<Result<Supplier>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}



}
