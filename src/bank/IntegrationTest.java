package bank;

import bank.dao.AccountDAO;
import bank.dao.BankStore;
import bank.dao.CustomerDAO;
import bank.dao.TransactionDAO;
import bank.model.Account;
import bank.model.AccountType;
import bank.model.Bank;
import bank.model.Customer;

public class IntegrationTest {
    
    private static int passed = 0;
    private static int failed = 0;
    
    private static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS " : "FAIL ") + name);
        if (ok) passed++; else failed++;
    }

    public static void main(String[] args) throws Exception {
        BankStore store = new BankStore();
        store.init();

        Bank bank = new Bank("Test Bank", "Main");
        store.loadInto(bank);

        Customer c = new Customer("Integration", "Tester", "Plot 9, Gaborone", "70000001", "it@email.com");
        bank.addCustomer(c);
        store.saveCustomer(c);
        Account acc = c.openAccount(AccountType.INVESTMENT, 1000);
        store.saveNewAccount(acc);
        check("Customer registered and account opened",
            bank.findCustomer(c.getCustomerId()) !=null && c.getAccounts().size() == 1);

            check("Login finds the customer by ID", bank.findCustomer(c.getCustomerId()) ==c);

            acc.deposit(200);
            store.saveMovement(acc);
            acc.withdraw(50);
            store.saveMovement(acc);
            check("Balance is 1150 after deposit and withdrawl",
                Math.abs(acc.getBalance() - 1150) < 0.001);
            
            boolean refused = false;
            try {
                c.openAccount(AccountType.INVESTMENT, 300);
            } catch (IllegalArgumentException e) {
                refused = true;
            }
            check("Investment account under BWP500 is refused", refused);

            Bank bank2 = new Bank("Test Bank", "Main");
            store.loadInto(bank2);
            Customer reloaded = bank2.findCustomer(c.getCustomerId());
            check("Customer survives a restart (loaded from the database)", reloaded != null);
            Account reAcc = reloaded == null ? null : reloaded.getAccount(acc.getAccountNumber());
            check("Account survives a restart", reAcc != null);
            check("Saved balance is 1150", reAcc != null && Math.abs(reAcc.getBalance() - 1150) < 0.001);
            check("3 transactions are stored",
                new TransactionDAO().findByAccount(acc.getAccountNumber()).size() == 3);
               
            new TransactionDAO().deleteByAccount(acc.getAccountNumber());
            new AccountDAO().delete(acc.getAccountNumber());
            new CustomerDAO().delete(c.getCustomerId());

            System.out.println();
            System.out.println("Passed: " + passed + " Failed: " + failed);
    }

}
