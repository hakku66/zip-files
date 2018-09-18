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
@Table(name="item")
public class Item {
	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemId;
	
	@Column(name="item_name", unique=true)
	private String name;
	
	@Column(name="item_hsnNo")
	private String hsnNo;
	
	@Column(name="item_qtyType")
	private String qtyType;

	@Column(name="item_gstP")
	private double gstP;

	@Column(name="item_igst")
	private  Boolean igst;

	@Column(name="item_cess")
	private double cess;

	@Column(name="item_stock")
	private double stock;

	@Column(name="item_costPrice")
	private double costPrice;

	@Column(name="item_sellingPrice")
	private double sellingPrice;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHsnNo() {
		return hsnNo;
	}
	public void setHsnNo(String hsnNo) {
		this.hsnNo = hsnNo;
	}
	public String getQtyType() {
		return qtyType;
	}
	public void setQtyType(String qtyType) {
		this.qtyType = qtyType;
	}
	public double getCess() {
		return cess;
	}
	public void setCess(double cess) {
		this.cess = cess;
	}
	public double getStock() {
		return stock;
	}
	public void setStock(double stock) {
		this.stock = stock;
	}
	public double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public double getGstP() {
		return gstP;
	}
	public void setGstP(double gstP) {
		this.gstP = gstP;
	}
	public Boolean isIgst() {
		return igst;
	}
	public void setIgst(Boolean igst) {
		this.igst = igst;
	}


	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", name=" + name + ", hsnNo=" + hsnNo + ", qtyType=" + qtyType + ", gstP="
				+ gstP + ", igst=" + igst + ", cess=" + cess + ", stock=" + stock + ", costPrice=" + costPrice
				+ ", sellingPrice=" + sellingPrice + "]";
	}
	
	
}
