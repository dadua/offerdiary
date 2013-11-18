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
import com.itech.student.model.Student;
import com.itech.web.constants.CommonEntityUIOperations;
import com.itech.web.constants.EachEntityConstants;


@Controller
public class StudentAction extends CommonAction {




	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addFlower.do")
	public Response addFlower (HttpServletRequest req, HttpServletResponse resp) {
		String flowerJson = req.getParameter(EachEntityConstants.ENTITY_JSON_KEY);
		Gson gson = new Gson();
		Type flowerType = new TypeToken<Student>() { }.getType();
		Student flower = gson.fromJson(flowerJson, flowerType);
		getStudentManager().addOrUpdate(flower);
		Result<Student> result = new Result<Student>(flower);
		Type resultFlowersType = new TypeToken<Result<Student>>() {
		}.getType();

		result.setMsg("Flower " + flower.getName() + " updated.");

		return new CommonBeanResponse(result, resultFlowersType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteFlower.do")
	public Response deleteFlower (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(WebConstatnts.FLOWER_ID_PARAM);
		Student flower = getStudentManager().getFlowerById(Long.parseLong(flowerId));
		getStudentManager().delete(flower);
		Result<String> result = new Result<String>("Successfully removed the flower " + flower.getName());
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteFlowers.do")
	public Response deleteFlowers (HttpServletRequest req, HttpServletResponse resp) {
		List<Long> flowerIdsToDelete = getIdListFromRequest(req, WebConstatnts.FLOWER_IDS_PARAM);
		getStudentManager().delete(flowerIdsToDelete);

		Result<String> result = new Result<String>("");
		result.setMsg("Removed " + flowerIdsToDelete.size() + " flowers");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowers.do")
	public Response getFlowers (HttpServletRequest req, HttpServletResponse resp) {
		List<Student> flowers = getFlowers(req);
		Result<List<Student>> result = new Result<List<Student>>(flowers);
		Type resultStringType = new TypeToken<Result<List<Student>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


	private List<Student> getFlowers(HttpServletRequest req) {
		String searchString = req.getParameter(WebConstatnts.FLOWER_SEARCH_STRING_PARAM);
		List<Student> flowers = getStudentManager().searchStudentFor(searchString);
		return flowers;
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowerDetails.do")
	public Response getFlowerDetails (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(EachEntityConstants.ENTITY_IDENTIFIER_PARAM_KEY);
		Student flower = getStudentManager().getFlowerById(Long.parseLong(flowerId));
		Result<Student> result = new Result<Student>(flower);
		Type resultStringType = new TypeToken<Result<Student>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

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
		Student flower = getStudentManager().getFlowerById(flowerId);

		Gson gson = new Gson();
		String flowerJson = gson.toJson(flower, Student.class);

		req.setAttribute(EachEntityConstants.ENTITY_JSON_KEY, flowerJson);


		req.setAttribute(EachEntityConstants.ENTITY_REQUESTED_OPERATION_ATTR_KEY, CommonEntityUIOperations.VIEW.getOperationVal());
		return new Forward(UrlConstants.FLOWERS_EACH_FLOWER_JSP);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="flowers.do")
	public Response viewAllFlowers(HttpServletRequest req, HttpServletResponse resp) {
		List<Student> flowers = getFlowers(req);
		Gson gson = new Gson ();
		Type flowersType = new TypeToken<List<Student>>() {
		}.getType();
		String flowersJSON = gson.toJson(flowers, flowersType);
		req.setAttribute(EachEntityConstants.ENTITIES_JSON_KEY, flowersJSON);
		return new Forward(UrlConstants.FLOWERS_JSP_RELATIVE_URL);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="students.do")
	public Response viewAllStudents(HttpServletRequest req, HttpServletResponse resp) {
		List<Student> flowers = getFlowers(req);
		Gson gson = new Gson ();
		Type flowersType = new TypeToken<List<Student>>() {
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
