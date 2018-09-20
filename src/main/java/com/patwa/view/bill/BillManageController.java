package com.patwa.view.bill;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.patwa.model.BillDetail;
import com.patwa.model.BillDetailBean;
import com.patwa.model.BillReceipt;
import com.patwa.model.Company;
import com.patwa.model.Customer;
import com.patwa.model.Owner;
import com.patwa.view.util.DateUtil;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

@Lazy
@Component
public class BillManageController extends AbstractBillController{
	
	@FXML ChoiceBox<String> customer;
	@FXML Label buyerName;
	@FXML Label buyerAddress;
	@FXML Label buyerPan;
	@FXML Label buyerGst;
	@FXML Label buyerCity;
	
	@FXML DatePicker fromDate;
	@FXML DatePicker toDate;
	
	@FXML TableView<String> tableBillReceipt;
	@FXML TableColumn<String, Integer> ino;
	@FXML TableColumn<String, String> iBillReceipt;
	
	@FXML Label failMsg;
	
	private ObservableList<String> receiptData = null;
	private BillReceipt billReceipt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		failMsg.setVisible(false);
		fromDate.setValue(DateUtil.getCurrentLocalDate());
		toDate.setValue(DateUtil.getCurrentLocalDate());
		itemMap =itemServ.getAllItemMap();
		ino.setCellFactory(new Callback<TableColumn<String, Integer>, TableCell<String, Integer>>() {
            @Override 
            public TableCell<String, Integer> call(TableColumn<String, Integer> param) {
                return new TableCell<String, Integer>() {
                    @Override 
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null) {
                            int index = this.getTableRow().getIndex();
                            if( index < tableBillReceipt.getItems().size()) {
                                int rowNum = index + 1;
                                setText(String.valueOf(rowNum));
                            } else {
                                setText("");
                            }
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
		
		configureOwner();
		seller.getSelectionModel().clearSelection();
		
		custMap = custServ.getAllCustomerMap();
		
		customer.setItems(FXCollections.observableArrayList(custMap.keySet()));
		
		customer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		      @Override
		      public void changed(ObservableValue<? extends String> observableValue, String str1, String str2) {
		    	  setCustomerInfo(str2);
		      }
		    });
		
		findBills();
		populateCellData();
		billData = FXCollections.observableArrayList();
		configureBillDetailTable();
		configurePer();
		populateCell();
		configureTransport();
		configureInputField();
	}
	
	public void findBills(){
		LocalDate fromD = fromDate.getValue();
		LocalDate toD = toDate.getValue();
		if(null != fromD && null != toD){
			toD = toD.plusDays(1);
			System.out.println(fromD.toString()+" : "+toD.toString());
			List<String> list = billReceiptService.getReceiptsByDates(fromD.toString(), toD.toString());
			receiptData = FXCollections.observableArrayList(list);
		}
		iBillReceipt.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));
		tableBillReceipt.setItems(receiptData);
	}
	public void onAdd(){
		setCustomerGST(buyerGst.getText());
		onAddButtonClick();
	}
	public void onUpdate(){
		int selectedIndex = tableBillDetailid.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	BillDetailBean billBean = tableBillDetailid.getItems().remove(selectedIndex);
	    	billData.remove(billBean);
	    	log.info("Deleted item : "+ billBean);
	    	setTaxableAmt(getTaxableAmt() - billBean.getRAmount()); 
	    	totalTaxCalcutation();
	    	setTotalAmt();
	    }
		onAdd();
	}
	
	public void populateCellData(){
		tableBillReceipt.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				int selectedIndex = tableBillReceipt.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0) {
					failMsg.setVisible(false);
					
					String billReceiptId = tableBillReceipt.getItems().get(selectedIndex);
					billReceipt = billReceiptService.getBillReceiptByReceiptId(billReceiptId);
					//Customer Data
					if(billReceipt != null 
							&& billReceipt.getCustomerId() > 0 
							&& billReceipt.getOwnerId() > 0)
					{
						refresPage();
						Customer c = custServ.getCustomerById(billReceipt.getCustomerId());
						if(c != null){
							customer.getSelectionModel().select(c.getAliasName());
							buyerName.setText(c.getName());
							buyerAddress.setText(c.getAddress());
							buyerPan.setText(c.getPanNo());
							buyerGst.setText(c.getGstNo());
							buyerCity.setText(c.getCity());
							setCustomerId(c.getCustomerId());
							setCustomerGST(c.getGstNo());
						}
					
						Owner o = oserv.getOwnerById(billReceipt.getOwnerId());
						if(null != o){
							seller.getSelectionModel().select(o.getAliasName());
							sellerName.setText(o.getName());
							sellerAddress.setText(o.getAddress());
							sellerPhones.setText(o.getPhone1()+", "+o.getPhone2());
							sellerGst.setText(o.getGstNo());
							sellerEmail.setText(o.getEmail());
							Company comp = comServ.getByCompanyId(o.getCompanyId());
							if(comp != null && comp.getBankName() != null)
								sellerBank.setText(comp.getBankName());
						}
					}
					if(null != billReceiptId){
						billData.clear();
						List<BillDetail> billDetailList = billDetailService.getAllBillByReceipt(billReceiptId);
						for(BillDetail b : billDetailList){
							billData.add(new BillDetailBean(b.getDescription(), 
									b.getHsnCode(), 
									b.getQty(), 
									b.getRate(), 
									b.getPer(), 
									b.getDiscVal(), 
									b.getDiscAmt(), 
									b.getTotalAmt(), b.getCgst(), b.getSgst(), b.getIgst(),b.getGst(),b.getcess()));
							addTax(b.getSgst(),b.getCgst(),b.getIgst(),b.getcess());
					    	
							
						}
						setTaxableAmt(billReceipt.getTaxableAmt());
						totalTaxCalcutation();
						setTranportChargeVal(billReceipt.getTransportAmt());
						setTotal(billReceipt.getTotal());
						tranportType.setText(billReceipt.getTransportType());
						tranportCharge.setText(String.valueOf(billReceipt.getTransportAmt()));
						TOTAL.setText(String.valueOf(billReceipt.getTotal()));
					}
				}
			}
		});
	}
	
	private void setCustomerInfo(String custAlias){
		Customer c = custMap.get(custAlias);
		if(c != null){
			customer.getSelectionModel().select(c.getAliasName());
			buyerName.setText(c.getName());
			buyerAddress.setText(c.getAddress());
			buyerPan.setText(c.getPanNo());
			buyerGst.setText(c.getGstNo());
			buyerCity.setText(c.getCity());
			setCustomerId(c.getCustomerId());
			setCustomerGST(c.getGstNo());
		}
	}
	
	public String saveBills(){
		if(getTaxableAmt() == 0d) return null;
		String dateStr = DateUtil.getDBCurrentDateS();
		Owner o = ownerMap.get(seller.getValue());
		//Customer c = custMap.get(buyer.getText());
		log.info("Update Bills :: owner detail : "+o);
		BillReceipt br = billReceipt;
		br.setOwnerId(o.getOwnerId());
		br.setCustomerId(getCustomerId());
		br.setTaxableAmt(getTaxableAmt());
		if(isSameState){
			br.setIgstAmt(getTotalTax());
		}else{
			br.setCgstAmt(toDouble(getTaxableAmt()*2.50/100D));
			br.setSgstAmt(toDouble(getTaxableAmt()*2.50/100D));
		}
		br.setBillType(sellerGst.getText().isEmpty() ? "UNREG" : "REG");
		br.setTransportType(tranportType.getText());	
		br.setReceiptDate(dateStr);
		br.setTotal(getTotal()+getTranportChargeVal());
		br.setTransportAmt(getTranportChargeVal());
		br = billReceiptService.saveBillReceipt(br);
		log.info("Updated Bills :: billReceipt detail : ",br);
		//Delete all Bill Detail First
		billDetailService.deleteBillDetailByreceipt(br.getReceiptId());
		//Save new value
		for(BillDetailBean billBean : billData){
			BillDetail billDetail = new BillDetail();
			billDetail.setReceiptId(br.getReceiptId());
			billDetail.setDescription(billBean.getRDescription());
			billDetail.setHsnCode(billBean.getRHSNCode());
			billDetail.setQty(billBean.getRQTY());
			billDetail.setRate(billBean.getRRate());
			billDetail.setPer(billBean.getRPer());
			billDetail.setDiscVal(billBean.getRDiscVal());
			billDetail.setDiscAmt(billBean.getRDiscAmt());
			billDetail.setTotalAmt(billBean.getRAmount());
			billDetail.setBillDate(dateStr);
			billDetail.setGst(billBean.getRgst());
			billDetail.setcess(billBean.getRcess());
			billDetail.setCgst(billBean.getRcgst());
			billDetail.setSgst(billBean.getRsgst());
			billDetail.setIgst(billBean.getRigst());
			
			billDetailService.saveBillDetail(billDetail);
			log.info("Update Bill Details :: "+billDetail);
		}
		return br.getReceiptId();
	}
	
	public void generateBill(){
		try{
			super.generateBill();
			failMsg.setText("Bill Receipt Updated !");
			failMsg.setTextFill(Color.GREEN);
		}catch(Exception e){
			log.info("Problem in creating PDF ::"+e.getMessage());
			failMsg.setTextFill(Color.RED);
		}
		failMsg.setVisible(true);
	}
}
