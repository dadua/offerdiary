package com.itech.alert.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.AlertConfig.ActivationStatus;
import com.itech.common.db.hibernate.HibernateCommonBaseDAO;

public class HibernateAlertConfigDAOImpl extends HibernateCommonBaseDAO<AlertConfig> implements AlertConfigDAO{

	@Override
	public void delete(AlertConfig alertConfig) {
		Session sess = getSession();
		sess.delete(alertConfig);
	}

	@Override
	public boolean deleteForDataType(String dataType, long dataId) {
		Session sess = getSession();
		Query query = sess.createQuery("delete from "+ getEntityClassName()+" where dataType =:dataType and dataId =:dataId");
		query.setParameter("dataType", dataType);
		query.setParameter("dataId", dataId);
		query.executeUpdate();
		return true;
	}

	@Override
	public List<AlertConfig> getAllAlertConfigExpiredWithStatus(
			ActivationStatus activationStatus) {
		Session sess = getSession();
		Query query = sess.createQuery("from " + getEntityClassName() + " where status = :status and triggerTime < :now");
		query.setParameter("status", activationStatus);
		query.setParameter("now", new Date(System.currentTimeMillis()));
		return query.list();
	}

	@Override
	public List<AlertConfig> getAllAlertConfigWithStatus(ActivationStatus status) {
		Session sess = getSession();
		Query query = sess.createQuery("from "+getEntityClassName()+" where status := status");
		query.setParameter("status", "status");
		return query.list();
	}

	@Override
	protected Class getEntityClass() {
		return AlertConfig.class;
	}

}
