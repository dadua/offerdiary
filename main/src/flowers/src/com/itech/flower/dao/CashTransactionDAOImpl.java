package com.itech.flower.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.CashTransaction;
import com.itech.flower.model.Contact;

public class CashTransactionDAOImpl extends HibernateCommonBaseDAO<CashTransaction> implements
CashTransactionDAO {

	@Override
	protected Class getEntityClass() {
		return CashTransaction.class;
	}

	@Override
	public List<CashTransaction> getCashTransactionsFor(Contact contact) {
		String hql = "select ct from " + getEntityClassName() + " ct where contact.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", contact.getId());
		List result = query.list();
		return result;
	}

	@Override
	public List<CashTransaction> getCashTransactionsFor(String searchString) {
		String hql = "select ct from " + getEntityClassName() + " ct where ct.uniqueId like :searchString or ct.contact.name like :searchString";
		Query query = getSession().createQuery(hql);
		query.setParameter("searchString", searchString);
		List result = query.list();
		return result;
	}

	@Override
	public void deleteTransactionsFor(Contact contact) {
		String hql = "delete from  " + getEntityClassName() + " where contact = :contact ";
		Query query = getSession().createQuery(hql);
		query.setParameter("contact", contact);
		query.executeUpdate();
	}





}
