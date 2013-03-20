package com.itech.offer.manager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;
import com.itech.common.db.SearchCriteria;
import com.itech.common.services.CommonBaseManager;
import com.itech.email.services.EmailManager;
import com.itech.email.vo.ShareOfferNotificationEmail;
import com.itech.event.offer.OfferEventGenerator;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.dao.OfferOfferCardAssocDAO;
import com.itech.offer.dao.OfferShareDAO;
import com.itech.offer.dao.OfferUserAssocDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferOfferCardAssoc;
import com.itech.offer.model.OfferShare;
import com.itech.offer.model.OfferUserAssoc;
import com.itech.offer.model.Vendor;
import com.itech.offer.vo.OfferSearchResultVO;
import com.itech.offer.vo.OfferVO;
import com.itech.user.model.User;
import com.itech.user.vos.ShareOfferActionVO;


public class OfferManagerImpl extends CommonBaseManager implements OfferManager {

	private static final Logger logger = Logger.getLogger(OfferManagerImpl.class);
	private OfferDAO offerDAO;
	private OfferUserAssocDAO offerUserAssocDAO;
	private OfferShareDAO offerShareDAO;
	private VendorManager vendorManager;
	private OfferEventGenerator offerEventGenerator;
	private OfferOfferCardAssocDAO offerOfferCardAssocDAO;
	private OfferCardManager offerCardManager;
	private EmailManager emailManager;

	@Override
	public void addOfferForUser(Offer offer, User user) {
		if ((offer.getExpiry() == null) && (offer.getExpiryDateInMillis() !=0)) {
			offer.setExpiry(new Date(offer.getExpiryDateInMillis()));
		}
		if (offer.getNotifyVO() != null) {
			offer.setFbNotification(offer.getNotifyVO().getFbNotify());
			offer.setEmailNotification(offer.getNotifyVO().getEmailNotify());
		}
		Vendor targetVendor = offer.getTargetVendor();
		if (targetVendor != null) {
			Long vendorId = targetVendor.getId();
			Vendor vendorFromDB = getVendorManager().getVendorFor(vendorId);
			offer.setTargetVendor(vendorFromDB);
		}

		if (CommonUtilities.isNullOrEmpty(offer.getUniqueId())) {
			offer.setUniqueId(CommonUtilities.getUniqueId("OFFER"));
		}
		offerDAO.addOrUpdate(offer);
		OfferUserAssoc offerUserAssoc = getOfferUserAssoc(offer,user);
		offerUserAssocDAO.addOrUpdate(offerUserAssoc);
		if (offer.getExpiry() != null) {
			getOfferEventGenerator().offerCreated(offer, getLoggedInUser());
		}
	}


