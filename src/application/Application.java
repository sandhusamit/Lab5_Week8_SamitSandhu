package application;

public class Application {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private String phone;
    private java.sql.Date startDate;
    private String relocate;
    private String comments;

    public Application(int id, String firstName, String lastName, String email,
                       String position, String phone, java.sql.Date startDate,
                       String relocate, String comments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        this.phone = phone;
        this.startDate = startDate;
        this.relocate = relocate;
        this.comments = comments;
    }

    // Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPosition() { return position; }
    public String getPhone() { return phone; }
    public java.sql.Date getStartDate() { return startDate; }
    public String getRelocate() { return relocate; }
    public String getComments() { return comments; }
}