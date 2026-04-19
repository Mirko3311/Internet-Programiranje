package org.unibl.etf.ip.studentskeprakse.dto;
import lombok.Data;

@Data
public class WorkLogRequest {
    private Integer weekNumber;
    private String description;
}