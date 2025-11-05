package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {

    private final String url = "jdbc:mysql://localhost:3306/EmploymentApplication";
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

    // Method to get a single application by id
    public EmploymentApplication getApplicationById(int id) {
        String query = "SELECT * FROM applications WHERE id = ?";
        EmploymentApplication app = null;

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id); // Set the id parameter
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    app = new EmploymentApplication(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("position"),
                        rs.getString("phone"),
                        rs.getDate("startDate"),
                        rs.getString("relocate"),
                        rs.getString("comments")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return app; // Will return null if no application with this ID exists
    }
    // Method to get all applications
    public List<EmploymentApplication> getApplications() {
        List<EmploymentApplication> applications = new ArrayList<>();
        String query = "SELECT * FROM applications";

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                EmploymentApplication app = new EmploymentApplication(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("position"),
                    rs.getString("phone"),
                    rs.getDate("startDate"),
                    rs.getString("relocate"),
                    rs.getString("comments")
                );
                applications.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }
    // Method to insert a new application and give back the id
	public int insertApplication(String firstName, String lastName, String email,
	                             String position, String phone, java.sql.Date startDate,
	                             String relocate, String comments) {
	
	    String query = "INSERT INTO applications (firstName, lastName, email, position, phone, startDate, relocate, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    int generatedId = -1;
	
	    try (Connection con = DriverManager.getConnection(url, username, password);
	         PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	
	        pst.setString(1, firstName);
	        pst.setString(2, lastName);
	        pst.setString(3, email);
	        pst.setString(4, position);
	        pst.setString(5, phone);
	        pst.setDate(6, startDate);
	        pst.setString(7, relocate);
	        pst.setString(8, comments);
	
	        int rowsAffected = pst.executeUpdate();
	
	        if (rowsAffected > 0) {
	            try (ResultSet rs = pst.getGeneratedKeys()) {
	                if (rs.next()) {
	                    generatedId = rs.getInt(1); // ✅ Grab the auto-generated ID
	                    System.out.println("✅ Insert successful! New application ID: " + generatedId);
	                }
	            }
	        }
	        
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    return generatedId; // returns -1 if insert failed
	}


}
