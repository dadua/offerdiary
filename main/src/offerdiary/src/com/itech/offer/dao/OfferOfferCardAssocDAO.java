package com.itech.offer.dao;

import com.itech.common.db.CommonBaseDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferOfferCardAssoc;

public interface OfferOfferCardAssocDAO extends CommonBaseDAO<OfferOfferCardAssoc> {

	void removeOffersForCard(OfferCard offerCard);

	OfferOfferCardAssoc getOfferAssocFor(OfferCard offerCard, String offerDescription, String targetVendorName);

	OfferOfferCardAssoc getOfferAssocFor(OfferCard card, Offer offer);

}
