package com.patwa.view.item;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.patwa.model.Item;
import com.patwa.model.ItemBean;
import com.patwa.service.ItemService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

@Lazy
@Component
public class ItemsController implements Initializable{

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    private Label itemsID;

    @FXML
    private TextField itemsItemName;

    @FXML
    private TextField itemsHSNNumber;

    @FXML
    private ChoiceBox<String> gstRate;

    @FXML
    private ChoiceBox<String> qtyType;
    @FXML
    private CheckBox itemIGST;
    @FXML
    private TextField itemsCESS;

    @FXML
    private TextField itemsStock;
    
    @FXML
    private TextField itemsCostPrice;

    @FXML
    private TextField itemsSellingPrice;

    @FXML
    private Button billinSaveAndPrintButton;

    @FXML
    private TableView<ItemBean> table;

    @FXML
    private TableColumn<ItemBean, Integer> collmItemsID;

    @FXML
    private TableColumn<ItemBean, String> collName;

    @FXML
    private TableColumn<ItemBean, String> collHSNNumber;

    @FXML
    private TableColumn<ItemBean, String> collQtyType;

    @FXML
    private TableColumn<ItemBean, Float> collGstRate;

    @FXML
    private TableColumn<ItemBean, Float> collCESS;

    @FXML
    private TableColumn<ItemBean, Float> collStock;

    @FXML
    private TableColumn<ItemBean, Float> collCostPrice;

    @FXML
    private TableColumn<ItemBean, Float> collSellingPrice;

	private ObservableList<ItemBean> data = null;
    
	@Autowired
	private ItemService itemServ;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		String[] qty = { "BAGS", "BOX", "PIECES", "BOTTLES", "CARTONS", "KILOGRAMS","LITRE","SqrFt","Meter" };
		qtyType.getItems().addAll(qty);
		String[] gst = { "0.0", "0.25", "5.0", "12.0", "18.0", "28.0" };
		gstRate.getItems().addAll(gst);
		
		collmItemsID.setCellFactory(new Callback<TableColumn<ItemBean, Integer>, TableCell<ItemBean, Integer>>() {
            @Override 
            public TableCell<ItemBean, Integer> call(TableColumn<ItemBean, Integer> param) {
                return new TableCell<ItemBean, Integer>() {
                    @Override 
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null) {
                            int index = this.getTableRow().getIndex();
                            if( index < table.getItems().size()) {
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
	collName.setCellValueFactory(new PropertyValueFactory<ItemBean,String>("name"));
	collHSNNumber.setCellValueFactory(new PropertyValueFactory<ItemBean,String>("hsnNo"));
	collQtyType.setCellValueFactory(new PropertyValueFactory<ItemBean,String>("qtyType"));
	collGstRate.setCellValueFactory(new PropertyValueFactory<ItemBean,Float>("gstP"));
	collCESS.setCellValueFactory(new PropertyValueFactory<ItemBean,Float>("cess"));
	collStock.setCellValueFactory(new PropertyValueFactory<ItemBean,Float>("stock"));
	collCostPrice.setCellValueFactory(new PropertyValueFactory<ItemBean,Float>("costPrice"));
	collSellingPrice.setCellValueFactory(new PropertyValueFactory<ItemBean,Float>("sellingPrice"));
	
	data = FXCollections.observableArrayList(itemServ.getAllItemBeans());
	
	table.setItems(data);
	populateCell();
	}

	public void populateCell(){
		table.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				int selectedIndex = table.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0) {
					ItemBean itemBean = table.getItems().get(selectedIndex);
					itemsItemName.setText(itemBean.getName());
					itemsHSNNumber.setText(itemBean.getHsnNo());
					if(itemBean.getIgst())
					itemIGST.setSelected(true);
					gstRate.setValue(String.valueOf(itemBean.getGstP()));
					gstRate.setValue(String.valueOf(itemBean.getGstP()));
					qtyType.setValue(itemBean.getQtyType());
					itemsCESS.setText(String.valueOf(itemBean.getCess()));
					itemsCostPrice.setText(String.valueOf(itemBean.getCostPrice()));
					itemsSellingPrice.setText(String.valueOf(itemBean.getSellingPrice()));
					itemsStock.setText(String.valueOf(itemBean.getStock()));
					qtyType.setValue(itemBean.getQtyType());
				}
			}
		});
	}
    
