package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.sql.Date;

public class SubmitHandler implements EventHandler<ActionEvent> {

    private TextField fullNameTxt, emailField, positionField, desiredSalary, phoneField, addressField;
    private String highestEducation;
    private DatePicker startDatePicker;
    private ToggleGroup isLegalGroup;
    private ToggleGroup genderGroup;
    private ComboBox<String> highestEducationCB;

    private ToggleGroup relativeWorkingGroup;
    private TextArea relativeExplainTA;

    public SubmitHandler(TextField fullNameTxt, TextField addressField, TextField phoneField, TextField emailField,
                         ToggleGroup genderGroup, ComboBox<String> highestEducationCB,  DatePicker startDatePicker, TextField positionField, TextField desiredSalary,
                         ToggleGroup isLegalGroup, ToggleGroup relativeWorkingGroup, TextArea relativeExplainTA) {
    	
    	

    	this.fullNameTxt = fullNameTxt;
    	this.addressField = addressField;
    	this.phoneField = phoneField;
    	this.emailField = emailField;
    	this.genderGroup = genderGroup;
    	this.highestEducationCB = highestEducationCB;
    	this.positionField = positionField;
    	this.desiredSalary = desiredSalary;
    	this.startDatePicker = startDatePicker;
    	this.isLegalGroup = isLegalGroup;
    	this.relativeWorkingGroup = relativeWorkingGroup;
    	this.relativeExplainTA = relativeExplainTA; 
    }

    @Override
    public void handle(ActionEvent event) {
        try {
        	
            SQLManager sqlManager = new SQLManager();
            // Read the values when the user clicks Submit
        	// == ApplicantTable Data ===
            String fullName = fullNameTxt.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            Gender gender = null; 
            Toggle genderToggle = genderGroup.getSelectedToggle();
            String highestEducation = highestEducationCB.getValue();
            if(genderToggle != null)
            {
            	gender = sqlManager.convertGender(((RadioButton) genderToggle).getText()); //Note for DEV: can possibly do getValue and assign Enum - no converter needed
            }
            //Note For DEV: highest education must be changed to dropdown after! but leave as string for input textfield for now... 
                    
            System.out.print("Creating Applicant...");
            
            // == EmploymentTable Data === 
            //insert to retrieve fk ID
            int id = sqlManager.insertApplicant(fullName, address, phone, email, gender, highestEducation);
            if(id != -1)
                System.out.print("Applicant Created!");
            else
                System.out.print("ApplicantID is -1");

            java.sql.Date startDate = null;
            if (startDatePicker.getValue() != null) {
                startDate = Date.valueOf(startDatePicker.getValue());
            }
            String position = positionField.getText();
            
            //DesiredSal
            double salary = toIntSafe(desiredSalary.getText());
            
            //Is Legal 
          
            Toggle isLegalToggle = isLegalGroup.getSelectedToggle();
            Boolean isLegal = checkToggleYN(isLegalToggle); // 1 or 0 to insert into DB
            
            //Is Rel Working - same as isLegal (Yes or No)
            
            Toggle isRelToggle = relativeWorkingGroup.getSelectedToggle();
            Boolean isRel = checkToggleYN(isRelToggle);
            
            String relExplain = relativeExplainTA.getText();
            
            
            int empID = sqlManager.insertEmployment(id, startDate, position, salary, isLegal, isRel, relExplain);
            
                        
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Application Submitted");
            alert.setHeaderText("Applicant ID:" + id);
            alert.setContentText("WARNING! Save your applicant ID # and employment ID # for retrieval! - Employment ID:" + empID);
            alert.showAndWait();
            
            System.out.print("✅ Application  submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error submitting application.");
        }
    }
    
    
    //Helper methods
    private Boolean checkToggleYN(Toggle providedToggle) //inputting into DB as T/F 1/0
    {
    	Boolean inputDB = null;
    	if(providedToggle != null)
    	{
    		String selectedToggle = ((RadioButton) providedToggle).getText();
        	switch (selectedToggle)
        	{
        	case "yes":
        		inputDB = true;
        		break;
        	case "no":
        		inputDB = false;
        		break;
        	default:
        		return inputDB; 
        	}
    	}
    	return inputDB;
    }

    public static int toIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0; // or any safe default
        }
    }

    
}
