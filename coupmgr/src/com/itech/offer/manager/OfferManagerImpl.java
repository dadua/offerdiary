package com.itech.offer.manager;

import java.util.List;

import com.itech.coupon.model.User;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.dao.OfferUserAssocDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferUserAssoc;


public class OfferManagerImpl implements OfferManager {


	private OfferDAO offerDAO;
	private OfferUserAssocDAO offerUserAssocDAO;

	@Override
	public void addOfferForUser(Offer offer, User user) {
		offerDAO.addOrUpdate(offer);
		OfferUserAssoc offerUserAssoc = getOfferUserAssoc(offer,user);
		offerUserAssocDAO.addOrUpdate(offerUserAssoc);
	}

	@Override
	public List<Offer> getAllOffersForUser(User user) {
		return offerDAO.getAllOfferForUser(user);
	}

	@Override
	public List<Offer> getAllUnexpiredOffersForUser(User user) {
		return offerDAO.getAllUnexpiredOffersForUser(user);
	}

	private OfferUserAssoc getOfferUserAssoc(Offer offer, User user) {
		OfferUserAssoc offerUserAssoc = new OfferUserAssoc();
		offerUserAssoc.setOffer(offer);
		offerUserAssoc.setUser(user);
		offerUserAssoc.setOriginalUser(user);
		return offerUserAssoc;
	}

	public OfferDAO getOfferDAO() {
		return offerDAO;
	}

	public void setOfferDAO(OfferDAO offerDAO) {
		this.offerDAO = offerDAO;
	}

	public OfferUserAssocDAO getOfferUserAssocDAO() {
		return offerUserAssocDAO;
	}

	public void setOfferUserAssocDAO(OfferUserAssocDAO offerUserAssocDAO) {
		this.offerUserAssocDAO = offerUserAssocDAO;
	}

}
