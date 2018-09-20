package com.patwa.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class BillDetailBean {
	
	private final SimpleStringProperty rDescription;
	private final SimpleStringProperty rHSNCode;
	private final SimpleDoubleProperty rQTY;
	private final SimpleDoubleProperty rRate;
	private final SimpleStringProperty rPer;
	private final SimpleDoubleProperty rDiscVal;
	private final SimpleDoubleProperty rDiscAmt;
	private final SimpleDoubleProperty rAmount;
	private final SimpleDoubleProperty rcgst;
	private final SimpleDoubleProperty rsgst;
	private final SimpleDoubleProperty rigst;
	private final SimpleDoubleProperty rgst;
	private SimpleDoubleProperty rcess;
	
	public BillDetailBean(String rDescription, String rHSNCode, double rQTY,
			double rRate, String rPer, double rDiscVal,
			double rDiscAmt, double rAmount,double rcgst,double rsgst,double rigst,double rgst, double rcess) {
		super();
		this.rDescription = new SimpleStringProperty(rDescription);
		this.rHSNCode = new SimpleStringProperty(rHSNCode);
		this.rQTY = new SimpleDoubleProperty(rQTY);
		this.rRate = new SimpleDoubleProperty(rRate);
		this.rPer = new SimpleStringProperty(rPer);
		this.rDiscVal = new SimpleDoubleProperty(rDiscVal);
		this.rDiscAmt = new SimpleDoubleProperty(rDiscAmt);
		this.rAmount = new SimpleDoubleProperty(rAmount);
		this.rcgst=new SimpleDoubleProperty(rcgst);
		this.rsgst=new SimpleDoubleProperty(rsgst);
		this.rigst=new SimpleDoubleProperty(rigst);
		this.rgst=new SimpleDoubleProperty(rgst);
		this.rcess=new SimpleDoubleProperty(rcess);
	}

	public String getRDescription() {
		return rDescription.get();
	}
	public void setRDescription(String desc){
		this.rDescription.set(desc);
	}
	
	public double getRcess() {
		return rcess.get();
	}

	public void setRcess(double rcess) {
		this.rcess.set(rcess);
	}

	public String getRHSNCode() {
		return rHSNCode.get();
	}
	public void setRHSNCode(String hsnCode){
		this.rHSNCode.set(hsnCode);
	}

	public double getRQTY() {
		return rQTY.get();
	}
	public void setRQTY(double qty){
		this.rQTY.set(qty);
	}
	
	public double getRRate() {
		return rRate.get();
	}
	public void setRRate(double rate){
		this.rRate.set(rate);
	}

	public String getRPer() {
		return rPer.get();
	}
	public void setRPer(String per){
		this.rPer.set(per);
	}
	
	public double getRDiscVal() {
		return rDiscVal.get();
	}
	public void setRDiscVal(double discVal){
		this.rDiscVal.set(discVal);
	}

	public double getRDiscAmt() {
		return rDiscAmt.get();
	}
	public void setRDiscAmt(double discAmt){
		this.rDiscAmt.set(discAmt);
	}

	public double getRAmount() {
		return rAmount.get();
	}
	public void setRAmount(double amount){
		this.rAmount.set(amount);
	}
	public double getRcgst() {
		return rcgst.get();
	}
	public void setRcgst(double cgst){
		this.rcgst.set(cgst);
	}
	
	public double getRsgst() {
		return rsgst.get();
	}
	public void setRsgst(double sgst){
		this.rsgst.set(sgst);
	}
	public double getRigst() {
		return rigst.get();
	}
	public void setRigst(double igst){
		this.rigst.set(igst);
	}
	public double getRgst() {
		return rgst.get();
	}
	public void setRgst(double gst){
		this.rgst.set(gst);
	}

	@Override
	public String toString() {
		return "BillDetailBean [rDescription=" + rDescription + ", rHSNCode=" + rHSNCode + ", rQTY=" + rQTY + ", rRate="
				+ rRate + ", rPer=" + rPer + ", rDiscVal=" + rDiscVal + ", rDiscAmt=" + rDiscAmt + ", rAmount="
				+ rAmount + ", rcgst=" + rcgst + ", rsgst=" + rsgst + ", rigst=" + rigst + ", rgst=" + rgst + "]";
	}

	
}
