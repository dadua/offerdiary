package com.itech.coupon.dao;

import com.itech.common.db.CommonBaseDAO;
import com.itech.coupon.model.User;

public interface UserDAO extends CommonBaseDAO<User>{

	public User getByUserId(String userId);

}
