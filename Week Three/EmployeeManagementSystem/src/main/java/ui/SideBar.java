package main.java.ui;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.java.controller.EmployeeController;
import main.java.model.Employee;

public class SideBar {
    EmployeeController employeeController;

    public SideBar(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    protected Node get(){
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));

        // Search Field
        TextField nameField = new TextField();
        nameField.setPromptText("Search Name");

        // Dynamic department list
        ComboBox<String> departmentBox = new ComboBox<>();
        departmentBox.getItems().addAll("All", "IT", "HR", "Finance");
        departmentBox.setValue("All");

        // Performance rating slider
        Slider ratingSlider = new Slider(0,5,0);
        ratingSlider.setShowTickLabels(true);
        ratingSlider.setShowTickMarks(true);

        Label ratingLabel = new Label("Performance >=");
        // Add listener to update the label when the slider value changes
        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ratingLabel.setText("Performance >= " + String.format("%.1f", newValue)); // Update label with the current value
        });

        // Salary fields
        TextField minSalary = new TextField();
        minSalary.setPromptText("Min Salary");
        
        TextField maxSalary = new TextField();
        maxSalary.setPromptText("Max Salary");

        // Apply filters button
        Button applyFilters = new Button("Apply Filters");

        applyFilters.setOnAction(e->{
            EmployeeTable.employeeTable.refresh();
            String name = nameField.getText().toLowerCase();
            String dept = departmentBox.getValue();
            double rating = ratingSlider.getValue();
            double min = minSalary.getText().isEmpty() ? 0 : Double.parseDouble(minSalary.getText());
            double max = maxSalary.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxSalary.getText());

            List<Employee<Integer>> filtered = EmployeeTable.employeeList.stream().filter(emp ->  emp.getName().toLowerCase().contains(name)).filter(emp -> dept.equals("All") || emp.getDepartment().equalsIgnoreCase(dept)).filter(emp -> emp.getPerformanceRating() >= rating).filter(emp -> emp.getSalary() >= min && emp.getSalary() <= max).collect(Collectors.toList());

            EmployeeTable.employeeTable.setItems(FXCollections.observableArrayList(filtered));
        });

        Button sortByExperience = new Button("Sort by experience");
        sortByExperience.setOnAction(e -> employeeController.compareByExperience());

        Button sortBySalary = new Button("Sort by salary");
        
        sortBySalary.setOnAction(e-> employeeController.compareBySalary());

        Button sortByRating = new Button("Sort by rating");
        sortByRating.setOnAction(e-> employeeController.compareByRating());


        sidebar.getChildren().addAll(
            new Label("Search by name:"), nameField,
            new Label("Department: "), departmentBox,
            ratingLabel, ratingSlider,
            new Label("Salary Range:"), minSalary, maxSalary, applyFilters, new Separator(), new Label("Sort By:"), sortByExperience, sortBySalary, sortByRating
        );

       

        return sidebar;
    }

}
