package com.patwa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bill_receipts")
public class BillReceipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bill_id")
	private int billId;
	@Column(name="receipt_id")
	private String receiptId;
	@Column(name="owner_id")
	private int ownerId;
	@Column(name="customer_id")
	private int customerId;
	@Column(name="taxable_amt")
	private double taxableAmt;
	@Column(name="sgst_amt")
	private double sgstAmt;
	@Column(name="cgst_amt")
	private double cgstAmt;
	@Column(name="igst_amt")
	private double igstAmt;
	@Column(name="cess_amt")
	private double cessAmt;
	public double getCessAmt() {
		return cessAmt;
	}
	public void setCessAmt(double cessAmt) {
		this.cessAmt = cessAmt;
	}
	@Column(name="transport_amt")
	private double transportAmt;
	private double total;
	@Column(name="receipt_date")
	private String receiptDate;
	@Column(name="bill_type")
	private String billType;
	@Column(name="transport_type")
	private String transportType;
	
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	public double getSgstAmt() {
		return sgstAmt;
	}
	public void setSgstAmt(double sgstAmt) {
		this.sgstAmt = sgstAmt;
	}
	public double getCgstAmt() {
		return cgstAmt;
	}
	public void setCgstAmt(double cgstAmt) {
		this.cgstAmt = cgstAmt;
	}
	public double getIgstAmt() {
		return igstAmt;
	}
	public void setIgstAmt(double igstAmt) {
		this.igstAmt = igstAmt;
	}
	public double getTransportAmt() {
		return transportAmt;
	}
	public void setTransportAmt(double transportAmt) {
		this.transportAmt = transportAmt;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public int getBillId() {
		return billId;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	@Override
	public String toString() {
		return "BillReceipt [billId=" + billId + ", receiptId=" + receiptId + ", ownerId=" + ownerId + ", customerId="
				+ customerId + ", taxableAmt=" + taxableAmt + ", sgstAmt=" + sgstAmt + ", cgstAmt=" + cgstAmt
				+ ", igstAmt=" + igstAmt + ", transportAmt=" + transportAmt + ", total=" + total + ", receiptDate="
				+ receiptDate + ", billType=" + billType + ", transportType=" + transportType + "]";
	}
}
