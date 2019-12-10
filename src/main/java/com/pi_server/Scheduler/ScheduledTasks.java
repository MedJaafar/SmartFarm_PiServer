package com.pi_server.Scheduler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.pi.server.models.FarmStatus;
import com.pi.server.models.FarmSystem;
import com.pi.server.utilities.FormatUtilities;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi_server.mdbServices.NextSequenceService;
import com.pi_server.mongoRepositories.FarmStatusRepository;
import come.pi_server.RaspiComponents.Raspi;
import come.pi_server.RaspiComponents.RaspiUtils;

@Component
public class ScheduledTasks {

private SimpleDateFormat TIME_FORMAT = FormatUtilities.TIME_FORMAT;
private static String[] temp_hum;
private static String soilHumidity;
private static String lightIntensity;
/////////////////////////////////////
private static final String CONTEXT_SYSTEM_KEY = "farmSystem";
/////////////////////////////////////
public static final long STATUS_FIXED_DELAY = 1000*60*10;
public static final long STATUS_INITIAL_DELAY = 1000*60*2;

@Autowired 
private NextSequenceService nextsequenceService;
@Autowired
private FarmStatusRepository statusRepository;
@Autowired
private ApplicationContext context;


private RaspiUtils utils = new RaspiUtils();

@Scheduled(fixedRateString = "${fixed-rate.in.milliseconds}")
public void LCD_ViewSystemState() throws InterruptedException, IOException {

	Raspi.lcd.clear();
	Raspi.lcd.write(Raspi.LCD_ROW_1, TIME_FORMAT.format(new Date()),LCDTextAlignment.ALIGN_CENTER);
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
	Raspi.lcd.write(Raspi.LCD_ROW_2, soilHumidity+" %" ,LCDTextAlignment.ALIGN_CENTER);
	
	Thread.sleep(5000);
	Raspi.lcd.clear();
	
	Raspi.lcd.write(Raspi.LCD_ROW_1, "Light_Intensity",LCDTextAlignment.ALIGN_CENTER);
	Raspi.lcd.write(Raspi.LCD_ROW_2, lightIntensity+ " %" ,LCDTextAlignment.ALIGN_CENTER);
	
	Thread.sleep(5000);
	Raspi.lcd.clear();
	
	Raspi.lcd.write(Raspi.LCD_ROW_1, "*RUNNING*",LCDTextAlignment.ALIGN_CENTER);
	Raspi.lcd.write(Raspi.LCD_ROW_2, "SYSTEM : OK " ,LCDTextAlignment.ALIGN_CENTER);
	}


	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * Export System sensors Values to MongoDB
	 */
	@Scheduled(fixedDelay = STATUS_FIXED_DELAY , initialDelay = 1000*60*2)
	public void exportFarmStatus() throws InterruptedException, IOException {
	FarmSystem system = (FarmSystem) context.getBean(CONTEXT_SYSTEM_KEY);
	// Call ReadDHT11() method that returns an array of Strings
	temp_hum = utils.ReadDHT11();
	// Call getHumidityADC() MCP3008
	double soilHumidity = Raspi.getHumidityBrightnessADC()[0];
	double lightIntensity = Raspi.getHumidityBrightnessADC()[1];
	// Creating Object	
	FarmStatus farmStatus = new FarmStatus();
	farmStatus.setStatusId(Integer.toString(nextsequenceService.getNextSequence(NextSequenceService.Farm_STATUS_SEQ)));
	farmStatus.setbWatering(Boolean.FALSE);
	farmStatus.setUserId(null);
	farmStatus.setType(FarmStatus.InsertionType.ENUM_TYPE_SCHEDULED.value);
	if(system !=null) {
	farmStatus.setSystemId(system.getSystemID()); }
	farmStatus.setValTempreture(Double.parseDouble(temp_hum[0]));
	farmStatus.setValHumidityAir(Double.parseDouble(temp_hum[1]));
	farmStatus.setValHumiditySoil(soilHumidity);
	farmStatus.setValLuminosity(lightIntensity);
	farmStatus.setWateringDuration(0);
	farmStatus.setDateInsertion(new Date());
	// End Setting values
	statusRepository.save(farmStatus);
	}
}
