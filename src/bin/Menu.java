package bin;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import driver.Button;

public class Menu implements GpioPinListenerDigital {

    private GUI mLCDGui;
    int counter = 0;
    private Button mNextButton;

    public Menu(GUI lcdGui) {
        this.mLCDGui = lcdGui;
       this.mNextButton = new Button(this, "pin5->21", RaspiPin.GPIO_21);
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        if(event.getPin().getName().equals("pin5->21") && event.getState().isHigh()) {
            if(Data.CUSTOM_DRINK_MENU_VECTOR.size() > counter) {
                this.mLCDGui.printDrink(Data.CUSTOM_DRINK_MENU_VECTOR.get(counter).getName());
                counter++;
            } else {
                counter = 0;
            }
        }
    }
}
