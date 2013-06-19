package com.itech.web;

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
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.flower.model.CashTransaction;
import com.itech.flower.model.Customer;
import com.itech.flower.model.FlowerTransaction;
import com.itech.flower.model.Supplier;
import com.itech.flower.vo.CashTransactionVO;
import com.itech.flower.vo.FlowerTransactionVO;
import com.itech.web.constants.CommonEntityUIOperations;
import com.itech.web.constants.EachEntityConstants;

@Controller
public class TransactionAction extends CommonAction {



	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="addFlowerTransaction.do")
	public Response addFlowerTransaction (HttpServletRequest req, HttpServletResponse resp) {
		String flowerTransactionJson = req.getParameter(WebConstatnts.FLOWER_TRANSACTION_PARAM_KEY);
		Gson gson = new Gson();
		Type flowerTxVOType = new TypeToken<FlowerTransactionVO>() { }.getType();
		FlowerTransactionVO flowerTransactionVO = gson.fromJson(flowerTransactionJson, flowerTxVOType);
		FlowerTransaction flowerTransaction = FlowerTransactionVO.fromVO(flowerTransactionVO);
		getTransactionManager().addOrUpdateFlowerTransaction(flowerTransaction);
		Result<FlowerTransactionVO> result = new Result<FlowerTransactionVO>(FlowerTransactionVO.toVO(flowerTransaction));
		Type resultFlowerTxVOType = new TypeToken<Result<FlowerTransactionVO>>() {
		}.getType();
		result.setMsg("Flower Transaction is successfully updated");
		return new CommonBeanResponse(result, resultFlowerTxVOType);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="viewAddNewFlowerTransaction.do")
	public Response viewAddNewFlowerTransaction(HttpServletRequest req, HttpServletResponse resp) {
		String txType = req.getParameter("txType");
		req.setAttribute("txType", txType);
		req.setAttribute(EachEntityConstants.ENTITY_REQUESTED_OPERATION_ATTR_KEY, CommonEntityUIOperations.ADDNEW.getOperationVal());
		return new Forward(UrlConstants.FLOWER_TRANSACTION_EACH_TRANSACTION_JSP);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowerTransactionDetail.do")
	public Response getFlowerTransactionDetail (HttpServletRequest req, HttpServletResponse resp) {
		String flowerTransactionId = req.getParameter(WebConstatnts.FLOWER_TRANSACTION_ID_PARAM_KEY);
		FlowerTransaction flowerTransaction = getTransactionManager().getFlowerTransactionById(Long.parseLong(flowerTransactionId));
		FlowerTransactionVO flowerTransactionVO = FlowerTransactionVO.toVO(flowerTransaction);
		Result<FlowerTransactionVO> result = new Result<FlowerTransactionVO>(flowerTransactionVO );
		Type resultFlowerTxVOType = new TypeToken<Result<FlowerTransactionVO>>() {
		}.getType();
		return new CommonBeanResponse(result, resultFlowerTxVOType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowerTransactions.do")
	public Response getAllFlowerTransactions(HttpServletRequest req, HttpServletResponse resp) {
		List<FlowerTransaction> flowerTransactions = getFlowerTransactions(req);
		Result<List<FlowerTransactionVO>> result = new Result<List<FlowerTransactionVO>>(getFLowerTransactionVos(flowerTransactions));
		Type resultFlowerTxnsType = new TypeToken<Result<List<FlowerTransactionVO>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultFlowerTxnsType);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="flowerTransactions.do")
	public Response getFlowerTransactions (HttpServletRequest req, HttpServletResponse resp) {
		List<FlowerTransaction> flowerTransactions = getFlowerTransactions(req);
		Gson gson = new Gson ();
		Type flowerTransactionType = new TypeToken<List<FlowerTransactionVO>>() {
		}.getType();
		String flowerTransactionsJSON = gson.toJson(getFLowerTransactionVos(flowerTransactions), flowerTransactionType);
		req.setAttribute(EachEntityConstants.ENTITIES_JSON_KEY, flowerTransactionsJSON);
		return new Forward(UrlConstants.FLOWER_TRANSACTIONS_JSP_RELATIVE_URL);
	}

	private List<FlowerTransaction> getFlowerTransactions(HttpServletRequest req) {
		String searchString = req.getParameter(WebConstatnts.SEARCH_STRING_PARAM_KEY);
		List<FlowerTransaction> flowerTransactions = getTransactionManager().getFlowerTransactionsFor(searchString);
		return flowerTransactions;
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="flowerTransaction.do")
	public Response getFlowerTransaction (HttpServletRequest req, HttpServletResponse resp) {

		String flowerTxIdStr = req.getParameter(EachEntityConstants.ENTITY_IDENTIFIER_PARAM_KEY);

		FlowerTransaction flowerTransaction = getTransactionManager().getFlowerTransactionById(Long.parseLong(flowerTxIdStr));
		FlowerTransactionVO flowerTransactionVO = FlowerTransactionVO.toVO(flowerTransaction);

		req.setAttribute(EachEntityConstants.ENTITY_REQUESTED_OPERATION_ATTR_KEY, CommonEntityUIOperations.VIEW.getOperationVal());

		Gson gson = new Gson ();
		Type flowerTransactionVoType = new TypeToken<FlowerTransactionVO>() {
		}.getType();
		String flowerTransactionVoJSON = gson.toJson(flowerTransactionVO, flowerTransactionVoType);
		req.setAttribute(EachEntityConstants.ENTITY_JSON_KEY, flowerTransactionVoJSON);

		return new Forward(UrlConstants.FLOWER_TRANSACTION_EACH_TRANSACTION_JSP);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="transactions.do")
	public Response getTransactions(HttpServletRequest req, HttpServletResponse resp) {
		return getFlowerTransactions(req, resp);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowerTransactionsForCustomer.do")
	public Response getFlowerTransactionsForCustomer (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(WebConstatnts.CUSTOMER_ID_PARAM);
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		List<FlowerTransaction> flowerTransactions = getTransactionManager().getFlowerTransactionsFor(customer);
		Result<List<FlowerTransactionVO>> result = new Result<List<FlowerTransactionVO>>(getFLowerTransactionVos(flowerTransactions));
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
		Result<List<FlowerTransactionVO>> result = new Result<List<FlowerTransactionVO>>(getFLowerTransactionVos(flowerTransactions));
		Type resultStringType = new TypeToken<Result<List<FlowerTransaction>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="addCashTransaction.do")
	public Response addCashTransaction (HttpServletRequest req, HttpServletResponse resp) {
		String cashTransactionJson = req.getParameter(WebConstatnts.CASH_TRANSACTION_PARAM_KEY);
		Gson gson = new Gson();
		Type cashTxVOType = new TypeToken<CashTransactionVO>() { }.getType();
		CashTransactionVO cashTransactionVO = gson.fromJson(cashTransactionJson, cashTxVOType);
		CashTransaction cashTransaction = CashTransactionVO.fromVO(cashTransactionVO);
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
		Result<CashTransactionVO> result = new Result<CashTransactionVO>(CashTransactionVO.toVO(cashTransaction));
		Type resultStringType = new TypeToken<Result<CashTransaction>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="cashTransactions.do")
	public Response getCashTransactions (HttpServletRequest req, HttpServletResponse resp) {
		String searchString = req.getParameter(WebConstatnts.SEARCH_STRING_PARAM_KEY);
		List<CashTransaction> cashTransactions = getTransactionManager().getCashTransactionsFor(searchString);
		Gson gson = new Gson ();
		Type cashTransactionType = new TypeToken<List<CashTransactionVO>>() {
		}.getType();
		String cashTransactionsJSON = gson.toJson(getCashTransactionVos(cashTransactions), cashTransactionType);
		req.setAttribute(EachEntityConstants.ENTITIES_JSON_KEY, cashTransactionsJSON);
		return new Forward(UrlConstants.CASH_TRANSACTIONS_JSP_RELATIVE_URL);
	}



	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCashTransactionsForCustomer.do")
	public Response getCashTransactionsForCustomer (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(WebConstatnts.CUSTOMER_ID_PARAM);
		Customer customer = getCustomerManager().getById(Long.parseLong(customerId));
		List<CashTransaction> cashTransactions = getTransactionManager().getCashTransactionsFor(customer);
		Result<List<CashTransactionVO>> result = new Result<List<CashTransactionVO>>(getCashTransactionVos(cashTransactions));
		Type resultStringType = new TypeToken<Result<List<CashTransactionVO>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCashTransactionsForSupplier.do")
	public Response getCashTransactionsForSupplier (HttpServletRequest req, HttpServletResponse resp) {
		String customerId = req.getParameter(WebConstatnts.SUPPLIER_ID_PARAM);
		Supplier supplier = getSupplierManager().getById(Long.parseLong(customerId));
		List<CashTransaction> cashTransactions = getTransactionManager().getCashTransactionsFor(supplier);
		Result<List<CashTransactionVO>> result = new Result<List<CashTransactionVO>>(getCashTransactionVos(cashTransactions));
		Type resultStringType = new TypeToken<Result<List<CashTransactionVO>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	private List<FlowerTransactionVO> getFLowerTransactionVos(List<FlowerTransaction> flowerTransactions) {
		List<FlowerTransactionVO> flowerTransactionVOs = new ArrayList<FlowerTransactionVO>();
		for (FlowerTransaction flowerTransaction : flowerTransactions) {
			flowerTransactionVOs.add(FlowerTransactionVO.toVO(flowerTransaction));
		}
		return flowerTransactionVOs;
	}

	private List<CashTransactionVO> getCashTransactionVos(List<CashTransaction> cashTransactions) {
		List<CashTransactionVO> cashTransactionVOs = new ArrayList<CashTransactionVO>();
		for (CashTransaction cashTransaction : cashTransactions) {
			cashTransactionVOs.add(CashTransactionVO.toVO(cashTransaction));
		}
		return cashTransactionVOs;
	}
}
