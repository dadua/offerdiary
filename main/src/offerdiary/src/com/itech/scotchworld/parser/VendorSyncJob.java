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
import com.itech.offer.model.Vendor.SourceType;
import com.itech.offer.model.enums.VendorType;
import com.itech.user.manager.UserManager;

@Component
@Scope("prototype")
public class VendorSyncJob extends BaseItechJob{

	private static final String SITE_URL_PREFIX = "http://";
	private static final String VENDOR_JSON_FILE_PATH = "resources\\couponduniya\\stores-minimal.json";
	private static final String VENDOR_TO_AFFILIATE_URL_MAPPINGS = "resources\\couponduniya\\vendor_to_aff_urls.properties";
	private static final String VENDOR_TO_SITE_URLS_MAPPINGS = "resources\\couponduniya\\vendor_to_urls.properties";
	private static final String VENDOR_TO_OD_REPUTATION_MAPPINGS = "resources\\couponduniya\\vendor_to_od_reputation.properties";

	private static final String AFFILIATE_URLS_TO_BE_FILTERED = "resources\\couponduniya\\affiliate_site_urls.properties";

	@Autowired
	private VendorManager vendorManager;

	private UserManager userManager;

	private static final Logger logger = Logger.getLogger(VendorSyncJob.class);

	private String[] affiliateUrlsToIgnore;
	private final Map<String, String> vendorToAffUrlMap = new HashMap<String, String>();
	private final Map<String, String> vendorToSiteUrlMap = new HashMap<String, String>();
	private final Map<String, Integer> vendorToOdReputationMap = new HashMap<String, Integer>();

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
			updateOdReputation(vendor);
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
				existingVendor.setSourceType(vendor.getSourceType());
				existingVendor.setLogoUrl(vendor.getLogoUrl());
				existingVendor.setOdReputation(vendor.getOdReputation());
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

	private void updateOdReputation(Vendor vendor) {
		Integer odReputation = vendorToOdReputationMap.get(vendor.getName().toLowerCase().trim());
		if (odReputation != null) {
			vendor.setOdReputation(odReputation);
		}
	}


	private void loadInitialData() {
		affiliateUrlsToIgnore =  CommonFileUtilities.getResourceFileAsLines(AFFILIATE_URLS_TO_BE_FILTERED);

		InputStream affiliateUrlsIS = null;
		try {
			affiliateUrlsIS = CommonFileUtilities.class.getClassLoader().getResourceAsStream(VENDOR_TO_AFFILIATE_URL_MAPPINGS);
			Properties vendorToAffUrlProperties = new Properties();
			vendorToAffUrlProperties.load(affiliateUrlsIS);
			Set<Entry<Object, Object>> entrySet = vendorToAffUrlProperties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				vendorToAffUrlMap.put((String)entry.getKey(),(String) entry.getValue());
			}

		} catch (Exception e) {
			logger.error("error while processing file for vendor url to affiliate url mappings", e);
		} finally {
			if (affiliateUrlsIS != null) {
				try {
					affiliateUrlsIS.close();
				} catch (IOException e) {
					logger.error("error in closing file for vendor url to affiliate url mappings", e);
				}
			}
		}

		InputStream siteUrlsIs = null;
		try {
			siteUrlsIs = CommonFileUtilities.class.getClassLoader().getResourceAsStream(VENDOR_TO_SITE_URLS_MAPPINGS);
			Properties vendorToSiteUrlProperties = new Properties();
			vendorToSiteUrlProperties.load(siteUrlsIs);
			Set<Entry<Object, Object>> entrySet = vendorToSiteUrlProperties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				vendorToSiteUrlMap.put((String)entry.getKey(),(String) entry.getValue());
			}

		} catch (Exception e) {
			logger.error("error while processing file for vendor url to affiliate url mappings", e);
		} finally {
			if (siteUrlsIs != null) {
				try {
					siteUrlsIs.close();
				} catch (IOException e) {
					logger.error("error in closing file for vendor url to affiliate url mappings", e);
				}
			}
		}


		InputStream odReputationFileIS = null;
		try {
			odReputationFileIS = CommonFileUtilities.class.getClassLoader().getResourceAsStream(VENDOR_TO_OD_REPUTATION_MAPPINGS);
			Properties vendorToOdReputationProperties = new Properties();
			vendorToOdReputationProperties.load(odReputationFileIS);
			Set<Entry<Object, Object>> entrySet = vendorToOdReputationProperties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				vendorToOdReputationMap.put((String)entry.getKey(),Integer.parseInt((String)entry.getValue()));
			}

		} catch (Exception e) {
			logger.error("error while processing file for vendor to od reputation mappings", e);
		} finally {
			if (odReputationFileIS != null) {
				try {
					odReputationFileIS.close();
				} catch (IOException e) {
					logger.error("error in closing file for vendor url to od reputation  mappings", e);
				}
			}
		}

	}

	private void checkAndUpdateVendorUrl(ScotchWorldStore scotchWorldStore) {
		String storeURL = SITE_URL_PREFIX + ScotchWorldUtil.formatSiteURL(scotchWorldStore.getStoreURL());
		scotchWorldStore.setStoreURL(storeURL);
		String storeName = scotchWorldStore.getStoreName().toLowerCase();

		String siteUrlFromConfig = vendorToSiteUrlMap.get(storeName);
		if (siteUrlFromConfig != null) {
			scotchWorldStore.setStoreURL(siteUrlFromConfig);
			return;
		}

		boolean fixStoreUrl = false;
		for (String affiliateUrl : affiliateUrlsToIgnore) {
			if (CommonUtilities.isNullOrEmpty(affiliateUrl)) {
				continue;
			}
			if (storeURL.contains(affiliateUrl) || storeURL.equals("http://") ||
					storeURL.equals("https:/") ||
					storeURL.equals("https://") ||
					CommonUtilities.isNullOrEmpty(storeURL)) {
				fixStoreUrl = true;
				break;
			}
		}


		if (fixStoreUrl) {
			String newUrl = "";
			if (storeName.endsWith(".com") || storeName.endsWith(".in") || storeName.endsWith("co.in")) {
				newUrl =  SITE_URL_PREFIX + storeName.toLowerCase().replace(" ", "");
				scotchWorldStore.setStoreURL(newUrl);
				logger.info("for vendor: " + storeName + ", URL replaced from: " + storeURL + "  to: " + newUrl);
				return;
			}

			newUrl =  SITE_URL_PREFIX + storeName.replace(" ", "").toLowerCase() + ".com";
			scotchWorldStore.setStoreURL(newUrl);
			logger.info("for vendor: " + storeName + ", URL replaced from: " + storeURL + "  to: " + newUrl);
		}
	}


	private Vendor getVendorFrom(ScotchWorldStore scotchWorldStore) {
		Vendor vendor = new Vendor();
		vendor.setName(scotchWorldStore.getStoreName());
		vendor.setDescription(scotchWorldStore.getDescription());
		vendor.setSiteUrl(scotchWorldStore.getStoreURL());
		vendor.setLogoUrl(scotchWorldStore.getStoreName() + ".jpg");
		vendor.setVendorType(VendorType.GLOBAL);
		vendor.setSourceType(SourceType.REDSCOTCH);
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
