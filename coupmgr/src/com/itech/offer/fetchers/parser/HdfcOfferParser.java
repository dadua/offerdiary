package com.itech.offer.fetchers.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import com.itech.parser.offer.model.CardOfferVO;

public class HdfcOfferParser extends CommonHttpParser{


	public HdfcOfferParser(String html) {
		super(html);
	}

	public List<CardOfferVO> getDebitCardOffers() {
		List<CardOfferVO> debitCardOffers = new ArrayList<CardOfferVO>();
		List<Element> offerPosts = getDoc().select("div.postdetails");
		for(Element offerPost : offerPosts){
			processOfferPost(offerPost, debitCardOffers);
		}
		return debitCardOffers;
	}

	private void processOfferPost(Element offerPost,
			List<CardOfferVO> debitCardOffers) {
		String merchantName = getMerchantName(offerPost);
		String imageURL = getOfferImage(offerPost);
		String description = getOfferDescription(offerPost);
		String validity = getOfferValidity(offerPost);
		CardOfferVO cardOffer = new CardOfferVO();
		cardOffer.setDescription(description);
		cardOffer.setImageSrc(imageURL);
		cardOffer.setMerchantName(merchantName);
		cardOffer.setValidity(validity);
		debitCardOffers.add(cardOffer);

	}

	private String getOfferValidity(Element offerPost) {
		String validity = "";
		Element validityBlock = offerPost.select("p.postsummery").first();
		if(null != validityBlock){
			Pattern pattern = Pattern.compile("Valid([a-zA-Z0-9]|\\s)*2012");
			Matcher matcher = pattern.matcher(validityBlock.text());
			if (matcher.find())
			{
				return matcher.group();
			}
		}
		return validity;
	}

	private String getOfferDescription(Element offerPost) {
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
		String merchantName = "";
		Element merchantBlock = offerPost.select("h4.posttitle").first();
		if(null != merchantBlock){
			Pattern pattern = Pattern.compile("([a-zA-Z0-9]|\\s)*-");
			Matcher matcher = pattern.matcher(merchantBlock.text());
			if (matcher.find())
			{
				return matcher.group();
			}
		}
		return merchantName;
	}

	public List<CardOfferVO> getCreditCardOffers() {
		List<CardOfferVO> creditCardOffers = new ArrayList<CardOfferVO>();
		List<Element> offerPosts = getDoc().select("div.postdetails");
		for(Element offerPost : offerPosts){
			processOfferPost(offerPost, creditCardOffers);
		}
		return creditCardOffers;
	}

}
