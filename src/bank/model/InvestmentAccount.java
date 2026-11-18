package bank.model;

public class InvestmentAccount extends Account {
    private static final double MINIMUM_INITIAL_DEPOSIT = 500.00;
    private double interestRate = 0.05; // 5% monthly

    public InvestmentAccount (Customer customer, String branch, double initialDeposit){
        if (!canOpen(initialDeposit)){
            throw new IllegalArgumentException("Initial deposit must be at least " + MINIMUM_INITIAL_DEPOSIT);
        }
        super(customer, AccountType.INVESTMENT, branch);
        deposit(initialDeposit);
    }

    public static boolean canOpen(double initialDeposit) {
        return initialDeposit >= MINIMUM_INITIAL_DEPOSIT;
    }

    @Override
    public double calculateInterest() {
        return getBalance() *interestRate;
    }

    public void applyInterest(){
        double interest = calculateInterest();
        if (interest > 0 ){
            deposit(interest);
        }
    }
}