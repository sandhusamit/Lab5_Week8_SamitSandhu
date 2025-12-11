package application;

public class SubjectEmployment {
    private int id;
    private int applicantID; //foreign key
    private java.sql.Date startDate;
    private String position;
    private Double desiredSalary;
    private Boolean isLegal; // 1 or 0
    private Boolean relativeWorking; 
    private String relativeExplain = "N/A";
    
    

    public SubjectEmployment(int id, int applicantID, java.sql.Date startDate, String position, Double desiredSalary, Boolean isLegal, Boolean relativeWorking, String relativeExplain) {

    	this.id = id;
    	this.applicantID = applicantID;
    	this.startDate = startDate;
    	this.position = position;
    	this.desiredSalary = desiredSalary;
    	this.isLegal = isLegal;
    	this.relativeWorking = relativeWorking;
    	if (!relativeExplain.isEmpty())
    		this.relativeExplain = relativeExplain;
    		
    
    }



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getApplicantID() {
		return applicantID;
	}



	public void setApplicantID(int applicantID) {
		this.applicantID = applicantID;
	}



	public java.sql.Date getStartDate() {
		return startDate;
	}



	public void setStartDate(java.sql.Date startDate) {
		this.startDate = startDate;
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
	}



	public Double getDesiredSalary() {
		return desiredSalary;
	}



	public void setDesiredSalary(Double desiredSalary) {
		this.desiredSalary = desiredSalary;
	}



	public Boolean getIsLegal() {
		return isLegal;
	}



	public void setIsLegal(Boolean isLegal) {
		this.isLegal = isLegal;
	}



	public Boolean getRelativeWorking() {
		return relativeWorking;
	}



	public void setRelativeWorking(Boolean relativeWorking) {
		this.relativeWorking = relativeWorking;
	}



	public String getRelativeExplain() {
		return relativeExplain;
	}



	public void setRelativeExplain(String relativeExplain) {
		this.relativeExplain = relativeExplain;
	}

    // Getters

}