package com.itech.offer.manager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;
import com.itech.common.services.CommonBaseManager;
import com.itech.coupon.model.User;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.dao.OfferShareDAO;
import com.itech.offer.dao.OfferUserAssocDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferShare;
import com.itech.offer.model.OfferUserAssoc;
import com.itech.offer.vo.OfferNotifyVO;


public class OfferManagerImpl extends CommonBaseManager implements OfferManager {

	private static final Logger logger = Logger.getLogger(OfferManagerImpl.class);
	private OfferDAO offerDAO;
	private OfferUserAssocDAO offerUserAssocDAO;
	private OfferShareDAO offerShareDAO;
	private VendorManager vendorManager;

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
	public Offer getById(long dataId) {
		return offerDAO.getById(dataId);
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

	@Override
	public OfferShare createOfferShare(Offer offer) {
		OfferShare offerShare = new OfferShare();
		offerShare.setOffer(offer);
		offerShare.setCreationDate(new Date(System.currentTimeMillis()));
		offerShare.setAccessToken(CommonUtilities.getGUID());
		getOfferShareDAO().addOrUpdate(offerShare);
		return offerShare;
	}

	@Override
	public Offer copySharedOffer(String accessToken) {
		OfferShare offerShare = getOfferShareDAO().getOfferShareFor(accessToken);
		Offer copiedOffer = null;
		try {
			copiedOffer = offerShare.getOffer().clone();
			copiedOffer.setId(null);
			addOfferForUser(copiedOffer, getLoggedInUser());
			return copiedOffer;
		} catch (CloneNotSupportedException e) {
			logger.error("Error in cloning the offer", e);
			throw new RuntimeException("Error in cloning the offer", e);
		}
	}

	@Override
	public Offer getOfferFor(Long offerId) {
		return getOfferDAO().getById(offerId);
	}

	@Override
	public OfferShare getOfferShareFor(String accessToken) {
		OfferShare offerShare = getOfferShareDAO().getOfferShareFor(accessToken);

		return offerShare;
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


	public void setOfferShareDAO(OfferShareDAO offerShareDAO) {
		this.offerShareDAO = offerShareDAO;
	}


	@Override
	public User getOfferOwner(Offer offer) {
		return getOfferUserAssocDAO().getOfferOwner(offer);
	}


	public OfferShareDAO getOfferShareDAO() {
		return offerShareDAO;
	}

	public void setVendorManager(VendorManager vendorManager) {
		this.vendorManager = vendorManager;
	}

	public VendorManager getVendorManager() {
		return vendorManager;
	}



}
