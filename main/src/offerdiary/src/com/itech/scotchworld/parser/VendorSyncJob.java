package com.itech.scotchworld.parser;

import java.lang.reflect.Type;
import java.util.List;

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

@Component
@Scope("prototype")
public class VendorSyncJob extends BaseItechJob{

	@Autowired
	private VendorManager vendorManager;

	private static final Logger logger = Logger.getLogger(VendorSyncJob.class);

	private  void syncVendors() {
		String[] affiliateUrls = CommonFileUtilities.getResourceFileAsLines("resources\\couponduniya\\affiliate_site_urls.properties");
		String vendorJsonData = CommonFileUtilities.getResourceFileAsString("resources\\couponduniya\\stores-minimal.json");
		Gson gson = new Gson();
		Type vendorJsonType = new TypeToken<List<ScotchWorldStore>>() { }.getType();
		List<ScotchWorldStore> scotchDuniyaStores = gson.fromJson(vendorJsonData, vendorJsonType);
		for ( ScotchWorldStore scotchWorldStore : scotchDuniyaStores) {
			if (scotchWorldStore.getStoreName() == null) {
				continue;
			}
			checkAndUpdateVendorUrl(scotchWorldStore, affiliateUrls);
			Vendor vendor = getVendorFrom(scotchWorldStore);
			Vendor existingVendor = getVendorManager().getVendorByName(vendor.getName());
			if (existingVendor != null) {
				existingVendor.setSiteUrl(scotchWorldStore.getStoreURL());
				getVendorManager().saveOrUpdateVendor(existingVendor);
				logger.warn("Vendor already exists for name - " + vendor.getName());
				continue;
			}
			getVendorManager().saveOrUpdateVendor(vendor);
		}

	}

	private void checkAndUpdateVendorUrl(ScotchWorldStore scotchWorldStore,
			String[] affiliateUrls) {
		String storeURL = scotchWorldStore.getStoreURL();
		String storeName = scotchWorldStore.getStoreName();
		if (CommonUtilities.isNullOrEmpty(storeURL)) {
			return;
		}
		boolean fixStoreUrl = false;
		for (String affiliateUrl : affiliateUrls) {
			if (storeURL.contains(affiliateUrl) || storeURL.equals("http://") || storeURL.equals("https:/")) {
				fixStoreUrl = true;
				break;
			}
		}

		if (fixStoreUrl) {
			String newUrl = "";
			if (storeName.endsWith(".com") || storeName.endsWith(".com")) {
				newUrl = "www." + storeName;
			} else {
				newUrl = "www." + storeName.replace(" ", "") + ".com";
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

}
