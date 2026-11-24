package bank.dao;

import bank.model.Account;
import bank.model.AccountType;
import bank.model.ChequeAccount;
import bank.model.Customer;
import bank.model.InvestmentAccount;
import bank.model.SavingsAccount;
import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types; 
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public void insert(Account a) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, customer_id, account_type, "
                     + "branch, balance, company_name, company_address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getAccountNumber());
            ps.setString(2, a.getCustomer().getCustomerId());
            ps.setString(3, a.getAccountType().name());
            ps.setString(4, a.getBranch());
            ps.setDouble(5, a.getBalance());
            if (a instanceof ChequeAccount cheque) {
                ps.setString(6, cheque.getCompanyName());
                ps.setString(7, cheque.getCompanyAddress());
            } else {
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.VARCHAR);
             }
            ps.executeUpdate();
        }
    }

    public List<Account> findByCustomer(Customer customer) throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ? ORDER BY account_number";
        try (Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getCustomerId());
            try (ResultSet rs = ps.executeQuery()) {
             while (rs.next()) {
             AccountType type = AccountType.valueOf(rs.getString("account_type"));
             String branch = rs.getString("branch");
             Account a;

             switch (type) {
             case SAVINGS -> a = new SavingsAccount(customer, branch);
             case INVESTMENT -> a = new InvestmentAccount(customer, branch, 500.00);
             default -> a = new ChequeAccount(customer, branch, rs.getString("company_name"), rs.getString("company_address"));
            }
             a.restoreState(rs.getString("account_number"), rs.getDouble("balance"));
             list.add(a);
            }
        }
    }
    return list;
}

    public void delete(String accountNumber) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.executeUpdate();
        }
    }

    public void updateBalance(Account a) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, a.getBalance());
            ps.setString(2, a.getAccountNumber());
            ps.executeUpdate();
        }
    }

}