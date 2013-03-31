package com.itech.scotchworld.parser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.CommonFileUtilities;
import com.itech.common.CommonUtilities;
import com.itech.offer.job.BaseItechJob;
import com.itech.offer.job.JobEventListener;
import com.itech.offer.manager.VendorManager;
import com.itech.offer.model.Vendor;
import com.itech.offer.model.enums.VendorType;
import com.itech.user.manager.UserManager;

@Component
@Scope("prototype")
public class VendorSyncJob extends BaseItechJob{

	private static final String SITE_URL_PREFIX = "www.";
	private static final String VENDOR_JSON_FILE_PATH = "resources\\couponduniya\\stores-minimal.json";
	private static final String VENDOR_TO_AFFILIATE_URL_MAPPINGS = "resources\\couponduniya\\vendor_to_aff_urls.properties";

	private static final String AFFILIATE_URLS_TO_BE_FILTERED = "resources\\couponduniya\\affiliate_site_urls.properties";

	@Autowired
	private VendorManager vendorManager;

	private UserManager userManager;

	private static final Logger logger = Logger.getLogger(VendorSyncJob.class);

	private String[] affiliateUrlsToIgnore;
	private final Map<String, String> vendorToAffUrlMap = new HashMap<String, String>();

	private  void syncVendors() {
		loadInitialData();
		String vendorJsonData = CommonFileUtilities.getResourceFileAsString(VENDOR_JSON_FILE_PATH);

		Gson gson = new Gson();
		Type vendorJsonType = new TypeToken<List<ScotchWorldStore>>() { }.getType();
		List<ScotchWorldStore> scotchDuniyaStores = gson.fromJson(vendorJsonData, vendorJsonType);
		for ( ScotchWorldStore scotchWorldStore : scotchDuniyaStores) {
			if (scotchWorldStore.getStoreName() == null) {
				continue;
			}
			checkAndUpdateVendorUrl(scotchWorldStore);
			updateAffiliateURL(scotchWorldStore);
			Vendor vendor = getVendorFrom(scotchWorldStore);
			logger.debug("Processing for vendor: " + vendor);
			Vendor existingVendor = getVendorManager().getVendorForVendorDetail(vendor);
			logger.debug("Processing for vendor: " + vendor + " with existing vendor: " + existingVendor);
			if (existingVendor != null) {
				existingVendor.setSiteUrl(vendor.getSiteUrl());
				if (CommonUtilities.isNotEmpty(vendor.getAffiliateUrl())) {
					existingVendor.setAffiliateUrl(vendor.getAffiliateUrl());
				}
				existingVendor.setName(vendor.getName());
				existingVendor.setDescription(vendor.getDescription());
				existingVendor.setOwner(vendor.getOwner());
				existingVendor.setVendorType(VendorType.GLOBAL);
				existingVendor.setLogoUrl(vendor.getLogoUrl());
				getVendorManager().saveOrUpdateVendor(existingVendor);
				logger.warn("Vendor already exists for name - " + vendor.getName());
				continue;
			}
			getVendorManager().saveOrUpdateVendor(vendor);
		}

	}

	private void updateAffiliateURL(ScotchWorldStore scotchWorldStore) {
		String affUrl = vendorToAffUrlMap.get(scotchWorldStore.getStoreName().toLowerCase());
		if (CommonUtilities.isNotEmpty(affUrl)) {
			scotchWorldStore.setAffiliateUrl(affUrl);
		}
	}

	private void loadInitialData() {
		this.affiliateUrlsToIgnore =  CommonFileUtilities.getResourceFileAsLines(AFFILIATE_URLS_TO_BE_FILTERED);

		InputStream fis = null;
		try {
			fis = CommonFileUtilities.class.getClassLoader().getResourceAsStream(VENDOR_TO_AFFILIATE_URL_MAPPINGS);
			Properties vendorToAffUrlProperties = new Properties();
			vendorToAffUrlProperties.load(fis);
			Set<Entry<Object, Object>> entrySet = vendorToAffUrlProperties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				vendorToAffUrlMap.put((String)entry.getKey(),(String) entry.getValue());
			}

		} catch (Exception e) {
			logger.error("error while processing file for vendor url to affiliate url mappings", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("error in closing file for vendor url to affiliate url mappings", e);
				}
			}
		}

	}

	private void checkAndUpdateVendorUrl(ScotchWorldStore scotchWorldStore) {
		String storeURL = scotchWorldStore.getStoreURL();
		String storeName = scotchWorldStore.getStoreName();
		if (CommonUtilities.isNullOrEmpty(storeURL)) {
			return;
		}
		boolean fixStoreUrl = false;
		for (String affiliateUrl : affiliateUrlsToIgnore) {
			if (storeURL.contains(affiliateUrl) || storeURL.equals("http://") || storeURL.equals("https:/")) {
				fixStoreUrl = true;
				break;
			}
		}

		if (fixStoreUrl) {
			String newUrl = "";
			if (storeName.endsWith(".com") || storeName.endsWith(".com")) {
				newUrl =  SITE_URL_PREFIX + storeName.toLowerCase();
			} else {
				newUrl =  SITE_URL_PREFIX + storeName.replace(" ", "").toLowerCase() + ".com";
			}
			logger.info("for vendor: " + storeName + ", URL replaced from: " + storeURL + "  to: " + newUrl);
			scotchWorldStore.setStoreURL(newUrl);
		}
	}


	private Vendor getVendorFrom(ScotchWorldStore scotchWorldStore) {
		Vendor vendor = new Vendor();
		vendor.setName(scotchWorldStore.getStoreName());
		vendor.setDescription(scotchWorldStore.getDescription());
		vendor.setSiteUrl(ScotchWorldUtil.formatSiteURL(scotchWorldStore.getStoreURL()));
		vendor.setLogoUrl(scotchWorldStore.getStoreName() + ".jpg");
		vendor.setVendorType(VendorType.GLOBAL);
		vendor.setAffiliateUrl(scotchWorldStore.getAffiliateUrl());
		vendor.setOwner(userManager.getODAdminUser());
		return vendor;
	}

	public void setVendorManager(VendorManager vendorManager) {
		this.vendorManager = vendorManager;
	}

	public VendorManager getVendorManager() {
		return vendorManager;
	}

	@Override
	public void doJobTask() {
		syncVendors();
	}

	@Override
	public void addJobEventListener(JobEventListener eventListener) {
		// TODO Auto-generated method stub

	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
