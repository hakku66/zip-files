package com.patwa.model;


import javafx.beans.property.SimpleDoubleProperty;

public class GstRateBean {

	private final SimpleDoubleProperty gstrate;
	
	
	public GstRateBean(double gstrate) {
		super();
		
		this.gstrate = new SimpleDoubleProperty(gstrate);

				
	}
	public double getGstrate() {
		return gstrate.get();
	}
	
	public void setGstrate(double gstrate)
	{
		this.gstrate.set(gstrate);
	}

	
	
	
	

}
