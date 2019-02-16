import resources.Button;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import resources.Pump;
import thread.ShutdownThread;

public class Controller implements GpioPinListenerDigital {

    private ShutdownThread mShutdownThread;
    private View mLCDView;
    private Model mModel;
    private Button mNextButton, mOkButton;
    private boolean mInventoryExists = false;
    private int mVecDrinkPos = 0;
    private Pump mPump1, mPump2, mPump3, mPump4, mPump5;

    public Controller() {
        this.init();
        this.checkInventory();
        this.loop();
    }

    private void init() {
        this.mModel = new Model();
        this.mLCDView = new View();
        this.mNextButton = new Button(this, "pin5->21, Next", RaspiPin.GPIO_21);
        this.mOkButton = new Button(this, "pin6->22, Ok", RaspiPin.GPIO_22);
        this.mPump1 = new Pump(23);
        this.mPump2 = new Pump(24);
        this.mPump3 = new Pump(16);
        this.mPump4 = new Pump(20);
        this.mPump5 = new Pump(21);
        this.mShutdownThread = new ShutdownThread(
                        new Button[]{
                                this.mNextButton,
                                this.mOkButton
                        },
                        new Pump[]{
                                this.mPump1,
                                this.mPump2,
                                this.mPump3,
                                this.mPump4,
                                this.mPump5
                        });
        Runtime.getRuntime().addShutdownHook(this.mShutdownThread);
    }

    private void checkInventory() {
        if(this.mModel.inventoryExists()) {
            this.mLCDView.printMessage("Inventar gefunden !");
            this.mLCDView.printSubmessage("Verw.    Pruefen");
            this.mInventoryExists = true;
        } else {
            this.start();
        }
    }

    private void start() {
        this.mLCDView.printMessage("Willkommen !");
        this.mModel.loadCustomDrinks();
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mLCDView.printNextOkMenu();
    }

    private void loop() {
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            this.mLCDView.printDrink(this.mModel.getNextCusctomDrink().getName());
            this.mVecDrinkPos++;
        } else if(event.getPin().getName().equals("pin6->22, Ok") && event.getState().isHigh()) {
            this.mModel.startPump(this.mPump1, this.mPump2, this.mPump3, this.mPump4, this.mPump5);
        }
    }

    public static void main(String[] args) {
        new Controller();
    }
}
