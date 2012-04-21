package com.itech.event.coupon;

import com.itech.coupon.model.Coupon;

public interface CouponEventGenerator {

	public void couponCreated(Coupon coupon);

	public void couponModified(Coupon coupon);

	public void couponDeleted(Coupon coupon);


}
