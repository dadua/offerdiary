package com.itech.offer.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.offer.model.Vendor;

public interface VendorDAO extends CommonBaseDAO<Vendor>{
	public List<Vendor> getVendorsFor(String vendorName, int maxResults);

	public Vendor getVendorByName(String name);

}
