package main;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import entity.CustomDrink;
import resources.Button;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import resources.Pump;
import thread.QueueThread;
import thread.ShutdownThread;

public class Controller implements GpioPinListenerDigital {

    private ShutdownThread mShutdownThread;
    private View mLCDView;
    private Model mModel;
    private Button mNextButton, mOkButton;
    private boolean mInventoryExists = false, mQueueMenu = false;
    private GpioController mGpioController;
    private Pump mPump1, mPump2, mPump3, mPump4, mPump5;
    private CustomDrink mTempCurQueueCustomDrink;
    private QueueThread mQueueThread;

    public Controller() {
        this.init();
        this.checkInventory();
        this.loop();
    }

    private void init() {
        this.mModel = new Model();
        this.mLCDView = new View();
        this.mQueueThread = new QueueThread(this);
        this.mGpioController = GpioFactory.getInstance();
        this.mNextButton = new Button(this, "pin5->21, Next", RaspiPin.GPIO_21, this.mGpioController);
        this.mOkButton = new Button(this, "pin6->22, Ok", RaspiPin.GPIO_22, this.mGpioController);
        this.mPump1 = new Pump(23);
        this.mPump2 = new Pump(24);
        this.mPump3 = new Pump(16);
        this.mPump4 = new Pump(20);
        this.mPump5 = new Pump(21);
        this.mShutdownThread = new ShutdownThread(
                        this.mGpioController,
                        new Pump[]{
                                this.mPump1,
                                this.mPump2,
                                this.mPump3,
                                this.mPump4,
                                this.mPump5
                        });
        this.mQueueThread.start();
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
        if(this.mQueueMenu) {
            if(this.mTempCurQueueCustomDrink != null) {
                if (event.getPin().getName().equals("pin5->21, Next") && event.getState().isHigh()) {
                    this.mQueueMenu = false;
                    this.mLCDView.printDrink(this.mModel.getCurCustomDrink().getName());
                    this.mLCDView.printNextOkMenu();
                } else if(event.getPin().getName().equals("pin6->22, Ok") && event.getState().isHigh()) {
                    this.mModel.startPump(this.mTempCurQueueCustomDrink.getId(), this.mPump1, this.mPump2, this.mPump3, this.mPump4, this.mPump5);
                }
            } else {
                this.mQueueMenu = false;
            }
            this.mTempCurQueueCustomDrink = null;
        } else if(this.mInventoryExists) {
            if (event.getPin().getName().equals("pin5->21, Next") && event.getState().isHigh()) {
                this.mModel.clearTable("Inventory");
                this.mInventoryExists = false;
                this.start();
            }
        } else if (event.getPin().getName().equals("pin5->21, Next") && event.getState().isHigh()) { // Normale Abfragen
            this.mLCDView.printDrink(this.mModel.getNextCustomDrink().getName());
        } else if(event.getPin().getName().equals("pin6->22, Ok") && event.getState().isHigh()) {
            this.mModel.startPump(this.mPump1, this.mPump2, this.mPump3, this.mPump4, this.mPump5);
        }
    }

    public boolean hasQueueCustomDrink() {
        return this.mQueueMenu;
    }

    public void newCustomDrinkInQueue(CustomDrink nextCustomDrink) {
        if(!this.mQueueMenu) {
            this.mTempCurQueueCustomDrink = nextCustomDrink;
            this.mQueueMenu = true;
            this.mLCDView.printDrink("|" + this.mTempCurQueueCustomDrink.getName());
            this.mLCDView.printDeleteOkMenu();
        }
    }
}
