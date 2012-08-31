package com.itech.config.dao;

import java.util.List;

import com.itech.common.db.CommonBaseDAO;
import com.itech.config.model.Config;

public interface ConfigDAO extends CommonBaseDAO<Config>{
	public void updateBatch(List<com.itech.config.model.Config> configList);
}
