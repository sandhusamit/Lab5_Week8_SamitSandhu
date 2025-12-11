package application;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // ===== Main Layout =====
        BorderPane mainPane = new BorderPane();

        // ===== Top Image =====
        Image favSport = new Image(getClass().getResource("snowboard.jpg").toExternalForm());
        ImageView iv = new ImageView(favSport);
        iv.setFitWidth(180);
        iv.setPreserveRatio(true);

        HBox topBox = new HBox(iv);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10, 0, 5, 0));
        mainPane.setTop(topBox);

        // ===== Center Form =====
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10));
        pane.setHgap(15);
        pane.setVgap(6);

        // === Name + Address ===
        Label firstLbl = new Label("Full Name (Max 50 characters)");
        TextField firstTxt = new TextField();
        firstTxt.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= 50) {
                return change; // allow change
            } else {
                return null; // reject change
            }
        }));


        Label addyLbl = new Label("Current Address");
        TextField addyField = new TextField();

        VBox firstBox = new VBox(2, firstLbl, firstTxt);
        VBox addyBox = new VBox(2, addyLbl, addyField);  // FIXED: no duplicate node

        HBox nameRow = new HBox(15, firstBox, addyBox);
        pane.add(nameRow, 0, 0, 2, 1);

        // === Email ===
        pane.add(new Label("Email"), 0, 1);
        TextField emailField = new TextField();
        pane.add(emailField, 0, 2, 2, 1);

        // === Position ===
        pane.add(new Label("Position Applying For *"), 0, 3);
        TextField positionField = new TextField();
        pane.add(positionField, 0, 4, 2, 1);

        // === Phone & Start Date ===
        pane.add(new Label("Phone *"), 0, 5);
        TextField phoneField = new TextField();
        pane.add(phoneField, 0, 6);

        pane.add(new Label("When Can You Start? YYYY-MM-DD"), 1, 5);
        DatePicker startDatePicker = new DatePicker();
        pane.add(startDatePicker, 1, 6);

        // === Legal Authorization ===
        pane.add(new Label("Are you authorized to work in Canada?"), 0, 7);

        ToggleGroup isLegalGroup = new ToggleGroup();
        RadioButton yes = new RadioButton("yes");
        RadioButton no = new RadioButton("no");
        yes.setToggleGroup(isLegalGroup);
        no.setToggleGroup(isLegalGroup);

        HBox isLegalBox = new HBox(10, yes, no);
        pane.add(isLegalBox, 1, 7);

        // === Relative Working Section ===
        pane.add(new Label("Do you have relatives working here?"), 0, 8);

        ToggleGroup relativeWorkingGroup = new ToggleGroup();
        RadioButton yes1 = new RadioButton("yes");
        RadioButton no1 = new RadioButton("no");

        yes1.setToggleGroup(relativeWorkingGroup); // FIXED
        no1.setToggleGroup(relativeWorkingGroup);  // FIXED

        HBox isRelBox = new HBox(10, yes1, no1);
        pane.add(isRelBox, 1, 8);

        // === Explain (Moved down to fix row collision) ===
        pane.add(new Label("Explain (If Relative Works Here)"), 0, 9);
        TextArea ta = new TextArea();
        ta.setPrefRowCount(2);
        pane.add(ta, 0, 10, 2, 1);

        // === Gender ===
        pane.add(new Label("Gender"), 0, 11);

        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton male = new RadioButton("male");
        RadioButton female = new RadioButton("female");
        RadioButton other = new RadioButton("other");

        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        other.setToggleGroup(genderGroup);

        HBox genderBox = new HBox(10, male, female, other);
        pane.add(genderBox, 1, 11);

        // === Desired Salary ===
        pane.add(new Label("Desired Salary *"), 0, 12);
        TextField desiredSalary = new TextField();
        pane.add(desiredSalary, 1, 12);

        // === Highest Education ===
        pane.add(new Label("Highest Education *"), 0, 13);
        ComboBox<String> highestEducationBox = new ComboBox<>();
        highestEducationBox.getItems().addAll("Masters", "Bachelors", "College Diploma");
        highestEducationBox.setPromptText("Select Education");
        pane.add(highestEducationBox, 1, 13);
        
        String highestEducation = getSelectedEducation(highestEducationBox); 
        System.out.print("highest education selected" + highestEducation);


        // === Buttons ===
        Button submitBtn = new Button("Send Application");
        Button viewBtn = new Button("Review Application");
        Button clearBtn = new Button("Clear Application");

        HBox btnBox = new HBox(10, submitBtn, viewBtn, clearBtn);
        btnBox.setAlignment(Pos.CENTER);

        pane.add(btnBox, 0, 14, 2, 1);

        // === Handler Wiring ===
        SubmitHandler submitHandler = new SubmitHandler(
                firstTxt, addyField, phoneField, emailField,
                genderGroup, highestEducationBox, startDatePicker,
                positionField, desiredSalary, isLegalGroup,
                relativeWorkingGroup, ta
        );

        submitBtn.setOnAction(submitHandler);

        clearBtn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("Clear the form?");
            alert.setContentText("All data will be lost.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                firstTxt.clear();
                addyField.clear();
                emailField.clear();
                phoneField.clear();
                positionField.clear();
                desiredSalary.clear();
                highestEducationBox.setValue(null);

                
                
                ta.clear();

                startDatePicker.setValue(null);
                isLegalGroup.selectToggle(null);
                relativeWorkingGroup.selectToggle(null);
                genderGroup.selectToggle(null);

                System.out.println("Application cleared.");
            }
        });
        
		viewBtn.setOnAction(event -> {
		
		    ReaderHandler readerHandler = new ReaderHandler();
		    readerHandler.handle(event);
		
		    System.out.println("Applicant ID: " + readerHandler.getAID());
		
		    Applicant app = readerHandler.getApplicant();
		    SubjectEmployment emp = readerHandler.getEmployment();
		
		    if (app != null && emp != null) {
		
		        System.out.println("Applicant retrieved: " + app.getFullName());
		
		        // ----- BASIC TEXT FIELDS -----
		        firstTxt.setText(app.getFullName());
		        firstTxt.setEditable(false);
		
		        addyField.setText(app.getAddress());
		        addyField.setEditable(false);
		
		        emailField.setText(app.getEmail());
		        emailField.setEditable(false);
		
		        positionField.setText(emp.getPosition());
		        positionField.setEditable(false);
		
		        phoneField.setText(app.getPhone());
		        phoneField.setEditable(false);
		        
		        desiredSalary.setText(emp.getDesiredSalary().toString());
		        desiredSalary.setEditable(false);
		
		        // ----- START DATE -----
		        Date date = emp.getStartDate();
		        if (date != null) {
		            startDatePicker.setValue(date.toLocalDate());
		        }
		        startDatePicker.setDisable(true);
		
		        // ----- LEGAL AUTH (yes/no) -----
		        Boolean legal = emp.getIsLegal();
		        if (legal) {
		            yes.setSelected(true);
		        }else {
		            no.setSelected(true);
		        }
		        yes.setDisable(true);
		        no.setDisable(true);
		
		        // ----- RELATIVES WORK HERE (yes/no) -----
		        Boolean rel = emp.getRelativeWorking();
		        if (rel) {
		            yes1.setSelected(true);
		        }else {
		            no1.setSelected(true);
		        }
		        yes1.setDisable(true);
		        no1.setDisable(true);
		
		        // ----- GENDER -----
		        Gender gen = app.getGender();
		        if (gen != null) {
		            if (gen.equals(Gender.MALE)) male.setSelected(true);
		            if (gen.equals(Gender.FEMALE)) female.setSelected(true);
		            if (gen.equals(Gender.OTHER)) other.setSelected(true);
		        }
		        male.setDisable(true);
		        female.setDisable(true);
		        other.setDisable(true);
		
		        // ----- EDUCATION -----
		        highestEducationBox.setValue(app.getHighestEducation());
		        highestEducationBox.setDisable(true);
		
		        // ----- Explanation -----
		        ta.setText(emp.getRelativeExplain());
		        ta.setEditable(false);
		
		    } else {
		        System.out.println("No App Retrieved!");
		    }
		});

        mainPane.setCenter(pane);

        // ===== Bottom Date =====
        Label dateLbl = new Label("Today's Date: " + LocalDate.now());
        HBox bottomBox = new HBox(dateLbl);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(5));
        mainPane.setBottom(bottomBox);

        // ===== Scene Setup =====
        Scene scene = new Scene(mainPane, 500, 650);
        primaryStage.setTitle("Employment Application Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //Helper Method
    private String getSelectedEducation(ComboBox<String> eduBox) {
        String selected = eduBox.getValue();
        return selected != null ? selected : "";
    }


    public static void main(String[] args) {
        launch(args);
    }
}
