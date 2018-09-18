/**
 * 
 */
package com.patwa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Abdul Hakeem
 *
 */
@Entity 
@Table(name="gst_rate")
public class GstRate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="gst_id")
	private int gstId;
	
	@Column(name="gstrate" , unique =true )
	private double gstrate;
	

	

	public int getGstId() {
		return gstId;
	}

	public void setGstId(int gstId) {
		this.gstId = gstId;
	}

	public double getGstrate() {
		return gstrate;
	}

	public void setGstrate(double gstrate) {
		this.gstrate = gstrate;
	}
	
	

}
