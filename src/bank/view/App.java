package bank.view;

import bank.controller.LoginController;
import bank.model.Bank;
import bank.model.Customer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Bank bank = new Bank ("First National Bank", "Gaborone Main");
        bank.addCustomer(new Customer("Thabo", "Molefe", "Plot 123, Gaborone", "71234567", "thabo@email.com")); //test cust: CUST0001

        LoginView loginView = LoginView.load();
        Scene scene = new Scene (loginView.getRoot(), 400, 300);

        new LoginController(bank, loginView, customer -> {
            Label welcome = new Label ("Welcome, " + customer.getFirstName());
            scene.setRoot(new StackPane(welcome));
        });

        stage.setTitle("Banking System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}