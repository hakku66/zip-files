package com.patwa.view.util;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

public class Formatter {
	private static <T> TextFormatter<T> getFormatter(String regex){
		Pattern decimalPattern = Pattern.compile(regex);
        UnaryOperator<Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c ;
            } else {
                return null ;
            }
        };
        return new TextFormatter<T>(filter);
	}
	
	public static TextFormatter<Double> getDoubleFormatter(){
		return getFormatter("-?\\d*(\\.\\d{0,2})?");
	}
	
	public static TextFormatter<Integer> getIntegerFormatter(){
		return getFormatter("-?\\d*");
	}
}
