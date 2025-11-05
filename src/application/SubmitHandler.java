package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import java.sql.Date;

public class SubmitHandler implements EventHandler<ActionEvent> {

    private TextField firstTxt, lastTxt, emailField, positionField, phoneField;
    private DatePicker startDatePicker;
    private ToggleGroup relocateGroup;
    private TextArea commentsArea;

    public SubmitHandler(TextField firstTxt, TextField lastTxt, TextField emailField,
                         TextField positionField, TextField phoneField, DatePicker startDatePicker,
                         ToggleGroup relocateGroup, TextArea commentsArea) {
        this.firstTxt = firstTxt;
        this.lastTxt = lastTxt;
        this.emailField = emailField;
        this.positionField = positionField;
        this.phoneField = phoneField;
        this.startDatePicker = startDatePicker;
        this.relocateGroup = relocateGroup;
        this.commentsArea = commentsArea;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            // Read the values when the user clicks Submit
            String firstName = firstTxt.getText();
            String lastName = lastTxt.getText();
            String email = emailField.getText();
            String position = positionField.getText();
            String phone = phoneField.getText();

            java.sql.Date startDate = null;
            if (startDatePicker.getValue() != null) {
                startDate = Date.valueOf(startDatePicker.getValue());
            }

            String relocate = "";
            Toggle selectedToggle = relocateGroup.getSelectedToggle();
            if (selectedToggle != null) {
                relocate = ((RadioButton) selectedToggle).getText();
            }

            String comments = commentsArea.getText();

            // Insert into database
            SQLManager sqlManager = new SQLManager();
            sqlManager.insertApplication(firstName, lastName, email, position, phone, startDate, relocate, comments);

            System.out.println("✅ Application submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error submitting application.");
        }
    }
}
