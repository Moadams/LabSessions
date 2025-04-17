package main.java.ui;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.controller.EmployeeController;
import main.java.database.EmployeeDatabase;


public class EmployeeAppUI {

    // Initialize the in-memory database
    EmployeeDatabase<Integer> employeeDatabase = new EmployeeDatabase<>();

    // Create the employee table UI component
    EmployeeTable employeeTable = new EmployeeTable(employeeDatabase);

    // Create the employee controller, passing the database and table
    EmployeeController employeeController = new EmployeeController(employeeDatabase, employeeTable);


    // Initializes the main UI and binds all components together
    public void startUI(Stage primaryStage){
        primaryStage.setTitle("Employee Management Application | Dashboard");

        // Layout using BorderPane: Top = Navbar, Left = Menu, Center = Table, Bottom = Status
        BorderPane layout = new BorderPane();

        // Top bar with app title and controls
        layout.setTop(new TopBar(employeeController).get());

        // Sidebar with navigation actions
        layout.setLeft(new SideBar(employeeController).get());

        // Main table view for displaying employee records
        layout.setCenter(employeeTable.get());

        // Status bar at the bottom 
        layout.setBottom(new StatusBar().buildStatusBar());

        // Attach layout to scene and display
        primaryStage.setScene(new Scene(layout, 1200,600));
        primaryStage.show();
    }
    
}
