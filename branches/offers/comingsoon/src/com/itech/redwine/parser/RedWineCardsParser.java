package com.itech.redwine.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.itech.common.CommonFileUtilities;
import com.itech.common.util.UtilHttp;
import com.itech.offer.fetchers.parser.CitiOfferParser;

public class RedWineCardsParser {

	private static final String REDANAR_CARDDATA_JSON_FILE_OUT = "c:\\data\\cards-minimal.json";
	private final Logger logger = Logger.getLogger(CitiOfferParser.class);
	private final String redWinedomainName ="http://redanar.com";
	private String redWineBasePageUrl= redWinedomainName+"/mobile/badge/list?search=&page=";
	private final String redWineOffersBaseUrl = redWinedomainName +"/mobile/badge/offers/cardNumber?page=offerPageNumber";

	private List<RedWineCard> redWineCards = new ArrayList<RedWineCard>();

	public void printGetRedWineOffers(){
		parseRedWine();
		writeRedWineRecordsToFile();
	}

	private void writeRedWineRecordsToFile() {
		Gson gson = new Gson();
		String json = gson.toJson(redWineCards);
		System.out.println(CommonFileUtilities.appendDataToFile(REDANAR_CARDDATA_JSON_FILE_OUT, json, true));
	}

	private List<RedWineCard> readRedWineRecordsFromFile() {
		//CommonFileUtilities.readDataFromFile(REDANAR_CARDDATA_JSON_FILE);
		return null;
	}



	public void parseRedWine(){
		for(int currentPageNumber =0; currentPageNumber <= 165; currentPageNumber++){
			redWineCards.addAll(getRedWineCardsForPage(currentPageNumber));
		}
	}

	private List<RedWineCard> getRedWineCardsForPage(int currentPageNumber) {
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

	private void setOtherCardDetails(RedWineCard rWCard) {
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

	private void getAllOffersForCurrentCard(RedWineCard rWCard) {
		int cardNumber = getCardNumberFromCarUrl(rWCard);
		for(int pageNumber =1; pageNumber < 10; pageNumber++){
			rWCard.getOffers().addAll(getCardPageOffers(cardNumber, pageNumber));
		}
	}

	private List<RedWineOffer> getCardPageOffers(
			int cardNumber, int pageNumber) {
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
		RedWineCardsParser redWineCardsParser = new RedWineCardsParser();
		redWineCardsParser.printGetRedWineOffers();
		//String readDataFromFile = CommonFileUtilities.readDataFromFile("c:\\data\redanarcards.txt", false);
		//System.out.println(readDataFromFile);
	}

	private int getCardNumberFromCarUrl(RedWineCard rWCard) {
		int length = rWCard.getCardUrl().length();
		return Integer.parseInt(rWCard.getCardUrl().substring(length-2, length));
	}

	private int getCountOfOffers(RedWineCard rWCard) {
		String[] offerCount = rWCard.getCardElement().text().split("\\|");
		String offerCountString = offerCount[1];
		int count = Integer.parseInt(offerCountString.trim().substring(0, offerCountString.length()-8));
		return count;
	}

	public List<RedWineCard> getRedWineCards() {
		return redWineCards;
	}

	public void setRedWineCards(List<RedWineCard> redWineCards) {
		this.redWineCards = redWineCards;
	}

	public String getBasePageUrl() {
		return redWineBasePageUrl;
	}

	public void setBasePageUrl(String basePageUrl) {
		this.redWineBasePageUrl = basePageUrl;
	}

}
