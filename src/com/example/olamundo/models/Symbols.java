package com.example.olamundo.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Symbols implements Serializable {

	private static final long serialVersionUID = 4370089315489336808L;

	int categoryID;
	Date createdAt;
	String hebrew;

	boolean hideUnhide;
	boolean hideUnhideHebrew;
	int id;

	String imageUrl;
	int sequence;

	String symbolImageContentType;
	String symbolImageFileName;
	int symbolImageFileSize;

	Date symbolImageUpdatedAt;
	int symbolLevel;
	String symbolType;

	Date updatedAt;
	String word;
	List<RelatedSymbols> relatedSymbols;

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
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

	public boolean isHideUnhide() {
		return hideUnhide;
	}

	public void setHideUnhide(boolean hideUnhide) {
		this.hideUnhide = hideUnhide;
	}

	public boolean isHideUnhideHebrew() {
		return hideUnhideHebrew;
	}

	public void setHideUnhideHebrew(boolean hideUnhideHebrew) {
		this.hideUnhideHebrew = hideUnhideHebrew;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getSymbolImageContentType() {
		return symbolImageContentType;
	}

	public void setSymbolImageContentType(String symbolImageContentType) {
		this.symbolImageContentType = symbolImageContentType;
	}

	public String getSymbolImageFileName() {
		return symbolImageFileName;
	}

	public void setSymbolImageFileName(String symbolImageFileName) {
		this.symbolImageFileName = symbolImageFileName;
	}

	public int getSymbolImageFileSize() {
		return symbolImageFileSize;
	}

	public void setSymbolImageFileSize(int symbolImageFileSize) {
		this.symbolImageFileSize = symbolImageFileSize;
	}

	public Date getSymbolImageUpdatedAt() {
		return symbolImageUpdatedAt;
	}

	public void setSymbolImageUpdatedAt(Date symbolImageUpdatedAt) {
		this.symbolImageUpdatedAt = symbolImageUpdatedAt;
	}

	public int getSymbolLevel() {
		return symbolLevel;
	}

	public void setSymbolLevel(int symbolLevel) {
		this.symbolLevel = symbolLevel;
	}

	public String getSymbolType() {
		return symbolType;
	}

	public void setSymbolType(String symbolType) {
		this.symbolType = symbolType;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public List<RelatedSymbols> getRelatedSymbols() {
		return relatedSymbols;
	}

	public void setRelatedSymbols(List<RelatedSymbols> relatedSymbols) {
		this.relatedSymbols = relatedSymbols;
	}

}
