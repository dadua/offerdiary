package com.itech.coupon.manager;

import java.util.List;

import com.itech.coupon.model.Store;

public interface StoreManager {
	public void save(Store store);
	public void save(List<Store> stores);
	public List<Store> searchByName(String nameSearchString);
	public List<Store> searchByTags(List<String> tags);
	public Store getById(long id);
}
