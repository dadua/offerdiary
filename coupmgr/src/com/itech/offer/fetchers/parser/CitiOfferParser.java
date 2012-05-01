package com.itech.offer.fetchers.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itech.offer.model.Offer;

public class CitiOfferParser {

	private final Document doc;

	public CitiOfferParser(String citiSpecialOfferHtml) {
		this.doc = Jsoup.parse(citiSpecialOfferHtml);
	}

	public List<String> getCityOfferLinks() {
		Elements elements = getDoc().select("div [id^=homect] div.more_btn a[href]");
		List<String> cityOfferLinks = new ArrayList<String>();
		for (Element element: elements) {
			String relativeLinkUrl = element.attr("href");
			cityOfferLinks.add(relativeLinkUrl);
		}
		return cityOfferLinks;
	}

	public Document getDoc() {
		return this.doc;
	}

	public List<Offer> getCuisineOffers() {
		Elements elements = getDoc().select(".cusine_frame");
		List<Offer> offers = new ArrayList<Offer>();
		for (Element element: elements) {
			String validity = getValidity(element);
			String description = getOfferDescription(element);
			Offer offer = new Offer(validity, description);
			offers.add(offer);
		}
		return offers;
	}

	private String getOfferDescription(Element element) {
		return element.select(".so-resholderright-col1").text();
	}

	private String getValidity(Element element) {
		return element.select(".validity_hd").text();
	}

}
