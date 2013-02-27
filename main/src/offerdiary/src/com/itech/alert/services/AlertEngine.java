package com.itech.alert.services;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.itech.alert.handler.AlertHandler;
import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.NullAlert;
import com.itech.alert.model.NullAlert.NullALertReason;
import com.itech.common.CommonUtilities;
import com.itech.common.db.ConnectionUtil;
import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.common.services.Initialize;
import com.itech.common.services.ServiceLocator;

public class AlertEngine implements Initialize, Runnable{
	private final Logger logger = Logger.getLogger(AlertEngine.class);
	private AlertConfigManager alertConfigManager;
	private AlertManager alertManager;
	private List<AlertHandler> alertHandlers;
	private List<AlertGenerator> alertGenerators;
	private ConnectionUtil connectionUtil;
	private HibernateSessionFactory hibernateSessionFactory;

	@Override
	public void init() {
		Thread alertEngineThread = new Thread(this);
		alertEngineThread.start();

	}


	@Override
	public void run() {
		while (true) {
			try {
				getHibernateSessionFactory().openNewSession();
				List<AlertConfig> alertConfigs = alertConfigManager.getAllActiveExpiredAlertConfigs();
				for (AlertConfig alertConfig : alertConfigs) {
					for (AlertGenerator alertGenerator : alertGenerators) {
						if (alertGenerator.handles(alertConfig)) {
							try {
								if (alertConfig.getTriggerExpiryTime() == null ||
										alertConfig.getTriggerExpiryTime().before(new Date(System.currentTimeMillis()))) {
									getAlertConfigManager().delete(alertConfig);
									continue;
								}

								Alert alert = alertGenerator.createAlert(alertConfig);
								if (alert == null) {
									getAlertConfigManager().delete(alertConfig);
									continue;
								}
								if (NullAlert.class.isAssignableFrom(alert.getClass())) {
									NullAlert nullAlert = (NullAlert) alert;
									if (NullALertReason.TRIGGER_NEXT_TIME.equals(nullAlert.getNullALertReason())) {
										Date nextTriggerTime = new Date(alertConfig.getTriggerTime().getTime() + CommonUtilities.MILLIS_IN_A_DAY);
										alertConfig.setTriggerTime(nextTriggerTime);
										getAlertConfigManager().save(alertConfig);
										continue;
									}
									getAlertConfigManager().delete(alertConfig);
									continue;
								}

								if (alertConfig.isPersistAlertInDB()) {
									getAlertManager().save(alert);
								}
								getAlertConfigManager().delete(alertConfig);
								getHibernateSessionFactory().commitCurrentTransaction();
								handleAlert(alert);
								break;
							} catch (Exception e) {
								logger.error("error in alert engine ", e);
							}
						}
					}
				}
				getHibernateSessionFactory().commitCurrentTransaction();

			} catch (Exception e) {
				logger.error("Exception in alert engine", e);
			}finally {
				getHibernateSessionFactory().closeCurrentSession();
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
				try {
					alertHandler.handle(alert);
				} catch (Exception e) {
					logger.error("Error in handling alert:  " + alert + " by handler: " + alertHandler, e);
				}
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


	public void setConnectionUtil(ConnectionUtil connectionUtil) {
		this.connectionUtil = connectionUtil;
	}


	public ConnectionUtil getConnectionUtil() {
		return connectionUtil;
	}


	public HibernateSessionFactory getHibernateSessionFactory() {
		if (hibernateSessionFactory == null) {
			hibernateSessionFactory = ServiceLocator.getInstance().getBean(HibernateSessionFactory.class);
		}
		return hibernateSessionFactory;
	}


	public void setHibernateSessionFactory(HibernateSessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}


	public void setAlertManager(AlertManager alertManager) {
		this.alertManager = alertManager;
	}


	public AlertManager getAlertManager() {
		return alertManager;
	}


}
