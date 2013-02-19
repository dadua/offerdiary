package com.itech.offer.web;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.CommonUtilities;
import com.itech.common.db.SearchCriteria;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.offer.OfferWalletConstants;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferShare;
import com.itech.offer.vo.OfferSearchResultVO;
import com.itech.offer.vo.OfferShareVO;
import com.itech.offer.vo.OfferVO;
import com.itech.user.model.User;

@Controller
public class OfferAction extends CommonAction{

	private static final String MY_OFFERS_JSON_ATTR_KEY = "myOffersJson";


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="home.do")
	public Response goToHome(HttpServletRequest req, HttpServletResponse resp) {
		return new Forward(OfferWalletConstants.INDEX_PAGE);
	}


	/**
	 * 
	 * e.g. /searchOffers.do?searchCriteria={uniqueFilter:'addedInLast7Days',q:'offerDesc'}
	 * uniqueFilter = expired, all, valid, expiringInNext7Days, addedInLast7Days
	 * 
	 */
	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="searchOffers.do")
	public Response searchOffers(HttpServletRequest req, HttpServletResponse resp) {
		SearchCriteria searchCriteria = getSearchCriteria(req);
		OfferSearchResultVO offers = getOfferManager().searchOffersFor(searchCriteria);
		Result<OfferSearchResultVO> result = new Result<OfferSearchResultVO>(offers);
		Type type = new TypeToken<Result<OfferSearchResultVO>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}



	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="offers.do")
	public Response goToOffers(HttpServletRequest req, HttpServletResponse resp) {
		return goToMyWallet(req, resp);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="wallet.do")
	public Response goToMyWallet(HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<OfferVO> offers = new ArrayList<OfferVO>();
		if (loggedInUser != null) {
			SearchCriteria validOffersSearchCriteria = new SearchCriteria();
			validOffersSearchCriteria.setUniqueFilter("valid");
			OfferSearchResultVO offerSearchResultVO = getOfferManager().searchOffersFor(validOffersSearchCriteria);
			offers = offerSearchResultVO.getOffers();
			Gson gson = new Gson();
			Type listOfOffersType = new TypeToken<List<OfferVO>>() {
			}.getType();
			String offersJson = gson.toJson(offers, listOfOffersType);
			req.setAttribute(MY_OFFERS_JSON_ATTR_KEY, StringEscapeUtils.escapeJavaScript(offersJson));
		}
		return new Forward(OfferWalletConstants.WALLET_PAGE);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getMyOffers.do")
	public Response getMyOffers (HttpServletRequest req, HttpServletResponse resp) {
		return searchOffers(req, resp);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getOffersOnMyCards.do")
	public Response getOffersOnMyCards (HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		List<Offer> offers = new ArrayList<Offer>();
		if (loggedInUser != null) {
			offers = getOfferManager().getAllOffersOnCardsForUser(loggedInUser);
		} else {
			offers = new ArrayList<Offer>();
		}
		Result<List<Offer>> result = new Result<List<Offer>>(offers);
		Type type = new TypeToken<Result<List<Offer>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getOffersOnCard.do")
	public Response getOffersOnCard (HttpServletRequest req, HttpServletResponse resp) {
		String offerCardId = req.getParameter(OfferWalletConstants.OFFER_CARD_ID_PARAM_KEY);
		Long cardId = Long.parseLong(offerCardId);
		List<Offer> offers = getOfferManager().getAllOffersForCard(cardId);
		Result<List<Offer>> result = new Result<List<Offer>>(offers);
		result.setMsg("Got offers on cards");
		Type type = new TypeToken<Result<List<Offer>>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="saveOffers.do")
	public Response saveOffers (HttpServletRequest req, HttpServletResponse resp) {
		String offersJson = req.getParameter(OfferWalletConstants.OFFER_LIST_PARAM_KEY);
		Gson gson = new Gson();
		Type offersType = new TypeToken<List<Offer>>() { }.getType();
		List<Offer> offers = gson.fromJson(offersJson, offersType);
		getOfferManager().addOffersForUser(offers, getLoggedInUser());
		Result<List<Offer>> result = new Result<List<Offer>>(offers);
		Type resultOffersType = new TypeToken<Result<List<Offer>>>() {
		}.getType();
		return new CommonBeanResponse(result, resultOffersType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteOffers.do")
	public Response deleteOffers (HttpServletRequest req, HttpServletResponse resp) {
		String offersJson = req.getParameter(OfferWalletConstants.OFFER_IDS_PARAM_KEY);
		Gson gson = new Gson();
		Type type = new TypeToken<List<String>>() { }.getType();
		List<String> offerUniqueIds = gson.fromJson(offersJson, type);
		getOfferManager().deleteByUniqueIds(offerUniqueIds);
		Result<String> result = new Result<String>("Successfully Deleted the offers");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="getSharedOffer.do")
	public Response getSharedOffer (HttpServletRequest req, HttpServletResponse resp) {
		String accessToken = req.getParameter(OfferWalletConstants.SHARED_OFFER_ACCESS_CODE_PARAM_KEY);
		OfferShare offerShare = null;
		if (accessToken != null) {
			offerShare = getOfferManager().getOfferShareFor(accessToken);
		}
		OfferVO sharedOffer = OfferVO.getOfferVOFor(offerShare.getOffer());
		sharedOffer.setId(accessToken);
		sharedOffer.setUniqueId(accessToken);
		sharedOffer.setAccessCode(accessToken);
		req.setAttribute(OfferWalletConstants.SHARED_OFFER_ATTR_KEY, sharedOffer);
		return new Forward(OfferWalletConstants.GET_SHARED_OFFER_PAGE);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="getOfferDetail.do")
	public Response getOfferDetail (HttpServletRequest req, HttpServletResponse resp) {
		String uniqueId = req.getParameter(OfferWalletConstants.OFFER_ID_PARAM_KEY);
		Offer offer = getOfferManager().getOfferForUnqueId(uniqueId);
		req.setAttribute(OfferWalletConstants.OFFER_PARAM_KEY, offer);
		return new Forward(OfferWalletConstants.GET_OFFER_DETAIL_PAGE);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="shareOffer.do")
	public Response shareOffer(HttpServletRequest req, HttpServletResponse resp) {
		String uniqueId = req.getParameter(OfferWalletConstants.SHARED_OFFER_ID_ATTR_KEY);
		OfferShare offerShare = null;
		if (CommonUtilities.isNotEmpty(uniqueId)) {
			Offer offer = getOfferManager().getOfferForUnqueId(uniqueId);
			offerShare = getOfferManager().createOfferShare(offer);
		}
		String shareOfferUrl = OfferWalletConstants.GET_SHARED_OFFER_SHARE_URL_PREFIX + offerShare.getAccessToken();
		String absoluteSharedURL = getAbsoluteURL(req, shareOfferUrl);
		OfferShareVO offerShareVO = OfferShareVO.getOfferShareVOFor(offerShare);
		offerShareVO.setSharedURL(absoluteSharedURL);
		Result<OfferShareVO> result = new Result<OfferShareVO>(offerShareVO);
		Type resultStringType = new TypeToken<Result<OfferShareVO>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

}
