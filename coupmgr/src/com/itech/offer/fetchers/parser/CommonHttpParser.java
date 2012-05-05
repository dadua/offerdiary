package com.itech.offer.fetchers.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CommonHttpParser {

	private final Document doc;

	public CommonHttpParser(String html) {
		this.doc = Jsoup.parse(html);
	}

	protected Document getDoc() {
		return this.doc;
	}
}
