package com.patwa.view.bill;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.patwa.model.Customer;
import com.patwa.view.util.DateUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

@Lazy
@Component
public class BillController extends AbstractBillController{
	
	@FXML TextField buyer;
	@FXML TextField buyerName;
	@FXML TextArea buyerAddress;
	@FXML TextField buyerPan;
	@FXML TextField buyerGst;
	@FXML TextField buyerCity;
	@FXML CheckBox autoSave;
	@FXML TextField description;
	@FXML Label hsnCode;
	@FXML TextField qty;
	@FXML TextField rate;
	@FXML ChoiceBox<String> per;
	@FXML TextField discVal;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		total= 0.0;
		tranportChargeVal=0;
		totalTax=0;
		configureBillDetailTable();
		configureOwner();
		
		custMap = custServ.getAllCustomerMap();
		itemMap =itemServ.getAllItemMap();

		//for buyer
		buyer.setOnKeyReleased(event -> {
			  if (event.getCode() == KeyCode.ENTER){
				  setBuyerInfo(buyer.getText());
				  description.requestFocus();
				  }
				});
		buyer.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	setBuyerInfo(buyer.getText());
            	description.requestFocus();
            }
        });
		
		buyer.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (!newPropertyValue){
		        	setBuyerInfo(buyer.getText());
		        	description.requestFocus();
		        }
		    }
		});

		//for items
		description.setOnKeyReleased(event -> {
					  if (event.getCode() == KeyCode.ENTER){
						  setItemInfo(description.getText());
						  
						  }
						});
		description.setOnMouseClicked(new EventHandler<MouseEvent>()
		        {
		            @Override
		            public void handle(MouseEvent t) {
		            	setItemInfo(description.getText());
		            }
		        });
				
		description.focusedProperty().addListener(new ChangeListener<Boolean>(){
				    @Override
				    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
				        if (!newPropertyValue){
				        	setItemInfo(description.getText());
				        }
				    }
				});
		
		configurePer();
		populateCell();
		configureTransport();
		configureInputField();
		
		//buyerAuto.addAutoCompleteOptions(buyer, custMap.keySet());
		descAuto.addAutoCompleteOptions(description, itemMap.keySet());
		buyerAuto.addAutoCompleteOptions(buyer, custMap.keySet());
		
		
	}
	
	
	protected void setBuyerInfo(String aliasName){
		Customer c = custMap.get(aliasName);
		if(null != c){
			setCustomerId(c.getCustomerId());
			setCustomerGST(c.getGstNo());
			buyerName.setText(c.getName());
			buyerAddress.setText(c.getAddress());
			buyerPan.setText(c.getPanNo());
			buyerGst.setText(c.getGstNo());
			buyerCity.setText(c.getCity());
			autoSave.setSelected(false);
			autoSave.setDisable(true);
		}else{
			buyerGst.setEditable(true);
			autoSave.setSelected(true);
			clearBuyer();
		}
	}
	 
	protected void clearBuyer(){
		autoSave.setDisable(false);
		buyerName.clear();
		buyerAddress.clear();
		buyerPan.clear();
		buyerGst.clear();
		buyerCity.clear();
		
	}
	
	public void onAdd(){
		setCustomerGST(buyerGst.getText());
		onAddButtonClick();
		if(autoSave.selectedProperty().getValue() 
				&& (!buyer.getText().isEmpty() || !buyerName.getText().isEmpty()))
		{
			Customer c = custMap.get(buyer.getText());
			if(c == null){
				c = new Customer();
				c.setName(buyerName.getText());
				c.setAddress(buyerAddress.getText());
				c.setAliasName(buyer.getText().isEmpty() ? buyerName.getText() : buyer.getText());
				c.setPanNo(buyerPan.getText());
				c.setGstNo(buyerGst.getText());
				c.setCity(buyerCity.getText());
				c.setCreated(DateUtil.getCurrentDateS());
				c.setActive(1);
				custServ.save(c);
				custMap.put(c.getAliasName(), c);
				setCustomerId(c.getCustomerId());
				setCustomerGST(c.getGstNo());
				log.info("Auto Save Customer : "+ c);
			}
		}
	}
}
