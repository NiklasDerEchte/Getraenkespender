package bin;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Button {
    private GpioPinDigitalInput mGpioPin;
    private GpioController mGpioController;

    public Button(GpioPinListenerDigital listener, String name, Pin pin) {
        this.mGpioController = GpioFactory.getInstance();
        this.mGpioPin = mGpioController.provisionDigitalInputPin(pin, name, PinPullResistance.PULL_DOWN);
        this.mGpioPin.setShutdownOptions(true);
        this.mGpioPin.setDebounce(250);
        this.mGpioPin.addListener(listener);
    }

    public void shutdown() {
        this.mGpioPin.removeAllListeners();
        this.mGpioController.shutdown();
    }
}