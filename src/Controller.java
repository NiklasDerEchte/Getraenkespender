import bin.Data;
import bin.GUI;
import bin.Menu;
import entity.CustomDrink;
import thread.ShutdownThread;

import java.util.Vector;

public class Controller {

    private Thread mShutdownThread;
    private GUI mLCDGui;
    private Model mModel;
    private Menu mMenu;

    public Controller() {
        this.mModel = new Model();
        this.mLCDGui = new GUI();
        this.mMenu = new Menu(this.mLCDGui);
        this.mShutdownThread = new Thread(new ShutdownThread());
        Runtime.getRuntime().addShutdownHook(this.mShutdownThread);

        if(this.mModel.inventoryExists()) {
            this.mLCDGui.printMessage("Es wurde ein");
            this.mLCDGui.printSubmessage("Inventar gefunden");
        }









        while(true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Controller();
    }
}
