package com.itech.coupon.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.coupon.model.Store;

public interface StoreDAO extends CommonBaseDAO<Store>{
	public List<Store> searchByTag(String tag);
	public List<Store> getByTags(List<String> tags);
	public List<Store> searchByName(String storeName);
}
