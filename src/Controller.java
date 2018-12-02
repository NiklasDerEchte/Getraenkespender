import bin.Data;
import bin.GUI;
import bin.Menu;
import entity.CustomDrink;
import thread.ShutdownThread;

import java.util.Vector;

public class Controller {

    private Thread mShutdownThread;

    public Controller() {
        new Model();
        this.mShutdownThread = new Thread(new ShutdownThread());
        Runtime.getRuntime().addShutdownHook(this.mShutdownThread);

        GUI gui = new GUI();
        gui.printMessage("Hallo");
        gui.printSubmessage("Welt");

        Data.CUSTOM_DRINK_MENU_VECTOR = new Vector<>();
        for(int x = 0; x < 10; x++) {
            CustomDrink customDrink = new CustomDrink();
            customDrink.setId(x);
            customDrink.setName("Hallo " + x);
            Data.CUSTOM_DRINK_MENU_VECTOR.add(customDrink);
        }
        new Menu();
        while(true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Controller();
    }
}
