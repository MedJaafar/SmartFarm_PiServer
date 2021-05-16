package com.pi_server.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pi.server.RaspiComponents.Raspi;
import com.pi.server.models.FarmStatus;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.io.gpio.RaspiPin;
import com.pi_server.mdbServices.NextSequenceService;
import com.pi_server.mongoRepositories.FarmStatusRepository;
import com.pi_sever.services.ExportDataService;

@CrossOrigin(origins="*")
@RestController
public class GpioController {

	private static final String ACTIVATE_PUMP_SYSTEM_PATH = "/activatepump/";
	@Autowired
	private NextSequenceService nextsequenceService;
	@Autowired
	private FarmStatusRepository statusRepository;
	@Autowired
	private ApplicationContext context;
	/////////////////////////////////////////////////////////////
	private static final String CONTEXT_SYSTEM_KEY = "farmSystem";

	@GetMapping(ACTIVATE_PUMP_SYSTEM_PATH+"{activationDelay}")
	public void ActivatePump(@PathVariable String activationDelay) throws InterruptedException, IOException {
		Long delaySeconds;
		if(Raspi.relay == null) {
			Raspi.relay = Raspi.gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_10, "Relay"); }
		try {
			delaySeconds = Long.parseLong(activationDelay);
		}catch (NumberFormatException e) {
			delaySeconds = 3L;
		}
		Raspi.relay.toggle();
		// Export Status when Active Pump
		ExportDataService.exportFarmStatusOnTrigger(nextsequenceService, statusRepository, Raspi.relay.isHigh(), FarmStatus.InsertionType.ENUM_TYPE_USER.value, context, CONTEXT_SYSTEM_KEY);
		// LCD CODE
		Raspi.lcd.clear();
		Raspi.lcd.write(Raspi.LCD_ROW_1, "Water Pump",LCDTextAlignment.ALIGN_CENTER);
		Raspi.lcd.write(Raspi.LCD_ROW_2, "Acitivated",LCDTextAlignment.ALIGN_CENTER);
		// Keep Running on user demand
		Thread.sleep(delaySeconds*1000);
		// LCD CODE
		Raspi.lcd.clear();
		Raspi.lcd.write(Raspi.LCD_ROW_1, "Water Pump",LCDTextAlignment.ALIGN_CENTER);
		Raspi.lcd.write(Raspi.LCD_ROW_2, "Stopped",LCDTextAlignment.ALIGN_CENTER);
		Raspi.relay.toggle();
		ExportDataService.exportFarmStatusOnTrigger(nextsequenceService, statusRepository, Raspi.relay.isHigh(), FarmStatus.InsertionType.ENUM_TYPE_USER.value, context, CONTEXT_SYSTEM_KEY);
	}
}
