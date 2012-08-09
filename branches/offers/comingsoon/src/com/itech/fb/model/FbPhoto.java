package com.itech.fb.model;

public class FbPhoto {
	
	private String id;
	
	private String caption;//name
	
	private String icon;
	
	private String fullPhotoUrl;//source
	
	private String height;
	
	private String width;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getFullPhotoUrl() {
		return fullPhotoUrl;
	}
	public void setFullPhotoUrl(String fullPhotoUrl) {
		this.fullPhotoUrl = fullPhotoUrl;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
}
