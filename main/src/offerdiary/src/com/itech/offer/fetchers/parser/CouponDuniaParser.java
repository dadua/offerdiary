package com.itech.offer.fetchers.parser;

import org.jsoup.nodes.Element;

import com.itech.offer.vo.CDOfferVO;


public class CouponDuniaParser extends CommonHtmlParser {

	public CouponDuniaParser(String html) {
		super(html);
	}

	public CDOfferVO getOffer() {

		CDOfferVO offerVO = new CDOfferVO();

		Element couponElement = getDoc().select(".selectedCoupon").first();
		Element couponVendorInfoElement = getDoc().select(".couponInfo").first();
		offerVO.setOfferTitle(getTitle(couponElement));
		offerVO.setOfferDescription(getDescription(couponElement));
		offerVO.setOfferCode(getOfferCode(couponElement));
		offerVO.setVendorImgUrl(getVendorImgUrl(couponVendorInfoElement));
		offerVO.setVendorUrl(getVendorUrl(couponVendorInfoElement));
		offerVO.setVendorName(getVendorName(couponVendorInfoElement));
		return offerVO;
	}

	private String getVendorName(Element couponVendorInfoElement) {
		return couponVendorInfoElement.select("a").attr("title");
	}

	private String getVendorUrl(Element couponVendorInfoElement) {
		return couponVendorInfoElement.select("a").attr("href");
	}

	private String getVendorImgUrl(Element couponVendorInfoElement) {
		return couponVendorInfoElement.select("img").attr("src");
	}

	private String getDescription(Element couponElement) {
		return couponElement.select("p a").html();
	}

	private String getOfferCode(Element couponElement) {
		return couponElement.select(".coupon_code_text").html();
	}

	private String getTitle(Element couponElement) {
		return couponElement.select(".couponTitle").html();
	}

}
