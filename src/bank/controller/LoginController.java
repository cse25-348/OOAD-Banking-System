package bank.controller;

import bank.model.Bank;
import bank.model.Customer;
import bank.view.LoginView;
import java.util.function.Consumer;

public class LoginController {
    
    private final Bank bank;
    private final LoginView view;
    private final Consumer <Customer> onSuccess;

    public LoginController(Bank bank, LoginView view, Consumer<Customer> onSuccess) {
        this.bank = bank;
        this.view = view;
        this.onSuccess = onSuccess;
        view.setOnLogin(this::handleLogin);
    }

private void handleLogin() {
    String id = view.getCustomerId();
    if (id.isEmpty()) {
        view.showMessage("Please enter your customer ID");
        return;
    }
    Customer customer = bank.findCustomer(id);
    if (customer == null) {
        view.showMessage("Customer not found");
    } else {
        onSuccess.accept(customer);
    }
}
}