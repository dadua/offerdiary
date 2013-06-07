package com.itech.flower.dao;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.Flower;

public class FlowerDAOImpl extends HibernateCommonBaseDAO<Flower> implements FlowerDAO {

	@Override
	protected Class getEntityClass() {
		return Flower.class;
	}

}
