package resources;

import entity.CustomDrink;
import entity.Queue;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueueManager {

    private List<Queue> mQueueList;
    private String mInOrderStatus = "inOrder", mFinishedStatus = "finished", mMakingStatus = "making";

    public QueueManager() {
        /*
        * TODO: Wird für das Webinterface genutzt. Muss dazu verändert werden.
        */
        this.mQueueList = new ArrayList<>();
        this.load();
    }

    private void load() {
        DAO dao = DAO.getInstance();
        ResultSet resultSet = dao.query("SELECT * FROM :", new String[]{"Queue"});
        this.mQueueList.clear();
        this.mQueueList.addAll(Arrays.asList(DAOParser.parseIntoQueueArray(resultSet)));
    }

    public boolean hasCustomDrink() {
        this.load();
        for(Queue queue : this.mQueueList) {
            if(queue.getCustomDrinkFk() != 0) {
                return true;
            }
        }
        return false;
    }

    public CustomDrink getNextCustomDrink() {
        CustomDrink  ret = null;
        if(hasCustomDrink()) {
           for(Queue queue : this.mQueueList) {
               if(queue.getCustomDrinkFk() != 0 && queue.getStatus().equals(this.mInOrderStatus)) {
                   ret = this.getCustomDrinkFromDB(queue.getCustomDrinkFk());
                   DAO dao = DAO.getInstance();
                   dao.update("DELETE FROM : WHERE id LIKE :", new String[]{"Queue", String.valueOf(queue.getId())}); //TODO: Vlt ein status setzten anstatt zu löschen
               }
           }
        }
        this.load();
        return ret;
    }

    private CustomDrink getCustomDrinkFromDB(int customDrinkFk) {
        DAO dao = DAO.getInstance();
        ResultSet resultSet = dao.query("SELECT * FROM : WHERE id LIKE :", new String[]{"CustomDrink", String.valueOf(customDrinkFk)});
        return DAOParser.parseIntoCustomDrink(resultSet);
    }

    public void addCustomDrinkToQueue(CustomDrink customDrink) {
        DAO dao = DAO.getInstance();
        ResultSet resultSet = dao.query("SELECT * FROM : WHERE name LIKE : AND description LIKE :", new String[]{"CustomDrink", "'" +customDrink.getName() + "'", "'" + customDrink.getDescription() + "'"});
        customDrink = DAOParser.parseIntoCustomDrink(resultSet);
        dao.update("INSERT INTO : (:, :) VALUES (:, :)", new String[]{"Queue", "customDrinkFk", "status", String.valueOf(customDrink.getId()), "'" + this.mInOrderStatus + "'"});
        this.load();
    }
}
