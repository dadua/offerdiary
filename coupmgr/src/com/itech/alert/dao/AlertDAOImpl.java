package com.itech.alert.dao;

import java.sql.Connection;
import java.util.List;

import com.itech.alert.model.Alert;
import com.itech.common.db.CommonBaseDAOImpl;

public class AlertDAOImpl extends CommonBaseDAOImpl<Alert> implements AlertDAO {

	@Override
	public boolean addOrUpdate(Alert object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addOrUpdate(List<Alert> objects) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long uniqueId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(List<Alert> objs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Alert> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alert getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnection(Connection con) {
		// TODO Auto-generated method stub

	}

}
