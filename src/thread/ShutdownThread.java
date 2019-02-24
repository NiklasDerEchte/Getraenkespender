package thread;

import com.pi4j.io.gpio.GpioController;
import resources.Button;
import resources.Pump;

public class ShutdownThread extends Thread {

    private GpioController mGpioController;
    private Pump[] mPumps;

    public ShutdownThread(GpioController gpioController, Pump[] pumps) {
        this.mGpioController = gpioController;
        this.mPumps = pumps;
    }

    @Override
    public void run() {
        super.run();
        if(this.mPumps != null) {
            for(Pump pump : this.mPumps) {
                pump.shutdown();
            }
        }

        this.mGpioController.removeAllListeners();
        this.mGpioController.unexportAll();

        System.out.println("\n ENDE !\n");
    }
}
