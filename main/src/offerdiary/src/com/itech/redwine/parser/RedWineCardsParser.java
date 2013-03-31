package com.itech.redwine.parser;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.CommonFileUtilities;
import com.itech.common.CommonUtilities;
import com.itech.common.util.UtilHttp;

public class RedWineCardsParser {
	public static final int MAX_PAGE_COUNT_FOR_OFFERS = 100;
	public static final int MAX_PAGE_COUNT_FOR_CARDS = 200;
	private final static Logger logger = Logger.getLogger(RedWineCardsParser.class);
	private static final String redWinedomainName ="http://redanar.com";
	private static final String redWineBasePageUrl= redWinedomainName+"/mobile/badge/list?search=&page=";
	private static final String redWineOffersBaseUrl = redWinedomainName +"/mobile/badge/offers/cardNumber?page=offerPageNumber";


	public static final String CARD_OFFER_FILE_NAME_PREFIX = "card_offers_";

	public static void printGetRedWineOffers(String outPutFile){
		List<RedWineCard> redWineForCards = parseRedWineForCards(MAX_PAGE_COUNT_FOR_CARDS);
		writeRecordsToFile(redWineForCards, outPutFile);
	}

	public static List<RedWineCard> readRedWineRecordsFromFile(String sourceFileName, boolean isAbsoluteUrl) {
		String cardDataJson = "";
		File targetFile = CommonFileUtilities.getResourceFileAsFile(sourceFileName, isAbsoluteUrl);
		String[] filesToProcess = new String[1];
		if (targetFile.isDirectory()) {
			filesToProcess = targetFile.list();
		} else {
			filesToProcess[0] = targetFile.getAbsolutePath();
		}

		List<RedWineCard> redWineCards = new ArrayList<RedWineCard>();

		for (String fileName : filesToProcess) {
			cardDataJson = CommonFileUtilities.getResourceFileAsString(fileName, isAbsoluteUrl);
			Gson gson = new Gson();
			Type offerCardJsonType = new TypeToken<List<RedWineCard>>() { }.getType();
			List<RedWineCard> redWineCardsFromFile = gson.fromJson(cardDataJson, offerCardJsonType);
			redWineCards.addAll(redWineCardsFromFile);
		}
		return redWineCards;
	}

	public static List<RedWineCard> readRedWineRecordsForCardId(String cardId, boolean isAbsoluteUrl) {
		String fileName = CARD_OFFER_FILE_NAME_PREFIX + cardId;
		return readRedWineRecordsFromFile(fileName, isAbsoluteUrl);
	}

	public static void writeRedWineCardsToSingleFile(List<RedWineCard> redWineForCards, String outPutFile){
		writeRecordsToFile(redWineForCards, outPutFile);
	}

	public static void writeRedWineCardsToSeperateFiles(List<RedWineCard> redWineCards, String baseDirectory){
		for (RedWineCard redWineCard : redWineCards) {
			writeRedWineCardsToSeperateFiles(redWineCard, baseDirectory);
		}
	}

	public static void writeRedWineCardsToSeperateFiles(RedWineCard redWineCard, String baseDirectory){
		String fileName = CARD_OFFER_FILE_NAME_PREFIX + redWineCard.getCardId();
		File outPutFile = new File(baseDirectory, fileName);
		writeRecordsToFile(redWineCard, outPutFile.getAbsolutePath());
	}


	private static void writeRecordsToFile(Object data, String outPutFile) {
		Gson gson = new Gson();
		String json = gson.toJson(data);
		System.out.println(CommonFileUtilities.appendDataToFile(outPutFile, json, true));
	}


	public static List<RedWineCard> parseRedWineForCards(int maxPageCount){
		List<RedWineCard> allCards =  new ArrayList<RedWineCard>();
		for(int currentPageNumber =0; currentPageNumber <= maxPageCount; currentPageNumber++){
			List<RedWineCard> redWineCardsForPage = getRedWineCardsForPage(currentPageNumber);
			if (redWineCardsForPage.size() == 0) {
				logger.info("number of pages processed by redwine - " + (currentPageNumber -1));
				break;
			}
			allCards.addAll(redWineCardsForPage);
		}
		return allCards;
	}

