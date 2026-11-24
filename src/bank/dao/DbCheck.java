package bank.dao;

import bank.model.Account;
import bank.model.AccountType;
import bank.model.Bank;
import bank.model.Customer;
import java.util.List;

public class DbCheck {
    public static void main(String[] args) throws Exception {
        Database.createTables();
        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();

        Bank bank = new Bank("Test Bank", "Main");
        Customer c = new Customer("Test", "User", "Plot 1, Gaborone", "70000000", "test@email.com");
        bank.addCustomer(c);
        Account savings = c.openAccount(AccountType.SAVINGS, 500);
        Account investment = c.openAccount(AccountType.INVESTMENT, 1000);
        savings.deposit(250);

        accountDAO.delete(savings.getAccountNumber());
        accountDAO.delete(investment.getAccountNumber());
        customerDAO.delete(c.getCustomerId());

        customerDAO.insert(c);
        accountDAO.insert(savings);
        accountDAO.insert(investment);
        System.out.println("Saved customer " + c.getCustomerId() + " with 2 accounts");

        Customer loaded = customerDAO.findById(c.getCustomerId());
        List<Account> accounts = accountDAO.findByCustomer(loaded);
        System.out.println("Loaded " + loaded.getCustomerId() + ": " + accounts.size() + " accounts");
        for (Account a : accounts) {
            System.out.println(String.format("%s %s BWP %.2f",
                    a.getAccountType(), a.getAccountNumber(), a.getBalance()));
        }
        
        for (Account a : accounts) {
            accountDAO.delete(a.getAccountNumber());
        }
        customerDAO.delete(loaded.getCustomerId());
        System.out.println("Cleaned up");
    }
}