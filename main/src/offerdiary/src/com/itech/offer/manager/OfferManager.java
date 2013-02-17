package com.itech.offer.manager;

import java.util.List;

import com.itech.common.db.SearchCriteria;
import com.itech.offer.model.Offer;
import com.itech.offer.model.OfferCard;
import com.itech.offer.model.OfferShare;
import com.itech.offer.vo.OfferSearchResultVO;
import com.itech.user.model.User;

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

	public Offer copySharedOffer(String accessToken);

	public Offer getOfferFor(Long offerId);

	public boolean removeOffersForCard(OfferCard offerCard);

	public boolean addOffersForCard(List<Offer> offers, OfferCard offerCard);

	public List<Offer> getAllOffersOnCardsForUser(User user);

	public List<Offer> getAllOffersForCard(Long offerCardId);

	public OfferSearchResultVO searchOffersFor(SearchCriteria searchCriteria);

	public void deleteByUniqueIds(List<String> offerUniqueIds);

	public Offer getOfferForUnqueId(String uniqueId);

}
