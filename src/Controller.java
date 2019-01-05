import bin.Button;
import bin.Data;
import bin.GUI;
import bin.Pump;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import thread.ShutdownThread;

public class Controller implements GpioPinListenerDigital {

    private Thread mShutdownThread;
    private GUI mLCDGui;
    private Model mModel;
    private Button mNextButton, mOkButton;
    private boolean mInventoryExists = false;
    private int mVecDrinkPos = 0;

    public Controller() {
        this.mModel = new Model();
        this.mLCDGui = new GUI();
        this.mNextButton = new Button(this, "pin5->21, Next", RaspiPin.GPIO_21);
        this.mShutdownThread = new Thread(
                new ShutdownThread(
                        new Button[]{
                                this.mNextButton
                        },
                        new Pump[]{

                        })
        );
        Runtime.getRuntime().addShutdownHook(this.mShutdownThread);
        this.checkInventory();
        this.loop();
    }

    private void checkInventory() {
        if(this.mModel.inventoryExists()) {
            this.mLCDGui.printMessage("Inventar gefunden !");
            this.mLCDGui.printSubmessage("Verw.    Pruefen");
            this.mInventoryExists = true;
        } else {
            this.start();
        }
    }

    private void start() {
        this.mLCDGui.printMessage("Willkommen !");
        this.mLCDGui.printNextOkMenu();
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
        if(this.mInventoryExists) {
            if (event.getPin().getName().equals("pin5->21, Next") && event.getState().isHigh()) {
                this.mModel.clearTable("Inventory");
                this.mInventoryExists = false;
                this.start();
            }
        } else if (event.getPin().getName().equals("pin5->21, Next") && event.getState().isHigh()) {
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
