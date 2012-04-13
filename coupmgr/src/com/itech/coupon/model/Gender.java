package com.itech.coupon.model;

public enum Gender {
	M, F, UNKNOWN;

	public static Gender getGender(String genderStr) {
		if ("male".equalsIgnoreCase(genderStr) || "M".equalsIgnoreCase(genderStr)) {
			return M;
		} else if ("female".equalsIgnoreCase(genderStr) || "F".equalsIgnoreCase(genderStr)) {
			return F;
		} else {
			return UNKNOWN;
		}
	}

}