	private static List<RedWineCard> getRedWineCardsForPage(int currentPageNumber) {
		List<RedWineCard> redWineCards = new ArrayList<RedWineCard>();
		String currentPageUrl = getBasePageUrl() + currentPageNumber;
		String respHtml = UtilHttp.fetchHttpResponse(currentPageUrl);
		Document doc = Jsoup.parse(respHtml);
		List<Element> cardBlockElements = doc.select("li.clearfix");
		for(Element cardElement: cardBlockElements){
			Element cardLinkElement = cardElement.select("div p a").first();
			RedWineCard rWCard = new RedWineCard();
			rWCard.setCardName(cardLinkElement.text());
			rWCard.setCardUrl(cardLinkElement.attr("href"));
			rWCard.setCardElement(cardElement);
			setOtherCardDetails(rWCard);
			//getAllOffersForCurrentCard(rWCard);
			System.out.println(rWCard);
			redWineCards.add(rWCard);
		}
		return redWineCards;
	}

	/**
	 * Sets cardType, imgSource, offersOnCardCount by parsing the card detail page
	 * @param rWCard
	 */
	private static void setOtherCardDetails(RedWineCard rWCard) {
		int countOfOffers = getCountOfOffers(rWCard);
		rWCard.setOffersCount(countOfOffers);
		String cardOffersUrl = redWinedomainName+rWCard.getCardUrl();
		String respHtml = UtilHttp.fetchHttpResponse(cardOffersUrl);
		Document doc = Jsoup.parse(respHtml);
		String cardType = doc.getElementsContainingOwnText("Card Type").first().nextElementSibling().text();
		rWCard.setCardType(cardType);
		String imageSrc =  doc.getElementsContainingOwnText("Card Image").first().nextElementSibling().child(0).attr("src");
		rWCard.setImageUrl(imageSrc);
	}

	private static int getCountOfOffers(RedWineCard rWCard) {
		String[] offerCount = rWCard.getCardElement().text().split("\\|");
		String offerCountString = offerCount[1];
		int count = Integer.parseInt(offerCountString.trim().substring(0, offerCountString.length()-8));
		return count;
	}

	public static List<RedWineOffer> getAllOffersForCard(String cardNumber, int maxOfferCount) {
		List<RedWineOffer> offers = new ArrayList<RedWineOffer>();
		if (CommonUtilities.isNotEmpty(cardNumber)) {
			for(int pageNumber =1; pageNumber < MAX_PAGE_COUNT_FOR_OFFERS; pageNumber++){
				if (offers.size() >= maxOfferCount) {
					break;
				}
				List<RedWineOffer> offersForPage = getCardOffersForPage(cardNumber, pageNumber);
				if (offersForPage.size() == 0) {
					break;
				}
				offers.addAll(offersForPage);
			}
		}
		return offers;
	}

	private static List<RedWineOffer> getCardOffersForPage(String cardNumber, int pageNumber) {
		List<RedWineOffer> redWineOffers = new ArrayList<RedWineOffer>();
		String offersUrl = redWineOffersBaseUrl.replace("cardNumber", cardNumber+"");
		offersUrl = offersUrl.replace("offerPageNumber", pageNumber+"");
		String respHtml = UtilHttp.fetchHttpResponse(offersUrl);
		if(null != respHtml){
			Document doc = Jsoup.parse(respHtml);
			List<Element> offerElements = doc.select("li.clearfix");
			for(Element offerElement: offerElements){
				RedWineOffer rOffer = new RedWineOffer();
				rOffer.setMerchantName(offerElement.child(0).text());
				rOffer.setDescription(offerElement.child(1).text());
				rOffer.setExpiryDate(offerElement.getElementsByTag("dt").get(0).text());
				redWineOffers.add(rOffer);
			}
		}
		return redWineOffers;
	}



	public static void main(String[] args){

	}

	private static String getBasePageUrl() {
		return redWineBasePageUrl;
	}

}
