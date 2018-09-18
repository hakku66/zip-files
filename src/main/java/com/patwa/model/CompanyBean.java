package com.patwa.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CompanyBean {
	
	private static int count = 1;
	private final SimpleIntegerProperty rNo;
	private final SimpleStringProperty rName;
	
	private final SimpleStringProperty rPanNo;
	
	private final SimpleStringProperty rBankName;
	
	private final SimpleStringProperty rBankAcNo;
	
	private final SimpleStringProperty rBankIfsc;

	public CompanyBean(String name, String rPanNo,
			String rBankName, String rBankAcNo, String rBankIfsc) {
		super();
		this.rNo = new SimpleIntegerProperty(count++);
		this.rName= new SimpleStringProperty(name);
		this.rPanNo = new SimpleStringProperty(rPanNo);
		this.rBankName = new SimpleStringProperty(rBankName);
		this.rBankAcNo = new SimpleStringProperty(rBankAcNo);
		this.rBankIfsc = new SimpleStringProperty(rBankIfsc);
	}

	public int getrNo() {
		return rNo.get();
	}

	public void setrNo(int rNo) {
		this.rNo.set(rNo);
	}

	public String getrName() {
		return rName.get();
	}

	public void setrName(String rName) {
		this.rName.set(rName);
	}

	public String getrPanNo() {
		return rPanNo.get();
	}

	public void setrPanNo(String rPanNo) {
		this.rPanNo.set(rPanNo);
	}

	public String getrBankName() {
		return rBankName.get();
	}

	public void setrBankName(String rBankName) {
		this.rBankName.set(rBankName);
	}

	public String getrBankAcNo() {
		return rBankAcNo.get();
	}

	public void setrBankAcNo(String rBankAcNo) {
		this.rBankAcNo.set(rBankAcNo);
	}

	public String getrBankIfsc() {
		return rBankIfsc.get();
	}

	public void setrBankIfsc(String rBankIfsc) {
		this.rBankIfsc.set(rBankIfsc);
	}
}
