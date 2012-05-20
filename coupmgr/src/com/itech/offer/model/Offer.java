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

import com.itech.common.db.PersistableEntity;
import com.itech.offer.model.enums.DiscountType;
import com.itech.offer.model.enums.OfferType;

@Entity
@Table(name="OFFERS")
public class Offer extends PersistableEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="TITLE")
	private String title;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="TERMS_N_CONDITIONS")
	private String termsAndConditions;

	@Enumerated(EnumType.STRING)
	@Column(name="DISCOUNT_TYPE")
	private DiscountType discountType = DiscountType.NO_DISCOUNT;//FIXED_AMOUNT, PERCENTAGE

	@Column(name="DISCOUNT_VALUE")
	private int discountValue;

	@Column(name="EXPIRY")
	private Date expiry;

	@Column(name="OFFER_CODE")
	private String offerCode;

	@Column(name="NAME")
	private OfferType offerType = OfferType.OFFER; //coupon, voucher, offer

	@ManyToOne
	@JoinColumn(name="TARGET_VENDOR")
	private Vendor targetVendor;

	@Column(name="IMAGE_SOURCE")
	private String imageSource;

	@ManyToOne
	@JoinColumn(name="SOURCE_VENDOR")
	private Vendor sourceVendor;

	@Column(name="IS_ONLINE_OFFER")
	private boolean isOnlineOffer;

	@Column(name="LOCATION")
	private String location;

	@Column(name="IS_PROTOTYPE")
	private boolean isProtoType;

	@Column(name="EXPIRY_DATE_UTC_IN_MILLIS")
	private long expiryDateInMillis;

	public Long getDaysToExpire() {
		Long timeMillis = System.currentTimeMillis();
		Long expiryTime = getExpiry().getTime();
		Long millisToExpire = expiryTime - timeMillis;
		Long days = (millisToExpire)/(1000*60*60*24);
		return days;
	}


	public String getExpiryDateBasedClass() {
		Long daysToExpire = getDaysToExpire();
		if(daysToExpire < 0 ) {
			return "";
		} else if(daysToExpire < 14) {
			return "label-important";
		} else if (daysToExpire < 30) {
			return "label-warning";
		} else if (daysToExpire < 90) {
			return "label-info";
		} else if (daysToExpire < 120) {
			return "label-inverse";
		} else {
			return "label-success";
		}
	}

	public String getFormattedTimeToExpire() {
		Long daysToExpire = getDaysToExpire();
		if (daysToExpire < 30) {
			return this.getFormattedDaysToExpire(daysToExpire);
		} else if (daysToExpire < 365) {
			return this.getFormattedMonthsToExpire(daysToExpire);
		} else {
			return this.getFormattedYearsToExpire(daysToExpire);
		}
	}

	private String getFormattedYearsToExpire(Long daysToExpire) {
		long yearsToExpire = daysToExpire/365;
		return yearsToExpire + (yearsToExpire==1?" year, ":" years, ") + this.getFormattedMonthsToExpire(daysToExpire%365);
	}

	private String getFormattedMonthsToExpire(Long daysToExpire) {
		long monthsToExpire = daysToExpire/30;
		return monthsToExpire + (monthsToExpire==1?" month, ":" months, ") + this.getFormattedDaysToExpire(daysToExpire%30);
	}

	private String getFormattedDaysToExpire(Long daysToExpire) {
		return daysToExpire + (daysToExpire==1?" day":" days") + " to expire";
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

	public boolean isOnlineOffer() {
		return isOnlineOffer;
	}

	public void setOnlineOffer(boolean isOnlineOffer) {
		this.isOnlineOffer = isOnlineOffer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isProtoType() {
		return isProtoType;
	}

	public void setProtoType(boolean isProtoType) {
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
		return expiryDateInMillis;
	}

	public void setExpiryDateInMillis(long expiryDateInMillis) {
		this.expiryDateInMillis = expiryDateInMillis;
	}

}