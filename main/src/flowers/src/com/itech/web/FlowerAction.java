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
import com.itech.flower.model.Flower;


@Controller
public class FlowerAction extends CommonAction {

	private static final String FLOWER_PARAM = "flower";
	private static final String FLOWER_ID_PARAM = "id";
	private static final String FLOWER_SEARCH_STRING_PARAM = "q";


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addFlower.do")
	public Response addFlower (HttpServletRequest req, HttpServletResponse resp) {
		String flowerJson = req.getParameter(FLOWER_PARAM);
		Gson gson = new Gson();
		Type flowerType = new TypeToken<Flower>() { }.getType();
		Flower flower = gson.fromJson(flowerJson, flowerType);
		getFlowerManager().addOrUpdate(flower);
		Result<Flower> result = new Result<Flower>(flower);
		Type resultFlowersType = new TypeToken<Result<Flower>>() {
		}.getType();

		return new CommonBeanResponse(result, resultFlowersType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteFlower.do")
	public Response deleteFlower (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(FLOWER_ID_PARAM);
		Flower flower = getFlowerManager().getFlowerById(Long.parseLong(flowerId));
		getFlowerManager().delete(flower);
		Result<String> result = new Result<String>("Successfully removed the flower " + flower.getName());
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowers.do")
	public Response getFlowers (HttpServletRequest req, HttpServletResponse resp) {
		String searchString = req.getParameter(FLOWER_SEARCH_STRING_PARAM);
		List<Flower> flowers = getFlowerManager().searchFlowersFor(searchString);
		Result<List<Flower>> result = new Result<List<Flower>>(flowers);
		Type resultStringType = new TypeToken<Result<List<Flower>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getFlowerDetails.do")
	public Response getFlowerDetails (HttpServletRequest req, HttpServletResponse resp) {
		String flowerId = req.getParameter(FLOWER_ID_PARAM);
		Flower flower = getFlowerManager().getFlowerById(Long.parseLong(flowerId));
		Result<Flower> result = new Result<Flower>(flower);
		Type resultStringType = new TypeToken<Result<Flower>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}


}
