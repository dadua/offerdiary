package com.itech.offer.model;

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

import org.hibernate.annotations.Type;

import com.itech.common.db.PersistableEntity;

@Entity
@Table(name=OfferCardModelConstants.TABLE_CARD)
public class OfferCard extends PersistableEntity{
	public enum CardType{
		CREDIT, DEBIT,CHARGE,GIFT, LOYALTY, MEMBERSHIP, PREPAID, UNKNOWN
	}

	public enum CardSource{
		REDWINE, OFFER_DIARY;
	}

	public enum PaymentChannel {
		VISA, MASTER_CARD, MAESTRO,UNKNOWN;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=OfferCardModelConstants.COL_ID)
	private Long id;

	@Column(name=OfferCardModelConstants.COL_NAME)
	private String name;

	@ManyToOne
	@JoinColumn(name=OfferCardModelConstants.COL_ISSUING_VENDOR)
	private Vendor issuingVendor;

	@Enumerated(EnumType.STRING)
	@Column(name=OfferCardModelConstants.COL_CARD_TYPE)
	private CardType cardType = CardType.UNKNOWN;//DEbit/Credit

	@Column(name=OfferCardModelConstants.COL_PAYMENT_SYSTEM_TYPE)
	private String paymentSystemType;//VISA,MASTER etc

	@Column(name=OfferCardModelConstants.COL_PRIVILEGE)
	private String privilege;//Platinum, Gold

	@Column(name=OfferCardModelConstants.COL_DESCRIPTION)
	@Type(type="text")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name=OfferCardModelConstants.COL_CARD_SOURCE)
	private CardSource cardSource = CardSource.OFFER_DIARY;

	@Column(name=OfferCardModelConstants.COL_CARD_SOURCE_IDENTIFIER)
	private String cardSourceIdentifier;

	@Column(name=OfferCardModelConstants.COL_IMAGE_URL)
	private String imageURL;

	@Column(name="PROVIDER")
	private String provider;//e.g. ICICI_BANK, AXIS_BANK

	@Column(name="PROVIDER_IMAGE_URL")
	private String providerImgUrl;

	@Enumerated(EnumType.STRING)
	@Column(name="PAYMENT_CHANNEL")
	private PaymentChannel paymentChannel = PaymentChannel.UNKNOWN;

	@Transient
	private boolean associatedWithCurrentUser;

	public Vendor getIssuingVendor() {
		return issuingVendor;
	}

	public void setIssuingVendor(Vendor issuingBank) {
		issuingVendor = issuingBank;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getPaymentSystemType() {
		return paymentSystemType;
	}

	public void setPaymentSystemType(String paymentSystemType) {
		this.paymentSystemType = paymentSystemType;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setAssociatedWithCurrentUser(boolean associatedWithCurrentUser) {
		this.associatedWithCurrentUser = associatedWithCurrentUser;
	}

	public boolean isAssociatedWithCurrentUser() {
		return associatedWithCurrentUser;
	}

	public void setCardSource(CardSource cardSource) {
		this.cardSource = cardSource;
	}

	public CardSource getCardSource() {
		return cardSource;
	}

	public void setCardSourceIdentifier(String cardSourceIdentifier) {
		this.cardSourceIdentifier = cardSourceIdentifier;
	}

	public String getCardSourceIdentifier() {
		return cardSourceIdentifier;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderImgUrl() {
		return providerImgUrl;
	}

	public void setProviderImgUrl(String providerImgUrl) {
		this.providerImgUrl = providerImgUrl;
	}

	public PaymentChannel getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(PaymentChannel paymentChannel) {
		this.paymentChannel = paymentChannel;
	}


}
