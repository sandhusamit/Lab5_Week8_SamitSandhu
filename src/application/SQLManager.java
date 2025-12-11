package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {

    private final String url = "jdbc:mysql://localhost:3306/employmentapplicationdb";
    private final String username = "root";
    private final String password = "Sandhu@1";

    // Constructor loads the driver
    public SQLManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("❌ MySQL Driver not found.");
        }
    }
    
    
// =============== APPLICANT TABLE CRUD ========================== //    
    public Applicant getApplicantById(int id) {
        String query = "SELECT * FROM applicanttable WHERE Applicant_ID = ?";
        Applicant app = null;
// PREPARED STATEMENT 
        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id); // Set the id parameter
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    app = new Applicant(
                        rs.getInt("Applicant_ID"),
                        rs.getString("Full_Name"),
                        rs.getString("Current_Address"),
                        rs.getString("Contact_Number"),
                        rs.getString("Email_Address"),
                        convertGender(rs.getString("Gender")), //Convert to gender type
                        rs.getString("Highest_Education")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return app; // Will return null if no applicant with this ID exists
    }
    
    
    //Helper method for getApplicant
    
    public Gender convertGender(String value) {
        if (value == null) return null;
        switch (value.trim().toLowerCase()) {
            case "male": return Gender.MALE;
            case "female": return Gender.FEMALE;
            case "other": return Gender.OTHER;
            default: throw new IllegalArgumentException("Unknown gender: " + value);
        }
    }

    // Method to get all applicants
    public List<Applicant> getApplicants() {
        List<Applicant> applicants = new ArrayList<>();
        String query = "SELECT * FROM applicanttable";

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) { // Loop over each row
                Applicant applicant = new Applicant(
                    rs.getInt("Applicant_ID"),
                    rs.getString("Full_Name"),
                    rs.getString("Current_Address"),
                    rs.getString("Contact_Number"),
                    rs.getString("Email_Address"),
                    convertGender(rs.getString("Gender")),
                    rs.getString("Highest_Education")
                );
                applicants.add(applicant); // Add each applicant inside the loop
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applicants; // Returns all applicants
    }

    
    // Method to insert a new application and give back the id
	public int insertApplicant(String fullName, String address, String phone, String email,
            Gender gender, String highestEducation) {
	
	    String query = "INSERT INTO applicanttable (Full_Name, Current_Address, Contact_Number, Email_Address, Gender, Highest_Education) VALUES (?, ?, ?, ?, ?, ?)";
	    int generatedId = -1;
	
	    try (Connection con = DriverManager.getConnection(url, username, password);
	         PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	
	    	
	    	
	    	pst.setString(1, fullName);
	    	pst.setString(2,  address);
	    	pst.setString(3, phone);
	    	pst.setString(4, email);
	    	pst.setString(5, gender.name());
	    	System.out.print(highestEducation);
	    	pst.setString(6, highestEducation);
	 
	        int rowsAffected = pst.executeUpdate();
	
	        if (rowsAffected > 0) {
	            try (ResultSet rs = pst.getGeneratedKeys()) {
	                if (rs.next()) {
	                    generatedId = rs.getInt(1); // ✅ Grab the auto-generated ID
	                    System.out.println("✅ Insert successful! New applicant ID: " + generatedId);
	                }
	            }
	        }
	        
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    return generatedId; // returns -1 if insert failed
	}
	// =============== EMPLOYMENT TABLE CRUD ========================== //    
	public SubjectEmployment getEmploymentById(int id) {
	    String query = "SELECT * FROM employmenttable WHERE Employment_ID = ?";
	    SubjectEmployment emp = null;

	    try (Connection con = DriverManager.getConnection(url, username, password);
	         PreparedStatement pst = con.prepareStatement(query)) {

	        pst.setInt(1, id); // Set the id parameter
	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) { // Move to first row
	                int isLegalInt = rs.getInt("Legal_Authorization");
	                int isRelWorkingInt = rs.getInt("Relative_Working");

	                Boolean isLegal = trueFalseFromDB(isLegalInt);
	                Boolean isRelativeWorking = trueFalseFromDB(isRelWorkingInt);

	                emp = new SubjectEmployment(
	                    rs.getInt("Employment_ID"),
	                    rs.getInt("Applicant_ID"),
	                    rs.getDate("Date_Available"),
	                    rs.getString("Desired_Position"),
	                    rs.getDouble("Desired_Salary"),
	                    isLegal,
	                    isRelativeWorking,
	                    rs.getString("Relative_Explanation")
	                );
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return emp; // Will return null if no employment with this ID exists
	}

	    
	    
	    //Helper method for getEmployment
	    
	   public static Boolean trueFalseFromDB(int tf) //1 true or 0 false
	   {
		   switch(tf)
		   {
		   		case 0:
		   			return false;
		   		case 1:
		   			return true;
		   		default:
		   			return null;
		   }
	   }

	   
// Optional - GetAllEmployments
	    
	    // Method to insert a new employment and give back the id
		public int insertEmployment(int applicantID, java.sql.Date startDate, String position, Double desiredSalary, Boolean isLegal, Boolean relativeWorking, String relativeExplain) {
		
		    String query = "INSERT INTO employmenttable (Applicant_ID, Date_Available, Desired_Position, Desired_Salary, Legal_Authorization, Relative_Working, Relative_Explanation) VALUES (?, ?, ?, ?, ?, ?, ?)";
		    int generatedId = -1;
		    int isLegalInt = -1;
		    int relInt = -1;
		
		    try (Connection con = DriverManager.getConnection(url, username, password);
		         PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
		//If True
		    	if(isLegal)
		    		isLegalInt = 1;
		    	else
		    		isLegalInt = 0;
		    	
		    	if(relativeWorking)
		    		relInt = 1;
		    	else
		    		relInt = 0;
		    	
		    	pst.setInt(1, applicantID);
		    	pst.setDate(2,  startDate);
		    	pst.setString(3, position);
		    	pst.setDouble(4, desiredSalary);
		    	pst.setInt(5, isLegalInt);
		    	pst.setInt(6, relInt);
		    	pst.setString(7, relativeExplain); // empty string if relInt ==0
		 
		        int rowsAffected = pst.executeUpdate();
		
		        if (rowsAffected > 0) {
		            try (ResultSet rs = pst.getGeneratedKeys()) {
		                if (rs.next()) {
		                    generatedId = rs.getInt(1); // ✅ Grab the auto-generated ID
		                    System.out.println("✅ Insert successful! New employment ID: " + generatedId);
		                }
		            }
		        }
		        
		
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
		    return generatedId; // returns -1 if insert failed
		}
	

}
