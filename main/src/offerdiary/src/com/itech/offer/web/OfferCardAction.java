package com.itech.offer.web;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.db.SearchCriteria;
import com.itech.common.exeption.ReturnCodes;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.offer.model.OfferCard;
import com.itech.offer.vo.OfferCardVO;
import com.itech.user.model.User;

@Controller
public class OfferCardAction extends CommonAction{
	private static final String OFFER_CARD_JSON_KEY = "offerCardJson";
	private static final String OFFER_CARD_JSON_ATTR_KEY = "myOfferCardsJsonAttrKey";
	private static final String OFFER_CARD_NAME_KEY = "cardNameKey";
	private static final String OFFER_CARD_ID_KEY = "cardIdKey";

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="addNewCard.do")
	public Response addOfferCard (HttpServletRequest req, HttpServletResponse resp) {
		String offerCardJson = req.getParameter(OFFER_CARD_JSON_KEY);
		Gson gson = new Gson();
		Type offerCardJsonType = new TypeToken<OfferCard>() { }.getType();
		OfferCard offerCard = gson.fromJson(offerCardJson, offerCardJsonType);
		OfferCard processedCard = getOfferCardManager().saveOrUpdateOfferCard(offerCard);
		Result<OfferCard> result = new Result<OfferCard>(processedCard);
		Type resultOffersType = new TypeToken<Result<OfferCard>>() {
		}.getType();
		return new CommonBeanResponse(result, resultOffersType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="searchOfferCards.do")
	public Response searchOfferCards(HttpServletRequest req, HttpServletResponse resp) {
		//String cardSearchString = req.getParameter(OFFER_CARD_NAME_SEARCH_KEY);
		SearchCriteria searchCriteria = getSearchCriteria(req);
		OfferCardVO offerCardVOs = getOfferCardManager().getOfferCardVOsFor(searchCriteria, true);
		Result<OfferCardVO> result = new Result<OfferCardVO>(offerCardVOs);
		Type type = new TypeToken<Result<OfferCardVO>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getMyCards.do")
	public Response getMyCards(HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		List<OfferCard> offerCards = getOfferCardManager().getAssociatedOfferCardFor(user);
		Result<List<OfferCard>> result = new Result<List<OfferCard>>(offerCards);
		Type type = new TypeToken<Result<List<OfferCard>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="addCardToWallet.do")
	public Response addCardToWallet(HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		String offerCardJson = req.getParameter(OFFER_CARD_JSON_KEY);
		Gson gson = new Gson();
		Type offerCardJsonType = new TypeToken<OfferCard>() { }.getType();
		OfferCard offerCard = gson.fromJson(offerCardJson, offerCardJsonType);
		OfferCard offerCardFilled = getOfferCardManager().associateOfferCardToUser(offerCard, user);
		if (offerCardFilled == null) {
			Result<String> result = new Result<String>(ReturnCodes.VALIDATION_FAILURE, "Failed to Added the Card");
			Type resultOffersType = new TypeToken<Result<String>>() {
			}.getType();
			return new CommonBeanResponse(result, resultOffersType);
		}
		Result<OfferCard> result = new Result<OfferCard>(offerCardFilled);
		Type resultOffersType = new TypeToken<Result<OfferCard>>() {
		}.getType();
		return new CommonBeanResponse(result, resultOffersType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="removeCardFromWallet.do")
	public Response removeCardFromWallet(HttpServletRequest req, HttpServletResponse resp) {
		User user = getLoggedInUser();
		String offerCardJson = req.getParameter(OFFER_CARD_JSON_KEY);
		Gson gson = new Gson();
		Type offerCardJsonType = new TypeToken<OfferCard>() { }.getType();
		OfferCard offerCard = gson.fromJson(offerCardJson, offerCardJsonType);
		getOfferCardManager().deAssociateOfferCardFromUser(offerCard, user);
		Result<String> result = new Result<String>(ReturnCodes.SUCCESS, "Successfully removed the Card");
		result.setMsg("Successfully removed the card");
		Type resultOffersType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultOffersType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getCardByKey.do")
	public Response getCardByKey(HttpServletRequest req, HttpServletResponse resp) {
		String cardName = req.getParameter(OFFER_CARD_NAME_KEY);
		String cardId = req.getParameter(OFFER_CARD_ID_KEY);
		OfferCard offerCard = null;

		if ((cardId != null) && !cardId.isEmpty()) {
			offerCard = getOfferCardManager().getOfferCardFor(Long.parseLong(cardId));
		} else if ((cardName != null) && !cardName.isEmpty()){
			offerCard = getOfferCardManager().getOfferCardFor(cardName);
		}

		Result<OfferCard> result = new Result<OfferCard>(offerCard);
		Type type = new TypeToken<Result<OfferCard>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="cards.do")
	public Response goToMyCards(HttpServletRequest req, HttpServletResponse resp) {

		List<OfferCard> offerCards = getOfferCardManager().getAssociatedOfferCardFor(getLoggedInUser());
		Type offerCardsType = new TypeToken<List<OfferCard>>() {
		}.getType();
		Gson gson = new Gson();
		String myOfferCardsJson = gson.toJson(offerCards, offerCardsType);
		req.setAttribute(OFFER_CARD_JSON_ATTR_KEY, myOfferCardsJson);
		return new Forward("cards/pages/cards.jsp");
	}
}
