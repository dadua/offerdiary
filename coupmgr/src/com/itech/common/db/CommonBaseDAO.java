package com.itech.common.db;

import java.sql.Connection;
import java.util.List;

public interface CommonBaseDAO <T>{

	public boolean addOrUpdate(T object);
	public boolean addOrUpdate(List<T> objects);

	public boolean delete(long uniqueId);

	public boolean delete(List<T> objs);

	T getById(long id);

	List<T> getAll();

	public void setConnection(Connection con);

}
