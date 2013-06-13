package com.itech.flower.dao;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.flower.model.DailyFlowerPrice;

public class DailyFlowerPriceDAOImpl extends HibernateCommonBaseDAO<DailyFlowerPrice> implements DailyFlowerPriceDAO{

	@Override
	protected Class getEntityClass() {
		return DailyFlowerPrice.class;
	}

}
