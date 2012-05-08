package com.itech.offer.fetchers.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import com.itech.parser.offer.model.Offer;

public class ICICIOfferParser extends CommonHttpParser{


	Logger logger = Logger.getLogger(ICICIOfferParser.class);
	public ICICIOfferParser(String html) {
		super(html);
	}

	public List<Offer> getAllOffers() {
		List<Offer> allOffers = new ArrayList<Offer>();
		List<Element> OfferHolders = getDoc().select("body>table>tbody>tr>td>table>tbody>tr>td>table");
		for(Element offerHolder : OfferHolders){
			Offer offer = processOffer(offerHolder);
			logger.info(offer);
			allOffers.add(offer);
		}
		return allOffers;
	}

	private Offer processOffer(Element offerHolder) {
		Offer offer = new Offer();
		String innerText =  offerHolder.text();
		offer.setDescription(innerText);
		offer.setMerchantWebAdress(getMerchantWebAdress(innerText));
		offer.setValidity(getValidity(innerText));
		return offer;
	}

	private String getValidity(String innerText) {
		Pattern pattern = Pattern.compile("Offer valid till([a-zA-Z0-9]|\\s)*, 2012");
		Matcher matcher = pattern.matcher(innerText);
		if (matcher.find())
		{
			return matcher.group();
		}
		return "";
	}

	private String getMerchantWebAdress(String innerText) {
		Pattern pattern = Pattern.compile("www\\.\\w*\\.(com|org|in|net)");
		Matcher matcher = pattern.matcher(innerText);
		if (matcher.find())
		{
			return matcher.group();
		}
		return "";
	}

}
