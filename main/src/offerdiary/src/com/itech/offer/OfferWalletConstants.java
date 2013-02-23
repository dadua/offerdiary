package com.itech.offer;

public class OfferWalletConstants {
	public static final String OFFER_LIST_PARAM_KEY = "offers";
	public static final String OFFER_IDS_PARAM_KEY = "offerIds";
	public static final String WALLET_PAGE = "offers/pages/offers.jsp";
	public static final String INDEX_PAGE = "index.jsp";
	public static final String OFFER_NOTIFICATION_CONFIG_PARAM_KEY = "offerNotificationConfigs";


	public static final String OFFER_ID_PARAM_KEY = "id";
	public static final String OFFER_PARAM_KEY = "offer";
	public static final String GET_OFFER_DETAIL_PAGE = "offerDetail.jsp";


	public static final String GET_SHARED_OFFER_PAGE = "sharedOffer.jsp";
	public static final String SHARED_OFFER_ACCESS_CODE_PARAM_KEY = "accessCcode";
	public static final String GET_SHARED_OFFER_SHARE_URL_PREFIX = "/getSharedOffer.do?accessCode=";
	public static String getSharedOfferURL(String accessToken) {
		return OfferWalletConstants.GET_SHARED_OFFER_SHARE_URL_PREFIX + accessToken;
	}


	public static final String SHARED_OFFER_ATTR_KEY = "offer";
	public static final String SHARED_OFFER_URL_ATTR_KEY = "sharedOfferUrl";
	public static final String SHARED_OFFER_ID_ATTR_KEY = "id";
	public static final String SHARE_OFFER_ACTION_PARAM_KEY = "shareOfferAction";


	public static final String OFFER_CARD_ID_PARAM_KEY = "cardIdKey";



	public static final String USER_INFO_PARAM_KEY = "userInfo";
	public static final String USER_NOTIFICATION_CONFIG_PARAM_KEY = "userNotificationConfig";

}
