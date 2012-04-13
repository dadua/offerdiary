package com.itech.fb.model;

public class FbPage {
	
	
	public static enum FbCategory {
		PRODUCT_SERVICE("Product/service"),
		AUTHOR("Author"),
		WEBSITE("Website"),
		APP("App"),
		INTERNET_SOFTWARE("Internet/software"),
		VIDEO("Video"),
		APPLICATION("Application"),
		COMPANY("Company"),
		ORGANIZATION("Organization");
		
		private String name;
		
		private FbCategory(String name) {
			this.setName(name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public static FbCategory getByName (String name) {
			for (FbCategory t : FbCategory.values()) {
				if (t.getName().compareTo(name) == 0) {
					return t;
				}
			}
			throw new RuntimeException("invalid value for: "+name);
		}
	}

	
	String id;
	
	String name;
	
	String picture;
	
	String link;
	
	String category;
	
	String description;
	
	String about;
	
	String personal_info;
	
	String talking_about_count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPersonal_info() {
		return personal_info;
	}

	public void setPersonal_info(String personal_info) {
		this.personal_info = personal_info;
	}

	public String getTalking_about_count() {
		return talking_about_count;
	}

	public void setTalking_about_count(String talking_about_count) {
		this.talking_about_count = talking_about_count;
	}
}
