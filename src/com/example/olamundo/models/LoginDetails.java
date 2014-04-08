package com.example.olamundo.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LoginDetails implements Serializable {

	private static final long serialVersionUID = 4723032419307789460L;
	
	boolean success;
	Date expiryDate;
	String email;
	String password;
	int msgCount;
	int freeMsgCount;
	List<Members> members;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

	public int getFreeMsgCount() {
		return freeMsgCount;
	}

	public void setFreeMsgCount(int freeMsgCount) {
		this.freeMsgCount = freeMsgCount;
	}

	public List<Members> getMembers() {
		return members;
	}

	public void setMembers(List<Members> members) {
		this.members = members;
	}
	
}