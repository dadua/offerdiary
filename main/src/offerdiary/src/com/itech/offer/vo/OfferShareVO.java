package com.itech.offer.vo;

import java.sql.Date;

import com.itech.offer.model.OfferShare;

public class OfferShareVO {
	private Long id;

	private OfferVO offer;

	private Date creationDate;

	private String accessToken;

	private String sharedURL;


	public static OfferShareVO getOfferShareVOFor(OfferShare offerShare) {
		OfferShareVO offerShareVO = new OfferShareVO();
		offerShareVO.setAccessToken(offerShare.getAccessToken());
		offerShareVO.setCreationDate(offerShare.getCreationDate());
		offerShareVO.setId(offerShare.getId());
		offerShareVO.setOffer(OfferVO.getOfferVOFor(offerShare.getOffer()));
		return offerShareVO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public OfferVO getOffer() {
		return offer;
	}

	public void setOffer(OfferVO offer) {
		this.offer = offer;
	}

	public String getSharedURL() {
		return sharedURL;
	}

	public void setSharedURL(String sharedURL) {
		this.sharedURL = sharedURL;
	}


}
