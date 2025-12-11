package application;

public class Applicant {
    private int id;
    private String fullName;
    private String email;

    private String phone;

  ;
    //Lab7 attributes added
    private String address;
    private String highestEducation;
    private Gender gender;   //using Gender enum
    
    

    public Applicant(int id, String fullName, String address, String phone, String email,
                       Gender gender, String highestEducation) {
        this.id = id;
        this.fullName = fullName; //max 50 
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.highestEducation = highestEducation; 

    }

    // Getters
    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getHighestEducation() { return highestEducation; }
    public Gender getGender() { return gender; }

}