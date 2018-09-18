package com.patwa.view.login;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.patwa.model.User;
import com.patwa.service.UserService;
import com.patwa.view.welcome.WelcomeView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class LoginController implements Initializable{
	Logger log = LoggerFactory.getLogger(this.getClass());
	@FXML
	private Label loginMsg;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button loginButton;
	
	@Autowired
	private UserService userService;
	@Autowired
	private WelcomeView welcomeView;
	
	public void login(ActionEvent e){
		loginMsg.setText("");
		if(username.getText().isEmpty() || password.getText().isEmpty()){
			loginMsg.setText("Username or Password required.");
			return;
		}
		User user = userService.getUserByUserIdPassword(username.getText(), password.getText());
		if(user != null){
			log.info("logged in USER : "+username.getText()+"/"+password.getText());
			username.clear();
			password.clear();
			((Node)e.getSource()).getScene().getWindow().hide();
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.setTitle("Welcome "+user.getUserId());
			stage.setScene(new Scene(welcomeView.getView()));
			stage.setResizable(true);
			stage.show();
		}else{
			loginMsg.setText("Login failed");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.loginButton.setDefaultButton(true);
		
	}
}
