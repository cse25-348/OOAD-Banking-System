package bank.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account implements AccountOperations {
    
  private static int nextNumber = 1000100;

  private String accountNumber;
  private double balance;
  private String branch;
  private AccountType accountType;
  private Customer customer;
  private List<Transaction> transactions = new ArrayList<>();

  public Account (Customer customer, AccountType accountType, String branch) {
    if (customer ==null){
        throw new IllegalArgumentException("An account cannot exist without a customer");
    }
    
    this.accountNumber = String.valueOf(nextNumber);
    nextNumber++;
    this.customer = customer;
    this.accountType = accountType;
    this.branch = branch;
    this.balance = 0;
    this.balance = 0;
    customer.addAccount(this);
  }

  public List<Transaction> getTransactions() {
    return new ArrayList<>(transactions);
  }

    public void restoreState(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions.clear();
        int n = Integer.parseInt(accountNumber);
        if (n >= nextNumber) {
            nextNumber = n + 1;
        }
    }

  @Override
  public void deposit (double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Deposit amount must be greater than zero");
    }
    balance = balance + amount;

    transactions.add(new Transaction(accountNumber, "DEPOSIT", amount, balance));
  }

@Override
public void withdraw(double amount) {
    if (amount <=0) {
        throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
    }
    balance = balance - amount;
    transactions.add(new Transaction(accountNumber, "WITHDRAWAL", amount, balance));
}

@Override
public double getBalance() {
    return balance;
}

public void setBranch(String branch) {
    this.branch = branch;
}

public String getAccountNumber() {
    return accountNumber;
}

public String getBranch() {
    return branch;
}

public AccountType getAccountType() {
    return accountType;
}

public Customer getCustomer() {
    return customer;
}
}