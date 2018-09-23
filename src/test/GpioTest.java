package test;

import gpio.GPIO;

import java.io.FileNotFoundException;

public class GpioTest {
    public static void main(String[] args) {
        GPIO gpio = new GPIO();
        gpio.setPin(5);
        gpio.setTyp(GPIO.OUT);
        System.out.println(gpio.export());
        for(int x = 0; x < 5; x++) {
            gpio.setValue(GPIO.HIGH_CODE);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(gpio.isHigh() == 1) {
                gpio.setValue(GPIO.LOW_CODE);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gpio.unexport();
    }
}
