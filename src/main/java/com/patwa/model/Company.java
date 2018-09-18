package com.patwa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company_detail")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="company_id")
	private int companyId;
	
	private String name;
	
	@Column(name="pan_no")
	private String panNo;
	
	@Column(name="bank_name")
	private String bankName;
	
	@Column(name="bank_ac_no")
	private String bankAcNo;
	
	@Column(name="bank_ifsc")
	private String bankIfsc;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAcNo() {
		return bankAcNo;
	}
	public void setBankAcNo(String bankAcNo) {
		this.bankAcNo = bankAcNo;
	}
	public String getBankIfsc() {
		return bankIfsc;
	}
	public void setBankIfsc(String bankIfsc) {
		this.bankIfsc = bankIfsc;
	}
	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", panNo=" + panNo + ", bankName=" + bankName
				+ ", bankAcNo=" + bankAcNo + ", bankIfsc=" + bankIfsc + "]";
	}
}
