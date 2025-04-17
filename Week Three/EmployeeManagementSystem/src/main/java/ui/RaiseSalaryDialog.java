package main.java.ui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import main.java.controller.EmployeeController;
import main.java.model.Employee;
import main.java.util.Validator;

public class RaiseSalaryDialog {
    EmployeeController employeeController;
    
    public RaiseSalaryDialog(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public void raiseSalary(){
        Dialog<Employee<Integer>> dialog = new Dialog<>();
        dialog.setTitle("Raise Salary");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));


        TextField amount = new TextField();
        

        grid.addRow(0, new Label("Enter Raise Amount:"));
        grid.addRow(1,amount);

        grid.addRow(2,new Label("This will raise the salary of employees with a rating greater than or equal to 4.5"));

        ButtonType okButtonType = ButtonType.OK;
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.disableProperty().bind(amount.textProperty().isEmpty());

        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            if(!Validator.isValidRaiseAmount(amount)) event.consume();
        });

        dialog.setResultConverter(btn -> {
            if (btn == okButtonType) {
                double amountValue = Double.parseDouble(amount.getText());
                employeeController.raiseSalaryOfEmployees(amountValue);
                return null;
            }
            return null;
            
    });
        dialog.showAndWait();
    }
}
