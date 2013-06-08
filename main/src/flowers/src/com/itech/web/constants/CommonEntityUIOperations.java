package com.itech.web.constants;

public enum CommonEntityUIOperations {

	VIEW, EDIT, ADDNEW, SAVE, CANCEL;


	public String getOperationVal () {
		return this.name().toLowerCase();
	}
}
