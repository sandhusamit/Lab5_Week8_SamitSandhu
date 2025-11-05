package application;

import java.sql.Date;
import java.time.LocalDate;

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
        HBox btnBox = new HBox(submitBtn, viewBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(5, 0, 0, 0));
        pane.add(btnBox, 0, 11, 2, 1);
        SQLManager sqlManager = new SQLManager();
        
     // Pass form controls, not their text
        SubmitHandler submitHandler = new SubmitHandler(
            firstTxt, lastTxt, emailField, positionField, phoneField,
            startDatePicker, relocateGroup, ta
        );

        submitBtn.setOnAction(submitHandler);

        
        // Event Handling
        submitBtn.setOnAction(submitHandler);        
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
