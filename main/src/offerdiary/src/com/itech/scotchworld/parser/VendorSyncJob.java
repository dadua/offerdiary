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
		String vendorJsonData = CommonFileUtilities.getResourceFileAsString("resources\\couponduniya\\stores-minimal.json");
		Gson gson = new Gson();
		Type vendorJsonType = new TypeToken<List<ScotchWorldStore>>() { }.getType();
		List<ScotchWorldStore> scotchDuniyaStores = gson.fromJson(vendorJsonData, vendorJsonType);
		for ( ScotchWorldStore scotchWorldStore : scotchDuniyaStores) {
			if (scotchWorldStore.getStoreName() == null) {
				continue;
			}
			Vendor vendor = getVendorFrom(scotchWorldStore);
			Vendor existingVendor = getVendorManager().getVendorByName(vendor.getName());
			if (existingVendor != null) {
				logger.warn("Vendor already exists for name - " + vendor.getName());
				continue;
			}
			getVendorManager().saveOrUpdateVendor(vendor);
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
