package com.itech.offer.manager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.itech.coupon.model.User;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.dao.OfferUserAssocDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferUserAssoc;
import com.itech.offer.vo.OfferNotifyVO;


public class OfferManagerImpl implements OfferManager {


	private OfferDAO offerDAO;
	private OfferUserAssocDAO offerUserAssocDAO;

	@Override
	public void addOfferForUser(Offer offer, User user) {
		if ((offer.getExpiry() == null) && (offer.getExpiryDateInMillis() !=0)) {
			offer.setExpiry(new Date(offer.getExpiryDateInMillis()));
		}
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

	@Override
	public void addOffersForUser(List<Offer> offers, User user) {

		for (Offer offer: offers) {
			addOfferForUser(offer, user);
		}
	}

	@Override
	public void deleteByIds(List<Long> offerIds) {
		for(Long id: offerIds){
			//TODO this has to re-factored to accommodate the sharing model arch
			List<OfferUserAssoc> offerUserAssocs = getOfferUserAssocDAO().getOfferUserAssocForOffer(id);
			for (OfferUserAssoc offerUserAssoc : offerUserAssocs) {
				getOfferUserAssocDAO().delete(offerUserAssoc.getId());
			}
			getOfferDAO().delete(id);
		}
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

	@Override
	public List<Offer> addOffersEventsConfigForUser(
			List<OfferNotifyVO> offerNotificationVOs, User loggedInUser) {
		List<Offer> offers = new ArrayList<Offer>();
		for (OfferNotifyVO offerNotifyVO : offerNotificationVOs) {
			Offer offer = offerNotifyVO.getOffer();
			addOfferForUser(offer, loggedInUser);
			offers.add(offer);
			//TODO: Have to handle offerNotifyVO.getNotifyConfig()
			// and fire the notification engine..
		}

		return offers;
	}
}
