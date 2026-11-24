package bank.dao;

import bank.model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    
    public void insert(Transaction t) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, type, amount, balance_after)"
        + "VALUES (?, ?, ?, ?)";
        try (Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getAccountNumber());
            ps.setString(2, t.getType());
            ps.setDouble(3, t.getAmount());
            ps.setDouble(4, t.getBalanceAfter());
            ps.executeUpdate();
        }
    }

    public List<Transaction> findByAccount(String accountNumber) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY id";
        try (Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Transaction(
                        rs.getString("account_number"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getDouble("balance_after"),
                        rs.getTimestamp("created_at").toLocalDateTime()));
                }
            }
        }
        return list;
    }

    public void deleteByAccount(String accountNumber) throws SQLException {
        String sql = "DELETE FROM transactions WHERE account_number = ?";
        try (Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.executeUpdate();
        }
    }
}
