package main.java.ui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import main.java.controller.EmployeeController;
import main.java.model.Employee;
import main.java.util.Validator;

public class AddEmployeeDialog {
    EmployeeController employeeController;

    public AddEmployeeDialog(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    protected void showAddEmployeeDialog(){
        Dialog<Employee<Integer>> dialog = new Dialog<>();
        dialog.setTitle("Add Employee");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField name = new TextField();
        ComboBox<String> department = new ComboBox<>();
        department.getItems().addAll("IT", "HR", "Finance","Wed");
        TextField salary = new TextField();
        TextField rating = new TextField();
        TextField experience = new TextField();

        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Department:"), 0, 1);
        grid.add(department, 1, 1);
        grid.add(new Label("Salary:"), 0, 2);    
        grid.add(salary, 1, 2);
        grid.add(new Label("Performance Rating:"), 0, 3);
        grid.add(rating, 1, 3);
        grid.add(new Label("Experience:"), 0, 4);
        grid.add(experience, 1, 4);

        
        ButtonType okButtonType = ButtonType.OK;
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            // Validation
            if (!Validator.isEmployeeFormValid(name, department, salary, rating, experience)) {
                event.consume(); // Prevent dialog from closing if invalid
            }
        });
        

        dialog.setResultConverter(btn -> {
            if(btn == ButtonType.OK){
                 employeeController.createEmployee(
                    EmployeeTable.employeeList.size() + 1,
                    name.getText(),
                    department.getValue(),
                    Double.parseDouble(salary.getText()),
                    Double.parseDouble(rating.getText()),
                    Integer.parseInt(experience.getText()),
                    true);
            }
            return null;
        });

        dialog.showAndWait();

        
        
    }

}
