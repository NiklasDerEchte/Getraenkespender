import entity.CustomDrink;
import resources.DAO;
import resources.DAOParser;
import resources.Pump;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class Model {

    private Vector<CustomDrink> mCustomDrinkVector;
    private CustomDrink mCurCustomDrink;
    private int mCustomDrinkPosCounter;

    public Model () {
        this.mCustomDrinkPosCounter = 0;
        this.mCustomDrinkVector = new Vector<>();
        DAO.init(new DAO("localhost", "pi", "nikeneo", "3306", "getraenkespender"));
    }

    public boolean inventoryExists() {
        DAO dao = DAO.getInstance();
        ResultSet rs = dao.query("SELECT * FROM :", new String[]{"Inventory"});
        int size = 0;
        try {
            while(rs.next()) {
                size++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size != 0;
    }

    public void clearTable(String table) {
        DAO dao = DAO.getInstance();
        dao.update("DELETE FROM :", new String[]{table});
    }

    public void loadCustomDrinks() {
        DAO dao = DAO.getInstance();
        ResultSet rs = dao.query("SELECT * FROM :", new String[] {"CustomDrink"});
        this.mCustomDrinkVector.clear();
        CustomDrink[] customDrinks = DAOParser.parseIntoCustomDrinkArray(rs);
        this.mCustomDrinkVector.addAll(Arrays.asList(customDrinks));
    }

    public CustomDrink getNextCusctomDrink() {
        if(this.mCustomDrinkVector.size() == 0) {
            return null;
        }
        if(!(this.mCustomDrinkPosCounter < this.mCustomDrinkVector.size())) {
            this.mCustomDrinkPosCounter = 0;
        }
        this.mCurCustomDrink = this.mCustomDrinkVector.get(this.mCustomDrinkPosCounter);
        this.mCustomDrinkPosCounter++;
        return this.mCurCustomDrink;
    }

    public void startPump(Pump... pump) {
        Pump pump1 = pump[0];
        Pump pump2 = pump[1];
        Pump pump3 = pump[2];
        Pump pump4 = pump[3];
        Pump pump5 = pump[4];

        if(this.mCurCustomDrink.getVolumeCl1() > 0.1f) {
            pump1.pumpMl(this.mCurCustomDrink.getVolumeCl1());
        }

        if(this.mCurCustomDrink.getVolumeCl2() > 0.1f) {
            pump2.pumpMl(this.mCurCustomDrink.getVolumeCl2());
        }

        if(this.mCurCustomDrink.getVolumeCl3() > 0.1f) {
            pump3.pumpMl(this.mCurCustomDrink.getVolumeCl3());
        }

        if(this.mCurCustomDrink.getVolumeCl4() > 0.1f) {
            pump4.pumpMl(this.mCurCustomDrink.getVolumeCl4());
        }

        if(this.mCurCustomDrink.getVolumeCl5() > 0.1f) {
            pump5.pumpMl(this.mCurCustomDrink.getVolumeCl5());
        }
    }
}
