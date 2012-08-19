package com.itech.config;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.itech.common.exeption.CommonException;
import com.itech.common.services.Initialize;
import com.itech.config.model.Config;

public abstract class ConfigLoader implements Initialize {
	private static final Logger logger = Logger.getLogger(ConfigLoader.class);
	private final Class configClass;
	protected final HashMap<String, Config> paramMap = new HashMap<String, Config>();
	protected final HashMap<String, Field> paramPropertyMap = new HashMap<String, Field>();
	protected final HashMap<String, Field> paramPropertyListMap = new HashMap<String, Field>();

	public ConfigLoader(Class configClass) {
		this.configClass = configClass;
	}

	@Override
	public void init() {
		logger.info("Loading config...");
		try {
			loadConfig();
			initializeConfig();
		} catch (Exception e) {
			logger.error("Unable to load server setting.", e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * TODO Comment
	 * @throws Exception
	 */
	protected abstract void loadConfig() throws Exception;

	private void initializeConfig(){
		Field[] fields = configClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.getType() == Property.class) {
				field.setAccessible(true);
				processProperty(field);
			} else if (field.getType() == PropertyList.class) {
				field.setAccessible(true);
				processPropertyList(field);
			}
		}
	}

	public synchronized void updateConfigParam(String configName, String value) throws Exception {
		Config configParam = getConfigParamFor(configName);
		configParam.setValue(value);
		List<Config> configParamList = new ArrayList<Config>();
		configParamList.add(configParam);
		updateConfigParam(configParamList);
	}

	public synchronized void updateConfigParam(List<Config> params) throws Exception {
		boolean updatedInSource = updateConfigInSource(params);
		if (updatedInSource) {
			updateInMemoryConfig(params);
		}
	}

	/**
	 * Update config values in source i.e. In Database or any other data source.
	 * @param params  Config Params
	 * @return status , If success then return true else false.
	 */
	protected abstract boolean updateConfigInSource(List<Config> params) throws Exception;

	private void updateInMemoryConfig(List<Config> paramsToUpdate) {
		for (Config configParam : paramsToUpdate) {
			try {
				Config configParamFromSource = paramMap.get(configParam.getName());
				if (configParamFromSource == null) {
					continue;
				}
				configParamFromSource.setValue(configParam.getValue());
				Field field = paramPropertyMap.get(configParamFromSource.getName());
				if(field == null) {
					field = paramPropertyListMap.get(configParamFromSource.getName());
					if(field == null) {
						continue;
					}
					processPropertyList(field);
				} else {
					processProperty(field);
				}
			}catch (Exception e) {
				throw new CommonException("Unable to update config for param: " + configParam.getName(), e);
			}
		}
	}

	protected void processProperty(final Field field) {
		Property<?> property;

		try {
			property = (Property<?>) field.get(null);
		} catch (Throwable e) {
			throw new RuntimeException("Error processing property field " + field.getName(), e);
		}

		if (!property.getName().equals(field.getName())) {
			handlePropertyFieldUnequality(field.getName(), property.getName());
		}

		String valueString = null;
		Config param = paramMap.get(property.getName());
		if (param != null) {
			valueString = param.getValue();
			paramPropertyMap.put(property.getName(), field);
		}

		Class<?> propertyType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
		try {
			loadPropertyFromString(property, propertyType, valueString);
		} catch (Exception e) {
			logger.error("failed to update config property: " + property.getName() + "  with value: " + valueString);
			throw new CommonException("failed to update config property: " + property.getName() + "  with value: " + valueString, e);
		}
	}

	protected void handlePropertyFieldUnequality(final String fieldname, final String propertyName) {
		throw new IllegalArgumentException("Invalid name for property " + fieldname + ", expected "
				+ fieldname + ", actual " + propertyName);
	}

	protected void processPropertyList(final Field field) {
		PropertyList property;

		try {
			property = (PropertyList) field.get(null);
		} catch (Throwable e) {
			throw new CommonException("Error processing property field " + field.getName(), e);
		}

		if (!property.getName().equals(field.getName())) {
			handlePropertyFieldUnequality(field.getName(), property.getName());
		}

		String valueString = null;
		Config param = paramMap.get(property.getName());
		if (param != null) {
			valueString = param.getValue();
			paramPropertyListMap.put(property.getName(),field);
		}
		try {
			loadStringListPropertyFromString(property, valueString);
		} catch (Exception e) {
			logger.error("failed to update config property: " + property.getName() + "  with value: " + valueString);
			throw new CommonException("failed to update config property: " + property.getName() + "  with value: " + valueString, e);
		}
	}

	protected void loadStringListPropertyFromString(final PropertyList property, final String valueString) {

		if (valueString == null) {
			if (property.getDefaultValue() == null) {
				throw new IllegalArgumentException("Missing value for required property " + property.getName());
			}
			property.useDefaultValue();
			return;
		}

		String strList[] = valueString.split(",");
		List<String> propList = new ArrayList<String>();
		for (int i = 0; i < strList.length; i++) {
			String str = strList[i].trim();
			if (!"".equals(str)) {
				propList.add(str);
			}
		}
		property.set(propList);
	}

	@SuppressWarnings("unchecked")
	protected void loadPropertyFromString(final Property<?> property, final Class<?> propertyType,
			final String valueString) {

		try {
			if (valueString == null) {
				if (property.getDefaultValue() == null) {
					throw new IllegalArgumentException("Missing value for required property " + property.getName());
				}

				property.useDefaultValue();
				return;
			}

			if (propertyType == String.class) {
				((Property<String>) property).set(valueString);
			} else if (propertyType == Boolean.class) {
				((Property<Boolean>) property).set(Boolean.parseBoolean(valueString));
			} else if (propertyType == Long.class) {
				((Property<Long>) property).set(Long.parseLong(valueString));
			} else if (propertyType == Integer.class) {
				((Property<Integer>) property).set(Integer.parseInt(valueString));
			} else if (propertyType == Short.class) {
				((Property<Short>) property).set(Short.parseShort(valueString));
			} else if (propertyType == Float.class) {
				((Property<Float>) property).set(Float.parseFloat(valueString));
			}
			else {
				throw new IllegalArgumentException("Unsupported property type " + propertyType.getName() + ", property "
						+ property.getName());
			}
		} catch (Exception e) {
			logger.error("Unable to parse property:" + property.getName());
			throw new RuntimeException(e);
		}

	}


	public List<Config> getAllConfigParams() {
		List<Config> params = new ArrayList<Config>();
		for (Config param : paramMap.values()) {
			params.add((Config)param.clone());
		}
		return params;
	}

	public Config getConfigParamFor(String paramName) {
		Config configParam = paramMap.get(paramName);
		if (configParam == null) {
			return null;
		}
		return (Config) configParam.clone();
	}

}
