package com.itech.config;

import java.util.List;

/**
 * TODO
 * Some reflection limitation when using Property<List<String>>;
 *  so making a new class. This might be changed later when we know of
 *  a better solution
 */
public class PropertyList {
	private final String name;
	private final List<String> defaultValue;
	private List<String> value;


	PropertyList(String name) {
		this(name, null);
	}

	PropertyList(String name, List<String> defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
		this.value = this.defaultValue;
	}

	public String getName() {
		return this.name;
	}

	List<String> getDefaultValue() {
		return this.defaultValue;
	}

	public List<String> get() {
		return this.value;
	}

	void set(List<String> value) {
		this.value = value;
	}

	void useDefaultValue() {
		this.value = this.defaultValue;
	}
}
