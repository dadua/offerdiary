package com.itech.redwine.parser;

import com.itech.common.test.CommonTestUtil;
import com.itech.offer.model.OfferCard.CardType;
import com.itech.offer.model.OfferCard.PaymentChannel;

public class TestRedWineParserUtil extends CommonTestUtil{

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub

	}

	public void testGetIdFromCard_validUrl() {
		String cardUrl = "http://redanar.com/mobile/badge/view/88";
		String actualResultId = RedWineParserUtil.getIdFromCard(cardUrl);
		String expectedId = "88";
		assertEquals(expectedId, actualResultId);
	}

	public void testGetIdFromCard_invalidUrl() {
		String cardUrl = "http://redanar.com/mobile/badge/view/88/add";
		String actualResultId = RedWineParserUtil.getIdFromCard(cardUrl);
		String expectedId = null;
		assertEquals(expectedId, actualResultId);
	}

	public void testGetIdFromCard_nullUrl() {
		String cardUrl = null;
		String actualResultId = RedWineParserUtil.getIdFromCard(cardUrl);
		String expectedId = null;
		assertEquals(expectedId, actualResultId);
	}

	public void testgGetPaymentChannelFor_valid() {
		String card1Name = "State Bank Cash Plus Maestro Debit Card";
		String card2Name = "HSBC Platinum Visa Credit Card";
		String card3Name = "BOBCARD Gold MasterCard Credit Card";
		String card4Name = "BOBCARD Gold Express Credit Card";
		String card5Name = null;

		PaymentChannel expPaymentChannelForCard1 = PaymentChannel.MAESTRO;
		PaymentChannel expPaymentChannelForCard2 = PaymentChannel.VISA;
		PaymentChannel expPaymentChannelForCard3 = PaymentChannel.MASTER_CARD;
		PaymentChannel expPaymentChannelForCard4 = PaymentChannel.UNKNOWN;
		PaymentChannel expPaymentChannelForCard5 = PaymentChannel.UNKNOWN;

		PaymentChannel actualPaymentChannelForCard1 = RedWineParserUtil.getPaymentChannelFor(card1Name);
		PaymentChannel actualPaymentChannelForCard2 = RedWineParserUtil.getPaymentChannelFor(card2Name);
		PaymentChannel actualPaymentChannelForCard3 = RedWineParserUtil.getPaymentChannelFor(card3Name);
		PaymentChannel actualPaymentChannelForCard4 = RedWineParserUtil.getPaymentChannelFor(card4Name);
		PaymentChannel actualPaymentChannelForCard5 = RedWineParserUtil.getPaymentChannelFor(card5Name);

		assertEquals(expPaymentChannelForCard1, actualPaymentChannelForCard1);
		assertEquals(expPaymentChannelForCard2, actualPaymentChannelForCard2);
		assertEquals(expPaymentChannelForCard3, actualPaymentChannelForCard3);
		assertEquals(expPaymentChannelForCard4, actualPaymentChannelForCard4);
		assertEquals(expPaymentChannelForCard5, actualPaymentChannelForCard5);
	}


	public void testGetCardTypeFor() {
		String card1Name = "State Bank Cash Plus Maestro Card";
		String card2Name = "HSBC Platinum Visa Card";
		String card3Name = "BOBCARD Gold MasterCard credit Card";
		String card4Name = "BOBCARD Gold Express debit Card";

		String card1Type = "debit";
		String card2Type = "credit";
		String card3Type = "";
		String card4Type = null;

		CardType expectedCardTypeForCard1 = CardType.DEBIT;
		CardType expectedCardTypeForCard2 = CardType.CREDIT;
		CardType expectedCardTypeForCard3 = CardType.CREDIT;
		CardType expectedCardTypeForCard4 = CardType.DEBIT;

		CardType actualCardTypeForCard1 = RedWineParserUtil.getCardTypeFor(card1Type, card1Name);
		CardType actualCardTypeForCard2 = RedWineParserUtil.getCardTypeFor(card2Type, card2Name);
		CardType actualCardTypeForCard3 = RedWineParserUtil.getCardTypeFor(card3Type, card3Name);
		CardType actualCardTypeForCard4 = RedWineParserUtil.getCardTypeFor(card4Type, card4Name);

		assertEquals(expectedCardTypeForCard1, actualCardTypeForCard1);
		assertEquals(expectedCardTypeForCard2, actualCardTypeForCard2);
		assertEquals(expectedCardTypeForCard3, actualCardTypeForCard3);
		assertEquals(expectedCardTypeForCard4, actualCardTypeForCard4);
	}

	public void testGetCardTypeFor_null_checks() {
		String card1Name = null;
		String card2Name = "";
		String card3Name = "";
		String card4Name = null;

		String card1Type = "debit";
		String card2Type = null;
		String card3Type = "";
		String card4Type = null;

		CardType expectedCardTypeForCard1 = CardType.DEBIT;
		CardType expectedCardTypeForCard2 = CardType.UNKNOWN;
		CardType expectedCardTypeForCard3 = CardType.UNKNOWN;
		CardType expectedCardTypeForCard4 = CardType.UNKNOWN;

		CardType actualCardTypeForCard1 = RedWineParserUtil.getCardTypeFor(card1Type, card1Name);
		CardType actualCardTypeForCard2 = RedWineParserUtil.getCardTypeFor(card2Type, card2Name);
		CardType actualCardTypeForCard3 = RedWineParserUtil.getCardTypeFor(card3Type, card3Name);
		CardType actualCardTypeForCard4 = RedWineParserUtil.getCardTypeFor(card4Type, card4Name);

		assertEquals(expectedCardTypeForCard1, actualCardTypeForCard1);
		assertEquals(expectedCardTypeForCard2, actualCardTypeForCard2);
		assertEquals(expectedCardTypeForCard3, actualCardTypeForCard3);
		assertEquals(expectedCardTypeForCard4, actualCardTypeForCard4);
	}


}
