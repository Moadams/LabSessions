package main.java.util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.java.ui.AlertUi;

public class Validator {
    public static boolean isEmployeeFormValid(TextField name, ComboBox<String> department, TextField salary, TextField rating, TextField experience) {
        if (name.getText().isEmpty() || department.getValue() == null || 
            salary.getText().isEmpty() || rating.getText().isEmpty() || 
            experience.getText().isEmpty()) {
            AlertUi.displayError("All fields are required");
            return false;
        }
    
        try {
            double salaryValue = Double.parseDouble(salary.getText());
            if (salaryValue < 0) {
                AlertUi.displayError("Salary cannot be negative");
                return false;
            }
    
            double ratingValue = Double.parseDouble(rating.getText());
            if (ratingValue < 0 || ratingValue > 5) {
                AlertUi.displayError("Performance rating must be between 0 and 5");
                return false;
            }
    
            int experienceValue = Integer.parseInt(experience.getText());
            if (experienceValue < 0) {
                AlertUi.displayError("Experience cannot be negative");
                return false;
            }
    
        } catch (NumberFormatException e) {
            AlertUi.displayError("Invalid number format in salary, rating, or experience");
            return false;
        }
    
        return true;
    }   

    public static boolean isValidRaiseAmount(TextField amount) {
        try {
            double raiseAmount = Double.parseDouble(amount.getText());
            if (raiseAmount < 0) {
                AlertUi.displayError("Raise amount cannot be negative");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertUi.displayError("Invalid number format in raise amount");
            return false;
        }
        return true;
    }
}
