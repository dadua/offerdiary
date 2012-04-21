package com.itech.event.vo;

import com.itech.coupon.model.Coupon;

public class CouponEvent extends Event{
	public enum CouponEventType {
		COUPON_CREATED, COUPON_MODIFIED, COUPON_DELETED
	}

	private CouponEventType couponEventType;
	private Coupon coupon;

	public CouponEvent(CouponEventType couponEventType, Coupon coupon) {
		super();
		this.couponEventType = couponEventType;
		this.coupon = coupon;
	}

	public void setCouponEventType(CouponEventType couponEventType) {
		this.couponEventType = couponEventType;
	}
	public CouponEventType getCouponEventType() {
		return couponEventType;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	public Coupon getCoupon() {
		return coupon;
	}



}
