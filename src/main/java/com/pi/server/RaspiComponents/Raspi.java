package com.pi.server.RaspiComponents;

import java.io.IOException;
import org.springframework.stereotype.Component;
import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.gpio.extension.base.AdcGpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3008GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3008Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;

@Component
public class Raspi {
	// public GpioController for the app
	public static GpioController gpioController = GpioFactory.getInstance();
	
	// Output Relay for the water pump
	public static GpioPinDigitalOutput relay ;
	
	//BEGIN : LCD 16*2 CONFIGURATION
	// Rows for LCD 16*2
	public final static int LCD_ROW_1 = 0;
    public final static int LCD_ROW_2 = 1;
    
	// LCD object to call
	public final static GpioLcdDisplay lcd = new GpioLcdDisplay(2,    // number of row supported by LCD
             16,       // number of columns supported by LCD
             RaspiPin.GPIO_09,  // LCD RS pin
             RaspiPin.GPIO_08,  // LCD strobe pin
             RaspiPin.GPIO_07,  // LCD data bit D4
             RaspiPin.GPIO_15,  // LCD data bit D5
             RaspiPin.GPIO_16,  // LCD data bit D6
             RaspiPin.GPIO_01); // LCD data bit D7
	 //END : LCD 16*2 CONFIGURATION
	
	 //BEGIN : MCP3008 Analog Converter Configuration //
	 public static  double [] getHumidityBrightnessADC () throws IOException {
		 double humidityADC = 0.0;
		 double brightnessADC = 0.0;
		 // Create custom MCP3008 analog gpio provider
		 // we must specify which chip select (CS) that that ADC chip is physically connected to. 
		 final AdcGpioProvider adcProvider = new MCP3008GpioProvider(SpiChannel.CS1,
		            SpiDevice.DEFAULT_SPI_SPEED,
		            SpiDevice.DEFAULT_SPI_MODE,
		            false);
		 
		 final GpioPinAnalogInput inputs[] = {
				  gpioController.provisionAnalogInputPin(adcProvider, MCP3008Pin.CH0, "MyAnalogInput-CH0"),
				  gpioController.provisionAnalogInputPin(adcProvider, MCP3008Pin.CH1, "MyAnalogInput-CH1"),
	              // gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH2, "MyAnalogInput-CH2"),
	              // gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH3, "MyAnalogInput-CH3"),
	              // gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH4, "MyAnalogInput-CH4"),
	              // gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH5, "MyAnalogInput-CH5"),
	              // gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH6, "MyAnalogInput-CH6"),
	              // gpio.provisionAnalogInputPin(provider, MCP3008Pin.CH7, "MyAnalogInput-CH7")
	     };
		 
         humidityADC = inputs[0].getValue();
         brightnessADC = inputs[1].getValue();
         double [] returnArr = new double[2];
         // 1024 -> 100%
         // val -> x
         returnArr[0]= 100-((humidityADC*100)/1024);
         returnArr[1]= (brightnessADC*100)/1024;
         adcProvider.shutdown();
         return returnArr;
	 }
	 //END : MCP3008 Analog Converter Configuration //
}
