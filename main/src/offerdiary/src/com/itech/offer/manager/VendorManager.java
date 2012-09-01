package com.itech.offer.manager;

import java.util.List;

import com.itech.offer.model.Vendor;

public interface VendorManager {
	public List<Vendor> getVendorsFor(String vendorName, int maxResults);
	public Vendor saveOrUpdateVendor(Vendor vendor);
	public Vendor getVendorFor(Long vendorId);
	public Vendor getVendorByName(String name);

}
