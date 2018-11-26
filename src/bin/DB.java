package bin;

import java.sql.*;

public class DB {

    private Connection mConnection;
    private Statement mStatement;
    private String mDriver;
    private String mUrl;
    private String mDelimiter = "\\|";
    private boolean isConnected;
    private static DB instance = null;

    public static void init(DB db) {
        DB.instance = db;
    }

    public static DB getInstance () {
        if (DB.instance == null) {
            throw new NullPointerException("Instance is null");
        }
        return DB.instance;
    }

    public DB(String serverName, String user, String password, String port, String databaseName) {
        this.isConnected = false;
        this.mDriver = "com.mysql.jdbc.Driver";
        this.mUrl = "jdbc:mysql://" + serverName + ":" + port + "/" + databaseName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            Class.forName(this.mDriver);
            this.mConnection = DriverManager.getConnection(this.mUrl, user, password);
            this.mStatement = this.mConnection.createStatement();
            this.isConnected = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String query, String[] args) {
        query = this.createQuery(query, args);
        try {
            return this.mStatement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createQuery(String query, String[] args) {
        if(args.length > 0) {
            char[] queryCharAr = query.toCharArray();
            String[] queryStringAr = query.split(this.mDelimiter);
            String finalQuery = null;
            int count = 0;

            for(char queryChar : queryCharAr) {
                if(queryChar == this.mDelimiter) {
                    count++;
                }
            }
            if(count == args.length) {
                finalQuery = "";
                for(int x = 0; x < count; x++) {
                    finalQuery = finalQuery + queryStringAr[x];
                    finalQuery = finalQuery + args[x];
                }
            }
            return finalQuery;
        }
        return query;
    }

    public boolean isConnected() {
        return this.isConnected;
    }
}
