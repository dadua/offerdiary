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
import com.itech.flower.model.CashTransaction;
import com.itech.flower.model.Customer;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.Supplier;
import com.itech.web.constants.EachEntityConstants;

public class TransactionAction extends CommonAction {



	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addFlowerTransaction.do")
	public Response addFlowerTransaction (HttpServletRequest req, HttpServletResponse resp) {
		String flowerTransactionJson = req.getParameter(WebConstatnts.FLOWER_TRANSACTION_PARAM_KEY);
		Gson gson = new Gson();
		Type flowerTxType = new TypeToken<FlowerTransaction>() { }.getType();
		FlowerTransaction flowerTransaction = gson.fromJson(flowerTransactionJson, flowerTxType);
		getTransactionManager().addOrUpdateFlowerTransaction(flowerTransaction);
		Result<String> result = new Result<String>("Successfully updated the transaction");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowerTransactionDetail.do")
	public Response getFlowerTransactionDetail (HttpServletRequest req, HttpServletResponse resp) {
		String flowerTransactionId = req.getParameter(WebConstatnts.FLOWER_TRANSACTION_ID_PARAM_KEY);
		FlowerTransaction flowerTransaction = getTransactionManager().getFlowerTransactionById(Long.parseLong(flowerTransactionId));
		Result<FlowerTransaction> result = new Result<FlowerTransaction>(flowerTransaction);
		Type resultStringType = new TypeToken<Result<FlowerTransaction>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="flowerTransactions.do")
	public Response getFlowerTransactions (HttpServletRequest req, HttpServletResponse resp) {
		String searchString = req.getParameter(WebConstatnts.SEARCH_STRING_PARAM_KEY);
		List<FlowerTransaction> flowerTransactions = getTransactionManager().getFlowerTransactionsFor(searchString);
		Gson gson = new Gson ();
		Type flowerTransactionType = new TypeToken<List<FlowerTransaction>>() {
		}.getType();
		String customersJSON = gson.toJson(flowerTransactions, flowerTransactionType);
		req.setAttribute(EachEntityConstants.ENTITIES_JSON_KEY, customersJSON);
		return new Forward(UrlConstants.CUSTOMERS_JSP_RELATIVE_URL);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowerTransactionsForCustomer.do")
	public Response getFlowerTransactionsForCustomer (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(WebConstatnts.CUSTOMER_ID_PARAM);
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		List<FlowerTransaction> flowerTransactions = getTransactionManager().getFlowerTransactionsFor(customer);
		Result<List<FlowerTransaction>> result = new Result<List<FlowerTransaction>>(flowerTransactions);
		Type resultStringType = new TypeToken<Result<List<FlowerTransaction>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowerTransactionsForSupplier.do")
	public Response getFlowerTransactionsForSupplier (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(WebConstatnts.SUPPLIER_ID_PARAM);
		Supplier supplier = getSupplierManager().getById(Long.parseLong(customerId));
		List<FlowerTransaction> flowerTransactions = getTransactionManager().getFlowerTransactionsFor(supplier);
		Result<List<FlowerTransaction>> result = new Result<List<FlowerTransaction>>(flowerTransactions);
		Type resultStringType = new TypeToken<Result<List<FlowerTransaction>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addCashTransaction.do")
	public Response addCashTransaction (HttpServletRequest req, HttpServletResponse resp) {
		String cashTransactionJson = req.getParameter(WebConstatnts.CASH_TRANSACTION_PARAM_KEY);
		Gson gson = new Gson();
		Type flowerTxType = new TypeToken<CashTransaction>() { }.getType();
		CashTransaction cashTransaction = gson.fromJson(cashTransactionJson, flowerTxType);
		getTransactionManager().addOrUpdateCashTransaction(cashTransaction);
		Result<String> result = new Result<String>("Successfully updated the transaction");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCashTransactionDetail.do")
	public Response getCashTransactionDetail (HttpServletRequest req, HttpServletResponse resp) {
		String cashTransactionId = req.getParameter(WebConstatnts.CASH_TRANSACTION_ID_PARAM_KEY);
		CashTransaction cashTransaction = getTransactionManager().getCashTransactionById(Long.parseLong(cashTransactionId));
		Result<CashTransaction> result = new Result<CashTransaction>(cashTransaction);
		Type resultStringType = new TypeToken<Result<CashTransaction>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="cashTransactions.do")
	public Response getCashTransactions (HttpServletRequest req, HttpServletResponse resp) {
		String searchString = req.getParameter(WebConstatnts.SEARCH_STRING_PARAM_KEY);
		List<CashTransaction> cashTransactions = getTransactionManager().getCashTransactionsFor(searchString);
		Gson gson = new Gson ();
		Type cashTransactionType = new TypeToken<List<CashTransaction>>() {
		}.getType();
		String customersJSON = gson.toJson(cashTransactions, cashTransactionType);
		req.setAttribute(EachEntityConstants.ENTITIES_JSON_KEY, customersJSON);
		return new Forward(UrlConstants.CUSTOMERS_JSP_RELATIVE_URL);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCashTransactionsForCustomer.do")
	public Response getCashTransactionsForCustomer (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(WebConstatnts.CUSTOMER_ID_PARAM);
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		List<CashTransaction> cashTransactions = getTransactionManager().getCashTransactionsFor(customer);
		Result<List<CashTransaction>> result = new Result<List<CashTransaction>>(cashTransactions);
		Type resultStringType = new TypeToken<Result<List<CashTransaction>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCashTransactionsForSupplier.do")
	public Response getCashTransactionsForSupplier (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(WebConstatnts.SUPPLIER_ID_PARAM);
		Supplier supplier = getSupplierManager().getById(Long.parseLong(customerId));
		List<CashTransaction> cashTransactions = getTransactionManager().getCashTransactionsFor(supplier);
		Result<List<CashTransaction>> result = new Result<List<CashTransaction>>(cashTransactions);
		Type resultStringType = new TypeToken<Result<List<CashTransaction>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}



}
