package com.itech.user.dao;

import com.itech.common.db.CommonBaseDAO;
import com.itech.user.model.User;

public interface UserDAO extends CommonBaseDAO<User>{

	public User getByUserId(String userId);

}
