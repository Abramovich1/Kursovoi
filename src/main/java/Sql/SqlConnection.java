package Sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс подключения к баззы данных сделаный на патерне singleton
 */
public class SqlConnection {

    private static SqlConnection instance;
    private Connection connection;
    private String url = "jdbc:sqlserver://\\SQLEXPRESS;databaseName=AutoParts;integratedSecurity=true";

    private SqlConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException ex) {
            System.out.println("Ошибка создания соединения с базой данных : " + ex.getMessage());
        }
    }
    public Connection getConnection() {
        return connection;
    }

    public static SqlConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new SqlConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new SqlConnection();
        }

        return instance;
    }
}
