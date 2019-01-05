package thread;

import bin.Button;
import bin.Pump;

public class ShutdownThread implements Runnable {

    private Button[] mButtons;
    private Pump[] mPumps;

    public ShutdownThread(Button[] buttons, Pump[] pumps) {
        this.mButtons = buttons;
        this.mPumps = pumps;
    }

    @Override
    public void run() {
        if(this.mPumps != null) {
            for(Pump pump : this.mPumps) {
                pump.shutdown();
            }
        }

        if(this.mButtons != null) {
            for(Button button : this.mButtons) {
                button.shutdown();
            }
        }

        System.out.println("\n ENDE !\n");
    }
}
