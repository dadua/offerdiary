package com.itech.common.exeption;


public final class ReturnCode {

	private static final String DISPLAY_KEYPREFIX =  "itech.error.";

	private int code;
	private String displayKeyPrefix;

	public ReturnCode() {

	}

	public ReturnCode(int x) {
		code = x;
	}

	public ReturnCode(int x, String displayKeyPrefix) {
		this.code = x;
		this.displayKeyPrefix = displayKeyPrefix;
	}

	public int getCode() {
		return code;
	}

	public String getDisplayKey() {
		return ((displayKeyPrefix == null)? DISPLAY_KEYPREFIX : displayKeyPrefix)
		+ getCode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
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
		if (!(obj instanceof ReturnCode)) {
			return false;
		}
		ReturnCode other = (ReturnCode) obj;
		if (code != other.code) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ReturnCode: " + code;
	}

}
