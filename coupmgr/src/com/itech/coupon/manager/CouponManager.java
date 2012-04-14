package com.itech.coupon.manager;

import java.util.List;

import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.Store;
import com.itech.coupon.model.User;

public interface CouponManager {
	public void save(Coupon coupon);
	public void save(List<Coupon> coupon, User user);
	public List<Coupon> searchBy(User owner);
	public List<Coupon> searchBy(Store store);
	public List<Coupon> searchBy(List<String> tags);

	public List<Coupon> searchBy(User owner, Store store);
	public List<Coupon> searchBy(User owner, List<String> tags);
	public Coupon getById(long id);
	public void delete(List<Coupon> coupons);

}
