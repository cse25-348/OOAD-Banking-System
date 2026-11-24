package bank.model;

import java.time.LocalDateTime;

public class Transaction {

    private final String accountNumber;
    private final String type;
    private final double amount;
    private final double balanceAfter;
    private final LocalDateTime createdAt;

    public Transaction(String accountNumber, String type, double amount, double balanceAfter) {
        this(accountNumber, type, amount, balanceAfter, LocalDateTime.now());
    }

    public Transaction(String accountNumber, String type, double amount, double balanceAfter, LocalDateTime createdAt) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }

    public String getAccountNumber() {
        return accountNumber; 
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
}
