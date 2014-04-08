package com.example.olamundo.models;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

public class Message implements Serializable {

	private static final long serialVersionUID = 4341097118545039684L;

	int fromID;
	int toID;
	int intDate;
	Date date;
	JSONObject msgJsonObject;
	
	

	public Message(int fromID, int toID, int intDate, JSONObject msgJsonObject) {
		super();
		this.fromID = fromID;
		this.toID = toID;
		this.intDate = intDate;
		this.msgJsonObject = msgJsonObject;
	}

	public int getFromID() {
		return fromID;
	}

	public void setFromID(int fromID) {
		this.fromID = fromID;
	}

	public int getToID() {
		return toID;
	}

	public void setToID(int toID) {
		this.toID = toID;
	}

	public int getIntDate() {
		return intDate;
	}

	public void setIntDate(int intDate) {
		this.intDate = intDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public JSONObject getMsgJsonObject() {
		return msgJsonObject;
	}

	public void setMsgJsonObject(JSONObject msgJsonObject) {
		this.msgJsonObject = msgJsonObject;
	}
	
	
}
