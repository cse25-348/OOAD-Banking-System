package bank.dao;

import bank.model.Account;
import bank.model.Bank;
import bank.model.Customer;
import bank.model.Transaction;
import java.sql.SQLException;
import java.util.List;

public class BankStore {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public void init() {
        try {
            Database.createTables();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not set up the database" + e.getMessage());
        }
    }

    public void loadInto(Bank bank) {
        try {
            for (Customer c : customerDAO.findAll()){
                bank.addCustomer(c);
                accountDAO.findByCustomer(c);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not load data: " + e.getMessage());
        }
    }

    public void saveCustomer(Customer c) {
        try {
            customerDAO.insert(c);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not save customer: " + e.getMessage());
        }
    }

    public void saveMovement(Account a) {
        try {
            accountDAO.updateBalance(a);
            List<Transaction> list = a.getTransactions();
            transactionDAO.insert(list.get(list.size() - 1));
        } catch (SQLException e) {
            throw new IllegalStateException("Could not save transaction: "+ e.getMessage());
        }
    }

        public void saveNewAccount(Account a) {
        try {
            accountDAO.insert(a);
            for (Transaction t : a.getTransactions()) {
                transactionDAO.insert(t);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not save account: " + e.getMessage());
        }
    }

}
