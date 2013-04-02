package com.itech.scotchworld.parser;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.CommonFileUtilities;
import com.itech.common.CommonUtilities;
import com.itech.common.util.UtilHttp;

public class ScotchWorldParser {

	private static final String SCOTCH_WORLD_STOREDATA_JSON_FILE_OUT = "c:\\data\\stores-minimal.json";
	private static List<ScotchWorldStore> scotchStores = new ArrayList<ScotchWorldStore>();
	private static String STOR_IMAGE_TARGET_FOLDER = "c:\\data\\storeImages";
	private static String STORE_IMAGE_LOCAL_SOURCE_FOLDER = "c:\\data\\stores";
	private static String STORE_IMAGE_LOCAL_SOURCE_URI = "file:///c:/data/stores";
	private final String currentPageUrl= "http://www.coupondunia.in/stores";

	public void printScotchWorldStores(){
		//parseScotchWorld();
		//writeScotchWorldToFile();
		//renameImageFiles();
		loadStoresFromFilesAndGetImages();
	}

	private void loadStoresFromFilesAndGetImages() {
		String scotchWorldStoresJSON = CommonFileUtilities.getResourceFileAsString(SCOTCH_WORLD_STOREDATA_JSON_FILE_OUT, true);
		Gson gson = new Gson();
		Type scotchWorldStoreType = new TypeToken<List<ScotchWorldStore>>() { }.getType();
		List<ScotchWorldStore> scotchWorldStores = gson.fromJson(scotchWorldStoresJSON, scotchWorldStoreType);
		ScotchWorldParser.scotchStores = scotchWorldStores;
		Map<String, String> storeImagenameURLMap = new HashMap<String, String>();
		for(ScotchWorldStore scotchWorldStore: getScotchStores()){
			String orgImagePath = scotchWorldStore.getImageSrc();
			if (CommonUtilities.isNotEmpty(orgImagePath)) {
				String orgImageName = orgImagePath.substring(orgImagePath.lastIndexOf("/") + 1);
				String newImageName = getCleanImageFileName(orgImageName);
				String newImagePath = STORE_IMAGE_LOCAL_SOURCE_URI + "/" + newImageName;
				File file = new File(STORE_IMAGE_LOCAL_SOURCE_FOLDER + "\\" + newImageName);
				if (!file.exists()) {
					continue;
				}
				storeImagenameURLMap.put(scotchWorldStore.getStoreName(), newImagePath);
			}
		}
		CommonFileUtilities.writeImageToImageRepoFolder(storeImagenameURLMap, STOR_IMAGE_TARGET_FOLDER);

	}

	public void renameImageFiles() {
		File imageDir = new File(STORE_IMAGE_LOCAL_SOURCE_FOLDER);
		File[] files = imageDir.listFiles();
		for (File imageFile : files) {
			String orgName = imageFile.getName();
			String newName = getCleanImageFileName(orgName);
			imageFile.renameTo(new File(imageDir, newName));
		}
	}

	private String getCleanImageFileName(String orgName) {
		// logo_0a9c0e0_444.jpg
		String[] nameParts = orgName.split("_");
		if (nameParts.length < 3) {
			return orgName;
		}
		return nameParts[0] + "_" + nameParts[2];
	}



	private void writeScotchWorldToFile() {
		Gson gson = new Gson();
		String json = gson.toJson(getScotchStores());
		System.out.println(CommonFileUtilities.appendDataToFile(SCOTCH_WORLD_STOREDATA_JSON_FILE_OUT, json, true));
		Map<String, String> storeImagenameURLMap = new HashMap<String, String>();
		for(ScotchWorldStore scotchWorldStore: getScotchStores()){
			storeImagenameURLMap.put(scotchWorldStore.getStoreName(), scotchWorldStore.getImageSrc());
		}
		CommonFileUtilities.writeImageToImageRepoFolder(storeImagenameURLMap, STOR_IMAGE_TARGET_FOLDER);
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

	public static ScotchWorldStore getScotchWorldStore(Element element) {
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

	public static synchronized void addStore(ScotchWorldStore scotchWorldStore) {
		ScotchWorldParser.scotchStores.add(scotchWorldStore);
	}

}

class ScotchWorldStoreParser implements Runnable{

	private final List<Element> elements;

	public ScotchWorldStoreParser(List<Element> elements) {
		this.elements = elements;

	}

	@Override
	public void run() {
		for(Element element : elements){
			ScotchWorldStore  scotchWorldStore = ScotchWorldParser.getScotchWorldStore(element);
			ScotchWorldParser.addStore(scotchWorldStore);
		}

	}

}

class ScotchWorldImageParser implements Runnable{
	private final List<ScotchWorldStore> scotchWorldStores;

	public ScotchWorldImageParser(List<ScotchWorldStore> scotchWorldStores) {
		this.scotchWorldStores = scotchWorldStores;

	}

	@Override
	public void run() {
		Map<String, String> storeImagenameURLMap = new HashMap<String, String>();
		for(ScotchWorldStore scotchWorldStore: scotchWorldStores){
			storeImagenameURLMap.put(scotchWorldStore.getStoreName(), scotchWorldStore.getImageSrc());
		}
		//CommonFileUtilities.writeImageToImageRepoFolder(storeImagenameURLMap);
	}

}


