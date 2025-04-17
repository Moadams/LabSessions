package main.java.ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.App;

public class WelcomePage {
    

    public void startWelcomePage(Stage primaryStage, App mainApp){
        primaryStage.setTitle("Welcome to Employee Management System");
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffffff, #e0f7fa);");

        // Logo or Title Placeholder
        Text logoText = new Text("👔 EMP-HUB");
        logoText.setFont(Font.font("Verdana", 36));
        logoText.setFill(Color.web("#004d40"));
        logoText.setEffect(new DropShadow(3, Color.GRAY));

        // Main Welcome Text
        Text welcomeText = new Text("Welcome to the Employee Management System");
        welcomeText.setFont(Font.font("Arial", 24));
        welcomeText.setFill(Color.web("#333"));
        welcomeText.setEffect(new DropShadow(2, Color.LIGHTGRAY));
        
        // Subtitle
        Text subtitle = new Text("Manage, Analyze, and Streamline Your Workforce");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setFill(Color.web("#555"));

        // Enter Button
        Button enterButton = new Button("Enter Dashboard");
        enterButton.setFont(Font.font("Arial", 16));
        enterButton.setStyle("-fx-padding: 10px 20px; -fx-background-color: #00796b; -fx-text-fill: white; -fx-font-weight: bold;");
        enterButton.setEffect(new DropShadow(3, Color.LIGHTGRAY));

        // Button Action
        enterButton.setOnAction(e -> {
            // Animate fade-out before switching to dashboard
            FadeTransition fade = new FadeTransition(Duration.seconds(1), layout);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(event -> mainApp.showEmployeeUI());
            fade.play();
        });
        
        layout.getChildren().addAll(logoText, welcomeText, subtitle, enterButton);
        Scene introScene = new Scene(layout, 1200, 600);
        primaryStage.setScene(introScene);
        primaryStage.show();

    }

}
