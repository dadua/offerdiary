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
import com.itech.customer.model.Customer;

public class CustomerAction extends CommonAction {
	private static final String CUSTOMER_PARAM = "customer";
	private static final String CUSTOMER_ID_PARAM = "id";
	private static final String CUSTOMER_SEARCH_STRING_PARAM = "q";


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addCustomer.do")
	public Response addCustomer (HttpServletRequest req, HttpServletResponse resp) {
		String customerJson = req.getParameter(CUSTOMER_PARAM);
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
	public Response deleteCustomers (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(CUSTOMER_ID_PARAM);
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		getCustomerManager().delete(customer);
		Result<String> result = new Result<String>("Successfully removed the customer " + customer.getName());
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCustomers.do")
	public Response getCustomer (HttpServletRequest req, HttpServletResponse resp) {
		String searchString = req.getParameter(CUSTOMER_SEARCH_STRING_PARAM);
		List<Customer> customers = getCustomerManager().searchByName(searchString);
		Result<List<Customer>> result = new Result<List<Customer>>(customers);
		Type resultStringType = new TypeToken<Result<List<Customer>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCustomerDetails.do")
	public Response getCustomerDetails (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(CUSTOMER_ID_PARAM);
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		Result<Customer> result = new Result<Customer>(customer);
		Type resultStringType = new TypeToken<Result<Customer>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

}
