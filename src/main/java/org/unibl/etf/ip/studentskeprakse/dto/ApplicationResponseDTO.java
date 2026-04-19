package org.unibl.etf.ip.studentskeprakse.dto;


import lombok.Data;

@Data
public class ApplicationResponseDTO {
    private Integer applicationId;
    private String status;
    private Integer internshipId;
    private String internshipTitle;
    private String companyName;
    private String studentFullName;
    private Integer studentId;
    private Integer grade;
    private String  comment;
}