package com.itech.flower.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="SUPPLIERS")
@Inheritance(strategy=InheritanceType.JOINED)
public class Supplier extends Contact {

	public Supplier() {
		this.type = ContactType.SUPPLIER;
	}


}
