package com.patwa;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.patwa.view.util.DBSystemConfig;

import javafx.application.Application;

public abstract class HelloPatwaApplication extends Application{
	
	private static String[] savedArgs;

	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() throws Exception {
		DBSystemConfig.copyDBFile();
		DBSystemConfig.copyFile("bill1.jrxml");
		applicationContext = SpringApplication.run(getClass(), savedArgs);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		applicationContext.close();
	}

	protected static void launchApp(Class<? extends HelloPatwaApplication> appClass, String[] args) {

		HelloPatwaApplication.savedArgs = args;
		Application.launch(appClass, args);
	}
}
