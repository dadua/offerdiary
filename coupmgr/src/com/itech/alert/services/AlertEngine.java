package com.itech.alert.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.itech.alert.handler.AlertHandler;
import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertConfig;
import com.itech.common.services.Initialize;

public class AlertEngine implements Initialize, Runnable{
	private final Logger logger = Logger.getLogger(AlertEngine.class);
	private AlertConfigManager alertConfigManager;
	private List<AlertHandler> alertHandlers;
	private List<AlertGenerator> alertGenerators;


	@Override
	public void init() {
		Thread alertEngineThread = new Thread(this);
		alertEngineThread.start();

	}


	@Override
	public void run() {
		while (true) {
			List<AlertConfig> alertConfigs = alertConfigManager.getActiveExpiredConfigs();
			for (AlertConfig alertConfig : alertConfigs) {
				for (AlertGenerator alertGenerator : alertGenerators) {
					if (alertGenerator.handles(alertConfig)) {
						Alert alert = alertGenerator.createAlert(alertConfig);
						handleAlert(alert);
						break;
					}
				}
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				logger.error("Exception in alert engine", e);
				break;
			}
		}
	}
	private void handleAlert(Alert alert) {
		for (AlertHandler alertHandler : getAlertHandlers()) {
			if (alertHandler.handles(alert)) {
				alertHandler.handle(alert);
			}
		}
	}


	public void setAlertConfigManager(AlertConfigManager alertConfigManager) {
		this.alertConfigManager = alertConfigManager;
	}
	public AlertConfigManager getAlertConfigManager() {
		return alertConfigManager;
	}


	public void setAlertHandlers(List<AlertHandler> alertHandlers) {
		this.alertHandlers = alertHandlers;
	}


	public List<AlertHandler> getAlertHandlers() {
		return alertHandlers;
	}


	public void setAlertGenerators(List<AlertGenerator> alertGenerators) {
		this.alertGenerators = alertGenerators;
	}


	public List<AlertGenerator> getAlertGenerators() {
		return alertGenerators;
	}


}
