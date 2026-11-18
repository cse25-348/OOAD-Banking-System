package bank.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String bankName;
    private List<String> branchList;
    private List<Customer> customers;

    public Bank (String bankName, String mainBranch) {
        this.bankName = bankName;
        this.branchList = new ArrayList<>();
        this.branchList.add(mainBranch);
        this.customers = new ArrayList<>();
    }

    public void addBranch(String branch) {
        branchList.add(branch);
    }

    public void addCustomer(Customer customer) {
        if(customer == null){
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (!customers.contains (customer)) {
            customers.add(customer);
        }
        customer.setBank(this);
    }

    public Customer findCustomer(String customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(customerId)) {
                return c;
            }
        }
        return null;
    }

    //Savings and investment
    public Account openAccount(Customer customer, AccountType accountType, double initialDeposit) {
        checkRegistered(customer);
        String branch = branchList.get(0);

        switch (accountType) {
            case SAVINGS:
                Account savings = new SavingsAccount(customer, branch);
                if (initialDeposit > 0) {
                    savings.deposit(initialDeposit);
                }
                return savings;
            case INVESTMENT:
                return new InvestmentAccount(customer, branch, initialDeposit);
            case CHEQUE:
                throw new IllegalArgumentException("A Cheque account needs the company name and address");
            default:
                throw new IllegalArgumentException("Invalid account type");
        }
    }
                //Cheque accounts(employee details needed)
public Account openAccount(Customer customer, AccountType type, double initialDeposit, String companyName, String companyAddress) {
    checkRegistered(customer);
    if (type != AccountType.CHEQUE) {
        throw new IllegalArgumentException("Employer details are only required for cheque accounts");
    }
    Account cheque = new ChequeAccount(customer,branchList.get(0), companyName, companyAddress);
    if (initialDeposit > 0){
        cheque.deposit(initialDeposit);
    }
    return cheque;
}

private void checkRegistered(Customer customer) {
    if (customer == null ||  !customers.contains(customer)) {
        throw new IllegalArgumentException("Customer is not registered with this bank");
    }
}

public String getBankName() {
    return bankName;
}

public List<String> getBranchList() {
    return new ArrayList<>(branchList);
}

public List<Customer> getCustomers() {
    return new ArrayList<>(customers);
}

}