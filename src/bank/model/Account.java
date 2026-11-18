package bank.model;

public abstract class Account implements AccountOperations {
    
  private static int nextNumber = 1000100;

  private String accountNumber;
  private double balance;
  private String branch;
  private AccountType accountType;
  private Customer customer;

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
  }

  @Override
  public void deposit (double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Deposit amount must be greater than zero");
    }
    balance = balance + amount;
  }

@Override
public void withdraw(double amount) {
    if (amount <=0) {
        throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
    }
    balance = balance - amount;
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