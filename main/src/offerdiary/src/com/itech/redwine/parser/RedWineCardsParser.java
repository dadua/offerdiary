package com.itech.redwine.parser;

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
import com.itech.offer.fetchers.parser.CitiOfferParser;

public class RedWineCardsParser {

	private static final int MAX_CARDS_TO_BE_PROCESSED_FOR_OFFERS = 200;
	private static final int MAX_PAGE_COUNT_FOR_OFFERS = 40;
	private static final int MAX_PAGE_COUNT_FOR_CARDS = 165;
	private static final String REDANAR_CARDDATA_JSON_FILE_OUT = "c:\\data\\cards-minimal.json";
	private static final String REDANAR_CARDDATA_WITH_OFFERS_JSON_FILE_OUT = "c:\\data\\cards-with-offers.json";
	private final static Logger logger = Logger.getLogger(CitiOfferParser.class);
	private static final String redWinedomainName ="http://redanar.com";
	private static final String redWineBasePageUrl= redWinedomainName+"/mobile/badge/list?search=&page=";
	private static final String redWineOffersBaseUrl = redWinedomainName +"/mobile/badge/offers/cardNumber?page=offerPageNumber";


	public static void printGetRedWineOffers(String outPutFile){
		List<RedWineCard> redWineForCards = parseRedWineForCards(MAX_PAGE_COUNT_FOR_CARDS);
		writeRecordsToFile(redWineForCards, outPutFile);
	}
	public static List<RedWineCard> readRedWineRecordsFromFile(String sourceFileName) {
		String cardDataJson = CommonFileUtilities.getResourceFileAsString(sourceFileName);
		Gson gson = new Gson();
		Type offerCardJsonType = new TypeToken<List<RedWineCard>>() { }.getType();
		List<RedWineCard> redWineCards = gson.fromJson(cardDataJson, offerCardJsonType);
		return redWineCards;
	}

	public static void writeRedWineCardsWithOffers(List<RedWineCard> redWineForCards, String outPutFile){
		int cardProcessed = 0;
		for (RedWineCard redWineCard : redWineForCards) {
			if (cardProcessed >= MAX_CARDS_TO_BE_PROCESSED_FOR_OFFERS) {
				break;
			}
			String cardNumber = RedWineParserUtil.getIdFromCard(redWineCard.getCardUrl());
			List<RedWineOffer> allOffersForCard = RedWineCardsParser.getAllOffersForCard(cardNumber, 10);
			redWineCard.setOffers(allOffersForCard);
			cardProcessed++;
		}
		writeRecordsToFile(redWineForCards, outPutFile);
	}

	public static List<RedWineCard> readRedWineCardsWithOfferFile(String sourceFileName) {
		String cardDataJson = CommonFileUtilities.getResourceFileAsString(sourceFileName);
		Gson gson = new Gson();
		Type offerCardJsonType = new TypeToken<List<RedWineCard>>() { }.getType();
		List<RedWineCard> redWineCards = gson.fromJson(cardDataJson, offerCardJsonType);
		return redWineCards;
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
		String currentPageUrl = getBasePageUrl()+currentPageNumber;
		String respHtml = UtilHttp.fetchHttpResponse(currentPageUrl);
		Document doc = Jsoup.parse(respHtml);
		List<Element> cardBlockElements = doc.select("li.clearfix");
		for(Element cardElement: cardBlockElements){
			Element cardLinkElement = cardElement.select("div p a").first();
			RedWineCard rWCard = new RedWineCard();
			rWCard.setCardName(cardLinkElement.text());
			rWCard.setCardUrl(cardLinkElement.attr("href"));
			rWCard.setCardElement(cardElement);
			//setOtherCardDetails(rWCard);
			//getAllOffersForCurrentCard(rWCard);
			System.out.println(rWCard);
			redWineCards.add(rWCard);
		}
		return redWineCards;
	}

	public static void setOtherCardDetails(RedWineCard rWCard) {
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
				List<RedWineOffer> offersForPage = getCardPageOffers(cardNumber, pageNumber);
				if (offersForPage.size() == 0) {
					break;
				}
				offers.addAll(offersForPage);
			}
		}
		return offers;
	}

	private static List<RedWineOffer> getCardPageOffers(String cardNumber, int pageNumber) {
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
				redWineOffers.add(rOffer);
			}
		}
		return redWineOffers;
	}

	public static void main(String[] args){
		//RedWineCardsParser.printGetRedWineOffers(REDANAR_CARDDATA_JSON_FILE_OUT);
		//String readDataFromFile = CommonFileUtilities.readDataFromFile("c:\\data\redanarcards.txt", false);
		//System.out.println(readDataFromFile);
		//		List<RedWineCard> redwineCards = RedWineCardsParser.readRedWineRecordsFromFile("resources\\redanar\\cards-minimal.json");
		//		List<RedWineCard> iciciCards = filterCardsWithName("ICICI",  redwineCards);
		//		RedWineCardsParser.writeRedWineCardsWithOffers(iciciCards,
		//				RedWineCardsParser.REDANAR_CARDDATA_WITH_OFFERS_JSON_FILE_OUT);

		String cardsWithOffersSourceFileName = "resources\\redanar\\cards-with-offers.json";
		List<RedWineCard> cardsWithOffer = readRedWineCardsWithOfferFile(cardsWithOffersSourceFileName);
	}



	private static List<RedWineCard> filterCardsWithName(String name, List<RedWineCard> cards) {
		List<RedWineCard> filteredCards = new ArrayList<RedWineCard>();
		for (RedWineCard redWineCard : cards) {
			if (redWineCard.getCardName().indexOf(name) != -1) {
				filteredCards.add(redWineCard);
			}
		}
		return filteredCards;
	}
	public static String getBasePageUrl() {
		return redWineBasePageUrl;
	}

}
