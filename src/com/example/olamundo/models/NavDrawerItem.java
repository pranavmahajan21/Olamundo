package com.example.olamundo.models;

public class NavDrawerItem {

	private String title;
	private int icon;
	private String smallText = "0";
	// boolean to set visiblity of the counter
	private boolean isSmallTextVisible = false;
	
	public NavDrawerItem(){}

	public NavDrawerItem(String title, int icon){
		this.title = title;
		this.icon = icon;
	}
	
	public NavDrawerItem(String title, int icon, boolean isSmallTextVisible, String smallText){
		this.title = title;
		this.icon = icon;
		this.isSmallTextVisible = isSmallTextVisible;
		this.smallText = smallText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getSmallText() {
		return smallText;
	}

	public void setSmallText(String smallText) {
		this.smallText = smallText;
	}

	public boolean isSmallTextVisible() {
		return isSmallTextVisible;
	}

	public void setSmallTextVisible(boolean isSmallTextVisible) {
		this.isSmallTextVisible = isSmallTextVisible;
	}
	
}
