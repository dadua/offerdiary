package com.itech.offer.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.itech.common.db.PersistableEntity;
import com.itech.offer.model.enums.DiscountType;
import com.itech.offer.model.enums.OfferType;
import com.itech.offer.vo.NotifyVO;

@Entity
@Table(name=OfferModelConstants.TABLE_OFFER)
public class Offer extends PersistableEntity implements Cloneable{


	private static final long TIME_23HR_59_MINUTES_IN_MILLIES = 23*60*60*1000 + 59*60*1000;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=OfferModelConstants.COL_ID)
	private Long id;

	@Column(name="UNIQUE_ID")
	private String uniqueId;

	@Column(name=OfferModelConstants.COL_TITLE)
	private String title;

	@Column(name=OfferModelConstants.COL_DESCRPTION)
	private String description;

	@Column(name="offer_link")
	private String offerLink;

	@Column(name=OfferModelConstants.COL_TERMS_N_CONDITIONS)
	private String termsAndConditions;

	@Enumerated(EnumType.STRING)
	@Column(name=OfferModelConstants.COL_DISCOUNT_TYPE)
	private DiscountType discountType = DiscountType.NO_DISCOUNT;//FIXED_AMOUNT, PERCENTAGE

	@Column(name=OfferModelConstants.COL_DISCOUNT_VALUE)
	private int discountValue;

	@Column(name=OfferModelConstants.COL_EXPIRY)
	private Date expiry;

	@Column(name="CREATED_ON")
	private Date createdOn = new Date(System.currentTimeMillis());

	@Column(name="LAST_UPDATED_ON")
	private Date lastUpdated = new Date(System.currentTimeMillis());


	@Column(name=OfferModelConstants.COL_OFFER_CODE)
	private String offerCode;

	@Column(name=OfferModelConstants.COL_OFFER_TYPE)
	private OfferType offerType = OfferType.OFFER; //coupon, voucher, offer

	@ManyToOne
	@JoinColumn(name=OfferModelConstants.COL_TARGET_VENDOR)
	private Vendor targetVendor;

	@Column(name=OfferModelConstants.COL_IMAGE_SOURCE)
	private String imageSource;

	@ManyToOne
	@JoinColumn(name=OfferModelConstants.COL_SOURCE_VENDOR)
	private Vendor sourceVendor;

	@Column(name=OfferModelConstants.COL_IS_ONLINE_OFFER)
	private Boolean isOnlineOffer = Boolean.TRUE;

	@Column(name=OfferModelConstants.COL_LOCATION)
	private String location;

	@Column(name=OfferModelConstants.COL_IS_PROTOTYPE)
	private Boolean isProtoType = Boolean.FALSE;

	@Column(name=OfferModelConstants.COL_EXPIRY_DATE_UTC_IN_MILLIS)
	private long expiryDateInMillis;


	@Column(name=OfferModelConstants.COL_EMAIL_NOTIFICATION)
	private Boolean emailNotification = Boolean.FALSE;


	@Column(name=OfferModelConstants.COL_FB_NOTIFICATION)
	private Boolean fbNotification = Boolean.FALSE;

	@Column(name="APPROVED")
	private boolean approved;


	@Column(name="IS_PUBLIC")
	private Boolean isPublic = Boolean.FALSE;

	@Enumerated(EnumType.STRING)
	@Column(name="SOURCE_TYPE")
	private OfferSourceType sourceTypeInDb = OfferSourceType.UNKOWN;


	@Column(name="SOURCE_TAG")
	private String sourceTag;

	@Column(name="FEEDER")
	private String feeder; //REDWINE etc

	@Column(name="REPUTATION")
	private int reputation = 1;


	@Column(name="FEEDER_REPUTATION")
	private int feederReputation = 1;

	@Transient
	private NotifyVO notifyVO;

	@Transient
	private boolean associatedWithLoggedInUser;

	@Transient
	private OfferSourceType sourceType = OfferSourceType.UNKOWN;

	@Transient
	private String source;

	public enum OfferSourceType {
		CARD, COUPON_SITE, USER, UNKOWN
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
		if (expiry != null) {
			setExpiryDateInMillis(expiry.getTime());
		}
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

	public Vendor getTargetVendor() {
		return targetVendor;
	}

	public void setTargetVendor(Vendor targetVendor) {
		this.targetVendor = targetVendor;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	public Vendor getSourceVendor() {
		return sourceVendor;
	}

	public void setSourceVendor(Vendor sourceVendor) {
		this.sourceVendor = sourceVendor;
	}

	public Boolean isOnlineOffer() {
		return isOnlineOffer;
	}

	public void setOnlineOffer(Boolean isOnlineOffer) {
		this.isOnlineOffer = isOnlineOffer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Boolean isProtoType() {
		return isProtoType;
	}

	public void setProtoType(Boolean isProtoType) {
		this.isProtoType = isProtoType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean isTransient() {
		return id == null;
	}

	public long getExpiryDateInMillis() {
		if (getExpiry() != null) {
			return getExpiry().getTime() + TIME_23HR_59_MINUTES_IN_MILLIES;//Temporary fixing expiry days issues in UI
		}
		return expiryDateInMillis;
	}

	public void setExpiryDateInMillis(long expiryDateInMillis) {
		this.expiryDateInMillis = expiryDateInMillis;
	}

	@Override
	public Offer clone() throws CloneNotSupportedException {
		return (Offer) super.clone();
	}


	public void setNotifyVO(NotifyVO notifyVO) {
		this.notifyVO = notifyVO;
	}


	public NotifyVO getNotifyVO() {
		return notifyVO;
	}


	public void setEmailNotification(Boolean emailNotification) {
		this.emailNotification = emailNotification;
	}


	public Boolean isEmailNotification() {
		return emailNotification;
	}

	public void setFbNotification(Boolean fbNotification) {
		this.fbNotification = fbNotification;
	}


	public Boolean isFbNotification() {
		return fbNotification;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public String getUniqueId() {
		return uniqueId;
	}


	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Offer other = (Offer) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
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


	public String getOfferLink() {
		return offerLink;
	}


	public void setOfferLink(String offerLink) {
		this.offerLink = offerLink;
	}


	public boolean isApproved() {
		return approved;
	}


	public void setApproved(boolean approved) {
		this.approved = approved;
	}


	public OfferSourceType getSourceTypeInDb() {
		return sourceTypeInDb;
	}


	public void setSourceTypeInDb(OfferSourceType sourceTypeInDb) {
		this.sourceTypeInDb = sourceTypeInDb;
	}


	public Boolean getIsPublic() {
		return isPublic;
	}


	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}


	public String getSourceTag() {
		return sourceTag;
	}


	public void setSourceTag(String sourceTag) {
		this.sourceTag = sourceTag;
	}


	public String getFeeder() {
		return feeder;
	}


	public void setFeeder(String feeder) {
		this.feeder = feeder;
	}


	public int getReputation() {
		return reputation;
	}


	public void setReputation(int reputation) {
		this.reputation = reputation;
	}


	public int getFeederReputation() {
		return feederReputation;
	}


	public void setFeederReputation(int feederReputation) {
		this.feederReputation = feederReputation;
	}


	public Date getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
