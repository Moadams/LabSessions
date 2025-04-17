package main.java.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.database.EmployeeDatabase;
import main.java.model.Employee;

public class EmployeeTable {
    public static TableView<Employee<Integer>> employeeTable = new TableView<>();
    public static ObservableList<Employee<Integer>> employeeList = FXCollections.observableArrayList();
    EmployeeDatabase<Integer> employeeDatabase;

    public EmployeeTable(EmployeeDatabase<Integer> employeeDatabase) {
        
        this.employeeDatabase = employeeDatabase;
    }

    // update table with employee list from database
    public void updateTable() {
        employeeList.setAll(employeeDatabase.getAllEmployees()); 
    }

    public void setTableData(List<Employee<Integer>> employees) {
        employeeList.setAll(employees);

    }

    public Node get(){
        TableColumn<Employee<Integer>, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        TableColumn<Employee<Integer>, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(200);

        TableColumn<Employee<Integer>, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        deptCol.setMinWidth(150);

        TableColumn<Employee<Integer>, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Employee<Integer>, Double> ratingCol = new TableColumn<>("Performance");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("performanceRating"));

        TableColumn<Employee<Integer>, Integer> expCol = new TableColumn<>("Experience");
        expCol.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));

        TableColumn<Employee<Integer>, Boolean> activeCol = new TableColumn<>("Active");
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));

        employeeTable.setItems(employeeList);
        employeeTable.getColumns().addAll(idCol, nameCol, deptCol, salaryCol, ratingCol, expCol, activeCol);

        return employeeTable;
    }
}
