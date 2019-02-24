package thread;

import main.Controller;
import resources.QueueManager;

public class QueueThread extends Thread{

    private Controller mController;
    private QueueManager mQueueManager;

    public QueueThread(Controller controller) {
        this.mController = controller;
        this.mQueueManager = new QueueManager();
    }

    @Override
    public void run() {
        super.run();
        while(true) {
            try {
                Thread.sleep(500);
                if(this.mQueueManager.hasCustomDrink()) {
                    if(!this.mController.hasQueueCustomDrink()) { // TODO: Testen !
                        this.mController.newCustomDrinkInQueue(this.mQueueManager.getNextCustomDrink()); //TODO: Die Db wird deswegen komplett gecleard
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
