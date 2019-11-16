package com.pi.server.utilities;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class StringsUtilities {
	
    public static String getSystemNameCode() throws IOException {
        try {
            // Create a new Scanner object which will read the data
            // from the file passed in. To check if there are more 
            // line to read from it we check by calling the 
            // scanner.hasNextLine() method. We then read line one 
            // by one till all lines is read.
        	Resource resource = new ClassPathResource("static/system_identification_data.txt");
        	InputStream file = resource.getInputStream();
            Scanner scanner = new Scanner(file);
            String codeRead = null;
            while (scanner.hasNextLine()) {
            	codeRead = scanner.nextLine();
                System.out.println(codeRead);
            }
            scanner.close();
            if (codeRead != null) 
            	return codeRead;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		return null;
    }
    
 // Search into string and get the dynamic url from NGROK execution http 8080
 	public static String getngrokUrl(String consoleOutput) {
 		if(!consoleOutput.equals("")) {
        int beginPos = consoleOutput.indexOf("public") + 13;
 		String ngrokUrl = consoleOutput.substring(beginPos,consoleOutput.indexOf("ngrok.io") + 8);
 		System.out.println("url : "+ngrokUrl);
 		return ngrokUrl;}
 		else {throw new RuntimeException("No return from the console Output");}
 	}
}
