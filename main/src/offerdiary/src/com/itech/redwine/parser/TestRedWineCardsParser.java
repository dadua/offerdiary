package com.itech.redwine.parser;

import java.util.ArrayList;
import java.util.List;

public class TestRedWineCardsParser {

	private static final int MAX_OFFERS_TOB_FETCHED = 1000;
	private static final int MAX_CARDS_TO_BE_PROCESSED_FOR_OFFERS = 200;
	private static final String REDANAR_CARDDATA_JSON_FILE_OUT = "c:\\data\\cards-minimal.json";
	private static final String ALL_REDANAR_CARDDATA_JSON_FILE_OUT = "c:\\data\\all-cards-minimal.json";
	private static final String REDANAR_CARDS_WITH_OFFERS_BASE_DIR = "c:\\data\\offers";

	public static void main(String[] args){
		getAllOffersForFilteredCardsAndWriteToFiles();
	}

	private static void getAllOffersForFilteredCardsAndWriteToFiles() {
		List<RedWineCard> redWineCards = RedWineCardsParser.readRedWineRecordsFromFile(REDANAR_CARDDATA_JSON_FILE_OUT, true);
		for (RedWineCard redWineCard : redWineCards) {
			String cardNumber = RedWineParserUtil.getIdFromCard(redWineCard.getCardUrl());
			List<RedWineOffer> allOffersForCard = RedWineCardsParser.getAllOffersForCard(cardNumber, MAX_OFFERS_TOB_FETCHED);
			redWineCard.setOffers(allOffersForCard);
			RedWineCardsParser.writeRedWineCardsToSeperateFiles(redWineCard, REDANAR_CARDS_WITH_OFFERS_BASE_DIR);
		}

	}

	private static void getAllCardsFromRedWineAndWriteToFile() {
		List<RedWineCard> redWineCardsWithNoOfferFilled = RedWineCardsParser.parseRedWineForCards(RedWineCardsParser.MAX_PAGE_COUNT_FOR_CARDS);
		List<RedWineCard> filteredCards = new ArrayList<RedWineCard>();
		for (RedWineCard redWineCard : redWineCardsWithNoOfferFilled) {
			if ("Credit".equalsIgnoreCase(redWineCard.getCardType()) || "Debit".equalsIgnoreCase(redWineCard.getCardType())) {
				filteredCards.add(redWineCard);
			}
		}
		RedWineCardsParser.writeRedWineCardsToSingleFile(redWineCardsWithNoOfferFilled,  ALL_REDANAR_CARDDATA_JSON_FILE_OUT);
		RedWineCardsParser.writeRedWineCardsToSingleFile(filteredCards, REDANAR_CARDDATA_JSON_FILE_OUT);

	}

	public static void getAllCardsWithOfferFilledForPage(int pageNo) {
		List<RedWineCard> redWineCardsWithNoOfferFilled = RedWineCardsParser.parseRedWineForCards(RedWineCardsParser.MAX_PAGE_COUNT_FOR_CARDS);
		for (RedWineCard redWineCard : redWineCardsWithNoOfferFilled) {
			String cardNumber = RedWineParserUtil.getIdFromCard(redWineCard.getCardUrl());
			List<RedWineOffer> allOffersForCard = RedWineCardsParser.getAllOffersForCard(cardNumber, 1000);
			redWineCard.setOffers(allOffersForCard);
		}


	}

	public static void writeRedWineCardsToSingleFile(List<RedWineCard> redWineForCards, String outPutFile){
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
	}
}
