package com.itech.config.dao;

import java.util.List;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.config.model.Config;

public class ConfigDAOImpl extends HibernateCommonBaseDAO<Config> implements ConfigDAO {

	@Override
	protected Class getEntityClass() {
		return Config.class;
	}

	@Override
	public void updateBatch(List<Config> configList) {
		addOrUpdate(configList);

	}

}