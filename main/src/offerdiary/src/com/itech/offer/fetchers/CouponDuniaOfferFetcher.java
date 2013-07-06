package com.itech.offer.fetchers;

import java.util.ArrayList;
import java.util.List;

import com.itech.common.util.UtilHttp;
import com.itech.offer.fetchers.parser.CouponDuniaParser;
import com.itech.offer.vo.CDOfferVO;

public class CouponDuniaOfferFetcher {

	private static final String COUPONDUNIA_EACH_COUPON_URL = "http://www.coupondunia.in/couponassist?cid=";

	public List<CDOfferVO> fetchAllOffers(int fromCouponId, int toCouponId) {

		List<CDOfferVO> cdOfferVOs = new ArrayList<CDOfferVO>();
		for (int i=fromCouponId; i<=toCouponId; i++) {
			CDOfferVO offerForUrl = getOfferForUrl(COUPONDUNIA_EACH_COUPON_URL+i);
			cdOfferVOs.add(offerForUrl);
		}
		return cdOfferVOs;
	}

	private CDOfferVO getOfferForUrl(String url) {
		String response = UtilHttp.fetchHttpResponse(url);
		CouponDuniaParser couponDuniaParser = new CouponDuniaParser(response);
		CDOfferVO offer = couponDuniaParser.getOffer();
		return offer;
	}

}
