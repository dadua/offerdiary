package com.itech.common;

import java.util.UUID;

public class CommonUtilities {

	public static String getGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}


	public static void main(String args[]) {
		System.out.println(getGUID());
	}
}
