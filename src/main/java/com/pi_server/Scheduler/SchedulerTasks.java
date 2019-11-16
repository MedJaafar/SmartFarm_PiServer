package com.pi_server.Scheduler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.pi.server.utilities.FormatUtilities;
import com.pi4j.component.lcd.LCDTextAlignment;
import come.pi_server.RaspiComponents.Raspi;
import come.pi_server.RaspiComponents.RaspiUtils;

@Component
public class SchedulerTasks {

private SimpleDateFormat DATE_FORMAT = FormatUtilities.dateformat;
private static String[] temp_hum;
private static String soilHumidity;
private static String lightIntensity;
public static boolean bActive = true ;

private RaspiUtils utils = new RaspiUtils();

@Scheduled(fixedRateString = "${fixed-rate.in.milliseconds}")
public void LCD_ViewSystemState() throws InterruptedException, IOException {
	if(bActive) {
	System.out.println("This the current time "+ DATE_FORMAT.format(new Date()));
	} else {System.out.println("disabled");}
	Raspi.lcd.clear();
	Raspi.lcd.write(Raspi.LCD_ROW_1, DATE_FORMAT.format(new Date()),LCDTextAlignment.ALIGN_CENTER);
	Raspi.lcd.write(Raspi.LCD_ROW_2, "SCHEDULED CALL",LCDTextAlignment.ALIGN_CENTER);
	
	Thread.sleep(5000);
	Raspi.lcd.clear();
	
	// Call ReadDHT11() method that returns an array of Strings
	temp_hum = utils.ReadDHT11();
	
	Raspi.lcd.write(Raspi.LCD_ROW_1, "Temp | Hum",LCDTextAlignment.ALIGN_CENTER);
	Raspi.lcd.write(Raspi.LCD_ROW_2, temp_hum[0]+"'C | "+temp_hum[1]+" %",LCDTextAlignment.ALIGN_CENTER);
	
	Thread.sleep(6000);
	Raspi.lcd.clear();
	
	// Call getHumidityADC() MCP3008
	soilHumidity = Double.toString(Raspi.getHumidityBrightnessADC()[0]);
	lightIntensity = Double.toString(Raspi.getHumidityBrightnessADC()[1]);
	
	Raspi.lcd.write(Raspi.LCD_ROW_1, "SOIL_MOISITURE",LCDTextAlignment.ALIGN_CENTER);
	Raspi.lcd.write(Raspi.LCD_ROW_2, soilHumidity ,LCDTextAlignment.ALIGN_CENTER);
	
	Thread.sleep(5000);
	Raspi.lcd.clear();
	
	Raspi.lcd.write(Raspi.LCD_ROW_1, "Light_Intensity",LCDTextAlignment.ALIGN_CENTER);
	Raspi.lcd.write(Raspi.LCD_ROW_2, lightIntensity ,LCDTextAlignment.ALIGN_CENTER);
	
	Thread.sleep(5000);
	Raspi.lcd.clear();
	
	Raspi.lcd.write(Raspi.LCD_ROW_1, "*RUNNING*",LCDTextAlignment.ALIGN_CENTER);
	Raspi.lcd.write(Raspi.LCD_ROW_2, "SYSTEM : OK " ,LCDTextAlignment.ALIGN_CENTER);
	}
}
