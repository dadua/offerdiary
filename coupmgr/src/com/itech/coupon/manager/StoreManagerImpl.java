package com.itech.coupon.manager;

import java.util.List;

import com.itech.coupon.dao.StoreDAO;
import com.itech.coupon.model.Store;

public class StoreManagerImpl implements StoreManager {
	private UserManager userManager;
	private StoreDAO storeDAO;

	@Override
	public Store getById(long id) {
		return getStoreDAO().getById(id);
	}

	@Override
	public void save(Store store) {
		getStoreDAO().addOrUpdate(store);
	}

	@Override
	public void save(List<Store> stores) {
		getStoreDAO().addOrUpdate(stores);

	}

	@Override
	public List<Store> searchByName(String nameSearchString) {
		return getStoreDAO().searchByName(nameSearchString);
	}

	@Override
	public List<Store> searchByTags(List<String> tags) {
		return getStoreDAO().getByTags(tags);
	}


	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setStoreDAO(StoreDAO storeDAO) {
		this.storeDAO = storeDAO;
	}

	public StoreDAO getStoreDAO() {
		return storeDAO;
	}


}
