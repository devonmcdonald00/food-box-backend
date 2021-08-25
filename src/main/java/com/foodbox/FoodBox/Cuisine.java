package com.foodbox.FoodBox;

public class Cuisine {
	private String cuisine;
	private boolean enabled;
	private String flag_image_url;
	
	public String getCuisine() {
		return cuisine;
	}
	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getFlagImageURL() {
		return this.flag_image_url;
	}
	
	public void setFlagImageURL(String flag_image_url) {
		this.flag_image_url = flag_image_url;
	}
	
	
}
