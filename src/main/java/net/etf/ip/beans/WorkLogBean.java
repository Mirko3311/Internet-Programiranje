package net.etf.ip.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.etf.ip.service.GenericService;
import net.etf.ip.models.WorkLogResponse; 

public class WorkLogBean implements Serializable {
    private int studentId;
    private int internshipId;
    private final GenericService<WorkLogResponse> service = new GenericService<>();

    public WorkLogBean() {}

    public List<WorkLogResponse> getLogs() {
        try {
            String endpoint = "/work-log/student/" + studentId + "/internship/" + internshipId;
            return service.getList(endpoint, WorkLogResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    

    public boolean saveComment(int logId, String comment) {
        try {
            String endpoint = "/work-log/" + logId + "/comment";
            service.put(endpoint, comment, String.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public List<WorkLogResponse> getLogsForStudent(Integer studentId, Integer internshipId) {
        try {
            GenericService<WorkLogResponse> logService = new GenericService<>();
            String endpoint = "/work-log/student/" + studentId + "/internship/" + internshipId;
            return logService.getList(endpoint, WorkLogResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setInternshipId(int internshipId) { this.internshipId = internshipId; }
    public int getStudentId() { return studentId; }
    public int getInternshipId() { return internshipId; }
}