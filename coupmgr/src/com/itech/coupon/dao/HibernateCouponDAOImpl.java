package com.itech.coupon.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.Store;
import com.itech.coupon.model.User;

public class HibernateCouponDAOImpl extends HibernateCommonBaseDAO<Coupon> implements CouponDAO{

	@Override
	protected String getClassName() {
		return Coupon.class.getName();
	}

	@Override
	public List<Coupon> getBy(User owner, Store store) {
		String hql = "from " + getClassName() + " where owner = :owner";
		Query query = getSession().createQuery(hql);
		query.setParameter("owner", owner);
		List list = query.list();
		return list;
	}

	@Override
	public List<Coupon> getBy(User owner, List<String> tags) {
		String hql = "from " + getClassName() + " where owner = :owner";
		Query query = getSession().createQuery(hql);
		query.setParameter("owner", owner);
		List list = query.list();
		return list;
	}

	@Override
	public Coupon getByAutoGuid(String guid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coupon> getByOwner(User owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coupon> getByStore(long storeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coupon> getByTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coupon> getByTags(List<String> tags) {
		// TODO Auto-generated method stub
		return null;
	}

}
