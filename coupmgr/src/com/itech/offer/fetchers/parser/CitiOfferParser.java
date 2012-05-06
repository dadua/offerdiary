package com.itech.offer.fetchers.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itech.offer.model.Offer;

public class CitiOfferParser extends CommonHttpParser {

	private final Logger logger = Logger.getLogger(CitiOfferParser.class);
	public CitiOfferParser(String citiOfferHtml) {
		super(citiOfferHtml);
	}

	/**
	 * 
	 * @return list of links containing offers per cities
	 */
	public List<String> getCityOfferLinks() {
		Elements elements = getDoc().select("div [id^=homect] div.more_btn a[href]");
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
	public List<Offer> getCuisineOffers() {
		String city = getCity();
		Elements offerChunkHolder = getDoc().select(".cusine_frame");
		List<Offer> citiCuisineoffers = new ArrayList<Offer>();
		for (Element offerChunk: offerChunkHolder) {
			getAllOfferForCurrentOfferChunk(city,offerChunk, citiCuisineoffers);
		}
		return citiCuisineoffers;
	}


	private String getCity() {
		Element offerHeadingCityElement = getDoc().select("p.offer_hd span").first();
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
			List<Offer> citiCuisineoffers) {

		String cusineType=  getCusineType(offerChunk);
		List<Element> allElementInsideOfferChunk = offerChunk.children();

		for(int i=0; i < allElementInsideOfferChunk.size() ; i++){

			Element currentElement = allElementInsideOfferChunk.get(i);
			String currentTagName = currentElement.tagName();

			if(TagUtil.DIV.equalsIgnoreCase(currentTagName)){
				if("so-resholder".equalsIgnoreCase(currentElement.className())){
					Offer offer = processNewOffer(currentElement);
					offer.setLocation(city);
					offer.setConsumptionType("Cuisine");
					offer.setConsumptionSubType(cusineType);
					debugOffer(offer);
					citiCuisineoffers.add(offer);
				}
			}else if(TagUtil.PARAGRAPGH.equalsIgnoreCase(currentTagName)){
				if("cusine_hd".equalsIgnoreCase(currentElement.className())){
					continue;
				}
				Element offerHolder = allElementInsideOfferChunk.get(++i);
				if(TagUtil.DIV.equalsIgnoreCase(offerHolder.tagName()) && "so-resholder".equalsIgnoreCase(offerHolder.className())){
					Offer offer = processNewOffer(currentElement, offerHolder);
					offer.setLocation(city);
					offer.setConsumptionType("Cuisine");
					offer.setConsumptionSubType(cusineType);
					debugOffer(offer);
					citiCuisineoffers.add(offer);
				}
			}
		}
	}

	private void debugOffer(Offer offer) {
		logger.debug(offer);
		logger.debug("----------------------------------------------------------------------------");
	}

	/*where an offer is composite entity*/
	private Offer processNewOffer(Element validityElement, Element offerHolder) {

		Offer offer = new Offer();
		String merchantName = getMerchantName(offerHolder);
		String validity = validityElement.text();
		String description = getOfferDescription(offerHolder);
		String imageSrc = getOffferImage(offerHolder);
		offer.setMerchantName(merchantName);
		offer.setValidity(validity);
		offer.setDescription(description);
		offer.setImageSrc(imageSrc);
		return offer;
	}

	/* where an offer block is individual entity*/
	private Offer processNewOffer(Element offerHolder) {
		Offer offer = new Offer();
		String merchantName = getMerchantName(offerHolder);
		String validity = getValidity(offerHolder);
		String description = getOfferDescription(offerHolder);
		String imageSrc = getOffferImage(offerHolder);
		offer.setMerchantName(merchantName);
		offer.setValidity(validity);
		offer.setDescription(description);
		offer.setImageSrc(imageSrc);
		return offer;
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
		Element descriptionElement = element.select("div[class^=so-resholderright-col]").first();
		String description = "";
		if(null != descriptionElement){
			description = descriptionElement.text();
		}
		return description;
	}


	private String getValidity(Element element) {
		String validity="";
		Element validityElem = element.select(".validity_hd").first();
		if(null != validityElem){
			validity=  validityElem.text();
		}
		return validity;
	}
}
