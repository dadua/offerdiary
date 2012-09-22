package com.itech.common;

import java.util.Collection;
import java.util.UUID;

public class CommonUtilities {

	public static String getGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}


	public static void main(String args[]) {
		System.out.println(getGUID());
	}

	public static boolean isNullOrEmpty(String str){
		return ((str == null) || (str.trim().equals("")));
	}

	public static boolean isNotEmpty(String str){
		return !isNullOrEmpty(str);
	}

	public static boolean isNullOrEmpty(Collection<?> collection){
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> collection){
		return !isNullOrEmpty(collection);
	}

	public static boolean isNullOrZero(Integer i){
		return ((i == null) || (i == 0));
	}

}
