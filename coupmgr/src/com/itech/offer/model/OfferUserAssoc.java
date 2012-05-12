package com.itech.offer.model;

import com.itech.common.db.PersistableEntity;
import com.itech.coupon.model.User;

public class OfferUserAssoc extends PersistableEntity{
	private Offer offer;
	private User user;

}
