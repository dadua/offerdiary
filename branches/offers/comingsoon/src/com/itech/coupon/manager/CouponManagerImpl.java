package com.itech.coupon.manager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.itech.common.CommonUtilities;
import com.itech.coupon.dao.CouponDAO;
import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.Store;
import com.itech.coupon.model.User;
import com.itech.event.coupon.OfferEventGenerator;


public class CouponManagerImpl implements CouponManager{
	private UserManager userManager;
	private CouponDAO couponDAO;
	private StoreManager storeManager;
	private OfferEventGenerator couponEventGenerator;

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
		String guid = CommonUtilities.getGUID();
		coupon.setAutoGUID(guid);
		if (coupon.getExpiryDate() == null && coupon.getExpiryDateInMillis() !=0) {
			coupon.setExpiryDate(new Date(coupon.getExpiryDateInMillis()));
		}
		getCouponDAO().addOrUpdate(coupon);
		Coupon couponFromDB = getCouponDAO().getByAutoGuid(coupon.getAutoGUID());
		coupon.setId(couponFromDB.getId());
		if (coupon.getExpiryDate() != null) {
			getCouponEventGenerator().offerCreated(coupon);
		}
	}

	@Override
	public void save(List<Coupon> coupons, User owner) {
		for (Coupon coupon: coupons) {
			String guid = CommonUtilities.getGUID();
			if (coupon.getExpiryDate() == null && coupon.getExpiryDateInMillis() !=0) {
				coupon.setExpiryDate(new Date(coupon.getExpiryDateInMillis()));
			}
			coupon.setAutoGUID(guid);
			coupon.setOwner(owner);
		}
		getCouponDAO().addOrUpdate(coupons);

		for (Coupon coupon: coupons) {
			Coupon couponFromDB = getCouponDAO().getByAutoGuid(coupon.getAutoGUID());
			coupon.setId(couponFromDB.getId());
			if (coupon.getExpiryDate() != null) {
				getCouponEventGenerator().offerCreated(coupon);
			}
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

	public void setCouponEventGenerator(OfferEventGenerator couponEventGenerator) {
		this.couponEventGenerator = couponEventGenerator;
	}

	public OfferEventGenerator getCouponEventGenerator() {
		return couponEventGenerator;
	}


}
