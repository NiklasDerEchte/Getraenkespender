package bin;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import driver.Button;

import java.security.Guard;

public class Menu implements GpioPinListenerDigital {

    private GUI gui;
    int counter = 0;

    public Menu() {
        this.gui = new GUI();
        Button button = new Button(this, "pin5->21", RaspiPin.GPIO_21);
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        if(event.getPin().getName().equals("pin5->21") && event.getState().isHigh()) {
            if(Data.CUSTOM_DRINK_MENU_VECTOR.size() > counter) {
                gui.printMessage(Data.CUSTOM_DRINK_MENU_VECTOR.get(counter).getName());
                gui.printSubmessage("OK => Weiter !");
                counter++;
            } else {
                counter = 0;
            }
        }
    }
}
