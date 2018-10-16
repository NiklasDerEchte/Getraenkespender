package bin;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    private Connection mConnection;
    private String mDriver;
    private String mUrl;
    private boolean isConnected;

    public DB(String serverName, String user, String password, String port, String databaseName) {
        this.isConnected = false;
        this.mDriver = "com.mysql.jdbc.Driver";
        this.mUrl = "jdbc:mysql://" + serverName + ":" + port + "/" + databaseName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            Class.forName(this.mDriver);
            this.mConnection = DriverManager.getConnection(this.mUrl, user, password);
            this.isConnected = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}
