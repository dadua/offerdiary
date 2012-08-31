package com.itech.offer.fetchers.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationUtil {

	/* Tier 1 Cities*/
	public static final String DELHI= "Delhi";
	public static final String MUMBAI= "Mumbai";
	public static final String CHENNAI= "Chennai";
	public static final String KOLKATA= "Kolakata";
	public static final String BENGALURU= "Bengaluru";
	public static final String PUNE = "Pune";
	public static final String HYDERABAD= "Hyderabad";

	/*Tier 2 Cities*/
	public static final String NOIDA="Noida";
	public static final String FARIDABAD="Faridabad";
	public static final String GURGAON="Gurgaon";
	public static final String GHAZIABAD="Ghaziabad";


	/*City Zones*/
	public static final String CENTRAL_DELHI="Central Delhi";
	public static final String SOUTH_DELHI="South Delhi";
	public static final String EAST_DELHI="East Delhi";
	public static final String NORTH_DELHI="North Delhi";
	public static final String WEST_DELHI="West Delhi";

	/*Data Lists/Maps*/
	public static List<String> delhiSubLocations= new ArrayList<String>();
	public static List<String> primeCityList = new ArrayList<String>();
	public static Map<String, List<String>> primeCitySubLocationMap = new HashMap<String, List<String>>();


	static{
		primeCityList.add(DELHI);
		primeCityList.add(MUMBAI);
		primeCityList.add(CHENNAI);
		primeCityList.add(KOLKATA);
		primeCityList.add(BENGALURU);
		primeCityList.add(HYDERABAD);
		primeCityList.add(PUNE);


		delhiSubLocations.add(CENTRAL_DELHI);
		delhiSubLocations.add(EAST_DELHI);
		delhiSubLocations.add(WEST_DELHI);
		delhiSubLocations.add(NORTH_DELHI);
		delhiSubLocations.add(SOUTH_DELHI);
		delhiSubLocations.add(NOIDA);
		delhiSubLocations.add(FARIDABAD);
		delhiSubLocations.add(GURGAON);
		delhiSubLocations.add(GHAZIABAD);


		primeCitySubLocationMap.put(DELHI, delhiSubLocations);
	}
}
