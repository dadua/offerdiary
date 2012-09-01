package com.itech.alert.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itech.alert.model.Alert;
import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.user.model.User;

public class HibernateAlertDAOImpl extends HibernateCommonBaseDAO<Alert> implements AlertDAO{

	@Override
	public void delete(Alert alert) {
		Session sess = getSession();
		sess.delete(alert);
	}

	@Override
	public boolean deleteAlertsFor(String dataType, long dataId) {
		Session sess = getSession();
		Query query = sess.createQuery("delete from "+getEntityClassName()+" where dataType = :dataType and dataId =:dataId");
		query.setParameter("dataType", dataType);
		query.setParameter("dataId", dataId);
		query.executeUpdate();
		return true;
	}

	@Override
	public List<Alert> getAlertsForUser(User user) {
		Session sess = getSession();
		Query query = sess.createQuery(" from "+getEntityClassName()+" where user =:user");
		query.setParameter("user", user);
		return query.list();
	}


	@Override
	public List<Alert> getAlertsForDataType(String dataType, long dataId) {
		String hql = "from " + getEntityClassName() + " where dataType = :dataType and dataId =:dataId";
		Query query = getSession().createQuery(hql);
		query.setParameter("dataType", dataType);
		query.setParameter("dataId", dataId);
		return query.list();
	}


	@Override
	public void deleteByIds(List<Long> alertIds) {
		Session sess = getSession();
		Query  query = sess.createQuery("delete from "+getEntityClassName()+" where id IN (:ids)");
		query.setParameterList("ids", alertIds);
		query.executeUpdate();
	}


	@Override
	public void markAlertsRead(User loggedInUser) {
		Session sess = getSession();
		Query query=sess.createQuery("update "+ getEntityClassName() +" set alertStatus =:alertStatus where user =:user");
		query.setParameter("alertStatus", Alert.AlertStatus.READ);
		query.setParameter("user", loggedInUser);
		query.executeUpdate();
	}

	@Override
	protected Class getEntityClass() {
		return Alert.class;
	}

}
