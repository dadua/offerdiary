package com.itech.flower.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;



@Entity
@Table(name="CUSTOMERS")
@Inheritance(strategy=InheritanceType.JOINED)
public class Customer extends Contact {
	public Customer() {
		this.type = ContactType.CUSTOMER;
	}


}
