package bank;
import bank.model.*;

public class Main {
    public static void main (String [] args) {

        //Create our bank
        Bank bank = new Bank ("First National Bank", "Gaborone");

        //Create our customer
        Customer thabo = new Customer ("Thabo", "Molefe", "Plot 123, Gaborone", "71234567", "thabo@email.com");
        bank.addCustomer(thabo);
    

    // Open one of each account type
    Account savings = thabo.openAccount(AccountType.SAVINGS, 2000);
    Account investment = thabo.openAccount(AccountType.INVESTMENT, 1000);
    Account cheque = thabo.openAccount(AccountType.CHEQUE, 0, "MASCOM", "Plot 500, Gaborone");

    // Deposits and withdrawals
    cheque.deposit(8000);
    cheque.withdraw(500);
    investment.withdraw(200);

    //Interest
    ((SavingsAccount) savings).applyInterest();
    ((InvestmentAccount) investment). applyInterest();

    //Show everything
    for (Account a : thabo.getAccounts()) {
        System.out.println(a.getAccountType() + " " + a.getAccountNumber() + " : BWP" + String.format("%.2f", a.getBalance()));
    }
    // Rules that must be refused 
    try {
        savings.withdraw(100);
    } 
         catch(UnsupportedOperationException e) {
            System.out.println("Savings withdrawal blocked: " + e.getMessage());
        }
  try {
    thabo.openAccount(AccountType.INVESTMENT, 300);
  }  
    catch(IllegalArgumentException e) {
            System.out.println("Investment refused: " + e.getMessage());
        }
    }
}
