package com.itech.offer.manager;

import java.util.List;

import com.itech.common.db.OfferSearchCriteria;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferShare;
import com.itech.offer.vo.OfferSearchResultVO;
import com.itech.user.model.User;
import com.itech.user.vos.ShareOfferActionVO;

public interface OfferManager {

	public void addOfferForUser(Offer offer, User user);

	public void addOffersForUser(List<Offer> offers, User user);

	public List<Offer> getAllOffersForUser(User user);

	public List<Offer> getAllUnexpiredOffersForUser(User user);

	public void deleteByIds(List<Long> offerIds);

	public User getOfferOwner(Offer offer);

	public Offer getById(long dataId);

	public OfferShare createOfferShare(Offer offer);

	public OfferShare getOfferShareFor(String accessToken);

	Offer addSharedOfferToWallet(String accessToken, User loggedInUser);

	public Offer getOfferFor(Long offerId);

	public boolean removeOffersForCard(OfferCard offerCard);

	public boolean addOffersForCard(List<Offer> offers, OfferCard offerCard);

	public OfferSearchResultVO getAllOffersOnCardsForUser(User user);

	public OfferSearchResultVO getAllOffersForCard(Long offerCardId);

	public Offer getOfferForUnqueId(String uniqueId);

	public void shareOffer(ShareOfferActionVO shareOfferActionVO);

	public void addOfferFromCardToUser(String offerId, User loggedInUser);

	void removeOffersFromWallet(List<String> offerUniqueIds, User loggedInUser);

	OfferSearchResultVO searchOffersFor(OfferSearchCriteria searchCriteria);

	public void addOffersForCards(List<Offer> offers, List<OfferCard> cards);

	public void addOfferToUser(String offerId, User user);


}
