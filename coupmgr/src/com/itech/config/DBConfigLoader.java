package com.itech.config;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.common.exeption.CommonException;
import com.itech.config.dao.ConfigDAO;
import com.itech.config.model.Config;

public class DBConfigLoader extends ConfigLoader {
	private static final Logger logger = Logger.getLogger(DBConfigLoader.class);

	private ConfigDAO configDAO;

	private HibernateSessionFactory hibernateSessionFactory;

	public DBConfigLoader(Class configClass) {
		super(configClass);
	}

	@Override
	protected void loadConfig() throws SQLException {
		List<Config> params = getConfigDAO().getAll();
		for(Config param : params) {
			paramMap.put(param.getName(), param);
		}
	}

	@Override
	public synchronized boolean updateConfigInSource(List<Config> params) throws Exception {
		List<Config> paramsToUpdate = new ArrayList<Config>();
		for (Config param : params) {
			Config configParam = paramMap.get(param.getName().trim());
			if (configParam == null) {
				throw new CommonException("Unsupported config parameter.");
			}
			configParam = (Config) configParam.clone();
			configParam.setValue(param.getValue());
			paramsToUpdate.add((Config)configParam.clone());
		}
		try {
			configDAO.updateBatch(paramsToUpdate);
			getHibernateSessionFactory().commitCurrentTransaction();
			return true;
		} catch (CommonException e) {
			logger.error("Unable to save config params in Database");
			getHibernateSessionFactory().rollbackCurrentTransaction();
			throw e;
		}
	}

	public void setConfigDAO(ConfigDAO configDAO) {
		this.configDAO = configDAO;
	}

	public ConfigDAO getConfigDAO() {
		return configDAO;
	}

	public void setHibernateSessionFactory(HibernateSessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	public HibernateSessionFactory getHibernateSessionFactory() {
		return hibernateSessionFactory;
	}

}
