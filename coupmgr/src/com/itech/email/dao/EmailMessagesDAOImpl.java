package com.itech.email.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.email.model.EmailMessages;
import com.itech.email.model.EmailMessages.EmailStatus;
import com.itech.email.vo.Email.EmailType;

public class EmailMessagesDAOImpl extends HibernateCommonBaseDAO<EmailMessages> implements EmailMessagesDAO{

	@Override
	public List<EmailMessages> getAllPendingEmailMessages() {
		String hql = "from " + getEntityClassName() + " where status = :status";
		Query query = getSession().createQuery(hql);
		query.setParameter("status", EmailStatus.PENDING);
		List list = query.list();
		return list;
	}

	@Override
	public List<EmailMessages> getPendingEmailMessagesByType(EmailType type) {
		String hql = "from " + getEntityClassName() + " where status = :status and email_type = :type";
		Query query = getSession().createQuery(hql);
		query.setParameter("status", EmailStatus.PENDING);
		query.setParameter("type", type);
		List list = query.list();
		return list;

	}

	@Override
	protected Class getEntityClass() {
		return EmailMessages.class;
	}

}
