package com.itech.alert.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itech.alert.model.Alert;
import com.itech.alert.model.AlertConfigModelConstant;
import com.itech.alert.model.AlertModelConstant;
import com.itech.common.db.CommonBaseDAOImpl;
import com.itech.common.db.DBConnectionManager;
import com.itech.coupon.model.User;

public class AlertDAOImpl extends CommonBaseDAOImpl<Alert> implements AlertDAO {

	@Override
	public boolean addOrUpdate(Alert alert) {
		List<Alert> alerts = new ArrayList<Alert>();
		alerts.add(alert);
		return addOrUpdate(alerts);
	}

	@Override
	public boolean addOrUpdate(List<Alert> alerts) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "insert into " + AlertModelConstant.TABLE_ALERT +
			" (DATA_TYPE, DATA_ID, ALERT_TYPE, ALERT_STATUS, CREATION_TIME, USER_ID, MESSAGE, HTML_MESSAGE) " +
			" values (?, ?, ?, ?, ?, ?, ?, ?)";
			ps = getConnection().prepareStatement(sql);
			for (Alert alert : alerts)  {
				ps.setString(1, alert.getDataType());
				ps.setFloat(2, alert.getDataId());
				ps.setString(3, alert.getAlertType());
				ps.setString(4, alert.getAlertStatus().toString());
				ps.setDate(5, alert.getCreationTime());
				ps.setLong(6, alert.getUserId());
				ps.setString(7, alert.getMessage());
				ps.setString(8, alert.getMessageHTML());
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
			String sql = "delete from  " + AlertModelConstant.TABLE_ALERT + "" +
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
	public boolean delete(List<Alert> alerts) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from  " + AlertConfigModelConstant.TABLE_ALERT_CONFIG + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			for (Alert alert :alerts) {
				ps.setLong(1, alert.getId());
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
	public Alert getById(long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + AlertModelConstant.TABLE_ALERT + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			Alert alert = readSingleDataFrom(rs);
			return alert;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	private Alert readSingleDataFrom(ResultSet rs) throws SQLException {
		List<Alert> alerts = readDataFrom(rs);
		return alerts.size() == 0? null : alerts.get(0);
	}

	private List<Alert> readDataFrom(ResultSet rs) throws SQLException {
		ArrayList<Alert> alerts = new ArrayList<Alert>();
		while (rs.next()) {
			Alert alert = new Alert();
			alert.setId(rs.getLong(AlertModelConstant.COL_ID));
			alert.setDataType(rs.getString(AlertModelConstant.COL_DATA_TYPE));
			alert.setDataId(rs.getLong(AlertModelConstant.COL_DATA_ID));
			alert.setAlertType(rs.getString(AlertModelConstant.COL_ALERT_TYPE));
			alert.setAlertStatus(Alert.AlertStatus.valueOf(rs.getString(AlertModelConstant.COL_ALERT_STATUS)));
			alert.setCreationTime(rs.getDate(AlertModelConstant.COL_CREATION_TIME));
			alert.setUserId(rs.getLong(AlertModelConstant.COL_USER_ID));
			alert.setMessage(rs.getString(AlertModelConstant.COL_MESSAGE));
			alert.setMessageHTML(rs.getString(AlertModelConstant.COL_HTML_MESSAGE));
			alerts.add(alert);
		}
		return alerts;
	}


	@Override
	public void delete(Alert alert) {
		List<Alert> alerts = new ArrayList<Alert>();
		alerts.add(alert);
		delete(alerts);
	}

	@Override
	public boolean  deleteAlertsFor(String dataType, long dataId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from  " + AlertModelConstant.TABLE_ALERT + "" +
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
	public List<Alert> getAlertsForUser(User user) {
		if(null == user){
			return null;
		}
		long userID = user.getId();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + AlertModelConstant.TABLE_ALERT + "" +
			" where user_id = ?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, userID);
			rs = ps.executeQuery();
			return readDataFrom(rs);
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Alert> getAlertsForDataType(String dataType, long dataId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + AlertModelConstant.TABLE_ALERT + "" +
			" where data_type = ? and data_id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, dataType);
			ps.setLong(2, dataId);
			rs = ps.executeQuery();
			return readDataFrom(rs);
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public void deleteByIds(List<Long> uniqueIds) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from " + AlertModelConstant.TABLE_ALERT +
			" where id=?" ;
			ps = getConnection().prepareStatement(sql);
			for (Long id : uniqueIds)  {
				ps.setLong(1, id);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public void markAlertsRead(User user) {
		if(null == user){
			return;
		}
		long user_id = user.getId();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "update "+ AlertModelConstant.TABLE_ALERT+ " set alert_status = '"+ Alert.AlertStatus.READ.toString()+"'"+
				" where user_id = ?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, user_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Alert> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
