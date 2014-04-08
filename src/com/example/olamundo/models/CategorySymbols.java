package com.example.olamundo.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CategorySymbols implements Serializable {

	private static final long serialVersionUID = -293836054156307185L;
	
	String backgroungColor;
	String categoryImageContentType;
	String categoryImageFileName;
	int categoryImageFileSize;
	Date categoryImageUpdatedAt;
	int categoryLevel;
	String categoryName;
	Date createdAt;
	String hebrew;
	int id;
	String imageURL;
	int memberID;
	int sequence;
	Date updatedAt;
	List<Symbols> symbols;
	
	public String getBackgroungColor() {
		return backgroungColor;
	}
	public void setBackgroungColor(String backgroungColor) {
		this.backgroungColor = backgroungColor;
	}
	public String getCategoryImageContentType() {
		return categoryImageContentType;
	}
	public void setCategoryImageContentType(String categoryImageContentType) {
		this.categoryImageContentType = categoryImageContentType;
	}
	public String getCategoryImageFileName() {
		return categoryImageFileName;
	}
	public void setCategoryImageFileName(String categoryImageFileName) {
		this.categoryImageFileName = categoryImageFileName;
	}
	public int getCategoryImageFileSize() {
		return categoryImageFileSize;
	}
	public void setCategoryImageFileSize(int categoryImageFileSize) {
		this.categoryImageFileSize = categoryImageFileSize;
	}
	public Date getCategoryImageUpdatedAt() {
		return categoryImageUpdatedAt;
	}
	public void setCategoryImageUpdatedAt(Date categoryImageUpdatedAt) {
		this.categoryImageUpdatedAt = categoryImageUpdatedAt;
	}
	public int getCategoryLevel() {
		return categoryLevel;
	}
	public void setCategoryLevel(int categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getHebrew() {
		return hebrew;
	}
	public void setHebrew(String hebrew) {
		this.hebrew = hebrew;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public int getMemberID() {
		return memberID;
	}
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<Symbols> getSymbols() {
		return symbols;
	}
	public void setSymbols(List<Symbols> symbols) {
		this.symbols = symbols;
	}

}
