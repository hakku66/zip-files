package com.patwa.view.bill;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.patwa.model.BillDetail;
import com.patwa.model.BillDetailBean;
import com.patwa.model.BillReceipt;
import com.patwa.model.Company;
import com.patwa.model.Customer;
import com.patwa.model.Item;
import com.patwa.model.Owner;
import com.patwa.pdf.PDFGenerator;
import com.patwa.service.BillDetailService;
import com.patwa.service.BillReceiptService;
import com.patwa.service.CompanyService;
import com.patwa.service.CustomerService;
import com.patwa.service.ItemService;
import com.patwa.service.OwnerService;
import com.patwa.view.print.PrintController;
import com.patwa.view.print.PrintView;
import com.patwa.view.util.DateUtil;
import com.patwa.view.util.Formatter;
import com.patwa.view.util.TextFieldAutoComplete;
import com.patwa.view.welcome.WelcomeView;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public abstract class AbstractBillController implements Initializable{
	Logger log = LoggerFactory.getLogger(this.getClass());
	@FXML ChoiceBox<String> seller;
	@FXML Label sellerName;
	@FXML Label sellerAddress;
	@FXML Label sellerGst;
	@FXML Label sellerPhones;
	@FXML Label sellerEmail;
	@FXML Label sellerBank;
	
	
	@FXML TextField description;
	@FXML Label hsnCode;
	@FXML TextField qty;
	@FXML TextField rate;
	@FXML ChoiceBox<String> per;
	@FXML TextField discVal;
	
	@FXML TableView<BillDetailBean> tableBillDetailid;
	@FXML TableColumn<BillDetailBean, Integer> iNo;
	@FXML TableColumn<BillDetailBean, String> idescription;
	@FXML TableColumn<BillDetailBean, String> ihsnCode;
	@FXML TableColumn<BillDetailBean, Double> iqty;
	@FXML TableColumn<BillDetailBean, Double> irate;
	@FXML TableColumn<BillDetailBean, String> iper;
	@FXML TableColumn<BillDetailBean, String> idiscVal;
	@FXML TableColumn<BillDetailBean, Double> itotalDisc;
	@FXML TableColumn<BillDetailBean, Double> iamount;
    @FXML TableColumn<BillDetailBean, Double> cGST;

   
    @FXML private TableColumn<BillDetailBean, Double> iCgstAmount;
    @FXML private TableColumn<BillDetailBean, Double> iSgstAmount;
    @FXML private TableColumn<BillDetailBean, Double> iIgstAmount;
    @FXML private TableColumn<BillDetailBean, Double> iGST;
    @FXML private TableColumn<BillDetailBean, Double>  iCESS;
	@FXML Label taxableAmtId;
	protected double taxableAmt = 0;
	
	@FXML Label sGSTId;
	@FXML Label cGSTId;
	@FXML Label iGSTId;
	@FXML Label iCess;
	@FXML Label totalTaxId;
	protected double totalTax =0;
	
	@FXML TextField tranportCharge;
	protected double tranportChargeVal =0;
	@FXML TextField tranportType;
	@FXML Label TOTAL;
	protected double total=0;
	
	@Autowired
	protected CustomerService custServ;
	@Autowired
	protected OwnerService oserv;
	@Autowired
	protected CompanyService comServ;
	@Autowired
	protected ItemService itemServ;
	
	@Autowired
	protected TextFieldAutoComplete buyerAuto;
	@Autowired
	protected TextFieldAutoComplete descAuto;
	@Autowired
	protected BillDetailService billDetailService;
	@Autowired
	protected BillReceiptService billReceiptService;
	@Autowired
	private PrintView printView;
	protected Map<String, Owner> ownerMap;
	
	protected Map<String, Customer> custMap;
	protected Map<String, Item> itemMap;
	protected ObservableList<BillDetailBean> billData;
	protected boolean isSameState = false; 
	
	private int customerId; 
	private String customerGST;
	protected double totalSgst=0;
	protected double totalCgst=0;
	protected double totalIgst=0;
	protected double totalCess=0;
	
	protected void setSellerInfo(String aliasName){
		log.info("setSellerInfo : "+aliasName);
		Owner o = ownerMap.get(aliasName);
		Company c = comServ.getByCompanyId(o.getCompanyId());
		sellerName.setText(o.getName());
		sellerAddress.setText(o.getAddress()+" "+o.getCity()+" "+o.getPin());
		sellerGst.setText("GSTIN/UIN: "+o.getGstNo());
		sellerPhones.setText("Contact: "+o.getPhone1() +", "+o.getPhone2());
		sellerEmail.setText("E‐Mail: "+o.getEmail());
		sellerBank.setText("BANK: "+(c!= null ? c.getBankName() : ""));
		log.info("Owner Info : "+ o);
		log.info("Customer Info : "+ c);
	}
	
	
	
	protected void totalTaxCalcutation(){
		sGSTId.setVisible(true);
		sGSTId.setText(String.valueOf(totalSgst));
		cGSTId.setVisible(true);
		cGSTId.setText(String.valueOf(totalCgst));
//		iGSTId.setVisible(true);
		iGSTId.setText(String.valueOf(totalIgst));
		iCess.setText(String.valueOf(totalCess));
		setTotalTax(totalSgst+totalCgst+totalIgst+totalCess);
		totalTaxId.setText(String.valueOf(getTotalTax()));
	}
	protected void calculateTranspotationCharge(){
		double transportCharge = Double.parseDouble(tranportCharge.getText().isEmpty() ? "0" : tranportCharge.getText());
		setTranportChargeVal(transportCharge);
		//setTotal(getTotal() + getTranportChargeVal());
		TOTAL.setText(String.valueOf(getTotal()+ getTranportChargeVal()));
	}
	protected void setTotalAmt(){
		double transportCharge = Double.parseDouble(tranportCharge.getText().isEmpty() ? "0" : tranportCharge.getText());
		setTranportChargeVal(transportCharge);
		setTotal(getTaxableAmt()+getTotalTax());
		TOTAL.setText(String.valueOf(getTotal() + transportCharge));
		log.info("TOTAL : "+TOTAL.getText());
	}
	
	protected void addTax(double sgst,double cgst,double igst,double cess){
		//totalSgst=totalCgst=totalIgst=totalCess=0.0;
		totalSgst = totalSgst + sgst;
		totalCgst = totalCgst + cgst;
		totalIgst = totalIgst + igst;
		totalCess = totalCess + cess;
		
	}
	public void onDelete(){
		int selectedIndex = tableBillDetailid.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	BillDetailBean billBean = tableBillDetailid.getItems().remove(selectedIndex);
	    	billData.remove(billBean);
	    	log.info("Deleted item : "+ billBean);
	    	setTaxableAmt(getTaxableAmt() - billBean.getRAmount()+billBean.getRcgst()+billBean.getRsgst()+billBean.getRigst()); 
	    	totalSgst = totalSgst - billBean.getRsgst();
			totalCgst = totalCgst - billBean.getRcgst();
			totalIgst = totalIgst - billBean.getRigst();	
			totalCess=totalCess - billBean.getRcess();
	    	totalTaxCalcutation();
	    	setTotalAmt();
	    	description.clear();
			description.requestFocus();
	    }
	}
	
	public void populateCell(){
		tableBillDetailid.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				int selectedIndex = tableBillDetailid.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0) {
					BillDetailBean billBean = tableBillDetailid.getItems().get(selectedIndex);
					description.setText(billBean.getRDescription());
					hsnCode.setText(billBean.getRHSNCode());
					qty.setText(String.valueOf(billBean.getRQTY()));
					rate.setText(String.valueOf(billBean.getRRate()));
					//per.setValue(billBean.getRPer());
					discVal.setText(String.valueOf(billBean.getRDiscVal()));
				}
			}
		});
	}
	protected double toDouble(double val){
		return Math.round(val * 100.0) / 100D;
	}

	public double getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(double taxableAmt) {
		if(taxableAmt >= 0)
			this.taxableAmt = toDouble(taxableAmt);
		taxableAmtId.setText(String.valueOf(getTaxableAmt()));
		log.info("Added Item Taxable amount : "+getTaxableAmt());
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getTranportChargeVal() {
		return tranportChargeVal;
	}

	public void setTranportChargeVal(double tranportChargeVal) {
		this.tranportChargeVal = tranportChargeVal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public void refresPage(){
		description.clear();
		qty.clear();
		rate.clear();
		discVal.clear();
		taxableAmtId.setText("");
		taxableAmt = 0;
		per.setValue(" ");
		sGSTId.setVisible(false);
		cGSTId.setVisible(false);
		totalTaxId.setText("");
		totalTax =0;
		
		tranportCharge.clear();
		tranportChargeVal =0;
		tranportType.clear();
		TOTAL.setText("");
		total =0;
		billData.clear();
		tableBillDetailid.refresh();
		isSameState = false;
		totalSgst=totalCgst=totalIgst=totalCess=0.0;
	}
	
	public void generateBill() throws JRException{
		String receiptId = saveBills();
		if(receiptId == null || receiptId.isEmpty()) return;
		BillReceipt billReceipt = billReceiptService.getBillReceiptByReceiptId(receiptId);
		List<BillDetail> listBillDetail = billDetailService.getAllBillByReceipt(receiptId);
		Customer c = null;
		Company company = null;
		Owner o = null;
		if(billReceipt != null && billReceipt.getOwnerId() > 0 && billReceipt.getCustomerId() >0){
			c = custServ.getCustomerById(billReceipt.getCustomerId());
			o = oserv.getOwnerById(billReceipt.getOwnerId());
			if(o != null)
				company = comServ.getByCompanyId(o.getCompanyId());
		}
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapP = new HashMap<>();
		map.put("billReceiptId", receiptId);
		map.put("customer", c);
		map.put("owner", o);
		map.put("company", company);
		map.put("billReceipt", billReceipt);
		JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(listBillDetail);
		map.put("billDetailList", dataSource1);
		JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(Arrays.asList(billReceipt));
		map.put("billReceiptDataSource", dataSource2);
		
		mapP.put("billReceiptId", receiptId);
		mapP.put("customer", c);
		mapP.put("owner", o);
		mapP.put("company", company);
		mapP.put("billReceipt", billReceipt);
		mapP.put("billDetailList", listBillDetail);
//		try{
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../print/Print.fxml"));
//						
//						PrintController printController=new PrintController();
//						
//						fxmlLoader.setController(printController);
//						Parent root = (Parent) fxmlLoader.load();
//						Scene scene = new Scene(root);
//						Stage stage = new Stage();
//						stage.setScene(scene);
//						stage.show();
//						printController.setDataFromBillingController(mapP);
//						
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
		String file = PDFGenerator.generateBillPDF(map);
		PDFGenerator.OpenPdfFile(file);
		
		log.info("PDF Gen :: Owner : "+o);
		log.info("PDF Gen :: Company : "+company);
		log.info("PDF Gen :: Customer : "+c);
		log.info("PDF Gen :: Bill Receipt : "+billReceipt);
		log.info("PDF Gen :: PDF Path : "+file);
	
	}
	
	protected String saveBills(){
		if(getTaxableAmt() == 0d) return null;
		String dateStr = DateUtil.getDBCurrentDateS();
		Owner o = ownerMap.get(seller.getValue());
		//Customer c = custMap.get(buyer.getText());
		log.info("Save Bills :: owner detail : "+o);
		BillReceipt br = new BillReceipt();
		br.setReceiptId(billReceiptService.getReceiptId(o.getAliasName()));
		br.setOwnerId(o.getOwnerId());
		br.setCustomerId(getCustomerId());
		br.setTaxableAmt(getTaxableAmt());
			br.setIgstAmt(totalIgst);
			br.setCgstAmt(totalCgst);
			br.setSgstAmt(totalSgst);
			br.setCessAmt(totalCess);
		
		br.setBillType(sellerGst.getText().isEmpty() ? "UNREG" : "REG");
		br.setTransportType(tranportType.getText());	
		br.setReceiptDate(dateStr);
		br.setTotal(getTotal()+getTranportChargeVal());
		br.setTransportAmt(getTranportChargeVal());
		br = billReceiptService.saveBillReceipt(br);
		log.info("Save Bills :: billReceipt detail : ",br);
		for(BillDetailBean billBean : billData){
			BillDetail billDetail = new BillDetail();
			billDetail.setReceiptId(br.getReceiptId());
			billDetail.setDescription(billBean.getRDescription());
			billDetail.setHsnCode(billBean.getRHSNCode());
			billDetail.setQty(billBean.getRQTY());
			billDetail.setRate(billBean.getRRate());
			billDetail.setGst(billBean.getRgst());
			billDetail.setPer(billBean.getRPer());
			billDetail.setDiscVal(billBean.getRDiscVal());
			billDetail.setDiscAmt(billBean.getRDiscAmt());
			billDetail.setTotalAmt(billBean.getRAmount());
			billDetail.setCgst(billBean.getRcgst());
			billDetail.setSgst(billBean.getRsgst());
			billDetail.setIgst(billBean.getRigst());
			billDetail.setBillDate(dateStr);
			
			billDetailService.saveBillDetail(billDetail);
			log.info("Save Bills :: BILL detail : "+billDetail);
		}
		return br.getReceiptId();
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerGST() {
		return customerGST;
	}
	public void setCustomerGST(String customerGST) {
		this.customerGST = customerGST;
	}
	protected void configureBillDetailTable(){
		iNo.setCellFactory(new Callback<TableColumn<BillDetailBean, Integer>, TableCell<BillDetailBean, Integer>>() {
            @Override 
            public TableCell<BillDetailBean, Integer> call(TableColumn<BillDetailBean, Integer> param) {
                return new TableCell<BillDetailBean, Integer>() {
                    @Override 
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null) {
                            int index = this.getTableRow().getIndex();
                            if( index < tableBillDetailid.getItems().size()) {
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
		idescription.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rDescription"));
		ihsnCode.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rHSNCode"));
		iqty.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rQTY"));
		irate.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rRate"));
		iper.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rPer"));
		idiscVal.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rDiscVal"));
		itotalDisc.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rDiscAmt"));
		iamount.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rAmount"));
		iCgstAmount.setCellValueFactory(new PropertyValueFactory<BillDetailBean,Double>("rcgst"));
		iSgstAmount.setCellValueFactory(new PropertyValueFactory<BillDetailBean,Double>("rsgst"));
		iIgstAmount.setCellValueFactory(new PropertyValueFactory<BillDetailBean,Double>("rigst"));
		iGST.setCellValueFactory(new PropertyValueFactory<BillDetailBean,Double>("rgst"));
		billData = FXCollections.observableArrayList();
		tableBillDetailid.setItems(billData);
	}
	protected void configureOwner(){
		ownerMap = oserv.getAllSellerMap();
		seller.setItems(FXCollections.observableArrayList(ownerMap.keySet()));
		seller.getSelectionModel().selectFirst();
		if(seller.getItems().size() > 0)
			setSellerInfo(seller.getItems().get(0));
		
		seller.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		      @Override
		      public void changed(ObservableValue<? extends String> observableValue, String str1, String str2) {
		    	  setSellerInfo(str2);
		      }
		    });
	}
	protected void configurePer(){
		//"BAG-BAGS", "BOX-BOX", "PCS-PIECES", "BTL-BOTTLES", "CTN-CARTONS", "KGS-KILOGRAMS","LTR-LITRE"
		String[] perOption = {"BAGS", "BOX", "PIECES", "BOTTLES", "CARTONS", "KILOGRAMS","LITRE","SqrFt","Meter" };
		per.setItems(FXCollections.observableArrayList(Arrays.asList(perOption)));
		per.getSelectionModel().selectFirst();
	}
	protected void onAddButtonClick(){
		if(!description.getText().isEmpty() 
				&& !qty.getText().isEmpty() 
				&& !rate.getText().isEmpty())
		{
			qty.setStyle("");
			rate.setStyle("");
			int _qty = Integer.parseInt(qty.getText());
			
			double _discVal = Double.parseDouble(discVal.getText().isEmpty() ? "0" : discVal.getText());
			double _rate = Double.parseDouble(rate.getText())-_discVal;
			double totalDisc = 0;
			double _amt = 0;
			double cgst,igst,sgst,cess;
			
				totalDisc = toDouble(_qty * _discVal);
			
			_amt = toDouble(_qty * _rate );
			Item item=itemMap.get(description.getText());
			if(item.isIgst()){
				igst=item.getGstP()*_amt/100;
				cgst=0.0;
				sgst=0.0;
			}else{
				cgst=item.getGstP()*_amt/200;
				sgst=cgst;
				igst=0.0;
			}
			if(item.getCess() != 0){
			cess=item.getCess()*_amt/100;
			}else{
				cess=0;
			}
			totalSgst = totalSgst + sgst;
			totalCgst = totalCgst + cgst;
			totalIgst = totalIgst + igst;
			totalCess = totalCess+cess;
			
			BillDetailBean billBean = new BillDetailBean(description.getText(), 
					hsnCode.getText(), 
					_qty, 
					_rate, 
					item.getQtyType(), 
					 _discVal, 
					totalDisc, (_amt-(cgst+sgst+igst+cess)),cgst,sgst,igst,item.getGstP(),cess);
			
			billData.add(billBean);
			log.info("Added Item :: "+billBean);
			description.clear();
			hsnCode.setText("");
			qty.clear();
			rate.clear();
			discVal.clear();
			per.setValue("");
			description.requestFocus();
 
			totalTaxCalcutation();
			setTaxableAmt(getTaxableAmt() + _amt - (cgst+sgst+igst+cess));
			setTotalAmt();
		}else{
			if(qty.getText().isEmpty())
				qty.setStyle("-fx-background-color: #ffc9d3;");
			if(rate.getText().isEmpty())
				rate.setStyle("-fx-background-color: #ffc9d3;");
		}
	}
	
	protected void setItemInfo(String text) {
		// TODO Auto-generated method stub
		Item i = itemMap.get(text);
		if(null != i){
			hsnCode.setText(i.getHsnNo());
			rate.setText(String.valueOf(i.getSellingPrice()));
			qty.requestFocus();
			per.setValue(i.getQtyType());
		}
	}

	protected void configureInputField(){
		sGSTId.setVisible(false);
		cGSTId.setVisible(false);
		
		qty.setTextFormatter(Formatter.getIntegerFormatter());
		rate.setTextFormatter(Formatter.getDoubleFormatter());
		discVal.setTextFormatter(Formatter.getDoubleFormatter());
		tranportCharge.setTextFormatter(Formatter.getDoubleFormatter());
		
		//String[] descOptions = {"B.S.N.L", "GOLDY", "BINA LABEL", "AMERICAN MOON"};
		
	}
	
	protected void configureTransport(){
		tranportCharge.setOnKeyReleased(event -> {
			  if (event.getCode() == KeyCode.ENTER){
				  calculateTranspotationCharge();
				  }
				});
		tranportCharge.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (!newPropertyValue){
		        	calculateTranspotationCharge();
		        }
		    }
		});
	}
}
