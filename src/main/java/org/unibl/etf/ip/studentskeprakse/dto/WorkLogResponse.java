package org.unibl.etf.ip.studentskeprakse.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WorkLogResponse {
    private Integer id;
    private String description;
   @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
   private LocalDateTime datesubmitted;
    private String mentorComment;
    private String studentName;
    private Integer weekNumber;
}


