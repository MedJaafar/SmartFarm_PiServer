package com.pi_sever.services;

import java.io.IOException;
import java.util.Date;
import org.springframework.context.ApplicationContext;

import com.pi.server.RaspiComponents.Raspi;
import com.pi.server.models.FarmStatus;
import com.pi.server.models.FarmSystem;
import com.pi_server.mdbServices.NextSequenceService;
import com.pi_server.mongoRepositories.FarmStatusRepository;


public class ExportDataService {
	
	/**
	 * @author M Jaafar
	 * Method save FarmStatus on every Moisiture sensor detection.
	 * @throws InterruptedException 
	 * @throws IOException  
	 */
	public static void exportFarmStatusOnTrigger(NextSequenceService nextsequenceService,FarmStatusRepository statusRepository,boolean bWatering,int operationType,ApplicationContext context,String contextSystemKey) throws IOException, InterruptedException {
		FarmSystem system = (FarmSystem) context.getBean(contextSystemKey);
		FarmStatus lastStatus = statusRepository.findTopBySystemIdOrderByDateInsertionDesc(system.getSystemID());
		Long wateringDuration = 0L;
		if(lastStatus.getType() == operationType
				&& lastStatus.isbWatering() && !bWatering ) {
			Long beginWateringTime = lastStatus.getDateInsertion().getTime();
			Long endWateringTime = new Date().getTime();
			// Time of watering in seconds
			wateringDuration = (endWateringTime - beginWateringTime)/1000;
		}
		// Call ReadDHT11() method that returns an array of Strings
		double lastTempretureVal = 0.0;
		double lastHumidityAirVal = 0.0;
		if(lastStatus != null) {
		 lastTempretureVal = lastStatus.getValTempreture();
		 lastHumidityAirVal = lastStatus.getValHumidityAir();
		 }
		// Call getHumidityADC() MCP3008
		double soilHumidity = Raspi.getHumidityBrightnessADC()[0];
		double lightIntensity = Raspi.getHumidityBrightnessADC()[1];
		// Creating Object	
		FarmStatus farmStatus = new FarmStatus();
		farmStatus.setStatusId(Integer.toString(nextsequenceService.getNextSequence(NextSequenceService.Farm_STATUS_SEQ)));
		farmStatus.setbWatering(bWatering);
		farmStatus.setUserId(null);
		farmStatus.setType(operationType);
		if(system !=null) {
		farmStatus.setSystemId(system.getSystemID()); }
		farmStatus.setValTempreture(lastTempretureVal);
		farmStatus.setValHumidityAir(lastHumidityAirVal);
		farmStatus.setValHumiditySoil(soilHumidity);
		farmStatus.setValLuminosity(lightIntensity);
		farmStatus.setWateringDuration(wateringDuration.intValue());
		farmStatus.setDateInsertion(new Date());
		// End Setting values
		statusRepository.save(farmStatus);
		}

}
