package com.itech.offer.hdfc.parser;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import com.itech.offer.fetchers.parser.CommonHtmlParser;
import com.itech.parser.offer.model.CardOfferVO;

public class HdfcOfferParser extends CommonHtmlParser{


	public HdfcOfferParser(String html) {
		super(html);
	}

	public List<CardOfferVO> getOffersOnCard() {
		List<CardOfferVO> debitCardOffers = new ArrayList<CardOfferVO>();
		List<Element> offerPosts = getDoc().select("div.postdetails");
		for(Element offerPost : offerPosts){
			processOfferPost(offerPost, debitCardOffers);
		}
		return debitCardOffers;
	}

	private void processOfferPost(Element offerPost,
			List<CardOfferVO> cardOffers) {
		String merchantName = getMerchantName(offerPost);
		String imageURL = getOfferImage(offerPost);
		String description = getOfferDescription(offerPost);
		String offerUrl = getOfferUrl(offerPost);
		Date expiry = getOfferExpiry(offerPost);
		CardOfferVO cardOffer = new CardOfferVO();
		cardOffer.setDescription(description);
		cardOffer.setImageSrc(imageURL);
		cardOffer.setMerchantName(merchantName);
		cardOffer.setOfferUrl(offerUrl);
		cardOffer.setMerchantImageUrl(imageURL);
		cardOffers.add(cardOffer);
		cardOffer.setExpiry(expiry);

	}

	private Date getOfferExpiry(Element offerPost) {
		Element validityBlock = offerPost.select("p.postsummery").first();
		String validityText = validityBlock.text();
		Date expiry = HdfcOfferParserUtil.getExpiryDateFrom(validityText);
		return expiry;
	}


	private String getOfferDescription(Element offerPost) {
		String fullOfferText = getFullOfferText(offerPost);
		String description = HdfcOfferParserUtil.getOfferDescriptionFrom(fullOfferText);
		return description;
	}

	private String getFullOfferText(Element offerPost) {
		String description = "";
		Element offerDescriptionBlock = offerPost.select("h4.posttitle").first();
		if(null != offerDescriptionBlock){
			description = offerDescriptionBlock.text();
		}
		return description;
	}

	private String getOfferImage(Element offerPost) {
		String imageURL = "";
		Element imageBlock = offerPost.select("img.post_img").first();
		if(null != imageBlock){
			imageURL = imageBlock.absUrl("src");
		}
		return imageURL;
	}

	private String getMerchantName(Element offerPost) {
		String fullOfferText = getFullOfferText(offerPost);
		String merchantName = HdfcOfferParserUtil.getMerchantNameFrom(fullOfferText);
		return merchantName;
	}

	private String getOfferUrl(Element offerPost) {
		String offerUrl = null;
		Element urlElement = offerPost.select(".readmore").first();
		if(null != urlElement){
			offerUrl = urlElement.attr("href");
		}
		return offerUrl;
	}

}
