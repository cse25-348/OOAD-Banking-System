package bank.view;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterView {
    
    @FXML private TextField firstNameField;
    @FXML private TextField surnameField;
    @FXML private TextField addressField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private Label message;
    @FXML private Label messageLabel;

    private Parent root;
    private Runnable onRegister;
    private Runnable onBack;

    public static RegisterView load() throws IOException {
        FXMLLoader loader = new FXMLLoader(RegisterView.class.getResource("RegisterView.fxml"));
        Parent root = loader.load();
        RegisterView view = loader.getController();
        view.root = root;
        return view;
    }

    public Parent getRoot() {
        return root; 
     }


    @FXML private void onRegisterClicked() {
        if (onRegister != null) onRegister.run();
    }
    @FXML private void onBackClicked() {
        if (onBack != null) onBack.run();
    }

    public void setOnRegister(Runnable action) { 
        this.onRegister = action; 
    }


    public String getFirstName() {
        return firstNameField.getText().trim();
    }

    public String getSurname() {
        return surnameField.getText().trim();
    }

     public String getAddress() {
        return addressField.getText().trim();
    }

     public String getPhoneNumber() {
        return phoneNumberField.getText().trim();
    }

     public String getEmail() {
        return emailField.getText().trim();
    }

    public void clearFields() {
        firstNameField.clear();
        surnameField.clear();
        addressField.clear();
        phoneNumberField.clear();
        emailField.clear();
    }

    public void showMessage(String message, boolean error) {
        messageLabel.setStyle(error ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        messageLabel.setText(message);
    }

    public void setOnBack(Runnable action) { 
        this.onBack = action;
    }
}
