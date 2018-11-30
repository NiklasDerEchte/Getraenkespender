import bin.GUI;
import thread.ShutdownThread;

public class Controller {

    private Thread mShutdownThread;

    public Controller() {
        new Model();
        this.mShutdownThread = new Thread(new ShutdownThread());
        Runtime.getRuntime().addShutdownHook(this.mShutdownThread);

        GUI gui = new GUI();
        gui.printMessage("Hallo");
        gui.printSubmessage("Welt");
    }

    public static void main(String[] args) {
        new Controller();
    }
}
