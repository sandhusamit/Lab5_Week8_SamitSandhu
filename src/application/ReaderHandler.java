package application;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;

public class ReaderHandler implements EventHandler<ActionEvent> {

    private int aid; 
    private int eid; 
    private final SQLManager sql = new SQLManager();

    private Applicant applicantInfo;
    private SubjectEmployment employmentInfo;

    public ReaderHandler() {
        aid = promptForId("ApplicantID");
        eid = promptForId("EmploymentID");
    }

    private int promptForId(String label) {
        while (true) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter " + label);
            dialog.setHeaderText("Please enter the " + label + ":");
            dialog.setContentText(label + ":");

            Optional<String> result = dialog.showAndWait();
            if (!result.isPresent()) {
                System.out.println("Cancelled.");
                return -1;
            }

            try {
                return Integer.parseInt(result.get());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
    }

    @Override
    public void handle(ActionEvent event) {

        try {
            if (aid > 0)
                applicantInfo = sql.getApplicantById(aid);
        } catch (Exception e) {
            System.out.println("Error fetching applicant.");
        }

        try {
            if (eid > 0)
                employmentInfo = sql.getEmploymentById(eid);
        } catch (Exception e) {
            System.out.println("Error fetching employment.");
        }
    }

    public Applicant getApplicant() { return applicantInfo; }
    public SubjectEmployment getEmployment() { return employmentInfo; }

    public int getAID() { return aid; }
    public int getEID() { return eid; }
}
