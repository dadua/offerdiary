package com.itech.offer.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.coupon.model.User;
import com.itech.offer.model.Offer;
import com.itech.offer.model.enums.OfferType;

public interface OfferDAO extends CommonBaseDAO<Offer>{

	List<Offer> getAllOfferForUser(User user);

	List<Offer> getAllUnexpiredOffersForUser(User user);

	List<Offer> getAllOfferForUser(User user, OfferType offerType);


}
