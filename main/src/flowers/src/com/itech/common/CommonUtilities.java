package com.itech.common;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import com.itech.common.db.PersistableEntity;
import com.itech.flower.model.Customer;
import com.itech.flower.model.Flower;
import com.itech.supplier.model.Supplier;

public class CommonUtilities {
	public static final long MILLIS_IN_A_DAY = 24*60*60*1000;

	public static String getGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static String getUniqueId(String moduleName) {
		long currentTimeMillis = System.currentTimeMillis();
		int random2 = new Random(99).nextInt();
		int random3 = new Random(5).nextInt();
		long timeComponent = currentTimeMillis/random2;
		String guuid = getGUID();
		String guidPart1 = guuid.substring(0, guuid.length()/random3);
		String guidPart2 = guuid.substring(guuid.length()/random3);
		String uniqueString = moduleName.hashCode() *3 + guidPart1 + timeComponent + guidPart2 + random2 ;
		return uniqueString.replace("-", "");
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

	public static String getUniqueId(PersistableEntity object) {
		if (object.isTransient()) {
			return null;
		}
		if (object instanceof Supplier) {
			Supplier supplier = (Supplier) object;
			return "SUP" + supplier.getId();
		}

		if (object instanceof Customer) {
			Customer customer = (Customer) object;
			return "CUS" + customer.getId();
		}

		if (object instanceof Flower) {
			Flower flower = (Flower) object;
			return "FLW" + flower.getId();
		}
		return object.getId().toString();
	}

}
