package bank.model;

public class SavingsAccount extends Account {

    private double interestRate = 0.0005; // 0.05%

    public SavingsAccount(Customer customer, String branch){
        super(customer, AccountType.SAVINGS, branch);
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals from savings accounts are not allowed");
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate;
    }

    public void applyInterest() {
        double interest = calculateInterest();
        if (interest >0){
            deposit(interest);
        }
    }
}