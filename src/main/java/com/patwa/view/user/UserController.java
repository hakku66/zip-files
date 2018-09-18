package com.patwa.view.user;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.patwa.model.User;
import com.patwa.model.UserBean;
import com.patwa.service.UserService;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

@Lazy
@Component
public class UserController implements Initializable{
	
	@FXML
	TableView<UserBean> tableUserid;
	
	@FXML TableColumn<UserBean, Integer> iNo;
	
	@FXML
	TableColumn<UserBean, String> iUserId;
	
	@FXML
	TableColumn<UserBean, String> iPassowrd;
	
	@FXML
	TableColumn<UserBean, Integer> iActive;
	
	@FXML
	TableColumn<UserBean, String> iCreated;
	
	@Autowired
	private UserService userService;
	
	private ObservableList<UserBean> data = null;
	
	@FXML TextField userid;
	@FXML
	TextField pass;
	@FXML
	CheckBox active;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		iNo.setCellValueFactory(new PropertyValueFactory<UserBean, Integer>("rNo"));
		iUserId.setCellValueFactory(new PropertyValueFactory<UserBean, String>("rUserId"));
		iPassowrd.setCellValueFactory(new PropertyValueFactory<UserBean, String>("rPassword"));
		iActive.setCellValueFactory(new PropertyValueFactory<UserBean, Integer>("rActive"));
		iCreated.setCellValueFactory(new PropertyValueFactory<UserBean, String>("rCreated"));
		
		data = FXCollections.observableArrayList(userService.findAllUserBean());
		tableUserid.setEditable(true);
		tableUserid.setItems(data);
		
		populateCell();
	}
	
	private void clear(){
		userid.clear();
		pass.clear();
		active.setSelected(true);
	}
	public void onAddClick(){
		if(userid.getText().trim().isEmpty() || pass.getText().trim().isEmpty()){
			Alert alert = new Alert(AlertType.NONE, "User Id and Password required.", ButtonType.OK);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.show();
			return;
		}
		User user1 = userService.getUserByUserId(userid.getText());
		if(user1 == null){
			User user = new User();
			user.setUserId(userid.getText());
			user.setPassword(pass.getText());
			user.setActive(active.selectedProperty().get() ? 1 : 0);
			user.setCreated(DateUtil.getCurrentDateS());
			userService.save(user);
			data.add(new UserBean(user.getUserId(), user.getPassword(), user.getActive(), user.getCreated()));
		}else{
			Alert alert = new Alert(AlertType.NONE, "User Id already exist!!", ButtonType.OK);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.show();
		}
		clear();
	}
	public void onDeleteClick(){
		int selectedIndex = tableUserid.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	UserBean userBean = tableUserid.getItems().remove(selectedIndex);
	    	if(userBean != null){
	    		userService.delete(userBean.getRUserId(), userBean.getRPassword());
	    	}
	    }
	    clear();
	}
	
	public void onUpdateClick(){
		int selectedIndex = tableUserid.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			UserBean userBean = tableUserid.getItems().get(selectedIndex);
			
			userService.update(userBean, userid.getText(), pass.getText(), active.selectedProperty().get() ? 1 : 0);
			
			userBean.setRUserId(userid.getText());
			userBean.setRPassword(pass.getText());
			userBean.setRActive(active.selectedProperty().get() ? 1 : 0);
			tableUserid.getItems().set(selectedIndex, userBean);
			
			clear();
			
		}
	}
	
	public void populateCell(){
		tableUserid.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				int selectedIndex = tableUserid.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0) {
					UserBean userBean = tableUserid.getItems().get(selectedIndex);
					userid.setText(userBean.getRUserId());
					pass.setText(userBean.getRPassword());
					active.setSelected(userBean.getRActive() > 0 ? true : false);
				}
			}
		});
	}
}
