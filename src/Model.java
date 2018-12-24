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
            size = rs.getFetchSize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size != 0;
    }
}
