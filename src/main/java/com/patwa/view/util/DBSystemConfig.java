package com.patwa.view.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
@Component
public class DBSystemConfig {
	private final static String SEP = File.separator;
	public static void copyDBFile(){
		String path = System.getProperty("user.home");
		path +=SEP+"AppData"+SEP+"Local"+SEP+"PatwaApp";
		Path p = Paths.get(path);
		
		try{
			if(!(Files.exists(p, LinkOption.NOFOLLOW_LINKS) && Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS))){
				Files.createDirectories(p);
			}
			p = Paths.get(path+SEP+"miniApp.sqlite");
			
			if(!(Files.exists(p, LinkOption.NOFOLLOW_LINKS) && Files.isRegularFile(p, LinkOption.NOFOLLOW_LINKS))){
				File file = null;
		        String resource = "miniApp.sqlite" ;
		        URL res = DBSystemConfig.class.getClassLoader().getResource(resource);
		        if (res.toString().startsWith("file:") || res.toString().startsWith("jar:")) {
		            
		                InputStream input = DBSystemConfig.class.getClassLoader().getResourceAsStream(resource);
		                file = new File(path+SEP+resource);
		                OutputStream out = new FileOutputStream(file);
		                int read;
		                byte[] bytes = new byte[1024];

		                while ((read = input.read(bytes)) != -1) {
		                    out.write(bytes, 0, read);
		                }
		                out.flush();
		                out.close();
		                input.close();
		            
		        }
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void copyFile(String fileName){
		String path = System.getProperty("user.home");
		path +=SEP+"AppData"+SEP+"Local"+SEP+"PatwaApp";
		Path p = Paths.get(path);
		
		try{
			if(!(Files.exists(p, LinkOption.NOFOLLOW_LINKS) && Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS))){
				Files.createDirectories(p);
			}
			p = Paths.get(path+SEP+fileName);
			
			if(!(Files.exists(p, LinkOption.NOFOLLOW_LINKS) && Files.isRegularFile(p, LinkOption.NOFOLLOW_LINKS))){
				File file = null;
		        URL res = DBSystemConfig.class.getClassLoader().getResource(fileName);
		        if (res.toString().startsWith("file:") || res.toString().startsWith("jar:")) {
		            
		                InputStream input = DBSystemConfig.class.getClassLoader().getResourceAsStream(fileName);
		                file = new File(path+SEP+fileName);
		                OutputStream out = new FileOutputStream(file);
		                int read;
		                byte[] bytes = new byte[1024];

		                while ((read = input.read(bytes)) != -1) {
		                    out.write(bytes, 0, read);
		                }
		                out.flush();
		                out.close();
		                input.close();
		            
		        }
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
}
