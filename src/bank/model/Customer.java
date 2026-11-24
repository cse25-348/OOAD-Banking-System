package bank.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private static int nextId = 1;

    private String customerId;
    private String firstName;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private List <Account> accounts;
    private Bank bank;

    public Customer(String firstName, String surname, String address, String phoneNumber, String email) {
        if (firstName == null || firstName.isBlank() || surname ==null || surname.isBlank()) {
            throw new IllegalArgumentException ("Enter First name and Surname");
        }

        this.customerId = String.format("CUST%04d", nextId);
        nextId++;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accounts = new ArrayList <>();
    }
        public static Customer restore(String customerId, String firstName, String surname, String address, String phoneNumber, String email) { 
        Customer c = new Customer(firstName, surname, address, phoneNumber, email);
        c.customerId = customerId;
        int n = Integer.parseInt(customerId.substring(4));
        if (n >= nextId) {
            nextId = n + 1;
        }
        return c;
    }

    void addAccount(Account account) {
        accounts.add(account);
    }

    void setBank(Bank bank) {
        this.bank = bank;
    }

    public Account openAccount (AccountType type, double initialDeposit) {
        requireBank();
        return bank.openAccount (this, type, initialDeposit);
    }

    public Account openAccount(AccountType type, double initialDeposit, String companyName, String companyAddress) {
        requireBank();
        return bank.openAccount (this, type, initialDeposit, companyName, companyAddress);
    }

    private void requireBank() {
        if (bank == null) {
            throw new IllegalStateException("Customer is not registered with a bank");
        }
    }

    public Account getAccount(String accountNumber) {
        for (Account a : accounts){
            if (a.getAccountNumber().equals(accountNumber)) {
            return a;
        }
     }
    return null;
}

public List <Account> getAccounts() {
    return new ArrayList<>(accounts);
}

public String getCustomerId() { 
    return customerId;
}

public String getFirstName() {
    return firstName;
}

public String getSurname(){
    return surname;
}

public String getAddress() {
    return address;
}

public String getPhoneNumber() {
    return phoneNumber;
}

public String getEmail() {
    return email;
}

public void setAddress(String address){
    this.address = address;
}
}
    