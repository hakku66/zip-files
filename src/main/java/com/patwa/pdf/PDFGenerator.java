package com.patwa.pdf;

import java.io.File;
import java.util.Map;

import com.patwa.view.util.DesktopApi;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class PDFGenerator {
	private final static String SEP = File.separator;
	public static String generateBillPDF(Map<String, Object> param) throws JRException {
		String outPath = "C:"+SEP+"bills";
		
			String path = System.getProperty("user.home");
			path +=SEP+"AppData"+SEP+"Local"+SEP+"PatwaApp"+SEP+"bill1.jrxml";
			JasperReport jasperReport = JasperCompileManager.compileReport(path);
			JRDataSource dataSource = new JREmptyDataSource();
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, dataSource);
			
			// Make sure the output directory exists.
			String fileName = (String)param.get("billReceiptId");
			fileName = fileName.replaceAll("/", "_");
			File outDir = new File(outPath);
			outDir.mkdirs();
			outPath = outPath+"/"+fileName+".pdf";
			// Export to PDF.
			JasperExportManager.exportReportToPdfFile(jasperPrint, outPath);
		
		return outPath;
	}
	
	public static void OpenPdfFile(String filePath){
		DesktopApi.open(new File(filePath));
	}
}
