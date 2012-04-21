package com.itech.coupon.manager;

import java.util.ArrayList;
import java.util.List;

import com.itech.coupon.dao.CouponDAO;
import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.Store;
import com.itech.coupon.model.User;
import com.itech.event.coupon.CouponEventGenerator;


public class CouponManagerImpl implements CouponManager{
	private UserManager userManager;
	private CouponDAO couponDAO;
	private StoreManager storeManager;
	private CouponEventGenerator couponEventGenerator;

	@Override
	public Coupon getById(long id) {
		Coupon coupon = getCouponDAO().getById(id);
		User owner = getUserManager().getById(coupon.getOwner().getId());
		Store store = getStoreManager().getById(coupon.getStoreId());
		coupon.setOwner(owner);
		coupon.setStore(store);
		return coupon;
	}

	@Override
	public void save(Coupon coupon) {
		getCouponDAO().addOrUpdate(coupon);
		couponEventGenerator.couponCreated(coupon);
	}

	@Override
	public void save(List<Coupon> coupons, User owner) {
		for (Coupon coupon: coupons) {
			coupon.setOwner(owner);
		}
		getCouponDAO().addOrUpdate(coupons);

		for (Coupon coupon: coupons) {
			couponEventGenerator.couponCreated(coupon);
		}

	}

	@Override
	public void update(Coupon coupon) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(List<Coupon> coupon, User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(List<Coupon> coupons) {
		getCouponDAO().delete(coupons);
	}

	@Override
	public void deleteByIds(List<String> couponIds) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for(String couponId : couponIds) {
			Coupon coupon = new Coupon();
			coupon.setId(Long.parseLong(couponId));
			coupons.add(coupon);
		}
		getCouponDAO().delete(coupons);
	}

	@Override
	public List<Coupon> searchBy(User owner) {
		return getCouponDAO().getByOwner(owner);
	}

	@Override
	public List<Coupon> searchBy(Store store) {
		return getCouponDAO().getByStore(store.getId());
	}

	@Override
	public List<Coupon> searchBy(List<String> tags) {
		return getCouponDAO().getByTags(tags);
	}

	@Override
	public List<Coupon> searchBy(User owner, Store store) {
		return getCouponDAO().getBy(owner, store);
	}

	@Override
	public List<Coupon> searchBy(User owner, List<String> tags) {
		return getCouponDAO().getBy(owner, tags);
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setCouponDAO(CouponDAO couponDAO) {
		this.couponDAO = couponDAO;
	}

	public CouponDAO getCouponDAO() {
		return couponDAO;
	}

	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public StoreManager getStoreManager() {
		return storeManager;
	}

	public void setCouponEventGenerator(CouponEventGenerator couponEventGenerator) {
		this.couponEventGenerator = couponEventGenerator;
	}

	public CouponEventGenerator getCouponEventGenerator() {
		return couponEventGenerator;
	}


}
