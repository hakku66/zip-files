package com.patwa.view.welcome;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.patwa.view.bill.BillView;
import com.patwa.view.bill.BillmanageView;
import com.patwa.view.customer.CustomerView;
import com.patwa.view.item.ItemsView;
import com.patwa.view.ower.OwnerView;
import com.patwa.view.user.UserView;
import com.patwa.view.util.AbstractFxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

@Lazy
@Component
public class WelcomeController implements Initializable{

	@FXML
	private Pane mainPane;
	@Autowired
	private UserView userView;
	@Autowired
	private OwnerView ownerView;
	@Autowired
	private CustomerView customerView;
	@Autowired
	private BillView billView;
	@Autowired
	private BillmanageView billManageView;
	@Autowired
	private ItemsView itemsView;
	
	public void manageUserLogin(){
		renderView(userView);
	}
	
	public void manageCompanyOwner(){
		renderView(ownerView);
	}
	
	public void manageCustomer(){
		renderView(customerView);
	}
	public void billGen(){
		renderView(billView);
	}
	public void billManage(){
		renderView(billManageView);
	}
	public void items(){
		renderView(itemsView);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Parent parent = billView.getView();
		mainPane.getChildren().setAll(parent);
	}
	
	private void renderView(AbstractFxmlView view){
		Parent parent = null;
		if(view instanceof BillView 
				|| view instanceof CustomerView 
				|| view instanceof BillmanageView){
			parent = view.loadAndgetView();
		}else{
			parent = view.getView();
		}
		mainPane.getChildren().setAll(parent);
	}
}
