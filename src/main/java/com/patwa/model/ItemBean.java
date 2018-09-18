/**
 * 
 */
package com.patwa.model;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty; 
//import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Abdul Hakeem
 *
 */


public class ItemBean {
	
	
	private SimpleStringProperty name; 
	private SimpleStringProperty hsnNo;
	private SimpleStringProperty qtyType;
	private SimpleDoubleProperty gstP;
	private SimpleBooleanProperty igst;
	private SimpleDoubleProperty cess;
	private SimpleDoubleProperty stock;
	private SimpleDoubleProperty costPrice;	
	private SimpleDoubleProperty sellingPrice;
	public ItemBean( String name, String hsnNo, String qtyType, double gstP, Boolean igst,
			double cess, double stock, double costPrice, double sellingPrice) {
		super();
		
		this.name = new SimpleStringProperty(name);
		this.hsnNo = new SimpleStringProperty(hsnNo);
		this.qtyType = new SimpleStringProperty(qtyType);
		this.gstP = new SimpleDoubleProperty(gstP);
		this.igst = new SimpleBooleanProperty(igst);		
		this.cess = new SimpleDoubleProperty(cess);
		this.stock = new SimpleDoubleProperty(stock);
		this.costPrice = new SimpleDoubleProperty(costPrice);
		this.sellingPrice = new SimpleDoubleProperty(sellingPrice);
	}
	
	public String getName() {
		return name.get();
	}
	public String getHsnNo() {
		return hsnNo.get();
	}
	public String getQtyType() {
		return qtyType.get();
	}
	public double getGstP() {
		return gstP.get();
	}
	public Boolean getIgst() {
		return igst.get();
	}public double getCess() {
		return cess.get();
	}
	public double getStock() {
		return stock.get();
	}
	public double getCostPrice() {
		return costPrice.get();
	}
	public double getSellingPrice() {
		return sellingPrice.get();
	}
	

	public void setName(String name) {
		this.name.set(name);
	}

	public void setHsnNo(String hsnNo) {
		this.hsnNo.set(hsnNo);
	}

	public void setQtyType(String qtyType) {
		this.qtyType.set(qtyType);
	}

	public void setGstP(double gstP) {
		this.gstP.set(gstP);
	}

	public void setIgst(Boolean igst) {
		this.igst.set(igst);
	}

	public void setCess(double cess) {
		this.cess.set(cess);
	}

	public void setStock(double stock) {
		this.stock.set(stock);
	}

	public void setCostPrice(double costPrice) {
		this.costPrice.set(costPrice);
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice.set(sellingPrice);
	}

	@Override
	public String toString() {
		return "ItemBean [name=" + name + ", hsnNo=" + hsnNo + ", qtyType=" + qtyType + ", gstP=" + gstP + ", igst="
				+ igst + ", cess=" + cess + ", stock=" + stock + ", costPrice=" + costPrice + ", sellingPrice="
				+ sellingPrice + "]";
	}
	
	
	
}
