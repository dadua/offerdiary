package com.itech.offer.fetchers.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itech.parser.offer.model.CardOfferVO;

public class CitiOfferParser extends CommonHttpParser {

	private final Logger logger = Logger.getLogger(CitiOfferParser.class);

	/*Selectors & other constants*/
	public static final String MORE_LINK_SELECTOR  =  "div [id^=homect] div.more_btn a[href]";
	public static final String CUISINE_OFFER_CHUNK_HOLDER_DIV_SELECTOR = ".cusine_frame";
	public static final String OFFFER_PAGE_CITY_NAME_SPAN_SELECTOR = "p.offer_hd span";
	public static final String OFFER_HOLDER_DIV_CLASSNAME_SELECTOR = "so-resholder";
	public static final String OFFER_VALIDITY_PARA_SELECTOR = ".validity_hd";
	public static final String OFFER_DESCRIPTION_DIV_SELECTOR = "div[class^=so-resholderright-col]";


	public static final String DINING_CONSPUTION_TYPE="dining";


	public CitiOfferParser(String citiOfferHtml) {
		super(citiOfferHtml);
	}

	public List<String> getCityOfferLinks() {
		Elements elements = getDoc().select(MORE_LINK_SELECTOR);
		List<String> cityOfferLinks = new ArrayList<String>();
		for (Element element: elements) {
			String relativeLinkUrl = element.attr("href");
			cityOfferLinks.add(relativeLinkUrl);
		}
		return cityOfferLinks;
	}


	/**
	 * 
	 * @return return list of citi cuisine offers
	 */
	public List<CardOfferVO> getCuisineOffers() {
		String city = getCity();
		Elements offerChunkHolder = getDoc().select(CUISINE_OFFER_CHUNK_HOLDER_DIV_SELECTOR);
		List<CardOfferVO> citiCuisineoffers = new ArrayList<CardOfferVO>();
		for (Element offerChunk: offerChunkHolder) {
			getAllOfferForCurrentOfferChunk(city,offerChunk, citiCuisineoffers);
		}
		return citiCuisineoffers;
	}


	private String getCity() {
		Element offerHeadingCityElement = getDoc().select(OFFFER_PAGE_CITY_NAME_SPAN_SELECTOR).first();
		return offerHeadingCityElement.text();
	}


	/**
	 * each offer chunk contains certain number of offers depending on
	 * location and the sub location
	 * @param city
	 * @param offerChunk
	 * @param citiCuisineoffers
	 */
	private void getAllOfferForCurrentOfferChunk(String city, Element offerChunk,
			List<CardOfferVO> citiCuisineoffers) {

		String cusineType=  getCusineType(offerChunk);
		List<Element> allElementInsideOfferChunk = offerChunk.children();

		for(int i=0; i < allElementInsideOfferChunk.size() ; i++){

			Element currentElement = allElementInsideOfferChunk.get(i);
			String currentTagName = currentElement.tagName();

			if(TagUtil.DIV.equalsIgnoreCase(currentTagName)){
				if(OFFER_HOLDER_DIV_CLASSNAME_SELECTOR.equalsIgnoreCase(currentElement.className())){
					CardOfferVO offer = processNewOffer(currentElement);
					offer.setLocation(city);
					offer.setConsumptionType(DINING_CONSPUTION_TYPE);
					offer.setConsumptionSubType(cusineType);
					debugOffer(offer);
					citiCuisineoffers.add(offer);
				}
			}else if(TagUtil.PARAGRAPGH.equalsIgnoreCase(currentTagName)){
				if("cusine_hd".equalsIgnoreCase(currentElement.className())){
					continue;
				}
				Element offerHolder = allElementInsideOfferChunk.get(++i);
				if(TagUtil.DIV.equalsIgnoreCase(offerHolder.tagName()) && OFFER_HOLDER_DIV_CLASSNAME_SELECTOR.equalsIgnoreCase(offerHolder.className())){
					CardOfferVO offer = processNewOffer(currentElement, offerHolder);
					offer.setLocation(city);
					offer.setConsumptionType(DINING_CONSPUTION_TYPE);
					offer.setConsumptionSubType(cusineType);
					debugOffer(offer);
					citiCuisineoffers.add(offer);
				}
			}
		}
	}

	private void debugOffer(CardOfferVO offer) {
		logger.debug(offer);
		logger.debug("----------------------------------------------------------------------------");
	}

	/*where an offer is composite entity*/
	private CardOfferVO processNewOffer(Element validityElement, Element offerHolder) {
		CardOfferVO offer = new CardOfferVO();
		String validity = validityElement.text();
		offer.setValidity(validity);
		setOfferFields(offerHolder, offer);
		return offer;
	}

	/* where an offer block is individual entity*/
	private CardOfferVO processNewOffer(Element offerHolder) {
		CardOfferVO offer = new CardOfferVO();
		setOfferFields(offerHolder, offer);
		String validity = getValidity(offerHolder);
		offer.setValidity(validity);
		return offer;
	}

	private void setOfferFields(Element offerHolder, CardOfferVO offer){
		String merchantName = getMerchantName(offerHolder);
		String description = getOfferDescription(offerHolder);
		String imageSrc = getOffferImage(offerHolder);
		offer.setMerchantName(merchantName);
		offer.setDescription(description);
		offer.setImageSrc(imageSrc);
	}

	private String getMerchantName(Element offerHolder) {
		Element merchant = offerHolder.select("p.so-bluetext11").first();
		String merchantName ="";
		if(null != merchant){
			merchantName = merchant.text();
		}
		return merchantName;
	}

	private String getOffferImage(Element offerHolder) {
		Element image = offerHolder.select("img").first();
		String url = image.attr("abs:src");
		return url;
	}

	private String getCusineType(Element offerChunk) {
		Element firstParaElement = offerChunk.select("p").first();
		String cusineType = "";
		if("cusine_hd".equalsIgnoreCase(firstParaElement.className())){
			String insideText = firstParaElement.text();
			if(insideText.contains("/")){
				String [] splittedString = insideText.split("/");
				cusineType = splittedString[1];
			}else{
				cusineType = insideText;
			}
		}
		return cusineType;
	}

	private String getOfferDescription(Element element) {
		Element descriptionElement = element.select(OFFER_DESCRIPTION_DIV_SELECTOR).first();
		String description = "";
		if(null != descriptionElement){
			description = descriptionElement.text();
		}
		return description;
	}


	private String getValidity(Element element) {
		String validity="";
		Element validityElem = element.select(OFFER_VALIDITY_PARA_SELECTOR).first();
		if(null != validityElem){
			validity=  validityElem.text();
		}
		return validity;
	}
}
