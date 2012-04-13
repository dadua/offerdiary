package com.itech.coupon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itech.common.db.CommonBaseDAOImpl;
import com.itech.common.db.DBConnectionManager;
import com.itech.coupon.model.Gender;
import com.itech.coupon.model.LoginType;
import com.itech.coupon.model.User;
import com.itech.coupon.model.constants.UserModelConstants;

public class UserDAOImpl extends CommonBaseDAOImpl<User> implements UserDAO {

	@Override
	public User getByUserId(String userId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + UserModelConstants.TABLE_USER + "" +
			" where user_id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			User user = readSingleDataFrom(rs);
			return user;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public boolean addOrUpdate(User user) {
		List<User> users = new ArrayList<User>();
		users.add(user);
		return addOrUpdate(users);
	}

	@Override
	public boolean addOrUpdate(List<User> users) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "insert into " + UserModelConstants.TABLE_USER + "" +
			" (USER_ID, LOGIN_TYPE, PASSWORD, NAME, AGE, GENDER, LOCATION, LANGUAGE, EMAIL_ID) " +
			" values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = getConnection().prepareStatement(sql);
			for (User user : users) {
				ps.setString(1, user.getUserId());
				ps.setString(2, user.getLoginType().toString());
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getName());
				ps.setInt(5, user.getAge());
				ps.setString(6, user.getGender().toString());
				ps.setString(7, user.getLocation());
				ps.setString(8, user.getLanguage());
				ps.setString(9, user.getEmailId());
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
			String sql = "delete from  " + UserModelConstants.TABLE_USER + "" +
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
	public boolean delete(List<User> users) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from  " + UserModelConstants.TABLE_USER + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			for (User user : users) {
				ps.setLong(1, user.getId());
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
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + UserModelConstants.TABLE_USER + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			User user = readSingleDataFrom(rs);
			return user;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	private User readSingleDataFrom(ResultSet rs) throws SQLException {
		List<User> users = readDataFrom(rs);
		return users.size() == 0? null : users.get(0);
	}

	private List<User> readDataFrom(ResultSet resultSet) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		if (resultSet.next()) {
			User user = new User();
			user.setAge(resultSet.getInt(UserModelConstants.COL_AGE));
			user.setEmailId(resultSet.getString(UserModelConstants.COL_EMAIL_ID));
			user.setGender(Gender.valueOf(resultSet.getString(UserModelConstants.COL_GENDER)));
			user.setId(resultSet.getInt(UserModelConstants.COL_ID));
			user.setLanguage(resultSet.getString(UserModelConstants.COL_LANGUAGE));
			user.setLocation(resultSet.getString(UserModelConstants.COL_LOCATION));
			user.setName(resultSet.getString(UserModelConstants.COL_NAME));
			user.setPassword(resultSet.getString(UserModelConstants.COL_PASSWORD));
			user.setUserId(resultSet.getString(UserModelConstants.COL_USER_ID));
			user.setLoginType(LoginType.valueOf(resultSet.getString(UserModelConstants.COL_LOGIN_TYPE)));
			users.add(user);
		}
		return users;
	}

}
