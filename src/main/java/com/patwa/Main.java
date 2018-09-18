package com.patwa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import com.patwa.view.login.LoginView;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@Lazy
@SpringBootApplication
public class Main extends HelloPatwaApplication{

	@Value("${app.ui.title:Example App}")//
	private String windowTitle;

	@Autowired
	private LoginView loginView;
	@Override
	public void start(Stage stage) throws Exception {

		notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

		stage.setTitle(windowTitle);
		stage.setScene(new Scene(loginView.getView()));
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/green-icon.png")));
		stage.show();
	}

	public static void main(String[] args) {
		launchApp(Main.class, args);
	}
}
