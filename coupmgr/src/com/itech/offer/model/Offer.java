package com.itech.offer.model;

public class Offer {

	private String description;
	private String validity;

	public Offer(String validity, String description) {
		this.validity = validity;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValidity() {
		return this.validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

}
