package com.itech.coupon.manager;

import java.util.List;

import com.itech.coupon.dao.CouponDAO;
import com.itech.coupon.dao.CouponDAOImpl;
import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.Store;
import com.itech.coupon.model.User;


public class CouponManagerImpl implements CouponManager{
	private UserManager userManager;
	private CouponDAO couponDAO;
	private StoreManager storeManager;

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
	}

	@Override
	public void save(List<Coupon> coupons) {
		getCouponDAO().addOrUpdate(coupons);

	}

	@Override
	public void delete(List<Coupon> coupons) {
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
		if (userManager == null) {
			userManager = new UserManagerImpl();
		}
		return userManager;
	}

	public void setCouponDAO(CouponDAO couponDAO) {
		this.couponDAO = couponDAO;
	}

	public CouponDAO getCouponDAO() {
		if (couponDAO == null) {
			couponDAO = new CouponDAOImpl();
		}
		return couponDAO;
	}

	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public StoreManager getStoreManager() {
		if (storeManager == null) {
			storeManager = new StoreManagerImpl();
		}
		return storeManager;
	}

}
