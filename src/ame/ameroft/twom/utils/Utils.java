package ame.ameroft.twom.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;

import ame.ameroft.twom.Launcher;

public class Utils {
	public static  String loadFileAsString(String path) {
		StringBuilder sb = new StringBuilder();
		try{
			
			BufferedReader br = new BufferedReader(new InputStreamReader(Launcher.class.getResourceAsStream(path)));;
	
			String line;

			while ((line = br.readLine()) !=null) 
				sb.append(line + "\n");


			br.close();


			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return  sb.toString();

	} 
	public static int parseInt(String number) {
		try{return Integer.parseInt(number);}catch(NumberFormatException e) {return -1;}
	}
	
	public static InputStream  ResourceLoader(String path) throws NoSuchFileException, IllegalArgumentException  {
		 

	        InputStream inputStream = Utils.class.getResourceAsStream(path);

	        if(inputStream == null)
	        {
	            throw new NoSuchFileException("Resource file not found. Note that the current directory is the source folder!");
	        }
	        if(path.startsWith("/"))
	        {
	            throw new IllegalArgumentException("Relative paths may not have a leading slash!");
	        }
	        return inputStream;
	}
}
