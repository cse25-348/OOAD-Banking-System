package bank.controller;

import bank.dao.BankStore;
import bank.model.Bank;
import bank.model.Customer;
import bank.view.RegisterView;

public class RegisterController {
    
    private final Bank bank;
    private final RegisterView view;
    private final BankStore store;

    public RegisterController(Bank bank, RegisterView view, BankStore store, Runnable onBack) {
        this.bank = bank;
        this.view = view;
        this.store = store;
        view.setOnRegister(this::handleRegister);
        view.setOnBack(onBack);
    }

    private void handleRegister() {
        if (view.getAddress().isEmpty()) {
            view.showMessage("Enter an address", true);
            return;
        }
        try {
         Customer c = new Customer(view.getFirstName(), view.getSurname(), view.getAddress(), view.getPhoneNumber(), view.getEmail());
            bank.addCustomer(c);
             store.saveCustomer(c);
            view.clearFields();
            view.showMessage("Registered. Your customer ID is " + c.getCustomerId(), false);
        } catch (IllegalArgumentException | IllegalStateException e) {
            view.showMessage(e.getMessage(), true);
        }
    }

}
