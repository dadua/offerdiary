package com.itech.offer.fetchers.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CommonHtmlParser {

	private final Document doc;

	public CommonHtmlParser(String html) {
		this.doc = Jsoup.parse(html);
	}

	protected Document getDoc() {
		return this.doc;
	}
}
