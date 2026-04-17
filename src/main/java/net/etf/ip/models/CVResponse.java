package net.etf.ip.models;

import java.util.List;

public class CVResponse {
    private Integer id;
    private String firstName;
    private String lastName;

    private String summary;
    private String photoPath;
    private String email;
    private String phoneNumber;
    private String interests;

    private List<String> skills;

    private List<InternshipExperienceResponse> internships;
    private List<EducationResponse> educations;
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	public List<String> getSkills() {
		return skills;
	}
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	public List<InternshipExperienceResponse> getInternships() {
		return internships;
	}
	public void setInternships(List<InternshipExperienceResponse> internships) {
		this.internships = internships;
	}
	public List<EducationResponse> getEducations() {
		return educations;
	}
	public void setEducations(List<EducationResponse> educations) {
		this.educations = educations;
	}
	public List<WorkExperienceResponse> getWorkExperiences() {
		return workExperiences;
	}
	public void setWorkExperiences(List<WorkExperienceResponse> workExperiences) {
		this.workExperiences = workExperiences;
	}
	private List<WorkExperienceResponse> workExperiences;
}