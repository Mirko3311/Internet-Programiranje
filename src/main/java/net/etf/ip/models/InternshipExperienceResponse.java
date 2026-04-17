package net.etf.ip.models;


public class InternshipExperienceResponse
{
	    private Long idInternshipExperience;
	    private String companyName;
	    private String period;
	    private String description;
	    private String position;
	    
	    
		public String getPosition() {
			return position;
		}
		public void setPosition(String position) {
			this.position = position;
		}
		public Long getIdInternshipExperience() {
			return idInternshipExperience;
		}
		public void setIdInternshipExperience(Long idInternshipExperience) {
			this.idInternshipExperience = idInternshipExperience;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getPeriod() {
			return period;
		}
		public void setPeriod(String period) {
			this.period = period;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
}

