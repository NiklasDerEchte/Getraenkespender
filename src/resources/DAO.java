package resources;

import entity.CustomDrink;

import java.sql.*;

public class DAO {

    private Connection mConnection;
    private Statement mStatement;
    private String mDriver;
    private String mUrl;
    private char mDelimiter = ':';
    private boolean isConnected;
    private static DAO instance = null;

    public static void init(DAO dao) {
        DAO.instance = dao;
    }

    public static DAO getInstance () {
        if (DAO.instance == null) {
            throw new NullPointerException("Instance is null");
        }
        return DAO.instance;
    }

    public void setDelimiter (char delimiter) {
        this.mDelimiter = delimiter;
    }

    public DAO(String serverName, String user, String password, String port, String databaseName) {
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

    public void update(String query, String[] args) {
        query = this.createQuery(query, args);
        try {
            this.mStatement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String createQuery(String query, String[] args) {
        if(args.length > 0) {
            char[] queryCharAr = query.toCharArray();
            String[] queryStringAr = query.split(String.valueOf(this.mDelimiter));
            StringBuilder finalQuery = null;
            int count = 0;

            for(char queryChar : queryCharAr) {
                if(queryChar == this.mDelimiter) {
                    count++;
                }
            }
            if(count == args.length) {
                finalQuery = new StringBuilder();
                for(int x = 0; x < count; x++) {
                    finalQuery.append(queryStringAr[x]);
                    finalQuery.append(args[x]);
                }
                if(queryStringAr.length > count) {
                    finalQuery.append(queryStringAr[count]);
                }
            }
            return finalQuery.toString();
        }
        return query;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

}
