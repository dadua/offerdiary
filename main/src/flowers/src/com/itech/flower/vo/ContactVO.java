package com.itech.flower.vo;

import com.itech.flower.model.Contact;
import com.itech.flower.model.Contact.ContactType;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Supplier;

public class ContactVO {

	private Long id;

	private String type;

	private String name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public static ContactVO toVO (Contact contact) {

		ContactVO contactVO = new ContactVO();
		contactVO.setId(contact.getId());
		contactVO.setType(contact.getType().name());
		contactVO.setName(contact.getName());
		return contactVO;
	}

	public static Contact fromVO(ContactVO contactVO) {

		if (ContactType.CUSTOMER.name().equalsIgnoreCase(contactVO.getType())){
			Customer customer = new Customer();
			customer.setId(contactVO.getId());
			customer.setName(contactVO.getName());
			return customer;
		} else if (ContactType.SUPPLIER.name().equalsIgnoreCase(contactVO.getType())){
			Supplier supplier = new Supplier();
			supplier.setId(contactVO.getId());
			supplier.setName(contactVO.getName());
			return supplier;
		}

		return null;


	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
