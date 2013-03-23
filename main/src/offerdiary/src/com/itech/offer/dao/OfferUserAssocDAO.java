package com.itech.offer.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferUserAssoc;
import com.itech.user.model.User;

public interface OfferUserAssocDAO extends CommonBaseDAO<OfferUserAssoc> {

	List<OfferUserAssoc> getOfferUserAssocForOffer(Long id);

	User getOfferOwner(Offer offer);

	OfferUserAssoc getAssocFor(Offer offer, User user);

}
