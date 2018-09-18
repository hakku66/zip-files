package com.patwa.view.print;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.patwa.model.BillDetail;
import com.patwa.model.BillDetailBean;
import com.patwa.model.BillReceipt;
import com.patwa.model.Company;
import com.patwa.model.Customer;
import com.patwa.model.Owner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

@Lazy
@Component
public class PrintController implements Initializable{
	@FXML
	private AnchorPane ownerAncherPane;
	@FXML
	private AnchorPane printAP;
	@FXML
	private Label ownerGstn;
	@FXML
	private Label customerName;
	@FXML
	private Label customerAddress;
	@FXML
	private Label stateCode;
	@FXML
	private Label customerPhine;
	@FXML
	private Label customerGSTN;
	@FXML
	private Label invocreNo;
	@FXML
	private Label date;
	@FXML
	private TableView<BillDetailBean> ptable;
	@FXML
	private TableColumn<BillDetailBean, Integer> pSiNo;
	@FXML
	private TableColumn<BillDetailBean, String> pHsnCode;
	@FXML
	private TableColumn<BillDetailBean, String> pItem;
	@FXML
	private TableColumn<BillDetailBean, Double> pGSTRate;
	@FXML
	private TableColumn<BillDetailBean, Double> pRate;
	@FXML
	private TableColumn<BillDetailBean, Double> pQty;
	@FXML
	private TableColumn<BillDetailBean, Double> pGrossValue;
	@FXML
	private Label pInWorfs;
	@FXML
	private Label pNetValue;
	@FXML
	private Label totDisc;
	@FXML
	private Label pCGST;
	@FXML
	private Label pSGST;
	@FXML
	private Label pIGST;
	@FXML
	private Label pCess;
	@FXML
	private Label pTotal;
	@FXML
	private Button bPrint;
	
	protected ObservableList<BillDetailBean> billData;
	private BillReceipt billRecipt;
	private List<BillDetail> listBillDetail;
	private Customer c;
	private Owner o;
	private Company cmp;
	private double totalCgst;
	private double totalSgst;
	private double totalIgst;
	private double totalCess;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		pSiNo.setCellFactory(new Callback<TableColumn<BillDetailBean, Integer>, TableCell<BillDetailBean, Integer>>() {
//            @Override 
//            public TableCell<BillDetailBean, Integer> call(TableColumn<BillDetailBean, Integer> param) {
//                return new TableCell<BillDetailBean, Integer>() {
//                    @Override 
//                    protected void updateItem(Integer item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (this.getTableRow() != null) {
//                            int index = this.getTableRow().getIndex();
//                            if( index < ptable.getItems().size()) {
//                                int rowNum = index + 1;
//                                setText(String.valueOf(rowNum));
//                            } else {
//                                setText("");
//                            }
//                        } else {
//                            setText("");
//                        }
//                    }
//                };
//            }
//        });
//		pHsnCode.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rDescription"));
//		pItem.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rDescription"));
//		pQty.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rQTY"));
//		pRate.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rRate"));
//		pGrossValue.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rAmount"));
	}
	@FXML
	public void a(ActionEvent event) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		bPrint.setVisible(false);
		printNode(printAP);
		//System.exit(0);
	}
	public static void printNode(final AnchorPane node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
	  
		Printer printer = Printer.getDefaultPrinter();
	    PageLayout pageLayout
	        = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
	    PrinterJob job = PrinterJob.createPrinterJob();

	     node.setPrefSize(pageLayout.getPrintableWidth(), pageLayout.getPrintableHeight());
	    if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
	      boolean success = job.printPage(pageLayout, node);
	      if (success) {
	        job.endJob();
	      }
	    }
	  }
	
	public void setDataFromBillingController(Map<String, Object> mapP){
		billRecipt=(BillReceipt) mapP.get("billReceipt");
		listBillDetail=  (List<BillDetail>) mapP.get("billDetailList");
		 c =(Customer) mapP.get("customer");
		 o =(Owner) mapP.get("owner");
		 cmp=(Company) mapP.get("company");
		 configureBillDetailTable();
		 ownerGstn.setText(o.getGstNo());
		 customerName.setText(c.getName());
		 customerAddress.setText(c.getAddress()+","+c.getCity());
		 stateCode.setText(c.getState());
		 customerPhine.setText(c.getPhone1());
		 customerGSTN.setText(String.valueOf(c.getGstNo()));
		 pNetValue.setText(String.valueOf(billRecipt.getTaxableAmt()));
		 invocreNo.setText(String.valueOf(mapP.get("billReceiptId")));
		 date.setText(String.valueOf(billRecipt.getReceiptDate()));
		 totalTaxCalcutation();
		 pTotal.setText(String.valueOf(billRecipt.getTotal()));
		
	}

	protected void configureBillDetailTable(){
		pSiNo.setCellFactory(new Callback<TableColumn<BillDetailBean, Integer>, TableCell<BillDetailBean, Integer>>() {
            @Override 
            public TableCell<BillDetailBean, Integer> call(TableColumn<BillDetailBean, Integer> param) {
                return new TableCell<BillDetailBean, Integer>() {
                    @Override 
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null) {
                            int index = this.getTableRow().getIndex();
                            if( index < ptable.getItems().size()) {
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
		pHsnCode.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rDescription"));
		pItem.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rDescription"));
		pQty.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rQTY"));
		pRate.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rRate"));
		pGrossValue.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rAmount"));
		pGSTRate.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rgst"));
		pGrossValue.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rAmount"));
		//billData.clear();
		billData=FXCollections.observableArrayList();
		for(BillDetail b : listBillDetail){
			System.out.println(b);
	
			billData.add(new BillDetailBean(b.getDescription(),b.getHsnCode(), b.getQty(),b.getRate(),b.getPer(), b.getDiscVal(), b.getDiscAmt(),b.getTotalAmt(), b.getCgst(), b.getSgst(), b.getIgst(),b.getGst(),b.getcess()));
			//addTax(b.getSgst(),b.getCgst(),b.getIgst(),b.getcess());
			totalSgst=totalCgst=totalIgst=totalCess=0.0;
			totalSgst = totalSgst + b.getSgst();
			totalCgst = totalCgst + b.getCgst();
			totalIgst = totalIgst + b.getIgst();
			totalCess = totalCess + b.getcess();
	    	
			
		}
		ptable.setItems(billData);
	}
	protected void totalTaxCalcutation(){
		//sGSTId.setVisible(true);
		pSGST.setText(String.valueOf(totalSgst));
		//pCGST.setVisible(true);
		pCGST.setText(String.valueOf(totalCgst));
//		iGSTId.setVisible(true);
		pIGST.setText(String.valueOf(totalIgst));
		pCess.setText(String.valueOf(totalCess));
	}
	
	}
	
	

