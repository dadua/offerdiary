package com.itech.coupon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itech.common.db.CommonBaseDAOImpl;
import com.itech.common.db.DBConnectionManager;
import com.itech.coupon.model.Coupon;
import com.itech.coupon.model.CouponPermission;
import com.itech.coupon.model.Store;
import com.itech.coupon.model.User;
import com.itech.coupon.model.constants.CouponModelConstants;

public class CouponDAOImpl extends CommonBaseDAOImpl<Coupon> implements CouponDAO {

	@Override
	public Coupon getByAutoGuid(String guid) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + CouponModelConstants.TABLE_COUPON + "" +
			" where AUTOGUID = ?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, guid);
			rs = ps.executeQuery();
			Coupon coupons = readSingleDataFrom(rs);
			return coupons;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Coupon> getByOwner(User owner) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + CouponModelConstants.TABLE_COUPON + "" +
			" where owner_id = ?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, owner.getId());
			rs = ps.executeQuery();
			List<Coupon> coupons = readDataFrom(rs);
			return coupons;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Coupon> getByStore(long storeId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + CouponModelConstants.TABLE_COUPON + "" +
			" where store_id = ?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, storeId);
			rs = ps.executeQuery();
			List<Coupon> coupons = readDataFrom(rs);
			return coupons;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Coupon> getByTag(String tag) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + CouponModelConstants.TABLE_COUPON + "" +
			" where tags like ?";
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, "%" + tag + "%");
			rs = ps.executeQuery();
			List<Coupon> coupons = readDataFrom(rs);
			return coupons;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Coupon> getByTags(List<String> tags) {
		if (tags.size() == 0) {
			return new ArrayList<Coupon>();
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + CouponModelConstants.TABLE_COUPON + "" +
			" where 1=0 ";
			for (int i =0; i <= tags.size(); i++) {
				sql += " or tags like ? ";
			}
			ps = getConnection().prepareStatement(sql);
			int indexCount = 1;
			for (String tag : tags) {
				indexCount++;
				ps.setString(indexCount, "%" + tag + "%");
			}
			rs = ps.executeQuery();
			List<Coupon> coupons = readDataFrom(rs);
			return coupons;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public boolean addOrUpdate(Coupon coupon) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		coupons.add(coupon);
		return addOrUpdate(coupons);
	}

	@Override
	public boolean addOrUpdate(List<Coupon> coupons) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "insert into " + CouponModelConstants.TABLE_COUPON + "" +
			" (STORE_ID, CODE, DISCOUNT, DETAIL, CREATION_DATE, EXPIRY_DATE, OWNER_ID, PERMISSION, TAGS, AUTOGUID) " +
			" values (?, ?, ?, ?, ?, ?,?, ?, ?, ?)";
			ps = getConnection().prepareStatement(sql);
			for (Coupon coupon : coupons) {
				ps.setLong(1, coupon.getStoreId());
				ps.setString(2, coupon.getCode());
				ps.setFloat(3, coupon.getDiscount());
				ps.setString(4, coupon.getDetail());
				ps.setDate(5, coupon.getCreationDate());
				ps.setDate(6, coupon.getExpiryDate());
				//TODO: Duds to take care of this issue
				if (coupon.getOwner() != null )  {
					ps.setLong(7, coupon.getOwner().getId());
				} else {
					ps.setLong(7, -1);
				}
				ps.setString(8, coupon.getPermission().toString());
				ps.setString(9, coupon.getTags());
				ps.setString(10, coupon.getAutoGUID());
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
			String sql = "delete from  " + CouponModelConstants.TABLE_COUPON + "" +
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
	public boolean delete(List<Coupon> coupons) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "delete from  " + CouponModelConstants.TABLE_COUPON + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			for (Coupon store : coupons) {
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
	public List<Coupon> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coupon getById(long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + CouponModelConstants.TABLE_COUPON + "" +
			" where id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			Coupon coupon = readSingleDataFrom(rs);
			return coupon;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Coupon> getBy(User owner, Store store) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + CouponModelConstants.TABLE_COUPON + "" +
			" where store_id = ? and owner_id=?";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, store.getId());
			ps.setLong(2, owner.getId());
			rs = ps.executeQuery();
			List<Coupon> coupons = readDataFrom(rs);
			return coupons;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	@Override
	public List<Coupon> getBy(User owner, List<String> tags) {
		if (tags.size() == 0) {
			return new ArrayList<Coupon>();
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from  " + CouponModelConstants.TABLE_COUPON + "" +
			" where owner_id= ? and (1=0 ";
			for (int i =0; i <= tags.size(); i++) {
				sql += " or tags like ? ";
			}
			sql +=")";
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, owner.getId());

			int indexCount = 1;
			for (String tag : tags) {
				indexCount++;
				ps.setString(indexCount, "%" + tag + "%");
			}
			rs = ps.executeQuery();
			List<Coupon> coupons = readDataFrom(rs);
			return coupons;
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e);
		} finally {
			DBConnectionManager.cleanDbPsRS(ps, rs);
		}
	}

	private Coupon readSingleDataFrom(ResultSet rs) throws SQLException {
		List<Coupon> coupons = readDataFrom(rs);
		return coupons.size() == 0? null : coupons.get(0);
	}

	private List<Coupon> readDataFrom(ResultSet resultSet) throws SQLException {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		while (resultSet.next()) {
			Coupon coupon = new Coupon();
			coupon.setId(resultSet.getLong(CouponModelConstants.COL_ID));
			coupon.setCode(resultSet.getString(CouponModelConstants.COL_CODE));
			coupon.setCreationDate(resultSet.getDate(CouponModelConstants.COL_CREATION_DATE));
			coupon.setDetail(resultSet.getString(CouponModelConstants.COL_DETAIL));
			coupon.setDiscount(resultSet.getFloat(CouponModelConstants.COL_DISCOUNT));
			coupon.setExpiryDate(resultSet.getDate(CouponModelConstants.COL_EXPIRY_DATE));
			User user = new User();
			user.setId(resultSet.getInt(CouponModelConstants.COL_OWNER_ID));
			coupon.setOwner(user);
			coupon.setPermission(CouponPermission.valueOf(resultSet.getString(CouponModelConstants.COL_PERMISSION)));
			coupon.setStoreId(resultSet.getLong(CouponModelConstants.COL_STORE_ID));
			coupon.setTags(resultSet.getString(CouponModelConstants.COL_TAGS));
			coupon.setAutoGUID(resultSet.getString(CouponModelConstants.COL_AUTOGUID));
			coupons.add(coupon);
		}
		return coupons;
	}


}
