package com.itech.offer.web;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.offer.model.OfferCard;

public class OfferCardAction extends CommonAction{
	private static final String OFFER_CARD_JSON_KEY = "offerCardJson";
	private static final int MAX_RESULT_COUNT = 10;
	private static final String OFFER_CARD_NAME_SEARCH_KEY = "searchKey";
	private static final String OFFER_CARD_NAME_KEY = "cardNameKey";
	private static final String OFFER_CARD_ID_KEY = "cardIdKey";

	public Response addOfferCard (HttpServletRequest req, HttpServletResponse resp) {
		String offerCardJson = req.getParameter(OFFER_CARD_JSON_KEY);
		Gson gson = new Gson();
		Type offerCardJsonType = new TypeToken<OfferCard>() { }.getType();
		OfferCard offerCard = gson.fromJson(offerCardJson, offerCardJsonType);
		OfferCard processedCard = getOfferCardManager().saveOrUpdateOfferCard(offerCard);
		Result<OfferCard> result = new Result<OfferCard>(true, processedCard, "Successfully Added the Card");
		Type resultOffersType = new TypeToken<Result<OfferCard>>() {
		}.getType();
		return new CommonBeanResponse(result, resultOffersType);
	}

	public Response searchOfferCards(HttpServletRequest req, HttpServletResponse resp) {
		String cardSearchString = req.getParameter(OFFER_CARD_NAME_SEARCH_KEY);
		List<OfferCard> offerCards = getOfferCardManager().getOfferCardsFor(cardSearchString, MAX_RESULT_COUNT);
		Result<List<OfferCard>> result = new Result<List<OfferCard>>(true, offerCards);
		Type type = new TypeToken<Result<List<OfferCard>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	public Response getCardByKey(HttpServletRequest req, HttpServletResponse resp) {
		String cardName = req.getParameter(OFFER_CARD_NAME_KEY);
		String cardId = req.getParameter(OFFER_CARD_ID_KEY);
		OfferCard offerCard = null;

		if (cardId != null && !cardId.isEmpty()) {
			offerCard = getOfferCardManager().getOfferCardFor(Long.parseLong(cardId));
		} else if (cardName != null && !cardName.isEmpty()){
			offerCard = getOfferCardManager().getOfferCardFor(cardName);
		}

		Result<OfferCard> result = new Result<OfferCard>(true, offerCard);
		Type type = new TypeToken<Result<OfferCard>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}
}
