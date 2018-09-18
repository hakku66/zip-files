package com.patwa.view.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	private static final SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static String getCurrentDateS(){
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}
	
	public static Date getDate(String dateStr){
		Date d = null;
		try {
			d = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static String getDateS(Date date){
		return dateFormat.format(date);
	}
	
	public static Date getDBCurrentDate(){
		Date d = null;
		try {
			 d = dbDateFormat.parse(getCurrentDateS());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static String getDBCurrentDateS(){
		Calendar calendar = Calendar.getInstance();
		return dbDateFormat.format(calendar.getTime());
	}
	
	public static Date getDBDate(String dateStr){
		Date d = null;
		try {
			 d = dbDateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static String getDateToDisplay(String dateStr){
		return dateFormat.format(getDate(dateStr));
	}
	
	public static LocalDate getCurrentLocalDate(){
		return Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static LocalDate getLocalDate(Date date){
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date getDate(LocalDate ld){
		return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static String getDateS(LocalDate ld){
		return getDateS(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}
	public static void main( String[] as){
		System.out.println(getCurrentLocalDate().toString());
	}
}
