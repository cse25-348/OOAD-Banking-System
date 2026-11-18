package bank.model;

public class ChequeAccount extends Account {

    private String companyName;
    private String companyAddress;
    private boolean isEmployed;

    public ChequeAccount(Customer customer, String branch, String companyName, String companyAddress) {
        if (companyName == null || companyName.isBlank() || companyAddress == null || companyAddress.isBlank()){
            throw new IllegalArgumentException("A cheque account can only be opened by an employed customer" + "Company name and address must be provided");
        }
        super(customer, AccountType.CHEQUE, branch);
        setEmploymentDetails(companyName, companyAddress);
    }

    public void setEmploymentDetails(String name, String address) {
        this.companyName = name;
        this.companyAddress = address;
        this.isEmployed = true;
    }

    public boolean isEligible() {
        return isEmployed;
    }

    @Override
    public double calculateInterest() {
        return 0; // cheque account has no inerest
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }
}