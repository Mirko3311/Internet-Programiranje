package org.unibl.etf.ip.studentskeprakse.dto;

import lombok.Data;

@Data
public class InternshipExperienceRequestDTO
{
    private String companyName;
    private String position;
    private String period;
    private String description;
}
