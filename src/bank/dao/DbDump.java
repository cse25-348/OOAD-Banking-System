package bank.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbDump {
    public static void main(String[] args) throws Exception {
        Database.createTables();
        try (Connection c = Database.getConnection(); Statement s = c.createStatement()) {
            System.out.println("CUSTOMERS");
            int customers = 0;
            try (ResultSet rs = s.executeQuery("SELECT * FROM customers ORDER BY customer_id")) {
                while (rs.next()) {
                    customers++;
                    System.out.println(rs.getString("customer_id") + "  "
                            + rs.getString("first_name") + " " + rs.getString("surname"));
                }
            }
            System.out.println("Total customers: " + customers);

            System.out.println();
            System.out.println("ACCOUNTS");
            int accounts = 0;
            try (ResultSet rs = s.executeQuery("SELECT * FROM accounts ORDER BY customer_id, account_number")) {
                while (rs.next()) {
                    accounts++;
                    System.out.println(rs.getString("customer_id") + "  "
                            + rs.getString("account_type") + "  "
                            + rs.getString("account_number") + "  BWP "
                            + String.format("%.2f", rs.getDouble("balance")));
                }
            }
            System.out.println("Total accounts: " + accounts);
        }
    }
}