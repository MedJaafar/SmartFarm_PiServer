package com.pi_server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.io.gpio.RaspiPin;
import com.pi_server.Scheduler.SchedulerTasks;

import come.pi_server.RaspiComponents.Raspi;

@RestController
public class GpioController {
	
	@RequestMapping("/activatepump")
	public String ActivatePump() throws InterruptedException {
		if(Raspi.relay == null) {
		Raspi.relay = Raspi.gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_10, "Relay");}
		Raspi.relay.toggle();
		Raspi.lcd.clear();
		Raspi.lcd.write(Raspi.LCD_ROW_1, "Water Pump",LCDTextAlignment.ALIGN_CENTER);
		Raspi.lcd.write(Raspi.LCD_ROW_2, "Acitivated",LCDTextAlignment.ALIGN_CENTER);
		Thread.sleep(3000);
		Raspi.lcd.clear();
		Raspi.lcd.write(Raspi.LCD_ROW_1, "Water Pump",LCDTextAlignment.ALIGN_CENTER);
		Raspi.lcd.write(Raspi.LCD_ROW_2, "Stopped",LCDTextAlignment.ALIGN_CENTER);
		Raspi.relay.toggle();
		return "OK";
	}
	
	@RequestMapping("/bactiveFalse")
	public String bActiveToggle(){
		boolean bActive = SchedulerTasks.bActive;
		if(bActive) {
			SchedulerTasks.bActive = false;}
		else {SchedulerTasks.bActive = true;}
		return "Bactive toggle !";	
	}
	
}
