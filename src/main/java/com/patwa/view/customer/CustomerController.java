package com.patwa.view.customer;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.patwa.model.Customer;
import com.patwa.model.CustomerBean;
import com.patwa.service.CustomerService;
import com.patwa.service.StateCodeService;
import com.patwa.view.util.DateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.Callback;

@Lazy
@Component
public class CustomerController implements Initializable{
	
	@FXML TableView<CustomerBean> tableCustomerid;
	@FXML TableColumn<CustomerBean, Integer> iNo;
	@FXML TableColumn<CustomerBean, String> name;
	@FXML TableColumn<CustomerBean, String> email;
	@FXML TableColumn<CustomerBean, String> phone1;
	@FXML TableColumn<CustomerBean, String> phone2;
	@FXML TableColumn<CustomerBean, String> state;
	@FXML TableColumn<CustomerBean, String> city;
	@FXML TableColumn<CustomerBean, String> address;
	@FXML TableColumn<CustomerBean, String> pin;
	@FXML TableColumn<CustomerBean, String> created;
	@FXML TableColumn<CustomerBean, String> aliasName;
	@FXML TableColumn<CustomerBean, String> panNo;
	@FXML TableColumn<CustomerBean, String> gstNo;
	@FXML TableColumn<CustomerBean, Integer> active;
	
	@FXML TextField nameId;
	@FXML TextField emailId;
	@FXML TextField phone1Id;
	@FXML TextField phone2Id;
	@FXML ChoiceBox<String> stateId;
	@FXML TextField cityId;
	@FXML TextField addressId;
	@FXML TextField pinId;
	@FXML TextField aliasNameId;
	@FXML TextField panNoId;
	@FXML TextField gstNoId;
	@FXML CheckBox activeId;
	
	private ObservableList<CustomerBean> data = null;
	private ObservableList<String> stateData = null;
	
	@Autowired
	private CustomerService custServ;
	@Autowired
	private StateCodeService scServ;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iNo.setCellFactory(new Callback<TableColumn<CustomerBean, Integer>, TableCell<CustomerBean, Integer>>() {
            @Override 
            public TableCell<CustomerBean, Integer> call(TableColumn<CustomerBean, Integer> param) {
                return new TableCell<CustomerBean, Integer>() {
                    @Override 
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null) {
                            int index = this.getTableRow().getIndex();
                            if( index < tableCustomerid.getItems().size()) {
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
		name.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rName"));
		email.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rEmail"));
		phone1.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rPhone1"));
		phone2.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rPhone2"));
		state.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rState"));
		city.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rCity"));
		address.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rAdress"));
		pin.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rPin"));
		created.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rCreated"));
		aliasName.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rAliasName"));
		panNo.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rPanNo"));
		gstNo.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rGstNo"));
		active.setCellValueFactory(new PropertyValueFactory<CustomerBean, Integer>("rActive"));
		
		stateData = FXCollections.observableArrayList(scServ.getAllState());
		stateId.setItems(stateData);
		stateId.getSelectionModel().select("Bihar");
		data = FXCollections.observableArrayList(custServ.getAllCustomerBeans());
		
		tableCustomerid.setItems(data);
		
		populateCell();
	}
	
	public void onAdd(){
		Customer customer  = custServ.getByPanNo(panNoId.getText());
		
		if(customer == null){
			Customer o = new Customer();
			o.setName(nameId.getText());
			o.setEmail(emailId.getText());
			o.setPhone1(phone1Id.getText());
			o.setPhone2(phone2Id.getText());
			o.setState(stateId.getValue());
			o.setCity(cityId.getText());
			o.setAddress(addressId.getText());
			o.setPin(pinId.getText());
			o.setCreated(DateUtil.getDBCurrentDateS());
			o.setAliasName(aliasNameId.getText());
			o.setPanNo(panNoId.getText());
			o.setGstNo(gstNoId.getText());
			o.setActive(activeId.selectedProperty().get() ? 1 : 0);
			
			custServ.save(o);
			data.add(new CustomerBean(
					o.getName(),
					o.getEmail(),
					o.getPhone1(),
					o.getPhone2(),
					o.getState(),
					o.getCity(),
					o.getAddress(),
					o.getPin(),
					o.getCreated(),
					o.getAliasName(),
					o.getPanNo(),
					o.getGstNo(),
					o.getActive()));
		}else{
			Alert alert = new Alert(AlertType.NONE, "Customer already exist!!", ButtonType.OK);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.show();
			return;
		}
		clear();
	}
	
	public void onUpdate(){
		int selectedIndex = tableCustomerid.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			CustomerBean customerBean = tableCustomerid.getItems().get(selectedIndex);
			
			Customer o = custServ.getByNameAndPanNo(customerBean.getRName(), customerBean.getRPanNo());
			o.setName(nameId.getText());
			o.setEmail(emailId.getText());
			o.setPhone1(phone1Id.getText());
			o.setPhone2(phone2Id.getText());
			o.setState(stateId.getValue());
			o.setCity(cityId.getText());
			o.setAddress(addressId.getText());
			o.setPin(pinId.getText());
			o.setCreated(DateUtil.getDBCurrentDateS());
			o.setAliasName(aliasNameId.getText());
			o.setPanNo(panNoId.getText());
			o.setGstNo(gstNoId.getText());
			o.setActive(activeId.selectedProperty().get() ? 1 : 0);
			
			custServ.save(o);
			
			customerBean = new CustomerBean(
					o.getName(),
					o.getEmail(),
					o.getPhone1(),
					o.getPhone2(),
					o.getState(),
					o.getCity(),
					o.getAddress(),
					o.getPin(),
					o.getCreated(),
					o.getAliasName(),
					o.getPanNo(),
					o.getGstNo(),
					o.getActive());
			
			tableCustomerid.getItems().set(selectedIndex, customerBean);
			
			clear();
			
		}
	}

	public void onDelete(){
		int selectedIndex = tableCustomerid.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	CustomerBean customerBean = tableCustomerid.getItems().remove(selectedIndex);
	    	if(customerBean != null){
	    		custServ.delete(customerBean.getRPanNo());
	    	}
	    }
	    clear();
	}
	
	public void populateCell(){
		tableCustomerid.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				int selectedIndex = tableCustomerid.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0) {
					CustomerBean customerBean = tableCustomerid.getItems().get(selectedIndex);
					nameId.setText(customerBean.getRName());
					emailId.setText(customerBean.getREmail());
					phone1Id.setText(customerBean.getRPhone1());
					phone2Id.setText(customerBean.getRPhone2());
					stateId.setValue(customerBean.getRState());
					cityId.setText(customerBean.getRCity());
					addressId.setText(customerBean.getRAdress());
					pinId.setText(customerBean.getRPin());
					aliasNameId.setText(customerBean.getRAliasName());
					panNoId.setText(customerBean.getRPanNo());
					gstNoId.setText(customerBean.getRGstNo());
					activeId.setSelected(customerBean.getRActive() > 0 ? true : false);
				}
			}
		});
	}
	private void clear(){
		nameId.clear();
		emailId.clear();
		phone1Id.clear();
		phone2Id.clear();
		stateId.setValue("");
		cityId.clear();
		addressId.clear();
		pinId.clear();
		aliasNameId.clear();
		panNoId.clear();
		gstNoId.clear();
		activeId.setSelected(true);
	}
}
