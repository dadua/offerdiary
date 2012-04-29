package com.itech.coupon.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.itech.coupon.model.constants.CouponModelConstants;

@Entity
@Table(name="COUPONS")
public class Coupon {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=CouponModelConstants.COL_ID)
	private Long id;

	@Column(name=CouponModelConstants.COL_AUTOGUID)
	private String autoGUID;
	private Store store;
	private long storeId;
	private String code;
	private float discount;
	private String detail;
	private Date creationDate;
	private Date expiryDate;
	private long expiryDateInMillis;
	private User owner;
	private CouponPermission permission =  CouponPermission.PRIVATE;
	private String tags;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public long getStoreId() {
		return storeId;
	}
	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public void setPermission(CouponPermission permission) {
		this.permission = permission;
	}
	public CouponPermission getPermission() {
		return permission;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public User getOwner() {
		return owner;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getTags() {
		return tags;
	}
	public void setAutoGUID(String autoGUID) {
		this.autoGUID = autoGUID;
	}
	public String getAutoGUID() {
		return autoGUID;
	}
	public void setExpiryDateInMillis(long expiryDateInMillis) {
		this.expiryDateInMillis = expiryDateInMillis;
	}
	public long getExpiryDateInMillis() {
		return expiryDateInMillis;
	}




}
