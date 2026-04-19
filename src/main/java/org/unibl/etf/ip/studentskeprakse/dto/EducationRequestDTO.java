package org.unibl.etf.ip.studentskeprakse.dto;

import lombok.Data;

@Data
public class EducationRequestDTO
{
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private String period;
}
