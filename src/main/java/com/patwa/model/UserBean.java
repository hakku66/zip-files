package com.patwa.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserBean {
	private static int count = 1;
	private final SimpleIntegerProperty rNo;
	private final SimpleStringProperty rUserId;
	private final SimpleStringProperty rPassword;
	private final SimpleIntegerProperty rActive;
	private final SimpleStringProperty rCreated;
	
	public UserBean(int rNo, String rUserId, String rPassword,
			int rActive, String rCreated) {
		super();
		this.rNo = new SimpleIntegerProperty(rNo);
		this.rUserId = new SimpleStringProperty(rUserId);
		this.rPassword = new SimpleStringProperty(rPassword);
		this.rActive = new SimpleIntegerProperty(rActive);
		this.rCreated = new SimpleStringProperty(rCreated);
		count = rNo;
	}
	
	public UserBean( String rUserId, String rPassword,
			int rActive, String rCreated) {
		super();
		this.rNo = new SimpleIntegerProperty(++count);
		this.rUserId = new SimpleStringProperty(rUserId);
		this.rPassword = new SimpleStringProperty(rPassword);
		this.rActive = new SimpleIntegerProperty(rActive);
		this.rCreated = new SimpleStringProperty(rCreated);
	}

	public int getRNo() {
		return rNo.get();
	}
	
	public void setRNo(int v){
		rNo.set(v);
	}

	public String getRUserId() {
		return rUserId.get();
	}
	public void setRUserId(String userId){
		rUserId.set(userId);
	}
	
	public String getRPassword() {
		return rPassword.get();
	}
	public void setRPassword(String pass){
		rPassword.set(pass);
	}
	
	public int getRActive() {
		return rActive.get();
	}
	public void setRActive(int val){
		rActive.set(val);
	}
	public String getRCreated() {
		return rCreated.get();
	}
	public void setRCreated(String created){
		rCreated.set(created);
	}
}
