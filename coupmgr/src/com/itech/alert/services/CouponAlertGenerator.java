package com.itech.alert.services;

import java.sql.Date;

import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.AlertDataTypes;
import com.itech.alert.model.Alert.AlertStatus;
import com.itech.coupon.manager.CouponManager;
import com.itech.coupon.model.Coupon;

public class CouponAlertGenerator implements AlertGenerator {
	private AlertManager alertManager;
	private AlertConfigManager alertConfigManager;
	private CouponManager couponManager;


	@Override
	public boolean handles(AlertConfig alertConfig) {
		if (AlertDataTypes.COUPON.toString().equalsIgnoreCase(alertConfig.getDataType())) {
			return true;
		}
		return false;
	}


	@Override
	public Alert createAlert(AlertConfig alertConfig) {
		Alert alert = createAlertFor(alertConfig);
		getAlertManager().save(alert);
		alertConfig.setStatus(AlertConfig.ActivationStatus.HANDLED);
		getAlertConfigManager().delete(alertConfig);
		return alert;
	}
	private Alert createAlertFor(AlertConfig alertConfig) {
		Coupon coupon = getCouponManager().getById(alertConfig.getDataId());
		String alertMessage = "Your coupon is expiring on: " + coupon.getExpiryDate()  + "\n" +
		"Coupon Detail:\n " + coupon.getDetail();
		Alert alert = new Alert(coupon.getOwner().getId(), AlertDataTypes.COUPON.toString(),
				coupon.getId(), AlertStatus.NEW, new Date(System.currentTimeMillis()), alertMessage, alertMessage);
		return alert;
	}
	public void setAlertManager(AlertManager alertManager) {
		this.alertManager = alertManager;
	}
	public AlertManager getAlertManager() {
		return alertManager;
	}
	public void setAlertConfigManager(AlertConfigManager alertConfigManager) {
		this.alertConfigManager = alertConfigManager;
	}
	public AlertConfigManager getAlertConfigManager() {
		return alertConfigManager;
	}


	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}


	public CouponManager getCouponManager() {
		return couponManager;
	}

}
