package com.itech.flower.manager;

import java.util.List;

import com.itech.flower.model.Customer;
import com.itech.flower.model.CustomerFlowerAssoc;
import com.itech.flower.model.Flower;
import com.itech.flower.model.Supplier;
import com.itech.flower.model.SupplierFlowerAssoc;

public interface FlowerManager {

	public void addOrUpdate(Flower flower);
	public void delete(Flower flower);
	public void delete(List<Long> Ids);
	public Flower getFlowerByName(String name);
	public Flower getFlowerById(long id);
	public List<Flower> searchFlowersFor(String searchString);



	public SupplierFlowerAssoc getAssocFor(Flower flower, Supplier supplier);
	public SupplierFlowerAssoc addFlowerToSupplier(Flower flower, Supplier supplier);
	public void  addFlowerToSupplier(List<Long> flowerIds, List<Long> supplierIds);
	public List<Flower> getFlowersForSupplier(Supplier supplier);
	public List<Supplier> getSuppliersForFlower(Flower flower);


	public CustomerFlowerAssoc getAssocFor(Flower flower, Customer customer);
	public CustomerFlowerAssoc addFlowerToCustomer(Flower flower, Customer customer);
	public void addFlowersToCustomers(List<Long> flowerIds, List<Long> customerIds);
	public List<Flower> getFlowersForCustomer(Customer customer);
	public List<Customer> getCustomersForFlower(Flower flower);

}
