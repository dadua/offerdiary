package com.itech.offer.web;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.CommonUtilities;
import com.itech.common.db.OfferSearchCriteria;
import com.itech.common.web.action.ActionResponseAnnotation;
import com.itech.common.web.action.CommonAction;
import com.itech.common.web.action.CommonBeanResponse;
import com.itech.common.web.action.Forward;
import com.itech.common.web.action.Redirect;
import com.itech.common.web.action.Response;
import com.itech.common.web.action.Result;
import com.itech.offer.OfferWalletConstants;
import com.itech.offer.manager.OfferViewUtils;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferShare;
import com.itech.offer.vo.FBPostVO;
import com.itech.offer.vo.OfferSearchResultVO;
import com.itech.offer.vo.OfferShareVO;
import com.itech.offer.vo.OfferVO;
import com.itech.user.model.User;
import com.itech.user.model.UserRole;
import com.itech.user.vos.ShareOfferActionVO;

@Controller
public class OfferAction extends CommonAction{

	private static final String OFFER_HTML_ATTR_KEY = "offerHtml";
	private static final String IS_SHARED_OFFER_PARAM_KEY = "isSharedOffer";
	private static final String MY_VALID_OFFERS_COUNT_ATTR_KEY = "myValidOffersCount";
	private static final String PUBLIC_OFFERS_COUNT_ATTR_KEY = "publicOffersCount";
	private static final String PUBLIC_OFFERS_SEARCH_QUERY_ATTR_KEY = "publicSearchQuery";
	private static final String VENDOR_LOGO_URL_ATTR_KEY = "vendorLogoUrl";


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
	 * for OffersOnMyCard
	 * privateSearchOnly=false
	 * cardOffersOnly=true
	 * offersOnMyCardsOnly=true
	 * 
	 * 
	 * for offersOnSpecificCard
	 * privateSearchOnly=false
	 * cardOffersOnly=true
	 * cardId=id of card
	 * 
	 * 
	 * 
	 * for public search--
	 * public=true
	 * q=query string
	 */

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="searchOffers.do")
	public Response searchOffers(HttpServletRequest req, HttpServletResponse resp) {
		OfferSearchCriteria searchCriteria = (OfferSearchCriteria) getSearchCriteria(req);
		if ((searchCriteria.isOffersOnMyCardsOnly()  || !searchCriteria.getPrivateSearchOnly()) && ( getLoggedInUser() == null || !UserRole.OD_ADMIN.equals(getLoggedInUser().getUserRole()))) {
			searchCriteria.setUniqueFilter("valid");//TODO do this in some good way
		}
		OfferSearchResultVO offers = getOfferManager().searchOffersFor(searchCriteria);
		Result<OfferSearchResultVO> result = new Result<OfferSearchResultVO>(offers);
		Type type = new TypeToken<Result<OfferSearchResultVO>>() { }.getType();
		return new CommonBeanResponse(result, type);
	}



	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="myoffers.do")
	public Response goToOffers(HttpServletRequest req, HttpServletResponse resp) {
		return goToMyWallet(req, resp);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="diary.do")
	public Response goToMyWallet(HttpServletRequest req, HttpServletResponse resp) {
		User loggedInUser = getLoggedInUser();
		if (loggedInUser != null) {
			OfferSearchCriteria validOffersSearchCriteria = new OfferSearchCriteria();
			validOffersSearchCriteria.setUniqueFilter("valid");
			OfferSearchResultVO offerSearchResultVO = getOfferManager().searchOffersFor(validOffersSearchCriteria);
			req.setAttribute(MY_VALID_OFFERS_COUNT_ATTR_KEY, offerSearchResultVO.getTotalCount());
		}
		return new Forward(OfferWalletConstants.WALLET_PAGE);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="offers.do")
	public Response goToPublicOffers(HttpServletRequest req, HttpServletResponse resp) {
		String query = req.getParameter("q");
		req.setAttribute(PUBLIC_OFFERS_SEARCH_QUERY_ATTR_KEY, query);
		OfferSearchCriteria validOffersSearchCriteria = new OfferSearchCriteria();
		validOffersSearchCriteria.setPrivateSearchOnly(false);
		if (getLoggedInUser() == null || !UserRole.OD_ADMIN.equals(getLoggedInUser().getUserRole())) {
			validOffersSearchCriteria.setUniqueFilter("valid");//TODO do this in some good way
		}
		validOffersSearchCriteria.setQ(query);

		OfferSearchResultVO offerSearchResultVO = getOfferManager().searchOffersFor(validOffersSearchCriteria);
		req.setAttribute(PUBLIC_OFFERS_COUNT_ATTR_KEY, offerSearchResultVO.getTotalCount());
		return new Forward(OfferWalletConstants.PUBLIC_OFFERS_PAGE);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="getMyOffers.do")
	public Response getMyOffers (HttpServletRequest req, HttpServletResponse resp) {
		return searchOffers(req, resp);
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

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addSharedOfferToWallet.do")
	public Response addSharedOfferToWallet (HttpServletRequest req, HttpServletResponse resp) {
		String accessToken = req.getParameter(OfferWalletConstants.SHARED_OFFER_ACCESS_CODE_PARAM_KEY);
		Offer newOffer = null;
		if (accessToken != null) {
			newOffer = getOfferManager().addSharedOfferToWallet(accessToken, getLoggedInUser());
		}
		return new Redirect("diary.do");
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addOfferFromCardToWallet.do")
	public Response addOfferFromCardToWallet (HttpServletRequest req, HttpServletResponse resp) {
		String offerId = req.getParameter(OfferWalletConstants.OFFER_ID_PARAM_KEY);
		getOfferManager().addOfferFromCardToUser(offerId, getLoggedInUser());
		Result<String> result = new Result<String>("Offer successfully  added to your wallet");
		result.setMsg("Offer successfully  added to your wallet");
		Type resultType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultType);
	}


	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="addOffer.do")
	public Response addOffer (HttpServletRequest req, HttpServletResponse resp) {
		String offerId = req.getParameter(OfferWalletConstants.OFFER_ID_PARAM_KEY);
		getOfferManager().addOfferToUser(offerId, getLoggedInUser());
		Result<String> result = new Result<String>("Offer successfully  added to your wallet");
		result.setMsg("Offer successfully  added to your wallet");
		Type resultType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultType);
	}


	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="deleteOffers.do")
	public Response removeOffersFromWallet (HttpServletRequest req, HttpServletResponse resp) {
		String offersJson = req.getParameter(OfferWalletConstants.OFFER_IDS_PARAM_KEY);
		Gson gson = new Gson();
		Type type = new TypeToken<List<String>>() { }.getType();
		List<String> offerUniqueIds = gson.fromJson(offersJson, type);
		getOfferManager().removeOffersFromWallet(offerUniqueIds, getLoggedInUser());
		Result<String> result = new Result<String>("Successfully removed the offers from wallet");
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

		String offerHtmlFrom = OfferViewUtils.getOfferHtmlFrom(sharedOffer, true);
		req.setAttribute(OfferWalletConstants.SHARED_OFFER_ATTR_KEY, sharedOffer);
		Gson gson = new Gson();
		String offerVOJson = gson.toJson(sharedOffer, OfferVO.class);
		req.setAttribute(IS_SHARED_OFFER_PARAM_KEY, true);
		req.setAttribute(OfferWalletConstants.OFFER_VO_PARAM_KEY,
				StringEscapeUtils.escapeJavaScript(offerVOJson));
		req.setAttribute(OFFER_HTML_ATTR_KEY, offerHtmlFrom);
		req.setAttribute(VENDOR_LOGO_URL_ATTR_KEY
				, OfferViewUtils.getVendorLogoUrl(sharedOffer));
		return new Forward(OfferWalletConstants.GET_SHARED_OFFER_PAGE);
	}

	@ActionResponseAnnotation(responseType=Forward.class)
	@ActionMapping(value="getOfferDetail.do")
	public Response getOfferDetail (HttpServletRequest req, HttpServletResponse resp) {
		String uniqueId = req.getParameter(OfferWalletConstants.OFFER_ID_PARAM_KEY);
		Offer offer = getOfferManager().getOfferForUnqueId(uniqueId);
		OfferVO offerVO = OfferVO.getOfferVOFor(offer);
		String offerHtmlFrom = OfferViewUtils.getOfferHtmlFrom(offerVO, false);
		Gson gson = new Gson();
		String offerVOJson = gson.toJson(offerVO, OfferVO.class);
		req.setAttribute(OfferWalletConstants.OFFER_PARAM_KEY, offer);
		req.setAttribute(OfferWalletConstants.OFFER_VO_PARAM_KEY,
				StringEscapeUtils.escapeJavaScript(offerVOJson));
		req.setAttribute(OFFER_HTML_ATTR_KEY, offerHtmlFrom);
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

		OfferShareVO offerShareVO = OfferShareVO.getOfferShareVOFor(offerShare);
		setSharingDetails(offerShareVO, req);
		Result<OfferShareVO> result = new Result<OfferShareVO>(offerShareVO);
		Type resultStringType = new TypeToken<Result<OfferShareVO>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="reShareOffer.do")
	public Response reShareOffer(HttpServletRequest req, HttpServletResponse resp) {
		String accessCode = req.getParameter(OfferWalletConstants.SHARED_OFFER_ACCESS_CODE_PARAM_KEY);
		OfferShare offerShare = null;
		if (CommonUtilities.isNotEmpty(accessCode)) {
			offerShare = getOfferManager().getOfferShareFor(accessCode);
		}

		OfferShareVO offerShareVO = OfferShareVO.getOfferShareVOFor(offerShare);
		setSharingDetails(offerShareVO, req);
		Result<OfferShareVO> result = new Result<OfferShareVO>(offerShareVO);
		Type resultStringType = new TypeToken<Result<OfferShareVO>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}

	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	@ActionMapping(value="shareOfferViaEmail.do")
	/**
	 * Param- dataToShare = { mailShare:true, accessToken:'shareId', message:'messageByUser', emailIds:'mailIds by user'}
	 * 
	 */
	public Response shareOfferViaEmail(HttpServletRequest req, HttpServletResponse resp) {
		String shareOfferActionVOJson = req.getParameter(OfferWalletConstants.SHARE_OFFER_ACTION_PARAM_KEY);
		Gson gson = new Gson();
		Type offersType = new TypeToken<ShareOfferActionVO>() { }.getType();
		ShareOfferActionVO shareOfferActionVO = gson.fromJson(shareOfferActionVOJson, offersType);
		String shareOfferUrl = OfferWalletConstants.getSharedOfferURL(shareOfferActionVO.getAccessToken());
		String absoluteSharedURL = getAbsoluteURL(req, shareOfferUrl);
		shareOfferActionVO.setMailShare(true);
		shareOfferActionVO.setShareOfferURL(absoluteSharedURL);
		getOfferManager().shareOffer(shareOfferActionVO);
		Result<String> result = new Result<String>("Mail sent to provided emails");
		Type resultStringType = new TypeToken<Result<String>>() {
		}.getType();
		return new CommonBeanResponse(result, resultStringType);
	}



	private void setSharingDetails(OfferShareVO offerShareVO, HttpServletRequest req) {
		String shareOfferUrl = OfferWalletConstants.getSharedOfferURL(offerShareVO.getAccessToken());
		String absoluteSharedURL = getAbsoluteURL(req, shareOfferUrl);
		offerShareVO.setSharedURL(absoluteSharedURL);
		FBPostVO fbPostVO = new FBPostVO();
		fbPostVO.setCaption("offerdiary.com");
		fbPostVO.setDescription(offerShareVO.getOffer().getDescription() + " (Shared via Offer Diary)");
		fbPostVO.setLink(absoluteSharedURL);
		if(CommonUtilities.isNotEmpty(offerShareVO.getOffer().getTargetVendor().getLogoUrl())) {
			String pictureUrl = "http://offerdiary.com/images/stores/" + offerShareVO.getOffer().getTargetVendor().getLogoUrl();
			fbPostVO.setPictureURL(pictureUrl);
		}
		fbPostVO.setName("Offer From - " + offerShareVO.getOffer().getTargetVendor().getName());
		fbPostVO.setRedirectURI(getAbsoluteURL(req, ""));
		offerShareVO.setFbPost(fbPostVO);
	}




























	//	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	//	@ActionMapping(value="getOffersOnMyCards.do")
	//	@Deprecated
	//	public Response getOffersOnMyCards (HttpServletRequest req, HttpServletResponse resp) {
	//		User loggedInUser = getLoggedInUser();
	//		OfferSearchResultVO offers = null;
	//		if (loggedInUser != null) {
	//			offers = getOfferManager().getAllOffersOnCardsForUser(loggedInUser);
	//		} else {
	//			offers = null;
	//		}
	//		Result<OfferSearchResultVO> result = new Result<OfferSearchResultVO>(offers);
	//		Type type = new TypeToken<Result<OfferSearchResultVO>>() { }.getType();
	//		return new CommonBeanResponse(result, type);
	//	}
	//
	//	@ActionResponseAnnotation(responseType=CommonBeanResponse.class)
	//	@ActionMapping(value="getOffersOnCard.do")
	//	@Deprecated
	//	public Response getOffersOnCard (HttpServletRequest req, HttpServletResponse resp) {
	//		String offerCardId = req.getParameter(OfferWalletConstants.OFFER_CARD_ID_PARAM_KEY);
	//		Long cardId = Long.parseLong(offerCardId);
	//		OfferSearchResultVO offers = getOfferManager().getAllOffersForCard(cardId);
	//		Result<OfferSearchResultVO> result = new Result<OfferSearchResultVO>(offers);
	//		result.setMsg("Got offers on cards");
	//		Type type = new TypeToken<Result<OfferSearchResultVO>>() { }.getType();
	//		return new CommonBeanResponse(result, type);
	//	}
	//
	//

}