package come.pi_server.RaspiComponents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.stereotype.Component;

/**
 * @author M Jaafar
 * Read from python script execution based on Runtime.exec.
 * 02/11/2019
 */
@Component
public class RaspiUtils {
	
	public String[] ReadDHT11() throws IOException, InterruptedException {
	Runtime runtime = Runtime.getRuntime();
	String line; 
	String ret = " , "; 
	
	// Prepare to reader from the output file
	Process pr = runtime.exec("python /home/pi/Desktop/Python_Scripts/dht11.py");
	BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
	
		while((line = br.readLine()) != null) {
			ret = line ; 
		}
	br.close();
	pr.waitFor();
	ret.split((","));
	String[] temp_hum =ret.split(",");
	return temp_hum;
	}
		
}

