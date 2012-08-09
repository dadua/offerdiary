package com.itech.coupon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itech.common.db.CommonBaseDAOImpl;
import com.itech.common.db.DBConnectionManager;
import com.itech.coupon.model.Store;
import com.itech.coupon.model.constants.StoreModelConstants;
import com.itech.coupon.model.constants.UserModelConstants;

public class StoreDAOImpl extends CommonBaseDAOImpl<Store> implements StoreDAO {

	@Override
	public List<Store> searchByTag(String tag) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + StoreModelConstants.TABLE_STORE + "" +
			" where tags like ?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, "%" + tag + "%");
			rs = ps.executeQuery();
			List<Store> stores = readDataFrom(rs);
			return stores;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Store> getByTags(List<String> tags) {
		if (tags.size() == 0) {
			return new ArrayList<Store>();
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + StoreModelConstants.TABLE_STORE + "" +
			" where 0=1 ";
			for (int i =0; i <= tags.size(); i++) {
				sql += " or tags like ? ";
			}
			ps = getConnection().prepareStatement(sql);
			for (int i =0; i <= tags.size(); i++) {
				ps.setString(i+1, "%" + tags.get(i) + "%");
			}
			rs = ps.executeQuery();
			List<Store> stores = readDataFrom(rs);
			return stores;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Store> searchByName(String storeName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + StoreModelConstants.TABLE_STORE + "" +
			" where name like ?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, "%" + storeName + "%");
			rs = ps.executeQuery();
			List<Store> stores = readDataFrom(rs);
			return stores;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public boolean addOrUpdate(Store store) {
		List<Store> stores = new ArrayList<Store>();
		stores.add(store);
		return addOrUpdate(stores);
	}

	@Override
	public boolean addOrUpdate(List<Store> stores) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "insert into " + UserModelConstants.TABLE_USER + "" +
			" (NAME, LOCATION, DESCRIPTION, WEB_URL, IMAGE_URL, TAGS) " +
			" values (?, ?, ?, ?, ?, ?)";
			ps = getConnection().prepareStatement(sql);
			for (Store store : stores) {
				ps.setString(1, store.getName());
				ps.setString(2, store.getLocation());
				ps.setString(3, store.getDescription());
				ps.setString(4, store.getWebUrl());
				ps.setString(5, store.getImageUrl());
				ps.setString(6, store.getTags());
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
			String sql = "delete from  " + StoreModelConstants.TABLE_STORE + "" +
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
	public boolean delete(List<Store> stores) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from  " + StoreModelConstants.TABLE_STORE + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			for (Store store : stores) {
				ps.setLong(1, store.getId());
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
	public List<Store> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Store getById(Long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + StoreModelConstants.TABLE_STORE + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			Store store = readSingleDataFrom(rs);
			return store;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	private Store readSingleDataFrom(ResultSet rs) throws SQLException {
		List<Store> stores = readDataFrom(rs);
		return stores.size() == 0? null : stores.get(0);
	}

	private List<Store> readDataFrom(ResultSet resultSet) throws SQLException {
		ArrayList<Store> stores = new ArrayList<Store>();
		while (resultSet.next()) {
			Store store = new Store();
			store.setDescription(resultSet.getString(StoreModelConstants.COL_DESCRIPTION));
			store.setId(resultSet.getLong(StoreModelConstants.COL_ID));
			store.setImageUrl(resultSet.getString(StoreModelConstants.COL_IMAGE_URL));
			store.setLocation(resultSet.getString(StoreModelConstants.COL_LOCATION));
			store.setName(resultSet.getString(StoreModelConstants.COL_NAME));
			store.setTags(resultSet.getString(StoreModelConstants.COL_TAGS));
			store.setWebUrl(resultSet.getString(StoreModelConstants.COL_WEB_URL));
			stores.add(store);
		}
		return stores;
	}


}
