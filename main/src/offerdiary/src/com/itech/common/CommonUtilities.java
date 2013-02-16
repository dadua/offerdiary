package com.itech.common;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

public class CommonUtilities {

	public static String getGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static String getUniqueId(String moduleName) {
		long currentTimeMillis = System.currentTimeMillis();
		int random = new Random(9999999).nextInt();
		int random2 = new Random(99).nextInt();
		int random3 = new Random(5).nextInt();
		long timeComponent = currentTimeMillis/random2;
		String guuid = getGUID();
		String guidPart1 = guuid.substring(0, guuid.length()/random3);
		String guidPart2 = guuid.substring(guuid.length()/random3);
		String uniqueString = moduleName.hashCode() *3  + moduleName + guidPart1 + timeComponent + guidPart2 + random2 + currentTimeMillis + random;
		return uniqueString;
	}


	public static void main(String args[]) {
		System.out.println(getUniqueId("offer"));
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
