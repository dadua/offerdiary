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
import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.flower.model.Supplier;
import com.itech.web.constants.CommonEntityUIOperations;
import com.itech.web.constants.EachEntityConstants;


@Controller
public class FlowerAction extends CommonAction {




	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addFlower.do")
	public Response addFlower (HttpServletRequest req, HttpServletResponse resp) {
		String flowerJson = req.getParameter(EachEntityConstants.ENTITY_JSON_KEY);
		Gson gson = new Gson();
		Type flowerType = new TypeToken<Flower>() { }.getType();
		Flower flower = gson.fromJson(flowerJson, flowerType);
		getFlowerManager().addOrUpdate(flower);
		Result<Flower> result = new Result<Flower>(flower);
		Type resultFlowersType = new TypeToken<Result<Flower>>() {
		}.getType();

		result.setMsg("Flower " + flower.getName() + " updated.");

		return new CommonBeanResponse(result, resultFlowersType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteFlower.do")
	public Response deleteFlower (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(WebConstatnts.FLOWER_ID_PARAM);
		Flower flower = getFlowerManager().getFlowerById(Long.parseLong(flowerId));
		getFlowerManager().delete(flower);
		Result<String> result = new Result<String>("Successfully removed the flower " + flower.getName());
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteFlowers.do")
	public Response deleteFlowers (HttpServletRequest req, HttpServletResponse resp) {
		List<Long> flowerIdsToDelete = getIdListFromRequest(req, WebConstatnts.FLOWER_IDS_PARAM);
		getFlowerManager().delete(flowerIdsToDelete);

		Result<String> result = new Result<String>("");
		result.setMsg("Removed " + flowerIdsToDelete.size() + " flowers");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowers.do")
	public Response getFlowers (HttpServletRequest req, HttpServletResponse resp) {
		List<Flower> flowers = getFlowers(req);
		Result<List<Flower>> result = new Result<List<Flower>>(flowers);
		Type resultStringType = new TypeToken<Result<List<Flower>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	private List<Flower> getFlowers(HttpServletRequest req) {
		String searchString = req.getParameter(WebConstatnts.FLOWER_SEARCH_STRING_PARAM);
		List<Flower> flowers = getFlowerManager().searchFlowersFor(searchString);
		return flowers;
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowerDetails.do")
	public Response getFlowerDetails (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(WebConstatnts.FLOWER_ID_PARAM);
		Flower flower = getFlowerManager().getFlowerById(Long.parseLong(flowerId));
		Result<Flower> result = new Result<Flower>(flower);
		Type resultStringType = new TypeToken<Result<Flower>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addFlowerToCustomer.do")
	public Response addFlowerToCustomer (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(WebConstatnts.FLOWER_ID_PARAM);
		String customerId = req.getParameter(WebConstatnts.CUSTOMER_ID_PARAM);
		Flower flower = getFlowerManager().getFlowerById(Long.parseLong(flowerId));
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		getFlowerManager().addFlowerToCustomer(flower, customer);
		Result<String> result = new Result<String>("Successfully added the flower " + flower.getName() + " to Customer " + customer.getName());
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addFlowerToSupplier.do")
	public Response addFlowerToSupplier (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(WebConstatnts.FLOWER_ID_PARAM);
		String supplierId = req.getParameter(WebConstatnts.SUPPLIER_ID_PARAM);
		Flower flower = getFlowerManager().getFlowerById(Long.parseLong(flowerId));
		Supplier supplier = getSupplierManager().getById(Long.parseLong(supplierId));
		getFlowerManager().addFlowerToSupplier(flower, supplier);
		Result<String> result = new Result<String>("Successfully added the flower " + flower.getName() + " to Supplier " + supplier.getName());
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowersForSupplier.do")
	public Response getFlowersForSupplier (HttpServletRequest req, HttpServletResponse resp) {
		String supplierId = req.getParameter(WebConstatnts.SUPPLIER_ID_PARAM);
		Supplier supplier = getSupplierManager().getById(Long.parseLong(supplierId));
		List<Flower> flowers = getFlowerManager().getFlowersForSupplier(supplier);
		Result<List<Flower>> result = new Result<List<Flower>>(flowers);
		Type resultStringType = new TypeToken<Result<List<Flower>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowersForCustomer.do")
	public Response getFlowersForCustomer (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(WebConstatnts.CUSTOMER_ID_PARAM);
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		List<Flower> flowers = getFlowerManager().getFlowersForCustomer(customer);
		Result<List<Flower>> result = new Result<List<Flower>>(flowers);
		Type resultStringType = new TypeToken<Result<List<Flower>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getSuppliersForFlower.do")
	public Response getSuppliersForFlower (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(WebConstatnts.FLOWER_ID_PARAM);
		Flower flower = getFlowerManager().getFlowerById(Long.parseLong(flowerId));
		List<Supplier> suppliers = getFlowerManager().getSuppliersForFlower(flower);
		Result<List<Supplier>> result = new Result<List<Supplier>>(suppliers);
		Type resultStringType = new TypeToken<Result<List<Supplier>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCustomersForFlower.do")
	public Response getCustomersForFlower (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(WebConstatnts.FLOWER_ID_PARAM);
		Flower flower = getFlowerManager().getFlowerById(Long.parseLong(flowerId));
		List<Customer> customers = getFlowerManager().getCustomersForFlower(flower);
		Result<List<Customer>> result = new Result<List<Customer>>(customers);
		Type resultStringType = new TypeToken<Result<List<Customer>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="goToAddNewFlower.do")
	public Response goToAddNewFlower(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute(EachEntityConstants.ENTITY_REQUESTED_OPERATION_ATTR_KEY, CommonEntityUIOperations.ADDNEW.getOperationVal());
		return new Forward(UrlConstants.FLOWERS_EACH_FLOWER_JSP);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="flower.do")
	public Response viewFlower(HttpServletRequest req, HttpServletResponse resp) {
		String flowerIdStr = req.getParameter(EachEntityConstants.ENTITY_IDENTIFIER_PARAM_KEY);

		long flowerId = Long.parseLong(flowerIdStr);
		Flower flower = getFlowerManager().getFlowerById(flowerId);

		Gson gson = new Gson();
		String flowerJson = gson.toJson(flower, Flower.class);

		req.setAttribute(EachEntityConstants.ENTITY_JSON_KEY, flowerJson);


		req.setAttribute(EachEntityConstants.ENTITY_REQUESTED_OPERATION_ATTR_KEY, CommonEntityUIOperations.VIEW.getOperationVal());
		return new Forward(UrlConstants.FLOWERS_EACH_FLOWER_JSP);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="flowers.do")
	public Response viewAllFlowers(HttpServletRequest req, HttpServletResponse resp) {
		List<Flower> flowers = getFlowers(req);
		Gson gson = new Gson ();
		Type flowersType = new TypeToken<List<Flower>>() {
		}.getType();
		String flowersJSON = gson.toJson(flowers, flowersType);
		req.setAttribute(EachEntityConstants.ENTITIES_JSON_KEY, flowersJSON);
		return new Forward(UrlConstants.FLOWERS_JSP_RELATIVE_URL);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="home.do")
	public Response goToHome(HttpServletRequest req, HttpServletResponse resp) {
		return viewAllFlowers(req, resp);
	}



}
