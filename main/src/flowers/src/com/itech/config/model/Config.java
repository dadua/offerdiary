package com.itech.config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name=ConfigModelConstants.TABLE_CONFIG)
public class Config  implements Cloneable{
	public enum ParamType{
		TEXT, TEXTLIST, BOOLEAN, NUMBER, DECIMAL
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=ConfigModelConstants.COL_ID)
	private Long id;


	@Column(name=ConfigModelConstants.COL_CONTEXT)
	private String context;


	@Column(name=ConfigModelConstants.COL_NAME)
	private String name;

	@Column(name=ConfigModelConstants.COL_VALUE)
	private String value;

	@Column(name=ConfigModelConstants.COL_VALUE_TYPE)
	private String type;


	public Config(){

	}

	public Config(String context,String name,String value,String type){
		this.context = context;
		this.name = name;
		this.value = value;
		this.type = type;
	}

	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch( CloneNotSupportedException e )
		{
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Config other = (Config) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}


	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
