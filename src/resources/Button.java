package resources;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Button {
    private GpioPinDigitalInput mGpioPin;
    private GpioController mGpioController;

    public Button(GpioPinListenerDigital listener, String name, Pin pin, GpioController gpioController) {
        this.mGpioController = gpioController;
        this.mGpioPin = mGpioController.provisionDigitalInputPin(pin, name, PinPullResistance.PULL_DOWN);
        this.mGpioPin.setShutdownOptions(true);
        this.mGpioPin.setDebounce(300);

        this.mGpioPin.addListener(listener);
    }
}
