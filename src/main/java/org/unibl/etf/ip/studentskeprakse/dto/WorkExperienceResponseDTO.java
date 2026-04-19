package org.unibl.etf.ip.studentskeprakse.dto;

import lombok.Data;

@Data
public class WorkExperienceResponseDTO
{
    private Integer id;
    private String employer;
    private String position;
    private String city;
    private String period;
    private String mainActivities;
}
