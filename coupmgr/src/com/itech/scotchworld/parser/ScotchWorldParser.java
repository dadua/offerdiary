package com.itech.scotchworld.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.itech.common.CommonFileUtilities;
import com.itech.common.util.UtilHttp;

public class ScotchWorldParser {

	private static final String SCOTCH_WORLD_STOREDATA_JSON_FILE_OUT = "c:\\data\\stores-minimal.json";
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
		Map<String, String> storeImagenameURLMap = new HashMap<String, String>();
		for(ScotchWorldStore scotchWorldStore: getScotchStores()){
			storeImagenameURLMap.put(scotchWorldStore.getStoreName(), scotchWorldStore.getImageSrc());
		}
		CommonFileUtilities.writeImageToImageRepoFolder(storeImagenameURLMap);
	}

	public void parseScotchWorld(){
		String respHtml = UtilHttp.fetchHttpResponse(currentPageUrl);
		Document doc = Jsoup.parse(respHtml);
		Element storeCollection = doc.select("ul#storeCollection").first();
		List<Element> storeElements = storeCollection.select("li");
		int counter=0;
		for(Element element : storeElements){
			ScotchWorldStore  scotchWorldStore = getScotchWorldStore(element);
			getScotchStores().add(scotchWorldStore);
			counter++;
			//			if(counter > 1){
			//				break;
			//			}
		}
	}

	private ScotchWorldStore getScotchWorldStore(Element element) {
		ScotchWorldStore scotchWorldStore = new ScotchWorldStore();
		Element nameEl =  element.select("img").first();
		if(null == nameEl){
			return scotchWorldStore;
		}
		String storeName = nameEl.attr("alt");
		String imageSrc = element.select("img").first().attr("src");
		String linkToStoreDetails = element.select("a").first().attr("href");
		String respHtml = UtilHttp.fetchHttpResponse(linkToStoreDetails);
		Document doc = Jsoup.parse(respHtml);
		String storeDesc = doc.select("span#web_desc").first().text();
		String storeURL  =  UtilHttp.getRedirectedURLLocation(doc.select("span#web_desc").first().parent().select("a").first().attr("href"));
		scotchWorldStore.setDescription(storeDesc);
		scotchWorldStore.setImageSrc(imageSrc);
		scotchWorldStore.setStoreName(storeName);
		scotchWorldStore.setStoreURL(storeURL);
		System.out.println(scotchWorldStore);
		return scotchWorldStore;
	}

	public static void main(String[] args){
		ScotchWorldParser scotchWorldParser  = new ScotchWorldParser();
		scotchWorldParser.printScotchWorldStores();
	}

	public List<ScotchWorldStore> getScotchStores() {
		return scotchStores;
	}

	public void setScotchStores(List<ScotchWorldStore> scotchStores) {
		this.scotchStores = scotchStores;
	}
}
