package bank.view;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.Parent;


public class DashboardView {
    @FXML private Label welcomeLabel;
    @FXML private ListView<String> accountList;
    @FXML private TextField amountField;
    @FXML private ChoiceBox<String> typeBox;
    @FXML private TextField openingDepositField;
    @FXML private TextField companyNameField;
    @FXML private TextField companyAddressField;
    @FXML private Label messageLabel;

    private Parent root;
    private Runnable onDeposit;
    private Runnable onWithdraw;
    private Runnable onOpenAccount;
    private Runnable onLogout;

    public static DashboardView load() throws IOException {
        FXMLLoader loader = new FXMLLoader (DashboardView.class.getResource("DashboardView.fxml"));
        Parent root = loader.load();
        DashboardView view = loader.getController();
        view.root = root;
        return view;
    }

    @FXML
    private void initialize() {
        typeBox.getItems().addAll("SAVINGS","INVESTMENT", "CHEQUE");    
        typeBox.setValue("SAVINGS");
    }
    @FXML private void onDepositClicked() { 
        if (onDeposit != null) onDeposit.run();
     }

    @FXML private void onWithdrawClicked() {
         if (onWithdraw != null) onWithdraw.run();
         }

    @FXML private void onOpenClicked() { 
        if (onOpenAccount != null) onOpenAccount.run(); 
    }

    @FXML private void onLogoutClicked() { 
        if (onLogout != null) onLogout.run(); 
    }

    public Parent getRoot() {
        return root; 
    }
    public void setWelcome(String text) {
        welcomeLabel.setText(text);
    }

    public void setAccounts(List<String> rows) {
        accountList.getItems().setAll(rows);
    }

    public void clearOpenAccountFields() {
        openingDepositField.clear();
        companyNameField.clear();
        companyAddressField.clear();
        }

    public int getSelectedAccountIndex() {
        return accountList.getSelectionModel().getSelectedIndex();
    }

    public void selectAccount(int index) {
        if (index >= 0) accountList.getSelectionModel().select(index);
    }

    public String getAmountText() {
        return amountField.getText().trim();
    }

    public void clearAmount() {
        amountField.clear();
    }

    public String getNewAccountType() {
        return typeBox.getValue();
    }

    public String getOpeningDepositText() {
        return openingDepositField.getText().trim();
    }

    public String getCompanyName() {
        return companyNameField.getText().trim();
    }

    public String getCompanyAddress() {
        return companyAddressField.getText().trim();
    }

    public void showMessage(String message, boolean error) {
        messageLabel.setStyle(error ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        messageLabel.setText(message);
    }

    public void setOnDeposit(Runnable action) {
        this.onDeposit = action;
    }

    public void setOnWithdraw(Runnable action) {
         this.onWithdraw = action;
    }

        public void setOnOpenAccount(Runnable action) {
        this.onOpenAccount = action;
    }

    public void setOnLogout(Runnable action) {
         this.onLogout = action;
    }

}

