package com.itech.offer.fetchers.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itech.parser.offer.model.Offer;


public class HsbcDiningOfferParser extends CommonHttpParser {

	public HsbcDiningOfferParser(String response) {
		super(response);
	}

	public List<String> getCityOfferLinks() {
		Elements elements = getDoc().select("area[href]");
		List<String> cityOfferLinks = new ArrayList<String>();
		for (Element element: elements) {
			String relativeLinkUrl = element.attr("href");
			cityOfferLinks.add(relativeLinkUrl);
		}
		return cityOfferLinks;
	}

	public List<Offer> getOffers() {
		//TODO: Too specific a selector?
		Elements elements = getDoc().select("html body table tbody tr td table tbody tr td table tbody tr td table tbody tr td table");
		List<Offer> offers = new ArrayList<Offer>();
		for (Element element : elements) {
			offers.add(getOffer(element));
		}
		return offers;
	}

	private Offer getOffer(Element element) {
		String merchantName = getMerchantName(element);
		String address = getAddress(element);
		String description = getDescription(element);
		Offer offer = new Offer();
		offer.setAddress(address);
		offer.setDescription(description);
		offer.setMerchantName(merchantName);
		return offer;
	}

	private String getDescription(Element element) {
		return element.select("tbody > tr:eq(1) > td").text();
	}

	private String getAddress(Element element) {
		return element.select("tbody > tr:eq(2) > td").text();
	}

	private String getMerchantName(Element element) {
		return element.select("tbody > tr:eq(0) > td img[alt]").attr("alt");
	}






}
