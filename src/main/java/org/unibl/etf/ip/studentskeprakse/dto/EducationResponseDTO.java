package org.unibl.etf.ip.studentskeprakse.dto;

import lombok.Data;

@Data
public class EducationResponseDTO
{
    private Integer id;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private String period;
}
