package com.itech.config;


public class Property<T> {
	private String name;
	private T defaultValue;
	private T value;


	Property(String name) {
		this(name, null);
	}

	Property(String name, T defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return this.name;
	}

	T getDefaultValue() {
		return this.defaultValue;
	}

	public T get() {
		return this.value;
	}

	void set(T value) {
		this.value = value;
	}

	void useDefaultValue() {
		this.value = this.defaultValue;
	}
}