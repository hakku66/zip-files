package com.patwa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bill_details")
public class BillDetail {

	@Id
	@Column(name="bill_detail_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int billDetailId;
	@Column(name="receipt_id")
	private String receiptId;
	
	private String description;
	private double rate;
	private double qty;
	@Column(name="disc_val")
	private double discVal;
	@Column(name="disc_amt")
	private double discAmt;
	
	@Column(name="total_amt")
	private double totalAmt;
	@Column(name="bill_date")
	private String billDate;
	@Column(name="hsn_code")
	private String hsnCode;

	@Column(name="gst")
	private double gst;
	
	@Column(name="cgst")
	private double cgst;
	
	@Column(name="sgst")
	private double sgst;
	
	@Column(name="igst")
	private double igst;
	
	@Column(name="cess")
	private double cess;
	
	private String per;
	

	public double getGst() {
		return gst;
	}
	public void setGst(double gst) {
		this.gst = gst;
	}
	public double getCgst() {
		return cgst;
	}
	public void setCgst(double cgst) {
		this.cgst = cgst;
	}
	public double getSgst() {
		return sgst;
	}
	public void setSgst(double sgst) {
		this.sgst = sgst;
	}
	public double getIgst() {
		return igst;
	}
	public void setIgst(double igst) {
		this.igst = igst;
	}
	
	public String getPer() {
		return per;
	}
	public void setPer(String per) {
		this.per = per;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getDiscVal() {
		return discVal;
	}
	public void setDiscVal(double discType) {
		this.discVal = discType;
	}
	public double getDiscAmt() {
		return discAmt;
	}
	public void setDiscAmt(double discAmt) {
		this.discAmt = discAmt;
	}
	public double getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public int getBillDetailId() {
		return billDetailId;
	}
	public void setBillDetailId(int billDetailId) {
		this.billDetailId = billDetailId;
	}
	@Override
	public String toString() {
		return "BillDetail [billDetailId=" + billDetailId + ", receiptId=" + receiptId + ", description=" + description
				+ ", rate=" + rate + ", qty=" + qty + ", discVal=" + discVal + ", discAmt=" + discAmt + ", totalAmt="
				+ totalAmt + ", billDate=" + billDate + ", hsnCode=" + hsnCode + ", gst=" + gst + ", cgst=" + cgst
				+ ", sgst=" + sgst + ", igst=" + igst + ", cess=" + cess + ", per=" + per + "]";
	}
	public double getcess() {
		return cess;
	}
	public void setcess(double icess) {
		this.cess = icess;
	}

	
}
