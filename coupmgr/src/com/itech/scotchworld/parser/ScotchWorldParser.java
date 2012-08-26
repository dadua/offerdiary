package com.itech.scotchworld.parser;

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
import com.itech.redwine.parser.RedWineCardsParser;

public class ScotchWorldParser {

	private static final String SCOTCH_WORLD_STOREDATA_JSON_FILE_OUT = "c:\\data\\stores-minimal.json";
	private final Logger logger = Logger.getLogger(CitiOfferParser.class);
	private final String scotchWOrlddomainName ="http://www.coupondunia.in";


	private List<ScotchWorldStore> scotchStores = new ArrayList<ScotchWorldStore>();
	private final String currentPageUrl= "http://www.coupondunia.in/stores";

	public void printScotchWorldStores(){
		parseScotchWorld();
		writeScotchWorldToFile();
	}

	private void writeScotchWorldToFile() {
		Gson gson = new Gson();
		String json = gson.toJson(getScotchStores());
		System.out.println(CommonFileUtilities.appendDataToFile(SCOTCH_WORLD_STOREDATA_JSON_FILE_OUT, json, true));
	}

	public void parseScotchWorld(){
		List<ScotchWorldStore> scotchStores = new ArrayList<ScotchWorldStore>();
		String respHtml = UtilHttp.fetchHttpResponse(currentPageUrl);
		Document doc = Jsoup.parse(respHtml);
		Element storeCollection = doc.select("ul#storeCollection").first();
		List<Element> storeElements = storeCollection.select("li");
		for(Element element : storeElements){
			ScotchWorldStore  scotchWorldStore = getScotchWorldStore(element);
			scotchStores.add(scotchWorldStore);
		}
		getScotchStores().addAll(scotchStores);
	}

	private ScotchWorldStore getScotchWorldStore(Element element) {
		String storeName =  element.select("img").first().attr("alt");
		String imageSrc = element.select("img").first().attr("src");
		String linkToStoreDetails = element.select("a").first().attr("href");
		String respHtml = UtilHttp.fetchHttpResponse(linkToStoreDetails);
		Document doc = Jsoup.parse(respHtml);

	}

	public static void main(String[] args){
		RedWineCardsParser redWineCardsParser = new RedWineCardsParser();
		redWineCardsParser.printGetRedWineOffers();
		//String readDataFromFile = CommonFileUtilities.readDataFromFile("c:\\data\redanarcards.txt", false);
		//System.out.println(readDataFromFile);
	}

	public List<ScotchWorldStore> getScotchStores() {
		return scotchStores;
	}

	public void setScotchStores(List<ScotchWorldStore> scotchStores) {
		this.scotchStores = scotchStores;
	}
}
