package com.itech.config;

import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import com.itech.config.model.Config;

public class PropertiesConfigLoader extends ConfigLoader{
	private Properties properties;

	public PropertiesConfigLoader(Class configClass) {
		super(configClass);
	}

	public PropertiesConfigLoader(Class configClass, Properties properties) {
		super(configClass);
		this.setProperties(properties);
	}

	@Override
	protected void loadConfig() throws Exception {
		paramMap.clear();
		for (Entry<Object, Object> entry : getProperties().entrySet()) {
			Config configParam = new Config();
			configParam.setName((String)entry.getKey());
			configParam.setValue((String)entry.getValue());
			paramMap.put((String)entry.getKey(), configParam);
		}

	}

	@Override
	public boolean updateConfigInSource(List<Config> params) throws Exception {
		return true;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}

}
