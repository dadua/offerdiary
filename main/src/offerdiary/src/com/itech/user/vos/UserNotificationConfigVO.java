package com.itech.user.vos;

import com.itech.user.model.User;
import com.itech.user.model.UserNotificationConfig;

public class UserNotificationConfigVO {

	private static final int DEFAULT_EXPIRY_ALERT_DAYS = 5;

	private Long id;

	private User user;

	private Boolean weeklySummary = Boolean.FALSE;

	private Boolean promotions = Boolean.FALSE;

	private Boolean offerExpiryAlert = Boolean.TRUE;

	private Integer offerExpiryAlertDays = DEFAULT_EXPIRY_ALERT_DAYS;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getWeeklySummary() {
		return weeklySummary;
	}

	public void setWeeklySummary(Boolean weeklySummary) {
		this.weeklySummary = weeklySummary;
	}

	public Boolean getPromotions() {
		return promotions;
	}

	public void setPromotions(Boolean promotions) {
		this.promotions = promotions;
	}

	public Boolean getOfferExpiryAlert() {
		return offerExpiryAlert;
	}

	public void setOfferExpiryAlert(Boolean offerExpiryAlert) {
		this.offerExpiryAlert = offerExpiryAlert;
	}

	public Integer getOfferExpiryAlertDays() {
		if (offerExpiryAlertDays == null) {
			return DEFAULT_EXPIRY_ALERT_DAYS;
		}
		return offerExpiryAlertDays;
	}

	public void setOfferExpiryAlertDays(Integer offerExpiryAlertDays) {
		this.offerExpiryAlertDays = offerExpiryAlertDays;
	}

	public static UserNotificationConfigVO getUserNotificationConfigVoFor(UserNotificationConfig userNotificationConfig) {
		UserNotificationConfigVO userNotificationConfigVO = new UserNotificationConfigVO();
		userNotificationConfigVO.setOfferExpiryAlert(userNotificationConfig.getOfferExpiryAlert());
		userNotificationConfigVO.setOfferExpiryAlertDays(userNotificationConfig.getOfferExpiryAlertDays());
		userNotificationConfigVO.setPromotions(userNotificationConfig.getPromotions());
		userNotificationConfigVO.setWeeklySummary(userNotificationConfig.getWeeklySummary());
		return userNotificationConfigVO;
	}


	public static void fillUserNotificationConfigFromVO(UserNotificationConfig userNotificationConfig, UserNotificationConfigVO userNotificationConfigVO) {
		userNotificationConfig.setOfferExpiryAlert(userNotificationConfigVO.getOfferExpiryAlert());
		userNotificationConfig.setOfferExpiryAlertDays(userNotificationConfigVO.getOfferExpiryAlertDays());
		userNotificationConfig.setPromotions(userNotificationConfigVO.getPromotions());
		userNotificationConfig.setWeeklySummary(userNotificationConfigVO.getWeeklySummary());
	}



}
