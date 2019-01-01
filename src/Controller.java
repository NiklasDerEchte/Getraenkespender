import bin.Button;
import bin.Data;
import bin.GUI;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import thread.ShutdownThread;

public class Controller implements GpioPinListenerDigital {

    private Thread mShutdownThread;
    private GUI mLCDGui;
    private Model mModel;
    private Button mNextButton;
    private int mVecDrinkPos = 0;

    public Controller() {
        this.mModel = new Model();
        this.mLCDGui = new GUI();
        this.mShutdownThread = new Thread(new ShutdownThread());
        this.mNextButton = new Button(this, "pin5->21", RaspiPin.GPIO_21);
        Runtime.getRuntime().addShutdownHook(this.mShutdownThread);

        if(this.mModel.inventoryExists()) {
            this.mLCDGui.printMessage("Es wurde ein");
            this.mLCDGui.printSubmessage("Inventar gefunden");
        }
        this.loop();
    }

    private void loop() {
        while(true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        if(event.getPin().getName().equals("pin5->21") && event.getState().isHigh()) {
            if(Data.CUSTOM_DRINK_MENU_VECTOR.size() > this.mVecDrinkPos) {
                this.mLCDGui.printDrink(Data.CUSTOM_DRINK_MENU_VECTOR.get(this.mVecDrinkPos).getName());
                this.mVecDrinkPos++;
            } else {
                this.mVecDrinkPos = 0;
            }
        }
    }

    public static void main(String[] args) {
        new Controller();
    }
}
