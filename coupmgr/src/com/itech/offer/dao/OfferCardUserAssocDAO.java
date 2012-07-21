package com.itech.offer.dao;

import com.itech.common.db.CommonBaseDAO;
import com.itech.coupon.model.User;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferCardUserAssoc;

public interface OfferCardUserAssocDAO extends CommonBaseDAO<OfferCardUserAssoc>{

	OfferCardUserAssoc getAssocFor(OfferCard offerCard, User user);

}
