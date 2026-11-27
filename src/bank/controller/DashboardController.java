package bank.controller;

import bank.dao.BankStore;
import bank.model.Account;
import bank.model.AccountType;
import bank.model.Customer;
import bank.view.DashboardView;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    
    private final Customer customer;
    private final DashboardView view;
    private List<Account> shownAccounts = new ArrayList<>();
    private final BankStore store;

    public DashboardController(Customer customer, DashboardView view, BankStore store, Runnable onLogout) {
        this.customer = customer;
        this.view = view;
        this.store = store;
        view.setWelcome("Welcome, " + customer.getFirstName() + " " + customer.getSurname());
        view.setOnDeposit(this::handleDeposit);
        view.setOnWithdraw(this::handleWithdraw);
        view.setOnOpenAccount(this::handleOpenAccount);
        view.setOnLogout(onLogout);
        view.setOnPayInterest(this::handlePayInterest);      
        refresh();
    }

    private void refresh() {
        shownAccounts = customer.getAccounts();
        List<String> rows = new ArrayList<>();
        for (Account a : shownAccounts) {
            rows.add(String.format("%s  |  %s  |  BWP %.2f",
                a.getAccountType(), a.getAccountNumber(), a.getBalance()));
        } 
        view.setAccounts(rows);
    }

    private Account selectedAccount() {
        int i = view.getSelectedAccountIndex();
        if(i < 0 || i>= shownAccounts.size()) {
            view.showMessage("Select an account first",true);
            return null;
        }
        return shownAccounts.get(i);
    }
    private void handleDeposit() {
        Account account = selectedAccount();
        if (account == null) return;
        int index = view.getSelectedAccountIndex();
        try {
            account.deposit(Double.parseDouble(view.getAmountText()));
            store.saveMovement(account);
            refresh();
            view.selectAccount(index);
            view.clearAmount();
            view.showMessage("Deposit successful", false);
        } 
        catch (NumberFormatException e) {
            view.showMessage("Enter a valid amount", true);
        }
        catch (IllegalArgumentException | IllegalStateException e) {
                view.showMessage(e.getMessage(), true);
            }
        }

        private void handleWithdraw() {
            Account account = selectedAccount();
            if (account == null) return;
            int index = view.getSelectedAccountIndex();
            try {
                account.withdraw(Double.parseDouble(view.getAmountText()));
                store.saveMovement(account);
                refresh();
                view.selectAccount(index);
                view.clearAmount();
                view.showMessage("Withdrawal Successful", false);
            } 
        catch (NumberFormatException e) {
            view.showMessage("Enter a valid amount", true);
             }   
         catch (IllegalArgumentException | UnsupportedOperationException | IllegalStateException e) {
            view.showMessage(e.getMessage(), true);    
        }
    }
    
    private void handleOpenAccount() {
        try {
            AccountType type = AccountType.valueOf(view.getNewAccountType());
            String text = view.getOpeningDepositText();
            double deposit = text.isEmpty() ? 0 : Double.parseDouble(text);
            Account opened; 
            if (type == AccountType.CHEQUE) {
                opened = customer.openAccount(type, deposit, view.getCompanyName(), view.getCompanyAddress());
            } else {
                opened = customer.openAccount(type, deposit);
            }
            store.saveNewAccount(opened);
            refresh();
            view.clearOpenAccountFields();
            view.showMessage(type + " account opened", false);
        } 
        catch (NumberFormatException e) {
           view.showMessage("Enter a valid opening deposit", true);
        } 
        catch (IllegalArgumentException | IllegalStateException e) {
            view.showMessage(e.getMessage(), true);
        } 
    }

    private void handlePayInterest() {
        double total = 0;
        int index = view.getSelectedAccountIndex();
        try {
            for (Account a : customer.getAccounts()) {
                double interest = a.calculateInterest();
                if (interest > 0) {
                    a.deposit(interest);
                    store.saveMovement(a);
                    total += interest;
                }
            }
            refresh();
            view.selectAccount(index);
            view.showMessage(String.format("Monthly interest paid: BWP %.2f", total), false);
        } catch (IllegalStateException e) {
            view.showMessage(e.getMessage(), true);
        }
    }
}

