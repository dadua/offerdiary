package com.itech.redwine.parser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itech.common.services.Initialize;
import com.itech.config.ProjectConfigs;

@Service
public class RedWineOfferEngine implements Initialize{
	private static final Logger logger = Logger.getLogger(RedWineOfferEngine.class);

	@Autowired
	private RedWineSyncJob redWineSyncJob;

	@Override
	public void init() {
		if (!ProjectConfigs.initializeRedWineData.get()) {
			logger.info("skipping redwine data initialization.");
			return;
		}
		redWineSyncJob.doJob();
	}

	public RedWineSyncJob getRedWineSyncJob() {
		return redWineSyncJob;
	}
	public void setRedWineSyncJob(RedWineSyncJob redWineSyncJob) {
		this.redWineSyncJob = redWineSyncJob;
	}
}