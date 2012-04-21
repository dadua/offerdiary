package com.itech.alert.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itech.alert.model.AlertConfig;
import com.itech.alert.model.AlertConfigModelConstant;
import com.itech.alert.model.AlertConfig.ActivationStatus;
import com.itech.common.db.CommonBaseDAOImpl;
import com.itech.common.db.DBConnectionManager;


public class AlertConfigDAOImpl extends CommonBaseDAOImpl<AlertConfig> implements
AlertConfigDAO {


	@Override
	public boolean addOrUpdate(AlertConfig config) {
		List<AlertConfig> configs = new ArrayList<AlertConfig>();
		configs.add(config);
		return addOrUpdate(configs);
	}

	@Override
	public boolean addOrUpdate(List<AlertConfig> configs) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "insert into " + AlertConfigModelConstant.TABLE_ALERT_CONFIG  +
			" (DATA_TYPE, DATA_ID, ALERT_TYPE,TRIGGER_TIME,CREATION_TIME, STATUS) " +
			" values (?, ?, ?, ?, ?, ?)";
			ps = getConnection().prepareStatement(sql);
			for (AlertConfig config : configs)  {
				ps.setString(1, config.getDataType());
				ps.setLong(2, config.getDataId());
				ps.setString(3, config.getAlertType());
				ps.setDate(4, config.getTriggerTime());
				ps.setDate(5, config.getCreationTime());
				ps.setString(6, config.getStatus().toString());
				ps.addBatch();
			}
			ps.executeBatch();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}


	@Override
	public boolean delete(long uniqueId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from  " + AlertConfigModelConstant.TABLE_ALERT_CONFIG + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, uniqueId);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}


	@Override
	public boolean delete(List<AlertConfig> configs) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from  " + AlertConfigModelConstant.TABLE_ALERT_CONFIG + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			for (AlertConfig config :configs) {
				ps.setLong(1, config.getId());
				ps.addBatch();
			}
			ps.executeBatch();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public AlertConfig getById(long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + AlertConfigModelConstant.TABLE_ALERT_CONFIG + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			AlertConfig config = readSingleDataFrom(rs);
			return config;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	private AlertConfig readSingleDataFrom(ResultSet rs) throws SQLException {
		List<AlertConfig> configs = readDataFrom(rs);
		return configs.size() == 0? null : configs.get(0);
	}

	private List<AlertConfig> readDataFrom(ResultSet resultSet) throws SQLException {
		ArrayList<AlertConfig> configs = new ArrayList<AlertConfig>();
		while (resultSet.next()) {
			AlertConfig config = new AlertConfig();
			config.setId(resultSet.getLong(AlertConfigModelConstant.COL_ID));
			config.setDataType(resultSet.getString(AlertConfigModelConstant.COL_DATA_TYPE));
			config.setDataId(resultSet.getLong(AlertConfigModelConstant.COL_DATA_ID));
			config.setAlertType(resultSet.getString(AlertConfigModelConstant.COL_ALERT_TYPE));
			config.setCreationTime(resultSet.getDate(AlertConfigModelConstant.COL_CREATION_TIME));
			config.setTriggerTime(resultSet.getDate(AlertConfigModelConstant.COL_TRIGGER_TIME));
			config.setStatus(AlertConfig.ActivationStatus.valueOf( resultSet.getString(AlertConfigModelConstant.COL_STATUS)));
			configs.add(config);
		}
		return configs;
	}


	@Override
	public void delete(AlertConfig config) {
		List<AlertConfig> configs = new ArrayList<AlertConfig>();
		configs.add(config);
		delete(configs);
	}

	@Override
	public boolean deleteForDataType(String dataType, long dataId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from  " + AlertConfigModelConstant.TABLE_ALERT_CONFIG + "" +
			" where data_type=? and data_id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, dataType);
			ps.setLong(2, dataId);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<AlertConfig> getAllAlertConfigExpiredWithStatus(
			ActivationStatus status) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + AlertConfigModelConstant.TABLE_ALERT_CONFIG + "" +
			" where status = ? and trigger_time < NOW()";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, status.toString());
			rs = ps.executeQuery();
			return readDataFrom(rs);
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<AlertConfig> getAllAlertConfigWithStatus(ActivationStatus status) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + AlertConfigModelConstant.TABLE_ALERT_CONFIG + "" +
			" where status = ?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, status.toString());
			rs = ps.executeQuery();
			return readDataFrom(rs);
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<AlertConfig> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
