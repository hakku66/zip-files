package com.patwa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.patwa.model.BillReceipt;

public interface BillReceiptRepository extends JpaRepository<BillReceipt, Integer>{
	
	@Query(value="select coalesce(max(b.bill_id), 0) from bill_receipts b", nativeQuery = true) 
	int getMaxId();
	
	public BillReceipt findBillReceiptByReceiptId(String receiptId);
	
	@Query(value="SELECT receipt_id FROM bill_receipts where  receipt_date between date(?1) and date(?2)", nativeQuery = true) 
	public List<String> findReceiptsByDate(String fromDate, String toDate);
}
