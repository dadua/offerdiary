package com.itech.offer.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.coupon.model.User;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferUserAssoc;

public interface OfferUserAssocDAO extends CommonBaseDAO<OfferUserAssoc> {

	List<OfferUserAssoc> getOfferUserAssocForOffer(Long id);

	User getOfferOwner(Offer offer);

}
