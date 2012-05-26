package com.itech.offer.vo;

import com.itech.offer.model.Offer;

public class OfferNotifyVO {

	private Offer offer;

	private NotifyVO notifyConfig;

	public NotifyVO getNotifyConfig() {
		return notifyConfig;
	}

	public void setNotifyConfig(NotifyVO notifyConfig) {
		this.notifyConfig = notifyConfig;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Offer getOffer() {
		return offer;
	}

}
