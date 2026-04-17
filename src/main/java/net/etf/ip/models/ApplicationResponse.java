package net.etf.ip.models;


import java.io.Serializable;

public class ApplicationResponse implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer applicationId;
    private String status;
    private Integer internshipId;
    private String internshipTitle;
    private String companyName;
    private String studentFullName;
    private Integer studentId;
    
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getInternshipId() {
		return internshipId;
	}
	public void setInternshipId(Integer internshipId) {
		this.internshipId = internshipId;
	}
	public String getInternshipTitle() {
		return internshipTitle;
	}
	public void setInternshipTitle(String internshipTitle) {
		this.internshipTitle = internshipTitle;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStudentFullName() {
		return studentFullName;
	}
	public void setStudentFullName(String studentFullName) {
		this.studentFullName = studentFullName;
	}
    
}
   