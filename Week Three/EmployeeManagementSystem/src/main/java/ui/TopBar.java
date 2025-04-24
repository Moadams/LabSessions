package main.java.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import main.java.controller.EmployeeController;
import main.java.model.Employee;

public class TopBar {
    EmployeeController employeeController;
    public TopBar(EmployeeController employeeController) {
        
        this.employeeController = employeeController;
    }

    public Node get(){
        HBox topBar = createTopBarLayout(); 

        topBar.getChildren().addAll(
            createAddButton(),
            createRemoveButton(),
            createRaiseButton(),
            createUpdateButton(),
            createHighPaidEmployeesButton(),
            createTotalSalaryButton(),
            createAverageSalaryButton(),
            createAverageDepartmentSalaryButton(),
            createEmployeeByIdButton(),
            createCalculateSalaryButton()
        );
        return topBar;
    }

    /**
     * Create the layout for the top bar. This is a horizontal box with all buttons
     * aligned left and with a padding of 10px.
     * @return the created layout
     */
    private HBox createTopBarLayout() {
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_LEFT);
        return topBar;
    }

    private Button createEmployeeByIdButton() {
        Button getEmployeeButton = new Button("Get employee by id");
        getEmployeeButton.setOnAction(e->employeeController.displayEmployeeById());
        return getEmployeeButton;
    }


    /**
     * Create a button that displays a dialog to add a new employee to the database
     * @return a button that displays a dialog to add a new employee
     */
    private Button createAddButton() {
        Button addButton = new Button("Add Employee");
        addButton.setOnAction(e -> new AddEmployeeDialog(employeeController).showAddEmployeeDialog());
        return addButton;
    }
    
/**
 * Create a button that removes the selected employee from the system.
 * @return a button that triggers the removal of the selected employee
 */

    private Button createRemoveButton() {
        Button removeButton = new Button("Delete Employee");
        removeButton.setOnAction(e -> employeeController.removeSelectedEmployee());
        return removeButton;
    }
    
    /**
     * Create a button that displays a dialog to apply a salary raise to all employees whose rating is 4.5 or higher
     * @return a button that displays a dialog to apply a salary raise
     */
    private Button createRaiseButton() {
        Button raiseButton = new Button("Apply Raise");
        raiseButton.setOnAction(e -> new RaiseSalaryDialog(employeeController).raiseSalary());
        return raiseButton;
    }
    
    /**
     * Create a button that displays a dialog to update the currently selected employee in the table
     * @return a button that displays a dialog to update the currently selected employee
     */
    private Button createUpdateButton() {
        Button updateButton = new Button("Update Employee");
        updateButton.setOnAction(e -> {
            Employee<Integer> selectedEmployee = EmployeeTable.employeeTable.getSelectionModel().getSelectedItem();
            if(selectedEmployee != null){
                new UpdateEmployeeDialog().showUpdateDialog(selectedEmployee);
            }else{
                AlertUi.displayError("Please select an employee from the table");
            }
        });
        return updateButton;
    }

    private Button createCalculateSalaryButton(){
        Button calculateSalaryButton = new Button("Calculate Salary");
        calculateSalaryButton.setOnAction(e->{
            Employee<Integer> selectedEmployee = EmployeeTable.employeeTable.getSelectionModel().getSelectedItem();
            if(selectedEmployee != null){
                employeeController.calculateSalary(selectedEmployee);
            }else{
                AlertUi.displayError("Please select an employee from the table");
            }
            
        });
        return calculateSalaryButton;
    }

    /**
     * Create a button that displays the top 5 paid employees in the system
     * @return a button that displays the top 5 paid employees
     */
    private Button createHighPaidEmployeesButton() {
        Button highPaidEmployeesButton = new Button("Top 5 Paid Employees");
        highPaidEmployeesButton.setOnAction(e-> employeeController.getTopPaidEmployees());
        return highPaidEmployeesButton;
    }

    /**
     * Create a button that displays the total salary of all employees in the system
     * @return a button that displays the total salary of all employees
     */
    private Button createTotalSalaryButton() {    
        Button totalSalaryButton = new Button("Get Total Salary");
        totalSalaryButton.setOnAction(e-> employeeController.displayTotalSalary());
        return totalSalaryButton;
    }

    /**
     * Create a button that displays the average salary of all employees in the system
     * @return a button that displays the average salary of all employees
     */
    private Button createAverageSalaryButton() {
        Button averageSalaryButton = new Button("Get Average Salary");
        averageSalaryButton.setOnAction(e-> employeeController.displayAverageSalary());
        return averageSalaryButton;
    }

    /**
     * Create a button that displays the average salary of all employees in a department. 
     * @return a button that displays the average salary of all employees in a department
     */
    private Button createAverageDepartmentSalaryButton() {    
        Button averageDepartmentSalaryButton = new Button("Get Department Avg. Salary");
        averageDepartmentSalaryButton.setOnAction(e-> employeeController.displayAverageSalaryInDepartment());
        return averageDepartmentSalaryButton;
    }
}
