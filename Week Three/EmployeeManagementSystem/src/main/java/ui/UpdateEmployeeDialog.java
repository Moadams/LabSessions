package main.java.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.model.Employee;
import main.java.util.Validator;

public class UpdateEmployeeDialog {

    public void showUpdateDialog(Employee employee) {
        Stage window = new Stage();
        window.setTitle("Update Employee");

        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setStyle("-fx-padding: 20;");

        TextField nameField = new TextField(employee.getName());
        ComboBox<String> departmentBox = new ComboBox<>();
        departmentBox.getItems().addAll("HR", "IT", "Finance", "Marketing");
        departmentBox.setValue(employee.getDepartment());

        TextField salaryField = new TextField(String.valueOf(employee.getSalary()));
        TextField ratingField = new TextField(String.valueOf(employee.getPerformanceRating()));
        TextField experienceField = new TextField(String.valueOf(employee.getYearsOfExperience()));

        Button updateBtn = new Button("Update");

        layout.addRow(0, new Label("Name:"), nameField);
        layout.addRow(1, new Label("Department:"), departmentBox);
        layout.addRow(2, new Label("Salary:"), salaryField);
        layout.addRow(3, new Label("Rating:"), ratingField);
        layout.addRow(4, new Label("Experience:"), experienceField);
        layout.addRow(5, updateBtn);

        updateBtn.setOnAction(e -> {
            if (Validator.isEmployeeFormValid(nameField, departmentBox, salaryField, ratingField, experienceField)) {
                employee.setName(nameField.getText());
                employee.setDepartment(departmentBox.getValue());
                employee.setSalary(Double.parseDouble(salaryField.getText()));
                employee.setPerformanceRating(Double.parseDouble(ratingField.getText()));
                employee.setYearsOfExperience(Integer.parseInt(experienceField.getText()));
                EmployeeTable.employeeTable.refresh(); // Refresh the UI table
                window.close();
            }
        });

        Scene scene = new Scene(layout, 400, 300);
        window.setScene(scene);
        window.showAndWait();
    }
}
