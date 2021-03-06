package com.itech.offer.vo;

import java.sql.Date;

import com.itech.common.UrlUtils;
import com.itech.offer.model.Offer;
import com.itech.offer.model.Offer.OfferSourceType;
import com.itech.offer.model.enums.DiscountType;
import com.itech.offer.model.enums.OfferType;

public class OfferVO {

	private String id;

	private String uniqueId;

	private String publicId;

	private String title;

	private String description;

	private String termsAndConditions;

	private DiscountType discountType = DiscountType.NO_DISCOUNT;//FIXED_AMOUNT, PERCENTAGE

	private int discountValue;

	private Date expiry;

	private Date createdOn = new Date(System.currentTimeMillis());

	private String offerCode;

	private OfferType offerType = OfferType.OFFER; //coupon, voucher, offer

	private VendorVO targetVendor;

	private String imageSource;

	private VendorVO sourceVendor;

	private Boolean isOnlineOffer;

	private String location;

	private Boolean isProtoType;

	private long expiryDateInMillis;

	private Boolean emailNotification;

	private Boolean fbNotification;

	private Boolean byUniqueId;

	private Boolean byAccessCode;

	private String accessCode;

	private String sharedURL;

	private Boolean isPublic;

	private Boolean associatedWithLoggedInUser;

	private OfferSourceType sourceType;

	private String source;

	private String offerLink;


	public static OfferVO getOfferVOFor(Offer offer) {
		if (offer == null) {
			return null;
		}
		OfferVO offerVO = new OfferVO();
		offerVO.setId(offer.getUniqueId());
		offerVO.setByUniqueId(true);
		offerVO.setCreatedOn(offer.getCreatedOn());
		offerVO.setDescription(offer.getDescription());
		offerVO.setDiscountType(offer.getDiscountType());
		offerVO.setDiscountValue(offer.getDiscountValue());
		offerVO.setExpiry(offer.getExpiry());
		offerVO.setExpiryDateInMillis(offer.getExpiryDateInMillis());
		offerVO.setImageSource(offer.getImageSource());
		offerVO.setIsOnlineOffer(offer.isOnlineOffer());
		offerVO.setLocation(offer.getLocation());
		offerVO.setOfferCode(offer.getOfferCode());
		offerVO.setTermsAndConditions(offer.getTermsAndConditions());
		offerVO.setTitle(offer.getTitle());
		offerVO.setUniqueId(offer.getUniqueId());
		offerVO.setAssociatedWithLoggedInUser(offer.isAssociatedWithLoggedInUser());
		offerVO.setSourceType(offer.getSourceType());
		offerVO.setSource(offer.getSource());
		offerVO.setOfferLink(UrlUtils.getHttpPrefixedUrl(offer.getOfferLink()));

		offerVO.setSourceVendor(VendorVO.getVendorVOFor(offer.getSourceVendor()));
		offerVO.setTargetVendor(VendorVO.getVendorVOFor(offer.getTargetVendor()));
		offerVO.setPublic(offer.getIsPublic());
		return offerVO;
	}


	public static void fillOfferFromVO(Offer offer, OfferVO offerVO) {
		//TODO: update offer from offerVO
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUniqueId() {
		return uniqueId;
	}


	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getTermsAndConditions() {
		return termsAndConditions;
	}


	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}


	public DiscountType getDiscountType() {
		return discountType;
	}


	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}


	public int getDiscountValue() {
		return discountValue;
	}


	public void setDiscountValue(int discountValue) {
		this.discountValue = discountValue;
	}


	public Date getExpiry() {
		return expiry;
	}


	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public String getOfferCode() {
		return offerCode;
	}


	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}


	public OfferType getOfferType() {
		return offerType;
	}


	public void setOfferType(OfferType offerType) {
		this.offerType = offerType;
	}


	public VendorVO getTargetVendor() {
		return targetVendor;
	}


	public void setTargetVendor(VendorVO targetVendor) {
		this.targetVendor = targetVendor;
	}


	public String getImageSource() {
		return imageSource;
	}


	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}


	public VendorVO getSourceVendor() {
		return sourceVendor;
	}


	public void setSourceVendor(VendorVO sourceVendor) {
		this.sourceVendor = sourceVendor;
	}


	public Boolean getIsOnlineOffer() {
		return isOnlineOffer;
	}


	public void setIsOnlineOffer(Boolean isOnlineOffer) {
		this.isOnlineOffer = isOnlineOffer;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Boolean getIsProtoType() {
		return isProtoType;
	}


	public void setIsProtoType(Boolean isProtoType) {
		this.isProtoType = isProtoType;
	}


	public long getExpiryDateInMillis() {
		return expiryDateInMillis;
	}


	public void setExpiryDateInMillis(long expiryDateInMillis) {
		this.expiryDateInMillis = expiryDateInMillis;
	}


	public Boolean getEmailNotification() {
		return emailNotification;
	}


	public void setEmailNotification(Boolean emailNotification) {
		this.emailNotification = emailNotification;
	}


	public Boolean getFbNotification() {
		return fbNotification;
	}


	public void setFbNotification(Boolean fbNotification) {
		this.fbNotification = fbNotification;
	}


	public Boolean getByUniqueId() {
		return byUniqueId;
	}


	public void setByUniqueId(Boolean byUniqueId) {
		this.byUniqueId = byUniqueId;
	}


	public String getSharedURL() {
		return sharedURL;
	}


	public void setSharedURL(String sharedURL) {
		this.sharedURL = sharedURL;
	}


	public String getAccessCode() {
		return accessCode;
	}


	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}


	public Boolean getByAccessCode() {
		return byAccessCode;
	}


	public void setByAccessCode(Boolean byAccessCode) {
		this.byAccessCode = byAccessCode;
	}


	public String getPublicId() {
		return publicId;
	}


	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}


	public boolean isAssociatedWithLoggedInUser() {
		return associatedWithLoggedInUser;
	}


	public void setAssociatedWithLoggedInUser(boolean associatedWithLoggedInUser) {
		this.associatedWithLoggedInUser = associatedWithLoggedInUser;
	}


	public OfferSourceType getSourceType() {
		return sourceType;
	}


	public void setSourceType(OfferSourceType sourceType) {
		this.sourceType = sourceType;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public void setOfferLink(String offerLink) {
		this.offerLink = offerLink;
	}


	public String getOfferLink() {
		return offerLink;
	}


	public Boolean isPublic() {
		return isPublic;
	}


	public void setPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

}
