package com.itech.common.db;


public abstract class PersistableEntity {

	public abstract boolean isTransient();

	public abstract Long getId();

}
