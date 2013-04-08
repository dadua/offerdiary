package com.itech.config;

public class ProjectConfigs {
	public static final  Property<Boolean> initializeRedWineData = new Property<Boolean>("initializeRedWineData", true);
	public static final  Property<Boolean> initializeScotchWorldData = new Property<Boolean>("initializeScotchWorldData", true);
	public static final  Property<Integer> defaultOfferNotificationTriggerTimeInDays = new Property<Integer>("defaultOfferNotificationTriggerTimeInDays", 7);
	public static final  Property<String> defaultServerUrl = new Property<String>("defaultServerUrl", "offerdiary.com");
	public static final  Property<String> serverMode = new Property<String>("serverMode", "DEV");

}
