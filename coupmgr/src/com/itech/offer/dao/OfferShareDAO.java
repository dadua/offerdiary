package com.itech.offer.dao;

import com.itech.common.db.CommonBaseDAO;
import com.itech.offer.model.OfferShare;

public interface OfferShareDAO extends CommonBaseDAO<OfferShare>{
	public OfferShare getOfferShareFor (String accessToken);
}
