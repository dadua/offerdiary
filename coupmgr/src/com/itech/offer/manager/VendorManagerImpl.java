package com.itech.offer.manager;

import java.util.List;

import com.itech.common.services.CommonBaseManager;
import com.itech.offer.dao.VendorDAO;
import com.itech.offer.model.Vendor;

public class VendorManagerImpl extends CommonBaseManager implements
VendorManager {
	private VendorDAO vendorDAO;

	@Override
	public Vendor getVendorFor(Long vendorId) {
		return vendorDAO.getById(vendorId);
	}

	@Override
	public List<Vendor> getVendorsFor(String vendorName, int maxResults) {
		return vendorDAO.getVendorsFor(vendorName, maxResults);
	}

	@Override
	public void saveOrUpdateVendor(Vendor vendor) {
		vendorDAO.addOrUpdate(vendor);

	}

	public void setVendorDAO(VendorDAO vendorDAO) {
		this.vendorDAO = vendorDAO;
	}

	public VendorDAO getVendorDAO() {
		return vendorDAO;
	}

}
