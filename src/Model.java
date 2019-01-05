import bin.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Model {
    public Model () { DAO.init(new DAO("localhost", "pi", "nikeneo", "3306", "getraenkespender")); }

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
}
