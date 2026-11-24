package bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:h2:./bankdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createTables() throws SQLException {
        try(Connection c = getConnection(); Statement s = c.createStatement()) {

            s.execute("CREATE TABLE IF NOT EXISTS customers ("
             + "customer_id VARCHAR (10) PRIMARY KEY, "
             + "first_name VARCHAR(50) NOT NULL, "
             + "surname VARCHAR(50) NOT NULL, "
             + "address VARCHAR(100), "
             + "phone_number VARCHAR(20), "
             + "email VARCHAR(100))");

             s.execute("CREATE TABLE IF NOT EXISTS accounts ("
             +"account_number VARCHAR (20) PRIMARY KEY, "
             + "customer_id VARCHAR (10) NOT NULL, "
             + "account_type VARCHAR (20) NOT NULL, "
             + "branch VARCHAR (50), "
             + "balance DOUBLE NOT NULL, "
             + "company_name VARCHAR(100), "
             + "company_address VARCHAR (100), "
             + "FOREIGN KEY (customer_id) REFERENCES customers (customer_id))");

             s.execute ("CREATE TABLE IF NOT EXISTS transactions ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "account_number VARCHAR(20) NOT NULL, "
                + "type VARCHAR(20) NOT NULL, "
                + "amount DOUBLE NOT NULL, "
                + "balance_after DOUBLE NOT NULL, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (account_number) REFERENCES accounts(account_number))");
        }
    }
}
