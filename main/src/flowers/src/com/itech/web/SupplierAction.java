package com.itech.web;

import java.lang.reflect.Type;
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
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.flower.model.Supplier;
import com.itech.web.constants.CommonEntityUIOperations;
import com.itech.web.constants.EachEntityConstants;


@Controller
public class SupplierAction extends CommonAction{



	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addSupplier.do")
	public Response addSupplier (HttpServletRequest req, HttpServletResponse resp) {
		String supplierJson = req.getParameter(EachEntityConstants.ENTITY_JSON_KEY);
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
		String supplierId = req.getParameter(WebConstatnts.SUPPLIER_ID_PARAM);
		Supplier supplier = getSupplierManager().getById(Long.parseLong(supplierId));
		getSupplierManager().delete(supplier);
		Result<String> result = new Result<String>("Successfully removed the supplier " + supplier.getName());
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteSuppliers.do")
	public Response deleteSuppliers (HttpServletRequest req, HttpServletResponse resp) {
		List<Long> idsToDelete = getIdListFromRequest(req, WebConstatnts.SUPPLIER_IDS_PARAM);
		getSupplierManager().delete(idsToDelete);
		Result<String> result = new Result<String>();
		result.setMsg("Removed " + idsToDelete.size() + " suppliers " );
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getSuppliers.do")
	public Response getSuppliers (HttpServletRequest req, HttpServletResponse resp) {
		List<Supplier> suppliers = getSuppliers(req);
		Result<List<Supplier>> result = new Result<List<Supplier>>(suppliers);
		Type resultStringType = new TypeToken<Result<List<Supplier>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	private List<Supplier> getSuppliers(HttpServletRequest req) {
		String searchString = req.getParameter(WebConstatnts.SUPPLIER_SEARCH_STRING_PARAM);
		List<Supplier> suppliers = getSupplierManager().searchByName(searchString);
		return suppliers;
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getSupplierDetails.do")
	public Response getSupplierDetails (HttpServletRequest req, HttpServletResponse resp) {
		String supplierId = req.getParameter(EachEntityConstants.ENTITY_IDENTIFIER_PARAM_KEY);
		Supplier supplier = getSupplierManager().getById(Long.parseLong(supplierId));
		Result<Supplier> result = new Result<Supplier>(supplier);
		Type resultStringType = new TypeToken<Result<Supplier>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}



	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="suppliers.do")
	public Response viewAllSuppliers(HttpServletRequest req, HttpServletResponse resp) {
		List<Supplier> suppliers = getSuppliers(req);
		Gson gson = new Gson ();
		Type suppliersType = new TypeToken<List<Supplier>>() {
		}.getType();
		String suppliersJSON = gson.toJson(suppliers, suppliersType);
		req.setAttribute(EachEntityConstants.ENTITIES_JSON_KEY, suppliersJSON);
		return new Forward(UrlConstants.SUPPLIERS_JSP_RELATIVE_URL);
	}


	@ActionMapping(value="supplier.do")
	public Response viewSupplier(HttpServletRequest req, HttpServletResponse resp) {
		String supplierIdStr = req.getParameter(EachEntityConstants.ENTITY_IDENTIFIER_PARAM_KEY);

		long supplierId = Long.parseLong(supplierIdStr);
		Supplier supplier = getSupplierManager().getById(supplierId);

		Gson gson = new Gson();
		String supplierJson = gson.toJson(supplier, Supplier.class);

		req.setAttribute(EachEntityConstants.ENTITY_JSON_KEY, supplierJson);


		req.setAttribute(EachEntityConstants.ENTITY_REQUESTED_OPERATION_ATTR_KEY, CommonEntityUIOperations.VIEW.getOperationVal());
		return new Forward(UrlConstants.SUPPLIERS_EACH_SUPPLIER_JSP);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="viewAddNewSupplier.do")
	public Response viewAddNewSupplier(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute(EachEntityConstants.ENTITY_REQUESTED_OPERATION_ATTR_KEY, CommonEntityUIOperations.ADDNEW.getOperationVal());
		return new Forward(UrlConstants.SUPPLIERS_EACH_SUPPLIER_JSP);
	}

}
