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

        // === Name Row ===
        Label firstLbl = new Label("First Name");
        TextField firstTxt = new TextField();
        Label lastLbl = new Label("Last Name");
        TextField lastTxt = new TextField();


        VBox firstBox = new VBox(2, firstLbl, firstTxt);
        VBox lastBox = new VBox(2, lastLbl, lastTxt);
        HBox nameRow = new HBox(15, firstBox, lastBox);
        nameRow.setAlignment(Pos.CENTER_LEFT);
        pane.add(nameRow, 0, 0, 2, 1);

        // === Email ===
        pane.add(new Label("Email"), 0, 1);
        TextField emailField = new TextField();

        pane.add(emailField, 0, 2, 2, 1);

        // === Position ===
        pane.add(new Label("Position Applying For *"), 0, 3);
        TextField positionField = new TextField();
        pane.add(positionField, 0, 4, 2, 1);

        // === Phone + Start Date ===
        pane.add(new Label("Phone *"), 0, 5);
        TextField phoneField = new TextField();

        pane.add(phoneField, 0, 6);

        pane.add(new Label("When Can You Start? YYY-MM-DD"), 1, 5);

        DatePicker startDatePicker = new DatePicker();
        pane.add(startDatePicker, 1, 6);


        
        
        // === Relocate ===
        pane.add(new Label("Willing to Relocate?"), 0, 7);
        ToggleGroup relocateGroup = new ToggleGroup();

        RadioButton yes = new RadioButton("Yes");
        RadioButton no = new RadioButton("No");
        RadioButton unsure = new RadioButton("Not Sure");
        yes.setToggleGroup(relocateGroup);
        no.setToggleGroup(relocateGroup);
        unsure.setToggleGroup(relocateGroup);
        HBox relocateBox = new HBox(10, yes, no, unsure);
        relocateBox.setAlignment(Pos.CENTER_LEFT);
        pane.add(relocateBox, 0, 8, 2, 1);

 
        // === Comments ===
        pane.add(new Label("Comments"), 0, 9);
        TextArea ta = new TextArea();

        ta.setPrefRowCount(2);
        pane.add(ta, 0, 10, 2, 1);

        // === Button ===
        Button submitBtn = new Button("Send Application");
        Button viewBtn = new Button("Review Application");
        Button clearBtn = new Button("Clear Application");
        HBox btnBox = new HBox(submitBtn, viewBtn, clearBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(5, 0, 0, 0));
        pane.add(btnBox, 0, 11, 2, 1);
        SQLManager sqlManager = new SQLManager();
        
     // Pass form controls, not their text
        SubmitHandler submitHandler = new SubmitHandler(
            firstTxt, lastTxt, emailField, positionField, phoneField,
            startDatePicker, relocateGroup, ta
        );
        
        
        
        // Event Handling
        submitBtn.setOnAction(submitHandler);      
        clearBtn.setOnAction(event -> {
        	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        	alert.setTitle("Warning!");
        	alert.setHeaderText("Are you sure you’d like to clear the application without saving?");
        	alert.setContentText("All entered data will be lost.");

        	Optional<ButtonType> result = alert.showAndWait();

        	if (result.isPresent() && result.get() == ButtonType.OK) {
        	    // ✅ user confirmed
            	System.out.println("Application Cleared");
                //Set the visible form inputs to show application details
            	firstTxt.clear();
                //Set it so that user cannot edit whats shown
                firstTxt.setEditable(true);
                
                lastTxt.clear();
                lastTxt.setEditable(true);
                
                emailField.clear();
                emailField.setEditable(true);
                
                positionField.clear();
                positionField.setEditable(true);
                
                phoneField.clear();
                phoneField.setEditable(true);
                
                startDatePicker.setEditable(true);
                startDatePicker.setDisable(false); // unlock

        		yes.setSelected(false);
        		no.setSelected(false);
        		unsure.setSelected(false);

        		yes.setDisable(false);
        		no.setDisable(false);
        		unsure.setDisable(false);
                
                
                
        		ta.clear();
        		ta.setEditable(true);
        	    // clear fields here
        	} else {
        	    // ❌ user canceled
        	    System.out.println("Canceled.");
        	}

         

        });
        
        viewBtn.setOnAction(event -> {
            ReaderHandler readerHandler = new ReaderHandler();
            readerHandler.handle(event); // Run your reader logic - will fetch application from DB + assign to readerHandler.application
            
            // Then do whatever else you need
            System.out.println("Application ID: " + readerHandler.getId());
            EmploymentApplication app = readerHandler.getApplication();
            if (app != null) {
                
            	//Let console know which applicant is retrieved 
            	System.out.println("Application retrieved: " + app.getFirstName());
                //Set the visible form inputs to show application details
            	firstTxt.setText(app.getFirstName());
                //Set it so that user cannot edit whats shown
                firstTxt.setEditable(false);
                
                lastTxt.setText(app.getLastName());
                lastTxt.setEditable(false);
                
                emailField.setText(app.getEmail());
                emailField.setEditable(false);
                
                positionField.setText(app.getPosition());
                positionField.setEditable(false);
                
                phoneField.setText(app.getPhone());
                phoneField.setEditable(false);
                
                //Date picker must be locked and set 
                Date date = app.getStartDate(); // java.sql.Date
                startDatePicker.setValue(date.toLocalDate()); // convert safely
                startDatePicker.setEditable(false);
                startDatePicker.setDisable(true); // fully lock

                //Toggle group selection + lock
                switch(app.getRelocate())
                {
                	case "Yes":
                		yes.setSelected(true);
                	case "No":
                		no.setSelected(true);
                	default:
                		unsure.setSelected(true);

                }
        		yes.setDisable(true);
        		no.setDisable(true);
        		unsure.setDisable(true);
                
                
                
                ta.setText(app.getComments() + "\nThe fields for the application you've selected has been filled above.");
                ta.setEditable(false);
            }
            else {
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
        Scene scene = new Scene(mainPane, 500, 600);
        primaryStage.setTitle("Employment Application Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
