package com.example.olamundo.models;

import java.io.Serializable;
import java.util.Date;

public class RelatedSymbols implements Serializable {

	private static final long serialVersionUID = -2142324754950809838L;

	Date createdAt;
	String hebrew;
	int id;

	String imageUrl;
	int sequence;
	int symbolId;

	String symbolImageContentType;
	String symbolImageFileName;
	int symbolImageFileSize;

	Date symbolImageUpdatedAt;
	String symbolText;
	Date updatedAt;
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
	public int getSymbolId() {
		return symbolId;
	}
	public void setSymbolId(int symbolId) {
		this.symbolId = symbolId;
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
	public String getSymbolText() {
		return symbolText;
	}
	public void setSymbolText(String symbolText) {
		this.symbolText = symbolText;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
