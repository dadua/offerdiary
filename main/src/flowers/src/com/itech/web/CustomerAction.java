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
import com.itech.web.constants.CommonEntityUIOperations;
import com.itech.web.constants.EachEntityConstants;


@Controller
public class CustomerAction extends CommonAction {

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addCustomer.do")
	public Response addCustomer (HttpServletRequest req, HttpServletResponse resp) {
		String customerJson = req.getParameter(EachEntityConstants.ENTITY_JSON_KEY);
		Gson gson = new Gson();
		Type customerType = new TypeToken<Customer>() { }.getType();
		Customer customer = gson.fromJson(customerJson, customerType);
		getCustomerManager().addOrUpdate(customer);
		Result<Customer> result = new Result<Customer>(customer);
		Type resultCustomersType = new TypeToken<Result<Customer>>() {
		}.getType();

		return new CommonBeanResponse(result, resultCustomersType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteCustomer.do")
	public Response deleteCustomer(HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(WebConstatnts.CUSTOMER_ID_PARAM);
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		getCustomerManager().delete(customer);
		Result<String> result = new Result<String>("Successfully removed the customer " + customer.getName());
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteCustomers.do")
	public Response deleteCustomers (HttpServletRequest req, HttpServletResponse resp) {
		List<Long> customerIdsToDelete = getIdListFromRequest(req, WebConstatnts.CUSTOMER_IDS_PARAM);
		getCustomerManager().delete(customerIdsToDelete);
		Result<String> result = new Result<String>( );
		result.setMsg("Successfully removed " + customerIdsToDelete.size() + " customers");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCustomers.do")
	public Response getCustomer (HttpServletRequest req, HttpServletResponse resp) {
		List<Customer> customers = getCustomers(req);
		Result<List<Customer>> result = new Result<List<Customer>>(customers);
		Type resultStringType = new TypeToken<Result<List<Customer>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	private List<Customer> getCustomers(HttpServletRequest req) {
		String searchString = req.getParameter(WebConstatnts.CUSTOMER_SEARCH_STRING_PARAM);
		List<Customer> customers = getCustomerManager().searchByName(searchString);
		return customers;
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCustomerDetails.do")
	public Response getCustomerDetails (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(EachEntityConstants.ENTITY_IDENTIFIER_PARAM_KEY);
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		Result<Customer> result = new Result<Customer>(customer);
		Type resultStringType = new TypeToken<Result<Customer>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="customers.do")
	public Response viewAllCustomers(HttpServletRequest req, HttpServletResponse resp) {
		List<Customer> customers = getCustomers(req);
		Gson gson = new Gson ();
		Type customersType = new TypeToken<List<Customer>>() {
		}.getType();
		String customersJSON = gson.toJson(customers, customersType);
		req.setAttribute(EachEntityConstants.ENTITIES_JSON_KEY, customersJSON);
		return new Forward(UrlConstants.CUSTOMERS_JSP_RELATIVE_URL);
	}

	@ActionMapping(value="customer.do")
	public Response viewCustomer(HttpServletRequest req, HttpServletResponse resp) {
		String customerIdStr = req.getParameter(EachEntityConstants.ENTITY_IDENTIFIER_PARAM_KEY);

		long customerId = Long.parseLong(customerIdStr);
		Customer customer = getCustomerManager().getById(customerId);

		Gson gson = new Gson();
		String customerJson = gson.toJson(customer, Customer.class);

		req.setAttribute(EachEntityConstants.ENTITY_JSON_KEY, customerJson);


		req.setAttribute(EachEntityConstants.ENTITY_REQUESTED_OPERATION_ATTR_KEY, CommonEntityUIOperations.VIEW.getOperationVal());
		return new Forward(UrlConstants.CUSTOMERS_EACH_CUSTOMER_JSP);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="viewAddNewCustomer.do")
	public Response viewAddNewCustomer(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute(EachEntityConstants.ENTITY_REQUESTED_OPERATION_ATTR_KEY, CommonEntityUIOperations.ADDNEW.getOperationVal());
		return new Forward(UrlConstants.CUSTOMERS_EACH_CUSTOMER_JSP);
	}

}
