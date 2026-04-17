package net.etf.ip.models;

import java.time.LocalDate;
import java.util.List;

public class InternshipRequest 
{
	    private String title;
	    private String description;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private List<String> skills;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public LocalDate getStartDate() {
			return startDate;
		}
		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}
		public LocalDate getEndDate() {
			return endDate;
		}
		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}
		public List<String> getSkills() {
			return skills;
		}
		public void setSkills(List<String> skills) {
			this.skills = skills;
		}

}
