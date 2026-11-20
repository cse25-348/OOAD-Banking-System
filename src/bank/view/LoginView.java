package bank.view;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginView {
    @FXML private TextField idField;
    @FXML private Label messageLabel;

    private Parent root;
    private Runnable onLogin;

    public static LoginView load() throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("LoginView.fxml"));
        Parent root = loader.load();
        LoginView View = loader.getController();
        View.root = root;
        return View;
    }

    @FXML
    private void onLoginClicked() {
        if (onLogin != null) onLogin.run();
    }

    public Parent getRoot() {
        return root;
    }

    public String getCustomerId() {
        return idField.getText().trim();
    }

    public void setOnLogin(Runnable action) {
        this.onLogin = action;
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }
}