package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.ui.EmployeeAppUI;
import main.java.ui.WelcomePage;

public class App extends Application {
    private Stage primaryStage;
    
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        WelcomePage introPage = new WelcomePage();
        introPage.startWelcomePage(primaryStage, this); 
    }

    public void showEmployeeUI(){
        EmployeeAppUI mainAppUI = new EmployeeAppUI();
        mainAppUI.startUI(primaryStage);
    }
}
