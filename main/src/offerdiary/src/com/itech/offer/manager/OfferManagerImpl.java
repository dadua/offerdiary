package com.itech.offer.manager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;
import com.itech.common.db.OfferSearchCriteria;
import com.itech.common.services.CommonBaseManager;
import com.itech.email.services.EmailManager;
import com.itech.email.vo.ShareOfferNotificationEmail;
import com.itech.event.offer.OfferEventGenerator;
import com.itech.offer.FeederConstants;
import com.itech.offer.dao.OfferDAO;
import com.itech.offer.dao.OfferOfferCardAssocDAO;
import com.itech.offer.dao.OfferShareDAO;
import com.itech.offer.dao.OfferUserAssocDAO;
import com.itech.offer.model.Offer;
import com.itech.offer.model.Offer.OfferSourceType;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferOfferCardAssoc;
import com.itech.offer.model.OfferShare;
import com.itech.offer.model.OfferUserAssoc;
import com.itech.offer.model.Vendor;
import com.itech.offer.model.enums.OfferOwnershipType;
import com.itech.offer.model.enums.OfferSharingType;
import com.itech.offer.model.enums.VendorType;
import com.itech.offer.vo.OfferSearchResultVO;
import com.itech.offer.vo.OfferVO;
import com.itech.user.manager.UserManager;
import com.itech.user.model.User;
import com.itech.user.model.UserRole;
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
	private UserManager userManager;

	@Override
	public void addOfferForUser(Offer offer, User user) {

		if (offer.isTransient()) {
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
				if (vendorFromDB == null) {
					vendorFromDB = createVendorForUser(targetVendor, user);
				}
				offer.setTargetVendor(vendorFromDB);
			}

			if (CommonUtilities.isNullOrEmpty(offer.getUniqueId())) {
				offer.setUniqueId(CommonUtilities.getUniqueId("OFFER"));
			}
			offer.setSourceType(OfferSourceType.USER);
			if (UserRole.OD_ADMIN.equals(user.getUserRole()) || UserRole.SUPER_USER.equals(user.getUserRole())) {
				offer.setIsPublic(true);
				offer.setFeederReputation(FeederConstants.OD_ADMIN_OFFER_REPUTATION);
			} else {
				offer.setReputation(FeederConstants.USER_OFFER_REPUTATION);
			}
			updateReputation(offer);
		} else {
			OfferUserAssoc existingUserAssoc = getOfferUserAssocDAO().getAssocFor(offer, user);
			if (existingUserAssoc != null) {
				return;
			}
		}

		OfferUserAssoc offerUserAssoc = getOfferUserAssoc(offer,user);
		if (offer.isTransient()) {
			offerDAO.addOrUpdate(offer);
		}

		offerUserAssocDAO.addOrUpdate(offerUserAssoc);
		if (offer.getExpiry() != null) {
			getOfferEventGenerator().offerCreated(offer, user);
		}
	}


	private Vendor createVendorForUser(Vendor vendor, User user) {
		vendor.setOwner(user);
		vendor.setVendorType(VendorType.USER_DEFINED);
		getVendorManager().saveOrUpdateVendor(vendor);
		return vendor;
	}


	@Override
	public OfferSearchResultVO searchOffersFor(OfferSearchCriteria searchCriteria) {
		OfferSearchResultVO offerSearchResultVO = getOfferDAO().searchOffersFor(searchCriteria);
		return offerSearchResultVO;
	}


	@Override
	public Offer getById(long offerId) {
		return offerDAO.getById(offerId);
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
	public Offer getOfferFor(Long offerId) {
		return getOfferDAO().getById(offerId);
	}

	@Override
	public OfferShare getOfferShareFor(String accessToken) {
		OfferShare offerShare = getOfferShareDAO().getOfferShareFor(accessToken);
		ArrayList<Offer> offers = new ArrayList<Offer>();
		offers.add(offerShare.getOffer());
		getOfferDAO().fetchAndFillOfferRelationshipWithUser(offers, getLoggedInUser());
		return offerShare;
	}

	@Override
	public Offer getOfferForUnqueId(String uniqueId) {
		Offer offer = getOfferDAO().getByUniqueId(uniqueId);
		ArrayList<Offer> offers = new ArrayList<Offer>();
		offers.add(offer);
		getOfferDAO().fetchAndFillOfferRelationshipWithUser(offers, getLoggedInUser());
		return offer;
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
	public void removeOffersFromWallet(List<String> offerUniqueIds, User loggedInUser) {
		List<Offer> offers = getOfferDAO().getByUniqueId(offerUniqueIds);
		for(Offer offer: offers){
			OfferUserAssoc offerUserAssocWithLoggedInUser = getOfferUserAssocDAO().getAssocFor(offer, loggedInUser);
			if (offerUserAssocWithLoggedInUser == null) {
				continue;
			}
			if (!OfferOwnershipType.CREATOR.equals(offerUserAssocWithLoggedInUser.getOwnershipType())) {
				getOfferUserAssocDAO().delete(offerUserAssocWithLoggedInUser);
				continue;
			}

			List<OfferUserAssoc> offerUserAssocs = getOfferUserAssocDAO().getOfferUserAssocForOffer(offer.getId());

			//If offer is associated with loggedin user only.
			if (offerUserAssocs.size() <= 1 || loggedInUser.isODAdmin()) {
				OfferShare offerShare = getOfferShareDAO().getOfferShareFor(offer);
				if (offerShare != null) {
					getOfferShareDAO().delete(offerShare);
				}
				for (OfferUserAssoc offerUserAssoc : offerUserAssocs) {
					getOfferUserAssocDAO().delete(offerUserAssoc.getId());
				}
				getOfferDAO().delete(offer);
				continue;
			}


			User odAdminUser = getUserManager().getODAdminUser();
			OfferUserAssoc offerUserAssocWithODAdmin = new OfferUserAssoc();
			offerUserAssocWithODAdmin.setOffer(offer);
			offerUserAssocWithODAdmin.setOfferSharingType(OfferSharingType.PUBLIC);
			offerUserAssocWithODAdmin.setOwnershipType(OfferOwnershipType.CREATOR);
			offerUserAssocWithODAdmin.setOriginalUser(loggedInUser);
			offerUserAssocWithODAdmin.setUser(odAdminUser);

			getOfferUserAssocDAO().delete(offerUserAssocWithLoggedInUser);
			getOfferUserAssocDAO().addOrUpdate(offerUserAssocWithODAdmin);
		}
	}

	private OfferUserAssoc getOfferUserAssoc(Offer offer, User user) {
		OfferUserAssoc offerUserAssoc = new OfferUserAssoc();
		offerUserAssoc.setOffer(offer);
		offerUserAssoc.setUser(user);
		offerUserAssoc.setOriginalUser(user);
		if (offer.isTransient()) {
			offerUserAssoc.setOwnershipType(OfferOwnershipType.CREATOR);
		} else {
			offerUserAssoc.setOwnershipType(OfferOwnershipType.BOOKMARKED);
		}
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
	public Offer addSharedOfferToWallet(String accessToken, User loggedInUser) {
		OfferShare offerShare = getOfferShareDAO().getOfferShareFor(accessToken);
		Offer offer = offerShare.getOffer();
		addOfferForUser(offer, loggedInUser);
		return offer;
	}


	@Override
	public void addOffersForCards(List<Offer> offers, List<OfferCard> cards) {
		List<Offer> modifiedOfferList = new ArrayList<Offer>();
		List<OfferOfferCardAssoc> assocs = new ArrayList<OfferOfferCardAssoc>();

		for (Offer offer : offers) {
			Vendor targetVendorFromOffer = offer.getTargetVendor();
			if (targetVendorFromOffer == null) {
				logger.error("target vendor found null :");
				continue;
			}
			if (CommonUtilities.isNotEmpty(targetVendorFromOffer.getName())){
				Vendor existingVendor = getVendorManager().getVendorForVendorDetail(targetVendorFromOffer);
				if (existingVendor == null) {
					getVendorManager().saveOrUpdateVendor(offer.getTargetVendor());
				} else {
					offer.setTargetVendor(existingVendor);
				}
			}

			Offer existingOffer = getOfferDAO().getOfferFor(offer.getDescription(), offer.getTargetVendor(), offer.getSourceTag(), OfferSourceType.CARD);

			if (existingOffer != null) {
				modifiedOfferList.add(existingOffer);
				Date newExpiryDate = offer.getExpiry();
				Date existingExpiryDate = existingOffer.getExpiry();

				if (newExpiryDate != null &&  !newExpiryDate.equals(existingExpiryDate)) {
					existingOffer.setExpiry(newExpiryDate);
				}
				existingOffer.setSourceTag(offer.getSourceTag());
				existingOffer.setIsPublic(true);
				existingOffer.setFeederReputation(offer.getFeederReputation());
				existingOffer.setReputation(offer.getReputation());
				updateReputation(existingOffer);
				getOfferDAO().addOrUpdate(existingOffer);
				continue;
			} else {
				if (CommonUtilities.isNullOrEmpty(offer.getUniqueId())) {
					offer.setUniqueId(CommonUtilities.getUniqueId("OFFER"));
				}
				offer.setIsPublic(true);
				offer.setSourceTypeInDb(OfferSourceType.CARD);
				updateReputation(offer);
				getOfferDAO().addOrUpdate(offer);
				modifiedOfferList.add(offer);
			}



		}

		for (Offer offer : modifiedOfferList) {
			for (OfferCard card : cards) {
				OfferOfferCardAssoc existingAssoc = getOfferOfferCardAssocDAO().getOfferAssocFor(card, offer);
				if (existingAssoc == null) {
					OfferOfferCardAssoc assoc = new OfferOfferCardAssoc();
					assoc.setOffer(offer);
					assoc.setOfferCard(card);
					assocs.add(assoc);
				}
			}
		}

		getOfferOfferCardAssocDAO().addOrUpdate(assocs);
	}


	private void updateReputation(Offer offer) {
		int reputation = offer.getFeederReputation();
		offer.setReputation(reputation);
	}


	@Override
	public boolean addOffersForCard(List<Offer> offers, OfferCard offerCard) {
		List<OfferCard> cards = new ArrayList<OfferCard>();
		cards.add(offerCard);
		addOffersForCards(offers, cards);
		return true;
	}

	@Override
	public boolean removeOffersForCard(OfferCard offerCard) {
		//TODO fix this by removing offers using remove offers from wallet with user a odAdmin.
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
		offerSearchResult.setResultsPerPage(Integer.valueOf(offerVOs.size()));
		return offerSearchResult;
	}

	@Override
	public OfferSearchResultVO getAllOffersForCard(Long offerCardId) {
		OfferCard offerCard = getOfferCardManager().getOfferCardFor(offerCardId);
		List<Offer> offers = getOfferDAO().getAllOffersForCard(offerCard);
		fetchAndFillOfferRelationshipWithUser(offers, getLoggedInUser());

		return convertToSearchResultVO(offers);
	}

	public void fetchAndFillOfferRelationshipWithUser(List<Offer> offers, User user) {
		List<OfferUserAssoc> filteredOffers = getOfferDAO().getOffersAssociatedWithUser(offers, user);
		for (OfferUserAssoc offerUserAssoc : filteredOffers) {
			Offer offer = offerUserAssoc.getOffer();
			offer.setAssociatedWithLoggedInUser(true);
			int positionOfOffer = offers.indexOf(offer);
			offers.remove(positionOfOffer);
			offers.add(positionOfOffer, offer);
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
		Offer offer = getOfferForUnqueId(offerId);
		addOfferForUser(offer , user);
	}

	@Override
	public void addOfferToUser(String offerId, User user) {
		Offer offer = getOfferForUnqueId(offerId);
		addOfferForUser(offer , user);
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


	public UserManager getUserManager() {
		return userManager;
	}


	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}




}
