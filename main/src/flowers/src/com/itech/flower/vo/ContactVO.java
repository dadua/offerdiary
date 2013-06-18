package com.itech.flower.vo;

import com.itech.flower.model.Contact;
import com.itech.flower.model.Contact.ContactType;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Supplier;

public class ContactVO {

	private Long id;

	private String type;

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
		return contactVO;
	}

	public static Contact fromVO(ContactVO contactVO) {

		if (contactVO.type == ContactType.CUSTOMER.name()){
			Customer customer = new Customer();
			customer.setId(contactVO.getId());
			return customer;
		} else if (contactVO.type == ContactType.SUPPLIER.name()){
			Supplier supplier = new Supplier();
			supplier.setId(contactVO.getId());
			return supplier;
		}

		return null;


	}

}
