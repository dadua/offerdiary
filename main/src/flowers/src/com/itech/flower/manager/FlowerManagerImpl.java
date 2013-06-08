package com.itech.flower.manager;

import java.util.List;

import com.itech.common.services.CommonBaseManager;
import com.itech.flower.dao.CustomerFlowerAssocDAO;
import com.itech.flower.dao.FlowerDAO;
import com.itech.flower.dao.SupplierFlowerAssocDAO;
import com.itech.flower.model.Customer;
import com.itech.flower.model.CustomerFlowerAssoc;
import com.itech.flower.model.Flower;
import com.itech.flower.model.Supplier;
import com.itech.flower.model.SupplierFlowerAssoc;

public class FlowerManagerImpl extends CommonBaseManager implements FlowerManager{
	private FlowerDAO flowerDAO;
	private CustomerFlowerAssocDAO customerFlowerAssocDAO;
	private SupplierFlowerAssocDAO supplierFlowerAssocDAO;


	@Override
	public void addOrUpdate(Flower flower) {
		getFlowerDAO().addOrUpdate(flower);
	}

	@Override
	public void delete(Flower flower) {
		getFlowerDAO().delete(flower);
	}

	@Override
	public Flower getFlowerByName(String name) {
		return getFlowerDAO().getFlowerFor(name);
	}

	@Override
	public List<Flower> searchFlowersFor(String searchString) {
		if (searchString == null) {
			searchString = "";
		}
		return getFlowerDAO().searchFlowerBy(searchString);
	}

	@Override
	public SupplierFlowerAssoc addFlowerToSupplier(Flower flower, Supplier supplier) {
		SupplierFlowerAssoc existingAssoc = getSupplierFlowerAssocDAO().getAssocFor(supplier, flower);
		if (existingAssoc != null) {
			return existingAssoc;
		}

		SupplierFlowerAssoc assoc = new SupplierFlowerAssoc(flower, supplier);
		getSupplierFlowerAssocDAO().addOrUpdate(assoc);
		return assoc;

	}

	@Override
	public List<Flower> getFlowersForSupplier(Supplier supplier) {
		return getSupplierFlowerAssocDAO().getFlowersFor(supplier);
	}

	@Override
	public List<Supplier> getSuppliersForFlower(Flower flower) {
		return getSupplierFlowerAssocDAO().getSuppliersFor(flower);
	}


	@Override
	public SupplierFlowerAssoc getAssocFor(Flower flower, Supplier supplier) {
		return getSupplierFlowerAssocDAO().getAssocFor(supplier, flower);
	}

	@Override
	public CustomerFlowerAssoc getAssocFor(Flower flower, Customer customer) {
		return getCustomerFlowerAssocDAO().getAssocFor(customer, flower);
	}
	@Override
	public CustomerFlowerAssoc addFlowerToCustomer(Flower flower, Customer customer) {
		CustomerFlowerAssoc existingAssoc = getCustomerFlowerAssocDAO().getAssocFor(customer, flower);
		if (existingAssoc != null) {
			return existingAssoc;
		}

		CustomerFlowerAssoc assoc = new CustomerFlowerAssoc(flower, customer);
		getCustomerFlowerAssocDAO().addOrUpdate(assoc);
		return assoc;
	}

	@Override
	public List<Flower> getFlowersForCustomer(Customer customer) {
		return getCustomerFlowerAssocDAO().getFlowersFor(customer);
	}

	@Override
	public List<Customer> getCustomersForFlower(Flower flower) {
		return getCustomerFlowerAssocDAO().getCustomersFor(flower);
	}

	public CustomerFlowerAssocDAO getCustomerFlowerAssocDAO() {
		return customerFlowerAssocDAO;
	}

	public void setCustomerFlowerAssocDAO(CustomerFlowerAssocDAO customerFlowerAssocDAO) {
		this.customerFlowerAssocDAO = customerFlowerAssocDAO;
	}

	public SupplierFlowerAssocDAO getSupplierFlowerAssocDAO() {
		return supplierFlowerAssocDAO;
	}

	public void setSupplierFlowerAssocDAO(SupplierFlowerAssocDAO supplierFlowerAssocDAO) {
		this.supplierFlowerAssocDAO = supplierFlowerAssocDAO;
	}

	public FlowerDAO getFlowerDAO() {
		return flowerDAO;
	}

	public void setFlowerDAO(FlowerDAO flowerDAO) {
		this.flowerDAO = flowerDAO;
	}


}
