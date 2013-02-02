package com.itech.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="USER_NOTIFICATION_CONFIG")
public class UserNotificationConfig {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@OneToOne
	@JoinColumn(name="USER_ID")
	private User user;

	@Column(name="WEEKLY_SUMMARY")
	private Boolean weeklySummary;

	@Column(name="PROMOTIONS")
	private Boolean promotions;

	@Column(name="OFFER_EXPIRY_ALERT")
	private Boolean offerExpiryAlert;

	@Column(name="OFFER_EXPIRY_ALERT_DAYS")
	private Integer offerExpiryAlertDays = 10;

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
		return offerExpiryAlertDays;
	}

	public void setOfferExpiryAlertDays(Integer offerExpiryAlertDays) {
		this.offerExpiryAlertDays = offerExpiryAlertDays;
	}



}
