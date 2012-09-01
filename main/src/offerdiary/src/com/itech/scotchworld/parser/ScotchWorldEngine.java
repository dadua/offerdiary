package com.itech.scotchworld.parser;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itech.common.CommonFileUtilities;
import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.common.services.Initialize;
import com.itech.config.ProjectConfigs;
import com.itech.offer.manager.VendorManager;
import com.itech.offer.model.Vendor;

public class ScotchWorldEngine implements Initialize{
	private VendorManager vendorManager;
	private HibernateSessionFactory hibernateSessionFactory;
	private static final Logger logger = Logger.getLogger(ScotchWorldEngine.class);

	@Override
	public void init() {
		if (!ProjectConfigs.initializeScotchWorldData.get()) {
			logger.info("skipping scotch world duniya data initialization.");
			return;
		}
		logger.info("initializing scotch world duniya data");
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
		vendor.setLogoUrl(scotchWorldStore.getStoreName() + ".jpeg");
		return vendor;
	}



	public void setHibernateSessionFactory(
			HibernateSessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	public HibernateSessionFactory getHibernateSessionFactory() {
		return hibernateSessionFactory;
	}

	public void setVendorManager(VendorManager vendorManager) {
		this.vendorManager = vendorManager;
	}

	public VendorManager getVendorManager() {
		return vendorManager;
	}

}
