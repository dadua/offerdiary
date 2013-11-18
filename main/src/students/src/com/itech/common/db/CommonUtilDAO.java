package com.itech.common.db;

import java.util.List;


public interface CommonUtilDAO extends CommonBaseDAO<PersistableEntity> {
	public boolean delete(Class clazz, long uniqueId);
	<T> T getById(Class<T> clazz, Long id);
	<T> List<T> getAll(Class<T> clazz);
}
