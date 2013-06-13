package com.itech.flower.dao;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Contact;

public class ContactDAOImpl extends HibernateCommonBaseDAO<Contact> implements
ContactDAO {

	@Override
	protected Class getEntityClass() {
		return Contact.class;
	}


}
