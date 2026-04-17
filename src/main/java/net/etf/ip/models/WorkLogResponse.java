package net.etf.ip.models;



import java.io.Serializable;
import java.time.Instant;

public class WorkLogResponse implements Serializable {
    
    private Integer id;
    private Integer weekNumber;
    private String description;
    private Instant datesubmitted;
    private String mentorComment;
    private String studentName;

    public WorkLogResponse() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getWeekNumber() { return weekNumber; }
    public void setWeekNumber(Integer weekNumber) { this.weekNumber = weekNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Instant getDatesubmitted() { return datesubmitted; }
    public void setDatesubmitted(Instant datesubmitted) { this.datesubmitted = datesubmitted; }

    public String getMentorComment() { return mentorComment; }
    public void setMentorComment(String mentorComment) { this.mentorComment = mentorComment; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
}