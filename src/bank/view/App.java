package bank.view;

import bank.controller.LoginController;
import bank.model.AccountType;
import bank.model.Bank;
import bank.model.Customer;
import bank.controller.DashboardController;
import bank.dao.BankStore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class App extends Application {

    private Bank bank;
    private Stage stage;
    private final BankStore store = new BankStore();

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        store.init();
        bank = new Bank("First National Bank", "Gaborone Main");
        store.loadInto(bank);

        if(bank.getCustomers().isEmpty()) {
            Customer thabo = new Customer("Thabo", "Molefe", "Plot 123, Gaborone", "71234567", "thabo@email.com");
            bank.addCustomer(thabo);
            store.saveCustomer(thabo);
            store.saveNewAccount(thabo.openAccount(AccountType.SAVINGS, 2000));
            store.saveNewAccount(thabo.openAccount(AccountType.INVESTMENT, 1000));
        }

        stage.setTitle("Banking System");
        showLogin();
        stage.show();
    }

    private void showLogin() throws Exception {
        LoginView loginView = LoginView.load();
        new LoginController(bank, loginView, this::showDashboard);
        stage.setScene(new Scene(loginView.getRoot(), 400, 300));
        stage.sizeToScene();
    }

        private void showDashboard(Customer customer) {
        try {
            DashboardView dashboardView = DashboardView.load();
            new DashboardController(customer, dashboardView, store, () -> {
                try {
                    showLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            stage.setScene(new Scene(dashboardView.getRoot(), 800, 560));
            stage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
    
}