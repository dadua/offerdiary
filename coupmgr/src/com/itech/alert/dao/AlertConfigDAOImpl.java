package com.itech.alert.dao;

import java.sql.Connection;
import java.util.List;

import com.itech.alert.model.AlertConfig;
import com.itech.common.db.CommonBaseDAOImpl;

public class AlertConfigDAOImpl extends CommonBaseDAOImpl<AlertConfig> implements
AlertConfigDAO {

	@Override
	public boolean addOrUpdate(AlertConfig object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addOrUpdate(List<AlertConfig> objects) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long uniqueId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(List<AlertConfig> objs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AlertConfig> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlertConfig getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnection(Connection con) {
		// TODO Auto-generated method stub

	}

}
