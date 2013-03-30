package com.itech.scotchworld.parser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itech.common.services.Initialize;
import com.itech.config.ProjectConfigs;


@Service
public class ScotchWorldEngine implements Initialize{

	private static final Logger logger = Logger.getLogger(ScotchWorldEngine.class);

	@Autowired
	private VendorSyncJob vendorSyncJob;

	@Override
	public void init() {
		if (!ProjectConfigs.initializeScotchWorldData.get()) {
			logger.info("skipping scotch world duniya data initialization.");
			return;
		}
		logger.info("initializing scotch world duniya data");
	}


	public VendorSyncJob getVendorSyncJob() {
		return vendorSyncJob;
	}

	public void setVendorSyncJob(VendorSyncJob vendorSyncJob) {
		this.vendorSyncJob = vendorSyncJob;
	}

}