	@Override
	public OfferSearchResultVO searchOffersFor(SearchCriteria searchCriteria) {
		return getOfferDAO().searchOffersFor(searchCriteria);
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

	@Override
	public void deleteByUniqueIds(List<String> offerUniqueIds) {
		for(String uniqueId: offerUniqueIds){
			//TODO this has to re-factored to accommodate the sharing model arch
			Offer offer = getOfferDAO().getByUniqueId(uniqueId);
			List<OfferUserAssoc> offerUserAssocs = getOfferUserAssocDAO().getOfferUserAssocForOffer(offer.getId());
			for (OfferUserAssoc offerUserAssoc : offerUserAssocs) {
				getOfferUserAssocDAO().delete(offerUserAssoc.getId());
			}

			OfferShare offerShare = getOfferShareDAO().getOfferShareFor(offer);
			if (offerShare != null) {
				getOfferShareDAO().delete(offerShare);
			}
			getOfferDAO().delete(offer);
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
	public OfferShare createOfferShare(Offer offer) {
		OfferShare existingOfferShare = getOfferShareDAO().getOfferShareFor(offer);
		if (existingOfferShare != null) {
			return existingOfferShare;
		}
		OfferShare offerShare = new OfferShare();
		offerShare.setOffer(offer);
		offerShare.setCreationDate(new Date(System.currentTimeMillis()));
		offerShare.setAccessToken(CommonUtilities.getUniqueId("OFFER"));
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

	@Override
	public Offer getOfferForUnqueId(String uniqueId) {
		return getOfferDAO().getByUniqueId(uniqueId);
	}


	@Override
	public boolean addOffersForCard(List<Offer> offers, OfferCard offerCard) {
		List<OfferOfferCardAssoc> assocs = new ArrayList<OfferOfferCardAssoc>();
		for (Offer offer : offers) {
			if (CommonUtilities.isNullOrEmpty(offer.getUniqueId())) {
				offer.setUniqueId(CommonUtilities.getUniqueId("OFFER"));
			}
			Vendor existingVendor = getVendorManager().getVendorByName(offer.getTargetVendor().getName());
			if (existingVendor == null) {
				getVendorManager().saveOrUpdateVendor(offer.getTargetVendor());
			} else {
				offer.setTargetVendor(existingVendor);
			}
			OfferOfferCardAssoc assoc = new OfferOfferCardAssoc();
			assoc.setOffer(offer);
			assoc.setOfferCard(offerCard);
			assocs.add(assoc);
		}
		getOfferDAO().addOrUpdate(offers);
		getOfferOfferCardAssocDAO().addOrUpdate(assocs);
		return true;
	}

	@Override
	public boolean removeOffersForCard(OfferCard offerCard) {
		getOfferOfferCardAssocDAO().removeOffersForCard(offerCard);
		getOfferDAO().removeOffersForCard(offerCard);
		return true;
	}

	@Override
	public OfferSearchResultVO getAllOffersOnCardsForUser(User user) {
		List<Offer> offers = getOfferDAO().getAllOffersOnCardsForUser(user);
		fetchAndFillOfferRelationshipWithUser(offers, user);
		return convertToSearchResultVO(offers);
	}


	private OfferSearchResultVO convertToSearchResultVO(List<Offer> offers) {
		List<OfferVO> offerVOs = new ArrayList<OfferVO>();
		for (Offer offer : offers) {
			OfferVO offerVO = OfferVO.getOfferVOFor(offer);
			offerVOs.add(offerVO);
		}
		OfferSearchResultVO offerSearchResult = new OfferSearchResultVO();
		offerSearchResult.setOffers(offerVOs);
		offerSearchResult.setTotalCount(Long.valueOf(offerVOs.size()));
		offerSearchResult.setPerPageCount(Integer.valueOf(offerVOs.size()));
		return offerSearchResult;
	}

	@Override
	public OfferSearchResultVO getAllOffersForCard(Long offerCardId) {
		OfferCard offerCard = getOfferCardManager().getOfferCardFor(offerCardId);
		List<Offer> offers = getOfferDAO().getAllOffersForCard(offerCard);
		fetchAndFillOfferRelationshipWithUser(offers, getLoggedInUser());

		return convertToSearchResultVO(offers);
	}

	@Override
	public void fetchAndFillOfferRelationshipWithUser(List<Offer> offers, User user) {
		List<Offer> filteredOffers = getOfferDAO().getOffersAssociatedWithUser(offers, user);
		for (Offer offer : filteredOffers) {
			offer.setAssociatedWithLoggedInUser(true);
			offers.remove(offer);
			offers.add(offer);
		}
	}


	@Override
	public void shareOffer(ShareOfferActionVO shareOfferActionVO) {
		if (shareOfferActionVO.isMailShare()) {
			OfferShare offerShare = getOfferShareFor(shareOfferActionVO.getAccessToken());
			ShareOfferNotificationEmail shareOfferNotificationEmail = new ShareOfferNotificationEmail(shareOfferActionVO, offerShare, getLoggedInUser());
			emailManager.sendEmailAsync(shareOfferNotificationEmail);
		}
	}

	@Override
	public void addOfferFromCardToUser(String offerId, User user) {
		try {
			Offer copiedOffer = null;
			Offer offer = getOfferForUnqueId(offerId);
			copiedOffer = offer.clone();
			copiedOffer.setId(null);
			addOfferForUser(offer , user);
		} catch (CloneNotSupportedException e) {
			logger.error("Error in cloning the offer", e);
			throw new RuntimeException("Error in cloning the offer", e);
		}

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

	public void setOfferEventGenerator(OfferEventGenerator offerEventGenerator) {
		this.offerEventGenerator = offerEventGenerator;
	}

	public OfferEventGenerator getOfferEventGenerator() {
		return offerEventGenerator;
	}

	public void setOfferOfferCardAssocDAO(OfferOfferCardAssocDAO offerOfferCardAssocDAO) {
		this.offerOfferCardAssocDAO = offerOfferCardAssocDAO;
	}

	public OfferOfferCardAssocDAO getOfferOfferCardAssocDAO() {
		return offerOfferCardAssocDAO;
	}

	public OfferCardManager getOfferCardManager() {
		return offerCardManager;
	}

	public void setOfferCardManager(OfferCardManager offerCardManager) {
		this.offerCardManager = offerCardManager;
	}


	public EmailManager getEmailManager() {
		return emailManager;
	}


	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}




}
