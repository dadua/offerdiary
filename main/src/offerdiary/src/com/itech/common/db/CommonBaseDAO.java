package com.itech.common.db;

import java.util.List;

public interface CommonBaseDAO <T>{

	public boolean addOrUpdate(T object);
	public boolean addOrUpdate(List<T> objects);

	public boolean delete(Long uniqueId);

	public boolean delete(List<T> objs);

	T getById(Long id);

	List<T> getAll();

	boolean delete(Object object);

}
