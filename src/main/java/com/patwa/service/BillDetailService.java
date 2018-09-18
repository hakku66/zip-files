package com.patwa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patwa.model.BillDetail;
import com.patwa.repository.BillDetailRepository;

@Service
public class BillDetailService {
	@Autowired
	private BillDetailRepository billDetailRepo;
	
	public BillDetail saveBillDetail(BillDetail billDetail){
		return billDetailRepo.saveAndFlush(billDetail);
	}
	
	public void saveBillDetail(List<BillDetail> billDetails){
		 billDetailRepo.save(billDetails);
	}
	
	public List<BillDetail> getAllBillByReceipt(String receiptId){
		return billDetailRepo.findBillDetailByReceiptId(receiptId);
	}
	
	public void deleteBillDetailByreceipt(String receiptId){
		 billDetailRepo.deleteBillDetailsByReceiptId(receiptId);
	}
}
