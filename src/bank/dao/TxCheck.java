package bank.dao;

import bank.model.Account;
import bank.model.AccountType;
import bank.model.Account;
import bank.model.Bank;
import bank.model.Customer;
import bank.model.Transaction;

public class TxCheck {
    public static void main (String[] args) throws Exception {
        Database.createTables();
        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO =  new AccountDAO();
        TransactionDAO txDAO = new TransactionDAO();

        Bank bank = new Bank("Test Bank", "Main");
        Customer c = new Customer("Test", "User", "Plot 1, Gaborone", "70000000", "test@email.com");
        bank.addCustomer(c);
        Account a = c.openAccount(AccountType.INVESTMENT, 1000);
        a.deposit(200);
        a.withdraw(50);

        txDAO.deleteByAccount(a.getAccountNumber());
        accountDAO.delete(a.getAccountNumber());
        customerDAO.delete(c.getCustomerId());

        customerDAO.insert(c);
        accountDAO.insert(a);
        for (Transaction t : a.getTransactions()) {
            txDAO.insert(t);
        }
        System.out.println("Saved" + a.getTransactions().size() + " transactions");
        
        for (Transaction t : txDAO.findByAccount(a.getAccountNumber())) {
            System.out.println(String.format("%s %.2f (balance %.2f)", t.getType(), t.getAmount(), t.getBalanceAfter()));
        }

        txDAO.deleteByAccount(a.getAccountNumber());
        accountDAO.delete(a.getAccountNumber());
        customerDAO.delete(c.getCustomerId());
        System.out.println("Cleaned up");
    }
}
