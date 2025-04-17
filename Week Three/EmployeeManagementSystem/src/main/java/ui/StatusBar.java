package main.java.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusBar {
    protected Node buildStatusBar(){
        HBox status = new HBox();
        status.setPadding(new Insets(5));
        status.getChildren().add(new Label("EMP-HUB v1.0.0"));
        return status;
    }
}
