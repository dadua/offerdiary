package com.itech.redwine.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itech.common.CommonUtilities;
import com.itech.offer.model.OfferCard.CardType;
import com.itech.offer.model.OfferCard.PaymentChannel;

public class RedWineParserUtil {

	/**
	 * http://redanar.com/mobile/badge/view/88
	 * here id is 88
	 * */
	public static String getIdFromCard(String cardUrl) {
		if (CommonUtilities.isNullOrEmpty(cardUrl)) {
			return null;
		}
		String IdSelectorRegEx = ".*/(\\d+)$";
		Pattern pattern = Pattern.compile(IdSelectorRegEx);
		Matcher matcher = pattern.matcher(cardUrl);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}

	public static PaymentChannel getPaymentChannelFor(String cardName) {
		if (CommonUtilities.isNullOrEmpty(cardName)) {
			return PaymentChannel.UNKNOWN;
		}
		cardName = cardName.toLowerCase();

		if (cardName.contains(" visa")) {
			return PaymentChannel.VISA;
		}

		if (cardName.contains(" maestro")) {
			return PaymentChannel.MAESTRO;
		}

		if (cardName.contains(" mastercard")) {
			return PaymentChannel.MASTER_CARD;
		}

		return PaymentChannel.UNKNOWN;
	}


	public static CardType getCardTypeFor(String cardTypeKey, String cardName) {

		if (CommonUtilities.isNotEmpty(cardTypeKey)) {
			cardTypeKey = cardTypeKey.toLowerCase();
			if ("credit".equalsIgnoreCase(cardTypeKey)) {
				return CardType.CREDIT;
			}

			if ("debit".equalsIgnoreCase(cardTypeKey)) {
				return CardType.DEBIT;
			}
		}

		//check in card name
		if (CommonUtilities.isNotEmpty(cardName)) {
			cardName = cardName.toLowerCase();
			if (cardName.contains(" credit ")) {
				return CardType.CREDIT;
			}

			if (cardName.contains(" debit ")) {
				return CardType.DEBIT;
			}
		}

		return CardType.UNKNOWN;
	}

}
