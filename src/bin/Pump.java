package bin;

public class Pump {
    MyGPIO mGPIOPump;
    public Pump(int gpioPin) {
        this.mGPIOPump = new MyGPIO(gpioPin, MyGPIO.OUT);
        this.mGPIOPump.export();
        if(this.mGPIOPump.isHigh() == 1) {
            this.mGPIOPump.setValue(MyGPIO.LOW_CODE);
        }
    }

    public void pumpMl(float ml) {
        float pumpRatePerSecond = 60/200;
        float pumpTime = (pumpRatePerSecond * ml) * 1000;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Pump.this.mGPIOPump.setValue(MyGPIO.HIGH_CODE);
                try {
                    Thread.sleep((long)pumpTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Pump.this.mGPIOPump.setValue(MyGPIO.LOW_CODE);
        }
        }).start();
    }

    public void pumpCl(float cl) {
        this.pumpMl(cl * 10);
    }

    public void shutdown() {
        this.mGPIOPump.setValue(MyGPIO.LOW_CODE);
        this.mGPIOPump.unexport();
    }
}
