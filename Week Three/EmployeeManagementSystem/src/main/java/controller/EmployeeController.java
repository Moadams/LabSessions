package main.java.controller;

import java.util.Comparator;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import main.java.database.EmployeeDatabase;
import main.java.exception.EmployeeNotFoundException;
import main.java.exception.InvalidDepartmentException;
import main.java.exception.InvalidSalaryException;
import main.java.model.Employee;
import main.java.ui.AlertUi;
import main.java.ui.EmployeeTable;
import main.java.util.Validator;

public class EmployeeController {
    EmployeeDatabase<Integer> employeeDatabase;
    public EmployeeTable employeeTable;
    AlertUi alertUi = new AlertUi();

    public EmployeeController(EmployeeDatabase<Integer> employeeDatabase, EmployeeTable employeeTable) {
        //TODO Auto-generated constructor stub
        this.employeeDatabase = employeeDatabase;
        this.employeeTable = employeeTable;
    }

    public void createEmployee(Integer employeeId, String name, String department, double salary, double rating, int experience, boolean isActive) {
        try{
            Employee<Integer> createdEmployee = new Employee<>(employeeId, name, department, salary, rating, experience, isActive);
            employeeDatabase.addEmployee(createdEmployee);
            employeeTable.updateTable();
        }catch(IllegalArgumentException e){
            alertUi.displayError(e.getMessage());
        }catch(InvalidSalaryException e){
            alertUi.displayError(e.getMessage());
        }catch(InvalidDepartmentException e){
            alertUi.displayError(e.getMessage());
        }
    }

    public void removeSelectedEmployee(){
        Employee<Integer> selectedEmployee = EmployeeTable.employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeDatabase.removeEmployee(selectedEmployee.getEmployeeId());
            employeeTable.updateTable();
        }
    }

    public void raiseSalaryOfEmployees(double raiseAmount){
        employeeDatabase.raiseSalaryOfEmployees(raiseAmount);
        employeeTable.updateTable();
    }

    public void getTopPaidEmployees(){
        List<Employee<Integer>> topPaidEmployees = employeeDatabase.getHighPaidEmployees();
        employeeTable.employeeList.setAll(topPaidEmployees); 
    }

    public void displayTotalSalary(){
        double totalSalary = employeeDatabase.calculateTotalSalary();
        alertUi.displayInfo("Total Salary: " + totalSalary);
    }

    public void displayAverageSalary(){
        double averageSalary = employeeDatabase.calculateAverageSalary();
        alertUi.displayInfo("Average Salary: " + averageSalary);
    }

    public void displayAverageSalaryInDepartment(){
        Dialog<Employee<Integer>> dialog = new Dialog<>();
        dialog.setTitle("Department Average Salary");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));


        ComboBox<String> departmentBox = new ComboBox<>();
        departmentBox.getItems().addAll("IT", "HR", "Finance");
        

        grid.addRow(0, new Label("Select the department:"));
        grid.addRow(1,departmentBox);


        TextField averageText = new TextField();

        ButtonType okButtonType = ButtonType.OK;
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true); // disable by default

        // Enable/disable dynamically when the selection changes
        departmentBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            okButton.setDisable(newVal == null);
        });

        
        dialog.setResultConverter(btn -> {
            if (btn == okButtonType) {
                double average = employeeDatabase.calculateAverageSalaryInDepartment(departmentBox.getValue());
                averageText.setText(String.valueOf(average));
                alertUi.displayInfo("Average Salary: " + averageText.getText());
                return null;
            }
            return null;
            
    });
        dialog.showAndWait();
    }
    
    public void compareBySalary() {
        Comparator<Employee<Integer>> salaryComparator = Comparator.comparing(Employee::getSalary);
        List<Employee<Integer>> sortedEmployees = employeeDatabase.sortEmployeesByCustomField(salaryComparator);
        employeeTable.setTableData(sortedEmployees);
    }

    public void compareByRating(){
        Comparator<Employee<Integer>> ratingComparator = Comparator.comparingDouble(Employee::getPerformanceRating);
        List<Employee<Integer>> sortedEmployees = employeeDatabase.sortEmployeesByCustomField(ratingComparator);
        employeeTable.setTableData(sortedEmployees);
    }

    public void compareByExperience(){ 
        // Sorting employees by experience using Comparable (since compareTo is overridden in Employee)
        List<Employee<Integer>> sortedEmployeesByExperience = employeeDatabase.getAllEmployees();
        // Sort the list by experience (ascending order by default)
        sortedEmployeesByExperience.sort(null); 
        employeeTable.setTableData(sortedEmployeesByExperience);
    }

    public void displayEmployeeById(){
        Dialog<Employee<Integer>> dialog = new Dialog<>();
        dialog.setTitle("Employee Details");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField employeeIdField = new TextField();

        grid.addRow(0, new Label("Enter Employee ID:"));
        grid.addRow(1, employeeIdField);

        ButtonType okButtonType = ButtonType.OK;
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.disableProperty().bind(employeeIdField.textProperty().isEmpty());

        dialog.setResultConverter(btn -> {
            if (btn == okButtonType) {
                int employeeId = Integer.parseInt(employeeIdField.getText());
                try{
                    Employee<Integer> employee = employeeDatabase.getEmployeeById(employeeId);
                    alertUi.displayInfo(
                        "Name: " + employee.getName() + "\n" +
                        "Department: " + employee.getDepartment() + "\n" +
                        "Salary: " + employee.getSalary() + "\n" +
                        "Performance Rating: " + employee.getPerformanceRating() + "\n" +
                        "Experience: " + employee.getYearsOfExperience()
                        );
                }catch(EmployeeNotFoundException e){
                    alertUi.displayError(e.getMessage());
                }
                
                return null;
            }
            return null;
        });

        dialog.showAndWait();
    }
}
