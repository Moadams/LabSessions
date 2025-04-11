import java.time.LocalDate;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BankUI {
    private BankAccount bankAccount;
    private Label customerNameLabel, accountNumberLabel, accountTypeLabel, amountLabel, transactionsLabel, balanceLabel;
    private TextField customerNameTextField, accountNumberTextField, amountField, transactionsTextField;
    private ComboBox<String> accountTypeComboBox;
    private TextArea transactionHistoryArea;

    public void startUI(Stage initialStage){
        initialStage.setTitle("Bank Account Management App"); // set the title of the application

        VBox layout = new VBox(5);
        layout.setPadding(new Insets(20));

        setupUI(layout);

        Scene scene = new Scene(layout, 400,600);
        initialStage.setScene(scene);
        initialStage.show(); // show user interface
    }

    public void setupUI(VBox layout){
        // USER INTERFACE
        customerNameLabel = new Label("Customer Name");
        customerNameTextField = new TextField();

        accountNumberLabel = new Label("Account Number");
        accountNumberTextField = new TextField();

        accountTypeLabel = new Label("Account Type");
        accountTypeComboBox = new ComboBox<>();
        accountTypeComboBox.getItems().addAll("Savings", "Current", "Fixed Deposit");

        balanceLabel = new Label();

        Button createAccountButton = new Button("Create Account");

        // TRANSACTION UI
        amountLabel = new Label("Enter amount:");
        amountField = new TextField();

        transactionsLabel = new Label("Enter the number of transactions you want to view");
        transactionsTextField = new TextField();


        transactionHistoryArea = new TextArea("Transaction History");
        transactionHistoryArea.setEditable(false);

                // Transaction Buttons
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button transactionsButton = new Button("Get Transactions");
        Button calculateIntrestButton  = new Button("Calculate Interest");

        HBox transactionButtons = new HBox(10);
        transactionButtons.getChildren().addAll(depositButton, withdrawButton);        


        layout.getChildren().addAll(
            customerNameLabel, customerNameTextField, accountNumberLabel, accountNumberTextField, accountTypeLabel, accountTypeComboBox, createAccountButton, amountLabel, amountField, transactionButtons, calculateIntrestButton, transactionsLabel, transactionsTextField,  transactionsButton, balanceLabel,  transactionHistoryArea
        );

        //Button actions
        createAccountButton.setOnAction(e->createBankAccount());
        depositButton.setOnAction(e -> deposit());
        withdrawButton.setOnAction(e -> withdraw());
        transactionsButton.setOnAction(e->getTransactions());
        calculateIntrestButton.setOnAction(e->calculateInterest());

    }

    private void createBankAccount(){
        
        String customerName = customerNameTextField.getText();
        String accountNumber = accountNumberTextField.getText();
        String accountType = accountTypeComboBox.getValue();
        double initialDeposit;

        if(customerName.isEmpty() || accountNumber.isEmpty() || accountType == null){
            displayError("Please fill in all the fields");
            return;
        }

        if (!validateAccountNumber(accountNumber)){
            displayError("Account number should only contain numbers");
            return;
        };

        if(amountField.getText().isEmpty()){
            initialDeposit = 0.0;
        }else{
            try {
                initialDeposit = Double.parseDouble(amountField.getText());
            } catch (NumberFormatException e) {
                
                displayError("Invalid deposit amount");
                return;
            }
        }

        if(accountType.equals("Savings")){
            bankAccount = new SavingsAccount(customerName, initialDeposit, accountNumber);
        }else if(accountType.equals("Current")){
            bankAccount = new CurrentAccount(customerName, accountNumber, initialDeposit);
        }else if(accountType.equals("Fixed Deposit")){
            LocalDate maturityDate = LocalDate.now().plusMonths(12); // add 12 months to today's date and set it as the maturity date
            bankAccount = new FixedDeposit(customerName, accountNumber, initialDeposit, maturityDate);
        }
        balanceLabel.setText("Account Balance: Ghs" + bankAccount.getAccountBalance());
        transactionHistoryArea.setText("Transaction History:");
        displayInfo(accountType + " account with account number " + accountNumber + " created successfully");
    }

    private double getAmountFromField(){
        double amount = 0.0;
        try{
            amount = Double.parseDouble(amountField.getText());
            if(amount<=0){
                displayError("Please enter a number bigger than 0");
                return 0.0;
            }
            return amount;
        }catch(NumberFormatException e){
            displayError("Please enter a valid amount");
            return 0.0;
        }
    }

    private void deposit(){
        // first check if the bank account is created
        if(bankAccount == null){
            displayError("Please create a bank account first");
            return ;
        }

        double amount = getAmountFromField();
        if(amount <= 0) return; // dont perform the deposit if the amount is 0

        String depositResponse = bankAccount.deposit(amount);
        amountField.setText("");
        balanceLabel.setText("Account Balance: Ghs" + bankAccount.getAccountBalance());
        displayInfo(depositResponse);
    }

    private void withdraw(){
        if(bankAccount == null){
            displayError("Please create a bank account first");
            return ;
        }

        double amount = getAmountFromField();

        if (amount <= 0) return; // don't perform withdrawal if the amount is 0

        try{
            amount = Double.parseDouble(amountField.getText());
        }catch(NumberFormatException e){
            displayError("Invalid withdrawal amount");
            return;
        }
        String withdrawalResponse = bankAccount.withdraw(amount);
        amountField.setText("");
        balanceLabel.setText("Account Balance: Ghs" + bankAccount.getAccountBalance());
        displayInfo(withdrawalResponse);
    }

    private void getTransactions(){
        if(bankAccount == null){
            displayError("Please create a bank account first");
            return;
        }
        int numberOfTransactions;
        List<Transaction> transactionHistory;

        if(transactionsTextField.getText().isEmpty()){
            transactionHistory = bankAccount.getTransactionList();
        }else{
            try{
                numberOfTransactions = Integer.parseInt(transactionsTextField.getText());
                if(numberOfTransactions <= 0){
                    displayError("Please enter number greater than 0");
                    return;
                }
            }catch(NumberFormatException e){
                displayError("Invalid number of transactions to display");
                return;
            }
            transactionHistory =  bankAccount.getTransactionList(numberOfTransactions);
        }

        String historyText = "Transaction History:\n";

        for(Transaction transaction : transactionHistory){
            historyText += transaction.transactionToString() + "\n";
        }

        transactionHistoryArea.setText(historyText.toString());
    }

    private void calculateInterest(){
        if(bankAccount == null){
            displayError("Please create a bank account first");
            return;
        }
        displayInfo(bankAccount.displayInterest());

    }

    public boolean validateAccountNumber(String accountNumber) {
        // Check if the account number is not empty and matches the pattern
        return accountNumber != null && accountNumber.matches("^[0-9]+$");
    }

    private void displayError(String message){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void displayInfo(String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