    @FXML
    void deleteItems(ActionEvent event) {
    	int selectedIndex = table.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	//ItemBean itemBean = table.getItems().get(selectedIndex);
	    	ItemBean itemBean = table.getItems().remove(selectedIndex);
	    	if(itemBean != null){
	    		itemServ.delete(itemBean.getHsnNo());
	    	}
	    }
	    clear();
    }

    @FXML
    void editItems(ActionEvent event) {
    	int selectedIndex = table.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			ItemBean itemBean = table.getItems().get(selectedIndex);
			
			Item o = itemServ.getByHSN(itemBean.getHsnNo());
			o.setName(itemsItemName.getText());
			o.setHsnNo(itemsHSNNumber.getText());
			o.setQtyType(qtyType.getValue());
			o.setGstP(Float.parseFloat(gstRate.getValue()));
		
			o.setIgst(itemIGST.isSelected());
			o.setCess(Float.parseFloat(itemsCESS.getText()));
			o.setCostPrice(Float.parseFloat(itemsCostPrice.getText()));
			o.setSellingPrice(Float.parseFloat(itemsSellingPrice.getText()));
			o.setStock(Float.parseFloat(itemsStock.getText()));
			itemServ.save(o);
			
			itemBean = new ItemBean(o.getName(),o.getHsnNo(),o.getQtyType(),o.getGstP(),o.isIgst(),o.getCess(),o.getStock(),o.getCostPrice(),o.getSellingPrice());
			
			table.getItems().set(selectedIndex, itemBean);
			
			clear();
			
		}
	
    }

    @FXML
    void itemsAdditems(ActionEvent event) {
		Item item  = itemServ.getByHSN(itemsHSNNumber.getText());

		if(item == null){
			if(itemServ.getByName(itemsItemName.getText()) == null){
				
			
			Item o = new Item();
			//mItemsCGST = (mItemsCostPrice * (mGstRate) / 2) / 100;
			//mItemsSGST = (mItemsCostPrice * (mGstRate) / 2) / 100;
			o.setName(itemsItemName.getText());
			o.setHsnNo(itemsHSNNumber.getText());
			o.setQtyType(qtyType.getValue());
			o.setGstP(Float.parseFloat(gstRate.getValue()));
			o.setIgst(itemIGST.isSelected());
			o.setCess(Float.parseFloat(itemsCESS.getText()));
			o.setCostPrice(Float.parseFloat(itemsCostPrice.getText()));
			o.setSellingPrice(Float.parseFloat(itemsSellingPrice.getText()));
			o.setStock(Float.parseFloat(itemsStock.getText()));

			itemServ.save(o);
			data.add(new ItemBean(o.getName(),o.getHsnNo(),o.getQtyType(),o.getGstP(),o.isIgst(),o.getCess(),o.getStock(),o.getCostPrice(),o.getSellingPrice()));
			
			
			}else{
				Alert alert = new Alert(AlertType.NONE, "Item already exist!!", ButtonType.OK);
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.show();
				return;
	}
		}else{
			Alert alert = new Alert(AlertType.NONE, "Item already exist!!", ButtonType.OK);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.show();
			return;
		}
		clear();
    }
	private void clear(){
		itemsItemName.clear();
		itemsHSNNumber.clear();
		qtyType.setValue("");
		gstRate.setValue("");
		itemIGST.setSelected(false);
		itemsCESS.clear();
		itemsCostPrice.clear();
		itemsSellingPrice.clear();
		itemsStock.clear();
	}

}
