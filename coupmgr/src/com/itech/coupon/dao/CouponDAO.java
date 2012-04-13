package com.itech.coupon.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.Store;
import com.itech.coupon.model.User;

public interface CouponDAO extends CommonBaseDAO<Coupon>{

	public List<Coupon> getByOwner(User owner);

	public List<Coupon> getByStore(long storeId);

	public List<Coupon> getByTag(String tag);

	public List<Coupon> getByTags(List<String> tags);

	public List<Coupon> getBy(User owner, Store store);

	public List<Coupon> getBy(User owner, List<String> tags);
}
