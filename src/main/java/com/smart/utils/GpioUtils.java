package com.smart.utils;

import com.pi4j.io.gpio.*;
import org.springframework.stereotype.Component;

public class GpioUtils {
   private static final GpioController gpio= GpioFactory.getInstance();
   private static final GpioPinDigitalOutput buzzer = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "buzzer", PinState.HIGH);

   public static  GpioController getGpioController(){
       return gpio;
   }

   public static GpioPinDigitalOutput getBuzzer(){
       return buzzer;
   }
}
