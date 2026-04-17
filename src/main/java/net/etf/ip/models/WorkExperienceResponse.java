package net.etf.ip.models;


public class WorkExperienceResponse
{
    private Integer id;
    private String employer;
    private String position;
    private String city;
    private String period;
    private String mainActivities;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getMainActivities() {
		return mainActivities;
	}
	public void setMainActivities(String mainActivities) {
		this.mainActivities = mainActivities;
	}
    
}
