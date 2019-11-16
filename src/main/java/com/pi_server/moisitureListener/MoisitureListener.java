package com.pi_server.moisitureListener;

import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;

import come.pi_server.RaspiComponents.Raspi;

@Component
public class MoisitureListener implements Runnable {

	final GpioPinDigitalInput sensor = Raspi.gpioController.provisionDigitalInputPin(RaspiPin.GPIO_25);

	public MoisitureListener() {

	}

	@PostConstruct
	@Override
	public void run() {

		// Check the state of the sensor in startup
		PinState pinstate = PinState.LOW;
		if (sensor.isHigh()) {
			pinstate = PinState.HIGH;
		}
		
		// setup gpio pins #06 as an output pin and make sure it is LOW at startup
		Raspi.relay = Raspi.gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_06, "Relay", pinstate);
		Raspi.lcd.clear();
		Raspi.lcd.write(Raspi.LCD_ROW_1,"SmartFarm System",LCDTextAlignment.ALIGN_CENTER);
		Raspi.lcd.write(Raspi.LCD_ROW_2,"** Started **",LCDTextAlignment.ALIGN_CENTER);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Raspi.lcd.clear();
		
		for(int i=0; i<Raspi.lcd.getColumnCount(); i++) {
			Raspi.lcd.write(Raspi.LCD_ROW_1,i, '*');
			try {
				Thread.sleep(350);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		sensor.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
				Raspi.lcd.clear();
				Raspi.lcd.write(Raspi.LCD_ROW_1,"Water Detected ",LCDTextAlignment.ALIGN_CENTER);
				Raspi.lcd.write(Raspi.LCD_ROW_2, "Pump : "+Raspi.relay.getState(),LCDTextAlignment.ALIGN_CENTER);
			}
		});
		sensor.addTrigger(new GpioSyncStateTrigger(Raspi.relay));
		// sensor.addTrigger(new GpioInverseSyncStateTrigger(Raspi.relay));
		// Inverser selon RELAY TYPE
		Raspi.lcd.clear(Raspi.LCD_ROW_2);
		Raspi.lcd.write(Raspi.LCD_ROW_2, "Listening.. "+Raspi.relay.getState(),LCDTextAlignment.ALIGN_CENTER);
		
		sensor.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
			public Void call() throws Exception {
				System.out.println("--> GPIO TRIGGER CALLBACK RECEIVED ");
				return null;
			}
		}));
	}
}
