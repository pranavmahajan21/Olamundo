package com.example.olamundo.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Members implements Serializable {


	public Members() {
		super();
	}
	
	public Members(String name, Drawable drawable) {
		this.name = name;
		this.drawable = drawable;
	}

	public Members(String firstName,String name,String imageURL,int id,String relationship) {
		super();
		this.firstName = firstName;
		this.name = name;
		this.imageURL = imageURL;
		this.id = id;
		this.relationship = relationship;
	}

	private static final long serialVersionUID = 4070151190596227520L;

	String appleId;
	int id;
	int userId;

	String avatarContentType;
	String avatarFileName;
	int avatarFileSize;

	Date avatarUpdatedAt;
	Date createdAt;
	Date updatedAt;

	String name;
	String firstName;
	String lastName;

	String imageURL;
	ImageView imageView;
	Drawable drawable;
	Bitmap bitmap;

	String relationship;
	List<Symbols> symbols;
	List<Message> messages;

	public String getAppleId() {
		return appleId;
	}

	public void setAppleId(String appleId) {
		this.appleId = appleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAvatarContentType() {
		return avatarContentType;
	}

	public void setAvatarContentType(String avatarContentType) {
		this.avatarContentType = avatarContentType;
	}

	public String getAvatarFileName() {
		return avatarFileName;
	}

	public void setAvatarFileName(String avatarFileName) {
		this.avatarFileName = avatarFileName;
	}

	public int getAvatarFileSize() {
		return avatarFileSize;
	}

	public void setAvatarFileSize(int avatarFileSize) {
		this.avatarFileSize = avatarFileSize;
	}

	public Date getAvatarUpdatedAt() {
		return avatarUpdatedAt;
	}

	public void setAvatarUpdatedAt(Date avatarUpdatedAt) {
		this.avatarUpdatedAt = avatarUpdatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	// @Override
	// public boolean equals(Object o) {
	// boolean temp = false;
	// if((o instanceof Members) && (this.getId() == ((Members)o).getId()))
	// temp = true;
	//
	// return temp;
	// }

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	
	
}
