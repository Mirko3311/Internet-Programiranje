package net.etf.ip.models;


public class EducationResponse
{
    private Integer id;
    private String institution;
    private String degree;
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getFieldOfStudy() {
		return fieldOfStudy;
	}
	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	private String fieldOfStudy;
    private String period;
}
