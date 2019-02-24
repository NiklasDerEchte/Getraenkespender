package resources;

public class Pump {

    private MyGPIO mGPIOPump;
    private boolean isPumpReady;

    public Pump(int gpioPin) {
        this.mGPIOPump = new MyGPIO(gpioPin, MyGPIO.OUT, 1);
        this.mGPIOPump.export();
        this.isPumpReady = true;
    }

    public void pumpMl(float ml) {
        this.isPumpReady = false;
        float pumpRatePerSecond = 60f/200f;
        float pumpTime = (pumpRatePerSecond * ml) * 1000;
        new Thread(() -> {
            mGPIOPump.setValue(this.mGPIOPump.invertCode(MyGPIO.HIGH_CODE));
            try {
                Thread.sleep((long)pumpTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mGPIOPump.setValue(this.mGPIOPump.invertCode(MyGPIO.LOW_CODE));
            this.isPumpReady = true;
        }).start();
    }

    public void pumpCl(float cl) {
        this.pumpMl(cl * 10);
    }

    public void shutdown() {
        this.mGPIOPump.setValue(this.mGPIOPump.invertCode(MyGPIO.LOW_CODE));
        this.mGPIOPump.unexport();
    }

    public boolean isPumpReady() {
        return isPumpReady;
    }
}
